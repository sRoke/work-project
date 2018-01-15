package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.SysConfTypeEnum;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Document
public class SysConf extends Base{

    /**
     * 所属公司。
     */
    private String brandAppId;
    /////////////////// 新的更改字段
    private SysConfTypeEnum dataType = SysConfTypeEnum.TEXT;

    /** 字段唯一名,不能重复,用于查找 */
    private String key;

    /** 该字段的简介或者是头部标题  */
    private  String title;

    /** 默认的描述 */
    private String defaultTitle;

    /** 默认值，根据类型再转换 */
    private String defaultValue;

    /** 数据类为文本类型 */
    private String valueText;

    /** 数据类为Map类型 */
    private Map valueMap;

    /** 数据类为Int类型 */
    // boolean 类型使用int数据,0是false 1或者是true
    private Integer valueInt;

    /** 数据类为List类型 */
    private ArrayList valueList;

    /** 备注 */
    private String memo;

    public SysConfTypeEnum getDataType() {
        return dataType;
    }

    public void setDataType(SysConfTypeEnum dataType) {
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public Map getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map valueMap) {
        this.valueMap = valueMap;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public ArrayList getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList valueList) {
        this.valueList = valueList;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }
}
