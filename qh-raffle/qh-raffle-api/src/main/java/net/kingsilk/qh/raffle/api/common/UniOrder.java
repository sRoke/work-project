package net.kingsilk.qh.raffle.api.common;

import io.swagger.annotations.*;

/**
 * 参考 spring-data 中的 Page.Order，方便 Jackson 实例化。
 */
@ApiModel
public class UniOrder {

    @ApiModelProperty("属性")
    private String property;

    @ApiModelProperty("是否是降序")
    private boolean desc;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

}
