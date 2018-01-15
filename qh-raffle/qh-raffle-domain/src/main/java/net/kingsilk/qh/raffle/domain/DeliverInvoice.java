package net.kingsilk.qh.raffle.domain;


import net.kingsilk.qh.raffle.core.DeliverStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 发货单
 */
@Document
public class DeliverInvoice extends Base {

    /**
     * 应用的Id。
     */
    private String raffleAppId;

    /**
     * 应用的Id。
     */
    private String raffleId;

    /**
     * 系统编号
     */
    private String seq;

    /**
     * 关联的order的id
     */
    private String recordId;

    /**
     * 发货单状态
     */
    private DeliverStatusEnum deliverStatus;

    /**
     * 关联的物流信息
     */
    private String logisticsesId;

    /**
     * 发货员工
     */
    private String deliverStaff;

    /**
     * 收货地址，从order中copy
     */
    private OrderAddress receiverAddr;

    public class OrderAddress {

        /**
         * 六位ADC地区码
         */
        private String adc;

        /**
         * 街道
         */
        private String street;

        /**
         * 收货人
         */
        private String receiver;

        /**
         * 收货人联系方式
         */
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

    public String getRaffleAppId() {
        return raffleAppId;
    }

    public void setRaffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public DeliverStatusEnum getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(DeliverStatusEnum deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public String getLogisticsesId() {
        return logisticsesId;
    }

    public void setLogisticsesId(String logisticsesId) {
        this.logisticsesId = logisticsesId;
    }

    public String getDeliverStaff() {
        return deliverStaff;
    }

    public void setDeliverStaff(String deliverStaff) {
        this.deliverStaff = deliverStaff;
    }

    public OrderAddress getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(OrderAddress receiverAddr) {
        this.receiverAddr = receiverAddr;
    }
}
