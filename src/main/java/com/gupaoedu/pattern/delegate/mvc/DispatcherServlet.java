package com.gupaoedu.pattern.delegate.mvc;

import com.gupaoedu.pattern.delegate.mvc.controller.MemeberController;
import com.gupaoedu.pattern.delegate.mvc.controller.OrderController;
import com.gupaoedu.pattern.delegate.mvc.controller.SystemController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doDispatcher(req, res);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String uri = req.getRequestURI();
        String mid = req.getParameter("mid");

        if ("getMemeberById".equals(uri)) {
            new MemeberController().getMemeberById();
        } else if ("getOrderById".equals(uri)) {
            new OrderController().getOrderById();
        } else if ("logout".equals(uri)) {
            new SystemController().logout();
        } else {
            res.getWriter().write("404 NOT FOUND!!!");
        }
    }
}
