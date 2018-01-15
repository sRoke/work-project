package net.kingsilk.qh.agency.wap.api.common.dto;

import net.kingsilk.qh.agency.core.LogisticsCompanyEnum;
import net.kingsilk.qh.agency.core.LogisticsStatusEnum;

import java.util.Date;

/**
 * 物流信息
 */

public class Logistics extends Base {


    /**
     * 所选的物流公司
     */
    private LogisticsCompanyEnum company;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递单状态
     */
    LogisticsStatusEnum status;

    //---------------------------------- 以下字段是定时任务向 快递100 订阅后更新(无论成功与否)
    //todo 以下只是复制过来的，快递100相关我暂时没有查看
    /**
     * 是否已经订阅
     */
    Boolean subscribed = false;

    /**
     * 计算签名用的盐值
     */
    String salt;

    /**
     * 发送订阅的次数
     */
    Integer subscribeCount = 0;

    /**
     * 最后一次发送订阅的时间
     */
    Date lastSubscribeTime;

    /**
     * 最后一次订阅的结果。结构详情请参考快递100的文档 (JSON格式的字符串)
     */
    String lastSubscribeResult;

    //---------------------------------- 以下字段是 快递100 向我们推送快递信息后更新

    /**
     * 最后一次收到推送信息的时间
     */
    Date lastPushedTime;

    /**
     * 收到推送信息的次数
     */
    Integer recvPushedCount = 0;

    /**
     * 推送的跟踪信息。结构详情请参考快递100的文档 (JSON格式的字符串)
     */
    String trackInfo;

    /**
     * 是否签收标记
     */
    boolean isCheck = false;

    // --------------------------------------- getter && setter



    public LogisticsCompanyEnum getCompany() {
        return company;
    }

    public void setCompany(LogisticsCompanyEnum company) {
        this.company = company;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public LogisticsStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LogisticsStatusEnum status) {
        this.status = status;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Integer subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Date getLastSubscribeTime() {
        return lastSubscribeTime;
    }

    public void setLastSubscribeTime(Date lastSubscribeTime) {
        this.lastSubscribeTime = lastSubscribeTime;
    }

    public String getLastSubscribeResult() {
        return lastSubscribeResult;
    }

    public void setLastSubscribeResult(String lastSubscribeResult) {
        this.lastSubscribeResult = lastSubscribeResult;
    }

    public Date getLastPushedTime() {
        return lastPushedTime;
    }

    public void setLastPushedTime(Date lastPushedTime) {
        this.lastPushedTime = lastPushedTime;
    }

    public Integer getRecvPushedCount() {
        return recvPushedCount;
    }

    public void setRecvPushedCount(Integer recvPushedCount) {
        this.recvPushedCount = recvPushedCount;
    }

    public String getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(String trackInfo) {
        this.trackInfo = trackInfo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
