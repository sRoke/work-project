package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 公司信息。比如：郝太太总部。
 */
@Deprecated
@Document
public class Company extends Base {

    //TODO 去除所有company相关代码
    /**
     * 公司名称
     */
    @Indexed(unique = true)
    private String name;

    /**
     * 营业执照编号。
     * <p>
     * business licence number
     */
    private String bizLicenseNo;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 办公地址
     */
    private String officeAddr;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    // --------------------------------------- getter && setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBizLicenseNo() {
        return bizLicenseNo;
    }

    public void setBizLicenseNo(String bizLicenseNo) {
        this.bizLicenseNo = bizLicenseNo;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getOfficeAddr() {
        return officeAddr;
    }

    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
