package com.fresh.web.aspect;

import com.fresh.web.annotation.ParamCheck;
import com.fresh.web.validator.ParamCheckValidator;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ljiajiali on 17-9-6.
 */
@Component
@Aspect
public class ParamCheckJoinPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamCheckJoinPoint.class);

    @Around("execution(* com.fresh.web.service..*(..))")
    public Object AroundParamCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ParamCheck paramCheck = method.getAnnotation(ParamCheck.class);
            if (paramCheck == null) {
                return result;
            }
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                List<Object> objectList = Lists.newArrayList();
                if (arg instanceof List) {
                    objectList = (List<Object>) arg;
                } else {
                    objectList.add(arg);
                }
                for (Object obj : objectList) {
                    Set<ConstraintViolation<Object>> validate = ParamCheckValidator.getInstance().validate(obj, Default.class);
                    if (validate.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        Iterator<ConstraintViolation<Object>> iterator = validate.iterator();
                        while (iterator.hasNext()) {
                            sb.append(iterator.next().getMessage()).append("-");
                        }
                        LOGGER.info(sb.toString());
                    }
                }
            }
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        return result;
    }
}
