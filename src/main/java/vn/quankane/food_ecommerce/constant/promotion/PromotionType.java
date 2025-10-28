package vn.quankane.food_ecommerce.constant.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PromotionType {
    @JsonProperty("ORDER")
    ORDER,

    @JsonProperty("CATEGORY")
    CATEGORY;

    public static PromotionType fromString(String value) {
        for(PromotionType type : PromotionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
