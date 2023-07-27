package com.ichtus.hotelmanagementsystem.model.anotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class ValidRoomCapacityValidator implements ConstraintValidator<ValidRoomCapacity, Integer> {

    @Value("${custom.room.capacity.min}")
    private int minCapacity;

    @Value("${custom.room.capacity.max}")
    private int maxCapacity;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null) {
            return false;
        }
        formatMessage(constraintValidatorContext);
        return (integer <= maxCapacity) && (integer >= minCapacity);
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(msg, this.maxCapacity);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg)
                .addConstraintViolation();
    }
}
