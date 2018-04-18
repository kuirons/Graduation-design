<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script>
        $(document).ready(function () {
            $("#s1").select2();
        });
        $("#save").click(function () {
            $.post("addUserSubmit", $("#userForm").serialize(), function (data) {
                if (data.result == "true") {
                    $('#myModal').modal('hide');
                    oTable.fnPageChange('first');
                } else {
                    var errors = {
                        fieldErrors: data.fieldErrors,
                        errors: data.actionErrors
                    };
                    bootstrapValidation($("#userForm"), errors);
                }
            });
        });
    </script>
</head>
<body>
<div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>
    <h3>新增用户</h3>
</div>
<div class="modal-body">
    <s:form id="userForm" enctype="multipart/form-data" cssClass="form-horizontal">
        <div class="control-group ">
            <label class="control-label">用户名:</label>

            <div class="controls">
                <input type="text" name="user.username" value="" id="userForm_user_username">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">密码:</label>

            <div class="controls">
                <input type="password" name="user.password" id="userForm_user_password">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">确认密码:</label>

            <div class="controls">
                <input type="password" name="confirmPassword" id="userForm_confirmPassword">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">角色:</label>

            <div class="controls">
                <select name="user.roleId" id="s1" tabindex="-1" class="select2-offscreen">
                    <option value="-1">请选择角色</option>
                	<c:forEach var="role" items="${roleList }">
                		<option value="${role.id }">${role.roleName }</option>
                	</c:forEach>
                </select>
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