<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link href="/css/layout-default-latest.css" rel="stylesheet">
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
                <a data-toggle="tab" href="#realTime"><spring:message code='gccp.online.realTime'/></a>
            </li>
            <!--
            <li class="">
                <a data-toggle="tab" href="#history"><spring:message code='gccp.online.history'/></a>
            </li>

            <li class="">
                <a data-toggle="tab" href="#top"><spring:message code='gccp.online.top'/></a>
            </li>

            <li class="">
                <a data-toggle="tab" href="#daily"><spring:message code='gccp.online.daily'/></a>
            </li>
            -->

            <li class="">
                <a data-toggle="tab" href="#onlineTime"><spring:message code='gccp.online.time'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="realTime" class="tab-pane fade active in">
                <h5><spring:message code='gccp.online.realTime.desc'/></h5>
                <div class="well">
                    <form id="realTimeForm" enctype="multipart/form-data" class="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.online.nowTime'/></label>
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
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.online.step'/>:</label>
                            <div class="controls">
                                <input id="timeStep" name="step" type="text" value="5"/>
                            </div>
                        </div>
                    </form>
                    <button id="realTimeQuery" style="margin-left: 180px;" class="btn btn-primary">
                        <spring:message code="gccp.common.query"/>
                    </button>
                </div>
                <div id="realTimeData" style="width:100%"></div>
            </div>
            <!--
            <div id="history" class="tab-pane fade">
                <div class="well">
                    <form id="historyForm" class="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>

                            <div class="controls">
                                <div class="input-daterange" id="datepicker2">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text" name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.viewType'/>:</label>

                            <div class="controls">
                                <select id="s2" name="type">
                                    <option value="0"><spring:message code='gccp.common.table'/></option>
                                    <option value="1"><spring:message code="gccp.common.line"/></option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <button id="historyQuery" style="margin-left:180px;" class="btn btn-primary">
                        <spring:message code='gccp.common.query'/></button>
                </div>
                <div id="historyFormData" style="width:100%"></div>
            </div>
            <div id="top" class="tab-pane fade">
                <div class="well">
                    <form id="topForm" class="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>

                            <div class="controls">
                                <div class="input-daterange" id="datepicker3">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text" name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.viewType'/>:</label>

                            <div class="controls">
                                <label for="topS"></label>
                                <select id="topS" name="type">
                                    <option value="0" selected="selected"><spring:message
                                            code='gccp.common.table'/></option>
                                    <option value="1"><spring:message code='gccp.common.line'/></option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <button id="topQuery" style="margin-left:180px;" class="btn btn-primary">
                        <spring:message code='gccp.common.query'/></button>
                </div>
                <div id="topChart" style="width:100%"></div>
            </div>
            <div id="daily" class="tab-pane fade in">
                <div class="well">
                    <form id="dailyForm" enctype="multipart/form-data" class="form-horizontal">
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
                    </form>
                    <button id="dailyQuery" style="margin-left: 180px;" class="btn btn-primary">
                        <spring:message code="gccp.common.query"/>
                    </button>
                </div>
                <div id="dailyTable" style="width: 100%"></div>
            </div>
