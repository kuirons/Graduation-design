package com.bigao.backend.module.player;

import com.bigao.backend.common.ParamBuilder;
import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.common.RoleIDKey;
import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.AccountCreateLog;
import com.bigao.backend.log.RoleBagLog;
import com.bigao.backend.log.RoleCreateLog;
import com.bigao.backend.log.RoleLevelUpLog;
import com.bigao.backend.log.RoleLoginOrOutLog;
import com.bigao.backend.log.RoleMonetaryLog;
import com.bigao.backend.module.player.dto.PlayerInfo;
import com.bigao.backend.module.player.dto.PlayerLoginInfo;
import com.bigao.backend.util.DateUtil;
import com.bigao.backend.util.SqlUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 玩家查询
 * Created by wait on 2015/11/30.
 */
@Service
public class PlayerService {

    /** 缓存3000个玩家 */
    private Cache<RoleIDKey, LogRole> roleIDCache = CacheBuilder.newBuilder().maximumSize(3000).build();

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    /**
     * 查询一个玩家
     *
     * @param platform
     * @param server
     * @param queryType 查询类型, 1:根据ID, 2:根据账号 3:根据名字
     * @param param     对应查询类型里面的值, 如ID, 账号, 名字
     * @return
     * @throws SQLException
     */
    public LogRole getRole(int platform, int server, int queryType, String param) throws SQLException {
        if (queryType == 1) {
            RoleIDKey key = new RoleIDKey(platform, server, Long.parseLong(param));
            LogRole logRole = roleIDCache.getIfPresent(key);
            if (logRole == null) {
                logRole = getRoleById(platform, server, param);
                if (logRole != null) {
                    logRole.setPlatform(platform).setServer(server);
                    roleIDCache.put(key, logRole);
                }
            }
            return logRole;
        } else if (queryType == 2) {
            for (LogRole logRole : roleIDCache.asMap().values()) {
                if (logRole.getAccount().equals(param)) {
                    return logRole;
                }
            }
            LogRole logRole = getRoleByAccount(platform, server, param);
            if (logRole != null) {
                RoleIDKey key = new RoleIDKey(platform, server, logRole.getRoleId());
                logRole.setPlatform(platform).setServer(server);
                roleIDCache.put(key, logRole);
            }
            return logRole;
        } else if (queryType == 3) {
            for (LogRole logRole : roleIDCache.asMap().values()) {
                if (logRole.getRoleName().equals(param)) {
                    return logRole;
                }
            }
            LogRole logRole = getRoleByName(platform, server, param);
            if (logRole != null) {
                RoleIDKey key = new RoleIDKey(platform, server, logRole.getRoleId());
                logRole.setPlatform(platform).setServer(server);
                roleIDCache.put(key, logRole);
            }
            return logRole;
        }
        return null;
    }

    /**
     * 查询玩家信息
     *
     * @param platform
     * @param server
     * @param param
     * @return
     * @throws SQLException
     */
    private LogRole getRoleById(int platform, int server, String param) throws SQLException {
        ParamBuilder builder = ParamBuilder.newInstance().select().where().add("id", param).asc("time").limit(1);
        return query(platform, server, builder.build());
    }

