<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>

    <h3>新增权限</h3>
</div>
<div class="modal-body">
    <s:form id="permissionForm" enctype="multipart/form-data" class="form-horizontal">
        <div class="control-group ">
            <label class="control-label">权限名称:</label>

            <div class="controls">
                <input type="text" name="permissionName" value=""
                       id="permissionForm_permission_permissionName">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限描述:</label>

            <div class="controls">
                <input type="text" name="permissionDesc" value=""
                       id="permissionForm_permission_permissionDesc">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限URL:</label>

            <div class="controls">
                <input type="text" name="permissionURL" value=""
                       id="permissionForm_permission_permissionURL">
            </div>
        </div>
         <div class="control-group ">
            <label class="control-label">父节点:</label>

            <div class="controls">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </s:form>
</div>
<div class="modal-footer">
    <a href="javascript:void(0);" id="save" class="btn btn-primary">保存</a>
    <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
</div>
<script type="text/javascript">
        var setting = {
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
            } , 
            </c:forEach>

        ];

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
    </script>
<script type="text/javascript">
    $("#save").click(function () {
    	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    	var nodes = treeObj.getSelectedNodes();
    	if(nodes.length<1){
    		alert("请选择一个父节点");
    		return
    	}else if(nodes.length>1){
    		alert("只能选择一个父节点");
    		return
    	}
       $.post("addPermissionSubmit", $("#permissionForm").serialize()+"&parentId="+nodes[0].id, function (data) {
            if (data.result == "true") {
                $('#myModal').modal('hide');
                oTable.fnPageChange('first');
            } else {
                var errors = {
                    fieldErrors: data.fieldErrors,
                    errors: data.actionErrors
                }
                bootstrapValidation($("#permissionForm"), errors);
            }
        });
    });

</script>