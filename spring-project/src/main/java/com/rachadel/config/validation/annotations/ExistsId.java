package com.rachadel.config.validation.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ExistsIdValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsId {

    String message() default "{com.rachadel.config.validation.annotations}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String fieldName();
    Class<?> domainClass();
}