package com.bigao.backend.module.atest;

import com.bigao.backend.util.MD5Util;
import com.bigao.backend.util.SystemConfig;

/**
 * Created by wait on 2015/12/29.
 */
public class GemPass {
    public static void main(String[] args) throws Exception {
        System.err.println(MD5Util.getMD5String("ztovkipo!2" + SystemConfig.getProperty("system.security")));
        System.err.println(System.currentTimeMillis());
    }
}
