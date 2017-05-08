<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">

    <!-- Animate CSS -->
    <link href="http://cdn.bootcss.com/animate.css/3.5.2/animate.min.css" rel="stylesheet">

<#--Sweet Alert-->
    <link href="http://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.js"></script>
    <script src="http://cdn.bootcss.com/blueimp-md5/2.7.0/js/md5.min.js"></script>

    <link href="/static/css/base.css" rel="stylesheet">


    <title></title>
</head>
<style type="text/css">
    html, body{
        height: 100%;
    }
    body{
        background: url(http://onice2szs.bkt.clouddn.com/122.jpg) no-repeat;
        background-size:100% 100%;
    }
    #loginForm{
        background: white;
        opacity: 0.8;
        border-radius:10px;
        margin-top: 20vh;
        padding: 5vh;
    }

</style>
<body>
<div>
    <form id="loginForm" class="form-horizontal col-md-4 col-md-offset-2">
        <div class="form-group">
            <div class="col-sm-12 text-center">
                <img src="http://onice2szs.bkt.clouddn.com/me-color.png">
                <span class="ft-size-20">账号密码登录</span>
            </div>
        </div>
        <br>
        <div class="form-group">
            <label class="col-sm-3 control-label">用户名</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="userName" placeholder="用户名">
            </div>
        </div>
        <div class="form-group">
            <label for="passwd" class="col-sm-3 control-label">密码</label>
            <div class="col-sm-9">
                <input type="password" class="form-control" name="passwd" placeholder="密码">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button id="loginBtn" type="button" class="btn btn-success col-md-4">登录</button>
                <button id="cancelBtn" type="button" class="btn btn-warning  pull-right col-md-4">取消</button>
            </div>
        </div>
        <div class="form-group">
            <a class="col-lg-offset-10 col-sm-offset-10 col-xs-offset-10" href="${signupUrl!"/sso/user/r/signup"}"><i>注册</i></a>
        </div>
    </form>
</div>
</body>
</html>
<script type="text/javascript" src="/static/js/util.js"></script>
<script type="text/javascript" src="/static/js/login.js"></script>
