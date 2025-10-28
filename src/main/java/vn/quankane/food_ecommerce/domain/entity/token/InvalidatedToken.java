package vn.quankane.food_ecommerce.domain.entity.token;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.quankane.food_ecommerce.constant.TokenType;

import java.util.Date;

@Entity(name = "InvalidatedToken")
@Table(name = "invalidated_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {

    @Id
    String id;

    Date expiryTime;
}
