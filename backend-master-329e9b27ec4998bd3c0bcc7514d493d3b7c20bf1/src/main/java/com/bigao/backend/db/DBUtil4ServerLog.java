package com.bigao.backend.db;

import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.common.anno.LogType;
import com.bigao.backend.common.anno.TableDesc;
import com.bigao.backend.util.DateUtil;
import com.bigao.backend.util.HttpUtil;
import com.bigao.backend.util.SystemConfig;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class DBUtil4ServerLog {

    private static Logger logger = LoggerFactory.getLogger(DBUtil4ServerLog.class);

    public static final Pattern JDBC_PATTERN = Pattern.compile("jdbc:mysql://(.*?):(\\d*)/(\\w*)\\?");
    private List<Server> sortCache = Lists.newArrayList();

    /** 这里只适用于内网, 内网经常有服务器连不上, 然后连接池会尝试3次连接, 为了启动tomcat快一点, 先屏蔽掉 */
    private static Set<Integer> unreachableServer = new HashSet<>();

    static {
        unreachableServer.add(1);
        unreachableServer.add(1004);
        unreachableServer.add(1111);
        unreachableServer.add(1201);
        unreachableServer.add(1202);
        unreachableServer.add(2000);
        unreachableServer.add(9999);
        unreachableServer.add(10000);
    }

    @Autowired
    @Qualifier("dbaDataSource")
    private BasicDataSource dbaDataSource;

    private ConcurrentHashMap<PlatformKey, DBServer> dbServerMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Platform> platformMap = new ConcurrentHashMap<>();

    /** 服务器里面的所有日志的表 */
    private LoadingCache<DBServer, List<String>> logCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .removalListener((RemovalListener<DBServer, List<String>>) notification -> logger.info("remove, [{},{}]", notification.getKey(), notification.getValue()))
            .build(new CacheLoader<DBServer, List<String>>() {
                @Override
                public List<String> load(DBServer key) throws Exception {
                    logger.info("reload log table [{},{}]", key.getServer().getPlatformId(), key.getServer().getServerId());
                    return getServerLog(key);
                }
            });


    private void closeBeforeReload() throws SQLException {
        for (DBServer dbServer : dbServerMap.values()) {
            dbServer.getDataSource().close();
        }
    }

    /**
     * 加载所有已经开服的服务器
     *
     * @throws SQLException
     */
    @PostConstruct
//    @Scheduled(cron = "0 0 */1 * * ?")
    public synchronized void refreshServer() throws SQLException {
        try {
            closeBeforeReload();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        QueryRunner queryRunner = new QueryRunner(dbaDataSource);
        List<LogicServer> logicServerList = queryRunner.query("select platform,server,logic_server from logic_server group by server", new BeanListHandler<>(LogicServer.class));
        List<Platform> platformList = queryRunner.query("select name,value from platform", new BeanListHandler<>(Platform.class));
        List<Server> gameServerList = queryRunner.query("select id,backend_port,db_log_url,open_date,ip,template from server", new BeanListHandler<>(Server.class));
        List<Template> templateList = queryRunner.query("select id,res_url,db_usr,db_pwd from template", new BeanListHandler<>(Template.class));
        if (templateList.size() > 0) {
            HttpUtil.RES_URL = templateList.get(0).getRes_url();
        }
        Set<Integer> platformId = new HashSet<>();
        // <server, <logic_server>>
        Map<Integer, List<Integer>> serverToLogic = new HashMap<>();
        for (LogicServer ls : logicServerList) {
            if (!serverToLogic.containsKey(ls.getServer())) {
                serverToLogic.put(ls.getServer(), Lists.newArrayList());
            }
            serverToLogic.get(ls.getServer()).add(ls.getLogic_server());
        }
        for (LogicServer ls : logicServerList) {
            Platform platform = null;
            for (Platform p : platformList) {
                if (ls.getPlatform().equals(p.getName())) {
                    platform = p;
                    break;
                }
            }
            if (platform == null) {
                continue;
            }
            PlatformKey key = PlatformKey.valueOf(platform.getValue(), ls.getLogic_server());
            if (dbServerMap.containsKey(key)) {
                continue;
            }
            if (SystemConfig.getProperty("mds.local", "0").equals("1")) {
                if (unreachableServer.contains(ls.getServer())) {
                    continue;
                }
            }
            DBServer dbServer = new DBServer();
            Server server = null;
            for (Server s : gameServerList) {
                if (s.getId() == ls.getServer()) {
                    server = s;
                    break;
                }
            }
            if (server == null) {
                continue;
            }
            sortCache.add(server);
            platformId.add(key.getPlatformId());
            server.setServerId(ls.getLogic_server());
            server.setPlatformId(key.getPlatformId());
            dbServer.setServer(server);
            boolean isMerge = false;
            if (serverToLogic.get(server.getId()).size() > 1
                    && serverToLogic.get(server.getId()).contains(ls.getLogic_server())) {
                isMerge = true;
            }
            String sName = isMerge ? String.valueOf(ls.getLogic_server() + "[合]") : String.valueOf(ls.getLogic_server());
            server.setServerName(sName);
            dbServer.setMerge(isMerge);
            if (!isMerge) {
                BasicDataSource tDataSource = new BasicDataSource();
                Matcher m = JDBC_PATTERN.matcher(server.getDb_log_url());
                if (m.find()) {
                    String dbPort = m.group(2);
                    String dbName = m.group(3);
                    server.setDb_log_url("jdbc:mysql://" + server.getIp() + ":" + dbPort + "/" + dbName + "?autoReconnect=true&amp;characterEncoding=UTF-8");
                    dbServer.setDbName(dbName);
                    dbServer.setDbIp(server.getIp());
                    dbServer.setDbPort(Integer.parseInt(dbPort));
                }

                // 初始化数据库连接池
                tDataSource.setInitialSize(0);
                tDataSource.setMinIdle(0);// 最小空闲连接
                tDataSource.setMaxIdle(10);
                tDataSource.setMaxTotal(10);
                tDataSource.setValidationQuery("SELECT 1");
                tDataSource.setMaxWaitMillis(10000); // 超时等待时间以毫秒为单位
                tDataSource.setTimeBetweenEvictionRunsMillis(600000);
                tDataSource.setMinEvictableIdleTimeMillis(1800000);
                tDataSource.setMaxWaitMillis(10000);

                tDataSource.setLogAbandoned(true);// 连接被泄露时是否打印
                tDataSource.setTestOnBorrow(false);
                tDataSource.setTestWhileIdle(true);


                tDataSource.setUrl(server.getDb_log_url());
                for (Template t : templateList) {
                    if (t.getId() == server.getTemplate()) {
                        tDataSource.setUsername(t.getDb_usr());
                        tDataSource.setPassword(t.getDb_pwd());
                        break;
                    }
                }
                if (StringUtils.isBlank(tDataSource.getUsername())) {
                    continue;
                }
                dbServer.setDataSource(tDataSource);
            }
            dbServerMap.put(key, dbServer);
        }
        for (Platform p : platformList) {
            if (!platformId.contains(p.getValue())) {
                continue;
            }
            platformMap.put(p.getValue(), p);
        }
        Collections.sort(sortCache, (o1, o2) -> o1.getServerId() - o2.getServerId());
        logger.info("refreshServer done...");
    }

    public List<String> getLogByServer(DBServer dbServer) {
        return logCache.getIfPresent(dbServer);
    }

    private List<String> getServerLog(DBServer dbServer) {
        ResultSetHandler<List<String>> handler = rs -> {
            ImmutableList.Builder<String> builder = ImmutableList.builder();
            while (rs.next()) {
                String tName = rs.getString(1);
                if (tName.contains("log")) {
                    builder.add(tName);
                }
            }
            return builder.build();
        };
        QueryRunner runner = new QueryRunner(dbServer.getDataSource());
        
        try {
            return runner.query("select table_name from information_schema.tables where table_schema='" + dbServer.getDbName() + "'", handler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            PlatformKey key = PlatformKey.valueOf(dbServer.getServer().getPlatformId(), dbServer.getServer().getId());
            logger.error("移除服务器[{}]", key);
            dbServerMap.remove(key);
        }
        return null;
    }

    public List<Server> getAllServer() {
        return sortCache;
    }

    public DBServer getDBServer(PlatformKey platformKey) {
        return dbServerMap.get(platformKey);
    }

    public DBServer getDBServer(int platformId, int serverId) {
        return dbServerMap.get(PlatformKey.valueOf(platformId, serverId));
    }

    public boolean isServerExist(int platformId, int serverId) {
        return dbServerMap.containsKey(PlatformKey.valueOf(platformId, serverId));
    }

    public String getOpenTimeString(int platformId, int serverId) {
        if (!dbServerMap.containsKey(PlatformKey.valueOf(platformId, serverId))) {
            return null;
        }
        return dbServerMap.get(PlatformKey.valueOf(platformId, serverId)).getServer().getOpen_date();
    }

    public LocalDateTime getOpenTime(int platformId, int serverId) {
        if (!dbServerMap.containsKey(PlatformKey.valueOf(platformId, serverId))) {
            return null;
        }
        return DateUtil.toLocalDateTimeByTime(dbServerMap.get(PlatformKey.valueOf(platformId, serverId)).getServer().getOpen_date());
    }

    public LocalDate getOpenDate(int platformId, int serverId) {
        if (!dbServerMap.containsKey(PlatformKey.valueOf(platformId, serverId))) {
            return null;
        }
        return DateUtil.toLocalDateByTime(dbServerMap.get(PlatformKey.valueOf(platformId, serverId)).getServer().getOpen_date());
    }

    public ConcurrentHashMap<PlatformKey, DBServer> getDbServerMap() {
        return dbServerMap;
    }

    public Platform getPlatform(String platformName) {
        for (Platform platform : platformMap.values()) {
            if (platform.getName().equals(platformName)) {
                return platform;
            }
        }
        return null;
    }

    public int getPlatformValue(String platformName) {
        Platform p = getPlatform(platformName);
        if (p == null) {
            return 0;
        }
        return p.getValue();
    }

    public ConcurrentHashMap<Integer, Platform> getPlatformMap() {
        return platformMap;
    }

    public List<Platform> getPlatformList() {
        return new ArrayList<>(platformMap.values());
    }

    public List<String> getTable(QueryBuilder builder) throws SQLException {
        if (!builder.getSql().contains("{0}")) {
            throw new SQLException(builder.getSql() + "的表名应该为'{0}', 而不应该是真实表名, 因为需要查询所有跟它属于同一种日志的表");
        }
        DBServer dbServer = getDBServer(builder.getPlatformId(), builder.getServerId());
        if (dbServer == null) {
            logger.error("dbServer[platform:{}, server:{}] is null", builder.getPlatformId(), builder.getServerId());
            return Collections.emptyList();
        }
        if (dbServer.isMerge()) {
            return Collections.emptyList();
        }
        List<String> allLogTable;
        try {
            allLogTable = logCache.get(dbServer);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e.getCause());
            throw new SQLException(e.getCause());
        }
        List<String> sortTable = Lists.newArrayList();
        if (allLogTable == null || allLogTable.isEmpty()) {
            return sortTable;
        }
        // 如果有时间限制, 则只取时间段内的表
        for (String dbTable : allLogTable) {
            if (dbTable.contains(builder.getTableName())) {
                boolean isAdd = true;
                if (!builder.getDbTableClass().getAnnotation(TableDesc.class).equals(LogType.ONLY_ONE)) {
                    if (!StringUtils.isBlank(builder.getStartDate()) && !StringUtils.isBlank(builder.getEndDate())) {
                        if (!DateUtil.isBetween(dbTable, builder.getStartDate(), builder.getEndDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    } else if (!StringUtils.isBlank(builder.getStartDate())) {
                        if (DateUtil.isBefore(dbTable, builder.getStartDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    } else if (!StringUtils.isBlank(builder.getEndDate())) {
                        if (DateUtil.isAfter(dbTable, builder.getEndDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    }
                }
                if (!isAdd) {
                    continue;
                }
                sortTable.add(dbTable);
            }
        }
        if (sortTable.size() > 1) {
            Collections.sort(sortTable, String::compareToIgnoreCase);
        }
        return sortTable;
    }
    
    public List<String> getTableName(QueryBuilder builder) throws SQLException{
    	DBServer dbServer = getDBServer(builder.getPlatformId(), builder.getServerId());
    	 List<String> allLogTable;
         try {
             allLogTable = logCache.get(dbServer);
         } catch (ExecutionException e) {
             logger.error(e.getMessage(), e.getCause());
             throw new SQLException(e.getCause());
         }
    	List<String> sortTable = Lists.newArrayList();
        if (allLogTable == null || allLogTable.isEmpty()) {
            return sortTable;
        }
        // 如果有时间限制, 则只取时间段内的表
        for (String dbTable : allLogTable) {
            if (dbTable.contains(builder.getTableName())) {
                boolean isAdd = true;
                if (!builder.getDbTableClass().getAnnotation(TableDesc.class).equals(LogType.ONLY_ONE)) {
                    if (!StringUtils.isBlank(builder.getStartDate()) && !StringUtils.isBlank(builder.getEndDate())) {
                        if (!DateUtil.isBetween(dbTable, builder.getStartDate(), builder.getEndDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    } else if (!StringUtils.isBlank(builder.getStartDate())) {
                        if (DateUtil.isBefore(dbTable, builder.getStartDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    } else if (!StringUtils.isBlank(builder.getEndDate())) {
                        if (DateUtil.isAfter(dbTable, builder.getEndDate(), builder.getDbTableClass())) {
                            isAdd = false;
                        }
                    }
                }
                if (!isAdd) {
                    continue;
                }
                sortTable.add(dbTable);
            }
        }
        if (sortTable.size() > 1) {
            Collections.sort(sortTable, String::compareToIgnoreCase);
        }
        return sortTable;
    
    }
    
    
    /**
     * 对这个服务器里面所有包含这个表名的表执行同一条sql, 并返回最终结果, 这里是所有的瓶颈所在
     *
     * @param query
     * @param <T>
     * @return
     * @throws SQLException
     */
    public <T> List<T> query(QueryBuilder query) throws SQLException {
        // 表格按时间排序
        List<String> sortTable = getTable(query);
        if (sortTable.isEmpty()) {
            return Collections.emptyList();
        }
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        DBServer dbServer = getDBServer(query.getPlatformId(), query.getServerId());
        QueryRunner runner = new QueryRunner(dbServer.getDataSource());
        int maxCount = 0;
        for (String tName : sortTable) {
            // TODO 这里还要根据数量做分页查询
            String finalSql = MessageFormat.format(query.getSql(), tName);
            logger.info("query:[{}]", finalSql);
            T result = (T) runner.query(finalSql, query.getResultSetHandler());
            if (result != null) {
                builder.add(result);
                if (query.getMaxCount() > 0 && result instanceof Collection) {
                    Collection collection = (Collection) result;
                    if (maxCount + collection.size() >= query.getMaxCount()) {
                        break;
                    }
                    maxCount += collection.size();
                }
                if (query.isSingleLimit()) {
                    break;
                }
            }
        }
        return builder.build();
    }
    
    public <T> T BeanQuery(QueryBuilder query) throws SQLException {
        DBServer dbServer = getDBServer(query.getPlatformId(), query.getServerId());
        QueryRunner runner = new QueryRunner(dbServer.getDataSource());
        String sql=query.getSql();
        logger.info("query:[{}]", sql);
       T result =  (T) runner.query(sql, query.getResultSetHandler());
        
        return result;
    }

    public <T> List<T> toOneList(List<List<T>> data) {
        if (data == null) {
            return Lists.newArrayList();
        }
        return data.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}


