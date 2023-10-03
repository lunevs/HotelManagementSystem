package com.ichtus.hotelmanagementsystem.model.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validation Entities. Defines borders for room capacity
 * @author smlunev
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidRoomCapacityValidator.class)
public @interface ValidRoomCapacity {

    String message() default "must be less than or equal to %s";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
