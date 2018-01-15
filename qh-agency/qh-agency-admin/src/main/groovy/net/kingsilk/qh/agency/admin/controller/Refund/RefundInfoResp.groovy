package net.kingsilk.qh.agency.admin.controller.Refund

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum
import net.kingsilk.qh.agency.core.OrderOperateEnum
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.core.RefundStatusEnum
import net.kingsilk.qh.agency.core.RefundTypeEnum
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.OrderLog
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.domain.Sku

/**
 * Created by yanfq
 */
@ApiModel(value = "RefundInfoResp")
class RefundInfoResp {

    @ApiParam(value="id")
    String id;

    RefundTypeEnum type;

    String typeDesp;

    RefundStatusEnum status;

    String statusDesp;

    Integer applyNum;

    /**
     * 需退款的最终金额(单价：分)
     */
    Integer refundAmount;

    String reason;

    String rejectReason;

    /**
     * 用户发货时间
     */
    Date deliveryTime;
    /**
     * 后台确认收货时间
     */
    Date receiveTime;

    Date dateCreated;

    String memo;
    /**
     * 买家
     */
    String realName;

    String phone;

    String orderId;
    /**
     * 成交时间
     */
    Date orderDate;

    String skuImg;

    List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

    String itemTitle;
    Map<String,OrderLogInfo> orderLogInfoMap = new HashMap<String,OrderLogInfo>();
    Map<String,String> logisticsMap = new HashMap<String,String>();

    RefundInfoResp convertRefundToResp(Refund refund, List<PartnerStaff> members, List<OrderLog> orderLogs) {
        this.id = refund.id
        this.type = refund.refundType
        this.typeDesp = refund.refundType.desp
        this.status = refund.status
        this.statusDesp = refund.status.desp
        //this.applyNum = refund.applyNum
        this.refundAmount = refund.refundAmount
        this.status = refund.status
        this.reason = refund.reason
        this.rejectReason = refund.rejectReason
        this.deliveryTime = refund.deliveryTime
        this.receiveTime = refund.receiveTime
        this.dateCreated = refund.dateCreated
        this.memo = refund.memo
        if(members.size()>0){
            this.realName = members.get(0).realName
            this.phone = members.get(0).phone
        }
        this.orderId = refund.order.seq
        this.orderDate = refund.order.dateCreated
        this.skuImg = refund.sku.item.imgs[0]
        this.itemTitle = refund.sku.item.title
        for(Sku.Spec spec : refund.sku.specs){
            this.specInfos.add(new SpecInfo().convertSpecToResp(spec))
        }
        if(orderLogs.size()>0){
            for(OrderLog orderLog : orderLogs){
                if(!orderLogInfoMap.containsKey(orderLog.operate.code)){
                    this.orderLogInfoMap.put(orderLog.operate.code,new OrderLogInfo().convertOrderLogToResp(orderLog))
                }
            }
        }
        this.logisticsMap = LogisticsCompanyEnum.getMap()
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
    static class OrderLogInfo {
        OrderStatusEnum orderStatus;
        OrderOperateEnum operate;
        Integer price = 0;
        Date dateCreated;

        OrderLogInfo convertOrderLogToResp(OrderLog orderLog) {
            this.operate = orderLog.operate
            this.price = orderLog.price
            this.dateCreated = orderLog.dateCreated
            this.orderStatus = orderLog.status
            return this;
        }
    }
}
