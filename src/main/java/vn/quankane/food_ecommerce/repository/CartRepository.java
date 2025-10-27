package vn.quankane.food_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.quankane.food_ecommerce.domain.entity.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
