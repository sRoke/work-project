package net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto;

import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopStaffInfoResp {

    @ApiParam(value = "id")
    private String id;
    /**
     * 账户
     */
    @ApiParam(value = "用户id")
    private String userId;

    @ApiParam(value = "门店id")
    private String shopId;

    @ApiParam(value = "用户姓名")
    private String realName;
    @ApiParam(value = "用户手机")
    private String phone;


    @ApiParam(value = "用户组")
    private List<ShopStaffGroupModel> staffGroupList = new ArrayList<>();
    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    @ApiParam(value = "是否禁用")
    private boolean disabled;
    /**
     * 备注
     */
    @ApiParam(value = "备注")
    private String memo;
    @ApiParam(value = "删除")
    private boolean deleted;
    @ApiParam(value = "最后修改时间")
    private Date lastModifiedDate;
    @ApiParam(value = "创建时间")
    private Date dateCreated;

    private String shopName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<ShopStaffGroupModel> getStaffGroupList() {
        return staffGroupList;
    }

    public void setStaffGroupList(List<ShopStaffGroupModel> staffGroupList) {
        this.staffGroupList = staffGroupList;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
