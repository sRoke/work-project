package net.kingsilk.qh.vote.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 员工表
 * <p>
 * TODO
 * 来源主要分为以下几部分：
 * 1. platform 注册时，默认同步过来的staff
 * 2. 从各个其他子系统中同步过来
 * 3. 初始化数据库时，创建默认staff
 */
public class Staff extends Base {
    /**
     * brandAppId
     */
    private String voteAppId;

    /**
     * 账户ID
     */
    private String userId;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 权限列表。
     * 暂时定义为有子活动的权限集合，有该子活动，就有权使用该子活动
     */
    private Set<String> authorities = new HashSet<>();

    /**
     * 备注
     */
    private String memo;

    public String getVoteAppId() {
        return voteAppId;
    }

    public void setVoteAppId(String voteAppId) {
        this.voteAppId = voteAppId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
