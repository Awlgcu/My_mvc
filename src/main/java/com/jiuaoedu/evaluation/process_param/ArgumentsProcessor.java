package com.jiuaoedu.evaluation.process_param;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsProcessor {

    private List<ArgumentHandler> handlers = new ArrayList<>();

    public ArgumentsProcessor(List<ArgumentHandler> handlers) {
        this.handlers = handlers;
    }

    public Object[] process(HttpServletRequest request, Method method) {
        //已经实现body参数处理
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        //方法上有很多参数,同时也有很多处理器,遍历每个参数,第二层遍历参数处理器的种类
        //如果参数有和参数处理器对应的注解,则让这个处理器去处理这个参数!
        //最终将http请求的参数(路径参数或请求体参数)转化为方法上需要的参数,并且通过反射调用这个方法完成业务处理
        for (int i = 0; i < parameters.length; i++) {
            for (ArgumentHandler handler : handlers) {
                Parameter parameter = parameters[i];
                Boolean supports = handler.supports(parameter);
                if (supports) {
                    Object arg = handler.handle(parameter, request);
                    args[i] = arg;
                }
            }
        }
        return args;
    }
}
