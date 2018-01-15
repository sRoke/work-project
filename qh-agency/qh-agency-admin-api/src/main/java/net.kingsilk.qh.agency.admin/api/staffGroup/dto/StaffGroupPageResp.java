package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;
//import sun.jvm.hotspot.debugger.Page;

/**
 *
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupPageResp extends Base {


//    private Page recPage;

    @ApiModel(value = "StaffGroupInfo")
    public static class StaffGroupMini extends Base {

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        /**
         * 用户组id
         */
        private String id;
        /**
         * 用户组名称
         */
        @ApiModelProperty(value = "用户组名称")
        private String name;
        /**
         * 对应的员工数量
         */
        @ApiModelProperty(value = "对应的员工数量")
        private int staffSize;
        @ApiModelProperty(value = "状态")
        private Boolean status;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

//        public int getStaffSize() {
//            return staffSize;
//        }
//
//        public void setStaffSize(int staffSize) {
//            this.staffSize = staffSize;
//        }
    }
}
