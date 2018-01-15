package net.kingsilk.qh.shop.api.common.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

/**
 * Created by zcw on 3/16/17.
 * 商品详情
 */
public class ItemInfoModel extends ItemMiniInfoModel {
    public List getProps() {
        return props;
    }

    public void setProps(List props) {
        this.props = props;
    }

    public List getSpecs() {
        return specs;
    }

    public void setSpecs(List specs) {
        this.specs = specs;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<SkuInfoModel> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuInfoModel> skus) {
        this.skus = skus;
    }

    @ApiModelProperty(value = "属性")
    private List props;
    @ApiModelProperty(value = "规格")
    private List specs;
    @ApiModelProperty(value = "图文描述")
    private String detail;
    @ApiModelProperty(value = "商品标签")
    private Set<String> tags;
    @ApiModelProperty(value = "sku详情")
    private List<SkuInfoModel> skus;
}
