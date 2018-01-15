package net.kingsilk.qh.shop.api.brandApp.shop.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "商品分类分页请求信息")
public class ItemPageReq extends UniPageReq {

    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWords")
    private String keyWords;
    @ApiParam(value = "状态")
    @QueryParam(value = "status")
    private String status;

    /**
     * 分类
     */
    @ApiParam(value = "分类")
    @QueryParam(value = "category")
    private String category;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
