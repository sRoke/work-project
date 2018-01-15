package net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto;


import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;

public class RaffleRecordMinResp {


    private String id;

    /**
     * 中奖人的真名
     */
    private String realName;

    /**
     * 中奖人的头像
     */
    private String avatar;

    /**
     * 中奖人的手机号
     */
    private String phone;

    /**
     * 奖品名
     */
    private String awardName;

    /**
     * 处理状态
     */
    private RecordHandleStatusEnum handleStatus;
    private String handleStausDesp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RecordHandleStatusEnum getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(RecordHandleStatusEnum handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHandleStausDesp() {
        return handleStausDesp;
    }

    public void setHandleStausDesp(String handleStausDesp) {
        this.handleStausDesp = handleStausDesp;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }
}
