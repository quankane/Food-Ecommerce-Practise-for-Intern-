package vn.quankane.food_ecommerce.domain.validator;

import vn.quankane.food_ecommerce.domain.dto.request.promotion.PromotionRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PromotionDateValidator
        implements ConstraintValidator<ValidPromotionDates, PromotionRequestDto> {

    @Override
    public boolean isValid(PromotionRequestDto dto,
                           ConstraintValidatorContext context) {
        if (dto == null) return true; // để @NotNull handle riêng
        if (dto.getStartDate() == null || dto.getEndDate() == null) return true;
        if (dto.getMinPriceOrder() == null || dto.getMaxPriceOrder() == null) return true;
        return dto.getStartDate().isBefore(dto.getEndDate())
                && dto.getMinPriceOrder() < dto.getMaxPriceOrder();
    }
}
