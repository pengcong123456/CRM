package com.pc.crm.web.filter;

import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : 彭聪
 * @date : 2020-08-05 17:07
 **/
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入字符过滤器");
        req.setCharacterEncoding("UTF-8");
        //设置响应数据格式为JSON

        resp.setContentType("application/json;charset=utf-8");
        filterChain.doFilter(req,resp);
    }
}
