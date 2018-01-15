package net.kingsilk.qh.platform.msg.api.admin;

import java.io.Serializable;
import java.util.List;

/**
 * 通知同步 更新一个 admin 管理员。
 * <p>
 * 加锁路径为： ${prefix}/${className}/${wxMpAppId}
 */
public class AddSAEventEx implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> appList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getAppList() {
        return appList;
    }

    public void setAppList(List<String> appList) {
        this.appList = appList;
    }
}
