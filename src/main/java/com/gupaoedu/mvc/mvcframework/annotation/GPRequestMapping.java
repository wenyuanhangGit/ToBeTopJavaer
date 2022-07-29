package com.gupaoedu.mvc.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author wen
 * @date 2020-06-28
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    
    String value() default "";

}
