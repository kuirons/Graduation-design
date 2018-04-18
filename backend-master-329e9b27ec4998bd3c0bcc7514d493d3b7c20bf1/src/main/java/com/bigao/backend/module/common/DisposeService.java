package com.bigao.backend.module.common;

import ch.qos.logback.classic.LoggerContext;

import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 关闭异步的SMTPAppender, 关闭inner里面注册的DriverClass
 * Created by wait on 2016/4/1.
 */
@Service
public class DisposeService implements DisposableBean {
	@Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
	
    @Override
    public void destroy() throws Exception {
        LoggerFactory.getLogger(DisposeService.class).info("close done.");
        try {
        	for (DBServer dbServer : dbUtil4ServerLog.getDbServerMap().values()) {
        		dbServer.getDataSource().close();
         	}
        	System.out.println("释放连接池成功！");
		} catch (Exception e) {
			System.out.printf("SEVERE problem cleaning up: [%s]\n", e.getMessage());
            e.printStackTrace();
		}
        try {
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
        } catch (Exception e) {
            System.out.printf("unregister driver problem : [%s]\n", e.getMessage());
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
            System.out.println("AbandonedConnectionCleanupThread shutdown");

            ((LoggerContext) LoggerFactory.getILoggerFactory()).stop();
        } catch (InterruptedException e) {
            System.out.printf("SEVERE problem cleaning up: [%s]\n", e.getMessage());
            e.printStackTrace();
        }
    }
}
