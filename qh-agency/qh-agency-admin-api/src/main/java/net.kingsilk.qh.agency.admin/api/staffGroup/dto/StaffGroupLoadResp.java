package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;

import java.util.Map;

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupLoadResp")
public class StaffGroupLoadResp extends Base {
//    public StaffGroupLoadResp convertToResp(Map<String, Map<String, Map<String, String>>> authorMap) {
//        this.setAuthorMap(authorMap);
//        return this;
//    }


    private Map<String, Map<String, Map<String, String>>> authorMap;

    public Map<String, Map<String, Map<String, String>>> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<String, Map<String, Map<String, String>>> authorMap) {
        this.authorMap = authorMap;
    }
}
