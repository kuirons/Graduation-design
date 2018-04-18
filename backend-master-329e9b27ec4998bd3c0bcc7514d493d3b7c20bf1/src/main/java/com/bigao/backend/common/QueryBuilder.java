package com.bigao.backend.common;

import com.bigao.backend.common.anno.TableDesc;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wait on 2015/11/30.
 */
public class QueryBuilder {
    public static QueryBuilder newInstance() {
        return new QueryBuilder();
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class tableClass, String startDate, String endDate, int maxCount, ResultSetHandler resultSetHandler) {
        QueryBuilder builder = newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(tableClass).setSql(sql).setMaxCount(maxCount).setResultSetHandler(resultSetHandler);
        builder.setStartDate(startDate).setEndDate(endDate);
        return builder;
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class tableClass, String startDate, String endDate, ResultSetHandler resultSetHandler) {
        QueryBuilder builder = newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(tableClass).setSql(sql).setResultSetHandler(resultSetHandler);
        builder.setStartDate(startDate).setEndDate(endDate);
        return builder;
    }
    
    public static QueryBuilder build(int platformId, int serverId, Class tableClass, String startDate, String endDate, ResultSetHandler resultSetHandler) {
        QueryBuilder builder = newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(tableClass).setResultSetHandler(resultSetHandler);
        builder.setStartDate(startDate).setEndDate(endDate);
        return builder;
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class resultClass, String startDate, String endDate) {
        QueryBuilder builder = newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(resultClass).setSql(sql).setResultSetHandler(new BeanHandler(resultClass));
        builder.setStartDate(startDate).setEndDate(endDate);
        return builder;
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class resultClass, ResultSetHandler resultSetHandler) {
        return newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(resultClass).setSql(sql).setResultSetHandler(resultSetHandler);
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class resultClass) {
        return newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(resultClass).setSql(sql).setResultSetHandler(new BeanHandler(resultClass));
    }

    public static QueryBuilder build(int platformId, int serverId, String sql, Class resultClass, boolean isSingleLimit) {
        return newInstance().setPlatformId(platformId).setServerId(serverId).setDbTableClass(resultClass).setSql(sql).setResultSetHandler(new BeanHandler(resultClass)).setSingleLimit(isSingleLimit);
    }

    // ====================logic method=====================
    public String getTableName() {
        if (dbTableClass.getAnnotation(TableDesc.class) == null) {
            throw new RuntimeException(dbTableClass.getName() + "需要有@TableName注解");
        }
        TableDesc tableDesc = (TableDesc) dbTableClass.getAnnotation(TableDesc.class);
        if (StringUtils.isBlank(tableDesc.value())) {
            throw new RuntimeException(dbTableClass.getName() + "上的@TableName注解里面的value不能为空");
        }
        return tableDesc.value();
    }


    // ================field=====================
    private int platformId;
    private int serverId;
    /** 当sql语句包含单引号时, 需要用两个单引号来让MessageFormat进行转义 */
    private String sql;
    private ResultSetHandler resultSetHandler;
    /** 形如"2015-12-05" */
    private String startDate;
    /** 形如"2015-12-06" */
    private String endDate;
    /** 映射到数据库表的java类 */
    private Class dbTableClass;
    /** 是否属于找到一条数据就不再往下查询 */
    private boolean isSingleLimit;
    /** 最大结果数[当招够这么多, 不再往下找] */
    private int maxCount;

    // ================getter and setter=================
    public int getPlatformId() {
        return platformId;
    }

    public QueryBuilder setPlatformId(int platformId) {
        this.platformId = platformId;
        return this;
    }

    public int getServerId() {
        return serverId;
    }

    public QueryBuilder setServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getSql() {
        return sql;
    }

    public QueryBuilder setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public ResultSetHandler getResultSetHandler() {
        return resultSetHandler;
    }

    public QueryBuilder setResultSetHandler(ResultSetHandler resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public QueryBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public QueryBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Class getDbTableClass() {
        return dbTableClass;
    }

    public QueryBuilder setDbTableClass(Class dbTableClass) {
        this.dbTableClass = dbTableClass;
        return this;
    }

    public boolean isSingleLimit() {
        return isSingleLimit;
    }

    public QueryBuilder setSingleLimit(boolean singleLimit) {
        isSingleLimit = singleLimit;
        return this;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public QueryBuilder setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }
}
