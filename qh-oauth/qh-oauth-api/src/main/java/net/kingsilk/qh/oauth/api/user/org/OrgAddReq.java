package net.kingsilk.qh.oauth.api.user.org;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.core.*;

/**
 *
 */
@ApiModel
public class OrgAddReq {


    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("组织状态")
    private OrgStatusEnum status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrgStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrgStatusEnum status) {
        this.status = status;
    }
}
