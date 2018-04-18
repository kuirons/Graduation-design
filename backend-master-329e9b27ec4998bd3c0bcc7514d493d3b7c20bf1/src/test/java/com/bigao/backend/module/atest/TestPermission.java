package com.bigao.backend.module.atest;

import java.sql.*;

/**
 * Created by wait on 2015/12/31.
 */
public class TestPermission {

    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/xiyou";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        String abc = "insert into gccp_permission(createTime,parentId,permissionDesc,permissionName,permissionURL) VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(abc);
        ps.setLong(1, System.currentTimeMillis());
        ps.setInt(2, 1);
        ps.setString(3, "运维GM操作");
        ps.setString(4, "OPERATE_COMMAND_MANAGE");
        ps.setString(5, "/operate");
        ps.execute();
    }
}
