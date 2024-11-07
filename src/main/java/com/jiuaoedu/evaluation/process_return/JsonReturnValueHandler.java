package com.jiuaoedu.evaluation.process_return;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JsonReturnValueHandler implements ReturnValueHandler{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean supports(HttpServletRequest request) {
        String requiredType = request.getContentType();
        return requiredType.contains("json");
    }

    @Override
    public void handle(Object returnValue, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf8");
        try {
            String respJson = objectMapper.writeValueAsString(returnValue);
            response.getWriter().write(respJson);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
