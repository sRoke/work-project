package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Base
import net.kingsilk.qh.agency.domain.StaffGroup
import org.springframework.data.domain.Page

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
 class StaffGroupPageResp extends Base {

    Page<StaffGroupMini> recPage;

   @ApiModel(value = "StaffGroupInfo")
     static class StaffGroupMini extends Base {
        /**
         * 用户组id
         */

        String id;
        /**
         * 用户组名称
         */
        @ApiModelProperty(value = "用户组名称")
        String name;

        /**
         * 对应的员工数量
         */
        @ApiModelProperty(value = "对应的员工数量")
        int staffSize;


        @ApiModelProperty(value = "状态")
        Boolean status;

    }
}
