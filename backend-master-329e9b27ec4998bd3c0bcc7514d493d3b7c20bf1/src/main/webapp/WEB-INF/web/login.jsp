<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <link rel="stylesheet" href="../css/bootstrap.css">
    <script src="../js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <style type="text/css">

        .navbar.navbar-static.navbar_as_heading .navbar-inner {
            border-radius: 0 0 0 0;
            margin: -20px -20px 20px;
        }

        .error {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            if (self != top) {
                <%
                String path = request.getContextPath();
                out.println("window.open ('"+path+"/login?type=timeout','_top')");
                %>

            }
            $("#background").css({
                'height': $(window).height() + 'px',
                'width': $(window).width() + 'px'
            });
        })
    </script>
</head>
<body>
<div>
    <div style="height:100%;position:absolute;width:100%;background: none repeat scroll 0 0 rgba(0, 0, 0, 0.4);z-index: 100;"></div>
    <div id="zoomWallpaperGrid"
         style="position:absolute;z-index:-10;top:0;left:0;height:100%;width:100%;background: #000000 url(../images/wallpaper/loading_ernv.jpg) no-repeat 50% 50%"></div>
    <!-- <img id="background" src="static/image/wallpaper/loading_ernv.jpg"> -->
</div>
<div style="position:absolute; top:35%;left:0px; width:100%;z-index:5555">
    <div class="container-fluid" style="margin:10px">
        <div class="row-fluid">
            <div class="well" style="width:576px;margin:auto auto;">
                <div class="navbar navbar-static navbar_as_heading navbar-inverse">
                    <div class="navbar-inner">
                        <div class="container" style="width: auto;">
                            <a class="brand"><spring:message code="systemName"/></a>
                        </div>
                    </div>
                </div>
                <form action="loginSubmit" method="post">
                    <div Class="form-horizontal">  <!--左浮动  -->
                        <div class="control-group ">
                            <label class="control-label"><spring:message code="gccp.login.username"/>:</label>

                            <div class="controls">
                                <input type="text" id="username" name="username"/>
                            </div>
                        </div>
                        <div class="control-group ">
                            <label class="control-label" for="password"><spring:message
                                    code="gccp.login.password"/>:</label>

                            <div class="controls">
                                <input type="password" id="password" name="password"/>
                            </div>
                        </div>

                        <c:if test="${not empty userError}">
                            <div class="error">${userError}</div>
                        </c:if>

                        <input type="submit" value="<spring:message code="gccp.login.submit" />"
                               style="margin-left:180px;"
                               class="btn btn-primary">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>