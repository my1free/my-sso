/**
 * Created by michealyang on 17/4/21.
 */
$(document).ready(function() {
        $("#loginForm input[name=userName]").focus();
    }
);

$("#loginBtn").click(function(){
    var data = {};
    data = FormValueUtil.getInputValue("loginForm", data)

    if(data.userName == ""){
        DlgProxy.error("用户名不能为空");
        return;
    }
    if(data.passwd == ""){
        DlgProxy.error("密码不能为空");
        return;
    }

    data.passwd = md5(data.passwd);

    //获得origin
    var origin;
    var href = window.location.href;
    var originIdx = href.indexOf("origin=");
    var ampersandIdx = href.indexOf("&", originIdx);
    if(originIdx != -1){
        if(ampersandIdx == -1){
            origin = href.substring(originIdx + 7, href.length);
        }else{
            origin = href.substring(originIdx + 7, ampersandIdx);
        }
        data.origin = origin;
    }

    var url = "/sso/user/r/doLogin";
    var resp = AjaxProxy.queryByPost(url, data);
    if(!resp.success){
        DlgProxy.error(resp.msg);
        return;
    }
    var redirect = resp.data;
    if(redirect != null){
        window.location.href = redirect;
        return;
    }
    DlgProxy.swalSimpleSuccess(resp.msg);
});

$("#cancelBtn").click(function(){
    window.history.back();
});

$("#loginForm input[name=userName]").focus(function(){
    KeyboardUtil.bindEnter($("#loginBtn"));
});

$("#loginForm input[name=userName]").blur(function(){
    KeyboardUtil.unbindEnter();
});

$("#loginForm input[name=passwd]").focus(function(){
    KeyboardUtil.bindEnter($("#loginBtn"));
});

$("#loginForm input[name=passwd]").blur(function(){
    KeyboardUtil.unbindEnter();
});