<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<div class="ui-layout-center">
    <div class="tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a data-toggle="tab" href="#activeCodeQuery"><spring:message code='gccp.activeCode.title'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="activeCodeQuery" class="tab-pane fade active in"
                 style="width:80%;margin: 0 auto;border: 1px solid #DDD;border-radius: 10px;padding: 20px;">
                <h5><spring:message code='gccp.activeCode.desc'/></h5>
                <h5 style="color: red">${platformInfo}</h5>
                <div class="well">
                    <s:form id="activeCodeForm" action="exportTable" target="exportframe" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.activeCode.platform'/>:</label>
                            <div class="controls">
                                <input type="text" name="platform"
                                       placeholder="<spring:message code='gccp.activeCode.platform.desc'/>"
                                       title=="<spring:message code='gccp.activeCode.platform.desc'/>">
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.activeCode.reward'/>:</label>
                            <div class="controls">
                                <input type="text" name="reward"
                                       placeholder="<spring:message code='gccp.activeCode.reward.desc'/>"
                                       title="<spring:message code='gccp.activeCode.reward.desc'/>">
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.activeCode.type'/>:</label>
                            <div class="controls">
                                <input type="text" name="type"
                                       placeholder="<spring:message code='gccp.activeCode.type.desc'/>"
                                       title="<spring:message code='gccp.activeCode.type.desc'/>">
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.activeCode.server'/>:</label>
                            <div class="controls">
                                <input type="text" name="server"
                                       placeholder="<spring:message code='gccp.activeCode.server.desc'/>"
                                       title="<spring:message code='gccp.activeCode.server.desc'/>">
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.activeCode.count'/>:</label>
                            <div class="controls">
                                <input type="text" name="count" value="1">
                            </div>
                        </div>
                    </s:form>
                    <button id="activeCodeBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start" /></button>
                    <button id='exportBtn' style='margin-left:70px;' class='btn btn-info'>导出激活码</button>
                    <iframe name='exportframe' style='display: none;' src="message.html"></iframe>
                </div>
                <div id="activeCodeInfo" style="width:100%;"></div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#activeCodeBtn").click(function () {
        $.post("activeCodeQuery", $("#activeCodeForm").serialize(), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert	(data.message);
            } else {
                var title = ["<spring:message code='gccp.activeCode.title' />"];
                var column = ["code"];

                var table = createTableFive(data.code, title, column);
                $("#activeCodeInfo").empty().html(table);
                
            }
        });
    });
    
    $("#exportBtn").click(function () {
        $("#activeCodeForm").submit();
    });
    
</script>
</html>