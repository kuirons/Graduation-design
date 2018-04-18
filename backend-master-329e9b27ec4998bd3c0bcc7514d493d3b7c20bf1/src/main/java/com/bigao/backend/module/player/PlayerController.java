package com.bigao.backend.module.player;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.log.RoleBagLog;
import com.bigao.backend.log.RoleLevelUpLog;
import com.bigao.backend.log.RoleMonetaryLog;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.player.dto.*;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "playerQuery")
public class PlayerController {

    private Logger logger = LoggerFactory.getLogger(PlayerController.class);
    @Autowired
    private ServerListService serverListService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerHBaseService playerHBaseService;

    @RequestMapping(value = "playerQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("player/playerQuery");
        return view;
    }

    @RequestMapping(value = "playerInfoQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PlayerInfoDto playerInfoQuery(HttpSession session,
                                         HttpServletRequest request,
                                         @RequestParam(value = "playerName") String playerName,
                                         @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(playerName)) {
            return CommonUtils.alterMessage(PlayerInfoDto.class, PlayerErrorKey.EMPTY_PLAYER_NAME);
        }
        try {
            List<PlayerInfo> playerInfo = playerHBaseService.getRoleByLikeName(server, playerName);
            PlayerInfoDto dto = new PlayerInfoDto();
            dto.setPlayerInfo(playerInfo);

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{playerName, server});

            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(PlayerInfoDto.class, CommonErrorKey.UNKNOWN);
        }
    }


    @RequestMapping(value = "playerLevelLogQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RoleLevelDto playerLogQuery(HttpSession session,
                                       HttpServletRequest request,
                                       @RequestParam(value = "start") String startDate,
                                       @RequestParam(value = "end") String endDate,
                                       @RequestParam(value = "content") String roleParam,
                                       @RequestParam(value = "way") int way,
                                       @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(RoleLevelDto.class, CommonErrorKey.EMPTY_DATE);
        }
        if (way < 0 || way > 1) {
            return CommonUtils.alterMessage(RoleLevelDto.class, CommonErrorKey.ERROR_PARAM);
        }
        if (StringUtils.isBlank(roleParam)) {
            return CommonUtils.alterMessage(RoleLevelDto.class, CommonErrorKey.EMPTY_ROLE_PARAM);
        }
        RoleLevelDto roleLevelDto = playerHBaseService.getRoleLevelDto(startDate, endDate, roleParam, server);
        if (roleLevelDto == null) {
            logger.error("查询玩家{}等级失败", roleParam);
            return CommonUtils.alterMessage(RoleLevelDto.class, CommonErrorKey.UNKNOWN);
        } else {
            return roleLevelDto;
        }
    }

    //此函数后面要添加检测用户是否存在的过程
    @RequestMapping(value = "playerLoginQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PlayerLoginDto playerLoginQuery(HttpSession session,
                                           HttpServletRequest request,
                                           @RequestParam(value = "startTimeInit") String startTimeInit,
                                           @RequestParam(value = "endTime") String endTime,
                                           @RequestParam(value = "account") String account,
                                           @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(account)) {
            return CommonUtils.alterMessage(PlayerLoginDto.class, PlayerErrorKey.EMPTY_PLAYER_NAME);
        }
        try {
            List<PlayerLoginInfo> playerInfo = playerHBaseService.queryPlayerLoginLog(startTimeInit, endTime, server, account);
            if (playerInfo == null) {
                logger.error("查询玩家{}等级失败", account);
                return CommonUtils.alterMessage(PlayerLoginDto.class, CommonErrorKey.ROLE_NOT_EXIST);
            } else {
                PlayerLoginDto dto = new PlayerLoginDto();
                dto.setLoginInfo(playerInfo);

                LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startTimeInit, endTime, account, server});

                return dto;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(PlayerLoginDto.class, CommonErrorKey.UNKNOWN);
        }
    }

    @RequestMapping(value = "playerBagLogQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PlayerBagDto playerBagLogQuery(HttpSession session,
                                          HttpServletRequest request,
                                          @RequestParam(value = "start") String startDate,
                                          @RequestParam(value = "end") String endDate,
                                          @RequestParam(value = "way") int way,
                                          @RequestParam(value = "roleId") String roleId,
                                          @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(PlayerBagDto.class, CommonErrorKey.EMPTY_DATE);
        }
        if (way < 1 || way > 4) {
            return CommonUtils.alterMessage(PlayerBagDto.class, CommonErrorKey.ERROR_PARAM);
        }
        if (StringUtils.isBlank(roleId)) {
            return CommonUtils.alterMessage(PlayerBagDto.class, PlayerErrorKey.EMPTY_ROLE_ID);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndOneServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(PlayerBagDto.class, parse.getMessageKey());
            }
            PlatformKey platformKey = parse.getFirst();
            List<RoleBagLog> bagLogs = playerService.queryRoleBagLog(platformKey.getPlatformId(), platformKey.getServerId(), way, roleId, startDate, endDate);
            List<PlayerBagInfo> data = Lists.newArrayList();
            if (!bagLogs.isEmpty()) {
                if (bagLogs.size() > 1) {
                    for (RoleBagLog r : bagLogs) {
                        PlayerBagInfo p = new PlayerBagInfo(r.getAction(), r.getItemId(), r.getItemNum(), r.getOpt(), DateUtil.timeStampToString(r.getTime()));
                        data.add(p);
                    }
                }
            }
            PlayerBagDto dto = new PlayerBagDto();
            dto.setPlayerBagInfos(data);
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, way, roleId, server});
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(PlayerBagDto.class, CommonErrorKey.UNKNOWN);
        }
    }

    @RequestMapping(value = "playerMonetaryLogQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PlayerMonetaryDto playerMonetaryLogQuery(HttpSession session,
                                                    HttpServletRequest request,
                                                    @RequestParam(value = "start") String startDate,
                                                    @RequestParam(value = "end") String endDate,
                                                    @RequestParam(value = "way") int way,
                                                    @RequestParam(value = "roleId") String roleId,
                                                    @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(PlayerMonetaryDto.class, CommonErrorKey.EMPTY_DATE);
        }
        if (way < 1 || way > 6) {
            return CommonUtils.alterMessage(PlayerMonetaryDto.class, CommonErrorKey.ERROR_PARAM);
        }
        if (StringUtils.isBlank(roleId)) {
            return CommonUtils.alterMessage(PlayerMonetaryDto.class, PlayerErrorKey.EMPTY_ROLE_ID);
        }

        try {
            ServerParse parse = CommonUtils.validateDateAndOneServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(PlayerMonetaryDto.class, parse.getMessageKey());
            }
            PlatformKey platformKey = parse.getFirst();
            List<RoleMonetaryLog> monetaryLogs = playerService.queryRoleMonetaryLog(platformKey.getPlatformId(), platformKey.getServerId(), way, roleId, startDate, endDate);
            List<PlayerMonetaryInfo> data = Lists.newArrayList();
            if (!monetaryLogs.isEmpty()) {
                if (monetaryLogs.size() > 1) {
                    for (RoleMonetaryLog m : monetaryLogs) {
                        PlayerMonetaryInfo p = new PlayerMonetaryInfo(m.getAction(), m.getChange(), m.getNewNum(), m.getOldNum(), DateUtil.timeStampToString(m.getTime()));
                        data.add(p);
                    }
                }
            }
            PlayerMonetaryDto dto = new PlayerMonetaryDto();
            dto.setInfos(data);
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, way, roleId, server});
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(PlayerMonetaryDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}

