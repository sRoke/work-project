package net.kingsilk.qh.oauth.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.*;
import java.util.*;

/**
 * 一条记录代表一次用户的微信登录认证的 state （有效期：10分钟）.
 *
 * ID 值就是 state 的值。
 */
@Deprecated
@Document
public class WxUserAtState extends Base {

    /**
     * state 将在给定的时间点后过期。
     */
    @Indexed(expireAfterSeconds = 0)
    private Date expiredAt = Date.from(ZonedDateTime.now().plusMinutes(10).toInstant());

    /**
     * 该 state 是否使用过。
     */
    private boolean used;

    /**
     * 该 state 的使用时间。
     */
    private Date usedAt;

    // ------------------------ 自动生成的 getter、 setter


    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Date usedAt) {
        this.usedAt = usedAt;
    }
}
