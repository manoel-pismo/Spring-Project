package com.rachadel.config.validation.annotations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Long> {

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        Query query = entityManager.createQuery("select 1 from " + klass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        String message = klass.getSimpleName().toLowerCase() + " with id "+ value + " was not found!.";
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return !list.isEmpty();
    }
}
