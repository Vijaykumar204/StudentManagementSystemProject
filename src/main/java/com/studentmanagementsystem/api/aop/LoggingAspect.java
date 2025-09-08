package com.studentmanagementsystem.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Before executing any method in service layer
     */
    @Before("execution(* com.studentmanagementsystem.api.serviceimpl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before - Method: {} | Arguments: {}", 
                    joinPoint.getSignature().toShortString(), 
                    joinPoint.getArgs());
    }

    /**
     * After successful return from service layer
     */
    @AfterReturning(pointcut = "execution(* com.studentmanagementsystem.api.serviceimpl.*.*(..))",
                    returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("After - Method: {} | Returned: {}", 
                    joinPoint.getSignature().toShortString(), 
                    result);
    }

    /**
     * On exception thrown in service layer
     */
    @AfterThrowing(pointcut = "execution(* com.studentmanagementsystem.api.serviceimpl.*.*(..))", 
                   throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception - Method: {} | Exception: {}", 
                     joinPoint.getSignature().toShortString(), 
                     ex.getMessage(), ex);
    }
}

