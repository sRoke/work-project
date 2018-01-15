package net.kingsilk.qh.agency.wap.api.common.dto;


/**
 * 商品属性值的候选值列表.
 */
public class ItemPropValue {

    /**
     * 所属品牌。
     */
    private String brandId;

    /**
     * 商品属性
     */
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

}
