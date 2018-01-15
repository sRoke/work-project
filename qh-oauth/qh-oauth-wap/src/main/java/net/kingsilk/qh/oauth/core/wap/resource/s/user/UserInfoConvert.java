package net.kingsilk.qh.oauth.core.wap.resource.s.user;

import net.kingsilk.qh.oauth.api.s.user.dto.UserInfoResp;
import net.kingsilk.qh.oauth.domain.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConvert {

    public UserInfoResp userInfoModelConvert(UserInfo userInfo) {

        UserInfoResp infoModel = new UserInfoResp();

        infoModel.setUserId(userInfo.getUserId());
        infoModel.setIdNo(userInfo.getIdNo());
        infoModel.setAvatar(userInfo.getAvatar());
        infoModel.setRealName(userInfo.getRealName());
        infoModel.setQq(userInfo.getQq());
        infoModel.setWxNo(userInfo.getWxNo());
        return infoModel;
    }
}
