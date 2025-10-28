package vn.quankane.food_ecommerce.domain.dto.request.auth;

import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String refreshToken;

}
