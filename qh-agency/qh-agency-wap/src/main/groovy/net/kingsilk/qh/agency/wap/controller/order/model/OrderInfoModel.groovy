package net.kingsilk.qh.agency.wap.controller.order.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.wap.controller.addr.model.AddrModel
import net.kingsilk.qh.agency.wap.controller.item.model.SkuInfoModel

@ApiModel
class OrderInfoModel {

    @ApiModelProperty(value = "订单id")
    String id;

    @ApiModelProperty(value = "订单编号")
    String seq;

    @ApiModelProperty(value = "订单状态")
    String status;

    @ApiModelProperty(value = "订单状态描述")
    String statusDesp

    @ApiModelProperty(value = "订单原价")
    Integer orderPrice;

    @ApiModelProperty(value = "订单实际支付的金额")
    Integer paymentPrice;

    @ApiModelProperty(value = "用户收货地址")
    AddrModel address

    @ApiModelProperty(value = "商品信息")
    List<OrderSkuModel> items

    void convert(Order order, String[] tags) {
        id = order.id
        seq = order.seq
        status = order.status.code
        statusDesp = order.status.desp
        orderPrice = order.orderPrice
        paymentPrice = order.paymentPrice
        items = []
        order.orderItems.each { Order.OrderItem item ->
            OrderSkuModel sku = new OrderSkuModel()
            sku.convert(item, tags)
            if (item.refund) {
                OrderSkuModel.RefundModel refundModel = new OrderSkuModel.RefundModel()
                refundModel.status = item.refund.status.code
                refundModel.statusDesp = item.refund.status.description
                sku.refund = refundModel
            }
            items.add(sku)
        }
        address = new AddrModel()
        address.convert(order.getAddress())
    }

    /**
     * 同Order中的OrderItem
     */
    @ApiModel
    static class OrderSkuModel {

        @ApiModelProperty(value = "skuId")
        String skuId;

        @ApiModelProperty(value = "sku详情")
        SkuInfoModel skuInfo;

        @ApiModelProperty(value = "当前sku下单数量")
        Integer num = 0;

        @ApiModelProperty(value = "订单中该商品实际支付的金额")
        Integer realTotalPrice = 0;

        @ApiModelProperty(value = "提交订单时刻,sku的单价")
        Integer skuPrice = 0;

        @ApiModelProperty(value = "退货状态")
        RefundModel refund

        void convert(Order.OrderItem item, String[] tags) {
            skuId = item.sku.id
            num = item.num
            realTotalPrice = item.realTotalPrice
            skuPrice = item.skuPrice
            skuInfo = new SkuInfoModel()
            skuInfo.convert(item.sku, tags)
        }

        static class RefundModel {

            @ApiModelProperty(value = '状态')
            String status

            @ApiModelProperty(value = '状态描述')
            String statusDesp;
        }
    }
}
