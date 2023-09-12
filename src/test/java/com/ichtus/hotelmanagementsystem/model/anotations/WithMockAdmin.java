package com.ichtus.hotelmanagementsystem.model.anotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;


import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockAdminSecurityContextFactory.class)
public @interface WithMockAdmin {

    String value() default "admin";
    String username() default "";
    String[] roles() default {"ADMIN"};
    String[] authorities() default {};
    String password() default "123456";

    @AliasFor(annotation = WithSecurityContext.class)
    TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;
}
