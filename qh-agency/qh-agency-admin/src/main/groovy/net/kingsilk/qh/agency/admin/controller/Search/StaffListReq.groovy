package net.kingsilk.qh.agency.admin.controller.Search

import net.kingsilk.qh.agency.admin.controller.BasePageReq

/**
 * Created by lit on 17-4-5.
 */
class StaffListReq extends BasePageReq{
    private String staffKeyword

    String getStaffKeyword() {
        return staffKeyword
    }

    void setStaffKeyword(String staffKeyword) {
        this.staffKeyword = staffKeyword
    }
}
