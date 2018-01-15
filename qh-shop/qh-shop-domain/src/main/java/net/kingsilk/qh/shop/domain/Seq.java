package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.SeqTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document
public class Seq extends Base {

    private String brandAppId;

    /**
     * 类型
     */
    private SeqTypeEnum type;

    /**
     * 时间
     */
    private Date date;

    /**
     * 当前可用序列号
     */
    private String curSeq;

    /**
     * 门店ID
     */
    private String shopId;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCurSeq() {
        return curSeq;
    }

    public void setCurSeq(String curSeq) {
        this.curSeq = curSeq;
    }

    public SeqTypeEnum getType() {
        return type;
    }

    public void setType(SeqTypeEnum type) {
        this.type = type;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
