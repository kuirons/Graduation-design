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
                <a data-toggle="tab" href="#registerQuery"><spring:message code='gccp.reg.sumRegister'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="registerQuery" class="tab-pane fade active in">
                <h5><spring:message code='gccp.register.desc'/></h5>
                <div class="well">
                    <s:form id="registerForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.common.date'/>:</label>

                            <div class="controls">
                                <div class="input-daterange" id="datepicker2">
                                    <input type="text" name="start" class="input-small yesterday"/>
                                    <span class="add-on">to</span>
                                    <input type="text"  name="end" class="input-small today"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                    <button id="registerBtn" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code='gccp.common.query'/></button>
                </div>

                <div id="registerChart" style="width:100%;"></div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#registerBtn").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        $.post("regUsersQuery", $("#registerForm").serialize() + serverToParamBySelect(servers), function (data) {
            if (data.message && data.message != "") {
                bootstrapAlert(data.message);
            } else {
                var title = ["<spring:message code='gccp.common.platformServer' />",
                    "<spring:message code='gccp.recharge.registerNum' />",
                    "<spring:message code='gccp.recharge.createRoleNum' />",
                    "<spring:message code='gccp.recharge.createRoleRate' />"
                ];
                var column = ["platformServer", "registerNum", "createRoleNum", "createRate"];

                var table = createTable(data.registerInfo, title, column);
                $("#registerChart").empty().html(table);
            }
        });
    });
</script>
</html>