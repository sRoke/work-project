package net.kingsilk.qh.agency.admin.controller.item

import io.swagger.annotations.ApiModel
import org.springframework.data.domain.Page

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品分页返回信息")
class ItemPageResp {


    Page<ItemMinInfo> recList

    static class ItemMinInfo {
        String id;
        /**
         * 主图
         */
        String imgs
//        /**
//         * 自定义编码
//         */
//        String code
//        String machineCode

        /**
         * 名称
         */
        String title;
        /**
         * 品牌
         */
        String brand
        /**
         * 分类标签id
         */
        String categoryId;
        /**
         * 分类标签名称
         */
        Set<String> categoryName=new HashSet<>();

        String statusCode
        /**
         * 状态
         */
        String statusDesp;
    }
}