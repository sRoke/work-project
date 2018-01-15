package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ItemPropTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class ItemProp extends Base {

    /**
     * 所属品牌。
     */
    private String brandAppId;


    private String shopId;

    /**
     * 属性类型
     */
    private ItemPropTypeEnum itemPropType;

    /**
     * 名称。
     * <p>
     * 给顾客查看时，使用该名字。比如: "蚕丝净重"
     */
    private String name;

    /**
     * 助记名。
     * <p>
     * 因为不同商品可能有类似商品属性，为方便操作人员区分，该名称可以很长，仅在管理后台显示，不在会员前端展示。
     * <p>
     * 比如："钱皇-万福娘娘-蚕丝净重"。
     * <p>
     * mnemonic name
     */
    private String memName;

    /**
     * 单位。仅当 type 字段 为 ItemPropTypeEnum.INT 时有意义。
     */
    private String unit;

    /**
     * 自定义代码
     */
    private String code;

    /**
     * 备注
     */
    private String memo;

    // --------------------------------------- getter && setter

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

    public ItemPropTypeEnum getItemPropType() {
        return itemPropType;
    }

    public void setItemPropType(ItemPropTypeEnum itemPropType) {
        this.itemPropType = itemPropType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
