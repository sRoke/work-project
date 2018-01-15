package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Base

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupLoadCurrentUserResp")
 class StaffGroupLoadCurrentUserResp extends Base {


    String source
    Set<String> currentAuthor

     StaffGroupLoadCurrentUserResp convertToResp(String source, String userId, Set<String> currentAuthor) {
        this.setSource(source)
        this.setId(userId)
        this.setCurrentAuthor(currentAuthor)
        return this
    }

}
