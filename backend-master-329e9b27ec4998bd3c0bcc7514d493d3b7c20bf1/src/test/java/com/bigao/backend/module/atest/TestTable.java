package com.bigao.backend.module.atest;

import java.sql.*;

/**
 * Created by wait on 2015/12/1.
 */
public class TestTable {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.1.50:3306/game_log_cehua";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getColumns(connection.getCatalog(), null, "roleloginoroutlog2016_1", "%");

        StringBuilder builder = new StringBuilder();
        while (rs.next()) {
            String sqlFieldName = rs.getString("COLUMN_NAME");
            String type = rs.getString("TYPE_NAME");
            type = type.toLowerCase();
            if (type.equalsIgnoreCase("BIGINT")) {
                type = "long";
            } else if (type.equalsIgnoreCase("SMALLINT")) {
                type = "int";
            } else if (type.equalsIgnoreCase("VARCHAR")) {
                type = "String";
            } else if (type.equalsIgnoreCase("datetime")) {
                type = "Timestamp";
            }
            builder.append("private ");
            builder.append(type).append(" ").append(sqlFieldName).append(";\n");
        }
        rs.close();
        System.err.println(builder.toString());
    }
}
