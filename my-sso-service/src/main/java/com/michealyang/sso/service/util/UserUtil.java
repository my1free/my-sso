package com.michealyang.sso.service.util;

import com.michealyang.commons.dto.ThreadContext;
import com.michealyang.sso.access.vo.UserVo;

/**
 * Created by michealyang on 17/5/2.
 */
public class UserUtil {
    private static final String MY_USER = "myUser";

    public static UserVo getUser() {
        return (UserVo) ThreadContext.get(MY_USER);
    }

    public static void bind(UserVo userVo) {
        if (userVo != null && userVo.getUser() != null) {
            ThreadContext.put(MY_USER, userVo);
        }
    }

    public static void unbindUser() {
        ThreadContext.remove(MY_USER);
    }
}
