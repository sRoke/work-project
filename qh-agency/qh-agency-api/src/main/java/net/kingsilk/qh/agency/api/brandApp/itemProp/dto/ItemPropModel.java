package net.kingsilk.qh.agency.api.brandApp.itemProp.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.ItemPropTypeEnum;

import java.util.List;

/**
 *
 */
public class ItemPropModel {
    @ApiParam(value = "brandAppId")
    private String brandAppId;

    @ApiParam(value = "id")
    private String id;
    /**
     * 属性类型
     */
    @ApiParam(value = "属性类型")
    private ItemPropTypeEnum type;
    /**
     * 名称。
     * <p>
     * 给顾客查看时，使用该名字。比如: "蚕丝净重"
     */
    @ApiParam(value = "属性名称")
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
    @ApiParam(value = "助记名")
    private String memName;
    /**
     * 单位。仅当 type 字段 为 ItemPropTypeEnum.INT 时有意义。
     */
    @ApiParam(value = "单位")
    private String unit;
    /**
     * 自定义代码
     */
    @ApiParam(value = "自定义代码")
    private String code;
    /**
     * 备注
     */
    @ApiParam(value = "备注")
    private String memo;
    @ApiParam(value = "属性值")
    private List<ItemPropValueModel> propValues;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemPropTypeEnum getType() {
        return type;
    }

    public void setType(ItemPropTypeEnum type) {
        this.type = type;
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

    public List<ItemPropValueModel> getPropValues() {
        return propValues;
    }

    public void setPropValues(List<ItemPropValueModel> propValues) {
        this.propValues = propValues;
    }
}
