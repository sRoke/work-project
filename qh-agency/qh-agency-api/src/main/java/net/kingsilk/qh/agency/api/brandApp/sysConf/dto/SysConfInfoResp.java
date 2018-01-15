package net.kingsilk.qh.agency.api.brandApp.sysConf.dto;

import java.util.List;
import java.util.Map;

public class SysConfInfoResp {

    private String dataType;

    private String key;

    /** 该字段的简介或者是头部标题  */
    private  String title;

    /** 默认的描述 */
    private String defaultTitle;

    /** 默认的描述 */
    private String defaultValue;

    /** 备注 */
    private String memo;

    /**
     * 当前值
     */
    private String currentValue;

    /**
     * 当前值
     */
    private Map currentMap;

    /**
     * 当前值
     */
    private List currentList;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    public List getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List currentList) {
        this.currentList = currentList;
    }
}
