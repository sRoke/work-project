package net.kingsilk.qh.agency.admin.api.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by lit on 17/7/20.
 */
@ApiModel(value = "商品属性值信息")
public class ItemPropValueInfo {
    @ApiParam(value = "id")
    private String id;
    /**
     * 属性值。
     * <p>
     * 比如: "粉色"、"180*200"、"XL"、"16.0"
     */
    @ApiParam(value = "属性值")
    private String name;
    /**
     * 自定义代码
     */
    @ApiParam(value = "自定义代码")
    private String code;
    /**
     * 颜色。CSS十六进制颜色值。比如（9999FF）
     */
    @ApiParam(value = "颜色")
    private String color;
    /**
     * 图片网址（https格式）
     */
    @ApiParam(value = "图片网址")
    private String img;
    /**
     * 备注
     */
    @ApiParam(value = "备注")
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
