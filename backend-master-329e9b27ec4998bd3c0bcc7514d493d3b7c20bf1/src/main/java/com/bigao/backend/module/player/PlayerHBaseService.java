package com.bigao.backend.module.player;

import com.bigao.backend.module.player.dto.PlayerInfo;
import com.bigao.backend.module.player.dto.PlayerLoginInfo;
import com.bigao.backend.module.player.dto.RoleLevelDto;
import com.bigao.backend.module.player.dto.RoleLevelInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.Time;
import com.google.common.collect.ImmutableList;
import hbase.util.HBaseUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PlayerHBaseService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerHBaseService.class);
    private static final String TABLE_NAME = "backtable";
    //这张表中存放经常使用且会被大量重复的数据，在这张表中存放数据需要仔细考虑，这张表的数据量决定了系统的查询效率
    private static final String USERINFO_TABLE = "backtabltuserinfo";

    /**
     * 获取玩家信息（支持模糊搜索，用户名或者角色名包含关键字即符合结果）
     *
     * @param server 服务器，包括平台和服务器（2,2）
     * @param param  查询的玩家名称
     * @return
     */
    public List<PlayerInfo> getRoleByLikeName(String server, String param) {
        List<Filter> list = new ArrayList<>();
        //此时这个过滤器是没有用的，但是不排除之后会有用
//        Filter rowFilterO = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("&01&"));
//        list.add(rowFilterO);
        //行过滤器，用户名或者账号中包含关键字都会返回结果
        Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(param));
        list.add(rowFilter);
        //列过滤器，只查询相应服务器
        Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(server.getBytes()));
        list.add(valueFilter);
        //只需要返回行健
        Filter keyOnlyFilter = new KeyOnlyFilter();
        list.add(keyOnlyFilter);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, list);
        ResultScanner results = HBaseUtil.getScan("backtabltuserinfo", null, filterList);
        Result result = null;
        ImmutableList.Builder<PlayerInfo> playerInfoBuilder = ImmutableList.builder();
        try {
            while ((result = results.next()) != null) {
                String r = Bytes.toString(result.getRow());
                PlayerInfo playerInfo = new PlayerInfo();
//                playerInfo.setRoleId(String.valueOf(log.getRoleId()));
                playerInfo.setUserName(r.split("&")[1].replace("#", ""));
                playerInfo.setRoleName(r.split("&")[0].replace("#", ""));
                playerInfoBuilder.add(playerInfo);
            }
        } catch (IOException e) {
            logger.error("模糊查询失败");
        } finally {
            results.close();
        }

        return playerInfoBuilder.build();
    }

    /**
     * 查询升级日志
     *
     * @param startDate
     * @param endDate
     * @param roleParam
     * @param server
     * @return
     */
    public RoleLevelDto getRoleLevelDto(String startDate, String endDate, String roleParam, String server) {
        //获取玩家信息，用来组建起始行健和结束行健
        PlayerInfo playerInfo = getPlayerInfo(roleParam, server);
        if (playerInfo == null) {
            logger.error("查询玩家:{}不存在", roleParam);
            return CommonUtils.alterMessage(RoleLevelDto.class, CommonErrorKey.ROLE_NOT_EXIST);
        }

        //排除同一账号在不同服建立相同名字角色的情况
        SingleColumnValueExcludeFilter singleColumnValueExcludeFilter = new SingleColumnValueExcludeFilter("backinfo".getBytes(), "server".getBytes(), CompareFilter.CompareOp.EQUAL, new BinaryComparator(server.getBytes()));
        singleColumnValueExcludeFilter.setFilterIfMissing(true);

        //起始键
        List<String> startRow = new ArrayList<>();
        startRow.add(getRowKey(startDate, playerInfo, "02"));

        //终止键
        List<String> stopRow = new ArrayList<>();
        stopRow.add(getRowKey(endDate, playerInfo, "02"));
        HashMap<String, List<String>> paramMap = new HashMap<>();
        paramMap.put("startRow", startRow);
        paramMap.put("stopRow", stopRow);

        ResultScanner results = HBaseUtil.getScan(TABLE_NAME, paramMap, singleColumnValueExcludeFilter);
        Result result = null;
        List<RoleLevelInfo> resultList = new ArrayList<>();
        try {
            while ((result = results.next()) != null) {
                RoleLevelInfo roleLevelInfo = new RoleLevelInfo();
                String roleInfo = Bytes.toString(result.getRow());
                String levelInfo = Bytes.toString(result.getValue("backinfo".getBytes(), "levelup".getBytes()));
                roleLevelInfo.setCreateTime(Time.changeTimeToString(roleInfo.split("&")[3]));
                roleLevelInfo.setLevel(Integer.parseInt(levelInfo.split(",")[1]));
                resultList.add(roleLevelInfo);
//                if(flag){
//                    userName = roleInfo.split("&")[1].replace("#","");
//                    roleName = roleInfo.split("&")[0].replace("#","");
//                    flag=false;
//                }
            }
            RoleLevelDto roleLevelDto = RoleLevelDto.valueOf(resultList, 0L, playerInfo.getUserName(), playerInfo.getRoleName());
            return roleLevelDto;
        } catch (IOException e) {
            logger.error("查询玩家:{}等级失败", roleParam, e);
        } finally {
            results.close();
        }
        return null;
    }

    /**
     * 查询登录信息
     *
     * @param startTimeInit
     * @param endTime
     * @param server
     * @param account
     * @return
     */
    public List<PlayerLoginInfo> queryPlayerLoginLog(String startTimeInit, String endTime, String server, String account) {
        //获取玩家信息，用来组建起始行健和结束行健
        PlayerInfo playerInfo = getPlayerInfo(account, server);
        if (playerInfo == null) {
            logger.error("查询玩家:{}不存在", account);
            return null;
        }
        //排除同一账号在不同服建立相同名字角色的情况
        SingleColumnValueExcludeFilter singleColumnValueExcludeFilter = new SingleColumnValueExcludeFilter("backinfo".getBytes(), "server".getBytes(), CompareFilter.CompareOp.EQUAL, new BinaryComparator(server.getBytes()));
        singleColumnValueExcludeFilter.setFilterIfMissing(true);

        //起始键
        List<String> startRow = new ArrayList<>();
        startRow.add(getRowKey(startTimeInit, playerInfo, "03"));

        //终止键
        List<String> stopRow = new ArrayList<>();
        stopRow.add(getRowKey(endTime, playerInfo, "03"));
        HashMap<String, List<String>> paramMap = new HashMap<>();
        paramMap.put("startRow", startRow);
        paramMap.put("stopRow", stopRow);

        ResultScanner results = HBaseUtil.getScan(TABLE_NAME, paramMap, singleColumnValueExcludeFilter);
        List<PlayerLoginInfo> playerLoginInfos = new ArrayList<>();
        Result result = null;
        try {
            while ((result = results.next()) != null) {
                PlayerLoginInfo playerLoginInfo = new PlayerLoginInfo();
                playerLoginInfo.setIp(Bytes.toString(result.getValue("backinfo".getBytes(), "logio".getBytes())).split("-")[1]);
                playerLoginInfo.setState(Bytes.toString(result.getValue("backinfo".getBytes(), "logio".getBytes())).split("-")[0]);
                playerLoginInfo.setTime(Time.changeTimeToString(Bytes.toString(result.getRow()).split("&")[3]));
                playerLoginInfos.add(playerLoginInfo);
            }
            return playerLoginInfos;
        } catch (IOException e) {
            logger.error("查询玩家{}登录信息失败", account, e);
        } finally {
            results.close();
        }
        return null;
    }

    /**
     * 根据提供的参数构建规范的行健
     *
     * @param date       行健的日期信息
     * @param playerInfo
     * @param operation  操作代码
     * @return
     */
    private String getRowKey(String date, PlayerInfo playerInfo, String operation) {
        String rowKey = "";
        rowKey += playerInfo.getRoleName();
        for (int i = playerInfo.getRoleName().getBytes().length; i < 15; i++) {
            rowKey += "#";
        }
        rowKey = rowKey + "&" + playerInfo.getUserName();
        for (int i = playerInfo.getUserName().getBytes().length; i < 15; i++) {
            rowKey += "#";
        }
        rowKey = rowKey + "&" + operation + "&" + Time.changeTimeToLong(date);
        return rowKey;
    }

    /**
     * 获取玩家信息，必须精确匹配
     *
     * @param userName
     * @param server
     * @return
     */
    public PlayerInfo getPlayerInfo(String userName, String server) {
        //将角色名转换为标准前缀，可以同时提供账号进行查找，但是这会造成很多代码的修改，如果需要在进行修改
        for (int i = userName.getBytes().length; i < 15; i++) {
            userName += "#";
        }
        PlayerInfo playerInfo = new PlayerInfo();
        //过滤器及过滤链
        Filter rowFilter = new PrefixFilter(userName.getBytes());
        Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(server.getBytes()));
        Filter keyOnlyFilter = new KeyOnlyFilter();

        List<Filter> filters = new ArrayList<>();
        filters.add(rowFilter);
        filters.add(valueFilter);
        filters.add(keyOnlyFilter);

        Filter filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

        ResultScanner results = HBaseUtil.getScan(USERINFO_TABLE, null, filterList);
        Result result = null;
        try {
            if ((result = results.next()) != null) {
                playerInfo.setRoleName(Bytes.toString(result.getRow()).split("&")[0].replace("#", ""));
                playerInfo.setUserName(Bytes.toString(result.getRow()).split("&")[1].replace("#", ""));
            } else
                return null;
        } catch (IOException e) {
            logger.error("获取玩家{}信息失败", userName, e);
        } finally {
            results.close();
        }
        return playerInfo;
    }
}
