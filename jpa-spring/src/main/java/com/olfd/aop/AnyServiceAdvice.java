package com.olfd.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AnyServiceAdvice {

    @Before("execution(* com.olfd.aop.AnyService.doMagic(..))")
    public void before(JoinPoint joinPoint) {
        log.info("-----------before-------------");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.error("Executing {} with argument", className + methodName);
        log.error("Args: {}", joinPoint.getArgs());
        log.info("-----------end before-------------");
    }

    @Around("execution(* com.olfd.aop.AnyService.doMagic(..))")
    @SneakyThrows
    public String around(ProceedingJoinPoint joinPoint) {
        log.info("-----------start around-------------");
        String actualResponse = (String) joinPoint.proceed();
        log.error("Actual response: {} ", actualResponse);
        String stubbedResponse = "piy piy";
        log.error("Stab response: {}", stubbedResponse);
        log.info("-----------end around-------------");
        return stubbedResponse;
    }

    @After("execution(* com.olfd.aop.AnyService.doMagic(..))")
    public void after(JoinPoint joinPoint) {
        log.info("-----------start after-------------");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.error("Executing {} with argument", className + methodName);
        log.error("Args: {}", joinPoint.getArgs());
        log.info("-----------end after-------------");
    }

    @AfterReturning(
            value = "execution(* com.olfd.aop.AnyService.doMagic(..))",
            returning = "returning")
    public void afterReturning(String returning) {
        log.info("-----------Start After Returning-------------");
        log.error("return value is: {}", returning);
        log.info("-----------End After Returning-------------");
    }

    @AfterThrowing(value = "execution(* com.olfd.aop.AnyService.doMagic(..))",
            throwing = "ex")
    public String afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("-----------Start AfterThrowing-------------");
        log.error("Exception", ex);
        log.info("-----------END AfterThrowing-------------");
        return "ups! exception :)";
    }
}
