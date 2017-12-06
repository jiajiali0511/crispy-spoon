package com.fresh.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * create by lijiajia on 2017/12/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD})
@Documented
public @interface BasicTypeCheck {
    String value() default "";
}
