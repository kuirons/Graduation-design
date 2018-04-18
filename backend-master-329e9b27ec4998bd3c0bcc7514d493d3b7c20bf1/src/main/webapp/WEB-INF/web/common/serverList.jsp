<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/js/tree/css/zTreeStyle.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/bootstrap-datetimepicker.min.css">
    <script src="/js/tree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
    <script src="/js/moment.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"
            charset="UTF-8"></script>
    <title></title>
    <style>
        .mywell {
            padding: 5px
        }

        .input-small {
            margin-bottom: 1px !important
        }
    </style>
</head>
<div class="row-fluid">
    <button id="quick" style="width:100%" class="btn btn-mini"><i id="icon" class="icon-chevron-down"></i></button>
    <div id="pane" class="well mywell">
        <select id="sn">
            <c:forEach items="${platform}" var="p">
                <option value="${p.value}">${p.name}</option>
            </c:forEach>
        </select>
        <input class="input-small" type="text" id="text"/>
        <button id="check" class="btn">勾选</button>
    </div>
</div>
<ul id="treeDemo" class="ztree"></ul>

<script type="text/javascript">
    var servers = new Array();
    $("#check").die().live("click", function () {

        var ids = $.trim($("#text").val());
        if (ids != "") {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            if (!zTree.setting.check.enable) {
                var parentNode = zTree.getNodeByParam("id", "p_" + $("#sn").val(), null);
                onClick(null, null, zTree.getNodeByParam("serverId", ids, parentNode));
            } else {
                zTree.checkAllNodes(false);
                if (zTree.setting.check.enable) {
                    var parentNode = zTree.getNodeByParam("id", "p_" + $("#sn").val(), null);
                    var id = ids.split(",");
                    for (var i = 0; i < id.length; i++) {
                        var to = new Array();
                        to = id[i].split("-");
                        if (to.length == 1) {
                            var node = zTree.getNodeByParam("serverId", id[i], parentNode);
                            if (node != null) {
                                zTree.checkNode(node, true, true, false);
                            }
                        } else if (to.length == 2) {
                            var start = Number(to[0]);
                            var end = Number(to[1]);
                            for (var j = start; j <= end; j++) {
                                var node = zTree.getNodeByParam("serverId", j, parentNode);
                                if (node != null) {
                                    zTree.checkNode(node, true, true, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    });
    $("#quick").die().live("click", function () {

        if ($("#pane")[0].style.display == "none") {
            $("#pane").slideDown();
            $("#icon").removeClass("icon-chevron-down");
            $("#icon").addClass("icon-chevron-up");
        } else {
            $("#pane").slideUp();
            $("#icon").removeClass("icon-chevron-up");
            $("#icon").addClass("icon-chevron-down");
        }
    });

    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            beforeClick: beforeClick
        }

    };
    var zNodes = [
        <c:forEach items="${gameList}" var="p" varStatus="st">
        {id:${p.id}, open: true, pId: -1, name: '${p.gameName}', type: 'game'},
        </c:forEach>
        <c:forEach items="${platform}" var="p" varStatus="st">
        {id: 'p_' +${p.value}, pId: 1, name: '${p.name}'},
        </c:forEach>
        <c:forEach items="${server}" var="p" varStatus="st">
        {
            id: 9999,
            serverId:${p.serverId},
            pId: 'p_' + ${p.platformId},
            platformId:${p.platformId},
            name: '${p.serverName}'
        },
        </c:forEach>
    ];
    function beforeClick(treeId, treeNode, clickFlag) {
        return clickFlag != 2;
    }
    function onClick(e, treeId, treeNode) {

        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
        if (typeof(treeNode.serverId) != "undefined") {
            servers.pop();
            servers.push({serverId: treeNode["serverId"], platformId: treeNode["platformId"]});
            zTree.checkNode(treeNode, true, true, false);
        } else {
            servers.pop();
        }
    }

    $('#text').popover({
        title: "使用说明",
        content: "输入要勾选的区服ID，格式为：1,2,3 或 1-10",
        placement: "bottom",
        trigger: "manual"
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            $("#check").trigger("click");
        }
    }).focus(function () {
        $("#text").popover('show');
    }).blur(function () {
        $("#text").popover('hide');
    });

    $(document).ready(function () {
        $("#sn").select2();
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var checkAll = $.cookie('checkAll');
        if (checkAll == "true") {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var node = zTree.getNodeByParam("type", "game", null);
            zTree.checkNode(node, true, true, false);
        }

        $('body').layout({
            west__size: 300
        });
        <!-- 设置日期 -->
        $('.input-daterange').datepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: "linked"
        });
        <!-- 处理日期, 开始日期和截止日期设置为今天, 设置提示信息 -->
        $('.yesterday').datepicker("setDate", new Date).prop("title", "<spring:message code="gccp.time.yesterday"/>");
        $('.today').datepicker("setDate", new Date).prop("title", "<spring:message code="gccp.time.today"/>");

        $('.startDateTimePicker').datetimepicker({
            language: "ch",
            format: "yyyy-MM-dd hh:mm:ss"
        });
        $('.startDateTimePicker input[name=startTime]').val(moment().startOf("day").subtract(1, "days").format("YYYY-MM-DD HH:mm:ss").toString());

        $('.endDateTimePicker').datetimepicker({
            language: "ch",
            format: "yyyy-MM-dd hh:mm:ss"
        });
        $('.endDateTimePicker input[name=endTime]').val(moment().format("YYYY-MM-DD HH:mm:ss"));
    });
    function selectedServers() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if (zTree.setting.check.enable) {
            var checkedNodes = zTree.getCheckedNodes(true);
            servers = new Array();
            for (var key in checkedNodes) {
                var obj = checkedNodes[key];
                if (obj["serverId"] == null || obj["serverId"] == "") continue;
                servers.push({serverId: obj["serverId"], platformId: obj["platformId"]});
            }
        }
        return servers;
    }

    function serverToParam() {
        var servers = selectedServers();
        var serParam = "&server=";
        for (var i = 0; i < servers.length; i++) {
            serParam += servers[i].platformId + "," + servers[i].serverId;
            if (i < servers.length - 1) {
                serParam += ";";
            }
        }
        return serParam;
    }

    function serverToParamBySelect(selectServers) {
        var serParam = "&server=";
        for (var i = 0; i < selectServers.length; i++) {
            serParam += selectServers[i].platformId + "," + selectServers[i].serverId;
            if (i < selectServers.length - 1) {
                serParam += ";";
            }
        }
        return serParam;
    }

    function getYesterday() {
        var yesterday = new Date();
        yesterday.setDate(yesterday.getDate() - 1);
        return yesterday;
    }
</script>
