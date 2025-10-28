package vn.quankane.food_ecommerce.domain.dto.response.auth;

import com.google.api.client.auth.oauth2.BearerToken;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.quankane.food_ecommerce.constant.CommonConstant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginResponseDto {

    @Builder.Default
    String tokenType = CommonConstant.BEARER_TOKEN;

    String userId;

    String role;

    String accessToken;

    String refreshToken;

//    Boolean isDeleted;
//
//    Boolean canRecovery;
//
//    long dayRecoveryRemaining;

}
