<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script>
        $("#save").click(function () {
            $.post("editRoleSubmit", $("#roleForm").serialize(), function (data) {
                if (data.result == "true") {
                    $('#myModal').modal('hide');
                    oTable.fnPageChange('first');
                } else {
                    var errors = {
                        fieldErrors: data.fieldErrors,
                        errors: data.actionErrors
                    };
                    bootstrapValidation($("#roleForm"), errors);
                }
            });
        });
    </script>
</head>
<body>
<div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>

    <h3>修改用户</h3>
</div>
<div class="modal-body">
    <s:form id="roleForm" enctype="multipart/form-data" cssClass="form-horizontal">
        <input type="hidden" name="id" value="${role.id}" id="userForm_user_id">

        <div class="control-group ">
            <label class="control-label">角色名:</label>

            <div class="controls">
                <input type="text" name="roleName" value="${role.roleName}"
                       id="userForm_user_username" readonly="readonly">
                <c:if test="${not empty usernameError}">
                    <div class="error">${usernameError}</div>
                </c:if>
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">角色描述:</label>

            <div class="controls">
                <input type="text" name="description" value="${role.description }" id="userForm_user_password">
            </div>
        </div>
    </s:form>
</div>
<div class="modal-footer">
    <a href="javascript:void(0);" id="save" class="btn btn-primary">提交</a>
    <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
</div>
</body>
</html>