package vn.quankane.food_ecommerce.domain.validator;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

    String message() default ErrorMessage.Validator.ERR_PHONE_VALIDATOR;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
