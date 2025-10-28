package vn.quankane.food_ecommerce.domain.dto.response.user;

import vn.quankane.food_ecommerce.domain.entity.user.Gender;
import vn.quankane.food_ecommerce.domain.entity.user.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {

    String id;

    String username;

    String firstName;

    String lastName;

    String dateOfBirth;

    String avatarLink;

    String phone;

    String email;

    Role role;

    Gender gender;

    String nationality;

    Long cartId;
}
