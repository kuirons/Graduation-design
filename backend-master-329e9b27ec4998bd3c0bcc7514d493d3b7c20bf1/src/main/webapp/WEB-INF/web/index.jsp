<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="gccp.index.title"/></title>

    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="stylesheet" href="../css/index.css">
    <link rel="stylesheet" href="../css/skins/ext.css" id="window-skin">
    <!-- 这里主要是为了中间那块文字-->
    <style type="text/css">
        .note {
            color: #FFFFFF;
            font-size: 16px;
            text-align: center;
        }

        .note_x {
            color: #BBBBBB;
            font-size: 12px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="loading"></div>
<!-- 桌面 -->
<div id="desktop">
    <div id="zoom-tip">
        <div><i></i>​<span></span></div>
        <a href="javascript:;" class="close" onClick="HROS.zoom.close();">x</a></div>
    <div id="desk">
        <sec:authorize access="hasAuthority('OPERATE_TOOL')">
            <div id="desk-1" class="desktop-container">
                <div class="scrollbar scrollbar-x"></div>
                <div class="scrollbar scrollbar-y"></div>
                <sec:authorize access="hasAuthority('ONLINE_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="1" id="d_1" class="appbtn" title="<spring:message code="gccp.online.query"/>"
                        url="onlineQuery/onlineQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.online.query"/>"
                                  title="<spring:message code="gccp.online.query"/>" src="../images/ui/online.png">
                        </div>
                        <span><spring:message code="gccp.online.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('RECHARGE_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="2" id="d_2" class="appbtn" title="<spring:message code="gccp.recharge.query"/>"
                        url="rechargeQuery/rechargeQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.recharge.query"/>"
                                  title="<spring:message code="gccp.recharge.query"/>" src="../images/ui/recharge.png">
                        </div>
                        <span><spring:message code="gccp.recharge.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('CONSUME_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="4" id="d_4" class="appbtn" title="<spring:message code="gccp.consume.query"/>"
                        url="consumeQuery/consumeQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.consume.query"/>"
                                  title="<spring:message code="gccp.consume.query"/>" src="../images/ui/consume.png">
                        </div>
                        <span><spring:message code="gccp.consume.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('RETENTION_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="5" id="d_5" class="appbtn" title="<spring:message code="gccp.retention.query"/>"
                        url="retentionQuery/retentionQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.retention.query"/>"
                                  title="<spring:message code="gccp.retention.query"/>"
                                  src="../images/ui/retention.png"></div>
                        <span><spring:message code="gccp.retention.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('TASK_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="6" id="d_6" class="appbtn" title="<spring:message code="gccp.task.query"/>"
                        url="taskQuery/taskQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.task.query"/>"
                                  title="<spring:message code="gccp.task.query"/>" src="../images/ui/task.png"></div>
                        <span><spring:message code="gccp.task.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('LEVEL_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="7" id="d_7" class="appbtn" title="<spring:message code="gccp.level.query"/>"
                        url="levelQuery/levelQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.level.query"/>"
                                  title="<spring:message code="gccp.level.query"/>" src="../images/ui/level.png"></div>
                        <span><spring:message code="gccp.level.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('REG_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="8" id="d_8" class="appbtn" title="<spring:message code="gccp.reg.query"/>"
                        url="regQuery/regQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.reg.query"/>"
                                  title="<spring:message code="gccp.reg.query"/>" src="../images/ui/reg.png"></div>
                        <span><spring:message code="gccp.reg.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('ZONE_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="9" id="d_9" class="appbtn" title="<spring:message code="gccp.zoneQuery.query"/>"
                        url="zoneManage/zoneManage" opening="0">
                        <div><img alt="<spring:message code="gccp.zoneQuery.query"/>"
                                  title="<spring:message code="gccp.zoneQuery.query"/>" src="../images/ui/zone.png">
                        </div>
                        <span><spring:message code="gccp.zoneQuery.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('PLAYER_QUERY')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="10" id="d_10" class="appbtn" title="<spring:message code="gccp.player.query"/>"
                        url="playerQuery/playerQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.player.query"/>"
                                  title="<spring:message code="gccp.player.query"/>" src="../images/ui/player.png">
                        </div>
                        <span><spring:message code="gccp.player.query"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('COMMON_GM')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="11" id="d_11" class="appbtn" title="<spring:message code="gccp.gm.mail.title"/>"
                        url="commonGm/gmQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.gm.mail.title"/>"
                                  title="<spring:message code="gccp.gm.mail.title"/>" src="../images/ui/commonGm.png">
                        </div>
                        <span><spring:message code="gccp.gm.mail.title"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('COMMON_SUPER_GM')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="12" id="d_12" class="appbtn" title="<spring:message code="gccp.superGm.mail.title"/>"
                        url="superGm/superGmQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.superGm.mail.title"/>"
                                  title="<spring:message code="gccp.superGm.mail.title"/>" src="../images/ui/setgm.png">
                        </div>
                        <span><spring:message code="gccp.superGm.mail.title"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('ACTIVE_CODE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="<spring:message code="gccp.superGm.mail.title"/>"
                        url="activeCode/active" opening="0">
                        <div><img alt="<spring:message code="gccp.activeCode.title"/>"
                                  title="<spring:message code="gccp.activeCode.title"/>" src="../images/ui/Calculator.png">
                        </div>
                        <span><spring:message code="gccp.activeCode.title"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('FORBID_ROLE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="<spring:message code="gccp.forbid.title"/>"
                        url="forbid/forbid" opening="0">
                        <div><img alt="<spring:message code="gccp.forbid.title"/>"
                                  title="<spring:message code="gccp.forbid.title"/>" src="../images/ui/permission.png">
                        </div>
                        <span><spring:message code="gccp.forbid.title"/></span></li>
                </sec:authorize>
                <!-- 地图日志统计 -->
                <sec:authorize access="hasAuthority('MAP_ROLE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="<spring:message code="gccp.map.title"/>"
                        url="mapLogQuery/mapLogQuery" opening="0">
                        <div><img alt="<spring:message code="gccp.map.title"/>"
                                  title="<spring:message code="gccp.map.title"/>" src="../images/ui/permission.png">
                        </div>
                        <span><spring:message code="gccp.map.title"/></span></li>
                </sec:authorize>
                <!-- 用户管理 -->
				<sec:authorize access="hasAuthority('USER_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="用户管理"
                        url="userManage/userManage" opening="0">
                        <div><img alt="用户管理"
                                  title="用户管理" src="../images/ui/permission.png">
                        </div>
                        <span>用户管理</span></li>
                </sec:authorize>
                <!-- 角色管理 -->
				<sec:authorize access="hasAuthority('ROLE_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="角色管理"
                        url="roleManage/roleManage" opening="0">
                        <div><img alt="角色管理"
                                  title="角色管理" src="../images/ui/permission.png">
                        </div>
                        <span>角色管理</span></li>
                </sec:authorize>
                <!-- 权限管理 -->
				<sec:authorize access="hasAuthority('PERMISSION_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="13" id="d_13" class="appbtn" title="权限管理"
                        url="permissionManage/permissionManage" opening="0">
                        <div><img alt="权限管理"
                                  title="权限管理" src="../images/ui/permission.png">
                        </div>
                        <span>权限管理</span></li>
                </sec:authorize>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('OPERATIONAL_MANAGE')">
            <div id="desk-3" class="desktop-container">
                <div class="scrollbar scrollbar-x"></div>
                <div class="scrollbar scrollbar-y"></div>
                <sec:authorize access="hasAuthority('OPERATE_COMMAND_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="15" id="d_15" class="appbtn" title="<spring:message code="gccp.GMCommand.manage"/>"
                        url="operate/operate" opening="0">
                        <div><img alt="<spring:message code="gccp.GMCommand.manage"/>"
                                  title="<spring:message code="gccp.GMCommand.manage"/>" src="../images/ui/gm.png">
                        </div>
                        <span><spring:message code="gccp.GMCommand.manage"/></span></li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('GMCOMMAND_MANAGE')">
                    <li isresize="1" isopenmax="0" type="app" style="top:27px;left:36px" width="1366" height="768"
                        appid="16" id="d_16" class="appbtn" title="<spring:message code="gccp.GMCommand.manage"/>"
                        url="gmCommand/gmCommand" opening="0">
                        <div><img alt="<spring:message code="gccp.GMCommand.manage"/>"
                                  title="<spring:message code="gccp.GMCommand.manage"/>" src="../images/ui/gm.png">
                        </div>
                        <span><spring:message code="gccp.GMCommand.manage"/></span></li>
                </sec:authorize>
            </div>
        </sec:authorize>
        <div id="dock-bar">
            <div id="dock-container">
                <div class="dock-middle">
                    <div class="dock-applist">
                        <div class="note">${sessionScope.username}，<spring:message code="gccp.index.welcome"/></div>
                        <div class="note_x"><spring:message code="gccp.index.note"/></div>

                    </div>
                    <div class="dock-toollist">
                        <sec:authorize access="hasAuthority('PERMISSION_MANAGE')">
                            <a href="javascript:;" class="dock-tool-setting"
                               title='<spring:message code="gccp.index.settings"/>'></a>
                        </sec:authorize>
                        <span href="javascript:;" class="dock-tool-logout"
                              title="<spring:message code="gccp.index.logout"/>"></span>

                    </div>
                </div>
            </div>
        </div>

    </div>
    <div id="task-bar-bg1"></div>
    <div id="task-bar-bg2"></div>
    <div id="task-bar">
        <div id="task-next"><a href="javascript:;" id="task-next-btn" hidefocus="true"></a></div>
        <div id="task-content">
            <div id="task-content-inner"></div>
        </div>
        <div id="task-pre"><a href="javascript:;" id="task-pre-btn" hidefocus="true"></a></div>
    </div>
    <div id="nav-bar">
        <div class="nav-wrapper">
            <div class="head">
            </div>
            <div class="middle" id="navContainer">
                <sec:authorize access="hasAuthority('OPERATE_TOOL')">
                    <div class="menu" index="1"><spring:message code='gccp.operate.tool'/></div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('CUSTOMER_SERVICE')">
                    <div class="menu" index="2"><spring:message code='gccp.customer.service'/></div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('OPERATIONAL_MANAGE')">
                    <div class="menu" index="3"><spring:message code='gccp.operational.manage'/></div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('MOLOONG_TOOL')">
                    <div class="menu" index="4"><spring:message code='gccp.xiyou.tool'/></div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('SYSTEM_MANAGE')">
                    <div class="menu" index="5">系统管理</div>
                </sec:authorize>
            </div>
            <div class="hou">
                <a class="indicator indicator-manage" href="javascript:;"
                   title="<spring:message code='gccp.all.view' />"></a>
            </div>
        </div>
    </div>
</div>
<!-- 全局视图 -->
<div id="appmanage">
    <a class="amg_close" href="javascript:;"></a>
    <div id="amg_dock_container"></div>
    <div class="amg_line_x"></div>
    <div id="amg_folder_container">
        <sec:authorize access="hasAuthority('OPERATE_TOOL')">
            <div class="folderItem">
                <div class="folder_bg"><spring:message code='gccp.operate.tool'/></div>
                <div class="folderOuter">
                    <div class="folderInner"></div>
                    <div class="scrollBar"></div>
                </div>
                <div class="amg_line_y"></div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('CUSTOMER_SERVICE')">
            <div class="folderItem">
                <div class="folder_bg"><spring:message code='gccp.customer.service'/></div>
                <div class="folderOuter">
                    <div class="folderInner"></div>
                    <div class="scrollBar"></div>
                </div>
                <div class="amg_line_y"></div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('OPERATIONAL_MANAGE')">
            <div class="folderItem">
                <div class="folder_bg"><spring:message code='gccp.operational.manage'/></div>
                <div class="folderOuter">
                    <div class="folderInner"></div>
                    <div class="scrollBar"></div>
                </div>
                <div class="amg_line_y"></div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('MOLOONG_TOOL')">
            <div class="folderItem">
                <div class="folder_bg"><spring:message code='gccp.xiyou.tool'/></div>
                <div class="folderOuter">
                    <div class="folderInner"></div>
                    <div class="scrollBar"></div>
                </div>
                <div class="amg_line_y"></div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAuthority('SYSTEM_MANAGE')">
            <div class="folderItem">
                <div class="folder_bg">系统管理</div>
                <div class="folderOuter">
                    <div class="folderInner"></div>
                    <div class="scrollBar"></div>
                </div>
                <div class="amg_line_y"></div>
            </div>
        </sec:authorize>
        <div class="folderItem">
            <div class="amg_line_y"></div>
        </div>
    </div>
</div>
<script src="../js/jquery-1.8.3.min.js"></script>
<script src="../js/HoorayLibs/hooraylibs.js"></script>
<script src="../js/templates.js"></script>
<script src="../js/util.js"></script>
<script src="../js/core.js"></script>
<script src="../js/hros.app.js"></script>
<script src="../js/hros.appmanage.js"></script>
<script src="../js/hros.base.js"></script>
<script src="../js/hros.desktop.js"></script>
<script src="../js/hros.dock.js"></script>
<script src="../js/hros.folderView.js"></script>
<script src="../js/hros.grid.js"></script>
<script src="../js/hros.maskBox.js"></script>
<script src="../js/hros.navbar.js"></script>
<script src="../js/hros.popupMenu.js"></script>
<script src="../js/hros.taskbar.js"></script>
<script src="../js/hros.uploadFile.js"></script>
<script src="../js/hros.wallpaper.js"></script>
<script src="../js/hros.widget.js"></script>
<script src="../js/hros.window.js"></script>
<script src="../js/hros.zoom.js"></script>
<script src="../js/artDialog4.1.6/jquery.artDialog.js?skin=default"></script>
<script src="../js/artDialog4.1.6/plugins/iframeTools.js"></script>
<script>

    function loading() {
        $(".window-loading").fadeIn(200);
    }
    function loadingOut() {
        $(".window-loading").fadeOut(200);
    }
    $(function () {
        //IE下禁止选中
        document.body.onselectstart = document.body.ondrag = function () {
            return false;
        }

        $('.loading').hide();
        $('#desktop').show();
        //初始化
        HROS.base.init();

        /* $.dialog({
         title: '欢迎使用 HoorayOS',
         icon: 'face-smile',
         width: 320,
         content: 'HoorayOS 是否就是你一直想要的 web 桌面么？<br>' + '那么我非常期待您能够热情的提供<font style="color:red"> 35 元</font>或者其他金额的捐赠鼓励，正如您支持其他开源项目一样。<br>' + '支付宝：<a href="https://me.alipay.com/hooray" style="color:#214FA3" target="_blank">https://me.alipay.com/hooray</a><div style="width:100%;height:0px;font-size:0;border-bottom:1px solid #ccc"></div>如果你对本框架感兴趣，欢迎加入讨论群：<br>213804727' + '<div style="width:100%;height:0px;font-size:0;border-bottom:1px solid #ccc"></div>' +
         'HoorayOS 仅供个人学习交流，未经授权禁止用于商业用途，版权归作者所有，未经作者同意，不得删除代码中作者信息。若需要商业使用，请联系 QQ：304327508 进行授权'
         }); */
    });
</script>
</body>
</html>