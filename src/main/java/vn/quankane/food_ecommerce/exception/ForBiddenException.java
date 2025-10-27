package vn.quankane.food_ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForBiddenException extends RuntimeException {
    public ForBiddenException(String message) {
        super(message);
    }
}
