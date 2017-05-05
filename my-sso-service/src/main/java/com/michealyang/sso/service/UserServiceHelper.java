package com.michealyang.sso.service;

import com.google.common.base.Preconditions;
import com.michealyang.commons.dto.Constants;
import com.michealyang.commons.dto.ResultDto;
import com.michealyang.commons.utils.DateUtil;
import com.michealyang.commons.utils.EncryptUtil;
import com.michealyang.sso.access.model.User;
import com.michealyang.sso.access.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michealyang on 17/4/21.
 */
@Service
public class UserServiceHelper {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceHelper.class);

    public ResultDto checkSignup(String userName,
                                 String passwd,
                                 String repeatPasswd,
                                 String phoneNum) {
        if(StringUtils.isBlank(userName)){
            return new ResultDto(false, "用户名不能为空");
        }
        if(StringUtils.isBlank(passwd) || StringUtils.isBlank(repeatPasswd)){
            return new ResultDto(false, "密码和密码确认不能为空");
        }
        if(!passwd.equals(repeatPasswd)){
            return new ResultDto(false, "密码不一致");
        }
        if(StringUtils.isBlank(phoneNum)){
            return new ResultDto(false, "手机号不能为空");
        }
        return new ResultDto(true, Constants.SUCCESS);
    }

    public ResultDto checkLogin(String userName,
                                 String passwd) {
        if(StringUtils.isBlank(userName)){
            return new ResultDto(false, "用户名不能为空");
        }
        if(StringUtils.isBlank(passwd)){
            return new ResultDto(false, "密码不能为空");
        }

        return new ResultDto(true, Constants.SUCCESS);
    }

    public String genSalt(String userName) {
        //TODO: 修改Salt策略
        return userName + DateUtil.currentSecond().toString();
    }

    /**
     * 生成一个Token
     * <p>token生成规则：</p>
     * <p>userName+当前Unix timestamp，然后计算MD5值</p>
     * @param userName
     * @return
     */
    public String genToken(String userName){
        if(StringUtils.isBlank(userName)) return "";
        return EncryptUtil.MD5(userName + DateUtil.now());
    }

    /**
     * 生成用户缓存和传递给第三方的user信息
     * <p>只包含userName和id</p>
     * @param origin
     * @return
     */
    public UserVo genCachedUserVo(UserVo origin){
        Preconditions.checkArgument(origin != null && origin.getUser() != null);
        UserVo userVo = new UserVo();
        User user = new User();
        user.setId(origin.getUser().getId());
        user.setUserName(origin.getUser().getUserName());
        userVo.setUser(user);
        return userVo;
    }
}
