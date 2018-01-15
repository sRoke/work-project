package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.RepertoryOperateEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Document
public class RepertoryLog extends Base {
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
     * 关联的供应商ID
     */
    private String supplierId;

    /**
     * 操作的员工
     */
    private String operator;

    /**
     * 仓库操作类型
     */
    private RepertoryOperateEnum operate;

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
     * 实付
     */
    private Integer realAmount;

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

        private Integer oldTotalSku;

        private Integer number;

        private Integer totalSku;

        /**
         * 仓库中的sku
         */
        private List<RepertorySku> repertorySkus = new LinkedList<>();

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Integer getOldTotalSku() {
            return oldTotalSku;
        }

        public void setOldTotalSku(Integer oldTotalSku) {
            this.oldTotalSku = oldTotalSku;
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
         * sku的更改数量
         */
        private String num;

        /**
         * sku的当前数量
         */
        private Integer newNum;

        /**
         * sku的修改前数量
         */
        private Integer oldNum;

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

        public Integer getNewNum() {
            return newNum;
        }

        public void setNewNum(Integer newNum) {
            this.newNum = newNum;
        }

        public Integer getOldNum() {
            return oldNum;
        }

        public void setOldNum(Integer oldNum) {
            this.oldNum = oldNum;
        }
    }

    public List<RepertoryItem> getRepertoryItems() {
        return repertoryItems;
    }

    public void setRepertoryItems(List<RepertoryItem> repertoryItems) {
        this.repertoryItems = repertoryItems;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public void setRealAmount(Integer realAmount) {
        this.realAmount = realAmount;
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
