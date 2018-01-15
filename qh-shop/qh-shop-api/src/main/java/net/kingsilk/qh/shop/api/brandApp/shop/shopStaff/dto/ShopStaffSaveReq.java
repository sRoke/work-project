package net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto;

import java.util.ArrayList;
import java.util.List;

public class ShopStaffSaveReq {

    private String realName;

    private String phone;

    private String shopId;

    private Boolean enable;

    private String memo;

    private List<String> staffGroupIds = new ArrayList<>();

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<String> getStaffGroupIds() {
        return staffGroupIds;
    }

    public void setStaffGroupIds(List<String> staffGroupIds) {
        this.staffGroupIds = staffGroupIds;
    }
}
