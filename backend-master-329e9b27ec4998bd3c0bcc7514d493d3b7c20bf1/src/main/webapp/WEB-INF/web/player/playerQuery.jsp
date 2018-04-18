<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link href="/css/layout-default-latest.css"
          rel="stylesheet">
    <link rel="stylesheet" href="/css/select2.css">
    <link rel="stylesheet" href="/css/datepicker.css">
    <link rel="stylesheet" href="/css/morris.css">
    <script src="/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap/js/bootstrap.js" type="text/javascript"></script>
    <script src="/js/layout/jquery-ui-latest.js" type="text/javascript"></script>
    <script src="/js/layout/jquery.layout-latest.js" type="text/javascript"></script>
    <script src="/js/my-common.js" type="text/javascript"></script>
    <script src="/js/select2/select2.min.js" type="text/javascript"></script>
    <script src="/js/datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="/js/datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>
    <script src="/js/chart/raphael-min.js" type="text/javascript"></script>
    <script src="/js/chart/morris.min.js" type="text/javascript"></script>
    <title>Insert title here</title>
</head>
<body>
<div class="ui-layout-west">
    <%@ include file="../common/serverList.jsp" %>
</div>
<div class="ui-layout-center">
    <div class="tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a data-toggle="tab" href="#playerInfo"><spring:message code='gccp.player.info.shortTitle'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#playerLevelLog"><spring:message code='gccp.player.query.levelLog'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#playerLoginLog"><spring:message code='gccp.player.login.title'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#playerBagLog"><spring:message code='gccp.player.query.bag'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#playerMonetaryLog"><spring:message code='gccp.player.query.monetary'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="playerInfo" class="tab-pane fade active in">
                <h5><spring:message code='gccp.player.info.desc'/></h5>
                <div class="well">
                    <s:form id="playerInfoForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.info.name'/>:</label>
                            <div class="controls">
                                <input type="text" name="playerName" id="playerName">
                            </div>
                        </div>
                    </s:form>
                    <button id="playerInfoQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerInfoTable" style="width:100%;"></div>
            </div>
            <div id="playerLevelLog" class="tab-pane fade in">
                <h5><spring:message code='gccp.online.desc'/></h5>
                <div class="well">
                    <s:form id="playerLevelLogForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>

                            <div class="controls">
                                <div class="input-daterange" id="datepicker">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text" name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.searchtype'/>:</label>

                            <div class="controls">
                                <select id="s3" name="way">
                                    <option value="1"><spring:message code='gccp.player.query.userName'/></option>
                                    <%--<option value="1"><spring:message code='gccp.player.query.roleId'/></option>--%>
                                </select>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.searchdata'/>:</label>

                            <div class="controls">
                                <label for="playerLevelLogForm_content"></label><textarea name="content" cols="" rows=""
                                                                                          id="playerLevelLogForm_content"></textarea>
                            </div>
                        </div>
                    </s:form>
                    <button id="playerLevelLogQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerLevelInfo"></div>
                <div id="levelLogTable" style="width:100%;"></div>
            </div>
            <div id="playerLoginLog" class="tab-pane fade in">
                <h5><spring:message code='gccp.player.login.desc'/></h5>
                <div class="well">
                    <s:form id="playerLoginForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>

                            <div class="controls">
                                <div class="input-append date form_datetime startDateTimePicker">
                                    <input type="text" id="startTime0" name="startTimeInit"/>
                                    <span class="add-on">
                                        <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                    </span>
                                    <span class="add-on">to</span>
                                </div>

                                <div class="input-append date form_datetime endDateTimePicker">
                                    <input type="text" id="endTime0" name="endTime"/>
                                    <span class="add-on">
                                        <i data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.login.account'/>:</label>
                            <div class="controls">
                                <input type="text" name="account" id="accountName">
                            </div>
                        </div>
                    </s:form>
                    <button id="playerLoginQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerLoginTable" style="width:100%;"></div>
            </div>
            <div id="playerBagLog" class="tab-pane fade in">
                <h5><spring:message code='gccp.online.bagdesc'/></h5>
                <div class="well">
                    <s:form id="playerBagLogForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>
                            <div class="controls">
                                <div class="input-daterange" id="datepicker">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text" name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.bagType'/>:</label>
                            <div class="controls">
                                <select id="s3" name="way">
                                    <option value="1"><spring:message code='gccp.player.query.BAG'/></option>
                                    <option value="2"><spring:message code='gccp.player.query.DEPOT'/></option>
                                    <option value="3"><spring:message code='gccp.player.query.FISH'/></option>
                                    <option value="4"><spring:message code='gccp.player.query.GANG'/></option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.roleId'/>:</label>
                            <div class="controls">
                            	<input type="text" name="roleId" id="roleId">
                            </div>
                        </div>
                    </s:form>
                    <button id="playerBagLogQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerBagLogInfo"></div>
                <div id="bagLogTable" style="width:100%;"></div>
            </div>
            <div id="playerMonetaryLog" class="tab-pane fade in">
                <h5><spring:message code='gccp.online.monetarydesc'/></h5>
                <div class="well">
                    <s:form id="playerMonetaryLogForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>
                            <div class="controls">
                                <div class="input-daterange" id="datepicker">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text" name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.type'/>:</label>
                            <div class="controls">
                                <select id="s3" name="way">
                                    <option value="1"><spring:message code='gccp.player.query.SILVER'/></option>
                                    <option value="2"><spring:message code='gccp.player.query.COPPER'/></option>
                                    <option value="3"><spring:message code='gccp.player.query.MORAL'/></option>
                                    <option value="4"><spring:message code='gccp.player.query.GOLD'/></option>
                                    <option value="5"><spring:message code='gccp.player.query.HONOR'/></option>
                                    <option value="6"><spring:message code='gccp.player.query.GANG_ACTIVE'/></option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.player.query.roleId'/>:</label>
                            <div class="controls">
                            	<input type="text" name="roleId" id="roleId">
                            </div>
                        </div>
                    </s:form>
                    <button id="playerMonetaryLogQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerMonetaryLogInfo"></div>
                <div id="monetaryLogTable" style="width:100%;"></div>
            </div>
        </div>
    </div>
