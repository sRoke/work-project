package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;


import io.swagger.annotations.ApiModel;

import java.util.ArrayList;

@ApiModel
public class OrderCommitReq {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

//    /**
//     * 订单id
//     */
//    private String orderId;

//    private String itemId;

    /**
     * 是否开发票
     */
    private String needInvoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 总价
     */
    private Integer totlePrice;

    /**
     * 系统编码
     */
    private String seq;

    /**
     * 订单来源
     */
    private String sourceType;

    /**
     * 买家备注
     */
    private String buyerMemo;

    /**
     * 到店自取时间
     */
    private String sinceTakeTime;

//    /**
//     * 卖家备注
//     */
//    private String sellerMemo;

//    /**
//     * 六位ADC地区码
//     */
//    private String adc;
//
//    /**
//     * 街道
//     */
//    private String street;
//
//    /**
//     * 收货人
//     */
//    private String receiver;
//    /**
//     * 收货人联系方式
//     */
//    private String phone;
//
//    /**
//     * 备注
//     */
//    private String addrMemo;



//    /**
//     * 接受sku相关信息
//     */
//    private ArrayList<Skus> skus;
//
//
//
//    public class  Skus {
//
//        private String no;
//        /**
//         * 编号，前端生成
//         */
//        private String skuId;
//
//        /**
//         * 数量
//         */
//        private Integer num;
//        private Integer totlePrice;

//        public String getSkuId() {
//            return skuId;
//        }
//
//        public void setSkuId(String skuId) {
//            this.skuId = skuId;
//        }

//        public Integer getTotlePrice() {
//            return totlePrice;
//        }
//
//        public void setTotlePrice(Integer totlePrice) {
//            this.totlePrice = totlePrice;
//        }

//        public Integer getNum() {
//            return num;
//        }
//
//        public String getNo() {
//            return no;
//        }
//
//        public void setNo(String no) {
//            this.no = no;
//        }
//
//        public void setNum(Integer num) {
//            this.num = num;
//        }
//    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(Integer totlePrice) {
        this.totlePrice = totlePrice;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

//    public ArrayList<Skus> getSkus() {
//        return skus;
//    }
//
//    public void setSkus(ArrayList<Skus> skus) {
//        this.skus = skus;
//    }

    public String getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(String needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getSinceTakeTime() {
        return sinceTakeTime;
    }

    public void setSinceTakeTime(String sinceTakeTime) {
        this.sinceTakeTime = sinceTakeTime;
    }

    //    public String getSellerMemo() {
//        return sellerMemo;
//    }
//
//    public void setSellerMemo(String sellerMemo) {
//        this.sellerMemo = sellerMemo;
//    }

//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }
}
