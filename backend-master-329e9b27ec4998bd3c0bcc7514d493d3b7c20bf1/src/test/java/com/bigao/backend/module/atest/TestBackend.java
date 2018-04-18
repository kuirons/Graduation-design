package com.bigao.backend.module.atest;

import com.bigao.backend.util.HttpClientUtil;
import com.bigao.backend.util.NVPUtil;

/**
 * Created by wait on 2015/12/13.
 */
public class TestBackend {

    public static void main(String[] args) throws Exception {
        String resultString = HttpClientUtil.get("http://192.168.1.52:8002/reload", NVPUtil.EMPTY_PAIR);
        System.err.println(resultString);
    }
}
