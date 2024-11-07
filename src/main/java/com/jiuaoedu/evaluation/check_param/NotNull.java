package com.jiuaoedu.evaluation.check_param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//可以放在属性或者方法参数上
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface NotNull {
    String message() default "该值不能为空";
}
