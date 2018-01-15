package net.kingsilk.qh.oauth.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 用户额外信息
 */
@Document
@CompoundIndexes({
})
public class UserInfo extends Base {

    /**
     * 所属用户
     */
    @Indexed
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 身份证号码
     */
    private String idNo;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private Set<String> phones;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 微信号。
     * 用户输入。可能不真实。
     */
    private String wxNo;

    /**
     * QQ号。
     * 用户输入。可能不真实。
     */
    private String qq;


    // ------------------------ 自动生成的 getter、 setter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
