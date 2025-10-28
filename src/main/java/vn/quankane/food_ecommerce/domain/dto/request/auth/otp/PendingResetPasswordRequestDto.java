package vn.quankane.food_ecommerce.domain.dto.request.auth.otp;

import vn.quankane.food_ecommerce.domain.dto.request.auth.ForgotPasswordRequestDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PendingResetPasswordRequestDto {

    ForgotPasswordRequestDto request;
    String otp;
    LocalDateTime expireAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }
}
