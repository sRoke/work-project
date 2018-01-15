package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.UserLoginTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 登录日志
 */
@Document
public class LoginLog extends Base {

    private String brandAppId;

    /**
     * 登录员工id
     */
    private String staffId;

    /**
     * 登录会员id
     */
    private String memberId;

    /**
     * 登录方式
     */
    private UserLoginTypeEnum userLoginTypeEnum;

    /**
     * 登录IP
     */
    private String loginIP;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 是否登录成功
     */
    private Boolean success;

    /**
     * 门店id
     */
    private String shopId;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public UserLoginTypeEnum getUserLoginTypeEnum() {
        return userLoginTypeEnum;
    }

    public void setUserLoginTypeEnum(UserLoginTypeEnum userLoginTypeEnum) {
        this.userLoginTypeEnum = userLoginTypeEnum;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
