package com.bigao.backend.module.level;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleLevelUpLog;
import com.bigao.backend.module.level.dto.LevelInfo;
import com.bigao.backend.module.level.vo.RoleLevelVo;
import com.bigao.backend.util.SqlUtil;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/12/14.
 */
@Service
public class LevelService {

	@Autowired
	private DBUtil4ServerLog dbUtil4ServerLog;

	private Logger logger = LoggerFactory.getLogger(LevelService.class);
	
	public List<LevelInfo> queryRegisterUser(int platform, int server, String startDate, String endDate)
			throws SQLException {
		String prefixSql = "SELECT newLevel, COUNT(roleId) FROM ";
		String repeatSql = "(SELECT MAX(newLevel) AS newLevel, roleId FROM {0} WHERE "
				+ SqlUtil.formatDate(startDate, endDate) + " GROUP BY roleId)";
		String suffixSql = " r1 GROUP BY r1.newLevel ASC";

		ResultSetHandler<List<LevelInfo>> handler = rs -> {
			List<LevelInfo> all = Lists.newArrayList();
			while (rs.next()) {
				all.add(new LevelInfo(rs.getInt(1), rs.getInt(2)));
			}
			return all;
		};
		QueryBuilder builder = QueryBuilder.build(platform, server, RoleLevelUpLog.class, startDate, endDate, handler);
		List<String> tableName = dbUtil4ServerLog.getTableName(builder);
		String sql = SqlUtil.getSqlByTableCount(prefixSql, repeatSql, suffixSql, tableName);
		if(sql==null){
			logger.error("没有此数据库表！");
			return null;
		}
		builder.setSql(sql);
		List<LevelInfo> LevelInfos=dbUtil4ServerLog.BeanQuery(builder);
		return LevelInfos;
	}

	public int getRoleLevel(int platform, int server, long roleId) throws SQLException {
		String querySql = "select max(newLevel) from {0} where roleId=" + roleId;
		List<Integer> allData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql,
				RoleLevelUpLog.class, LocalDate.now().toString(), LocalDate.now().toString(), rs -> {
					if (rs.next()) {
						return rs.getInt(1);
					}
					return 0;
				}));
		return allData.get(0);
	}

	public Map<Long, Integer> getRoleLevel(int platform, int server, Collection<Long> roleId) throws SQLException {
		String querySql = "select roleId,max(newLevel) as level from {0} where roleId in ("
				+ SqlUtil.listToSqlString(roleId) + ") group by roleId";
		List<List<RoleLevelVo>> allData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql,
				RoleLevelUpLog.class, LocalDate.now().toString(), LocalDate.now().toString(), rs -> {
					List<RoleLevelVo> all = Lists.newArrayList();
					while (rs.next()) {
						RoleLevelVo vo = new RoleLevelVo();
						vo.setRoleId(rs.getLong("roleId"));
						vo.setLevel(rs.getInt("level"));
						all.add(vo);
					}
					return all;
				}));
		List<RoleLevelVo> all = dbUtil4ServerLog.toOneList(allData);
		Map<Long, Integer> result = new HashMap<>();
		all.forEach(v -> result.put(v.getRoleId(), v.getLevel()));
		return result;
	}
}
