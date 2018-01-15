package net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record.dto.RecordInfoResp;
import net.kingsilk.qh.raffle.core.DrawTypeEnum;
import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;

import java.util.ArrayList;
import java.util.List;

public class RaffleRecordDetail {

    /**
     * 奖品详情
     */
    @ApiParam(value = "地址")
    private List<AwardInfo> awardInfoList = new ArrayList<>();

    private String memo;

    /**
     * 抽奖活动领取方式
     */
    private DrawTypeEnum drawType;

    private String logisticsId;
    private String expressNo;
    private String logisticsCompany;
    private String name;
    private String phone;

    /**
     * 负责人帐号
     */
    private String shopPhone;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 展示地址
     */
    private String shopAddress;
    /**
     * 处理状态
     */
    private RecordHandleStatusEnum handleStatus;
    /**
     * 收货地址
     */
    @ApiParam(value = "地址")
    private RecordInfoResp.AddrModel address;

    public static class AddrModel {
        /**
         * Address id
         */
        @ApiParam(value = "id", required = false)
        @ApiModelProperty(value = "id")
        private String id;
        /**
         * adc No
         */
        @ApiParam(value = "adcNo", required = true)
        @ApiModelProperty(value = "adcNo")
        private String adcNo;

        private String provinceNo;
        private String cityNo;
        private String countyNo;
        /**
         * 省
         */
        @ApiParam(value = "省", required = true)
        @ApiModelProperty(value = "省")
        private String province;
        /**
         * 市
         */
        @ApiParam(value = "市", required = true)
        @ApiModelProperty(value = "市")
        private String city;
        /**
         * 区
         */
        @ApiParam(value = "区", required = true)
        @ApiModelProperty(value = "区")
        private String area;
        /**
         * 街道
         */
        @ApiParam(value = "街道", required = true)
        @ApiModelProperty(value = "街道")
        private String street;
        /**
         * 收货人
         */
        @ApiParam(value = "收货人", required = true)
        @ApiModelProperty(value = "收货人")
        private String receiver;
        /**
         * 手机号
         */
        @ApiParam(value = "手机号", required = true)
        @ApiModelProperty(value = "手机号")
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdcNo() {
            return adcNo;
        }

        public void setAdcNo(String adcNo) {
            this.adcNo = adcNo;
        }

        public String getProvinceNo() {
            return provinceNo;
        }

        public void setProvinceNo(String provinceNo) {
            this.provinceNo = provinceNo;
        }

        public String getCityNo() {
            return cityNo;
        }

        public void setCityNo(String cityNo) {
            this.cityNo = cityNo;
        }

        public String getCountyNo() {
            return countyNo;
        }

        public void setCountyNo(String countyNo) {
            this.countyNo = countyNo;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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
    }

    public List<AwardInfo> getAwardInfoList() {
        return awardInfoList;
    }

    public void setAwardInfoList(List<AwardInfo> awardInfoList) {
        this.awardInfoList = awardInfoList;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public RecordInfoResp.AddrModel getAddress() {
        return address;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public RecordHandleStatusEnum getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(RecordHandleStatusEnum handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public void setAddress(RecordInfoResp.AddrModel address) {
        this.address = address;
    }
}
