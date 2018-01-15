package net.kingsilk.qh.agency.admin.controller.company

import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Staff

/**
 * Created by tpx on 17-3-20.
 */
public class CompanySaveReq {

    private String id;

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

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getBizLicenseNo() {
        return bizLicenseNo
    }

    void setBizLicenseNo(String bizLicenseNo) {
        this.bizLicenseNo = bizLicenseNo
    }

    String getLegalPerson() {
        return legalPerson
    }

    void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson
    }

    String getOfficeAddr() {
        return officeAddr
    }

    void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr
    }

    String getContacts() {
        return contacts
    }

    void setContacts(String contacts) {
        this.contacts = contacts
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    public Company convertReqToCompany(Company company) {
        company.setBizLicenseNo(this.getBizLicenseNo());
        company.setName(this.getName());
        company.setLegalPerson(this.getLegalPerson());
        company.setLastModifiedDate(new Date());
        company.setOfficeAddr(this.getOfficeAddr());
        company.setContacts(this.getContacts());
        company.setPhone(this.getPhone());
        return company;
    }
}
