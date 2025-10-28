package vn.quankane.food_ecommerce.service;

import vn.quankane.food_ecommerce.constant.verification.VerificationType;

public interface EmailService {

    void verifyOtpViaGmail(String to, String name, String otp, VerificationType type);
}
