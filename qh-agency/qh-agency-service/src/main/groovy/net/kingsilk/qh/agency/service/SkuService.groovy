package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Sku
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class SkuService {

    @Autowired
    PartnerStaffService memberService

    /**
     * 取出当前sku，符合条件的最低标签价
     * @param sku
     * @param tags
     * @return
//     */
    Sku.TagPrice getTagPrice(Sku sku, String tags) {
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it0 ->
            return tags==it0.tag.code
        }.min { Sku.TagPrice it0 ->
            it0.price
        }
        return minTag
    }

    Sku.TagPrice getTagPrice(Sku sku) {
        PartnerStaff curMember = memberService.getCurPartnerStaff()
        String tags = curMember?.partner?.partnerTypeEnum?.code
        return this.getTagPrice(sku, tags)
    }


}
