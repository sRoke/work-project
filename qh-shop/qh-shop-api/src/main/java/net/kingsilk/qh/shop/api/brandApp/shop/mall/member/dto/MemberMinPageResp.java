package net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashSet;
@ApiModel
public class MemberMinPageResp {
    @ApiModelProperty("memberId")
    private String id;
    @ApiModelProperty("brandAppId")
    private String brandAppId;
    @ApiModelProperty("门店id")
    private String shopId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("首字母排序")
    private Integer order;
    @ApiModelProperty("联系方式")
    private LinkedHashSet contacts;



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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedHashSet getContacts() {
        return contacts;
    }

    public void setContacts(LinkedHashSet contacts) {
        this.contacts = contacts;
    }
}
