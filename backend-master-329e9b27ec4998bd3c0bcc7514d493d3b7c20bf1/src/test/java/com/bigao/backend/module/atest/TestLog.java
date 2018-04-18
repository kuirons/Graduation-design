package com.bigao.backend.module.atest;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wait on 2016/3/25.
 */
public class TestLog {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://121.201.59.100:22306/game_log1";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("select DISTINCT roleId from roleloginoroutlog2016_3 WHERE `time` BETWEEN '2016-03-24' AND '2016-03-24T23:59:59' AND state ='LOGIN'");
        Set<Long> dayLogin = new HashSet<>();
        while (rs.next()) {
            dayLogin.add(rs.getLong("roleId"));
        }
        System.err.println(dayLogin.size() + ":" + dayLogin);
        rs.close();
        rs = stat.executeQuery("select DISTINCT roleId from rolecreatelog2016 WHERE `time` BETWEEN '2016-03-23' AND '2016-03-23T23:59:59'");
        Set<Long> register = new HashSet<>();
        while (rs.next()) {
            register.add(rs.getLong("roleId"));
        }

        int dayLoginNum = 0;
        for (long loginId : dayLogin) {
            if (register.contains(loginId)) {
                dayLoginNum++;
            }
        }
        System.err.println(dayLoginNum + "/" + register.size() + "=" + ((float) dayLoginNum / register.size()));

        rs.close();
    }
}
