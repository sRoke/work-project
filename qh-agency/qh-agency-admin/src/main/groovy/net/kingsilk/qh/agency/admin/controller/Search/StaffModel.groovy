package net.kingsilk.qh.agency.admin.controller.Search

import net.kingsilk.qh.agency.domain.Staff

/**
 * Created by lit on 17-4-5.
 */
class StaffModel {
    private String id
    private  String realName;
    String getId() {
        return id
    }

    String getRealName() {
        return realName
    }

    void setId(String id) {
        this.id = id
    }

    void setRealName(String realName) {
        this.realName = realName
    }

    void convert(Staff staff) {
        if (!staff) {
            return;
        }
        realName = staff.realName
        id=staff.id
    }
}
