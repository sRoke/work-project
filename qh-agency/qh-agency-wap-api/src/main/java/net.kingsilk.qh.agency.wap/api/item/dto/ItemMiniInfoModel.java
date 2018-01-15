package net.kingsilk.qh.agency.wap.api.item.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 精简版商品详情
 */
public class ItemMiniInfoModel  {
//    public void convert(Item item, List<Sku> skus, final String[] tags) {
//        this.price = -1;
//        this.curTag = "ERROR";
//        this.tags = ((Set<String>) (item.tags));
//        if (skus && tags) {
//            Sku sku = skus.getAt(0);//暂时随便取一条
//            TagPrice minTag = sku.tagPrices.invokeMethod("findAll", new Object[]{new Closure(this, this) {
//                public Object doCall(TagPrice it) {
//                    return tags.invokeMethod("contains", new Object[]{it.tag.code});
//                }
//
//            }}).invokeMethod("min", new Object[]{new Closure(this, this) {
//                public Object doCall(TagPrice it) {
//                    return it.price;
//                }
//
//            }});
//            this.price = ((int) (minTag.price));
//            this.curTag = ((String) (minTag.tag.code));
//        }
//
//
//        this.itemId = ((String) (item.id));
//        this.title = ((String) (item.title));
//        this.itemUnit = ((String) (item.itemUnit));
//        this.desp = ((String) (item.desp));
//        this.imgs = ((Set<String>) (item.imgs));
//    }


    @ApiModelProperty(value = "商品id")
    private String itemId;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品金额")
    private BigDecimal price;
    @ApiModelProperty(value = "用户标签")
    private String partnerType;
    @ApiModelProperty(value = "单位")
    private String itemUnit;
    @ApiModelProperty(value = "商品描述")
    private String desp;
    @ApiModelProperty(value = "商品图片")
    private Set<String> imgs;
    @ApiModelProperty(value = "商品标签")
    private Set<String> tags;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
