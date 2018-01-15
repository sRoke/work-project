package net.kingsilk.qh.oauth.domain;

import net.kingsilk.qh.oauth.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * 组织表。
 *
 * 只管理是否是员工，不管理其员工在各个子系统中的权限。
 * 可以理解为：等价于 微信企业号中 的通讯录。
 *
 * 允许一个人创建多个组织。
 */
@Document
@CompoundIndexes({
})
public class Org extends Base {

    /**
     * 所属用户ID
     */
    private String userId;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 状态
     */
    private OrgStatusEnum status;

    /**
     * 办公地址
     */
    private Addr officeAddr;

    /**
     * 注册地址
     */
    private Addr regAddr;

    /**
     * 组织收货地址
     */
    private Addr shippingAddr;

    /**
     * 组织收货地址
     */
    private Addr refundAddr;
    // ------------------------ 自动生成的 getter、 setter


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrgStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrgStatusEnum status) {
        this.status = status;
    }

    public Addr getOfficeAddr() {
        return officeAddr;
    }

    public void setOfficeAddr(Addr officeAddr) {
        this.officeAddr = officeAddr;
    }

    public Addr getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(Addr regAddr) {
        this.regAddr = regAddr;
    }

    public Addr getShippingAddr() {
        return shippingAddr;
    }

    public void setShippingAddr(Addr shippingAddr) {
        this.shippingAddr = shippingAddr;
    }

    public Addr getRefundAddr() {
        return refundAddr;
    }

    public void setRefundAddr(Addr refundAddr) {
        this.refundAddr = refundAddr;
    }
}
