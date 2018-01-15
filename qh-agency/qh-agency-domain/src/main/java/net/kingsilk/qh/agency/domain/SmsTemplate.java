package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.SmsPlatformEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信记录
 */
@Document
public class SmsTemplate extends Base {

    /**
     * 标题
     */
    private String title;

    /**
     * 发送平台
     */
    private SmsPlatformEnum platform;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 模板关键字，发送时根据此字段来匹配模板（应该唯一）
     */
    private String key;

    /**
     * 是否启用，true发送，false不发送此类信息
     */
    private boolean usable = false;

    /**
     * 短信内容，动态参数用${}或##包含，云片网短信要使用具体内容，参数勿随便更改
     */
    private String content;

    /**
     * 默认参数值,json格式;{code:xxx,name:xxx};
     */
    private Map defaultParams = new LinkedHashMap();

    /**
     * 阿里短信模板编码
     */
    private String aliTemplateCode;

    // --------------------------------------- getter && setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SmsPlatformEnum getPlatform() {
        return platform;
    }

    public void setPlatform(SmsPlatformEnum platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(Map defaultParams) {
        this.defaultParams = defaultParams;
    }

    public String getAliTemplateCode() {
        return aliTemplateCode;
    }

    public void setAliTemplateCode(String aliTemplateCode) {
        this.aliTemplateCode = aliTemplateCode;
    }
}
