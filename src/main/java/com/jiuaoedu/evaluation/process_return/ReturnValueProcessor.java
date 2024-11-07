package com.jiuaoedu.evaluation.process_return;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ReturnValueProcessor {
    private List<ReturnValueHandler> handlers;
    public ReturnValueProcessor(List<ReturnValueHandler> handlers){
        this.handlers = handlers;
    }
    public void handle(HttpServletRequest request, HttpServletResponse response, Object returnValue){
        for(ReturnValueHandler handler: handlers){
            if (!handler.supports(request)){
                continue;
            }
            handler.handle(returnValue, response);
        }
    }
}
