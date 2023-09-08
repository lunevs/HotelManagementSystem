package com.ichtus.hotelmanagementsystem.logconfig;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggerHandler {

    @Pointcut("execution(public org.springframework.http.ResponseEntity *(..))")
    protected void logAnyControllerPointcut() {}

    @AfterReturning(pointcut = "logAnyControllerPointcut()", returning = "result")
    private void logAnyController(ResponseEntity<?> result) {
        log.info("Result status: " + result.getStatusCode());
        if (result.getStatusCode().isError()) {
            ErrorDetail errorDetail = (ErrorDetail) result.getBody();
            assert errorDetail != null;
            log.info("Error details: " + errorDetail.getTitle());
        }
    }

    @Around("logAnyControllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }
}
