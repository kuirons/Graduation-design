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
                <a data-toggle="tab" href="#taskFinishQuery"><spring:message code='gccp.task.query'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="taskFinishQuery" class="tab-pane fade active in">
                <h5><spring:message code='gccp.task.desc'/></h5>
                <div class="well">
                    <s:form id="taskFinishedForm" enctype="multipart/form-data" cssClass="form-horizontal">
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
                    </s:form>
                    <button id="taskFinishQueryBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerRegisterInfo"></div>
                <div id="taskFinishInfo" style="width:100%;"></div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#taskFinishQueryBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        $.post("taskFinishedQuery", $("#taskFinishedForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = ["<spring:message code='gccp.task.taskId' />",
                    "<spring:message code='gccp.task.taskName' />",
                    "<spring:message code='gccp.task.accept'/>",
                    "<spring:message code='gccp.task.finish'/>",
                    "<spring:message code='gccp.task.rate'/>",
                ];

                var info = "<div class='well form-inline'>" +
                        createSpan4LabelInput('<spring:message code='gccp.task.register'/>', data.registerNum) +
                        createSpan4LabelInput('<spring:message code='gccp.task.createRole'/>', data.createRoleNum)
                        + "</div>";
                var column = ["taskId", "taskName", "acceptNum", "finishNum", "finishRate"];

                var table = createTable(data.taskInfo, title, column);
                $("#playerRegisterInfo").empty().html(info);
                $("#taskFinishInfo").empty().html(table);
            }
        });
    });
</script>
</html>