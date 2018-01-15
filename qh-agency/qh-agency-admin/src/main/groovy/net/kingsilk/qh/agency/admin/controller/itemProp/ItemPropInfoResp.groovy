package net.kingsilk.qh.agency.admin.controller.itemProp;

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.ItemPropTypeEnum;
import net.kingsilk.qh.agency.domain.Base;
import net.kingsilk.qh.agency.domain.ItemProp;
import net.kingsilk.qh.agency.domain.ItemPropValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApiModel(value = "商品属性详情返回信息")
class ItemPropInfoResp extends Base {

     @ApiParam(value = "公司ID")
     String companyId;
    @ApiParam(value = "公司名字")
     String companyName;

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

    @ApiParam(value = "属性值")
     Set<ItemPropValue> itemPropValues = new HashSet<ItemPropValue>();


    static ItemPropInfoResp convertItemPropToItemPropInfoResp(ItemProp itemProp, List<ItemPropValue> valuesList) {
        ItemPropInfoResp itemPropResp = new ItemPropInfoResp();
        itemPropResp.code = itemProp.code
        itemPropResp.id = itemProp.id
        itemPropResp.type = itemProp.itemPropType
        itemPropResp.name = itemProp.name
        itemPropResp.dateCreated = itemProp.dateCreated
        itemPropResp.companyId = itemProp.company == null ? null : itemProp.company.id
        itemPropResp.companyName = itemProp.company == null ? null : itemProp.company.name
        itemPropResp.unit = itemProp.unit
        itemPropResp.memName = itemProp.memName
        itemPropResp.memo = itemProp.memo
        itemPropResp.lastModifiedDate = itemProp.lastModifiedDate
        for (ItemPropValue itemPropValue : valuesList) {
            itemPropResp.itemPropValues.add(itemPropValue)
        }
        return itemPropResp;
    }
}
