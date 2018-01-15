package net.kingsilk.qh.shop.api.brandApp.staffGroup.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

public class StaffGroupSaveReq {

    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用户组名称")

    private String name;
    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    private Boolean reserved;

    /**
     * 状态
     */
    private Boolean status;
    /**
     * 描述
     */
    private String desp;
    /**
     * 权限列表
     */
    private Set<String> authorMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Set<String> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Set<String> authorMap) {
        this.authorMap = authorMap;
    }
}
