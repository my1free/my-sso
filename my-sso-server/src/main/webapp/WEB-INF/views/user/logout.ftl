<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <title>注销</title>
</head>
<body>
<input id="loginUrl" value="${loginUrl!"/sso/user/r/login"}" hidden>
注销成功。即将跳转到登录页<a href="${loginUrl!"/sso/user/r/login"}">>>></a>
</body>
<script type="application/javascript">
    $(document).ready(function(){
        var loginUrl = $("#loginUrl").val();
        window.setTimeout(function() {
            window.location.href = loginUrl;
        }, 1000)
    })
</script>
