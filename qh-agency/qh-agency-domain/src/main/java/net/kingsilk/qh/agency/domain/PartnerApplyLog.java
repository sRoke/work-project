package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 渠道商申请记录表
 */
@Document
public class PartnerApplyLog extends Base {

    /**
     * 申请的渠道商
     */
    @DBRef
    private Partner partner;

    /**
     * 渠道商状态
     */
    private PartnerApplyStatusEnum partnerApplyStatus;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 渠道商类型
     * 比如："代理商", "加盟商"
     */
    private PartnerTypeEnum PartnerTypeEnum;


    /**
     * 审核人
     */
    @DBRef
    private Staff staff;


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public PartnerApplyStatusEnum getPartnerApplyStatus() {
        return partnerApplyStatus;
    }

    public void setPartnerApplyStatus(PartnerApplyStatusEnum partnerApplyStatus) {
        this.partnerApplyStatus = partnerApplyStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public net.kingsilk.qh.agency.core.PartnerTypeEnum getPartnerTypeEnum() {
        return PartnerTypeEnum;
    }

    public void setPartnerTypeEnum(net.kingsilk.qh.agency.core.PartnerTypeEnum partnerTypeEnum) {
        PartnerTypeEnum = partnerTypeEnum;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
