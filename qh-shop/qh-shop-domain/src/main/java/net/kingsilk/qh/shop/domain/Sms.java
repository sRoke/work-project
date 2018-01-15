package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by zcw on 3/14/17.
 * 短信相关
 */
@Document
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
     * 阿里大于短信状态
     */
    private ALiSendStatus aLiSendStatus = new ALiSendStatus();

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

}
