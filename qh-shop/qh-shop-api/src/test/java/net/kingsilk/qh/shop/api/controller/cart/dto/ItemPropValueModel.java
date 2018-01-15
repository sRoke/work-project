package net.kingsilk.qh.shop.api.controller.cart.dto;


/**
 * 商品属性值的候选值列表.
 */
public class ItemPropValueModel {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * id
     */
    private String id;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
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
