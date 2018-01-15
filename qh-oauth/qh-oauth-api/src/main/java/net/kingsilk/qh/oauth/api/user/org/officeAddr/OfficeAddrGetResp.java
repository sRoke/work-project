package net.kingsilk.qh.oauth.api.user.org.officeAddr;

import io.swagger.annotations.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class OfficeAddrGetResp {

    // --------------------------------------- common fields

    @ApiModelProperty("组织ID")
    private String id;

    @ApiModelProperty("创建时间")
    private Date dateCreated;

    @ApiModelProperty("创建者的ID")
    private String createdBy;

    @ApiModelProperty("最后修改日期")
    private Date lastModifiedDate;

    @ApiModelProperty("最后更新者的ID")
    private String lastModifiedBy;


    // --------------------------------------- biz fields

    @ApiModelProperty("所属组织ID")
    private String orgId;


    @ApiModelProperty("行政区划代码(6位数字)")
    private String adc;

    @ApiModelProperty("街道")
    private String street;

    @ApiModelProperty("邮政编码")
    private String postCode;

    @ApiModelProperty("百度地图中经度")
    private String coorX;

    @ApiModelProperty("百度地图中纬度")
    private String coorY;

    @ApiModelProperty("联系人姓名")
    private String contact;

    @ApiModelProperty("联系人手机号")
    private Set<String> phones;

    @ApiModelProperty("用户备注")
    private String memo;

    @ApiModelProperty("是否是默认地址")
    private boolean defaultAddr;

    // ------------------------ 自动生成的 getter、 setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCoorX() {
        return coorX;
    }

    public void setCoorX(String coorX) {
        this.coorX = coorX;
    }

    public String getCoorY() {
        return coorY;
    }

    public void setCoorY(String coorY) {
        this.coorY = coorY;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
