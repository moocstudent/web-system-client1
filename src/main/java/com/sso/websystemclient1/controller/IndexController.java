package com.sso.websystemclient1.controller;

import com.sso.webssocore.conf.Conf;
import com.sso.webssocore.entity.ReturnT;
import com.sso.webssocore.login.SsoWebLoginHelper;
import com.sso.webssocore.user.WebSsoUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Value("${web.sso.server}")
    private String webSsoServer;
    @Value("${web.sso.logout.path}")
    private String webSsologoutPath;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
        WebSsoUser webSsoUser = (WebSsoUser) request.getAttribute(Conf.SSO_USER);
        model.addAttribute("webSsoUser",webSsoUser);
        return "index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public ReturnT<WebSsoUser> json(Model model,HttpServletRequest request){
        WebSsoUser webSsoUser = (WebSsoUser) request.getAttribute(Conf.SSO_USER);
        ReturnT returnT = new ReturnT(webSsoUser);
        return returnT;
    }
}
