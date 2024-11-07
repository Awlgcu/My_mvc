package com.jiuaoedu.evaluation;

import com.jiuaoedu.evaluation.check_param.ArgumentsCheckerProcessor;
import com.jiuaoedu.evaluation.check_param.NotNullChecker;
import com.jiuaoedu.evaluation.process_controller.Controller;
import com.jiuaoedu.evaluation.process_controller.ControllerMapping;
import com.jiuaoedu.evaluation.process_controller.Post;
import com.jiuaoedu.evaluation.process_param.ArgumentsProcessor;
import com.jiuaoedu.evaluation.process_param.BodyHandler;
import com.jiuaoedu.evaluation.process_param.ParamHandler;
import com.jiuaoedu.evaluation.process_return.JsonReturnValueHandler;
import com.jiuaoedu.evaluation.process_return.ReturnValueProcessor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

@WebListener
public class MvcStarter implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("初始化开始...");
        ControllerMapping controllerMapping = createMapping(sce);
        ArgumentsProcessor argumentsProcessor = createArgumentsProcessor(sce);
        ReturnValueProcessor returnValueProcessor = createReturnValueProcessor(sce);
        createArgumentsCheckerProcessor(sce);
        String basePackage = "com.jiuaoedu.evaluation.controller";
        String basePath = basePackage.replace(".", "/");
        //最核心的点是通过反射把class文件实例化
        //扫描包,拿到所有controller类的信息及其方法的信息
        ClassLoader classLoader = MvcStarter.class.getClassLoader();
        URL resource = classLoader.getResource(basePath);
        try {
            if (resource != null) {
                String path = resource.getFile();
                File directory = new File(path);
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String s = basePackage + '.' + file.getName().split("\\.")[0];
                        Class<?> aClass = classLoader.loadClass(s);
                        Controller controller = aClass.getAnnotation(Controller.class);
                        if (controller == null) continue;
                        Method[] methods = aClass.getMethods();
                        for (Method method : methods) {
                            Post post = method.getAnnotation(Post.class);
                            if (post == null) continue;
                            String url = post.url();
                            String httpMethod = post.method();
                            //将http请求和一个请求处理器映射在一起,处理器里面就是这个请求对应的service层的方法和
                            //一个controller实体(invoke方法需要一个实体)
                            controllerMapping.register(url, httpMethod, method, aClass.newInstance());
                        }
                    }
                }
            }
            System.out.println("所有初始化完成");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public ReturnValueProcessor createReturnValueProcessor(ServletContextEvent sce){
        ReturnValueProcessor returnValueProcessor = new ReturnValueProcessor(Collections.singletonList(new JsonReturnValueHandler()));
        sce.getServletContext().setAttribute("returnValueProcessor", returnValueProcessor);
        return returnValueProcessor;
    }
    public ControllerMapping createMapping(ServletContextEvent sce) {
        ControllerMapping controllerMapping = new ControllerMapping();
        sce.getServletContext().setAttribute("controllerMapping", controllerMapping);
        return controllerMapping;
    }

    public ArgumentsProcessor createArgumentsProcessor(ServletContextEvent sce) {
        ArgumentsProcessor argumentsProcessor = new ArgumentsProcessor(Arrays.asList(new BodyHandler(), new ParamHandler()));
        sce.getServletContext().setAttribute("argumentsProcessor", argumentsProcessor);
        return argumentsProcessor;
    }
    public ArgumentsCheckerProcessor createArgumentsCheckerProcessor(ServletContextEvent sce){
        ArgumentsCheckerProcessor argumentsCheckerProcessor = new ArgumentsCheckerProcessor(Collections.singletonList(new NotNullChecker()));
        sce.getServletContext().setAttribute("argumentsCheckerProcessor", argumentsCheckerProcessor);
        System.out.println("成功初始化参数检查器");
        return argumentsCheckerProcessor;
    }
}
