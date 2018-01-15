package net.kingsilk.qh.shop.api.brandApp.shop.category.dto;

/**
 * Created by lit on 17/7/20.
 */
public class CategoryResp {

    private String id;
    /**
     * 类别名称
     */
    private String name;

    /**
     * 图片网址
     */
    private String img;

    /**
     * 前端展示用的图标名称
     */
    private String icon;

    /**
     * 顺序。 数值越小，排序越靠前/上。
     */
    private Integer order = 0;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 描述
     */
    private String desp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
