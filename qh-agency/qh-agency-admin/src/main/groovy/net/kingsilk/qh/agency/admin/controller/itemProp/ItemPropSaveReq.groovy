package net.kingsilk.qh.agency.admin.controller.itemProp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.ItemPropTypeEnum
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性保存请求信息")
class ItemPropSaveReq {

    String id;

    @ApiParam(value = "companyId")
    String companyId;
    /**
     * 属性类型
     */
    @ApiParam(value = "类型")
    ItemPropTypeEnum type;

    /**
     * 名称。
     * <p>
     * 给顾客查看时，使用该名字。比如: "蚕丝净重"
     */
    @ApiParam(value = "名字")
    String name;

    /**
     * 助记名。
     * <p>
     * 因为不同商品可能有类似商品属性，为方便操作人员区分，该名称可以很长，仅在管理后台显示，不在会员前端展示。
     * <p>
     * 比如："钱皇-万福娘娘-蚕丝净重"。
     * <p>
     * mnemonic name
     */
    @ApiParam(value = "另称")
    String memName;

    /**
     * 单位。仅当 type 字段 为 ItemPropTypeEnum.INT 时有意义。
     */
    @ApiParam(value = "单位")
    String unit;

    /**
     * 自定义代码
     */
    @ApiParam(value = "自定义代码")
    String code;

    /**
     * 备注
     */
    @ApiParam(value = "备注")
    String memo;

    /**
     * 候选值
     */
    @ApiParam(value = "属性值")
    Set<ItemPropValues> itemPropValues;

    ItemProp convertItemPropReqToItemProp(ItemProp itemProp) {
        itemProp.setName(name);
        itemProp.setMemName(memName);
        itemProp.setMemo(memo);
        return itemProp;
    }

    static class ItemPropValues {

        @ApiParam(value = "id")
        String id;
        /**
         * 属性值。
         * <p>
         * 比如: "粉色"、"180*200"、"XL"、"16.0"
         */
        @ApiParam(value = "名字")
        String name;

        /**
         * 自定义代码
         */
        @ApiParam(value = "自定义代码")
        String code;

        /**
         * 颜色。CSS十六进制颜色值。比如（9999FF）
         */
        @ApiParam(value = "颜色")
        String color;

        /**
         * 图片网址（https格式）
         */
        @ApiParam(value = "图片")
        String img;

        /**
         * 备注
         */
        @ApiParam(value = "备注")
        String memo;

        ItemPropValue convertToItemPropValue(ItemPropValue itemPropValue, ItemProp itemProp) {
            itemPropValue.setName(name);
            itemPropValue.setCompany(itemProp.getCompany());
            itemPropValue.setItemProp(itemProp);
            return itemPropValue;
        }

        static ItemPropValues convertItemPropValueToResp(ItemPropValue itemPropValue) {
            ItemPropValues itemPropValues = new ItemPropValues();
            itemPropValues.setName(itemPropValue.getName());
            itemPropValues.setCode(itemPropValue.getCode());
            itemPropValues.setColor(itemPropValue.getColor());
            itemPropValues.setImg(itemPropValue.getImg());
            itemPropValues.setMemo(itemPropValue.getMemo());
            return itemPropValues;
        }
    }
}

