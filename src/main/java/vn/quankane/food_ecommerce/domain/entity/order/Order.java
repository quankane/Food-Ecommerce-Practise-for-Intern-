package vn.quankane.food_ecommerce.domain.entity.order;

import vn.quankane.food_ecommerce.constant.order.OrderStatus;
import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import vn.quankane.food_ecommerce.domain.entity.payment.Payment;
import vn.quankane.food_ecommerce.domain.entity.promotion.Promotion;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String orderNumber;

    Double shippingFee;

    Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    OrderStatus status;

    LocalDate orderDate;

    LocalDate deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    Promotion promotion;

    // ---------------- Helper methods ----------------

    public void addOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (orderItems.contains(orderItem)) {
            orderItems.remove(orderItem);
            orderItem.setOrder(null);
        }
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setOrder(this);
        }
    }
}
