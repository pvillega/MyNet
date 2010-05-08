package com.perevillega.mynet.action.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Annotation that validates an URL has the proper format
 * @author pvillega
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
@ValidatorClass(URIValidator.class)
@Documented
public @interface URI {

    String message() default "This is not a valid URI";

}