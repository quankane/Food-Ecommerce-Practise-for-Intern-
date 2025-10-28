package vn.quankane.food_ecommerce.domain.dto.request.user;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmPasswordUpdateUserRequestDto {

    @Schema(description = "Mật khẩu xác nhận", example = "User123@")
    @NotBlank(message = ErrorMessage.User.ERR_PASSWORD_NOT_BLANK)
    String passwordConfirm;

    @Schema(description = "Thông tin profile cần cập nhật")
    @Valid
    UpdateUserRequestDto profileData;
}
