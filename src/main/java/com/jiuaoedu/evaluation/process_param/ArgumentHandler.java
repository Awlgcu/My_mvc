package com.jiuaoedu.evaluation.process_param;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;


public interface ArgumentHandler {
    //比如@RequestBody @RequestParam
    Boolean supports(Parameter parameter);
    Object handle(Parameter parameter, HttpServletRequest request);
}
