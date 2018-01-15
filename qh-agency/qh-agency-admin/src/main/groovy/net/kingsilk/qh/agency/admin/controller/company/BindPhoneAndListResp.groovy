package net.kingsilk.qh.agency.admin.controller.company

import net.kingsilk.qh.agency.domain.Staff

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


    void convert(List<Staff> list) {
        list.each {
            if (!it.brandId) {
                return;
            }
            def info = new CompanyModel()
            info.id = it.brandId
//            info.title = it.company.name
//            info.phone = it.company.phone
//            info.contacts = it.company.contacts
            recList.add(info)
        }
    }
}
