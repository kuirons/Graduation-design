<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="utf-8">
<head>
    <title>内网登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=100">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/main.css" rel="stylesheet">
    <!--[if (gte IE 6)&(lte IE 8)]>
    <script type="text/javascript" src="../../js/jquery-1.3.min.js"></script>
    <script type="text/javascript" src="../../js/selectivizr-min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="form">
        <form id="contactform" action="inner.php" method="get">
            <label>服务器选择：</label>
            <select name="server" class="select-style">${servers}</select><br>

            <fieldset id="inputs">
                <label>账号：</label>
                <input type="text" name="usr" id="username" required>
            </fieldset>

            <div class="operate">
                <input id="r1" name="adult" type="radio" value="1" class="css-checkbox" checked/>
                <label for="r1" class="css-label">成年 </label>
                <input id="r2" name="adult" type="radio" value="2" class="css-checkbox"/>
                <label for="r2" class="css-label">未成年 </label>
            </div>
            <input type="submit" name="submit" value="登录" class="submit_button"/>
        </form>
    </div>
</div>
</body>
</html>