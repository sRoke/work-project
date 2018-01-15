package net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 *
 */
@ApiModel(value = "分页请求信息")
public class PartnerStaffPageReq extends UniPageReq {


    @ApiParam(value = "帐号")
    @QueryParam(value = "keyWord")
    private String keyWord;
    @ApiParam(value = "开始时间")
    @QueryParam(value = "startDate")
    private String startDate;
    @ApiParam(value = "结束时间")
    @QueryParam(value = "endDate")
    private String endDate;

    @ApiParam(value = "id列表")
    @QueryParam(value = "idList")
    private List<String> idList;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
