<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="utf-8">
<head>
    <title>重启</title>
    <meta http-equiv="X-UA-Compatible" content="IE=100">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/main.css" rel="stylesheet">
    <link href="../../css/result.css" rel="stylesheet">
    <script type="text/javascript" src="../../js/jquery-1.3.min.js"></script>
    <!--[if (gte IE 6)&(lte IE 8)]>
    <script type="text/javascript" src="../../js/selectivizr-min.js"></script>
    <![endif]-->
</head>
<body>
<div id="loading" style="z-index:1;padding:5px;position:fixed;width:90px;display: none;"><img src="../../images/loading.gif"></div>
<div class="container">
    <div class="form">
        <form id="contactform">
            <label>服务器选择：</label>
            <select name="server" class="select-style">${servers}</select>

            <div class="operate">
                <input id="r1" name="type" type="radio" value="1" class="css-checkbox" checked/>
                <label for="r1" class="css-label">更新&重启 </label>
                <input id="r2" name="type" type="radio" value="2" class="css-checkbox"/>
                <label for="r2" class="css-label">更新 </label>
                <input id="r3" name="type" type="radio" value="3" class="css-checkbox"/>
                <label for="r3" class="css-label">重启 </label>
            </div>
            <input id="submit_button" type="button" name="submit" value="执行" class="submit_button"/>
        </form>
    </div>
</div>
<div id="result" class="grey"></div>
</body>
<script type="text/javascript">
    $("#submit_button").click(function () {
        var toData = $("#contactform").serialize();
        $("#result").empty();
        $("#result").removeClass("basic-grey");
        $("#loading").show();
        $.ajax({
            url: "postRestart.php",
            type: "POST",
            dataType: "json",
            data: toData,
            success: function (data) {
                jQuery("#loading").fadeOut();

                var r = "<label><span>用时【" + data.useTime + "毫秒】</span></label><br/><textarea id='message' name='message'>" + data.result + "</textarea>";
                $("#result").empty().html(r);
                $("#result").addClass("basic-grey");
            }
        });
    });
</script>
</html>