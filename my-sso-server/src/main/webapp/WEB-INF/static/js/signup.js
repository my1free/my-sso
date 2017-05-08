/**
 * Created by michealyang on 17/4/21.
 */

$(document).ready(function() {
        $("#signup input[name=userName]").focus();
    }
);

$("#signup input[name=userName]").blur(function(){
    var userName = $.trim($("#signup input[name=userName]").val());
    var url = "/sso/user/r/checkDuplicate?userName=" + userName;
    var resp = AjaxProxy.queryByGet(url);
    if(!resp.success){
        DlgProxy.swalSimpleError(resp.msg);
    }
});

$("#signupBtn").click(function(){
    var data = {};
    data = FormValueUtil.getInputValue("signup", data)

    if(data.userName == ""){
        DlgProxy.swalSimpleError("用户名不能为空");
        return;
    }
    if(data.passwd == ""){
        DlgProxy.swalSimpleError("密码不能为空");
        return;
    }
    if(data.repeatPasswd == "" || data.passwd != data.repeatPasswd){
        DlgProxy.swalSimpleError("两次密码输入不一致");
        return;
    }
    if(data.phoneNum == ""){
        DlgProxy.swalSimpleError("手机号不能为空");
        return;
    }

    data.passwd = md5(data.passwd);
    data.repeatPasswd = md5(data.repeatPasswd);

    var url = "/sso/user/w/signup"
    var resp = AjaxProxy.queryByPost(url, data);
    if(!resp.success){
        DlgProxy.swalSimpleError(resp.msg);
        return;
    }
    DlgProxy.swalSimpleSuccess(resp.msg);
    window.setTimeout(function(){
        var loginUrl = $("#loginUrl").val();
        console.log(loginUrl);
        window.location.href = loginUrl;
    }, 2000)
});

$("#cancelBtn").click(function(){
    window.history.back();
});