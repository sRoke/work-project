package net.kingsilk.qh.agency.admin.controller.StaffGroup

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Base

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupPageReq extends Base {

    @ApiParam(value = "当前页数", defaultValue = "1")
    @ApiModelProperty(value = "")
     Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
     Integer pageSize = 15;

    @ApiParam(value = "帐号")
     String keyWord;

    @ApiParam(value = "开始时间")
     Date startDate;


    @ApiParam(value = "结束时间")
     Date endDate;

}
