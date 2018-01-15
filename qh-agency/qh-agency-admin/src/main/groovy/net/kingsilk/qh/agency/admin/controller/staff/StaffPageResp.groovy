package net.kingsilk.qh.agency.admin.controller.staff

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.StaffGroup
import org.springframework.data.domain.Page

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品分页返回信息")
class StaffPageResp {


    Page<StaffMinInfo> recPage


    @ApiModel(value = "商品信息")
    static class StaffMinInfo {

        @ApiParam(value = "id")
        String id;

        /**
         * 账户
         */
        @ApiParam(value = "用户id")
        String userId;

        /**
         * 真实姓名。 第一次创建时从 UserDetails 中复制得来
         */
        @ApiParam(value = "用户姓名")
        String realName;

        /**
         * 花名
         */
        @ApiParam(value = "用户花名")
        String nickName;

        @ApiParam(value = "用户手机号")
        String phone;
        /**
         * 身份证号码
         */
        @ApiParam(value = "用户身份证")
        String idNumber;

        /**
         * 如果是前端自己申请的资质,则需要上传图片验证。后台直接编辑可以不需要。
         * 图片作为审核使用
         * 第一张:身份证正面,第二张身份证反面,第三张学生证。 学生资质申请的时候拍摄图片的顺序
         */
        @ApiParam(value = "用户头像")
        String avatar;

        @ApiParam(value = "用户组")
        List<StaffGroup> staffGroupList = new ArrayList<StaffGroup>();
        /**
         * 备注
         */
        @ApiParam(value = "备注")
        String memo;

        /**
         * 是否已经禁用。
         * <p>
         * true - 已经禁用。
         */
        @ApiParam(value = "是否禁用")
        boolean disabled;

        @ApiParam(value = "创建时间")
        Date dateCreated;

        @ApiParam(value = "创建人")
        String createdBy;

    }
}