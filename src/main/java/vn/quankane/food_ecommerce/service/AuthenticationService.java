package vn.quankane.food_ecommerce.service;

import vn.quankane.food_ecommerce.domain.dto.request.auth.*;
import vn.quankane.food_ecommerce.domain.dto.request.auth.otp.VerifyOtpRequestDto;
import vn.quankane.food_ecommerce.domain.dto.response.auth.LoginResponseDto;
import vn.quankane.food_ecommerce.domain.dto.response.auth.RefreshTokenResponseDto;
import vn.quankane.food_ecommerce.domain.dto.response.user.UserResponseDto;

public interface AuthenticationService {

    LoginResponseDto authentication(LoginRequestDto request);

//    LoginResponseDto loginWithGoogle(OAuth2GoogleRequestDto request) throws GeneralSecurityException, IOException;

    void logout(LogoutRequestDto request);

    RefreshTokenResponseDto refresh(RefreshTokenRequestDto request);

    void register(RegisterRequestDto request);

    UserResponseDto verifyOtpToRegister(VerifyOtpRequestDto request);

    void forgotPassword(ForgotPasswordRequestDto request);

    boolean verifyOtpToResetPassword(VerifyOtpRequestDto request);

    UserResponseDto resetPassword(ResetPasswordRequestDto request);

}
