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
public class LoginRequestDto {

    @Schema(description ="User người dùng", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email
    String username;

    @Schema(description = "Mật khẩu", example = "Quankane1905@")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String password;

}
