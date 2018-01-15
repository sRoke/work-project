package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import net.kingsilk.qh.agency.domain.Order.OrderItem
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.domain.Sku
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.mapping.DBRef

@ApiModel(value = "订单分页返回信息")
class OrderPageResp {

    Map<String,Integer> dataCountMap;
    Map<String,String> orderStatusEnumMap;
    Page<OrderInfo> recPage


    @ApiModel(value = "订单信息")
    static class OrderInfo {

        @ApiParam(value="id")
        String id;

        @ApiParam(value="订单号")
        String seq;
        /**
         * 下单的用户，即代理商
         */
        @ApiParam(value="realName")
        String realName;

        @ApiParam(value="phone")
        String phone;
        /**
         * 订单价，原价，不包含改价
         */
        @ApiParam(value="订单价")
        Integer orderPrice = 0;

        /**
         * 实际支付的金额
         */
        @ApiParam(value="实际支付金额")
        Integer paymentPrice = 0;

        /**
         * 订单当前状态
         */
        @ApiParam(value="订单当前状态")
        OrderStatusEnum status;

        String statusDesp;

        @ApiParam(value="售后")
        List<RefundInfo> refundList = new ArrayList<RefundInfo>();

        @ApiParam(value="创建日期")
        Date dateCreated;
        /**
         * 下单的商品
         */
        @ApiParam(value="下单的商品信息")
        List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();
    }

    static class RefundInfo{
        String id;
        String skuId;
        String status;
        RefundInfo convertRefundToResp(Refund refund) {
            this.id = refund.id
            this.skuId = refund.sku ? refund.sku.id : ""
            this.status = refund.status.desp
            return this;
        }
    }

    static class OrderItemInfo{
        String skuImg;
        String skuTitle;
        String itemTitle;
        String skuPrice;
        String num;
        RefundInfo refundInfo;
        List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

        OrderItemInfo convertOrderItemToResp(OrderItem orderItem) {
            this.skuImg = orderItem.sku.item.imgs[0]
            this.skuTitle = orderItem.sku.title
            this.itemTitle = orderItem.sku.item.title
            this.skuPrice = orderItem.skuPrice
            this.num = orderItem.num
            if(orderItem.refund){
                this.refundInfo = new RefundInfo().convertRefundToResp(orderItem.refund)
            }
            for(Sku.Spec spec : orderItem.sku.specs){
                this.specInfos.add(new SpecInfo().convertSpecToResp(spec))
            }
            return this;
        }

        static class SpecInfo{
            String propName;
            String propValue;
            SpecInfo convertSpecToResp(Sku.Spec spec){
                this.propName = spec.itemProp.name
                this.propValue = spec.itemPropValue.name
                return this
            }
        }
    }

}