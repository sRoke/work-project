package net.kingsilk.qh.agency.admin.api.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.ItemStatusEnum;

import java.util.Set;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "CategorySaveReq")
public class CategorySaveReq {

    @ApiParam(value = "是否是新增", defaultValue = "true")
    @ApiModelProperty(value = "是否是新增")
    private boolean isAdd;

    @ApiParam(value = "不是新增时填入ID")
    @ApiModelProperty(value = "不是新增时填入ID")
    private String id;

    @ApiParam(value = "分类名称")
    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiParam(value = "分类图片")
    @ApiModelProperty(value = "分类图片")
    private String img;

    @ApiParam(value = "描述")
    @ApiModelProperty(value = "描述")
    private String desp;

    @ApiParam(value = "图标")
    @ApiModelProperty(value = "类型")
    private String icon;

    @ApiParam(value = "排序")
    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiParam(value = "父级分类Id")
    @ApiModelProperty(value = "父级分类id")
    private String parent;

    @ApiParam(value = "父级分类名字")
    @ApiModelProperty(value = "父级分类名字")
    private String parentName;

    @ApiParam(value = "商品的Id数组")
    @ApiModelProperty(value = "商品的Id数组")
    private Set<ItemMinInfo> items;

    @ApiParam(value = "是否禁用")
    @ApiModelProperty(value = "是否禁用")
    private boolean disabled;

    boolean getIsAdd() {
        return isAdd;
    }

    void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Set<ItemMinInfo> getItems() {
        return items;
    }

    public void setItems(Set<ItemMinInfo> items) {
        this.items = items;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public static class ItemMinInfo {
        private String id;
        private String title;
        private ItemStatusEnum status;
        private boolean delete;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ItemStatusEnum isStatus() {
            return status;
        }

        public void setStatus(ItemStatusEnum status) {
            this.status = status;
        }

        public boolean isDelete() {
            return delete;
        }

        public void setDelete(boolean delete) {
            this.delete = delete;
        }
    }

//    public void convertToCategory(Category category) {
//        category.setName(this.name);
//        category.setDesp(this.desp);
//        category.setOrder(this.order);
//        category.setIcon(this.icon);
//        category.setDisabled(this.disabled);
//        category.setImg(this.img);
//    }

//    public void convertCategoryToResp(Category category) {
//        this.setName(category.getName());
//        this.setDesp(category.getDesp());
//        this.setOrder(category.getOrder());
//        this.setIcon(category.getIcon());
//        this.setDisabled(category.isDisabled());
//        this.setImg(category.getImg());
//        this.setParent(category.getParent() == null ? null : category.getParent().getId());
//        this.setId(category.getId());
//        this.setParentName(category.getParent() == null ? null : category.getParent().getName());
//        this.items = new HashSet<ItemMinInfo>();
//        for (Item item : category.getItems()) {
//            ItemMinInfo itemMinInfo = new ItemMinInfo();
//            itemMinInfo.setTitle(item.getTitle());
//            itemMinInfo.setStatus(item.getStatus());
//            itemMinInfo.setId(item.getId());
//            itemMinInfo.setDelete(item.isDelete());
//            this.items.add(itemMinInfo);
//        }
//
//    }
}

