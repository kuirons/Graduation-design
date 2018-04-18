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
    <script src="/js/xy-async.js" type="text/javascript"></script>
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
                <a data-toggle="tab" href="#oneGmMail"><spring:message code='gccp.gm.mail.oneTitle'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#superGmMail"><spring:message code='gccp.gm.mail.multiTitle'/></a>
            </li>
            <li class="">
                <a data-toggle="tab" href="#superGmNote"><spring:message code='gccp.gm.note.shortTitle'/></a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="oneGmMail" class="tab-pane fade  active in">
                <h5><spring:message code='gccp.gm.mail.one.desc'/></h5>
                <div class="well">
                    <s:form id="oneMailGmForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.gm.mail.one.account'/>:</label>
                            <div class="controls">
                                <input type="text" id="account" name="account"/>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.gm.mail.content'/>:</label>
                            <div class="controls">
                                <label for="oneMailContent"></label><textarea name="mailContent" cols="" rows=""
                                                                              id="oneMailContent"></textarea>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.gm.mail.reward'/>:</label>
                            <div class="controls">
                                <label for="oneMailReward"></label><textarea name="mailReward" cols="" rows=""
                                                                             id="oneMailReward"></textarea>
                            </div>
                        </div>
                    </s:form>
                    <button id="oneMailAction" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start"/></button>
                </div>
                <div id="oneMailResult" style="width:100%;"></div>
            </div>
            <div id="superGmMail" class="tab-pane fade in">
                <h5><spring:message code='gccp.gm.mail.desc'/></h5>
                <div class="well">
                    <s:form id="mailGmForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.gm.mail.reward'/>:</label>
                            <div class="controls">
                                <label for="mailReward"></label><textarea name="mailReward" cols="" rows=""
                                                                          id="mailReward"></textarea>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.gm.mail.content'/>:</label>
                            <div class="controls">
                                <label for="mailContent"></label><textarea name="mailContent" cols="" rows=""
                                                                           id="mailContent"></textarea>
                            </div>
                        </div>
                    </s:form>
                    <button id="superMailAction" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start"/></button>
                </div>
                <div class="row-fluid">
                    <div id="mailFail" class="span6">
                    </div>
                    <div id="mailSuccess" class="span6">
                    </div>
                </div>
            </div>
            <div id="superGmNote" class="tab-pane fade in">
                <h5><spring:message code='gccp.gm.note.desc'/></h5>
                <div class="well">
                    <s:form id="superGmNoteForm" enctype="multipart/form-data" cssClass="form-horizontal">
                        <div class="control-group ">
                            <label class="control-label"><spring:message code='gccp.gm.note.startTime'/></label>
                            <div class="controls">
                                <div class="input-append date form_datetime endDateTimePicker">
                                    <input type="text" id="startTime" name="startTime" value="0" title="0表示取当前服务器时间"/>
					                <span class="add-on">
					                    <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
					                </span>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.gm.note.interval'/>:</label>
                            <div class="controls">
                                <input type="text" id="noteInterval" name="interval"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.gm.note.times'/>:</label>
                            <div class="controls">
                                <input type="text" id="noteTimes" name="times"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><spring:message code='gccp.gm.note.content'/>:</label>
                            <div class="controls">
                                <label for="noteContent"></label><textarea name="noteContent" cols="" rows=""
                                                                           id="noteContent"></textarea>
                            </div>
                        </div>
                    </s:form>
                    <button id="superNoteAction" style="margin-left:180px;" class="btn btn-primary"><spring:message
                            code="gccp.common.start"/></button>
                </div>
                <div class="row-fluid">
                    <div id="noteFail" class="span6">
                    </div>
                    <div id="noteSuccess" class="span6">
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
    $("#superMailAction").click(function () {
        var servers = selectedServers();
        var mailContent = $("#mailContent").val();
        if (mailContent == null || mailContent == "") {
            bootstrapAlert("<spring:message code='gccp.gm.mail.empty' />");
            return;
        }
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
        } else {
            bootstrapConfirm("<spring:message code='gccp.gm.confirm'/>", function () {
                $.post("sendMail", $("#mailGmForm").serialize() + serverToParamBySelect(servers), function (data) {
                    if (data.errorMessage != "") {
                        bootstrapAlert(data.errorMessage);
                    } else {
                        actionState(data, "mailFail", "mailSuccess");
                    }
                });
            });
        }
    });
    $("#superNoteAction").click(function () {
        var servers = selectedServers();
        var note = $("#noteContent").val();
        if (note == null || note == "") {
            bootstrapAlert("<spring:message code='gccp.gm.note.empty' />");
            return;
        }
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
        } else {
            bootstrapConfirm("<spring:message code='gccp.gm.confirm'/>", function () {
                $.post("sendNote", $("#superGmNoteForm").serialize() + serverToParamBySelect(servers), function (data) {
                    if (data.errorMessage != "") {
                        bootstrapAlert(data.errorMessage);
                    } else {
                        actionState(data, "noteFail", "noteSuccess");
                    }
                });
            });
        }
    });
    $("#oneMailAction").click(function () {
        var servers = selectedServers();
        var note = $("#oneMailContent").val();
        if (note == null || note == "") {
            bootstrapAlert("<spring:message code='gccp.gm.mail.empty' />");
            return;
        }
        if (servers.length == 0) {
            bootstrapAlert("<spring:message code='gccp.common.select.server' />");
            return;
        }
        if (servers.length > 1) {
            bootstrapAlert("<spring:message code='gccp.common.select.server.one' />");
            return;
        }

        bootstrapConfirm("<spring:message code='gccp.gm.confirm'/>", function () {
            $.post("oneMail", $("#oneMailGmForm").serialize() + serverToParamBySelect(servers), function (data) {
                if (data.message != null && data.message != "") {
                    bootstrapAlert(data.message);
                } else {
                    var info = "<div class='well form-inline'>" +
                            createSpan4LabelInput('<spring:message code='gccp.gm.result'/>', data.result) +
                            createSpan4LabelInput('<spring:message code='gccp.gm.mail.one.content'/>', data.content)
                            + "</div>";
                    $("#oneMailResult").empty().html(info);
                }
            });
        });
    });
</script>
</html>