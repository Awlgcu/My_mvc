package com.jiuaoedu.evaluation.process_return;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个处理returnValue的接口
 */
public interface ReturnValueHandler {
    boolean supports(HttpServletRequest request);
    void handle(Object returnValue, HttpServletResponse response);
}
