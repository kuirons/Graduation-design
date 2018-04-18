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
                <a data-toggle="tab" href="#forbidInfo"><spring:message code='gccp.forbid.info'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#forbidRole"><spring:message code='gccp.forbid.title'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#kickOffRole"><spring:message code='gccp.forbid.kickTitle'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="forbidInfo" class="tab-pane fade active in">
                <h5><spring:message code='gccp.forbid.info.desc'/></h5>
                <div class="well">
                    <button id="forbidInfoBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code='gccp.common.query'/></button>
                </div>
                <div class="row-fluid">
                    <div id="forbidIpInfo" class="span6">
                    </div>
                    <div id="forbidAccountInfo" class="span6">
                    </div>
                </div>
            </div>
            <div id="forbidRole" class="tab-pane fade in">
                <h5><spring:message code='gccp.forbid.role.desc'/></h5>
                <div class="well">
                    <s:form id="forbidRoleForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.forbid.role.content'/>:</label>
                            <div class="controls">
                                <input type="text" id="forbidContent" name="forbidContent"/>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.forbid.role.type'/>:</label>
                            <div class="controls">
                                <select id="forbidType" name="forbidType">
                                    <option value="0" selected="selected"><spring:message
                                            code='gccp.forbid.byIp'/></option>
                                    <option value="1"><spring:message code='gccp.forbid.byAccount'/></option>
                                </select>
                            </div>
                        </div>
                    </s:form>
                    <button id="addForbidBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code='gccp.forbid.addForbid'/></button>
                    <button id="delForbidBtn" class="btn btn-primary"><spring:message
                            code='gccp.forbid.removeForbid'/></button>
                    <button id="delAllForbidBtn" class="btn btn-primary"><spring:message
                            code='gccp.forbid.delAll'/></button>
                </div>
                <div id="forbidResultInfo"></div>
            </div>
            <div id="kickOffRole" class="tab-pane fade in">
                <h5><spring:message code='gccp.forbid.kickDesc'/></h5>
                <div class="well">
                    <s:form id="kickOffRoleForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <label class="control-label"><spring:message code='gccp.forbid.kickAccount'/>:</label>
                        <div class="controls">
                            <input type="text" id="kickOffAccount" name="kickOffAccount"/>
                        </div>
                    </s:form>
                    <button id="kickOffBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code='gccp.common.start'/></button>
                </div>
                <div id="kickOffResult"></div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#forbidInfoBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("info", serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var ipTitle = ["<spring:message code='gccp.forbid.ip' />"];
                var accountTitle = ["<spring:message code='gccp.forbid.account' />"];
                var table1 = createTableWithData(data.forbidIp, ipTitle);
                var table2 = createTableWithData(data.forbidAccount, accountTitle);
                $("#forbidIpInfo").html(table1);
                $("#forbidAccountInfo").html(table2);
            }
        });
    });

    $("#addForbidBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        if ($("#forbidContent").val() == "") {
            bootstrapAlert("<spring:message code='gccp.forbid.emptyContent' />");
            return;
        }
        $.post("addForbid", $("#forbidRoleForm").serialize() + serverToParamBySelect(servers) + "&add=1", function (data) {
            var info = "<div class='form-horizontal well'><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.result'/>' + "</label><div class='controls'>"
                    + "<input class='form-control' value='" + (data.result == -1 ? '<spring:message code='gccp.common.fail'/>' : '<spring:message code='gccp.common.succ'/>') + "' type='text' style='margin-left:5px;' readonly='readonly'/></div>"
                    + "</div><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.resultDesc'/>' + "</label><div class='controls'>"
                    + "<textarea  cols='' rows='' id=''>" + data.content + "</textarea></div></div></div>";
            $("#forbidResultInfo").empty().html(info);
        });
    });
    $("#delForbidBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        if ($("#forbidContent").val() == "") {
            bootstrapAlert("<spring:message code='gccp.forbid.emptyContent' />");
            return;
        }
        $.post("addForbid", $("#forbidRoleForm").serialize() + serverToParamBySelect(servers) + "&add=2", function (data) {
            var info = "<div class='form-horizontal well'><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.result'/>' + "</label><div class='controls'>"
                    + "<input class='form-control' value='" + (data.result == -1 ? '<spring:message code='gccp.common.fail'/>' : '<spring:message code='gccp.common.succ'/>') + "' type='text' style='margin-left:5px;' readonly='readonly'/></div>"
                    + "</div><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.resultDesc'/>' + "</label><div class='controls'>"
                    + "<textarea  cols='' rows='' id=''>" + data.content + "</textarea></div></div></div>";
            $("#forbidResultInfo").empty().html(info);
        });
    });

    $("#delAllForbidBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("addForbid", $("#forbidRoleForm").serialize() + serverToParamBySelect(servers) + "&add=3", function (data) {
            var info = "<div class='form-horizontal well'><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.result'/>' + "</label><div class='controls'>"
                    + "<input class='form-control' value='" + (data.result == -1 ? '<spring:message code='gccp.common.fail'/>' : '<spring:message code='gccp.common.succ'/>') + "' type='text' style='margin-left:5px;' readonly='readonly'/></div>"
                    + "</div><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.resultDesc'/>' + "</label><div class='controls'>"
                    + "<textarea  cols='' rows='' id=''>" + data.content + "</textarea></div></div></div>";
            $("#forbidResultInfo").empty().html(info);
        });
    });

    $("#kickOffBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        if ($("#kickOffAccount").val() == "") {
            bootstrapAlert("<spring:message code='gccp.forbid.accountEmpty' />");
            return;
        }
        $.post("kickOff", $("#kickOffRoleForm").serialize() + serverToParamBySelect(servers), function (data) {
            var info = "<div class='form-horizontal well'><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.result'/>' + "</label><div class='controls'>"
                    + "<input class='form-control' value='" + (data.result == -1 ? '<spring:message code='gccp.common.fail'/>' : '<spring:message code='gccp.common.succ'/>') + "' type='text' style='margin-left:5px;' readonly='readonly'/></div>"
                    + "</div><div class='control-group '><label class='control-label' >" + '<spring:message code='gccp.common.resultDesc'/>' + "</label><div class='controls'>"
                    + "<textarea  cols='' rows='' id=''>" + data.content + "</textarea></div></div></div>";
            $("#kickOffResult").empty().html(info);
        });
    });
</script>
</html>