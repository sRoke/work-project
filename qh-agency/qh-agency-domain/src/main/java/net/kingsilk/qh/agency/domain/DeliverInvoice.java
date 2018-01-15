package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.DeliverStatusEnum;
import net.kingsilk.qh.agency.core.SourceTypeEnum;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 发货单
 */
@Document
public class DeliverInvoice extends Base {

    /**
     * 品牌商ID
     */
    @Indexed
    private String brandAppId;

    /**
     * 发货渠道商ID
     * ps. 一般为上级渠道商，如果该字段为空，则代表由品牌商发货
     */
    @DBRef
    private Partner deliverPartner;

    /**
     * 编号
     */
    private String seq;

    /**
     * 进货单/订单
     */
    @DBRef
    private Order order;

    /**
     * 来源类型
     */
    private SourceTypeEnum sourceTypeEnum = SourceTypeEnum.PARTNER_DELIVER;

    /**
     * 买方渠道商
     */
    @DBRef
    private Partner buyerPartner;

    /**
     * 发货状态
     */
    private DeliverStatusEnum deliverStatusEnum = DeliverStatusEnum.UNSHIPPED;

    /**
     * 关联的物流信息。
     */
    @DBRef
    private Set<Logistics> logisticses = new LinkedHashSet<>();


    /**
     * 渠道商收货地址
     */
    private Order.OrderAddress orgShippingAddr;

    /**
     * 发货人
     */
    @DBRef
    private Staff deliverStaff;

    public static class OrderAddress {
        private String adc;
        private String street;
        private String receiver;
        private String phone;

        public String getAdc() {
            return adc;
        }

        public void setAdc(String adc) {
            this.adc = adc;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Partner getDeliverPartner() {
        return deliverPartner;
    }

    public void setDeliverPartner(Partner deliverPartner) {
        this.deliverPartner = deliverPartner;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SourceTypeEnum getSourceTypeEnum() {
        return sourceTypeEnum;
    }

    public void setSourceTypeEnum(SourceTypeEnum sourceTypeEnum) {
        this.sourceTypeEnum = sourceTypeEnum;
    }

    public Partner getBuyerPartner() {
        return buyerPartner;
    }

    public void setBuyerPartner(Partner buyerPartner) {
        this.buyerPartner = buyerPartner;
    }

    public DeliverStatusEnum getDeliverStatusEnum() {
        return deliverStatusEnum;
    }

    public void setDeliverStatusEnum(DeliverStatusEnum deliverStatusEnum) {
        this.deliverStatusEnum = deliverStatusEnum;
    }

    public Set<Logistics> getLogisticses() {
        return logisticses;
    }

    public void setLogisticses(Set<Logistics> logisticses) {
        this.logisticses = logisticses;
    }

    public Order.OrderAddress getOrgShippingAddr() {
        return orgShippingAddr;
    }

    public void setOrgShippingAddr(Order.OrderAddress orgShippingAddr) {
        this.orgShippingAddr = orgShippingAddr;
    }

    public Staff getDeliverStaff() {
        return deliverStaff;
    }

    public void setDeliverStaff(Staff deliverStaff) {
        this.deliverStaff = deliverStaff;
    }
}
