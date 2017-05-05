package com.michealyang.sso.server.filter;

import com.michealyang.sso.access.vo.UserVo;
import com.michealyang.sso.service.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by michealyang on 16/3/20.
 */
@Component
public class UserFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserFilter.class);

    private static final String LOGIN_URI = "/sso/user/r/login";
    private static final String SIGNUP_URI = "/sso/user/r/signup";
    private static final String LOGOUT_URI = "/sso/user/logout";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        logger.info("[doFilterInternal] uri=#{}", uri);

        UserUtil.unbindUser();

        //1. 对于静态资源，要通过
        //2. 对于API接口，要通过
        if(uri.startsWith("/static/")
                || uri.startsWith("/api/")){
            filterChain.doFilter(request, response);
            return;
        }

        if("/favicon.ico".equals(uri)){
            filterChain.doFilter(request, response);
            return;
        }

        //3. 对于Ajax请求，要通过
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        logger.info("sessionId=#{}", session.getId());
        UserVo userVo = (UserVo)WebUtils.getSessionAttribute(request, "user");
        logger.info("[doFilterInternal] userVo=#{}", userVo);
        if ((!uri.equals(LOGIN_URI) && !uri.equals(SIGNUP_URI) && !uri.equals(LOGOUT_URI))
                && (userVo == null || userVo.getUser() == null)) {
            response.sendRedirect(LOGIN_URI);
        }

        UserUtil.bind(userVo);

        filterChain.doFilter(request, response);
    }
}
