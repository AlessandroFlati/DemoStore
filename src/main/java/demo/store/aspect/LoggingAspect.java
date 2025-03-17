package demo.store.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Before("execution(* demo.store.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.service.*.*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.service.*.*(..))")
    public void logAfterService(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.repository.*.*(..))")
    public void logBeforeRepository(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.repository.*.*(..))")
    public void logAfterRepository(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Before("execution(* demo.store.model.*.*(..))")
    public void logBeforeModel(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @After("execution(* demo.store.model.*.*(..))")
    public void logAfterModel(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

}
