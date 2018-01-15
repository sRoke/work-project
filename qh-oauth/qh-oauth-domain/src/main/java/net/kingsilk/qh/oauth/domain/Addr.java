package net.kingsilk.qh.oauth.domain;

import net.kingsilk.qh.oauth.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 用户收货地址。
 */
@Document
@CompoundIndexes({
})
public class Addr extends Base {


    /**
     * 地址类型
     */
    private AddrTypeEnum addrType;

    /**
     * 所属用户
     *
     * 当 addrType 在以下情形时才有值：
     *
     * - AddrTypeEnum.USER_SHIPPING_ADDR
     */
    private String userId;

    /**
     * 所属组织。
     *
     * 当 addrType 在以下情形时才有值：
     *
     * - AddrTypeEnum.ORG_OFFICE_ADDR
     * - AddrTypeEnum.ORG_REG_ADDR
     * - AddrTypeEnum.ORG_SHIPPING_ADDR
     * - AddrTypeEnum.ORG_RETURN_ADDR
     */
    private String orgId;

    /**
     * 收货地址所在省市区.
     *
     * 行政区划代码(6位数字)。
     *
     * 参考 [最新县及县以上行政区划代码（截止2016年7月31日）](http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html)
     */
    private String adc;

    /**
     * 收货街道
     */
    private String street;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 百度地图中经度。
     */
    private String coorX;

    /**
     * 百度地图中纬度。
     */
    private String coorY;


    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系人电话
     */
    private Set<String> phones;

    /**
     * 是否是默认地址
     *
     * 当 addrType in (AddrTypeEnum.USER_SHIPPING_ADDR, ORG_OFFICE_ADDR) 时才可能有值。
     *
     * 同一个用户下只能有一个为 true。
     */
    private boolean defaultAddr;

    /**
     * 用户备注
     */
    private String memo;


    // ------------------------ 自动生成的 getter、 setter

    public AddrTypeEnum getAddrType() {
        return addrType;
    }

    public void setAddrType(AddrTypeEnum addrType) {
        this.addrType = addrType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCoorX() {
        return coorX;
    }

    public void setCoorX(String coorX) {
        this.coorX = coorX;
    }

    public String getCoorY() {
        return coorY;
    }

    public void setCoorY(String coorY) {
        this.coorY = coorY;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
