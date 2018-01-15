package net.kingsilk.qh.agency.wap.api.partner.dto;

/**
 *
 */
public class PartnerInfoResp {
    /**
     * id
     */
    private String id;
    /**
     * 编号
     */
    private String seq;
    /**
     * 代理类型
     */
    private String partnerType;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 联系人
     */
    private String userName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 等级
     */
    private String leave;
    /**
     * 店铺地址
     */
    private String addr;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
