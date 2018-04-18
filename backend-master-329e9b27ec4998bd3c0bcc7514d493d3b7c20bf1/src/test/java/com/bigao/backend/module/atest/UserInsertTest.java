package com.bigao.backend.module.atest;

import com.bigao.backend.util.MD5Util;
import com.bigao.backend.util.SystemConfig;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wait on 2016/4/6.
 */
public class UserInsertTest {

    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/xiyou";

    static final String USER = "game";
    static final String PASS = "game";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        Class.forName("com.mysql.jdbc.Driver");
        // 创建5个用户
        List<XiyouUser> allUser = new ArrayList<>();
        // 程序
        XiyouUser user1 = XiyouUser.valueOf("xiyou", MD5Util.getMD5String("XiYou.123" + SystemConfig.getProperty("system.security")));
        user1.getAuth().addAll(Arrays.asList(2, 3, 4, 53, 6, 7, 8, 9, 10, 14, 34, 55, 56, 57, 44, 48));

        // 运维
        XiyouUser user2 = XiyouUser.valueOf("xiyou2", MD5Util.getMD5String("XiYou2.123" + SystemConfig.getProperty("system.security")));
        user2.getAuth().addAll(Arrays.asList(44, 58, 3, 54));

        // 运营方
        XiyouUser user3 = XiyouUser.valueOf("xiyou3", MD5Util.getMD5String("XiYou3.123" + SystemConfig.getProperty("system.security")));
        user3.getAuth().addAll(Arrays.asList(3, 4, 53, 6, 7, 8, 9, 10, 14, 34));

        // 策划管理
        XiyouUser user4 = XiyouUser.valueOf("xiyou4", MD5Util.getMD5String("XiYou4.123" + SystemConfig.getProperty("system.security")));
        user4.getAuth().addAll(Arrays.asList(3, 4, 53, 6, 7, 8, 9, 10, 14, 34, 55, 56));

        allUser.add(user1);
        allUser.add(user2);
        allUser.add(user3);
        allUser.add(user4);

        QueryRunner runner = new QueryRunner();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        int roleId = 5;
        for (XiyouUser user : allUser) {
            runner.update(connection, "insert into gccp_user(deleted,createTime,password,roleId,username) values(0,now(), '" + user.getPass() + "', " + roleId + ",'" + user.getUserName() + "')");
            for (int pId : user.getAuth()) {
                runner.update(connection, "insert into gccp_role_permission(`permissionId`, `roleId`) VALUES (" + pId + ", " + roleId + ")");
                Thread.sleep(100L);
            }
            roleId++;
        }

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.printf("unregister jdbc driver: [%s]\n", driver);
            } catch (SQLException e) {
                System.out.printf("Error unregister driver: [%s]\n ", driver);
            }
        }

        try {
            AbandonedConnectionCleanupThread.shutdown();
            System.out.println("AbandonedConnectionCleanupThread shutdown");
        } catch (InterruptedException e) {
            System.out.printf("SEVERE problem cleaning up: [%s]\n", e.getMessage());
            e.printStackTrace();
        }
    }
}
