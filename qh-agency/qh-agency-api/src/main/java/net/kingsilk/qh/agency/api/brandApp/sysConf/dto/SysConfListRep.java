package net.kingsilk.qh.agency.api.brandApp.sysConf.dto;

import net.kingsilk.qh.agency.api.UniPageReq;

public class SysConfListRep extends UniPageReq {

    private String dataType;

    private String key;

    /** 该字段的简介或者是头部标题  */
    private  String title;

    /** 默认的描述 */
    private String defaultTitle;

    /** 备注 */
    private String memo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
