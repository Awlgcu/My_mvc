package com.jiuaoedu.evaluation.check_param;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public interface ParamChecker {
    boolean supports(Parameter parameter);
    String check(Parameter parameter, HttpServletRequest request);
}
