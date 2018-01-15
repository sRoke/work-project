package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.Logistics
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.domain.Sku

/**
 * Created by yanfq
 */
@ApiModel(value = "OrderInfoResp")
class OrderInfoResp{

    @ApiParam(value="id")
    String id;
    @ApiParam(value="订单号")
    String seq;

    @ApiParam(value="支付方式")
    String payType;

    @ApiParam(value="支付方式")
    Date payTime;
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

    @ApiParam(value="是否存在退款请求")
     Boolean haveRefund = false;

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
    /**
     * 收货地址
     */
    @ApiParam(value="地址")
    AddressInfo address;

    @ApiParam(value="创建时间")
    Date dateCreated

    /**
     * 下单的商品
     */
    @ApiParam(value="下单的商品信息")
     List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

    @ApiParam(value = "物流")
    Set<LogisticInfo> logisticses = new LinkedHashSet<>();

    Map<String,String> companyEnums = new HashMap<String,String>();


    OrderInfoResp convertOrderToResp(Order order, List<PartnerStaff> members, List<Refund> refunds) {
        this.id = order.id
        this.seq = order.seq
        this.payType=order.payType
        this.payTime=order.payTime
        if(members.size()>0){
            this.realName = members.get(0).realName
            this.phone = members.get(0).phone
        }
        if(refunds.size()>0){
            this.haveRefund = true
        }
        this.orderPrice = order.orderPrice
        this.paymentPrice = order.paymentPrice
        this.status = order.status
        this.statusDesp = order.status.desp
        if(order.orderAddress){
            this.address = new AddressInfo().convertAddressToResp(order.orderAddress)
        }else{
            this.address = new AddressInfo().convertAddressToResp(order.address)
        }
        this.dateCreated = order.dateCreated
        for(Order.OrderItem orderItem : order.orderItems){
            OrderItemInfo orderItemInfo = new OrderItemInfo()
            orderItemInfo.convertOrderItemToResp(orderItem)
            this.orderItems.add(orderItemInfo)
        }
        for(Logistics logistics : order.logisticses){
            LogisticInfo logisticInfo = new LogisticInfo()
            logisticInfo.convertLogisticToResp(logistics)
            this.logisticses.add(logisticInfo)
        }
        companyEnums = LogisticsCompanyEnum.getMap();
        return this;
    }

    static class OrderItemInfo{
        String skuImg;
        String skuTitle;
        String itemTitle;
        String skuPrice;
        String num;
        List<SpecInfo> specInfos = new ArrayList<SpecInfo>();
        OrderItemInfo convertOrderItemToResp(Order.OrderItem orderItem) {
            this.skuImg = orderItem.sku.item.imgs[0]
            this.skuTitle = orderItem.sku.title
            this.itemTitle = orderItem.sku.item.title
            this.skuPrice = orderItem.sku.price
            this.num = orderItem.num
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
    static class AddressInfo{
        String receiver;
        String street;
        String phone;
        String province;//省
        String provinceNo;
        String city;//市
        String cityNo
        String county;//区县
        String countyNo;
        AddressInfo convertAddressToResp(def address) {
            this.receiver = address.receiver
            this.street = address.street
            this.phone = address.phone
            if(address.adc){
                this.county = address.adc.name
                this.countyNo = address.adc.no
                if(address.adc.parent){
                    this.city = address.adc.parent.name
                    this.cityNo = address.adc.parent.no
                    if(address.adc.parent.parent){
                        this.province = address.adc.parent.parent.name
                        this.provinceNo = address.adc.parent.parent.no
                    }
                }
            }
            return this;
        }
    }

    static class LogisticInfo{
        String company;
        String expressNo;
        LogisticInfo convertLogisticToResp(Logistics logistic) {
            if(logistic.company){
                this.company = logistic.company.desp
            }
            this.expressNo = logistic.expressNo
            return this;
        }
    }

}
