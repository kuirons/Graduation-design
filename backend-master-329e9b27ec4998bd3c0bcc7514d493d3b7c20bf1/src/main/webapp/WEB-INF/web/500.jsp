<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<link type="image/x-icon" href="../images/favicon.ico" rel="shortcut icon">
<title>500 Internal Server Error</title>
<style type="text/css">
body {
	background-color: #333;
	margin-top: 8px;
	margin-bottom: 2.5em;
}

div.message {
	color: #dd4b39;
	font-family: arial, helvetica, sans-serif;
	width: 250px;
	margin: 1em auto 0 auto;
	overflow: visible;
}

div.message h1 {
	font-size: 16pt;
	font-weight: bold;
	margin: 0;
	white-space: nowrap;
	overflow: visible;
}

div.message p {
	font-size: 10pt;
	margin: 1em 0 1em 0;
	color: #fff;
	line-height: 14pt;
}

div#display {
	position: relative;
	width: 250px;
	margin: 10px auto 10px auto;
	overflow: visible;
	vertical-align: top;
}
</style>
</head>
<body onload="TheSoundOfError.init(&#39;500&#39;);">
	<div class="message">
		<h1>Internal Server Error</h1>
		<p>The server encountered an internal error or misconfiguration
			and was unable to complete your request.</p>
	</div>
	<div id="display">
		<img src="../images/5.gif" id="statusCode0"
			class="statusCode" style="opacity: 1; transform: rotate(0deg);">
		<img src="../images/0.gif" id="statusCode1"
			class="statusCode" style="opacity: 1; transform: rotate(0deg);">
		<img src="../images/0.gif" id="statusCode2"
			class="statusCode" style="opacity: 1; transform: rotate(0deg);"><br>
	</div>
</body>
</html>