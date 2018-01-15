package net.kingsilk.qh.oauth.repo;

import net.kingsilk.qh.oauth.domain.Sms;

public interface SmsRepo extends BaseRepo<Sms, String> {

    long countByPhone(String phone);

    Sms findOneByPhoneAndVerifyCodeAndIsValid(String phone, String code, boolean isValid);
}
