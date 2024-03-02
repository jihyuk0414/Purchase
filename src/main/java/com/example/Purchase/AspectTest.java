package com.example.Purchase;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class AspectTest {
    @Around("execution(* com.example.Purchase.controller.*.*(..))")
    public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("controllertime = {}", stopWatch.prettyPrint(), "실행함수 = {}", stopWatch.currentTaskName()) ;

        return result;
    }

    @Around("execution(* com.example.Purchase.service.*.*(..))")
    public Object measureServiceMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("{} method execution time: {} ms", joinPoint.getSignature().toShortString(), stopWatch.getTotalTimeMillis());

        return result;
    }


}
