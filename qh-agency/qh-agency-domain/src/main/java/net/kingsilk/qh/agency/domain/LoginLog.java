package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.LoginTypeEnum;
import net.kingsilk.qh.agency.core.QhAppEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 登录日志。
 *
 * @deprecated 应为使用RESTFUl 架构，无状态（无session），故不做登录日志。
 */
@Document
@Deprecated
public class LoginLog extends Base {

    /**
     * 用户
     */
    private String userId;
    /**
     * 登录方式
     */
    private LoginTypeEnum loginType;
    /**
     * 登录帐号。
     * <p>
     * 填写 User#username、User#phone、User#email、或者第三方的openId
     */
    private String account;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登录客户端
     */
    private QhAppEnum loginApp;
    /**
     * 是否登录成功
     */
    private Boolean success;
    /**
     * 备注
     */
    private String memo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LoginTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginTypeEnum loginType) {
        this.loginType = loginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public QhAppEnum getLoginApp() {
        return loginApp;
    }

    public void setLoginApp(QhAppEnum loginApp) {
        this.loginApp = loginApp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
