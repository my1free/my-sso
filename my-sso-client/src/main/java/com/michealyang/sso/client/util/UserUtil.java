package com.michealyang.sso.client.util;

import com.michealyang.commons.dto.ThreadContext;
import com.michealyang.sso.access.model.User;

/**
 * Created by michealyang on 17/5/2.
 */
public class UserUtil {
    private static final String MY_USER = "myUser";

    public static User getUser() {
        return (User) ThreadContext.get(MY_USER);
    }

    public static void bind(User user) {
        if (user != null) {
            ThreadContext.put(MY_USER, user);
        }
    }

    public static void unbindUser() {
        ThreadContext.remove(MY_USER);
    }
}
