//package net.kingsilk.qh.raffle.domain;
//
//import java.util.Date;
//import java.util.Set;
//
//public class CouponDef extends Base{
//
//    /** 名称 */
//    String name;
//
////    /** 优惠券的来源，是新用户发放，还是兑换*/
////    CouponOriginEnum origin = CouponOriginEnum.USED
//
//    /** 优惠券使用类型。 如果为null，则视作 CouponTypeEnum#ORDER */
////    CouponUseTypeEnum useType = CouponUseTypeEnum.CURRENCY
//
//    /** 类型。 如果为null，则视作 CouponTypeEnum#ORDER */
////    CouponTypeEnum type = CouponTypeEnum.ORDER
//
//    /**该优惠券可以使用多少积分进行兑换，origin=EXCHANGE时候使用*/
//    Integer integral = 0;
//
////    /** 关联的活动 (暂时不用) */
////    Activity activity
//
////    /** 关联的订单 (暂时不用) */
////    Order order
//
//    /** 创建的员工 */
//    Staff staff;
//
////    /** 邀请人 (暂时不用) */
////    User inviter
//
////    /** 分发人（拥有该号码段的会员） */
////    User user
////    /** 优惠券商品列表（允许为空列表）, 如果为null的时候则是全场通用*/
////    Set<Sku> skus = []
//
//    /////////////////////////// 优惠券可以领取的时间
//    /** 该优惠券可领取的起始时间 */
//    Date startTime;
//
//    /** 该优惠券可领取的结束时间 */
//    Date endTime;
//
//    /////////////////////////// 优惠券可以使用的时间
//    /**
//     * 优惠券的有效时间方式
//     *
//     * 如果为null时，默认为 固定时间段，validFrom = startTime，validTo = endTime
//     */
////    CouponTimeEnum couponTime
//
//    // ------------ 固定时间段
//    /** 该优惠券可使用的起始时间 */
//    Date validFrom;
//
//    /** 该优惠券可使用的结束时间 */
//    Date validTo;
//
//    // ------------ 领取后计时
//    /** 有效天数 */
//    Long validDays;
//
//    /** 抵用金额,普通优惠券的时候有值 */
//    Integer reduceMoney = 0;
//
//    /** 折扣优惠券,折扣优惠券的时候才有用。八五折记做 85 */
//    Integer discount = 0;
//
//    /** 最高折扣的金额,如果大于0，则需要展示最高封顶的折扣金额.折扣金额中使用 */
//    Integer maxPrice = 0;
//
//    ///////////////////////////////////////////////// 满减
//    /** 订单需要的金额 */
//    Integer requiredMoney = 0;
//
//    ///////////////////////////////////////////////// 号码相关
//
//    /** 是否可以公开领取. 如果可以公开领取，则会在[可领取优惠券]中显示，让用户自主领取 */
//    private Boolean isPublic;
//
//    /** 优惠券号码前缀 */
//    String numPrefix;
//
//    /** 是否只用单个号码 */
//    private Boolean singleNum;
//
//    /** 优惠券号码（当singleNo=true 时才有意义） */
//    // 当singleNum=false时，优惠券号码= "${numPrefix}-${numList[i]}"
//    // 当singleNum=true 时，优惠券号码= "${numPrefix}"
//    private Set<String> numList ;
//
//    /**
//     * 该类优惠券的数量。
//     * 当 singleNum = false 时，该值必须 > 0 (需要有数量限制).
//     * 当 singleNum = true 时，可以为任意值，但是当小于等于0时，代表不限制数量。
//     */
//    private Integer maxCount = 0;
//
//    /** 当前领取数量 */
//    private Integer curCount = 0;
//
//    /** 状态 */
//    private CouponDefStatusEnum status = CouponDefStatusEnum.EDITING;
//
//    ///////////////////////////////////////////////// type = CouponTypeEnum.SILK_RENOVATE
//
//    /** 产品验证码码 */
//    private String itemVerifyCode;
//
//    //是否使用过，用来抽奖活动中过滤
//    private Boolean isUse = false;
//
//    /**
//     * 品牌
//     */
//    private String raffleAppId;
//
//}
