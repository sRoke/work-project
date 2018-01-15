package net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto;


/**
 * Created by yk on 17/8/31.
 */
public class PartnerAccountLogResp {

    /**
     * 订单id
     */
    private String id;

    /**
     * 所属渠道商
     */
    private String partnerId;
    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 交易编号
     */
    private String seq;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}

