package com.jiuaoedu.evaluation.check_param;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuaoedu.evaluation.globalParams.REQUEST_BODY;
import com.jiuaoedu.evaluation.process_param.RequestBody;
import com.jiuaoedu.evaluation.process_param.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class NotNullChecker implements ParamChecker{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean supports(Parameter parameter) {
        NotNull annotation = parameter.getAnnotation(NotNull.class);
        return annotation!=null;
    }

    @Override
    public String check(Parameter parameter, HttpServletRequest request) {
        //1.路径参数处理
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        if (requestParam!=null){
            NotNull notNull = parameter.getAnnotation(NotNull.class);
            String requestParamValue = requestParam.value();
            String pathParam = request.getParameter(requestParamValue);
            if (pathParam==null) return notNull.message();
        }
        //2.请求体参数处理
        RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
        if (requestBody!=null){
            try {
                //先校验请求体是否为空,再校验请求体中的属性是否为空
                System.out.println("请求体长度:"+request.getContentLength());
                if (request.getContentLength()<=0){
                    System.out.println(parameter.getAnnotation(NotNull.class).message());
                    return parameter.getAnnotation(NotNull.class).message();
                }
                //将前端字节流转化为目标类型对象
                Object body = objectMapper.readValue(request.getInputStream(), parameter.getType());
                REQUEST_BODY.setBody(body);
                //拿到对象的所有属性
                Class<?> bodyClass = parameter.getType();
//                Class<?> bodyClass = body.getClass();
                Field[] fields = bodyClass.getDeclaredFields();
                for (Field field:fields){
                    System.out.println(field.getName());
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    if (notNull==null) continue;
                    //这个属性有不为空注解,则进行检验
                    PropertyDescriptor fieldMethods = new PropertyDescriptor(field.getName(), bodyClass);
                    Method readMethod = fieldMethods.getReadMethod();
                    Object bodyField = readMethod.invoke(body);
                    if (bodyField==null) return notNull.message();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
