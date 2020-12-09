package com.byau.controller;

import com.byau.dao.IErrorInfoDao;
import com.byau.domain.User;
import com.byau.service.IUserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.deploy.nativesandbox.comm.Response;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IErrorInfoDao errorInfoDao;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String toLogin() {
        System.out.println("===================");
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    public void login(@RequestParam String username,@RequestParam String password, Model model, HttpSession session, HttpServletResponse response) {
        User user = new User(username, password);
        System.out.println(user);
        int msg = 1;
        if (userService.loginIn(user)) {
            System.out.println("12313123123123");
            session.setAttribute("USER_SESSION", user);
            System.out.println(username + password);
            msg = 0;
        }
        try {
            response.getWriter().print(msg);
            System.out.println(msg+"=======================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(username + password);
    }
//@RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
//public String login(User user, Model model, HttpSession session) {
//    System.out.println("0========================");
//    String username = user.getUsername();
//    String password = user.getPassword();
//    System.out.println("1==========================");
//    if (username != null && password != null && this.userService.loginIn(user)) {
//        session.setAttribute("USER_SESSION", user);
//        return "redirect:main";
//    }
//    model.addAttribute("msg", "用户名或密码错误，请重新登录！");
//    return "/login";
//}
    @RequestMapping({"/main"})
    public String toMain() {
        System.out.println("User controller main");
        return "/index";
    }

    @RequestMapping({"/logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @RequestMapping({"/errorinfo"})
    public String insertErrorInfo(String name, String errorinfo) {
        this.errorInfoDao.insertErrorInfo(errorinfo, name);
        return "errorInfo";
    }
}