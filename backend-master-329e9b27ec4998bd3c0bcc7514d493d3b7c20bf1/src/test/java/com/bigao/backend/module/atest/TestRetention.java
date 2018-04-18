package com.bigao.backend.module.atest;

import com.bigao.backend.log.AccountCreateLog;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.List;

/**
 * Created by wait on 2016/2/10.
 */
public class TestRetention {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/abc";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        String sql = "select id from accountcreatelog2016 where  time >='2016-01-11T00:00' and time <='2016-01-11T23:59:59'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<AccountCreateLog> accountCreateLog = Lists.newArrayList();
        while (resultSet.next()) {
            long roleId = resultSet.getLong("id");
            AccountCreateLog log = new AccountCreateLog();
            log.setId(roleId);
            accountCreateLog.add(log);
        }

        sql = "select DISTINCT(roleId) FROM roleloginoroutlog2016_1 WHERE TIME >='2016-01-13' AND state='LOGIN'";
        resultSet = statement.executeQuery(sql);
        List<Long> tRole = Lists.newArrayList();
        while (resultSet.next()) {
            tRole.add(resultSet.getLong(1));
        }
        int count = 0;
        for (AccountCreateLog log : accountCreateLog) {
            if (tRole.contains(log.getId())) {
                System.err.println(log.getId());
                count++;
            }
        }
        System.err.println(count);
    }
}
