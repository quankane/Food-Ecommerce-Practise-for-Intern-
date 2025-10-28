package vn.quankane.food_ecommerce.constant.payment;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentGateway {
    @JsonProperty("vnpay")
    VNPAY,
    
    @JsonProperty("momo")
    MOMO;

}
