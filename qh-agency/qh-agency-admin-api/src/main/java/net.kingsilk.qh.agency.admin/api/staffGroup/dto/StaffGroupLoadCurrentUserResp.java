package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;

import java.util.Set;

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupLoadCurrentUserResp")
public class StaffGroupLoadCurrentUserResp extends Base {
//    public StaffGroupLoadCurrentUserResp convertToResp(String source, String userId, Set<String> currentAuthor) {
//        this.setSource(source);
//        this.invokeMethod("setId", new Object[]{userId});
//        this.setCurrentAuthor(currentAuthor);
//        return this;
//    }


    private String source;
    private Set<String> currentAuthor;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<String> getCurrentAuthor() {
        return currentAuthor;
    }

    public void setCurrentAuthor(Set<String> currentAuthor) {
        this.currentAuthor = currentAuthor;
    }
}
