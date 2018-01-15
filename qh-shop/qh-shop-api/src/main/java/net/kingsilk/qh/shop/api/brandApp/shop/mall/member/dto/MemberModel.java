package net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashSet;

/**
 * 会员
 */
@ApiModel(value = "会员详细信息")
public class MemberModel {

    @ApiModelProperty(value = "会员id")
//    @QueryParam("id")
    private String id;

    @ApiModelProperty(value = "应用的Id名")
//    @QueryParam("brandAppId")
    private String brandAppId;

    @ApiModelProperty(value = "门店的ID")
//    @QueryParam("shopId")
    private String shopId;

    @ApiModelProperty(value = "用户ID")
//    @QueryParam("userId")
    private String userId;

    @ApiModelProperty(value = "是否禁用")
//    @QueryParam("enable")
    private Boolean enable = true;

    @ApiModelProperty(value = "会员等级Id")
//    @QueryParam("levelId")
    private String levelId;

    @ApiModelProperty(value = "会员真名")
//    @QueryParam("realName")
    private String realName;

    @ApiModelProperty(value = "会员真名")
//    @QueryParam("nickName")
    private String nickName;

    @ApiModelProperty(value = "会员手机号")
//    @QueryParam("phone")
    private String phone;

    @ApiModelProperty(value = "所在地区")
//    @QueryParam("adc")
    private String adc;

    @ApiModelProperty(value = "所在省")
//    @QueryParam("province")
    private String province;

    @ApiModelProperty(value = "所在市")
//    @QueryParam("city")
    private String city;

    @ApiModelProperty(value = "详细地址")
//    @QueryParam("srteet")
    private String srteet;

    @ApiModelProperty(value = "头像")
//    @QueryParam("avatar")
    private String avatar;

    @ApiModelProperty(value = "性别")
//    @QueryParam("sex")
    private String sex;

    @ApiModelProperty(value = "联系方式")
//    @QueryParam("contacts")
    private LinkedHashSet<String> contacts;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
//    @QueryParam("memo")
    private String memo;

    /**
     * 账户id
     */
    @ApiModelProperty(value = "账户id")
//    @QueryParam("accountId")
    private String accountId;

    @ApiModelProperty(value = "创建时间")
//    @QueryParam("dateCreated")
    private String dateCreated;


    @ApiModelProperty(value = "会员等级")
//    @QueryParam("level")
    private MemberLevel level;

    private class MemberLevel {

        /**
         * 会员等级名称
         */
        @ApiModelProperty(value = "会员等级名称")
//        @QueryParam("name")
        private String name;

        /**
         * 等级需要的积分数/每年
         */
        @ApiModelProperty(value = "级需要的积分数/每年")
//        @QueryParam("integral")
        private Integer integral;

        /**
         * 等级需要的金额/累计
         */
        @ApiModelProperty(value = "等级需要的金额/累计")
//        @QueryParam("price")
        private Integer price;

        @ApiModelProperty(value = "联系方式")
//        @QueryParam("orderNum")
        private Integer orderNum;

        /**
         * 是否禁用
         */
        @ApiModelProperty(value = "是否禁用")
//        @QueryParam("enable")
        private Boolean enable;

        /**
         * 反积分比例
         */
        @ApiModelProperty(value = "反积分比例")
//        @QueryParam("payRate")
        private Integer payRate;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIntegral() {
            return integral;
        }

        public void setIntegral(Integer integral) {
            this.integral = integral;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(Integer orderNum) {
            this.orderNum = orderNum;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public Integer getPayRate() {
            return payRate;
        }

        public void setPayRate(Integer payRate) {
            this.payRate = payRate;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getSrteet() {
        return srteet;
    }

    public void setSrteet(String srteet) {
        this.srteet = srteet;
    }

    public MemberLevel getLevel() {
        return level;
    }

    public void setLevel(MemberLevel level) {
        this.level = level;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LinkedHashSet getContacts() {
        return contacts;
    }

    public void setContacts(LinkedHashSet contacts) {
        this.contacts = contacts;
    }
}
