package vn.quankane.food_ecommerce.domain.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestDto {

    @Schema(description = "Email người dùng", example = "quanducbui2017@gmail.com")
    @NotBlank
    String email;

    String password;
}
