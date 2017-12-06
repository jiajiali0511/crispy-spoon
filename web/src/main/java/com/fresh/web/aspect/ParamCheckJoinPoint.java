package com.fresh.web.aspect;

import com.fresh.web.annotation.BasicTypeCheck;
import com.fresh.web.annotation.StructureTypeCheck;
import com.fresh.web.validator.ParamCheckValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
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
            Annotation[] annotations = method.getAnnotations();
            if (annotations == null || annotations.length == 0) {
                return result;
            }
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder("输入参数有误_");

            Set<ConstraintViolation<Object>> validates = Sets.newHashSet();
            for (Annotation anno : annotations) {
                Set<ConstraintViolation<Object>> currentVSets = Sets.newHashSet();
                if (anno instanceof StructureTypeCheck) {
                    currentVSets = getStructureTypeViolation(args);
                } else if (anno instanceof BasicTypeCheck) {
                    currentVSets = getBasicTypeViolation(joinPoint.getTarget().getClass().newInstance(), method, args);
                }
                if (CollectionUtils.isNotEmpty(currentVSets)) {
                    validates.addAll(currentVSets);
                }
            }

            if (CollectionUtils.isNotEmpty(validates)) {
                //错误日志处理（或抛异常）
                handleLogger(validates);
            }

            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        return result;
    }

    private void handleLogger(Set<ConstraintViolation<Object>> validates) {
        if (CollectionUtils.isEmpty(validates)) {
            return;
        }
        StringBuilder sb = new StringBuilder("入参有误 : ");
        Iterator<ConstraintViolation<Object>> iterator = validates.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<Object> violation = iterator.next();
            sb.append(violation.getMessage()).append("_");
        }
        LOGGER.error(sb.toString());
    }

    private Set<ConstraintViolation<Object>> getBasicTypeViolation(Object obj, Method method, Object[] args) {
        if (obj == null || method == null || args == null || args.length == 0) {
            return null;
        }
        ExecutableValidator executableValidator = ParamCheckValidator.getInstance().forExecutables();
        Set<ConstraintViolation<Object>> constraintViolations = executableValidator.validateParameters(obj, method, args);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return null;
        }
        return constraintViolations;
    }

    private Set<ConstraintViolation<Object>> getStructureTypeViolation(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        Set<ConstraintViolation<Object>> violationSets = Sets.newHashSet();
        for (Object arg : args) {
            List<Object> objectList = Lists.newArrayList();
            if (arg instanceof List) {
                objectList = (List<Object>) arg;
            } else {
                objectList.add(arg);
            }
            for (Object obj : objectList) {
                Set<ConstraintViolation<Object>> validates = ParamCheckValidator.getInstance().validate(obj, Default.class);
                if (CollectionUtils.isEmpty(validates)) {
                    continue;
                }
                violationSets.addAll(validates);
            }
        }
        return violationSets;
    }
}
