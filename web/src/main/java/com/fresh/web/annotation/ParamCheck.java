package com.fresh.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by ljiajiali on 17-9-6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD})
@Documented
public @interface ParamCheck {
}
