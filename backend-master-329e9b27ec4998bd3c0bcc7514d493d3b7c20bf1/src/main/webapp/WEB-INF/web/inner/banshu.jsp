<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ang="utf-8">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=100" >
    <meta charset="utf-8">
    <title>西游归来</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div id="preloaded-images">
    <img src="/images/login_btn_hover.png" width="1" height="1"/>
    <img src="/images/s-bg-2.jpg" width="1" height="1"/>
    <img src="/images/warning.png" width="1" height="1"/>
</div>
<div class="login">
    <form action="http://192.168.1.100:81/xy_cehua_temp/index.html" method="get">
        <table>
            <tr>
                <td>
                    <label class="login_label"></label>
                    <input type="text" class="login_input" name="usr">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" class="login_btn" value=""/>
                <td>
            </tr>
        </table>
        <input type="hidden" name="server" value="1"/>
        <input type="hidden" name="platform" value="1"/>
        <input type="hidden" name="adult" value="1"/>
        <input type="hidden" name="host" value="120.24.182.80"/>
        <input type="hidden" name="port" value="8000"/>
        <input type="hidden" name="sport" value="8001"/>
        <input id="nowTime" type="hidden" name="time" value="0"/>
        <input type="hidden" name="sign" value="0"/>
        <input type="hidden" name="data" value=""/>
    </form>
    <img src="/images/warning.png" class="warning" border="0"/>
</div>
<script type="text/javascript">
    document.getElementById('nowTime').value = (new Date()).getTime();
</script>
</body>
</html>
