package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Base

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupLoadResp")
 class StaffGroupLoadResp extends Base {
    Map<String, Map<String, Map<String, String>>> authorMap

     StaffGroupLoadResp convertToResp(Map<String, Map<String, Map<String, String>>> authorMap) {
        this.setAuthorMap(authorMap)
        return this
    }

}
