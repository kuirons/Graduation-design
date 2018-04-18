package com.bigao.backend.module.atest;

import com.google.common.collect.Lists;

import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by wait on 2015/12/29.
 */
public class RolePerm {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/xiyou";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("select * from gccp_role_permission where roleId = 2");
        List<Integer> per = Lists.newArrayList();
        while (rs.next()) {
            per.add(rs.getInt("permissionId"));
        }
        Collections.sort(per);
        System.err.println(per);
        for (int pId : per) {
            stat.execute("insert into gccp_role_permission(`permissionId`, `roleId`) VALUES (" + pId + ", 3)");
            Thread.sleep(100L);
        }
        for (int pId : per) {
            stat.execute("insert into gccp_role_permission(`permissionId`, `roleId`) VALUES (" + pId + ", 4)");
            Thread.sleep(100L);
        }
    }
}
