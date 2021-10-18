package com.rachadel.config.validation.annotations;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery("select 1 from " + klass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        String message = "the system found more than one " + klass.getSimpleName().toLowerCase() + " with the attribute " + domainAttribute + " = " + value;
        Assert.state(list.size() <= 1, message);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return list.isEmpty();
    }
}