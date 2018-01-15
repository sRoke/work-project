package net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto;

import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuRep;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RepertorySkuLogRep {

    /**
     * sku的id
     */
    private String skuId;

    /**
     * sku的数量
     */
    private String num;

    /**
     * 该 SKU 的规格信息
     */
    private Set<RepertorySkuRep.Spec> specs = new LinkedHashSet<>();


    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice = 0;

    /**
     * 促销价
     */
    private Integer salePrice = 0;

    /**
     * 采购价
     */
    private Integer buyPrice = 0;

    /**
     * 自定义编码
     */
    private String code;

    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 (标题下面较长的文本)
     */
    private String desp;

    /**
     * 图片列表，第一张图片为主图 (请注意去除重复)
     */
    private Set<String> imgs = new HashSet<>();


    /**
     * 图文详情
     */
    private String detail;

    /**
     * 商品单位
     */
    private String itemUnit;

    /**
     * sku状态
     */
    private Boolean disabled;

    /**
     * 操作的员工
     */
    private String operator;

    private String memo;

    /**
     * 规格信息
     */
    public static class Spec {


        /**
         * 规格ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 商品属性。
         */
        private String itemPropId;

        /**
         * 商品属性值列表。
         * <p>
         * 删除候选值时，必须检查是否已经已上架的商品已经在用该候选值。
         */
        private String itemPropValue;

        // --------------------------------------- getter && setter


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemPropId() {
            return itemPropId;
        }

        public void setItemPropId(String itemPropId) {
            this.itemPropId = itemPropId;
        }

        public String getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(String itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }

    public Set<RepertorySkuRep.Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(Set<RepertorySkuRep.Spec> specs) {
        this.specs = specs;
    }

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

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
