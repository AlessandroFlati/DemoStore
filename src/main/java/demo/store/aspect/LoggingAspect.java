package demo.store.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* demo.store.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.service.*.*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        System.out.println("Before service method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.service.*.*(..))")
    public void logAfterService(JoinPoint joinPoint) {
        System.out.println("After service method: " + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.repository.*.*(..))")
    public void logBeforeRepository(JoinPoint joinPoint) {
        System.out.println("Before repository method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.repository.*.*(..))")
    public void logAfterRepository(JoinPoint joinPoint) {
        System.out.println("After repository method: " + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.model.*.*(..))")
    public void logBeforeModel(JoinPoint joinPoint) {
        System.out.println("Before model method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.model.*.*(..))")
    public void logAfterModel(JoinPoint joinPoint) {
        System.out.println("After model method: " + joinPoint.getSignature().getName());
    }

}
