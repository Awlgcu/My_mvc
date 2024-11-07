package com.jiuaoedu.evaluation.process_controller;

import java.lang.reflect.Method;


public class MethodHandler {
    private final Method method;
    private final Object controller;

    public MethodHandler(Method method, Object controller) {
        this.method = method;
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public Object getController() {
        return controller;
    }
}
