package com.bigao.backend.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;


public class SystemConfig {

    private static Properties properties = new Properties();
    private static Properties message = new Properties();

    private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    static {
        initProperties();
    }

    private static void initProperties() {
        InputStream appConfigFileStream = null;
        InputStream errFileStream = null;
        try {
            // 全局配置文件
            String applicationConfigFileName = "applicationConfig.properties";
            appConfigFileStream = SystemConfig.class.getClassLoader().getResourceAsStream(applicationConfigFileName);
            properties.load(appConfigFileStream);

            // 加载语言文件
            errFileStream = SystemConfig.class.getClassLoader().getResourceAsStream("error_message.properties");
            message.load(errFileStream);

            logger.info("载入系统配置文件成功！");
        } catch (Exception e) {
            logger.error("载入系统配置文件错误：", e);
        } finally {
            try {
                if (appConfigFileStream != null) {
                    appConfigFileStream.close();
                }
                if (errFileStream != null) {
                    errFileStream.close();
                }
            } catch (Exception e2) {
                logger.error("关闭流错误：", e2);
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getLang(String key) {
        return message.getProperty(key, "empty");
    }
}
