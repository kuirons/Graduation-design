package com.bigao.backend.module.atest;

import com.bigao.backend.util.HttpClientUtil;
import com.bigao.backend.util.NVPUtil;
import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by wait on 2016/1/4.
 */
public class TestMail {
    public static void main(String[] args) throws Exception {
        List<NameValuePair> nameValuePair = Lists.newArrayList();
        nameValuePair.add(NVPUtil.build("content", "测试邮件"));
        nameValuePair.add(NVPUtil.build("reward", "-2,30000"));
        String resultString = HttpClientUtil.get("http://127.0.0.1:8002/gmMail", nameValuePair);
        System.err.println(resultString);
    }
}
