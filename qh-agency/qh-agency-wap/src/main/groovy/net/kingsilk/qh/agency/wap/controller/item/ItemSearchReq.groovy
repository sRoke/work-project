package net.kingsilk.qh.agency.wap.controller.item

import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.wap.controller.BasePageReq

class ItemSearchReq {

    @ApiParam(value = "当前页数", required = false)
    int number;

    @ApiParam(value = "分页大小", required = false)
    int pageSize;

    @ApiParam(value = "类型", required = false)
    String type;

    @ApiParam(value = "排序方式", required = false, allowableValues = "ASC, DESC")
    String sort = "DESC"

    @ApiParam(value = "排序依据", required = false)
    String sortBy = "dateCreated"

    @ApiParam(value = "商品分类", required = false)
    String categoryId

}
