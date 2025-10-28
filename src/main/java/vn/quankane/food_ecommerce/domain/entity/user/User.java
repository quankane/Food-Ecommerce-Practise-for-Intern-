package vn.quankane.food_ecommerce.domain.entity.user;

import vn.quankane.food_ecommerce.constant.CommonConstant;
import vn.quankane.food_ecommerce.domain.entity.base.BaseEntity;
import vn.quankane.food_ecommerce.domain.entity.address.Address;
import vn.quankane.food_ecommerce.domain.entity.news.CommentNews;
import vn.quankane.food_ecommerce.domain.entity.news.News;
import vn.quankane.food_ecommerce.domain.entity.cart.Cart;
import vn.quankane.food_ecommerce.domain.entity.order.Order;
import vn.quankane.food_ecommerce.domain.entity.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36) DEFAULT (UUID())")
    String id;

    @Column(nullable = false, updatable = false, unique = true)
    String username;

    @Column(nullable = false)
    @JsonIgnore
    String password;

    String firstName;

    String lastName;

    Date dateOfBirth;

    String avatarLink;

    String avatarPublicId;

    @Column(nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String phone;

    @Builder.Default
    Boolean isLock = CommonConstant.FALSE;

    @Builder.Default
    Boolean isActive = CommonConstant.TRUE;

    @Builder.Default
    Boolean isDeleted = CommonConstant.FALSE;

    @Nationalized
    String nationality;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Order> orders;

    @OneToMany(mappedBy = "author")
    List<News> authoredNews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<CommentNews> newsComments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Address> addresses;
}
