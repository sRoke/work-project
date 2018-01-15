package net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto;

import io.swagger.annotations.ApiParam;

import java.util.List;

public class RepertorySkuCreateRep {

    @ApiParam(value = "是否草稿") Boolean draf;
    @ApiParam(value = "是否草稿") String operator;
    @ApiParam(value = "是否草稿") String memo;
    @ApiParam(value = "是否草稿") List<RepertorySkuRep> repertorySkuReps;

    public Boolean getDraf() {
        return draf;
    }

    public void setDraf(Boolean draf) {
        this.draf = draf;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<RepertorySkuRep> getRepertorySkuReps() {
        return repertorySkuReps;
    }

    public void setRepertorySkuReps(List<RepertorySkuRep> repertorySkuReps) {
        this.repertorySkuReps = repertorySkuReps;
    }
}
