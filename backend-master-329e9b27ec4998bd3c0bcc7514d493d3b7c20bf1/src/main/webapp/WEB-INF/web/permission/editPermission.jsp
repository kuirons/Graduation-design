<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>

    <h3>修改权限</h3>
</div>
<div class="modal-body">
    <s:form id="permissionForm" enctype="multipart/form-data" cssClass="form-horizontal">
        <input type="hidden" name="permission.id"/>

        <div class="control-group ">
            <label class="control-label">父节点:</label>

            <div class="controls">
                <label for="permissionForm_permission_parentId"></label>
                <select name="permission.parentId"
                        id="permissionForm_permission_parentId"
                        style="width:auto;">
                    <c:set var="parentId" value="${parentId}"/>
                    <c:forEach items="${permissionList}" var="p" varStatus="st">
                        <option value="${p.id}"
                                <c:if test="p.id == parentId">selected="selected"</c:if>>${p.permissionDesc}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限名称:</label>

            <div class="controls">
                <input type="text" name="permission.permissionName" value="${permissionName}"
                       id="permissionForm_permission_permissionName">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限描述:</label>

            <div class="controls">
                <input type="text" name="permission.permissionDesc" value="${permissionDesc}"
                       id="permissionForm_permission_permissionDesc">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限URL:</label>

            <div class="controls">
                <input type="text" name="permission.permissionURL" value="${permissionUrl}"
                       id="permissionForm_permission_permissionURL">
            </div>
        </div>


    </s:form>
</div>
<div class="modal-footer">
    <a href="javascript:void(0);" id="save" class="btn btn-primary">保存</a>
    <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
</div>
<script type="text/javascript">
    $("#save").click(function () {
        $.post("editPermissionSubmit", $("#permissionForm").serialize(), function (data) {
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