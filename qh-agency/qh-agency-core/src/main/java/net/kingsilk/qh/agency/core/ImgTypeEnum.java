package net.kingsilk.qh.agency.core;

/**
 * 图片类别。
 */
public enum ImgTypeEnum {
    USER_AVATAR("用户头像"),
    CATEGORY_IMG("分类图片"),
    ITEM_PHOTO("商品主图"),
    ITEM_DESC_IMG("商品描述图片"),
    ITEM_USER_SHOW("用户晒单图片"),
    COMPLAIN("投诉附图"),
    BIZ_LICENCE("营业执照"),
    ORG_CODE("组织机构代码"),
    ID_CARD("身份证"),
    BRAND_LOGO("品牌LOGO"),
    AGENT_SHOP("代理商店铺图片"),
    LOGISTICS("快递单背景"),
    ACTIVITY_IMG("活动轮播图"),
    CMS_PAGE_IMG("文章内容图片"),
    AVATAR_BG("用户头像背景图"),
    SERVICE_STATION_IMG("服务网点图"),
    WAP_INDEX_IMG("商城主页轮播图"),
    WAP_INDEX_SEARCH("商城主页热搜"),
    NO_SERVICE_IMG("不予服务的证据图片"),
    BATCH_IMG("批次图片"),
    SERVICE_ITEM_IMG("服务主页图"),
    DESC_IMG("描述图片"),
    URLIMG("URL图片"),
    UPLOAD("七牛上传图片");

    ImgTypeEnum(String desp) {
        this.description = desp;
    }

    public final String getDescription() {
        return description;
    }
    /**
     * 描述
     */
    private final String description;
}
