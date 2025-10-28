package vn.quankane.food_ecommerce.domain.mapper;

import vn.quankane.food_ecommerce.domain.dto.request.auth.RegisterRequestDto;
import vn.quankane.food_ecommerce.domain.dto.response.user.UserResponseDto;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    User registerRequestDtoToUser(RegisterRequestDto request);

    @Mapping(target = "cartId", source = "user.cart.id")
    UserResponseDto userToUserResponseDto(User user);
}
