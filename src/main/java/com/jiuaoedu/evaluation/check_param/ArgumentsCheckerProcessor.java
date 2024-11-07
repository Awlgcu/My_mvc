package com.jiuaoedu.evaluation.check_param;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsCheckerProcessor {
    private List<ParamChecker> checkers = new ArrayList<>();
    public ArgumentsCheckerProcessor(List<ParamChecker> checkers){
        this.checkers = checkers;
    }
    public String check(HttpServletRequest request, Method method){
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter: parameters){
            for (ParamChecker checker: checkers){
                if (checker.supports(parameter)){
                    String checked = checker.check(parameter, request);
                    if (checked!=null){
                        return checked;
                    }
                }
            }
        }
        return null;
    }
}
