package com.bigao.backend.util;

import com.bigao.backend.common.PlatformServer;
import com.bigao.backend.common.ServerParse;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dell on 2015/11/26.
 */
public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * @param serverData platform,server;platform,server....
     * @return
     */
    public static Map<Integer, List<Integer>> parseToServer(String serverData) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        String[] allServer = StringUtils.split(serverData, ";");
        for (String one : allServer) {
            String[] t = StringUtils.split(one, ",");
            int platform = Integer.parseInt(t[0]);
            int server = Integer.parseInt(t[1]);

            if (!result.containsKey(platform)) {
                result.put(platform, Lists.newArrayList());
            }
            if (!result.get(platform).contains(server)) {
                result.get(platform).add(server);
            }
        }
        return result;
    }

    public static Map<Integer, PlatformServer> toPlatformServer(String urlData) {
        Map<Integer, List<Integer>> end = parseToServer(urlData);
        if (end == null || end.isEmpty()) {
            return null;
        }

        Map<Integer, PlatformServer> result = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : end.entrySet()) {
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), PlatformServer.valueOf(entry.getKey()));
            }
            for (int server : entry.getValue()) {
                result.get(entry.getKey()).getServer().add(server);
            }
        }
        return result;
    }

    public static ServerParse validateServer(String server) {
        Map<Integer, List<Integer>> tServer = CommonUtils.parseToServer(server);
        if (tServer.isEmpty()) {
            return ServerParse.err(CommonErrorKey.EMPTY_SERVER);
        }
        return ServerParse.succ(tServer);
    }

    public static ServerParse validateOneServer(String server) {
        Map<Integer, List<Integer>> tServer = CommonUtils.parseToServer(server);
        if (tServer.isEmpty()) {
            return ServerParse.err(CommonErrorKey.EMPTY_SERVER);
        }
        int size = 0;
        for (Map.Entry<Integer, List<Integer>> e : tServer.entrySet()) {
            size += e.getValue().size();
        }
        if (size > 1) {
            return ServerParse.err(CommonErrorKey.ONE_SERVER_LIMIT);
        }
        return ServerParse.succ(tServer);
    }

    public static ServerParse validateDateAndOneServer(String startDate, String endDate, String server) {
        ServerParse parse = validateDateAndServer(startDate, endDate, server);
        if (parse.isFail()) {
            return parse;
        }
        if (parse.getServer().size() > 1) {
            return ServerParse.err(CommonErrorKey.ONE_SERVER_LIMIT);
        }
        if (parse.getServer().entrySet().iterator().next().getValue().size() > 1) {
            return ServerParse.err(CommonErrorKey.ONE_SERVER_LIMIT);
        }
        return parse;
    }

    public static ServerParse validateDateAndServer(String startDate, String endDate, String server) {
        LocalDate start = DateUtil.toLocalDateByDate(startDate);
        LocalDate end = DateUtil.toLocalDateByDate(endDate);
        if (start.isAfter(end)) {
            return ServerParse.err(CommonErrorKey.DATE_BEFORE);
        }
        if (StringUtils.isBlank(server)) {
            return ServerParse.err(CommonErrorKey.EMPTY_SERVER);
        }
        Map<Integer, List<Integer>> tServer = CommonUtils.parseToServer(server);
        if (tServer.isEmpty()) {
            return ServerParse.err(CommonErrorKey.SEVER_NOT_EXIST);
        }
        return ServerParse.succ(tServer);
    }

    /** 产生一个随机的字符串 */
    public static String randString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static <T> T alterMessage(Class<T> clz, String messageKey) {
        try {
            Class<?> forClass = Class.forName(clz.getCanonicalName());
            Object obj = forClass.newInstance();
            BeanUtils.setProperty(obj, "message", SystemConfig.getLang(messageKey));
            return (T) obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            return md5(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(byte[] b) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] bytes = md5.digest(b);
        StringBuilder ret = new StringBuilder(bytes.length << 1);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
            ret.append(Character.forDigit(bytes[i] & 0xf, 16));
        }
        return ret.toString();
    }


    public static String base64Encode(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        return new String(Base64.encodeBase64(input.getBytes(Charsets.UTF_8)));
    }

    public static String base64Decode(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }
        return new String(Base64.decodeBase64(input), Charsets.UTF_8);
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
