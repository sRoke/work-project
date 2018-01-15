package net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto;


public class PaLogResp {


    /**
     * 明细id
     */
    private String partnerAccountLogId;

    /**
     * 关联的账户信息
     */
    private String partnerAccount;

    /**
     * 各种单id
     */
    private String id;

    /**
     * 时间
     */
    private String date;

    /**
     * 年月日
     */
    private String datestamp;


    /**
     * 时分秒
     */
    private String dateminstamp;

    /**
     * 类型
     */
    private String type;

    /**
     * 类型描述
     */
    private String typeDetil;

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
    private String targetAccount;

    //////////////////////////////////////// 关联订单等记录

    /**
     * 支付
     */
    private String qhPayId;

    /**
     * 提现的时候才有的状态,是否提现成功
     */
    private String status;


    /***
     * 钱款变更动态
     */
    private String moneyChangeStatus;

    /***
     * 钱款变更动态描述
     */
    private String moneyChangeDetil;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public String getDateminstamp() {
        return dateminstamp;
    }

    public void setDateminstamp(String dateminstamp) {
        this.dateminstamp = dateminstamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDetil() {
        return typeDetil;
    }

    public void setTypeDetil(String typeDetil) {
        this.typeDetil = typeDetil;
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

    public Integer getOwedBalance() {
        return owedBalance;
    }

    public void setOwedBalance(Integer owedBalance) {
        this.owedBalance = owedBalance;
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

    public Integer getSrcOwedBalance() {
        return srcOwedBalance;
    }

    public void setSrcOwedBalance(Integer srcOwedBalance) {
        this.srcOwedBalance = srcOwedBalance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getQhPayId() {
        return qhPayId;
    }

    public void setQhPayId(String qhPayId) {
        this.qhPayId = qhPayId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartnerAccount() {
        return partnerAccount;
    }

    public void setPartnerAccount(String partnerAccount) {
        this.partnerAccount = partnerAccount;
    }

    public String getPartnerAccountLogId() {
        return partnerAccountLogId;
    }

    public void setPartnerAccountLogId(String partnerAccountLogId) {
        this.partnerAccountLogId = partnerAccountLogId;
    }

    public String getMoneyChangeDetil() {
        return moneyChangeDetil;
    }

    public void setMoneyChangeDetil(String moneyChangeDetil) {
        this.moneyChangeDetil = moneyChangeDetil;
    }

    public String getMoneyChangeStatus() {
        return moneyChangeStatus;
    }

    public void setMoneyChangeStatus(String moneyChangeStatus) {
        this.moneyChangeStatus = moneyChangeStatus;
    }
}
