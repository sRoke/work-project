package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.core.PartnerOperateEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;

/**
 * Created by lit on 17/8/23.
 */
public class PartnerChangeLog extends Base {
    /**
     * 渠道商ID
     */
    private String partnerId;

    /**
     * 品牌商ID
     */
    private String brandAppId;

    /**
     * 渠道商当前类型
     */
    private PartnerTypeEnum partnerTypeEnum;
    /**
     * 前一次渠道商类型
     */
    private PartnerTypeEnum lastPartnerTypeEnum;

    /**
     * 渠道商当前状态
     */
    private PartnerApplyStatusEnum partnerStatusEnum;

    /**
     * 渠道商前一次状态
     */
    private PartnerApplyStatusEnum lastPartnerStatusEnum;

    /**
     * 渠道商操作类型
     */
    private PartnerOperateEnum operate;

    /**
     * 操作员工
     */
    private String staffId;
    public PartnerChangeLog(){

    }

    public PartnerChangeLog(String partnerId, String brandAppId, PartnerTypeEnum partnerTypeEnum, PartnerTypeEnum lastPartnerTypeEnum, PartnerApplyStatusEnum partnerStatusEnum, PartnerApplyStatusEnum lastPartnerStatusEnum, PartnerOperateEnum operate, String staffId) {
        this.partnerId = partnerId;
        this.brandAppId = brandAppId;
        this.partnerTypeEnum = partnerTypeEnum;
        this.lastPartnerTypeEnum = lastPartnerTypeEnum;
        this.partnerStatusEnum = partnerStatusEnum;
        this.lastPartnerStatusEnum = lastPartnerStatusEnum;
        this.operate = operate;
        this.staffId = staffId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public PartnerTypeEnum getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(PartnerTypeEnum partnerTypeEnum) {
        this.partnerTypeEnum = partnerTypeEnum;
    }

    public PartnerTypeEnum getLastPartnerTypeEnum() {
        return lastPartnerTypeEnum;
    }

    public void setLastPartnerTypeEnum(PartnerTypeEnum lastPartnerTypeEnum) {
        this.lastPartnerTypeEnum = lastPartnerTypeEnum;
    }

    public PartnerApplyStatusEnum getPartnerStatusEnum() {
        return partnerStatusEnum;
    }

    public void setPartnerStatusEnum(PartnerApplyStatusEnum partnerStatusEnum) {
        this.partnerStatusEnum = partnerStatusEnum;
    }

    public PartnerApplyStatusEnum getLastPartnerStatusEnum() {
        return lastPartnerStatusEnum;
    }

    public void setLastPartnerStatusEnum(PartnerApplyStatusEnum lastPartnerStatusEnum) {
        this.lastPartnerStatusEnum = lastPartnerStatusEnum;
    }

    public PartnerOperateEnum getOperate() {
        return operate;
    }

    public void setOperate(PartnerOperateEnum operate) {
        this.operate = operate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
