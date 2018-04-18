package com.bigao.backend.module.atest;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wait on 2016/3/23.
 */
public class LevelTest {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/game_log1";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement stat = connection.createStatement();

        Map<Long, Integer> maxLevel = new HashMap<>();
        ResultSet rs = stat.executeQuery("select newLevel,roleId from roleleveluplog2016_3 ");
        while (rs.next()) {
            int newLevel = rs.getInt("newLevel");
            long roleId = rs.getLong("roleId");
            maxLevel.merge(roleId, newLevel, Integer::max);
        }
        Map<Integer, Integer> levelNum = new HashMap<>();
        for (Map.Entry<Long, Integer> t : maxLevel.entrySet()) {
            levelNum.merge(t.getValue(), 1, Integer::sum);
        }
        System.err.println(levelNum);
        System.err.println(levelNum.values().stream().mapToInt(Integer::intValue).sum());
    }
}
