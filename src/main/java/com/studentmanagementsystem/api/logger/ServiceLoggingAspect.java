//package com.studentmanagementsystem.api.logger;
//
//import java.util.Arrays;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
////import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class ServiceLoggingAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);
//
//    // Pointcut for all methods inside serviceimpl package
//    @Pointcut("execution(* com.studentmanagementsystem.api.serviceimpl..*(..))")
//    public void serviceMethods() {}
//
//    // Before method execution
//    @Before("serviceMethods()")
//    public void logBefore(JoinPoint joinPoint) {
//        logger.info("Before Method: {} | Args: {}",
//                joinPoint.getSignature().toShortString(),
//                Arrays.toString(joinPoint.getArgs()));
//    }
//
//    // After successful execution
//    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
//    public void logAfter(JoinPoint joinPoint, Object result) {
//        logger.info("After Method: {} | Returned: {}",
//                joinPoint.getSignature().toShortString(),
//                result);
//    }
//
//    // After exception
//    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
//    public void logException(JoinPoint joinPoint, Throwable ex) {
//        logger.error("Exception in Method: {} | Message: {}",
//                joinPoint.getSignature().toShortString(),
//                ex.getMessage());
//    }
//    
//    
//  
//   
//}

