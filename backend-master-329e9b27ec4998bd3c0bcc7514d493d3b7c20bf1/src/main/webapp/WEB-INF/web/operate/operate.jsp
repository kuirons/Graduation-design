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
    <script src="/js/xy-async.js" type="text/javascript"></script>
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
                <a data-toggle="tab" href="#gmcommand"><spring:message code='gccp.GMCommand.manage'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#innerReload"><spring:message code='gccp.gm.inner.reload'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="gmcommand" class="tab-pane fade active in">
                <h5><spring:message code='gccp.gm.desc'/></h5>
                <div class="well">
                    <s:form id="GMForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.GMCommand.manage'/>:</label>
                            <div class="controls">
                                <select id="s3" name="command">
                                    <option value="1" selected="selected"><spring:message
                                            code='gccp.gm.reload.desc'/></option>
                                    <option value="2"><spring:message code='gccp.gm.jar.desc'/></option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.GMCommand.param'/>:</label>
                            <div class="controls">
                                <label for="GMForm_param"></label><textarea name="gmParam" cols="" rows=""
                                                                            id="GMForm_param"></textarea>
                            </div>
                        </div>
                    </s:form>
                    <button id="action" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start"/></button>
                </div>
                <div class="row-fluid">
                    <div id="fail" class="span6">
                    </div>
                    <div id="success" class="span6">
                    </div>
                </div>
            </div>
            <div id="innerReload" class="tab-pane fade in">
                <h5><spring:message code='gccp.gm.inner.reload'/></h5>
                <div class="well">
                    <button id="innerReloadAction" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start"/></button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>


<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">×</a>
        <h5><spring:message code='gccp.gm.actioning'/></h5>
    </div>
    <div class="modal-body">
        <div class="progress progress-striped active">
            <div id="bar" class="bar" style="width: 0%;"></div>
        </div>
        <div id="num" style="text-align:center">
            0%
        </div>
    </div>
</div>
<div class="modal hide fade" id="checkModel">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">×</a>
        <h3>自定义勾选</h3>
    </div>
    <div class="modal-body">
        <textarea id="checkInfo"></textarea>
    </div>
    <div class="modal-footer">
        <a href="javascript:void(0);" id="customCheckSubmit" class="btn btn-primary">确定</a>
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>
</body>
<script>
    $("#action").click(function () {
        var servers = selectedServers();
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
        } else {
            var gmType = $("#s3")
            if (gmType.val() == 3 && servers.length > 1) {
                bootstrapAlert("<spring:message code='gccp.gm.setGm.err' />");
                return;
            }
            bootstrapConfirm("<spring:message code='gccp.gm.confirm'/>", function () {
                $.post("operateGMCommand", $("#GMForm").serialize() + serverToParamBySelect(servers), function (data) {
                    if (data.errorMessage != "") {
                        bootstrapAlert(data.errorMessage);
                    } else {
                        actionState(data, "fail", "success");
                    }
                });
            });
        }
    });
    $("#innerReloadAction").click(function () {
        bootstrapConfirm("<spring:message code='gccp.gm.confirm'/>", function () {
            $.post("innerReload", function (data) {
                bootstrapAlert(data.result);
            });
        });
    });
</script>
</html>