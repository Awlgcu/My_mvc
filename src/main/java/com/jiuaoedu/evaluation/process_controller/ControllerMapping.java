package com.jiuaoedu.evaluation.process_controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapping {
    private static final Map<MappingInfo, MethodHandler> MAPPINGS = new HashMap<>();

    public void register(String url, String httpMethod, Method method, Object controller) {
        MappingInfo mappingInfo = new MappingInfo(url, httpMethod);
        System.out.println("注册了一个处理器服务于:"+url+"-"+httpMethod);
        MAPPINGS.put(mappingInfo, new MethodHandler(method, controller));
    }

    public MethodHandler getHandler(String url, String httpMethod) {
        return MAPPINGS.get(new MappingInfo(url, httpMethod));
    }
    public void printSize(){
        System.out.println(MAPPINGS.size());
    }
}
