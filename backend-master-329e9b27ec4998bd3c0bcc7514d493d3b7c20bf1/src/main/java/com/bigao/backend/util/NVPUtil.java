package com.bigao.backend.util;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wait on 2016/1/18.
 */
public class NVPUtil {
    public static List<NameValuePair> EMPTY_PAIR = Lists.newArrayList();

    static {
        if (EMPTY_PAIR.size() == 0) {
            EMPTY_PAIR.add(build("param", "param"));
        }
    }

    public static BasicNameValuePair build(String name, Serializable value) {
        return new BasicNameValuePair(name, String.valueOf(value));
    }
}
