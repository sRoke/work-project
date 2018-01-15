package net.kingsilk.qh.platform.api.app.dto;


/***
 * 在商家新增时需要仅仅需要id和名称
 */
public class AppListResp {

    private String appId;

    private String appName;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
