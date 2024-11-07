package com.jiuaoedu.evaluation.process_param;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public class ParamHandler implements ArgumentHandler {
    @Override
    public Boolean supports(Parameter parameter) {
        //同样的判断controller的方法参数是否被@RequestParam标注
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        return requestParam != null;
    }

    @Override
    public Object handle(Parameter parameter, HttpServletRequest request) {
        //拿到这个参数的注解,注解里面包含了需要的参数的key
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
//        String name = parameter.getName();
//        System.out.println(name);
        //从注解中拿到参数的key
        String key = requestParam.value();
        //根据key拿到前端传的路径参数的value
        String value = request.getParameter(key);
        //前端传的value都是string类型,需要转化成parameter指定的类型!
        Class<?> type = parameter.getType();
        if (type.getName().split("\\.")[2].equals("Long")){
            return Long.valueOf(value);
        }
        return Long.valueOf(value);
    }
}
