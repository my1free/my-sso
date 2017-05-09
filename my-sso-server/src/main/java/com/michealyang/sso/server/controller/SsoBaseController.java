package com.michealyang.sso.server.controller;

import com.michealyang.sso.access.model.User;
import com.michealyang.sso.access.vo.UserVo;
import com.michealyang.sso.service.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SsoBaseController {
    private static final Logger logger = LoggerFactory.getLogger(SsoBaseController.class);

    @RequestMapping("/")
    public String index(Model model){
        logger.info("[index] index");
        UserVo userVo = UserUtil.getUser();
        if(userVo == null) {
            userVo = new UserVo();
            User user = new User();
            user.setId(0);
            user.setUserName("未知");
            userVo.setUser(user);
        }
        model.addAttribute("id", userVo.getUser().getId());
        model.addAttribute("userName", userVo.getUser().getUserName());
        return "index";
    }

    @RequestMapping("/error/404")
    public String index404(Model model){
        logger.info("[index404]");
        return "404";
    }

    @RequestMapping("/error/500")
    public String index500(Model model){
        logger.info("[index500]");
        return "500";
    }
}
