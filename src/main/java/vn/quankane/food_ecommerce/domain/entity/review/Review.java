package vn.quankane.food_ecommerce.domain.entity.review;

import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import vn.quankane.food_ecommerce.domain.entity.product.Product;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer rating;

    @Lob
    @Column(columnDefinition = "TEXT")
    String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

}

