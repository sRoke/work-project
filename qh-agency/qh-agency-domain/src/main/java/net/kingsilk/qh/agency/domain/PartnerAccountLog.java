package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.core.MoneyChangeEnum;
import net.kingsilk.qh.agency.core.WithdrawCashStatusEnum;

/**
 * 渠道商账户变更记录表
 */
public class PartnerAccountLog extends Base {

    /**
     * 关联的账户
     */
    private String partnerAccountId;

    /**
     * 所属渠道商
     */
    private String partnerId;
    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 提现的时候才有的状态,是否提现成功
     */
    private WithdrawCashStatusEnum status;

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
     * 已欠货款
     */
    private Integer owedBalance;

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
     * 来源账户原有已欠货款
     */
    private Integer srcOwedBalance;

    /**
     * 备注
     */
    private String memo;

    ////////////////////////// 转账情况下列字段有值

    /**
     * 目标账户 ,转账的时候双方都要有记录的
     */
    private String targetAccountId;

    //////////////////////////////////////// 关联订单等记录

    /**
     * 支付
     */
    private String qhPayId;

    /**
     * 如果是退款，退款的记录
     */
    private String refundId;

    /**
     * 商品订单
     */
    private String orderId;

    /**
     * 收银订单
     */
    private String retailOrderId;

    /**
     * 提现记录
     */
    private String withdrawId;

    /***
     * 钱款变更动态
     */
    private MoneyChangeEnum moneyChangeEnum;

    public PartnerAccountLog() {

    }

    public PartnerAccountLog(
            String partnerAccountId,
            String partnerId,
            String brandAppId,
            WithdrawCashStatusEnum status,
            AccountChangeTypeEnum type,
            Integer changeAmount,
            Integer freezeBalance,
            Integer balance,
            Integer noCashBalance,
            Integer owedBalance,
            Integer srcFreezeBalance,
            Integer srcBalance,
            Integer srcNoCashBalance,
            Integer srcOwedBalance,
            String memo,
            String targetAccountId,
            String qhPayId,
            String refundId,
            String orderId,
            String retailOrderId,
            String withdrawId,
            MoneyChangeEnum moneyChangeEnum) {
        this.partnerAccountId = partnerAccountId;
        this.partnerId = partnerId;
        this.brandAppId = brandAppId;
        this.status = status;
        this.type = type;
        this.changeAmount = changeAmount;
        this.freezeBalance = freezeBalance;
        this.balance = balance;
        this.noCashBalance = noCashBalance;
        this.owedBalance = owedBalance;
        this.srcFreezeBalance = srcFreezeBalance;
        this.srcBalance = srcBalance;
        this.srcNoCashBalance = srcNoCashBalance;
        this.srcOwedBalance = srcOwedBalance;
        this.memo = memo;
        this.targetAccountId = targetAccountId;
        this.qhPayId = qhPayId;
        this.refundId = refundId;
        this.orderId = orderId;
        this.retailOrderId = retailOrderId;
        this.withdrawId = withdrawId;
        this.moneyChangeEnum = moneyChangeEnum;
    }

    public String getPartnerAccountId() {
        return partnerAccountId;
    }

    public void setPartnerAccountId(String partnerAccountId) {
        this.partnerAccountId = partnerAccountId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public WithdrawCashStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WithdrawCashStatusEnum status) {
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

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public String getQhPayId() {
        return qhPayId;
    }

    public void setQhPayId(String qhPayId) {
        this.qhPayId = qhPayId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRetailOrderId() {
        return retailOrderId;
    }

    public void setRetailOrderId(String retailOrderId) {
        this.retailOrderId = retailOrderId;
    }

    public String getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(String withdrawId) {
        this.withdrawId = withdrawId;
    }

    public Integer getOwedBalance() {
        return owedBalance;
    }

    public void setOwedBalance(Integer owedBalance) {
        this.owedBalance = owedBalance;
    }

    public Integer getSrcOwedBalance() {
        return srcOwedBalance;
    }

    public void setSrcOwedBalance(Integer srcOwedBalance) {
        this.srcOwedBalance = srcOwedBalance;
    }

    public MoneyChangeEnum getMoneyChangeEnum() {
        return moneyChangeEnum;
    }

    public void setMoneyChangeEnum(MoneyChangeEnum moneyChangeEnum) {
        this.moneyChangeEnum = moneyChangeEnum;
    }
}
