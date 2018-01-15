package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 商品属性值的候选值列表.
 */
@Document
public class ItemPropValue extends Base {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * 商品属性
     */
    @DBRef
    private ItemProp itemProp;

    /**
     * 属性值。
     * <p>
     * 比如: "粉色"、"180*200"、"XL"、"16.0"
     */
    private String name;

    /**
     * 自定义代码
     */
    private String code;

    /**
     * 颜色。CSS十六进制颜色值。比如（9999FF）
     * //TODO 确定是否使用
     */
    private String color;

    /**
     * 图片网址（https格式）
     * //TODO 确定是否使用
     */
    private String img;

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

    public ItemProp getItemProp() {
        return itemProp;
    }

    public void setItemProp(ItemProp itemProp) {
        this.itemProp = itemProp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
