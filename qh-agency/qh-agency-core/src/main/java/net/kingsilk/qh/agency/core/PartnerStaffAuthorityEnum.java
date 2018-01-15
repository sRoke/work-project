package net.kingsilk.qh.agency.core;

/**
 * 平台会员权限代码
 */
public enum PartnerStaffAuthorityEnum {

    /**
     * 是否有会员权限
     */
    PARTNERSTAFF("会员");


    private final String desp;
    private final PartnerStaffAuthorityEnum[] children;

    PartnerStaffAuthorityEnum(String desp, PartnerStaffAuthorityEnum... children) {

        this.desp = desp;
        this.children = children;
    }

    public String getDesp() {
        return desp;
    }

    public PartnerStaffAuthorityEnum[] getChildren() {
        return children;
    }
}