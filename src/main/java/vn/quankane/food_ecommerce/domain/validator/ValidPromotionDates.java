package vn.quankane.food_ecommerce.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.quankane.food_ecommerce.constant.response.ErrorMessage;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PromotionDateValidator.class)
@Documented
public @interface ValidPromotionDates {
    String message() default ErrorMessage.Validator.ERR_PROMOTION_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
