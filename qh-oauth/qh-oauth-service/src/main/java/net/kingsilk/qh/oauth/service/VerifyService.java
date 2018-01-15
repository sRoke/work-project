package net.kingsilk.qh.oauth.service;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by lit on 17/9/20.
 */
@Service
public class VerifyService {

    private static final EmailValidator emailValidator = new EmailValidator();

    public boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches("\\d{11}");
    }

    public boolean isValidEmail(String email) {
        return emailValidator.isValid(email, null);
    }

    public boolean isValidUsername(String username) {

        if (StringUtils.isEmpty(username)) {
            return false;
        }

        return username.matches("[a-zA-Z_]\\w{3,16}");
    }
}
