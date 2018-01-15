package net.kingsilk.qh.agency.admin.api.itemProp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.ItemPropTypeEnum;

import javax.ws.rs.QueryParam;
import java.util.Set;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性保存请求信息")
public class ItemPropSaveReq  {
//    public ItemProp convertItemPropReqToItemProp(ItemProp itemProp) {
//        itemProp.invokeMethod("setName", new Object[]{name});
//        itemProp.invokeMethod("setMemName", new Object[]{memName});
//        itemProp.invokeMethod("setMemo", new Object[]{memo});
//        return itemProp;
//    }

    @ApiParam(value = "id")
    private String id;

    @ApiParam(value = "brandId")
    private String brandId;
    /**
     * 属性类型
     */
    @ApiParam(value = "类型")
    private ItemPropTypeEnum type;
    /**
     * 名称。
     * <p>
     * 给顾客查看时，使用该名字。比如: "蚕丝净重"
     */
    @ApiParam(value = "名字")
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
    @ApiParam(value = "另称")
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
    /**
     * 候选值
     */
    @ApiParam(value = "属性值")
    private Set<ItemPropValues> itemPropValues;



    public static class ItemPropValues  {
//        public ItemPropValue convertToItemPropValue(ItemPropValue itemPropValue, ItemProp itemProp) {
//            itemPropValue.invokeMethod("setName", new Object[]{name});
//            itemPropValue.invokeMethod("setCompany", new Object[]{itemProp.invokeMethod("getCompany", new Object[0])});
//            itemPropValue.invokeMethod("setItemProp", new Object[]{itemProp});
//            return itemPropValue;
//        }
//
//        public static ItemPropValues convertItemPropValueToResp(ItemPropValue itemPropValue) {
//            ItemPropValues itemPropValues = new ItemPropValues();
//            itemPropValues.setName(itemPropValue.invokeMethod("getName", new Object[0]));
//            itemPropValues.setCode(itemPropValue.invokeMethod("getCode", new Object[0]));
//            itemPropValues.setColor(itemPropValue.invokeMethod("getColor", new Object[0]));
//            itemPropValues.setImg(itemPropValue.invokeMethod("getImg", new Object[0]));
//            itemPropValues.setMemo(itemPropValue.invokeMethod("getMemo", new Object[0]));
//            return itemPropValues;
//        }



        @ApiParam(value = "id")
        private String id;
        /**
         * 属性值。
         * <p>
         * 比如: "粉色"、"180*200"、"XL"、"16.0"
         */
        @ApiParam(value = "名字")
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
        @ApiParam(value = "图片")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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

    public Set<ItemPropValues> getItemPropValues() {
        return itemPropValues;
    }

    public void setItemPropValues(Set<ItemPropValues> itemPropValues) {
        this.itemPropValues = itemPropValues;
    }
}
