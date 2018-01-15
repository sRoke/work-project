package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;

/**
 * Created by zcw on 3/16/17.
 * 订单列表返回信息
 */
public class OrderListResp  {
    public Page<OrderInfoModel> getOrderInfoModel() {
        return orderInfoModel;
    }

    public void setOrderInfoModel(Page<OrderInfoModel> orderInfoModel) {
        this.orderInfoModel = orderInfoModel;
    }

    @ApiParam(value = "内容", required = false)
    private Page<OrderInfoModel> orderInfoModel;
}
