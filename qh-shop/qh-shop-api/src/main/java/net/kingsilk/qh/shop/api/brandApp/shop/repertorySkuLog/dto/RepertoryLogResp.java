package net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto;

import net.kingsilk.qh.shop.core.RepertoryOperateEnum;

import java.util.LinkedList;
import java.util.List;

public class RepertoryLogResp {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 关联的仓库ID
     */
    private String repertoryId;

    /**
     * 操作的员工
     */
    private String operator;

    /**
     * 仓库操作类型
     */
    private RepertoryOperateEnum operate;

    private String memo;

    /**
     * 是否草稿
     */
    private Boolean draft;

    /**
     * 草稿Id
     */
    private String draftId;

    /**
     * 仓库中的sku
     */
    private List<RepertorySku> repertorySkus = new LinkedList<>();

    public class RepertorySku {

        /**
         * sku的id
         */
        private String skuId;

        /**
         * sku的数量
         */
        private String num;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public List<RepertorySku> getRepertorySkus() {
        return repertorySkus;
    }

    public void setRepertorySkus(List<RepertorySku> repertorySkus) {
        this.repertorySkus = repertorySkus;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(String repertoryId) {
        this.repertoryId = repertoryId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public RepertoryOperateEnum getOperate() {
        return operate;
    }

    public void setOperate(RepertoryOperateEnum operate) {
        this.operate = operate;
    }

}
