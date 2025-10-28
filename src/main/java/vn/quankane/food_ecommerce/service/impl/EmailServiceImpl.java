package vn.quankane.food_ecommerce.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.quankane.food_ecommerce.constant.response.ErrorMessage;
import vn.quankane.food_ecommerce.constant.response.SuccessMessage;
import vn.quankane.food_ecommerce.constant.verification.VerificationType;
import vn.quankane.food_ecommerce.exception.SendEmailException;
import vn.quankane.food_ecommerce.service.EmailService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {

    private final SendGrid sendGrid;

    @Value("${spring.sendGrid.apiKey}")
    String apiKey;

    @Value("${spring.sendGrid.fromEmail}")
    String from;

    @Value("${spring.sendGrid.templateRegistrationId}")
    String templateRegistrationId;

    @Value("${spring.sendGrid.templateForgotId}")
    String templateForgotId;

    @Value("${spring.sendGrid.imagePrev}")
    String imagePrev;

    @Value("${spring.sendGrid.imageNext}")
    String imageNext;

    @Value("${spring.sendGrid.logo}")
    String logo;

    @Override
    public void verifyOtpViaGmail(String to, String name, String otp, VerificationType type) {
        log.info("Send email verification for username = {}", name);

        Email fromEmail = new Email(from, "Food Ecommerce");
        Email toEmail = new Email(to);

        //Dynamic data
        Map<String, String> dynamicTemplateData = new HashMap<>();
        dynamicTemplateData.put("NAME", name);
        dynamicTemplateData.put("OTP_CODE", otp);
        dynamicTemplateData.put("IMAGE_PREV", imagePrev);
        dynamicTemplateData.put("IMAGE_NEXT", imageNext);
        dynamicTemplateData.put("LOGO_LINK", logo);

        Mail mail = new Mail();
        mail.setFrom(fromEmail);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);

        //add dynamic template data to personal
        dynamicTemplateData.forEach(personalization::addDynamicTemplateData);
        mail.addPersonalization(personalization);

        switch (type) {
            case ACCOUNT_VERIFICATION -> {
                mail.setSubject("Xác thực tài khoản");
                mail.setTemplateId(templateRegistrationId);
            }
            case PASSWORD_FORGOTTEN_VERIFICATION -> {
                mail.setSubject("Quên mật khẩu");
                mail.setTemplateId(templateForgotId);
            }
        }

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setBody(mail.build());
            request.setEndpoint("mail/send");
            Response response = sendGrid.api(request);
            if (response.getStatusCode() == 202) {
                log.info(SuccessMessage.Email.ERR_SEND_EMAIL_SUCCESS);
            } else {
                log.warn(ErrorMessage.Email.ERR_SEND_EMAIL_FAILED);
            }
        } catch (Exception e) {
            log.warn(ErrorMessage.Email.ERR_SEND_EMAIL_FAILED);
            throw new SendEmailException(ErrorMessage.Email.ERR_SEND_EMAIL_FAILED);
        }
    }
}
