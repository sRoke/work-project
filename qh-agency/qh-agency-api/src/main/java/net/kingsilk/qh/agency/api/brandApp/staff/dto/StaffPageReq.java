package net.kingsilk.qh.agency.api.brandApp.staff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 *
 */
@ApiModel(value = "员工分页请求信息")
public class StaffPageReq extends UniPageReq {

    @ApiParam(value = "帐号")
    @QueryParam(value = "keyWord")
    private String keyWord;

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
}
