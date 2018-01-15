package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.SmsPlatformEnum;
import net.kingsilk.qh.agency.core.SmsTypeEnum;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by zcw on 3/14/17.
 * 短信相关
 */
//@Document
public class Sms extends Base {

    /**
     * 短信接收者
     */
    private String phone;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 是否有效
     */
    private boolean valid;

    /**
     * 发送时间
     */
    @Indexed(expireAfterSeconds = 1800)
    private Date sendTime;

    /**
     * 请求短信的公网ip
     */
    private String ip;

    /**
     * 短信类型
     */
    private SmsTypeEnum smsType;

    /**
     * 短信发送平台
     */
    private SmsPlatformEnum paltform;

    /**
     * 阿里大于短信状态
     */
    private ALiSendStatus aLiSendStatus = new ALiSendStatus();

    /**
     * 云片网短信状态
     */
    private YpSendStatus ypSendStatus = new YpSendStatus();

    /**
     * 阿里大于短信发送状态
     */
    public class ALiSendStatus {

        /**
         * 序号
         */
        String no;

        /**
         * 错误码
         */
        String errorCode;

        /**
         * 返回的结果
         */
        String model;

        /**
         * 返回信息描述
         */
        String msg;

        /**
         * 是否发送成功
         */
        Boolean success;

        // --------------------------------------- getter && setter

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }

    /**
     * 云片网短信发送状态
     */
    public class YpSendStatus {
        /**
         * 序号
         */
        String no;

        /**
         * 成功发送的数量
         */
        Integer count;

        /**
         * 费扣费条数，70个字一条，超出70个字时按每67字一条计
         */
        Integer fee;

        /**
         * 短信id，多个号码时以该id+各手机号尾号后8位作为短信id。64位整型， 对应Java和C#的Long，不可用int解析
         */
        Long sid;

        /**
         * 用户自定义id
         */
        String uid;

        /**
         * 用户接收时间
         */
        Date userReceiveTime;

        /**
         * 运营商返回的代码，如："DB:0103"
         */
        String errorMsg;

        /**
         * 手机号。 所有带+86前缀的号码都会去掉+86
         */
        String mobile;

        /**
         * 接收状态有  :SUCCESS/FAIL
         */
        String reportStatus;

        // --------------------------------------- getter && setter

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getFee() {
            return fee;
        }

        public void setFee(Integer fee) {
            this.fee = fee;
        }

        public Long getSid() {
            return sid;
        }

        public void setSid(Long sid) {
            this.sid = sid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Date getUserReceiveTime() {
            return userReceiveTime;
        }

        public void setUserReceiveTime(Date userReceiveTime) {
            this.userReceiveTime = userReceiveTime;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(String reportStatus) {
            this.reportStatus = reportStatus;
        }
    }

    // --------------------------------------- getter && setter

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public SmsTypeEnum getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTypeEnum smsType) {
        this.smsType = smsType;
    }

    public SmsPlatformEnum getPaltform() {
        return paltform;
    }

    public void setPaltform(SmsPlatformEnum paltform) {
        this.paltform = paltform;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public ALiSendStatus getaLiSendStatus() {
        return aLiSendStatus;
    }

    public void setaLiSendStatus(ALiSendStatus aLiSendStatus) {
        this.aLiSendStatus = aLiSendStatus;
    }

    public YpSendStatus getYpSendStatus() {
        return ypSendStatus;
    }

    public void setYpSendStatus(YpSendStatus ypSendStatus) {
        this.ypSendStatus = ypSendStatus;
    }
}
