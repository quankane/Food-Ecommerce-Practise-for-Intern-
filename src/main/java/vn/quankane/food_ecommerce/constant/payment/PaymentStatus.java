package vn.quankane.food_ecommerce.constant.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentStatus {
    @JsonProperty("pending")
    PENDING,

    @JsonProperty("completed")
    COMPLETED,

    @JsonProperty("expired")
    EXPIRED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("refunded")
    REFUNDED
}
