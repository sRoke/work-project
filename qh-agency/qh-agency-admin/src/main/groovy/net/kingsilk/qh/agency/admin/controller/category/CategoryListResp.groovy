package net.kingsilk.qh.agency.admin.controller.category;

import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "CategoryListResp")
public class CategoryListResp {

    private String id;
    private String name;
    private String status;
    private String desp;
    private String icon;
    private String code;

    private List<CategoryListResp> data;
    private String typeName;
    private String typeDesp;
    private String parent;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CategoryListResp> getData() {
        return data;
    }

    public void setData(List<CategoryListResp> data) {
        this.data = data;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesp() {
        return typeDesp;
    }

    public void setTypeDesp(String typeDesp) {
        this.typeDesp = typeDesp;
    }

}
