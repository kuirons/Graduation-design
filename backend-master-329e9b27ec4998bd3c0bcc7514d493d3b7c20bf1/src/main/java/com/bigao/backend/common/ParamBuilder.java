package com.bigao.backend.common;

import com.bigao.backend.util.SqlUtil;

import java.io.Serializable;

/**
 * Created by wait on 2015/11/30.
 */
public class ParamBuilder {
    private StringBuilder paramStringBuilder;

    public static ParamBuilder newInstance() {
        ParamBuilder paramBuilder = new ParamBuilder();
        paramBuilder.paramStringBuilder = new StringBuilder();
        return paramBuilder;
    }

    public ParamBuilder select() {
        paramStringBuilder.append(SqlUtil.SELECT_TABLE);
        return this;
    }

    public ParamBuilder add(String key, Serializable value) {
        paramStringBuilder.append(key).append("=").append(value).append("&&");
        return this;
    }

    public ParamBuilder where() {
        paramStringBuilder.append(" where ");
        return this;
    }

    /**
     * 这个方法在最后才能调用
     *
     * @param start
     * @return
     */
    public ParamBuilder limit(int start) {
        trim();
        paramStringBuilder.append(" limit ").append(start);
        return this;
    }

    /**
     * 这个方法在最后才能调用
     *
     * @param start
     * @param end
     * @return
     */
    public ParamBuilder limit(int start, int end) {
        trim();
        paramStringBuilder.append(" limit ").append(start).append(", ").append(end);
        return this;
    }

    public ParamBuilder asc(String column) {
        trim();
        paramStringBuilder.append(" order by ").append(column).append(" asc ");
        return this;
    }

    public ParamBuilder desc(String column) {
        trim();
        paramStringBuilder.append(" order by ").append(column).append(" desc ");
        return this;
    }

    public String build() {
        return toString();
    }

    private void trim() {
        if (paramStringBuilder.length() > 0 && paramStringBuilder.toString().endsWith("&&")) {
            paramStringBuilder.delete(paramStringBuilder.length() - 2, paramStringBuilder.length());
        }
    }

    public ParamBuilder clear() {
        paramStringBuilder.delete(0, paramStringBuilder.length());
        return this;
    }

    @Override
    public String toString() {
        if (paramStringBuilder.length() > 0 && paramStringBuilder.toString().endsWith("&&")) {
            return paramStringBuilder.substring(0, paramStringBuilder.length() - 2);
        }
        return paramStringBuilder.toString();
    }

    public static void main(String[] args) {
        ParamBuilder builder = ParamBuilder.newInstance().where().add("roleId", 8383838).asc("time").limit(1, 20);
        System.err.println(builder.toString());
    }
}
