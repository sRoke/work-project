package net.kingsilk.qh.agency.admin.api.partner.dto;

import io.swagger.annotations.ApiParam;

/**
 *
 */
public class PartnerSaveReq {
    @ApiParam(value = "渠道商的ID")
    private String id;


    @ApiParam(required = true, value = "真实姓名")
    private String realName;


    @ApiParam(required = true, value = "手机号")
    private String phone;


    @ApiParam(required = true, value = "店铺地址")
    private String shopAddr;

    /**
     * 上级渠道商ID
     */
    @ApiParam(required = true, value = "上级渠道商ID")
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
