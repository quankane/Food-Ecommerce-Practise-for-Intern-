package vn.quankane.food_ecommerce.domain.validator;

import vn.quankane.food_ecommerce.domain.entity.user.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class GenderSubsetValidator implements ConstraintValidator<GenderSubset, Gender> {

    private Gender[] genders;

    @Override
    public void initialize(GenderSubset constraintAnnotation) {
        this.genders = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(genders).contains(value);
    }
}
