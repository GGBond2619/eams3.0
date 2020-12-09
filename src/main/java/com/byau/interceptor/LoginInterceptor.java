package com.byau.interceptor;

import com.byau.domain.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        if (uri.indexOf("/login") >= 0 || uri.indexOf("/css") == 0 || uri.indexOf("/js") == 0 || uri.indexOf("/images") == 0 || uri.indexOf("/fonts") == 0)
            return true;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("USER_SESSION");
        if (user != null)
            return true;
        request.setAttribute("msg", "您还没有登录，请先登录！");
        request.getRequestDispatcher("/login.jsp").forward((ServletRequest) request, (ServletResponse) response);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

