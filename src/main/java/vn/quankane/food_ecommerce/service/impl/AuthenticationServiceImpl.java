package vn.quankane.food_ecommerce.service.impl;

import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.quankane.food_ecommerce.constant.CommonConstant;
import vn.quankane.food_ecommerce.constant.TokenType;
import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import vn.quankane.food_ecommerce.constant.verification.VerificationType;
import vn.quankane.food_ecommerce.domain.dto.request.auth.*;
import vn.quankane.food_ecommerce.domain.dto.request.auth.otp.PendingRegistrationRequestDto;
import vn.quankane.food_ecommerce.domain.dto.request.auth.otp.PendingResetPasswordRequestDto;
import vn.quankane.food_ecommerce.domain.dto.request.auth.otp.VerifyOtpRequestDto;
import vn.quankane.food_ecommerce.domain.dto.response.auth.LoginResponseDto;
import vn.quankane.food_ecommerce.domain.dto.response.auth.RefreshTokenResponseDto;
import vn.quankane.food_ecommerce.domain.dto.response.user.UserResponseDto;
import vn.quankane.food_ecommerce.domain.entity.cart.Cart;
import vn.quankane.food_ecommerce.domain.entity.token.InvalidatedToken;
import vn.quankane.food_ecommerce.domain.entity.user.Role;
import vn.quankane.food_ecommerce.domain.entity.user.User;
import vn.quankane.food_ecommerce.domain.mapper.AuthMapper;
import vn.quankane.food_ecommerce.exception.InvalidDataException;
import vn.quankane.food_ecommerce.exception.ResourceNotFoundException;
import vn.quankane.food_ecommerce.repository.CartRepository;
import vn.quankane.food_ecommerce.repository.InvalidatedTokenRepository;
import vn.quankane.food_ecommerce.repository.UserRepository;
import vn.quankane.food_ecommerce.security.CustomUserDetails;
import vn.quankane.food_ecommerce.security.CustomUserDetailsService;
import vn.quankane.food_ecommerce.service.AuthenticationService;
import vn.quankane.food_ecommerce.service.EmailService;
import vn.quankane.food_ecommerce.service.JwtService;
import vn.quankane.food_ecommerce.util.OtpUtil;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    JwtService jwtService;

    AuthMapper authMapper;

    EmailService emailService;

    AuthenticationManager authenticationManager;

    InvalidatedTokenRepository invalidatedTokenRepository;

    UserRepository userRepository;

    CartRepository cartRepository;

    Map<String, PendingRegistrationRequestDto> pendingRegisterMap = new ConcurrentHashMap<>();

    Map<String, PendingResetPasswordRequestDto> pendingResetPwMap = new ConcurrentHashMap<>();

    @Override
    public LoginResponseDto authentication(LoginRequestDto request) {

        Authentication authentication;
        User user;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            user = customUserDetails.getUser();

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new BadCredentialsException(ErrorMessage.Auth.ERR_ACCOUNT_LOCKED);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(ErrorMessage.Auth.ERR_INTERNAL_ERROR);
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), List.of(new SimpleGrantedAuthority(user.getRole().name())));

        return LoginResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void logout(LogoutRequestDto request) {
        String jwtId = null;
        Date expirationTime = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());

            jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            invalidatedTokenRepository.save(new InvalidatedToken(jwtId, expirationTime));
        } catch (ParseException ex) {
            log.error("Signed Jwt parsed fail, message = {}", ex.getMessage());
            throw new InvalidDataException(ErrorMessage.Auth.ERR_TOKEN_INVALIDATED);
        }
    }

    @Override
    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);

        if (jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, username)) {
            throw new InvalidDataException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));

        return RefreshTokenResponseDto.builder()
                .tokenType(CommonConstant.BEARER_TOKEN)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void register(RegisterRequestDto request) {
        if (userRepository.existsUserByUsernameAndIsDeletedFalse(request.getUsername())) {
            throw new InvalidDataException(ErrorMessage.User.ERR_USERNAME_EXISTED);
        }
        if (userRepository.existsUserByEmailAndIsDeletedFalse(request.getEmail())) {
            throw new InvalidDataException(ErrorMessage.User.ERR_EMAIL_EXISTED);
        }

        String otp = OtpUtil.generateOtp();

        PendingRegistrationRequestDto pendingRegistrationRequestDto = new PendingRegistrationRequestDto();
        pendingRegistrationRequestDto.setOtp(otp);
        pendingRegistrationRequestDto.setRequest(request);
        pendingRegistrationRequestDto.setExpireAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5));

        pendingRegisterMap.put(request.getEmail(), pendingRegistrationRequestDto);

        emailService.verifyOtpViaGmail(request.getEmail(), request.getUsername(), otp, VerificationType.ACCOUNT_VERIFICATION);
    }

    @Override
    public UserResponseDto verifyOtpToRegister(VerifyOtpRequestDto request) {
        PendingRegistrationRequestDto pending = pendingRegisterMap.get(request.getEmail());

        if (pending == null){
            throw new InvalidDataException(ErrorMessage.Auth.ERR_PENDING_REGISTER_REQUEST_NULL);
        }

        if (pending.isExpired())
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_EXPIRED);

        if (!pending.getOtp().equals(request.getOtp()))
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_NOT_MATCH);

        RegisterRequestDto req = pending.getRequest();

        User user = authMapper.registerRequestDtoToUser(req);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        userRepository.save(user);
        cart = cartRepository.save(cart);

        pendingRegisterMap.remove(request.getEmail());

        return authMapper.userToUserResponseDto(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDto request) {

        if (!userRepository.existsUserByEmailAndIsDeletedFalse(request.getEmail()))
            throw new ResourceNotFoundException(ErrorMessage.User.ERR_EMAIL_NOT_EXISTED);

        String otp = OtpUtil.generateOtp();

        PendingResetPasswordRequestDto pending = new PendingResetPasswordRequestDto();

        pending.setRequest(request);
        pending.setOtp(otp);
        pending.setExpireAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5));

        pendingResetPwMap.put(request.getEmail(), pending);

        emailService.verifyOtpViaGmail(request.getEmail(), request.getEmail(), otp, VerificationType.PASSWORD_FORGOTTEN_VERIFICATION);
    }

    @Override
    public boolean verifyOtpToResetPassword(VerifyOtpRequestDto request) {
        PendingResetPasswordRequestDto pending = pendingResetPwMap.get(request.getEmail());

        if (pending == null)
            throw new InvalidDataException(ErrorMessage.Auth.ERR_PENDING_RESET_REQUEST_NULL);

        if (pending.isExpired())
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_EXPIRED);

        if (!pending.getOtp().equals(request.getOtp()))
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_NOT_MATCH);

        return pendingResetPwMap.containsKey(request.getEmail())
                && pendingResetPwMap.get(request.getEmail()).getOtp().equals(request.getOtp());
    }

    @Override
    public UserResponseDto resetPassword(ResetPasswordRequestDto request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new InvalidDataException(ErrorMessage.User.ERR_DUPLICATE_OLD_PASSWORD);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        pendingResetPwMap.remove(request.getEmail());

        return authMapper.userToUserResponseDto(user);
    }
}
