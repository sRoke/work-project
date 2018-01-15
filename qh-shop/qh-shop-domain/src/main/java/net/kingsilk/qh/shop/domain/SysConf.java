package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.SysConfTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * 系统配置表
 */
@Document
public class SysConf extends Base {

    private String brandAppId;

    private SysConfTypeEnum type;

    private Map<String, ?> mapConf;


    public SysConfTypeEnum getType() {
        return type;
    }

    public void setType(SysConfTypeEnum type) {
        this.type = type;
    }

    public Map<String, ?> getMapConf() {
        return mapConf;
    }

    public void setMapConf(Map<String, ?> mapConf) {
        this.mapConf = mapConf;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }
}
