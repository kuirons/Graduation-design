<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link href="/css/demo_table_jui.css" rel="stylesheet">
    <link href="/css/jquery-ui-1.8.16.custom.css" rel="stylesheet">
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/select2.css">
</head>
<body>
<input type="hidden" name="permissionId" value="${permissionId}" id="permissionId">

<div style="margin-top:20px;margin-left: auto;margin-right: auto;width:98%">
    <table id="meterDataExp" width="100%" class="display">
        <thead>
        <tr>
            <th width="16%">编号</th>
            <th width="15%">父节点编号</th>
            <th width="15%">权限名称</th>
            <th width="15%">权限描述</th>
            <th width="25%">操作</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<div class="modal hide fade" id="myModal"></div>
<div style="text-align: center;">
    <button class='btn btn-primary' id="return"><i class="icon-chevron-left icon-white"></i>返回</button>
</div>
</body>
<script src="/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="/js/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="/js/select2/select2.min.js" type="text/javascript"></script>
<script src="/js/my-common.js" type="text/javascript"></script>
<script type="text/javascript">
    oTable = $('#meterDataExp').dataTable({
        "bProcessing": true,
        "bJQueryUI": true,
        "bSort": false,
        "bFilter": false,
        "sPaginationType": "full_numbers",
        "sAjaxSource": "listChildPermission?permissionId=" + $("#permissionId").val(),
        "bServerSide": true,
        "sDom": '<"H"lr>t<"F"ip>',
        "fnServerData": retrieveData,
        "fnRowCallback": rowCallback,
        "aoColumns": [{
            "mDataProp": "id",
            "bVisible": false
        }, {
            "mDataProp": "parentId",
            "sClass": "center"
        }, {
            "mDataProp": "permissionName",
            "sClass": "center"
        }, {
            "mDataProp": "permissionDesc",
            "sClass": "center"
        }, {
            "mData": null,
            "sClass": "center"
        }],
        "oLanguage": {
            "oPaginate": {
                "sFirst": "<spring:message code='gccp.pagination.first' />",
                "sLast": "<spring:message code='gccp.pagination.last' />",
                "sNext": "<spring:message code='gccp.pagination.next' />",
                "sPrevious": "<spring:message code='gccp.pagination.previous' />",
            },
            "sInfo": "<spring:message code='gccp.pagination.info' />",
            "sEmptyTable": "<spring:message code='gccp.pagination.empty' />",
            "sInfoEmpty": "<spring:message code='gccp.pagination.empty' />",
            "sLengthMenu": "<spring:message code='gccp.pagination.length' />",
            "sLoadingRecords": "<spring:message code='gccp.pagination.loading' />",
            "sProcessing": "<spring:message code='gccp.pagination.loading' />",
            "sSearch": "<spring:message code='gccp.pagination.search' />"
        }
    });
    $('.dataTables_length').css("float", "right");
    $('.dataTables_length').parent().append("<div style='float:left;'><button class='btn btn-primary' id='newPermission'><i class='icon-user icon-white'></i>新增权限</button></div>");
    function rowCallback(nRow, aData, iDisplayIndex) {
        $('td:eq(3)', nRow).html("<button value=" + aData.id + " name='childPermission' class='btn btn-primary'><i class='icon-list icon-white'></i>查看子权限</i></button><button value=" + aData.id + " class='btn btn-info'><i class='icon-edit icon-white'></i>编辑</i></button><button value=" + aData.id + " class='btn btn-danger'><i class='icon-trash icon-white'></i>删除</i></button>");
    }
    function retrieveData(sSource, aoData, fnCallback) {
        var pageSize = fnGetKey(aoData, "iDisplayLength");
        var startRow = fnGetKey(aoData, "iDisplayStart");
        var echo = fnGetKey(aoData, "sEcho");
        $.ajax({
            "type": "POST",
            "url": sSource,
            "dataType": "json",
            "data": {
                pageSize: pageSize,
                startRow: startRow,
                echo: echo
            },
            "success": function (resp) {
                fnCallback($.parseJSON(resp.result));
            }
        });
    }
    function fnGetKey(aoData, sKey) {
        for (var i = 0, iLen = aoData.length; i < iLen; i++) {
            if (aoData[i].name == sKey) {
                return aoData[i].value;
            }
        }
        return null;
    }

    $("#newPermission").click(function () {
        $.get("addPermission", function (data) {
            $("#myModal").html(data);
            $('#myModal').modal({
                backdrop: 'static'
            });
        });
    });
    $(".btn-danger").die().live("click", function () {
        var id = $(this).val();

        function callback() {
            $.get("deletePermission?permissionId=" + id, function (data) {
                if (data.result == "true") {
                    oTable.fnPageChange('first');
                } else {
                    alert("失败！");
                }
            });
        }

        bootstrapConfirm("确定删除吗？", callback);
    });
    $(".btn-info").die().live("click", function () {
        var id = $(this).val();
        $.get("editPermission?permissionId=" + id, function (data) {
            $("#myModal").html(data);
            $('#myModal').modal({
                backdrop: 'static'
            });
        });
    });
    $("button[name='childPermission']").die().live("click", function () {
        var id = $(this).val();
        location.href = "childPermissionManage?permissionId=" + id;
    });
    $("#return").die().live("click", function () {
    	window.history.go(-1);
        /* location.href = "permissionManage"; */
    });
</script>
</html>