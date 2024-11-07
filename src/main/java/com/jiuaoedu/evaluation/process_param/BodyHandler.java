package com.jiuaoedu.evaluation.process_param;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuaoedu.evaluation.globalParams.REQUEST_BODY;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Parameter;

public class BodyHandler implements ArgumentHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Boolean supports(Parameter parameter) {
        //检查方法的参数是否被@RequestBody注解标注
        RequestBody annotation = parameter.getAnnotation(RequestBody.class);
        return annotation != null;
    }

    @Override
    public Object handle(Parameter parameter, HttpServletRequest request) {
        //   反序列化
        //把前端传来的字节流的请求体转换成对象
        //parameter的类型就是我们需要转化的对象
        try {
            if (REQUEST_BODY.getBody()!=null){
                return REQUEST_BODY.getBody();
            }
            return objectMapper.readValue(request.getInputStream(), parameter.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
