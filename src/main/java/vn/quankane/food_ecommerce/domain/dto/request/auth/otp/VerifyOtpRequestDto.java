package vn.quankane.food_ecommerce.domain.dto.request.auth.otp;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpRequestDto {

    @Schema(description = "Email người dùng", example = "user@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    String email;

    @Schema(description = "Mã OTP")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    String otp;
}

