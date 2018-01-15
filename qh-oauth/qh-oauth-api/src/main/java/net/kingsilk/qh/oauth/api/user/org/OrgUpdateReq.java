package net.kingsilk.qh.oauth.api.user.org;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.core.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class OrgUpdateReq {


    @ApiModelProperty("地址名称")
    private Optional<String> name = Optional.empty();

    @ApiModelProperty("地址名称")
    private Optional<OrgStatusEnum> status = Optional.empty();

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<OrgStatusEnum> getStatus() {
        return status;
    }

    public void setStatus(Optional<OrgStatusEnum> status) {
        this.status = status;
    }
}