    private LogRole query(int platform, int server, String queryCreateSql) throws SQLException {
        List<AccountCreateLog> accountCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, queryCreateSql, AccountCreateLog.class, true));

        if (accountCreateLogs.isEmpty()) {
            return null;
        }
        AccountCreateLog accountCreateLog = accountCreateLogs.get(0);
        ParamBuilder builder = ParamBuilder.newInstance().select().where().add("roleId", accountCreateLog.getId()).asc("time").limit(1);
        List<RoleCreateLog> roleCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, builder.build(), RoleCreateLog.class, true));
        if (roleCreateLogs.isEmpty()) {
            return null;
        }
        RoleCreateLog roleCreateLog = roleCreateLogs.get(0);
        return LogRole.valueOf(accountCreateLog, roleCreateLog);
    }

    private LogRole getRoleByAccount(int platform, int server, String param) throws SQLException {
        String sql = SqlUtil.SELECT_TABLE + " where account=''" + param + "'' order by time asc  limit 1";
        return query(platform, server, sql);
    }

    public List<PlayerInfo> getRoleByLikeName(int platform, int server, String param) throws SQLException {
        String sql = SqlUtil.SELECT_TABLE + " where roleName like ''%" + param + "%'' order by time asc";
        DBServer ls = dbUtil4ServerLog.getDBServer(platform, server);
        String startDate = DateUtil.toLocalDateTimeByTime(ls.getServer().getOpen_date()).toLocalDate().toString();
        List<List<RoleCreateLog>> roleCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, sql, RoleCreateLog.class, startDate, LocalDate.now().toString(), new BeanListHandler<>(RoleCreateLog.class)));
        if (roleCreateLogs == null || roleCreateLogs.isEmpty()) {
            return Collections.emptyList();
        }
        List<RoleCreateLog> allCreate = dbUtil4ServerLog.toOneList(roleCreateLogs);
        ImmutableList.Builder<PlayerInfo> playerInfoBuilder = ImmutableList.builder();
        for (RoleCreateLog log : allCreate) {
            ParamBuilder builder = ParamBuilder.newInstance().select().where().add("id", log.getRoleId()).asc("time").limit(1);
            List<AccountCreateLog> accountCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, builder.build(), AccountCreateLog.class));
            if (accountCreateLogs.isEmpty()) {
                return null;
            }
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setRoleId(String.valueOf(log.getRoleId()));
            playerInfo.setUserName(accountCreateLogs.get(0).getAccount());
            playerInfo.setRoleName(log.getRoleName());
            playerInfoBuilder.add(playerInfo);
        }

        return playerInfoBuilder.build();
    }

    private LogRole getRoleByName(int platform, int server, String param) throws SQLException {
        String sql = SqlUtil.SELECT_TABLE + " where roleName like ''%" + param + "%'' order by time asc  limit 1";
        List<RoleCreateLog> roleCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, sql, RoleCreateLog.class));
        if (roleCreateLogs.isEmpty()) {
            return null;
        }
        RoleCreateLog roleCreateLog = roleCreateLogs.get(0);
        ParamBuilder builder = ParamBuilder.newInstance().select().where().add("id", roleCreateLog.getRoleId()).asc("time").limit(1);
        List<AccountCreateLog> accountCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, builder.build(), AccountCreateLog.class));
        if (accountCreateLogs.isEmpty()) {
            return null;
        }
        return LogRole.valueOf(accountCreateLogs.get(0), roleCreateLog);
    }

    /**
     * 查询某个玩家某段时间的升级日志
     *
     * @param startDate
     * @param endDate
     * @param logRole
     * @return
     */
    public List<RoleLevelUpLog> queryRoleLevel(String startDate, String endDate, LogRole logRole) throws SQLException {
        if (logRole == null || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return Collections.emptyList();
        }
        String querySql = "select *,max(time) from {0} where roleId=" + logRole.getRoleId() + " and " + SqlUtil.formatDate(startDate, endDate) + " group by newLevel";
        List<List<RoleLevelUpLog>> allLog = dbUtil4ServerLog.query(QueryBuilder.build(logRole.getPlatform(), logRole.getServer(), querySql, RoleLevelUpLog.class, startDate, endDate, new BeanListHandler<>(RoleLevelUpLog.class)));
        return dbUtil4ServerLog.toOneList(allLog);
    }

    public List<LogRole> queryLogRole(int platform, int server, List<Long> roleId) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SqlUtil.SELECT_TABLE);
        queryBuilder.append(" where `id` in(");
        for (long id : roleId) {
            queryBuilder.append(id).append(",");
        }
        if (queryBuilder.toString().endsWith(",")) {
            queryBuilder.delete(queryBuilder.length() - 1, queryBuilder.length());
        }
        queryBuilder.append(")");
        List<List<AccountCreateLog>> accountCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, queryBuilder.toString(), AccountCreateLog.class, new BeanListHandler<>(AccountCreateLog.class)));

        queryBuilder.delete(0, queryBuilder.length());
        queryBuilder.append(SqlUtil.SELECT_TABLE);
        queryBuilder.append(" where `roleId` in(");
        for (long id : roleId) {
            queryBuilder.append(id).append(",");
        }
        if (queryBuilder.toString().endsWith(",")) {
            queryBuilder.delete(queryBuilder.length() - 1, queryBuilder.length());
        }
        queryBuilder.append(")");
        List<List<RoleCreateLog>> roleCreateLogs = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, queryBuilder.toString(), RoleCreateLog.class, new BeanListHandler<>(RoleCreateLog.class)));

        List<LogRole> logRoleList = Lists.newArrayList();
        if (accountCreateLogs != null) {
            List<AccountCreateLog> allAccount = dbUtil4ServerLog.toOneList(accountCreateLogs);
            for (AccountCreateLog accountCreateLog : allAccount) {
                LogRole logRole = new LogRole();
                logRole.setAccountCreateLog(accountCreateLog);
                logRoleList.add(logRole);
            }
        }
        if (roleCreateLogs != null) {
            List<RoleCreateLog> allRole = dbUtil4ServerLog.toOneList(roleCreateLogs);
            for (RoleCreateLog roleCreateLog : allRole) {
                logRoleList.stream().filter(logRole -> logRole.getAccountCreateLog().getId() == roleCreateLog.getRoleId()).forEach(logRole -> {
                    logRole.setRoleCreateLog(roleCreateLog);
                });
            }
        }
        return logRoleList;
    }

    public List<PlayerLoginInfo> queryPlayerLoginLog(String startTime, String endTime, LogRole logRole) throws SQLException {
        String querySql = "SELECT `state`,`time`,`ip` FROM {0} WHERE roleId=" + logRole.getRoleId() + " AND " + SqlUtil.formatTime(startTime, endTime) + "   ORDER BY `time` desc limit 100";
        LocalDate nowDate = LocalDate.now();
        List<List<RoleLoginOrOutLog>> data = dbUtil4ServerLog.query(QueryBuilder.build(logRole.getPlatform(), logRole.getServer(), querySql, RoleLoginOrOutLog.class, nowDate.toString(), nowDate.toString(), 100, new BeanListHandler<>(RoleLoginOrOutLog.class)));
        if (data != null) {
            List<RoleLoginOrOutLog> all = dbUtil4ServerLog.toOneList(data);
            ImmutableList.Builder<PlayerLoginInfo> builder = ImmutableList.builder();
            int size = all.size() > 100 ? 100 : all.size();
            for (int i = 0; i < size; i++) {
                RoleLoginOrOutLog log = all.get(i);
                PlayerLoginInfo info = new PlayerLoginInfo();
                info.setState(log.getState());
                info.setIp(log.getIp());
                info.setTime(DateUtil.timeStampToString(log.getTime()));
                builder.add(info);
            }
            return builder.build();
        }
        return Collections.emptyList();
    }

	public List<RoleBagLog> queryRoleBagLog(int platformId, int serverId, int way, String roleId, String startDate,
			String endDate) throws SQLException {
		String str=null;
		if(way==1){
			str="bagType=''BAG''";
		}else if(way==2){
			str="bagType=''DEPOT''";
		}else if(way==3){
			str="bagType=''FISH''";
		}else{
			str="bagType=''GANG''";
		}
		String queruSql="SELECT * FROM {0} WHERE roleId="+roleId+" and "+str+" and "+SqlUtil.formatDate(startDate, endDate)+" order by time asc";
		List<List<RoleBagLog>> bagLogs=dbUtil4ServerLog.query(QueryBuilder.build(platformId, serverId, queruSql, RoleBagLog.class,new BeanListHandler<>(RoleBagLog.class)));
		return dbUtil4ServerLog.toOneList(bagLogs);
	}

	public List<RoleMonetaryLog> queryRoleMonetaryLog(int platformId, int serverId, int way, String roleId,
			String startDate, String endDate) throws SQLException {
		String str=null;
		if(way==1){
			str="monetary=''SILVER''";
		}else if(way==2){
			str="monetary=''COPPER''";
		}else if(way==3){
			str="monetary=''MORAL''";
		}else if(way==4){
			str="monetary=''GOLD''";
		}else if(way==5){
			str="monetary=''HONOR''";
		}else{
			str="monetary=''GANG_ACTIVE''";
		}
		String queruSql="SELECT * FROM {0} WHERE roleId="+roleId+" and "+str+" and "+SqlUtil.formatDate(startDate, endDate)+" order by time asc";
		List<List<RoleMonetaryLog>> bagLogs=dbUtil4ServerLog.query(QueryBuilder.build(platformId, serverId, queruSql, RoleMonetaryLog.class,new BeanListHandler<>(RoleMonetaryLog.class)));
		return dbUtil4ServerLog.toOneList(bagLogs);
	}
}
