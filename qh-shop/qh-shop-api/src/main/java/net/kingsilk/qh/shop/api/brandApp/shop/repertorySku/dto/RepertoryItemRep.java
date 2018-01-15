package net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto;

import net.kingsilk.qh.shop.core.ItemStatusEnum;

import java.util.*;

public class RepertoryItemRep {

    /**
     * 自定义编码
     */
    private String code;

    /**
     * 状态
     */
    private ItemStatusEnum status = ItemStatusEnum.EDITING;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 (标题下面较长的文本)
     */
    private String desp;

    /**
     * 图片列表，第一张图片为主图 (请注意去除重复)
     */
    private ArrayList<String> imgs = new ArrayList<>();


    private List<RepertorySkuRep> repertorySkuReps;

    // --------------------------------------- getter && setter


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public List<RepertorySkuRep> getRepertorySkuReps() {
        return repertorySkuReps;
    }

    public void setRepertorySkuReps(List<RepertorySkuRep> repertorySkuReps) {
        this.repertorySkuReps = repertorySkuReps;
    }
}
