package net.kingsilk.qh.agency.admin.controller.Search

import net.kingsilk.qh.agency.admin.controller.BasePageResp
import net.kingsilk.qh.agency.domain.Staff
import org.springframework.data.domain.Page

/**
 * Created by lit on 17-4-5.
 */
class StaffListResp extends BasePageResp<StaffModel>{
    void convert(Page<Staff> page, StaffListReq req) {
        pageSize = req.pageSize
        curPage = req.curPage + 1
        totalCount = page.totalElements
        totalPages = page.totalPages
        recList = new ArrayList<>()
        page.content.each { Staff it ->
            StaffModel info = new StaffModel()
            info.convert(it)
            recList.add(info)
        }
    }
}
