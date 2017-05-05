package com.michealyang.sso.service;

import com.google.common.base.Preconditions;
import com.michealyang.commons.dto.Constants;
import com.michealyang.commons.dto.ResultDto;
import com.michealyang.commons.utils.EncryptUtil;
import com.michealyang.commons.utils.ResultUtil;
import com.michealyang.sso.access.dao.UserDao;
import com.michealyang.sso.access.model.User;
import com.michealyang.sso.access.vo.UserVo;
import com.michealyang.sso.service.cache.ICache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by michealyang on 17/4/21.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final int expire = 24 * 3600 * 3;

    @Resource
    private UserDao userDao;
    @Resource
    private UserServiceHelper userServiceHelper;
    @Resource
    private ICache cacheProxy;

    public boolean checkUserName(String userName) {
        if(StringUtils.isBlank(userName)) {
            return false;
        }
        return userDao.getUserNameNum(userName) > 0;
    }

    public String getSaltByUserName(String userName) {
        //TODO: check param
        return userDao.getSaltByUserName(userName);
    }

    public UserVo getUserByUserName(String userName) {
        //TODO: check param
        User user = userDao.getUserByUserName(userName);
        if(user == null) return null;
        UserVo userVo = new UserVo();
        userVo.setUser(user);
        return userVo;
    }

    public ResultDto doSignup(String userName,
                              String passwd,
                              String repeatPasswd,
                              String phoneNum) {
        logger.info("[doSingup] userName=#{}, passwd=#{}, repeatPasswd=#{}, phoneNum=#{}",
                userName, passwd, repeatPasswd, phoneNum);
        ResultDto resultDto = userServiceHelper.checkSignup(userName,
                passwd, repeatPasswd, phoneNum);
        if(!resultDto.isSuccess()) return resultDto;
        String salt = userServiceHelper.genSalt(userName);

        User user = new User();
        user.setUserName(userName);
        user.setPasswd(EncryptUtil.MD5(passwd + salt));
        user.setPhoneNum(phoneNum);
        user.setSalt(salt);
        logger.info("**** SUCCESS *****");

        try {
            if(userDao.insert(user) <= 0) {
                return new ResultDto(false, Constants.SYS_FAILURE);
            }
        }catch (DataAccessException e){
            logger.error("[doSiginup] e=#{}", e);
            return new ResultDto(false, Constants.SSO_DUPLICATE_USERNAME_ERR);
        }catch (Exception e){
            logger.error("[doSiginup] e=#{}", e);
            return new ResultDto(false, Constants.SYS_FAILURE);
        }

        return new ResultDto(true, Constants.SUCCESS);
    }

    public ResultDto doLogin(String userName, String passwd) {
        logger.info("[doLogin] userName=#{}, passwd=#{}", userName, passwd);
        ResultDto resultDto = userServiceHelper.checkLogin(userName,
                passwd);
        if(!resultDto.isSuccess()) return resultDto;
        UserVo userVo = getUserByUserName(userName);
        if(userVo == null || userVo.getUser() == null) {
            logger.warn("[doLogin] 用户名不存在。userName=#{}", userName);
            return ResultUtil.genFailResult(Constants.SSO_USERNAME_ERR, userName);
        }

        User user = userVo.getUser();
        if(!user.getPasswd().equals(EncryptUtil.MD5(passwd + user.getSalt()))){
            logger.warn("[doLogin] 密码不正确。userName=#{}", userName);
            return ResultUtil.genFailResult(Constants.SSO_PASSWD_ERR, userName);
        }

        return new ResultDto(true, Constants.SUCCESS, userVo);
    }

    public ResultDto cacheUserVo(UserVo userVo){
        Preconditions.checkArgument(userVo != null && userVo.getUser() != null);
        int retryTimes = 3;
        boolean success = true;
        UserVo cachedUserVo = userServiceHelper.genCachedUserVo(userVo);
        while (retryTimes-- > 0){
            if(cacheProxy.setex(userVo.getToken(), cachedUserVo, expire)) {
                success = true;
                break;
            }
            success = false;
        }
        if(!success) {
            return ResultUtil.genFailResult(Constants.FAILURE, null);
        }
        return ResultUtil.genSuccessResult(Constants.SUCCESS, userVo.getToken());
    }

    public boolean clearCache(String token){
        if(StringUtils.isBlank(token)) return false;
        return cacheProxy.delete(token);
    }

}
