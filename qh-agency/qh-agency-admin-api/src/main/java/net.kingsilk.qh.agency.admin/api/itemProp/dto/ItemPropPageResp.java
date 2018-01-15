package net.kingsilk.qh.agency.admin.api.itemProp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageResp;
import net.kingsilk.qh.agency.admin.api.common.dto.ItemPropValue;
import net.kingsilk.qh.agency.core.ItemPropTypeEnum;
import org.springframework.data.domain.Page;
//import sun.jvm.hotspot.debugger.Page;

import java.util.List;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性分页返回信息")
public class ItemPropPageResp extends BasePageResp{
//    public ItemPropPageResp convertItemPropToItemPropPageResp(Page<ItemProp> page, ItemPropPageReq itemPropPageReq) {
//        final List<ItemPropResp> itemProps = new ArrayList<ItemPropResp>();
//        page.content.invokeMethod("each", new Object[]{new Closure(this, this) {
//            public Boolean doCall(ItemProp itemProp) {
//                ItemPropResp itemPropResp = new ItemPropResp();
//                itemPropResp.code = ((String) (itemProp.code));
//                itemPropResp.id = ((String) (itemProp.id));
//                itemPropResp.type = ((ItemPropTypeEnum) (itemProp.itemPropType));
//                itemPropResp.name = ((String) (itemProp.name));
//                itemPropResp.dateCreated = itemProp.dateCreated;
//                itemPropResp.setCompanyId(itemProp.company == null ? null : itemProp.company.invokeMethod("getId", new Object[0]));
//                itemPropResp.setCompanyName(itemProp.company == null ? null : itemProp.company.invokeMethod("getName", new Object[0]));
//                itemPropResp.unit = ((String) (itemProp.invokeMethod("getUnit", new Object[0])));
//                itemPropResp.memName = ((String) (itemProp.invokeMethod("getMemName", new Object[0])));
//                itemPropResp.memo = ((String) (itemProp.invokeMethod("getMemo", new Object[0])));
//                return itemProps.add(itemPropResp);
//            }
//
//        }});
//        this.recList = itemProps;
//        this.curPage = itemPropPageReq.getCurPage();
//        this.pageSize = itemPropPageReq.getPageSize();
//        this.totalCount = page.totalElements;
//        return this;
//    }


    private Page recPage;

    @ApiModel(value = "商品属性信息")
    public static class ItemPropResp {

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
        private List<ItemPropValue> propValues;

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

        public List<ItemPropValue> getPropValues() {
            return propValues;
        }

        public void setPropValues(List<ItemPropValue> propValues) {
            this.propValues = propValues;
        }
    }

    public Page getRecPage() {
        return recPage;
    }

    public void setRecPage(Page recPage) {
        this.recPage = recPage;
    }
}
