package net.kingsilk.qh.agency.admin.api.deliverInvoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerInfoResp;
import net.kingsilk.qh.agency.admin.api.order.dto.OrderInfoResp;
import net.kingsilk.qh.agency.core.DeliverStatusEnum;
import net.kingsilk.qh.agency.core.SourceTypeEnum;

/**
 *
 */
@ApiModel(value = "发货单信息返回")
public class DeliverInvoiceInfoResp {

    @ApiParam(value = "id")
    private String id;

    /**
     * 品牌商ID
     */
    @ApiParam(value = "品牌商ID")
    private String brandId;

    /**
     * 进货单/订单
     */
    @ApiParam(value = "进货单")
    private OrderInfoResp order;

    private String seq;

    /**
     * 来源类型
     */
    @ApiParam(value = "来源类型")
    private SourceTypeEnum sourceTypeEnum = SourceTypeEnum.PARTNER_DELIVER;

    @ApiParam(value = "发货渠道商")
    private PartnerInfoResp partner;

    /**
     * 发货状态
     */
    @ApiParam(value = "发货状态")
    private DeliverStatusEnum deliverStatusEnum = DeliverStatusEnum.UNSHIPPED;

    private String statusDesp;

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public OrderInfoResp getOrder() {
        return order;
    }

    public void setOrder(OrderInfoResp order) {
        this.order = order;
    }

    public SourceTypeEnum getSourceTypeEnum() {
        return sourceTypeEnum;
    }

    public void setSourceTypeEnum(SourceTypeEnum sourceTypeEnum) {
        this.sourceTypeEnum = sourceTypeEnum;
    }

    public PartnerInfoResp getPartner() {
        return partner;
    }

    public void setPartner(PartnerInfoResp partner) {
        this.partner = partner;
    }

    public DeliverStatusEnum getDeliverStatusEnum() {
        return deliverStatusEnum;
    }

    public void setDeliverStatusEnum(DeliverStatusEnum deliverStatusEnum) {
        this.deliverStatusEnum = deliverStatusEnum;
    }
}
