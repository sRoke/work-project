package net.kingsilk.qh.agency.es.domain;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.*;

/**
 * Created by lit on 17/8/31.
 */
@Document(indexName = "qh-agency", type = "esSku", refreshInterval = "-1")
public class EsSku extends Base {
    /**
     * 所属品牌商id
     */
    private String brandAppId;

    /**
     * 所属商品
     */
    private String itemId;

    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice = 0;

    /**
     * 销售价
     */
    private Integer salePrice;

    /**
     * 自定义编码
     */
    private String code;
    /**
     * 根据不同标签给不同打价格。
     * <p>
     * 标签可以随意定。当前是与 PartnerStaff 中的 tags 进行匹配。
     * 如果匹配到多个标签，则使用最低价。
     */
    private Set<TagPrice> tagPrices = new LinkedHashSet<>();


    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

    /**
     * 商品规格列表。
     */
    private Map<String, List<String>> itemSpecs = new HashMap<>();

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 (标题下面较长的文本)
     */
    private String desp;

    /**
     * 图片列表，第一张图片为主图 (请注意去除重复)
     */
    private Set<String> imgs = new LinkedHashSet<>();


    /**
     * 标签。由店铺任意指定
     */
    private Set<String> tags;

    /**
     * 图文详情
     */
    private String detail;

    /**
     * 商品单位
     */
    private String itemUnit;

    /**
     * 上架日期
     */
    private Date onSaleTime;

    /**
     * sku状态  FIXME  boolean disabled
     */
    private String status;


    /**
     * 跟标签相关的价格。
     */
    public static class TagPrice {

        /**
         * 规格ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 标签
         */
        private PartnerTypeEnum tag;

        /**
         * 价格。 单位：分。
         */
        private Integer price;


        // --------------------------------------- getter && setter

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public PartnerTypeEnum getTag() {
            return tag;
        }

        public void setTag(PartnerTypeEnum tag) {
            this.tag = tag;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }
}
