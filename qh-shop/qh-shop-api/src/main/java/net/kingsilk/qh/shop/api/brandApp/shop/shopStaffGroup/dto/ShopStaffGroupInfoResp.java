package net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto;

import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.shop.api.common.dto.Base;

import java.util.HashSet;
import java.util.Set;

public class ShopStaffGroupInfoResp extends Base {
    /**
     * 用户组名称
     */
    @ApiModelProperty(value = "用")
    private String name;
    /**
     * 是否是系统预留用户组。如果是预留，则不允许删除
     */
    private Boolean reserved;
    /**
     * authorMap 角色权限三层map结构
     */
    private Set<String> author = new HashSet<String>();
    /**
     * 状态
     */
    private String status;

    private String source;

    private String desp;

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

    public Set<String> getAuthor() {
        return author;
    }

    public void setAuthor(Set<String> author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
