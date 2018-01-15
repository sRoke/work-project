package net.kingsilk.qh.agency.wap.controller.addr

import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.wap.controller.BasePageResp
import net.kingsilk.qh.agency.wap.controller.addr.model.AddrModel
import org.springframework.data.domain.Page

/**
 * Created by zcw on 3/16/17.
 */
class AddrListResp extends BasePageResp<Address, AddrModel> {

    void convert(Page<Address> page, AddrListReq req) {
        super.convert(page, req)
        recList = []
        page.content.each { Address it ->
            AddrModel info = new AddrModel()
            info.convert(it)
            recList.add(info)
        }
    }
}
