package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.CouponDefStatusEnum;
import net.kingsilk.qh.shop.core.CouponOriginEnum;
import net.kingsilk.qh.shop.core.CouponTypeEnum;
import net.kingsilk.qh.shop.core.CouponUseTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Set;

/**
 * 定义的优惠卷
 */
@Document
public class CouponDef extends Base {

    private String brandAppId;

    /**
     *
     */
    private String Name;

    /**
     * 优惠卷来源
     */
    private CouponOriginEnum couponOriginEnum;  //todo

    /**
     * 使用类型
     */
    private CouponUseTypeEnum couponUseTypeEnum;

    /**
     *优惠卷类型
     */
    private CouponTypeEnum couponTypeEnum;

    /**
     *多少积分可以兑换该优惠卷
     */
    private Integer integral;

    /**
     * 创建的员工
     */
    private Staff staff;  //todo

    /**
     * 优惠券商品列表（允许为空列表）, 如果为null的时候则是全场通用
     */
    private Set<Sku> skus;  //todo

    /**
     * 该优惠券可领取的起始时间
     */
    private Date startTime;

    /**
     *该优惠券可领取的结束时间
     */
    private Date endTime;

    /**
     *优惠券的有效时间方式
     *如果为null时，默认为 固定时间段，validFrom = startTime，validTo = endTime
     */
    private Date couponTime;

    /**
     * 该优惠券可使用的起始时间
     */
    private Date validFrom;

    /**
     * 该优惠券可使用的结束时间
     */
    private Date validTo;

    /**
     * 有效天数
     */
    private Integer validDays;

    /**
     * 抵用金额,普通优惠券的时候有值
     */
    private Integer reduceMoney;

    /**
     * 折扣优惠券,折扣优惠券的时候才有用。八五折记做 85
     */
    private Integer discount;

    /**
     * 最高折扣的金额,如果大于0，则需要展示最高封顶的折扣金额.折扣金额中使用
     */
    private Integer maxPrice;

    /**
     * 订单需要的金额
     */
    private Integer requiredMoney;

    /**
     * 是否可以公开领取. 如果可以公开领取，则会在[可领取优惠券]中显示，让用户自主领取
     */
    private Boolean isPublic;

    /**
     * 优惠券号码前缀
     */
    private String numPrefix;

    /**
     * 是否只用单个号码
     */
    private Boolean singleNum;

    /**
     * 优惠券号码（当singleNo=true 时才有意义）
     */
    private Set<String> numList;

    /**
     * 该类优惠券的数量。
     * 当 singleNum = false 时，该值必须 > 0 (需要有数量限制).
     * 当 singleNum = true 时，可以为任意值，但是当小于等于0时，代表不限制数量。
     */
    private Integer maxCount;

    /**
     * 当前领取数量
     */
    private Integer curCount;

    /**
     * 状态
     */
    private CouponDefStatusEnum couponDefStatusEnum = CouponDefStatusEnum.EDITING;

    /**
     * 产品验证码码
     */
    private String itemVerifyCode;

    /**
     * 是否使用过，用来抽奖活动中过滤
     */
    private Boolean isUse;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public CouponOriginEnum getCouponOriginEnum() {
        return couponOriginEnum;
    }

    public void setCouponOriginEnum(CouponOriginEnum couponOriginEnum) {
        this.couponOriginEnum = couponOriginEnum;
    }

    public CouponUseTypeEnum getCouponUseTypeEnum() {
        return couponUseTypeEnum;
    }

    public void setCouponUseTypeEnum(CouponUseTypeEnum couponUseTypeEnum) {
        this.couponUseTypeEnum = couponUseTypeEnum;
    }

    public CouponTypeEnum getCouponTypeEnum() {
        return couponTypeEnum;
    }

    public void setCouponTypeEnum(CouponTypeEnum couponTypeEnum) {
        this.couponTypeEnum = couponTypeEnum;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Set<Sku> getSkus() {
        return skus;
    }

    public void setSkus(Set<Sku> skus) {
        this.skus = skus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(Date couponTime) {
        this.couponTime = couponTime;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Integer getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(Integer reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney(Integer requiredMoney) {
        this.requiredMoney = requiredMoney;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getNumPrefix() {
        return numPrefix;
    }

    public void setNumPrefix(String numPrefix) {
        this.numPrefix = numPrefix;
    }

    public Boolean getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(Boolean singleNum) {
        this.singleNum = singleNum;
    }

    public Set<String> getNumList() {
        return numList;
    }

    public void setNumList(Set<String> numList) {
        this.numList = numList;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getCurCount() {
        return curCount;
    }

    public void setCurCount(Integer curCount) {
        this.curCount = curCount;
    }

    public CouponDefStatusEnum getCouponDefStatusEnum() {
        return couponDefStatusEnum;
    }

    public void setCouponDefStatusEnum(CouponDefStatusEnum couponDefStatusEnum) {
        this.couponDefStatusEnum = couponDefStatusEnum;
    }

    public String getItemVerifyCode() {
        return itemVerifyCode;
    }

    public void setItemVerifyCode(String itemVerifyCode) {
        this.itemVerifyCode = itemVerifyCode;
    }

    public Boolean getUse() {
        return isUse;
    }

    public void setUse(Boolean use) {
        isUse = use;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}

