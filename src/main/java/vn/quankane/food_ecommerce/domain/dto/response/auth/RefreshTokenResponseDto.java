package vn.quankane.food_ecommerce.domain.dto.response.auth;

import vn.quankane.food_ecommerce.constant.CommonConstant;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenResponseDto {

    @Builder.Default
    String tokenType = CommonConstant.BEARER_TOKEN;

    String accessToken;

    String refreshToken;

}

