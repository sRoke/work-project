package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 收货地址
 */
//FIXME 地址信息是否完全移除，
//FIXME 或者在销售宝中地址信息更换到渠道商级别，和用户的收货地址无关，用户的收货地址是否要放到oauth中
@Document
public class Address extends Base {

    //TODO user
//    @DBRef
//    private Partner partner;

    /**
     * 用户备注
     */
    private String memo;

    /**
     * 收货地区
     */
    @DBRef
    private Adc adc;

    /**
     * 收货街道
     */
    private String street;

    /**
     * 收件人姓名
     */
    private String receiver;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否是默认地址
     */
    private boolean defaultAddr;

    //////////////////////setter、getter

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Adc getAdc() {
        return adc;
    }

    public void setAdc(Adc adc) {
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

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
