package net.kingsilk.qh.agency.admin.controller.itemProp

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.core.ItemPropTypeEnum
import net.kingsilk.qh.agency.domain.Base
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import org.springframework.data.domain.Page

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性分页返回信息")
class ItemPropPageResp {
    

     Page<ItemPropResp> recPage;

    @ApiModel(value="商品属性信息")
    static class ItemPropResp{

         String id;

         String companyId;

         String companyName;

        /**
         * 属性类型
         */
         ItemPropTypeEnum type;

        /**
         * 名称。
         * <p>
         * 给顾客查看时，使用该名字。比如: "蚕丝净重"
         */
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
         String memName;

        /** 单位。仅当 type 字段 为 ItemPropTypeEnum.INT 时有意义。 */
         String unit;

        /**
         * 自定义代码
         */
         String code;

        /**
         * 备注
         */
         String memo;

         List<ItemPropValue> propValues;
        

    }

    public ItemPropPageResp convertItemPropToItemPropPageResp(Page<ItemProp> page,ItemPropPageReq itemPropPageReq) {
        List<ItemPropResp> itemProps = [];
        page.content.each { ItemProp itemProp ->
            ItemPropResp itemPropResp = new ItemPropResp();
            itemPropResp.code = itemProp.code
            itemPropResp.id = itemProp.id
            itemPropResp.type = itemProp.itemPropType
            itemPropResp.name = itemProp.name
            itemPropResp.dateCreated = itemProp.dateCreated;
            itemPropResp.companyId = itemProp.company == null ? null : itemProp.company.getId();
            itemPropResp.companyName = itemProp.company == null ? null : itemProp.company.getName();
            itemPropResp.unit = itemProp.getUnit();
            itemPropResp.memName = itemProp.getMemName();
            itemPropResp.memo = itemProp.getMemo();
            itemProps.add(itemPropResp)
        }
        this.recList = itemProps;
        this.curPage = itemPropPageReq.curPage;
        this.pageSize = itemPropPageReq.pageSize;
        this.totalCount = page.totalElements;
        return this;
    }

}
