package net.kingsilk.qh.oauth.domain;

import net.kingsilk.qh.oauth.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 短信验证码。
 *
 * 如果验证码已经验证过，则应当物理删除。
 */
@Document
public class SmsVerifyCode extends Base {

    /**
     * 短信验证码类型。
     */
    private SmsVerifyCodeTypeEnum verifyCodeType;

    /**
     * 短信签名者标识。
     *
     * 比如 "小皇叔", "钱皇" 等对应的标识符 —— "xhs", "kingsilk" 等。
     */
    private String signer;

    /**
     * 发短信的渠道商。
     *
     * 比如阿里大于，云片网等。
     */
    private String channel;

    /**
     * 手机号
     */
    @Indexed
    private String phone;

    /**
     * 短信验证码
     */
    private String verifyCode;


    /**
     * 短信模板ID。
     */
    private String tplId;


    /**
     * 短信参数。
     */
    private Map<String, String> params;


    /**
     * 完整的短信内容。
     */
    private String sms;

    /**
     * 短信验证码过期时间
     */
    @Indexed(expireAfterSeconds = 0)
    private Date expiredAt;


    /**
     * 客户端IP地址
     *
     * 可选
     */
    private String clientIp;

    /**
     * 客户端环境指纹
     *
     * 可选
     */
    private String clientFingerPrint;


    // ------------------------ 自动生成的 getter、 setter

    public SmsVerifyCodeTypeEnum getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(SmsVerifyCodeTypeEnum verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientFingerPrint() {
        return clientFingerPrint;
    }

    public void setClientFingerPrint(String clientFingerPrint) {
        this.clientFingerPrint = clientFingerPrint;
    }
}
