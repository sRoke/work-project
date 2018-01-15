package net.kingsilk.qh.oauth.core.wap.resource.sms;

import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.sms.SmsVerifyCodeApi;
import net.kingsilk.qh.oauth.core.SmsVerifyCodeTypeEnum;
import net.kingsilk.qh.oauth.domain.QSms;
import net.kingsilk.qh.oauth.domain.Sms;
import net.kingsilk.qh.oauth.repo.SmsRepo;
import net.kingsilk.qh.oauth.service.AliSmsService;
import net.kingsilk.qh.oauth.service.UserService;
import net.kingsilk.qh.oauth.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

/**
 * 手机验证码登录
 */
public class SmsVerifyCodeResource implements SmsVerifyCodeApi {

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private SmsRepo smsRepo;



    @Autowired
    private VerifyService verifyService;

    /**
     * 发送登录短信验证码。
     *
     * 内部实现逻辑必须检查 fingerPrint, clientIp, 同一个手机号等频率限制。
     */
    @Override
    public UniResp<String> send(
//            String signer,
            SmsVerifyCodeTypeEnum type,
            String phone
//            String fingerPrint,
//            String clientIp,
//            String captchaId,
//            String captchaTxt
    ) {
        boolean isValid = verifyService.isValidPhone(phone);

        Assert.isTrue(isValid, "手机号码格式不正确！");
        aliSmsService.sendSms(phone);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> verifyLoginCode(
            String phone,
            String phoneCode
    ) {
        Assert.isTrue(!StringUtils.isEmpty(phone), "手机号码不能为空");
        Assert.isTrue(!StringUtils.isEmpty(phoneCode), "手机短信验证码不能为空");
        // 验证验证码的真实性
        Sms sms = smsRepo.findOne(allOf(
                QSms.sms.phone.eq(phone),
                QSms.sms.verifyCode.eq(phoneCode),
                QSms.sms.valid.eq(true),
                QSms.sms.codeExpiredTime.after(new Date()),
                anyOf(
                        QSms.sms.deleted.isNull(),
                        QSms.sms.deleted.eq(false)
                )
        ));

        if (sms == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

}
