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
                <a data-toggle="tab" href="#retention"><spring:message code='gccp.retention.save'/></a>
            </li>
        </ul>
        <div id="retention" class="tab-pane fade active in">
            <h5><spring:message code='gccp.retention.desc'/></h5>
            <div class="well">
                <button id="retentionQuery" style="margin-left:180px;" class="btn btn-primary"><spring:message
                        code='gccp.common.query'/></button>
            </div>
            <div id="retentionServer"></div>
            <div id="loginSaveData" style="width:100%"></div>
        </div>
    </div>
</div>
</div>

</body>
<script type="text/javascript">
    $("#retentionQuery").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }
        $.post("retention", serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var info = "<div class='well form-inline'>" +
                        createSpan4LabelInput('<spring:message code='gccp.common.platformServer'/>', data.platformServer) +
                        createSpan4LabelInput('<spring:message code='gccp.common.openDate'/>', data.openServerDate) + "</div>";
                var title = [
                    "<spring:message code="gccp.retention.retentionDate"/> ",
                    "<spring:message code='gccp.retention.register'/>",
                    "<spring:message code='gccp.retention.2' />",
                    "<spring:message code='gccp.retention.3' />",
                    "<spring:message code='gccp.retention.4' />",
                    "<spring:message code='gccp.retention.5' />",
                    "<spring:message code='gccp.retention.6' />",
                    "<spring:message code='gccp.retention.7' />",
                    "<spring:message code='gccp.retention.10' />",
                    "<spring:message code='gccp.retention.14' />",
                    "<spring:message code='gccp.retention.21' />",
                    "<spring:message code='gccp.retention.30' />"
                ];
                var column = ["openDatesNum", "registerNum", "secondNum", "thirdNum", "forthNum",
                    "fifthNum", "sixthNum", "sevenNum", "tenNum", "fourteenNum", "twentyOneNum", "thirtyNum"];
                var table = createTable(data.saveInfo, title, column);
                $("#retentionServer").html(info);
                $("#loginSaveData").html(table);
            }
        });
    });
</script>
</html>