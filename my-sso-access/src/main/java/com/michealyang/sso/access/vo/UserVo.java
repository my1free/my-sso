package com.michealyang.sso.access.vo;

import com.michealyang.sso.access.model.User;

/**
 * Created by michealyang on 17/4/21.
 */
public class UserVo {
    private User user;

    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
