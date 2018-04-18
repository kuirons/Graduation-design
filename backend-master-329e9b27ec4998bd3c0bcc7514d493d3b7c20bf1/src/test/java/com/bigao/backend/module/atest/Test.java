package com.bigao.backend.module.atest;

import com.bigao.backend.module.player.PlayerErrorKey;
import com.bigao.backend.module.player.dto.PlayerLoginDto;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.DateUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * Created by wait on 2015/12/31.
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.err.println(URLDecoder.decode("%E6%B5%8B%E8%AF%95%E9%82%AE%E4%BB%B6", "utf-8"));
        System.err.println(new Date(1451597291000L));
        System.err.println(DateUtil.toLocalDateTimeByTime("2015-12-31 21:33:00").atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L);
        System.err.println(new Date(1451568780000L));

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime nowDateTime = ZonedDateTime.now();
        System.err.println(now.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        System.err.println(now.atOffset(ZoneOffset.of("+8")).toInstant().toEpochMilli());
        System.err.println(nowDateTime.toEpochSecond() * 1000);
        System.err.println(now.getLong(ChronoField.MILLI_OF_SECOND));
        System.err.println(System.currentTimeMillis());

        System.err.println(CommonUtils.alterMessage(PlayerLoginDto.class, PlayerErrorKey.EMPTY_PLAYER_NAME));
    }
}
