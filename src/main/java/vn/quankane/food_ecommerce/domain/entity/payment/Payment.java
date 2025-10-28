package vn.quankane.food_ecommerce.domain.entity.payment;

import vn.quankane.food_ecommerce.constant.payment.PaymentGateway;
import vn.quankane.food_ecommerce.constant.payment.PaymentStatus;
import vn.quankane.food_ecommerce.constant.payment.PaymentType;
import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import vn.quankane.food_ecommerce.domain.entity.order.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    String id;

    Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway", length = 20)
    PaymentGateway gateway;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    PaymentType type;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    Date expireAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

}
