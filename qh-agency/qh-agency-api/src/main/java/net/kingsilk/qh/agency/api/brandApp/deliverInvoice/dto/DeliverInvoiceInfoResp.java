package net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.order.dto.LogisticInfo;
import net.kingsilk.qh.agency.core.DeliverStatusEnum;
import net.kingsilk.qh.agency.core.SourceTypeEnum;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
     * 订单ID，具体订单信息，请前端人员调用Order/info接口
     */
    @ApiParam(value = "进货单")
    private String orderId;

    private String seq;

    private String deliverStaffId;
    /**
     * 来源类型
     */
    @ApiParam(value = "来源类型")
    private SourceTypeEnum sourceTypeEnum = SourceTypeEnum.PARTNER_DELIVER;

    @ApiParam(value = "发货渠道商")
    private String partnerId;


    @ApiParam(value = "物流公司列表")
    private Map<String, String> logisticsCompanys = new HashMap<>();
    /**
     * 发货状态
     */
    @ApiParam(value = "发货状态")
    private DeliverStatusEnum deliverStatusEnum = DeliverStatusEnum.UNSHIPPED;

    @ApiParam(value = "物流")
    private Set<LogisticInfo> logisticses = new LinkedHashSet<LogisticInfo>();

    @ApiParam(value = "发货状态描述")
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

    public Set<LogisticInfo> getLogisticses() {
        return logisticses;
    }

    public void setLogisticses(Set<LogisticInfo> logisticses) {
        this.logisticses = logisticses;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SourceTypeEnum getSourceTypeEnum() {
        return sourceTypeEnum;
    }

    public void setSourceTypeEnum(SourceTypeEnum sourceTypeEnum) {
        this.sourceTypeEnum = sourceTypeEnum;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public DeliverStatusEnum getDeliverStatusEnum() {
        return deliverStatusEnum;
    }

    public Map<String, String> getLogisticsCompanys() {
        return logisticsCompanys;
    }

    public void setLogisticsCompanys(Map<String, String> logisticsCompanys) {
        this.logisticsCompanys = logisticsCompanys;
    }

    public void setDeliverStatusEnum(DeliverStatusEnum deliverStatusEnum) {
        this.deliverStatusEnum = deliverStatusEnum;


    }

    public String getDeliverStaffId() {
        return deliverStaffId;
    }

    public void setDeliverStaffId(String deliverStaffId) {
        this.deliverStaffId = deliverStaffId;
    }
}
