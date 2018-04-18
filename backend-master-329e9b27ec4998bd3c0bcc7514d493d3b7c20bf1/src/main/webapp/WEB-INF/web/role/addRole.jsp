<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
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
            $.post("addRoleSubmit", $("#roleForm").serialize(), function (data) {
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

    <h3>新增角色</h3>
</div>
<div class="modal-body">
    <s:form id="roleForm" enctype="multipart/form-data" cssClass="form-horizontal" commandName="role">
        <div class="control-group ">
            <label class="control-label">角色名:</label>

            <div class="controls">
                <label for="roleForm_role_roleName"></label>
                <input type="text" name="role.roleName" value="" id="roleForm_role_roleName">
            </div>
        </div>
        <div class="control-group ">
            <label class="control-label">角色描述:</label>

            <div class="controls">
                <label for="roleForm_role_description"></label>
                <input type="text" name="role.description" value="" id="roleForm_role_description">
				</div>
        </div>
        <div class="control-group ">
            <label class="control-label">权限拷贝自:</label>

            <div class="controls">
                <s:select path="id" items="${roleList}" id="s1" itemLabel="roleName" itemValue="id"/>
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