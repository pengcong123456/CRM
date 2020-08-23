package com.pc.crm.web.filter;

import com.pc.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : 彭聪
 * @date : 2020-08-05 17:37
 **/
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
/*
        System.out.println("进入登录过滤器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                chain.doFilter(req, response);
            } else {
                //重定向
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
*/
        chain.doFilter(req, resp);
    }
}
