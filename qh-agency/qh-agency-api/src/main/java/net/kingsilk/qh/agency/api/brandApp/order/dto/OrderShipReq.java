package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum;

@Deprecated
@ApiModel(value = "订单修改提供信息")
public class OrderShipReq {

    @ApiParam(value = "id")
    private String id;

    @ApiParam(value = "物流公司")
    private LogisticsCompanyEnum company;

    @ApiParam(value = "物流单号")
    private String expressNo;
}
