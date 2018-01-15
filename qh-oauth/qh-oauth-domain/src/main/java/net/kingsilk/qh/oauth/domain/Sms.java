package net.kingsilk.qh.oauth.domain;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * Created by tpx on 17-3-24.
 */
@Deprecated
public class Sms extends Base {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否有效
     */
    private boolean isValid;

    /**
     * 短信验证码
     */
    private String verifyCode;

    /**
     * 短信验证码过期时间
     * TODO 1.  追加注解 @Indexed(expireAfterSeconds=0)
     * TODO 2.  codeExpiredTime => expiredAt
     */
    @Indexed(expireAfterSeconds = 1800)
    private Date codeExpiredTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getCodeExpiredTime() {
        return codeExpiredTime;
    }

    public void setCodeExpiredTime(Date codeExpiredTime) {
        this.codeExpiredTime = codeExpiredTime;
    }
}
