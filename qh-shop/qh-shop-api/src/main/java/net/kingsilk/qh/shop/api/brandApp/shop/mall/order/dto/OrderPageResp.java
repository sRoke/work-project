package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.shop.api.UniPageResp;

import java.util.Map;

@ApiModel(value = "订单分页返回信息")
public class OrderPageResp<OrderMiniInfo> extends UniPageResp<OrderMiniInfo> {


    private Map<String, Integer> dataCountMap;
    private Map<String, String> orderStatusEnumMap;


    public Map<String, Integer> getDataCountMap() {
        return dataCountMap;
    }

    public void setDataCountMap(Map<String, Integer> dataCountMap) {
        this.dataCountMap = dataCountMap;
    }

    public Map<String, String> getOrderStatusEnumMap() {
        return orderStatusEnumMap;
    }

    public void setOrderStatusEnumMap(Map<String, String> orderStatusEnumMap) {
        this.orderStatusEnumMap = orderStatusEnumMap;
    }

}
