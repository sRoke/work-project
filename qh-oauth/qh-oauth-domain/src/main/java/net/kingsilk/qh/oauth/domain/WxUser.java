package net.kingsilk.qh.oauth.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 *  关联的第三方微信账号。
 *
 *  如果解绑，则物理删除
 */
@Document
@CompoundIndexes({
        @CompoundIndex(def = "{'appId':1,'openId':1}", unique = true)
})
public class WxUser extends Base {

    /**
     * 用户ID
     */
    @Indexed
    private String userId;

    /**
     * 微信 APP ID
     */
    private String appId;

    /**
     * 当前用户在 appId 下的 openId
     */
    private String openId;

    /**
     * 当前用户在 appId 下的 unionId
     */
    private String unionId;

    // ------------------------ 自动生成的 getter、 setter


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
