package vn.quankane.food_ecommerce.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {

    @NoArgsConstructor
    public static class Validator {

        public static final String ERR_INPUT_CONSTRAINT_VALIDATE = "exception.input.value.must.be.greater.than.or.equal.to.0.if.entry.data.AND.min.less.than.max.price";
        public static final String ERR_PHONE_VALIDATOR = "exception.phone.not.matches.pattern";
        public static final String ERR_GENDER_VALIDATOR = "exception.gender.must.be.not.any.of.array.['male', 'female', 'other']";
        public static final String ERR_ENUM_VALUE_VALIDATOR = "exception.{name}.must.be.not.any.of.enum.{enumClass}";
        public static final String ERR_EMAIL_VALIDATOR = "exception.email.invalid";
    }

    @NoArgsConstructor
    public static class User {
        public static final String ERR_USER_NOT_FOUND = "exception.user.not.found";
    }
}
