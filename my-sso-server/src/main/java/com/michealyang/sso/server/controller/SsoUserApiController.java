package com.michealyang.sso.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.michealyang.commons.dto.Constants;
import com.michealyang.commons.utils.JsonResponseUtil;
import com.michealyang.sso.access.vo.UserVo;
import com.michealyang.sso.service.UserService;
import com.michealyang.sso.service.cache.ICache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by michealyang on 17/5/3.
 */
@Controller
@RequestMapping("/api/sso/user")
public class SsoUserApiController {
    private static final Logger logger = LoggerFactory.getLogger(SsoUserController.class);

    @Resource
    private UserService userService;

    @Resource
    private ICache cacheProxy;

    @ResponseBody
    @RequestMapping("/r/auth")
    public Object auth(String token){
        logger.info("[auth] token=#{}", token);
        if(StringUtils.isBlank(token)) {
            return JsonResponseUtil.failureResp(Constants.PERMISSION_DENIED, null);
        }
        JSONObject jsonObject = (JSONObject)cacheProxy.get(token);
        if(jsonObject == null) {
            return JsonResponseUtil.failureResp(Constants.PERMISSION_DENIED, null);
        }
        UserVo userVo = JSON.toJavaObject(jsonObject, UserVo.class);
        if(userVo == null || userVo.getUser() == null){
            return JsonResponseUtil.failureResp(Constants.PERMISSION_DENIED, null);
        }
        return JsonResponseUtil.successResp(Constants.SUCCESS, userVo);
    }
}
