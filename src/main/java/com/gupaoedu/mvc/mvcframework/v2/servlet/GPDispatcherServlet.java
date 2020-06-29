package com.gupaoedu.mvc.mvcframework.v2.servlet;

import com.gupaoedu.mvc.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class GPDispatcherServlet extends HttpServlet {

    /** 存储application.properties的配置内容 */
    private Properties contextConfig = new Properties();

    /** 存储所有扫描到的类 */
    private List<String> classNames = new ArrayList<>();

    /** IOC容器，保存所有实例对象。注册式单例 */
    private Map<String, Object> ioc = new HashMap<>();

    /** 保存Controller中所有Mapping的对应关系 */
    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.调用，运行阶段
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url.replaceAll(contextPath, ""). replaceAll("/+", "/");

        if (!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 Not Found!!!");
            return;
        }
        Method method = this.handlerMapping.get(url);
        Map<String, String[]> paramMap = req.getParameterMap();
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName), new Object[]{req, resp, paramMap.get("name")[0]});
    }

//    private void doDispatch(HttpServletRequest req, HttpServletResponse resp)throws Exception {
//        String url = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
//        if(!this.handlerMapping.containsKey(url)){
//            resp.getWriter().write("404 Not Found!!");
//            return;
//        }
//
//        Method method = this.handlerMapping.get(url);
//        //第一个参数：方法所在的实例
//        //第二个参数：调用时所需要的实参
//        Map<String,String[]> params = req.getParameterMap();
//        //获取方法的形参列表
//        Class<?> [] parameterTypes = method.getParameterTypes();
//        //保存请求的url参数列表
//        Map<String,String[]> parameterMap = req.getParameterMap();
//        //保存赋值参数的位置
//        Object [] paramValues = new Object[parameterTypes.length];
//        //按根据参数位置动态赋值
//        for (int i = 0; i < parameterTypes.length; i ++){
//            Class parameterType = parameterTypes[i];
//            if(parameterType == HttpServletRequest.class){
//                paramValues[i] = req;
//                continue;
//            }else if(parameterType == HttpServletResponse.class){
//                paramValues[i] = resp;
//                continue;
//            }else if(parameterType == String.class){
//
//                //提取方法中加了注解的参数
//                Annotation[] [] pa = method.getParameterAnnotations();
//                for (int j = 0; j < pa.length ; j ++) {
//                    for(Annotation a : pa[i]){
//                        if(a instanceof GPRequestParam){
//                            String paramName = ((GPRequestParam) a).value();
//                            if(!"".equals(paramName.trim())){
//                                String value = Arrays.toString(parameterMap.get(paramName))
//                                        .replaceAll("\\[|\\]","")
//                                        .replaceAll("\\s",",");
//                                paramValues[i] = value;
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//        //投机取巧的方式
//        //通过反射拿到method所在class，拿到class之后还是拿到class的名称
//        //再调用toLowerFirstCase获得beanName
//        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
//        method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0]});
//    }

    /**
     * 初始化阶段
     * @param config ServletConfig
     * @throws ServletException ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3.初始化扫描到的类，并且将他们放入IOC容器之中
        doInstance();

        //4.完成依赖注入
        doAutowired();

        //5.初始化HandlerMapping
        initHandlerMapping();

        System.out.println("GP Spring framework is init.");
    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                handlerMapping.put(url, method);
                System.out.println("Mapped " + url + "," + method);
            }
        }
    }

    /**
     * 自动注入
     */
    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取实例对象中的属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 实例化
     * 控制反转过程
     * 工厂模式来实现
     */
    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(GPController.class)) {
                    Object instance = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    GPService service = clazz.getAnnotation(GPService.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    for (Class<?> clazzInterface : clazz.getInterfaces()) {
                        if (ioc.containsKey(clazzInterface.getName())) {
                            throw new Exception("The beanName is exists!!");
                        }
                        ioc.put(clazzInterface.getName(), instance);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * simpleName首字符小写
     * @param simpleName simpleName
     * @return 转小写后的字符串
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 将scanPackage包下面的所有类全部扫描进来
     * @param scanPackage scanPackage
     */
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader()
                .getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     * @param contextConfigLocation 配置文件名
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
