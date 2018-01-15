package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * 仓库表
 */
@Document
public class Repertory extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库管理员ID
     */
    private String manager;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否草稿
     */
    private Boolean draft;

    /**
     * 草稿Id
     */
    private String draftId;

    private List<RepertoryItem> repertoryItems;

    public class RepertoryItem {

        private String itemId;

        private Integer totalSku;

        /**
         * 仓库中的sku
         */
        private List<RepertorySku> repertorySkus = new LinkedList<>();

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Integer getTotalSku() {
            return totalSku;
        }

        public void setTotalSku(Integer totalSku) {
            this.totalSku = totalSku;
        }

        public List<RepertorySku> getRepertorySkus() {
            return repertorySkus;
        }

        public void setRepertorySkus(List<RepertorySku> repertorySkus) {
            this.repertorySkus = repertorySkus;
        }
    }

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

    public List<RepertoryItem> getRepertoryItems() {
        return repertoryItems;
    }

    public void setRepertoryItems(List<RepertoryItem> repertoryItems) {
        this.repertoryItems = repertoryItems;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
