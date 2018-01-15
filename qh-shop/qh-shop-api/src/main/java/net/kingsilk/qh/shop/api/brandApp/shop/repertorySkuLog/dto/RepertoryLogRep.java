package net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto;

import java.util.LinkedList;
import java.util.List;

public class RepertoryLogRep {
    /**
     * 关联的仓库ID
     */
    private String repertory;

    /**
     * 关联的供应商ID
     */
    private String supplier;

    /**
     * 商品总数量
     */
    private Integer total;

    /**
     * 商品货款
     */
    private Integer totalAmount;

    /**
     * 应付
     */
    private Integer amount;

    /**
     * 应付
     */
    private String dataCreate;

    /**
     * 实付
     */
    private Integer realAmount;

    /**
     * 操作的员工
     */
    private String operator;

    /**
     * 仓库操作类型
     */
    private String operate;

    private String memo;

    /**
     * 仓库中的sku信息
     */
    private List<RepertorySkuLogRep> repertorySkus = new LinkedList<>();

    public List<RepertorySkuLogRep> getRepertorySkus() {
        return repertorySkus;
    }

    public void setRepertorySkus(List<RepertorySkuLogRep> repertorySkus) {
        this.repertorySkus = repertorySkus;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getRealAmount() {
        return realAmount;
    }

    public String getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(String dataCreate) {
        this.dataCreate = dataCreate;
    }

    public void setRealAmount(Integer realAmount) {
        this.realAmount = realAmount;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperate() {
        return operate;
    }
}
