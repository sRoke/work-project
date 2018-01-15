package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ActivityStackTypeEnum;
import net.kingsilk.qh.shop.core.ActivityStatusEnum;
import net.kingsilk.qh.shop.core.ActivityTypeEnum;
import net.kingsilk.qh.shop.core.ActivityUseTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 活动
 */
@Document
public class Activity extends Base {

    private String brandAppId;

    /**
     *活动类型
     */
    private ActivityTypeEnum activityTypeEnum;

    /**
     * 满减活动的时候才有值：是否全场通用型
     */
    private ActivityUseTypeEnum activityUseTypeEnum;

    /**
     *是否可以 与其他活动 叠加使用
     */
    private ActivityStackTypeEnum activityStackTypeEnum;

    /**
     * 系统编号
     */
    private String seq;

    /**
     * name
     */
    private String name;

    /**
     * 活动开始时间
     */
    private Date beginTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动展示图片
     */
    private String img;

    /**
     * 活动链接
     */
    private String imgUrl;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态
     */
    private ActivityStatusEnum Status;

    /**
     * 活动商品列表
     */
    private Set<ActivitySku> activitySkus;

    /**
     * 梯度满减信息。
     * 满减活动才有效
     * 注意：在更新是时需要保障 requireMoney 按照从小往大的顺序排序
     */
    private List<ReduceInfo> reduces;

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

    public ActivityTypeEnum getActivityTypeEnum() {
        return activityTypeEnum;
    }

    public void setActivityTypeEnum(ActivityTypeEnum activityTypeEnum) {
        this.activityTypeEnum = activityTypeEnum;
    }

    public ActivityUseTypeEnum getActivityUseTypeEnum() {
        return activityUseTypeEnum;
    }

    public void setActivityUseTypeEnum(ActivityUseTypeEnum activityUseTypeEnum) {
        this.activityUseTypeEnum = activityUseTypeEnum;
    }

    public ActivityStackTypeEnum getActivityStackTypeEnum() {
        return activityStackTypeEnum;
    }

    public void setActivityStackTypeEnum(ActivityStackTypeEnum activityStackTypeEnum) {
        this.activityStackTypeEnum = activityStackTypeEnum;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ActivityStatusEnum getStatus() {
        return Status;
    }

    public void setStatus(ActivityStatusEnum status) {
        Status = status;
    }

    public Set<ActivitySku> getActivitySkus() {
        return activitySkus;
    }

    public void setActivitySkus(Set<ActivitySku> activitySkus) {
        this.activitySkus = activitySkus;
    }

    public List<ReduceInfo> getReduces() {
        return reduces;
    }

    public void setReduces(List<ReduceInfo> reduces) {
        this.reduces = reduces;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    //内部类
    public class  ActivitySku{

        private String brandAppId;

         //参与活动的sku
        private Sku sku;

         // 活动价格
        private Integer activityPrice;

         // 限购数量
         //  -1 为 不限
        private Integer totalCount;

         // 活动商品剩余数量
        private Integer remainCount;

        public String getBrandAppId() {
            return brandAppId;
        }

        public void setBrandAppId(String brandAppId) {
            this.brandAppId = brandAppId;
        }

        public Sku getSku() {
            return sku;
        }

        public void setSku(Sku sku) {
            this.sku = sku;
        }

        public Integer getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(Integer activityPrice) {
            this.activityPrice = activityPrice;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getRemainCount() {
            return remainCount;
        }

        public void setRemainCount(Integer remainCount) {
            this.remainCount = remainCount;
        }
    }

    //内部类
    //梯度满减满赠的信息
    public class ReduceInfo{

         // 序号
         // 手动赋值，使用ObjectId
        private String no;

        ///** 需要消费总额达到的金额 (单位：分) */
        private Integer requiredMoney;

        ///** 减去的金额 (单位：分) */
        private Integer reduceMoney;


        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Integer getRequiredMoney() {
            return requiredMoney;
        }

        public void setRequiredMoney(Integer requiredMoney) {
            this.requiredMoney = requiredMoney;
        }

        public Integer getReduceMoney() {
            return reduceMoney;
        }

        public void setReduceMoney(Integer reduceMoney) {
            this.reduceMoney = reduceMoney;
        }
    }

}

