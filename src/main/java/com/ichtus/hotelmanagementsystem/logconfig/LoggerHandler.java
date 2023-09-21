package com.ichtus.hotelmanagementsystem.logconfig;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging aspect. It logs each controller call
 * @author smlunev
 */
@Component
@Aspect
@Slf4j
public class LoggerHandler {

    /**
     * Pointcut defining rules for selecting controllers
     */
    @Pointcut("execution(public org.springframework.http.ResponseEntity *(..))")
    protected void logAnyControllerPointcut() {}

    /**
     * Logs response statuses
     * @param result controller's response result data
     */
    @AfterReturning(pointcut = "logAnyControllerPointcut()", returning = "result")
    private void logAnyController(ResponseEntity<?> result) {
        log.info("Result status: " + result.getStatusCode());
        if (result.getStatusCode().isError()) {
            ErrorDetail errorDetail = (ErrorDetail) result.getBody();
            assert errorDetail != null;
            log.info("Error details: " + errorDetail.getTitle());
        }
    }

    /**
     * Calculate methods executing time
     * @param joinPoint controller join point
     * @return controllers's response result data
     * @throws Throwable controller result exception
     */
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
            log.info("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }
}
