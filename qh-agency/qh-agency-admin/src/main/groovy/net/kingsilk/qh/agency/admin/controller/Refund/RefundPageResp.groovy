package net.kingsilk.qh.agency.admin.controller.Refund

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.RefundStatusEnum
import net.kingsilk.qh.agency.core.RefundTypeEnum
import net.kingsilk.qh.agency.domain.Logistics
import net.kingsilk.qh.agency.domain.Sku
import org.springframework.data.domain.Page

@ApiModel(value = "订单分页返回信息")
class RefundPageResp {

    Map<String, String> refundTypeEnumMap;

    Map<String, String> refundStatusEnumMap;

    Map<String, String> refundReasonEnumMap;

    Page<RefundInfo> recPage

    @ApiModel(value = "订单信息")
    static class RefundInfo {
        @ApiParam(value = "id")
        String id;

        SkuInfo skuInfo;

        RefundTypeEnum type;

        RefundStatusEnum status;

        String statusDesp;

        String realName;

        String phone;

        String orderSeq;

        Integer orderAmount;
        /**
         * 需退款的最终金额(单价：分)
         */
        Integer refundAmount;

        String reason;

        String rejectReason;

        LogisticsInfo logisticsInfo;

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
    }

    static class SkuInfo {
        String id;
        String title;
        String skuTitle;
        String skuImage;
        List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

        SkuInfo convertSkuToResp(Sku sku) {
            this.id = sku.id
            this.title = sku.item.title
            this.skuTitle = sku.title
            this.skuImage = sku.item.imgs[0]
            for(Sku.Spec spec : sku.specs){
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

    static class LogisticsInfo {

        String company;

        String expressNo;

        LogisticsInfo convertLogisticsToResp(Logistics logistics) {
            if(logistics.company){
                this.company = logistics.company.desp
            }
            this.expressNo = logistics.expressNo
            return this;
        }
    }

}