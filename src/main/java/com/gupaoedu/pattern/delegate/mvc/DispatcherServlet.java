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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 仿SpringMvc DispatcherServlet 思想
 */
public class DispatcherServlet extends HttpServlet {

    private List<Handler> handlerMaping = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try {
            Class<?> memberControllerClass = MemeberController.class;
            handlerMaping.add(new Handler().setController(memberControllerClass.newInstance())
                    .setMethod(memberControllerClass.getMethod("getMemeberById"))
                            .setUrl("/web/getMemeberById.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doDispatcher(req, res);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse res) {
        String uri = req.getRequestURI();
        Handler handler = null;
        for (Handler h : handlerMaping) {
            if (uri.equals(h.getUrl())) {
                handler = h;
                break;
            }
        }

        try {
            Object obj = handler.getMethod().invoke(handler.getController(), req.getParameter("uid"));
            res.getWriter().write(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void doDispatcher(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        String uri = req.getRequestURI();
//        String mid = req.getParameter("mid");
//
//        if ("getMemeberById".equals(uri)) {
//            new MemeberController().getMemeberById();
//        } else if ("getOrderById".equals(uri)) {
//            new OrderController().getOrderById();
//        } else if ("logout".equals(uri)) {
//            new SystemController().logout();
//        } else {
//            res.getWriter().write("404 NOT FOUND!!!");
//        }
//    }

    class Handler {
        private Object controller;
        private Method method;
        private String url;

        public Object getController() {
            return controller;
        }

        public Handler setController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Method getMethod() {
            return method;
        }

        public Handler setMethod(Method method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Handler setUrl(String url) {
            this.url = url;
            return this;
        }
    }

}
