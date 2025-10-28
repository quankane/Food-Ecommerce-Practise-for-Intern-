package vn.quankane.food_ecommerce.domain.dto.request.auth;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import vn.quankane.food_ecommerce.domain.validator.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequestDto {

    @Schema(description = "Email người dùng", example = "user@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email
    String email;
}
