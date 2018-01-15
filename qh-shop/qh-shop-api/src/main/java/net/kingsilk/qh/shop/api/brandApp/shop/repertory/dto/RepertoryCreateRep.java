package net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto;

public class RepertoryCreateRep {

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库管理员
     */
    private String manager;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
