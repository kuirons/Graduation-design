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
                <a data-toggle="tab" href="#mapLogQuery"><spring:message code='gccp.map.query'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="#mapLogQuery" class="tab-pane fade active in">
                <h5><spring:message code='gccp.map.desc'/></h5>
                <div class="well">
                    <s:form id="mapLogForm" enctype="multipart/form-data" cssClass="form-horizontal">
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
                            <label class="control-label"><spring:message code='gccp.map.query.active'/>:</label>
                            <div class="controls">
                                <select id="s3" name="way">
                                    <option value="133"><spring:message code='gccp.map.query.pth'/></option>
                                    <option value="115"><spring:message code='gccp.map.query.hgs'/></option>
                                    <option value="112"><spring:message code='gccp.map.query.senb'/></option>
                                </select>
                            </div>
                        </div>
                    </s:form>
                    <button id="mapLogQueryBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.query"/></button>
                </div>
                <div id="playerRegisterInfo"></div>
                <div id="mapLogInfo" style="width:100%;"></div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#mapLogQueryBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        $.post("mapLog1Query", $("#mapLogForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = ["参与人数",
                    "参与率",
                    "平均参与时间",
                ];

                var column = ["roleNum", "involvRate", "avgTime"];

                var table = createTable(data.lists, title, column);
                $("#mapLogInfo").empty().html(table);
            }
        });
    });
</script>
</html>