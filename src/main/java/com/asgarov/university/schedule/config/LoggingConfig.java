package com.asgarov.university.schedule.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingConfig {

    final static Logger logger = LoggerFactory.getLogger(LoggingConfig.class.getSimpleName());

    @Pointcut("@annotation(com.asgarov.university.schedule.annotations.Loggable)")
    public void anyServiceClassMethod() {
    }


    @Before("anyServiceClassMethod()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().toShortString();
        logger.info("Executing: " + methodName);
    }

    @AfterReturning("anyServiceClassMethod()")
    public void logMethodCompletion(JoinPoint jp) {
        String methodName = jp.getSignature().toShortString();
        logger.info("Successfully executed: " + methodName);
    }
}
