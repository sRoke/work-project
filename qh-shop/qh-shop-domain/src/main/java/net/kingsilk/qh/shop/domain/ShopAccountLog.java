package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.AccountChangeTypeEnum;
import net.kingsilk.qh.shop.core.MoneyChangeEnum;
import net.kingsilk.qh.shop.core.WithdrawStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class ShopAccountLog extends Base{
    /**
     * 关联的账户
     */
    private String shopAccountId;

    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 所属品牌商
     */
    private String shopId;

    /**
     * 提现的时候才有的状态,是否提现成功
     */
    private WithdrawStatusEnum status;

    /**
     * 类型
     */
    private AccountChangeTypeEnum type;
    /**
     * 金额，正值代表(充值|退款)，负值(消费|转出|提现)等
     */
    private Integer changeAmount = 0;

    /**
     * 冻结中的余额(单位：分)
     * ps. 采购未结算的钱
     */
    private Integer freezeBalance;

    /**
     * 可提现余额(单位：分)
     * ps. 下级代理商、普通消费者购买的订单已经结算的金额
     */
    private Integer balance;

    /**
     * 不可提现余额(单位：分)
     * ps. 换货时的临时金额。——暗指不能退货
     */
    private Integer noCashBalance;

    /**
     * 来源账户原有冻结中的余额(单位：分)
     */
    private Integer srcFreezeBalance = 0;

    /**
     * 来源账户原有可提现余额(单位：分)
     */
    private Integer srcBalance = 0;

    /**
     * 来源账户原有不可提现余额(单位：分)
     */
    private Integer srcNoCashBalance = 0;


    /**
     * 备注
     */
    private String memo;

    /**
     * 支付
     */
    private String qhPayId;

//    /**
//     * 如果是退款，退款的记录
//     */
//    private String refundId;

    /**
     * 商品订单
     */
    private String orderId;


    /**
     * 提现记录
     */
    private String withdrawId;

    /***
     * 钱款变更动态
     */
    private MoneyChangeEnum moneyChangeEnum;



    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public WithdrawStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WithdrawStatusEnum status) {
        this.status = status;
    }

    public AccountChangeTypeEnum getType() {
        return type;
    }

    public void setType(AccountChangeTypeEnum type) {
        this.type = type;
    }

    public Integer getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Integer changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Integer getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Integer freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getNoCashBalance() {
        return noCashBalance;
    }

    public void setNoCashBalance(Integer noCashBalance) {
        this.noCashBalance = noCashBalance;
    }

    public Integer getSrcFreezeBalance() {
        return srcFreezeBalance;
    }

    public void setSrcFreezeBalance(Integer srcFreezeBalance) {
        this.srcFreezeBalance = srcFreezeBalance;
    }

    public Integer getSrcBalance() {
        return srcBalance;
    }

    public void setSrcBalance(Integer srcBalance) {
        this.srcBalance = srcBalance;
    }

    public Integer getSrcNoCashBalance() {
        return srcNoCashBalance;
    }

    public void setSrcNoCashBalance(Integer srcNoCashBalance) {
        this.srcNoCashBalance = srcNoCashBalance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getQhPayId() {
        return qhPayId;
    }

    public void setQhPayId(String qhPayId) {
        this.qhPayId = qhPayId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(String withdrawId) {
        this.withdrawId = withdrawId;
    }

    public MoneyChangeEnum getMoneyChangeEnum() {
        return moneyChangeEnum;
    }

    public void setMoneyChangeEnum(MoneyChangeEnum moneyChangeEnum) {
        this.moneyChangeEnum = moneyChangeEnum;
    }

    public String getShopAccountId() {
        return shopAccountId;
    }

    public void setShopAccountId(String shopAccountId) {
        this.shopAccountId = shopAccountId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

}
