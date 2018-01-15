package net.kingsilk.qh.raffle.domain;

import net.kingsilk.qh.raffle.core.DrawTypeEnum;
import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;

public class RaffleRecord extends Base {

//    /** 业务id */
//    String id

//    /** 业务创建时间,框架提供，无法修改 */
//    Date dateCreated
//
//    /** 业务最后修改时间,框架提供，无法修改 */
//    Date lastUpdated
    /**
     *
     */
    private String raffleAppId;
    /**
     * 抽奖活动领取方式
     */
    private DrawTypeEnum drawType;
    /** 中奖人 */
    private String userId;

    /**
     * 奖品
     */
    private String awardId;

    /**
     * 抽奖活动
     */
    private String raffleId;

    /**
     * 参与抽奖的产品的sn编号
     */
    private String sn;

    /**
     * 中奖人的微信信息
     */
    private WeiXinUserInfo weiXinUserInfoId;

    /**
     * 默认为true，用于刚抽完奖后第一次进入的展示，展示后改为false
     */
    private Boolean isFirstShow = true;

    /**
     * 获奖奖品是否已经领取
     */
    private Boolean isAccept = false;

    private String logisticsId;

    private String logisticsCompany;
    private String expressNo;
    /**
     * 备注
     */
    private String memo;

    /**
     * 备注
     */
    private String sellerMemo;

    /**
     * 手机号（方便搜索）
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 昵称
     */
    private String avatar;
    /**
     * 奖品名
     */
    private String awardName;
    /**
     * 中奖处理状态
     */
    private RecordHandleStatusEnum handleStatus;

    private ShippingAddress addr;

    public class ShippingAddress {
        /**
         * 六位ADC地区码
         */
        private String adc;

        /**
         * 街道
         */
        private String street;

        /**
         * 收货人
         */
        private String receiver;

        /**
         * 收货人联系方式
         */
        private String phone;

        /**
         * 备注
         */
        private String memo;

        public String getAdc() {
            return adc;
        }

        public void setAdc(String adc) {
            this.adc = adc;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSn() {
        return sn;
    }

    public ShippingAddress getAddr() {
        return addr;
    }

    public void setAddr(ShippingAddress addr) {
        this.addr = addr;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public WeiXinUserInfo getWeiXinUserInfoId() {
        return weiXinUserInfoId;
    }

    public void setWeiXinUserInfoId(WeiXinUserInfo weiXinUserInfoId) {
        this.weiXinUserInfoId = weiXinUserInfoId;
    }

    public Boolean getFirstShow() {
        return isFirstShow;
    }

    public void setFirstShow(Boolean firstShow) {
        isFirstShow = firstShow;
    }

    public Boolean getAccept() {
        return isAccept;
    }

    public void setAccept(Boolean accept) {
        isAccept = accept;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public RecordHandleStatusEnum getHandleStatus() {
        return handleStatus;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public void setHandleStatus(RecordHandleStatusEnum handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRaffleAppId() {
        return raffleAppId;
    }

    public void setRaffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }


}
