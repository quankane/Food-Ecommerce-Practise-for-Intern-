package vn.quankane.food_ecommerce.domain.entity.promotion;

import vn.quankane.food_ecommerce.constant.promotion.PromotionStatus;
import vn.quankane.food_ecommerce.constant.promotion.PromotionType;
import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.quankane.food_ecommerce.domain.entity.category.Category;
import vn.quankane.food_ecommerce.domain.entity.order.Order;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "promotions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "promotion_code", unique = true)
    String promotionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    PromotionType type; //Order, Category

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "min_price_order")
    Float minPriceOrder;

    @Column(name = "max_price_order")
    Float maxPriceOrder;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "discount_percent", nullable = false)
    Float discountPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    PromotionStatus status;

    @Column(name = "is_deleted")
    @Builder.Default
    Boolean isDeleted = false;

    @OneToMany(mappedBy = "promotion")
    List<Order> orders;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;


    // ---------------- Helper methods ----------------

    //Order Promotion
    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
            order.setPromotion(this);
        }
    }

    public void removeOrder(Order order) {
        if (orders.contains(order)) {
            orders.remove(order);
            order.setPromotion(null);
        }
    }

}
