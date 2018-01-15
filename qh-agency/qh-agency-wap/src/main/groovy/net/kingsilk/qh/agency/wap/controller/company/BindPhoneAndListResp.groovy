package net.kingsilk.qh.agency.wap.controller.company

import net.kingsilk.qh.agency.domain.PartnerStaff

class BindPhoneAndListResp {

    List<CompanyModel> recList = []

    static class CompanyModel {

        /**
         * id
         */
        String id;

        /**
         * 公司名称
         */
        String title;

        /**
         * 手机号
         */
        String phone;

        /**
         * 联系人
         */
        String contacts;
    }


    void convert(List<PartnerStaff> list) {
        list.each {
            if (!it.company) {
                return;
            }
            def info = new CompanyModel()
            info.id = it.company.id
            info.title = it.company.name
            info.phone = it.company.phone
            info.contacts = it.company.contacts
            recList.add(info)
        }
    }
}
