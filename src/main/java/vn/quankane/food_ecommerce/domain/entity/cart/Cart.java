package vn.quankane.food_ecommerce.domain.entity.cart;

import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(
        name = "carts",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id")
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems;

    // ---------------- Helper methods ----------------
    public void addCartItem(CartItem item) {
        if (!cartItems.contains(item)) {
            cartItems.add(item);
            item.setCart(this);
        }
    }

    public void removeCartItem(CartItem item) {
        if (cartItems.contains(item)) {
            cartItems.remove(item);
            item.setCart(null);
        }
    }
}
