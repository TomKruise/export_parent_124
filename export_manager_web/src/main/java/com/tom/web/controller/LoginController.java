package com.tom.web.controller;

import com.tom.domain.system.Module;
import com.tom.domain.system.User;
import com.tom.service.system.ModuleService;
import com.tom.service.system.UserService;
import com.tom.utils.Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    //传统登录方式
//    @RequestMapping(path = "/login",name = "用户登录")
//    public String login(String email,String password) {
//        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
//            return "redirect:/login.jsp";
//        }
//
//        User user = userService.findByEmail(email);
//
//        String md5 = Encrypt.md5(password, email);
//
//        if (null == user || !md5.equals(user.getPassword())) {
//            request.setAttribute("error", "Email or password is incorrect!");
//
//            return "forward:/login.jsp";
//        }
//
//        session.setAttribute("loginUser", user);
//
//        List<Module> moduleList = moduleService.findModulesByUser(user);
//
//        session.setAttribute("modules", moduleList);
//
//        return "home/main";
//    }

    //shiro认证方式
    @RequestMapping(path = "/login",name = "用户登录")
    public String login(String email,String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "redirect:/login.jsp";
        }

        String md5 = Encrypt.md5(password, email);

        UsernamePasswordToken token = new UsernamePasswordToken(email, md5);

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            request.setAttribute("error", "Email or password is incorrect");

            return "forward:/login.jsp";
        }

        User user = (User) subject.getPrincipal();

        session.setAttribute("loginUser", user);

        List<Module> moduleList = moduleService.findModulesByUser(user);

        session.setAttribute("modules", moduleList);

        return "home/main";
    }


    //退出
    @RequestMapping(path = "/logout",name="用户登出")
    public String logout(){
        SecurityUtils.getSubject().logout();   //登出
        session.removeAttribute("loginUser");
        return "forward:/login.jsp";
    }

    @RequestMapping(path="/home",name = "后台管理页面")
    public String main(){
        return "home/home";
    }
}
