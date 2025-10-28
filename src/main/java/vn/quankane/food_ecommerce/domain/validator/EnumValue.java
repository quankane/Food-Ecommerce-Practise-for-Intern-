package vn.quankane.food_ecommerce.domain.validator;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    String name();
    String message() default ErrorMessage.Validator.ERR_ENUM_VALUE_VALIDATOR;
    Class<? extends Enum<?>> enumClass();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
