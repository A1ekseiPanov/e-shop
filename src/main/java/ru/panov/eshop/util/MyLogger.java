package ru.panov.eshop.util;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;


@Aspect
@Component
public class MyLogger {
    private Logger log = LoggerFactory.getLogger(getClass());

    @After("execution(* ru.panov.eshop.controllers..*.*(..))")
    public void log(JoinPoint point) {
        log.info(LocalDateTime.now() + "  [INFO]  " +  point.getSignature().getDeclaringType().getSimpleName() + " "
                + point.getSignature().getName() + " called...");
    }


//    @Around("execution(* ru.panov.eshop.controllers..*.*(..))")
//    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
//
//        final StopWatch stopWatch = new StopWatch();
//
//        //calculate method execution time
//        stopWatch.start();
//        Object result = proceedingJoinPoint.proceed();
//        stopWatch.stop();
//
//        //Log method execution time
//        log.info("Execution time of "
//                + methodSignature.getDeclaringType().getSimpleName() // Class Name
//                + "." + methodSignature.getName() + " " // Method Name
//                + ":: " + stopWatch.getTotalTimeMillis() + " ms");
//
//        return result;
//    }
}
