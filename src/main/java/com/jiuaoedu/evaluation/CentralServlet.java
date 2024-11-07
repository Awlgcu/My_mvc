package com.jiuaoedu.evaluation;

import com.jiuaoedu.evaluation.check_param.ArgumentsCheckerProcessor;
import com.jiuaoedu.evaluation.dto.Result;
import com.jiuaoedu.evaluation.process_controller.ControllerMapping;
import com.jiuaoedu.evaluation.process_controller.MethodHandler;
import com.jiuaoedu.evaluation.process_param.ArgumentsProcessor;
import com.jiuaoedu.evaluation.process_return.ReturnValueProcessor;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


@WebServlet(name = "centralServlet", value = "/*")
public class CentralServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }


    @Override
    public ServletConfig getServletConfig() {
        return null;
    }


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        try {
            //请求和响应的类型转换
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            //拿到servletContext容器,监听容器初始化,在初始化时进行包扫描,通过反射拿到所有的controller信息
            //并且完成controller和其需要对应的http请求的映射,将得到的http请求和其对应的方法存在map里面
            //!!!这些事都是在servlet初始化的时候就干完了,形成了静态资源
            ServletContext servletContext = servletRequest.getServletContext();
            ControllerMapping controllerMapping = (ControllerMapping) servletContext.getAttribute("controllerMapping");
            ArgumentsProcessor argumentsProcessor = (ArgumentsProcessor) servletContext.getAttribute("argumentsProcessor");
            ReturnValueProcessor returnValueProcessor = (ReturnValueProcessor) servletContext.getAttribute("returnValueProcessor");
            ArgumentsCheckerProcessor argumentsCheckerProcessor = (ArgumentsCheckerProcessor)servletContext.getAttribute("argumentsCheckerProcessor");


            System.out.println("拿到请求:"+httpServletRequest.getRequestURI());
            //从之前准备好的静态资源map里面拿到当前真正进来的方法处理器,通过处理器里面的controller对象和方法去执行方法
            //!!!是通过反射去执行的
            MethodHandler methodHandler = controllerMapping.getHandler(httpServletRequest.getRequestURI(), httpServletRequest.getMethod());
            Method method = methodHandler.getMethod();

            //参数校验
            String checked = argumentsCheckerProcessor.check(httpServletRequest, method);
            if (checked!=null){
                //参数校验不通过,直接返回错误信息
                returnValueProcessor.handle(httpServletRequest, httpServletResponse, Result.fail(checked));
                return;
            }

            //参数处理
            Object[] args = argumentsProcessor.process(httpServletRequest, method);
            Object result = method.invoke(methodHandler.getController(), args);

            //todo 处理执行完后的响应序列化
            returnValueProcessor.handle(httpServletRequest, httpServletResponse, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
    public void testGithub(){

    }
}