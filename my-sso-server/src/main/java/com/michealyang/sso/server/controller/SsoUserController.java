package com.michealyang.sso.server.controller;

import com.michealyang.commons.dto.Constants;
import com.michealyang.commons.dto.ResultDto;
import com.michealyang.commons.utils.JsonResponseUtil;
import com.michealyang.sso.access.vo.UserVo;
import com.michealyang.sso.service.UserService;
import com.michealyang.sso.service.UserServiceHelper;
import com.michealyang.sso.service.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/sso/user")
public class SsoUserController {
    private static final Logger logger = LoggerFactory.getLogger(SsoUserController.class);

    //固定盐值，就是随便按的字母
    private static final String FIXED_SALT = "ertyujmvfdftyuiknbfyuiknbvfyu";

    @Resource
    private UserService userService;

    @Resource
    private UserServiceHelper userServiceHelper;

    @RequestMapping("/r/signup")
    public String signup(Model model) {
        return "/user/signup";
    }

    @RequestMapping("/r/login")
    public String login(HttpServletResponse response, Model model, String origin) throws IOException {
        logger.info("[login] origin=#{}", origin);
        UserVo userVo = UserUtil.getUser();
        logger.info("[login] userVo=#{}", userVo);
        if(userVo != null && StringUtils.isBlank(origin)){
            response.sendRedirect("/");
        }
        if(StringUtils.isNotBlank(origin) &&  userVo != null) {
            String newOrigin = origin.indexOf("?") == -1 ? origin + "?token=" + userVo.getToken()
                    : "&token=" +  userVo.getToken();
            response.sendRedirect(newOrigin);
        }
        return "/user/login";
    }

    @RequestMapping("/r/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         String origin) throws IOException {
        logger.info("[logout] origin=#{}", origin);
        UserVo userVo = UserUtil.getUser();
        if(userVo != null) {
            userService.clearCache(userVo.getToken());
            UserUtil.unbindUser();
        }
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();
        if(!response.isCommitted()) {
            String targetUrl = "/sso/user/r/login";
            if(StringUtils.isNotBlank(origin)) {
                targetUrl += "?origin=" + origin;
            }
            response.sendRedirect(targetUrl);
        }
        return "";
    }

    /**
     * 根据userId获得盐值
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/r/getSalt")
    public Object getSalt(String userName){
        logger.info("[getSalt] userName=#{}", userName);
        if(StringUtils.isBlank(userName)) {
            return JsonResponseUtil.failureResp("请填写用户名", null);
        }
        String salt = userService.getSaltByUserName(userName);
        if(StringUtils.isBlank(salt)) {
            return JsonResponseUtil.failureResp(Constants.SSO_COMMON_ERR, null);
        }
        return JsonResponseUtil.successResp(Constants.SUCCESS, salt);
    }

    @ResponseBody
    @RequestMapping("/w/signup")
    public Object doSignup(String userName, String passwd,
                           String repeatPasswd, String phoneNum) {
        logger.info("[doSignup] userName=#{}, passwd=#{}, repeatPasswd=#{}, phoneNum=#{}",
                userName, passwd, repeatPasswd, phoneNum);
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)) {
            return JsonResponseUtil.failureResp("请填写用户名和密码", null);
        }
        ResultDto resultDto = userService.doSignup(userName, passwd,
                repeatPasswd, phoneNum);

        if(!resultDto.isSuccess()) {
            return JsonResponseUtil.failureResp(resultDto.getMsg(), resultDto.getData());
        }
        return JsonResponseUtil.successResp(resultDto.getMsg(), resultDto.getData());
    }

    @ResponseBody
    @RequestMapping("/r/doLogin")
    public Object doLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          String userName,
                          String passwd,
                          String origin) throws IOException {
        logger.info("[doLogin] userName=#{}, passwd=#{}, origin=#{}", userName, passwd, origin);
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)) {
            return JsonResponseUtil.failureResp("请填写用户名和密码", null);
        }
        ResultDto resultDto = userService.doLogin(userName, passwd);
        if(!resultDto.isSuccess()) {
            return JsonResponseUtil.failureResp(resultDto.getMsg(), null);
        }
        UserVo userVo = (UserVo) resultDto.getData();
        String token = userServiceHelper.genToken(userVo.getUser().getUserName());
        userVo.setToken(token);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userVo);

        resultDto = userService.cacheUserVo(userVo);
        if(!resultDto.isSuccess()) {
            logger.error("[doLogin] 缓存信息失败");
            return resultDto;
        }
        String newOrigin = "/";
        if(StringUtils.isNotBlank(origin)) {
             newOrigin = origin.indexOf("?") == -1 ? origin + "?token=" + (String) resultDto.getData()
                    : "&token=" + (String) resultDto.getData();
        }

        return JsonResponseUtil.successResp(resultDto.getMsg(), newOrigin);
    }

    @ResponseBody
    @RequestMapping("/r/checkDuplicate")
    public Object checkDuplicate(String userName){
        logger.info("[checkDuplicate] userName=#{}", userName);
        if(StringUtils.isBlank(userName)) {
            return JsonResponseUtil.successResp("用户名为空，不理会", null);
        }
        if(userService.checkUserName(userName)) {
            return JsonResponseUtil.failureResp(Constants.SSO_DUPLICATE_USERNAME_ERR, null);
        }else{
            return JsonResponseUtil.successResp(Constants.SUCCESS, null);
        }
    }
}