</div>
<div class="modal hide fade" id="trackModel">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">×</a>
    </div>
    <div class="modal-body">
        <div id="itemTrack"></div>

    </div>
    <div class="modal-footer">
        <a href="javascript:void(0);" id="uploadBtn" class="btn btn-primary">确定</a>
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#playerInfoQuery").click(function () {
        var pName = $("#playerName").val();
        if (pName == null || pName == "") {
            bootstrapAlert("<spring:message code='gccp.player.info.nameEmpty' />");
            return;
        }
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("playerInfoQuery", $("#playerInfoForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = [
//                    如果需要使用，再配置
                    <%--"<spring:message code='gccp.player.query.roleId' />",--%>
                    "<spring:message code='gccp.player.query.userName' />",
                    "<spring:message code='gccp.player.query.roleName' />"
                ];
//                var column = ["roleId", "userName", "roleName"];
                var column = ["userName", "roleName"];
                var table = createTable(data.playerInfo, title, column);
                $("#playerInfoTable").empty().html(table);
            }
        });
    });

    $("#playerLevelLogQuery").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("playerLevelLogQuery", $("#playerLevelLogForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = [
                    "<spring:message code='gccp.player.query.level' />",
                    "<spring:message code='gccp.player.query.createTime' />",
                ];
                var column = ["level", "createTime"];
                var info = "<div class='well form-inline'>" +
                        <%--createSpan4LabelInput('<spring:message code='gccp.player.query.roleId'/>', data.roleId) +--%>
                        createSpan4LabelInput('<spring:message code='gccp.player.query.userName'/>', data.userName) +
                        createSpan4LabelInput('<spring:message code='gccp.player.query.roleName'/>', data.roleName)
                        + "</div>";
                var table = createTable(data.resultList, title, column);
                $("#playerLevelInfo").empty().html(info);
                $("#levelLogTable").empty().html(table);
            }
        });
    });

    $("#startTime0").val(moment().subtract(2, "hour").format("YYYY-MM-DD HH:mm:ss"));
    $("#playerLoginQuery").click(function () {
        var pName = $("#accountName").val();
        if (pName == null || pName == "") {
            bootstrapAlert("<spring:message code='gccp.player.login.accountEmpty' />");
            return;
        }
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("playerLoginQuery", $("#playerLoginForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = [
                    "<spring:message code='gccp.player.login.state' />",
                    "<spring:message code='gccp.player.login.ip' />",
                    "<spring:message code='gccp.player.login.time' />"
                ];
                var column = ["state", "ip", "time"];
                var table = createTable(data.loginInfo, title, column);
                $("#playerLoginTable").empty().html(table);
            }
        });
    });
    $("#playerBagLogQuery").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("playerBagLogQuery", $("#playerBagLogForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = [
                    "<spring:message code='gccp.player.query.action' />",
                    "<spring:message code='gccp.player.query.opt' />",
                    "<spring:message code='gccp.player.query.itemId' />",
                    "<spring:message code='gccp.player.query.itemNum' />",
                    "<spring:message code='gccp.player.login.time' />"
                ];
                var column = ["action","opt","itemId","itemNum","time"];
             
                var table = createTable(data.playerBagInfos, title, column);
                $("#bagLogTable").empty().html(table);
            }
        });
    });
    $("#playerMonetaryLogQuery").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("playerMonetaryLogQuery", $("#playerMonetaryLogForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = [
                    "<spring:message code='gccp.player.query.action' />",
                    "<spring:message code='gccp.player.query.oldNum' />",
                    "<spring:message code='gccp.player.query.newNum' />",
                    "<spring:message code='gccp.player.query.change' />",
                    "<spring:message code='gccp.player.login.time' />"
                ];
                var column = ["action","oldNum","newNum","change","time"];
             
                var table = createTable(data.infos, title, column);
                $("#monetaryLogTable").empty().html(table);
            }
        });
    });
</script>
</html>