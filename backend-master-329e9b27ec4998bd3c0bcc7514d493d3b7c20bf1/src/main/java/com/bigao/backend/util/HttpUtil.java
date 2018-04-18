package com.bigao.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wait on 2015/12/15.
 */
public class HttpUtil {
    public static String RES_URL = "http://123.56.129.155:10000/res";

    public static InputStream get50InputStream(String beanName) throws IOException {
        URL url = new URL(RES_URL + "/" + beanName);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }

    public static InputStream getInputStream(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }
}
