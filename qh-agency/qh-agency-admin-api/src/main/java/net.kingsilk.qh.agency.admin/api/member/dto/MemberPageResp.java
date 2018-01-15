package net.kingsilk.qh.agency.admin.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageResp;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.data.domain.Page;

/**
 *
 */
@ApiModel(value = "会员分页返回信息")
public class MemberPageResp extends BasePageResp<MemberPageResp.MemberMinInfo> {

    private Page memberMinInfoPage;

    public static class MemberMinInfo {


        /**
         * id
         */
        private String id;
        /**
         * 账户
         */
        @ApiParam(value = "用户id")
        private String userId;
        /**
         * 真实姓名
         */
        @ApiParam(value = "真实姓名")
        private String realName;
        /**
         * 手机号
         */
        @ApiParam(value = "手机号")
        private String phone;
        /**
         * 用户标签。
         * <p>
         * 比如："代理商", "加盟商"
         */
        @ApiParam(value = "用户标签")
        private String partnerType;

        /**
         * 渠道商编号
         */
        @ApiParam(value = "用户标签")
        private String partnerSeq;
        /**
         * 头像地址
         */
        @ApiParam(value = "头像地址")
        private String avatar;
        /**
         * 身份证号码
         */
        @ApiParam(value = "身份证号码")
        private String idNo;
        /**
         * 是否已经禁用。
         * <p>
         * true - 已经禁用。
         */
        @ApiParam(value = "是否已经禁用")
        private boolean disabled;
        /**
         * 备注
         */
        @ApiParam(value = "备注")
        private String memo;
        /**
         * 店铺地址。
         */
        @ApiParam(value = "店铺地址")
        private String shopAddr;
        /**
         * 店铺地址在百度地中经度。
         */
        @ApiParam(value = "店铺地址在百度地中经度")
        private String shopAddrMapCoorX;
        /**
         * 店铺地址在百度地中维度。
         */
        @ApiParam(value = "店铺地址在百度地中维度")
        private String shopAddrMapCoorY;

        public String getPartnerType() {
            return partnerType;
        }

        public String getPartnerSeq() {
            return partnerSeq;
        }

        public void setPartnerSeq(String partnerSeq) {
            this.partnerSeq = partnerSeq;
        }

        public void setPartnerType(String partnerType) {
            this.partnerType = partnerType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public boolean getDisabled() {
            return disabled;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getShopAddr() {
            return shopAddr;
        }

        public void setShopAddr(String shopAddr) {
            this.shopAddr = shopAddr;
        }

        public String getShopAddrMapCoorX() {
            return shopAddrMapCoorX;
        }

        public void setShopAddrMapCoorX(String shopAddrMapCoorX) {
            this.shopAddrMapCoorX = shopAddrMapCoorX;
        }

        public String getShopAddrMapCoorY() {
            return shopAddrMapCoorY;
        }

        public void setShopAddrMapCoorY(String shopAddrMapCoorY) {
            this.shopAddrMapCoorY = shopAddrMapCoorY;
        }
    }

    public Page getMemberMinInfoPage() {
        return memberMinInfoPage;
    }

    public void setMemberMinInfoPage(Page memberMinInfoPage) {
        this.memberMinInfoPage = memberMinInfoPage;
    }
}
