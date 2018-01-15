package net.kingsilk.qh.oauth.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.ErrStatus;
import net.kingsilk.qh.oauth.api.ErrStatusException;
import net.kingsilk.qh.oauth.domain.QWxUser;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.domain.WxUser;
import net.kingsilk.qh.oauth.repo.WxUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Service
public class WxUserService {


    @Autowired
    private WxUserRepo wxUserRepo;

    @Nullable
    public WxUser getWxUserFromDB(String wxMpAppId, String wxOpenId) {
        return wxUserRepo.findOne(allOf(
                QWxUser.wxUser.appId.eq(wxMpAppId),
                QWxUser.wxUser.openId.eq(wxOpenId),
                QWxUser.wxUser.deleted.eq(false)
        ));
    }

    @Nonnull
    public WxUser requireWxUser(String wxMpAppId, String wxOpenId) {
        WxUser wxUser = getWxUserFromDB(wxMpAppId, wxOpenId);
        if (wxUser == null) {
            throw new ErrStatusException(
                    ErrStatus.USER_404_WITH_WX,
                    "未找到绑定该微信公众号的用户"
            );
        }
//        Assert.isTrue(wxUser != null, "微信用户尚未授权");
        return wxUser;
    }


    /**
     * 绑定用户微信信息。
     */
    public void bindWx(
            User user,
            String wxMpAppId,
            String openId
    ) {
        Assert.notNull(user, "user 不能为空");
        Assert.notNull(wxMpAppId, "appId 不能为空");
        Assert.notNull(openId, "openId 不能为空");
//        Optional.ofNullable(wxUserRepo.findAll(
//                Expressions.allOf(
//                        QWxUser.wxUser.userId.eq(user.getId()),
//                        QWxUser.wxUser.appId.eq(wxMpAppId),
//                        QWxUser.wxUser.deleted.ne(true)
//                )
//        )).ifPresent(it ->
//                Lists.newArrayList(it).stream().forEach(wxUser1 -> {
//                            wxUser1.setDeleted(true);
//                            wxUserRepo.save(wxUser1);
//                        }
//
//                )
//        );
//
//
        WxUser wxUser = wxUserRepo.findOne(allOf(
                QWxUser.wxUser.appId.eq(wxMpAppId),
                QWxUser.wxUser.openId.eq(openId)
//                QWxUser.wxUser.deleted.eq(false)
        ));
//        if (wxUser == null) {
//            wxUser = new WxUser();
//            wxUser.setAppId(wxMpAppId);
//            wxUser.setOpenId(openId);
//        }
//        wxUser.setUserId(user.getId());
//        wxUserRepo.save(wxUser);
//

        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setAppId(wxMpAppId);
            wxUser.setOpenId(openId);
        }
        wxUser.setDeleted(false);
        wxUser.setUserId(user.getId());
        wxUserRepo.save(wxUser);
    }

}
