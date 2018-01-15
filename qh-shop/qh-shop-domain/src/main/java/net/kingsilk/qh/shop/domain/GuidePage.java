package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.GuideTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GuidePage extends Base {

    private String userId;

    private GuideTypeEnum type;

    private boolean isUsed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GuideTypeEnum getType() {
        return type;
    }

    public void setType(GuideTypeEnum type) {
        this.type = type;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
