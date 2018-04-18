package com.bigao.backend.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * HttpClient工具类
 *
 * @author yuanhai
 */
public class HttpClientUtil {

    private static final int CONNECTION_TIMEOUT = 5 * 1000;

    private static final int REQUEST_TIMEOUT = 10 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // httpGet连接
    public static String get(String url, List<NameValuePair> nvps) throws Exception {
        StringBuilder responseString = new StringBuilder();
        HttpClient httpclient = HttpClientBuilder.create().build();
        String connectUrl = buildGetUrl(url, nvps);
        logger.info("http get request:" + connectUrl);
        HttpGet httpGet = new HttpGet(connectUrl);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
        httpGet.setConfig(requestConfig);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        if (entity != null && statusCode == 200) {
            responseString.append(EntityUtils.toString(entity, "utf-8"));
        } else {
            logger.error("httpclient get connect error! status code: " + statusCode + " | url:" + connectUrl);
        }
        HttpClientUtils.closeQuietly(httpclient);
        return responseString.toString();
    }

    // httpPost连接
    public static String post(String url, List<NameValuePair> nvps) throws Exception {
        StringBuilder responseString = new StringBuilder();
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        if (nvps == null) {
            nvps = new ArrayList<NameValuePair>();
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        logger.info("http post request:" + httpPost.getURI().toString());
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        if (entity != null && statusCode == 200) {
            responseString.append(EntityUtils.toString(entity));
        } else {
            logger.error("httpclient post connect error! status code: " + statusCode + " " + httpPost.getURI().toString());
        }
        HttpClientUtils.closeQuietly(httpclient);
        //logger.info("http response:" + responseString.toString());

        return responseString.toString();
    }

    // 构造get方法参数//没有URL转码
    public static String buildHttpGetParams(List<NameValuePair> params) {
        String paramStr = null;
        StringBuffer paramStrBuff = new StringBuffer();
        if (params.size() > 0) {
            paramStrBuff.append("?");
            for (NameValuePair nvp : params) {
                paramStrBuff.append(nvp.toString()).append("&");
            }
            String str = paramStrBuff.toString();
            paramStr = str.substring(0, str.length() - 1);
        }

        return paramStr;
    }

    // 构造get请求URL
    public static String buildGetUrl(String url, List<NameValuePair> nvps) {
        StringBuffer connectUrl = new StringBuffer(url);
        if (nvps != null) {
            if (url.indexOf("?") == -1) {
                if (nvps.size() != 0 && nvps.size() > 0) {
                    connectUrl.append("?");
                    connectUrl.append(URLEncodedUtils.format(nvps, "UTF-8"));
                }
            } else {
                if (nvps.size() != 0 && nvps.size() > 0) {
                    connectUrl.append("&");
                    connectUrl.append(URLEncodedUtils.format(nvps, "UTF-8"));
                }
            }
        }

        return connectUrl.toString();
    }


}
