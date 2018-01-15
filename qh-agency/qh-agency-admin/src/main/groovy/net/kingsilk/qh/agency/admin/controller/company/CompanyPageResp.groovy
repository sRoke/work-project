package net.kingsilk.qh.agency.admin.controller.company

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Base
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.util.Page

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "公司分页返回信息")
public class CompanyPageResp {

    private Integer curPage;

    private Integer pageSize;

    private Integer totalCount;

    private List<CompanyMinInfo> recList = new ArrayList<CompanyMinInfo>();

    Integer getCurPage() {
        return curPage
    }

    void setCurPage(Integer curPage) {
        this.curPage = curPage
    }

    Integer getPageSize() {
        return pageSize
    }

    void setPageSize(Integer pageSize) {
        this.pageSize = pageSize
    }

    Integer getTotalCount() {
        return totalCount
    }

    void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount
    }

    List<CompanyMinInfo> getRecList() {
        return recList
    }

    void setRecList(List<CompanyMinInfo> recList) {
        this.recList = recList
    }

    public CompanyPageResp convertToResp(Page<Company> companys) {
        this.pageSize = companys.pageSize;
        this.curPage = companys.curPage;
        this.totalCount = companys.totalCount;
        for (Company company in companys.list) {
            this.recList.add(new CompanyMinInfo().convertToResp(company))
        }
        return this;
    }

    public static class CompanyMinInfo extends Base {

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

        public CompanyMinInfo convertToResp(Company company) {
            this.setBizLicenseNo(company.getBizLicenseNo());
            this.setName(company.getName());
            this.setLegalPerson(company.getLegalPerson());
            this.setLastModifiedDate(company.getLastModifiedDate());
            this.setOfficeAddr(company.getOfficeAddr());
            this.setContacts(company.getContacts());
            this.setPhone(company.getPhone());
            this.setDateCreated(company.getDateCreated());
            this.setId(company.getId());
            return this;
        }
    }
}