<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=88907251c6b5e78a755f6d0600f8b810"></script>

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

    <title>注册</title>
</head>
<style type="text/css">
    html, body{
        height: 100%;
    }
    body{
        background: url(http://onice2szs.bkt.clouddn.com/122.jpg) no-repeat;
        background-size:100% 100%;
    }
    #signup{
        background: white;
        opacity: 0.8;
        border-radius:10px;
        margin-top: 20vh;
        padding: 5vh;
    }

</style>
<body>
<div>
    <input id="loginUrl" value="${loginUrl!"/sso/user/r/login"}" class="hidden">
    <form id="signup" class="form-horizontal col-md-4 col-md-offset-2">
        <div class="form-group">
            <label class="col-sm-3 control-label">用户名</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="userName" placeholder="用户名">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">密码</label>
            <div class="col-sm-9">
                <input type="password" class="form-control" name="passwd" placeholder="密码">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">确认密码</label>
            <div class="col-sm-9">
                <input type="password" class="form-control" name="repeatPasswd" placeholder="确认密码">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">手机号</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="phoneNum" placeholder="手机号">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button type="button" id="signupBtn" class="btn btn-success col-md-4">确认</button>
                <button type="button" id="cancelBtn" class="btn btn-warning  pull-right col-md-4">取消</button>
            </div>
        </div>
        <div class="form-group">
            <a class="col-lg-offset-8 col-sm-offset-8 col-xs-offset-7" href="${loginUrl!"/sso/user/r/login"}"><i>已有账号？登录</i></a>
        </div>
    </form>
</div>
</body>
</html>

<script type="text/javascript" src="/static/js/util.js"></script>
<script type="text/javascript" src="/static/js/signup.js"></script>