-->
            <div id="onlineTime" class="tab-pane fade in">
                <h5><spring:message code='gccp.online.onlineTime.desc'/></h5>
                <div class="well">
                    <form id="onlineTimeForm" enctype="multipart/form-data" class="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.online.nowTime'/></label>
                            <div class="controls">
                                <div class="input-append date form_datetime startDateTimePicker">
                                    <input type="text" id="startTime" name="startTime"/>
					            <span class="add-on">
					                <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
					            </span>
                                    <span class="add-on">to</span>
                                </div>

                                <div class="input-append date form_datetime endDateTimePicker">
                                    <input type="text" id="endTime" name="endTime"/>
					            <span class="add-on">
                                    <i data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
					            </span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <button id="onlineTimeQuery" style="margin-left: 180px;" class="btn btn-primary">
                        <spring:message code="gccp.common.query"/>
                    </button>

                </div>
                <div style="width: 100%">
                    <div id="onlineTimeTable"></div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#startTime0").val(moment().subtract(2, "hour").format("YYYY-MM-DD HH:mm:ss"));
    $("#onlineTimeQuery").click(function () {
        var startTime = $("#startTime");
        var endTime = $("#endTime");
        if (startTime.value == "" || endTime.value == "") {
            bootstrapAlert("请选择时间段");
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
        $.post("onlineTimeQuery", startTime.attr("name") + "=" + startTime.val() + "&" + endTime.attr("name") + "=" + endTime.val() + serverToParamBySelect(servers),
                function (data) {
                    if (data.message && data.message != "") {
                        bootstrapAlert(data.message);
                    } else {
                        if (data.onlineTimeInfo) {
                            var title = [
                                "<spring:message code='gccp.online.account' />",
                                "<spring:message code='gccp.online.roleName' />",
                                "<spring:message code='gccp.online.level' />",
                                "<spring:message code='gccp.online.startTime' />",
                                "<spring:message code='gccp.online.onlineTime' />"];
                            var column = ["account", "roleName", "level", "startTime", "onlineTime"];
                            var table = createTable(data.onlineTimeInfo, title, column);
                            $("#onlineTimeTable").html(table);
                        }
                    }
                }
        );
    });
    $("#realTimeQuery").click(function () {
        var startTime = $("#startTime0");
        var endTime = $("#endTime0");
        if (startTime.value == "" || endTime.value == "") {
            bootstrapAlert("请选择时间段");
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
        var timeStep = $("#timeStep");
        $.post("realTimeQuery", timeStep.attr("name") + "=" + timeStep.val() + "&" + startTime.attr("name") + "=" + startTime.val() + "&" + endTime.attr("name") + "=" + endTime.val() + serverToParamBySelect(servers),
                function (data) {
                    if (data.message && data.message != "") {
                        bootstrapAlert(data.message);
                    } else {
                        if (data.realTimeInfo) {
                            createLineChart("realTimeData", data.realTimeInfo, "nowTime", "onlineNum", "<spring:message code='gccp.online.count' />");
                        }
                    }
                });
    });
    $("#historyQuery").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        $.post("historyQuery", $("#historyForm").serialize() + serverToParamBySelect(servers),
                function (data) {
                    if (data.message && data.message != "") {
                        bootstrapAlert(data.message);
                    } else {
                        if (data.type == "1") {

                        } else {
                            var title = [
                                "<spring:message code='gccp.common.platformServer' />",
                                "<spring:message code='gccp.common.time' />",
                                "<spring:message code='gccp.online.count' />"];
                            var column = ["platformServer", "time", "onlineNum"];
                            var table = createTable(data.historyInfo, title, column);
                            $("#historyFormData").html(table);
                        }
                    }
                });

    });
    $("#topQuery").click(function () {
        var servers = selectedServers();

        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        $.post("topQuery", $("#topForm").serialize() + serverToParamBySelect(servers),
                function (data) {
                    if (data.message && data.message != "") {
                        bootstrapAlert(data.message);
                    } else {
                        if (data.type == "1") {

                        } else {
                            var title = [
                                "<spring:message code='gccp.common.platformServer' />",
                                "<spring:message code='gccp.common.time' />",
                                "<spring:message code='gccp.online.count' />"];
                            var column = ["platformServer", "time", "onlineNum"];
                            var table = createTable(data.topInfo, title, column);
                            $("#topChart").empty().html(table);
                        }
                    }
                });

    });
    $("#dailyQuery").click(
            function () {
                var servers = selectedServers();
                if (servers.length == 0) {
                    bootstrapAlert("<spring:message code='gccp.common.select.server' />");
                    return;
                }
                $.post("dailyQuery", $("#dailyForm").serialize() + serverToParamBySelect(servers),
                        function (data) {
                            if (data.message && data.message != "") {
                                bootstrapAlert("<spring:message code='gccp.common.error' />");
                            } else {
                                if (data.dailyInfo) {
                                    var title = [
                                        "<spring:message code='gccp.common.platformServer' />",
                                        "<spring:message code='gccp.online.averageTime'/> "
                                    ];
                                    var column = ["platformServer", "averageTime"];
                                    var table = createTable(data.dailyInfo, title, column);
                                    $("#dailyTable").html(table);
                                }
                            }
                        });

            });
</script>
</html>