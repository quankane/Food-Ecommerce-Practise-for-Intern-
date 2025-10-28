package vn.quankane.food_ecommerce.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import vn.quankane.food_ecommerce.exception.ResourceNotFoundException;
import vn.quankane.food_ecommerce.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOM-USER-DETAILS-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_FOUND))
        );
    }
}
