<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey : "id", // id编号命名   
                    pIdKey : "pId", // 父id编号命名    
                    rootPId : 0  
                }
            }
        };

        var zNodes = [
            <c:forEach items="${allPermissions}" var="p" varStatus="st">
            {
                id:'${p.id}',
                pId:'${p.parentId}',
                name:'${p.permissionDesc}' 
                <c:if test="${p.hasPermission}">,checked: true</c:if>
            } , 
            </c:forEach>

        ];

        /* var zNodes1 = [
            {id: 0, pId: -1, name: '全部平台'},
            <c:forEach items="${platformList}" varStatus="st" var="p">
            {
                id:'${p.id}',
                pId: 0,
                name: '${p.platformName}' <c:if test="${p.hasPermission}">,
                checked: true</c:if>
            },
            </c:forEach>
        ]; */

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
        $("#save").die().live("click", function () {
            var checked = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
            var roleId = ${role.id};
            var permIds = "";
           /*  var platformIds = ""; */
            for (var i = 0; i < checked.length; i++) {
                if (i == checked.length - 1) {
                    permIds = permIds + checked[i].id;
                } else {
                    permIds = permIds + checked[i].id + ",";
                }
            }
            $.post("assignPermissionSubmit",{'roleId':roleId,'permIds':permIds} , function (data) {
            	if (data.result == "true") {
                    $('#myModal').modal('hide');
                    oTable.fnPageChange('first');
                } else {
                    alert("失败！");
                }
            });
        });
    </script>
    <title>Insert title here</title>
</head>
<body>
<div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>
    <h3>分配权限</h3>
</div>
<div class="modal-body">
	<h4>角色名：${role.roleName}</h4>
    <div class="row">
        <div class="span6">
            <div class="well">
                <span class="label label-info">系统权限：</span>
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
        <%-- <div class="span6">
            <div class="well">
                <span class="label label-info">平台权限：</span>
                <ul id="treeDemo1" class="ztree"></ul>
            </div>
        </div> --%>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:void(0);" id="save" class="btn btn-primary">提交</a>
    <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
</div>
</body>
</html>