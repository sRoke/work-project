package net.kingsilk.qh.oauth.core.wap.resource.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.ErrStatus;
import net.kingsilk.qh.oauth.api.ErrStatusException;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.s.attr.WxComMpAuthInfo;
import net.kingsilk.qh.oauth.api.s.attr.WxMpAuthInfo;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.UserInfoRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.repo.WxUserRepo;
import net.kingsilk.qh.oauth.security.SecService;
import net.kingsilk.qh.oauth.service.ParamUtils;
import net.kingsilk.qh.oauth.service.SessionService;
import net.kingsilk.qh.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component
public class UserResource implements UserApi {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private WxUserRepo wxUserRepo;

    @Autowired
    private UniPageConverter uniPageConverter;

    @Autowired
    private SecService secService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService cs;

    private UserGetResp toUserGetResp(User user) {

        UserGetResp resp = new UserGetResp();
        resp.setId(user.getId());
        resp.setDateCreated(user.getDateCreated());
        resp.setDateCreated(user.getDateCreated());
        resp.setCreatedBy(user.getCreatedBy());
        resp.setLastModifiedDate(user.getLastModifiedDate());
        resp.setLastModifiedBy(user.getLastModifiedBy());

        resp.setEnabled(user.isEnabled());
        resp.setAccountLocked(user.isAccountLocked());
        resp.setAccountExpired(user.isAccountExpired());

        resp.setInviter(user.getInviterUserId());
        resp.setUsername(user.getUsername());
        resp.setPhone(user.getPhone());
        resp.setPhoneVerifiedAt(user.getPhoneVerifiedAt());
        resp.setEmail(user.getEmailVerifiedAt());


        // UserInfo
        UserInfo userInfo = userInfoRepo.findOne(Expressions.allOf(
                QUserInfo.userInfo.userId.eq(user.getId()),
                Expressions.anyOf(
                        QUserInfo.userInfo.deleted.isNull(),
                        QUserInfo.userInfo.deleted.eq(false)
                )
        ));
        if (userInfo != null) {
            resp.setAvatar(userInfo.getAvatar());
            resp.setRealName(userInfo.getRealName());
        }

        // WxUser
        Iterable<WxUser> wxUsersIt = wxUserRepo.findAll(Expressions.allOf(
                QWxUser.wxUser.userId.eq(user.getId()),
                Expressions.anyOf(
                        QWxUser.wxUser.deleted.isNull(),
                        QWxUser.wxUser.deleted.eq(false)
                )
        ));
        List<UserGetResp.WxUser> wxUsers = StreamSupport.stream(wxUsersIt.spliterator(), false)
                .map(wxUser -> {
                    UserGetResp.WxUser u = new UserGetResp.WxUser();
                    u.setAppId(wxUser.getAppId());
                    u.setOpenId(wxUser.getOpenId());
                    u.setUnionId(wxUser.getUnionId());
                    return u;
                })
                .collect(Collectors.toList());

        resp.setWxUsers(wxUsers);
        return resp;
    }

    @Override
    public UniResp<UserGetResp> get(String userId) {


        String loginUserId = null; // FIXME

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));

        if (user == null) {
            UniResp<UserGetResp> uniResp = new UniResp<>();
            uniResp.setStatus(404);
            uniResp.setError("用户 `" + userId + "`不存在");
            return uniResp;
        }


        UserGetResp resp = toUserGetResp(user);

        UniResp<UserGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<UserGetResp>> list(
            Integer size,
            Integer page,
            List<String> sort,
            List<String> userIds
    ) {

        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);

        // or 的查询条件
        BooleanExpression[] anyExprs = Arrays.asList(
                userIds != null && userIds.size() > 0 ? QUser.user.id.in(userIds) : null
        ).stream()
                .filter(v -> !Objects.isNull(v))
                .collect(Collectors.toList())
                .toArray(new BooleanExpression[0]);

        // 进行查询
        Page<User> yunFilePage = userRepo.findAll(Expressions.allOf(
                Expressions.anyOf(anyExprs),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ), pageable);


        Page<UserGetResp> domainPage = yunFilePage.map(i -> toUserGetResp(i));

        UniPage<UserGetResp> respPage = uniPageConverter.convert(domainPage);

        UniResp<UniPage<UserGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respPage);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<UserGetResp>> search(
            Integer size,
            Integer page,
            List<String> sort,
            String q) {

        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);

        Page<User> users = userRepo.findAll(
                Expressions.allOf(
                        QUser.user.deleted.in(false),
                        QUser.user.phone.eq(q)
                ), pageable);

        Page<UserGetResp> domainPage = users.map(i -> toUserGetResp(i));

        UniPage<UserGetResp> respPage = uniPageConverter.convert(domainPage);

        UniResp<UniPage<UserGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respPage);
        return uniResp;
    }

    @Override
    public UniResp<String> addUser(AddUserReq addUserReq) {
        Assert.notNull(addUserReq.getPhone(), "手机号码不能为空");

        User user = userRepo.findOneByPhone(addUserReq.getPhone());

        UniResp<String> uniResp = new UniResp<>();

        UserInfo userInfo;
        if (user != null) {
            if (!StringUtils.isEmpty(addUserReq.getRealName())) {
                userInfo = userInfoRepo.findOne(
                        QUserInfo.userInfo.userId.eq(user.getId())
                );
                if (userInfo != null) {
                    userInfo.setRealName(addUserReq.getRealName());

                } else {
                    userInfo = new UserInfo();
                    userInfo.setRealName(addUserReq.getRealName());
                }
                userInfoRepo.save(userInfo);
            }
            uniResp.setData(user.getId());
        } else {
            user = new User();
            user.setPhone(addUserReq.getPhone());
            User u = userRepo.save(user);
            uniResp.setData(u.getId());
            userInfo = new UserInfo();
            userInfo.setUserId(u.getId());
            if (!StringUtils.isEmpty(addUserReq.getRealName())) {
                userInfo.setRealName(addUserReq.getRealName());
            }
            userInfoRepo.save(userInfo);
            uniResp.setData(u.getId());
        }
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UserGetResp> info() {


        String curUserId = secService.curUserId();
        User user = userService.requireUser(curUserId);

        UserGetResp resp = toUserGetResp(user);
        UniResp<UserGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

//    @Override
//    public UniResp<String> update(AddUserReq addUserReq) {
//        User user = userRepo.findOneByPhone(addUserReq.getPhone());
//        Assert.notNull(user, "更新的用户不存在");
//        UserInfo userInfo = userInfoRepo.findOne(
//                Expressions.allOf(
//                        QUserInfo.userInfo.deleted.in(false),
//                        QUserInfo.userInfo.userId.eq(user.getId())
//                )
//        );
//        Assert.notNull(userInfo, "更新的用户不存在");
//        userInfo.setRealName(addUserReq.getRealName());
//        userInfoRepo.save(userInfo);
//        UniResp<String> uniResp = new UniResp<>();
//        uniResp.setStatus(200);
//        uniResp.setData(userInfo.getId());
//        return uniResp;
//    }

    @Override
    public UniResp<String> update(AddUserReq userAddreq) {
        Assert.notNull(userAddreq.getUserId(), "用户id不能为空");
        User user = userRepo.findOne(userAddreq.getUserId());
        Assert.notNull(user, "没有该用户");

        if (!StringUtils.isEmpty(userAddreq.getPayPassword())) {
            user.setPayPassword(userAddreq.getPayPassword());
        }
        if (!StringUtils.isEmpty(userAddreq.getPassword())) {
            user.setPassword(userAddreq.getPassword());
        }
        userRepo.save(user);
        UserInfo userInfo = userInfoRepo.findOne(
                QUserInfo.userInfo.userId.eq(userAddreq.getUserId())
        );
        if (userInfo != null) {
            if (!StringUtils.isEmpty(userAddreq.getRealName())) {
                userInfo.setRealName(userAddreq.getRealName());
            }

        } else {
            userInfo = new UserInfo();
            userInfo.setUserId(userAddreq.getUserId());
            if (!StringUtils.isEmpty(userAddreq.getRealName())) {
                userInfo.setRealName(userAddreq.getRealName());
            }
        }
        userInfoRepo.save(userInfo);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UserGetResp> getInfoByUserId(String userId) {
        User user = userService.requireUser(userId);
        UserGetResp resp = toUserGetResp(user);


        List<String> list = resp.getWxUsers().stream()
                .map(UserGetResp.WxUser::getOpenId)
                .collect(Collectors.toList());

        List<WxMpAuthInfo> wxMpAuthInfos = sessionService.getWxMpAuthInfos();

        WxMpAuthInfo wxMpAuthInfo = wxMpAuthInfos.isEmpty()
                ? null
                : wxMpAuthInfos.get(wxMpAuthInfos.size() - 1);

        List<WxComMpAuthInfo> wxComMpAuthInfos = sessionService.getWxComMpAuthInfos();

        WxComMpAuthInfo wxComMpAuthInfo = wxComMpAuthInfos.isEmpty()
                ? null
                : wxComMpAuthInfos.get(wxComMpAuthInfos.size() - 1);

        String curWxMpId = null;
        if (!list.isEmpty()
                && wxMpAuthInfo != null) {
            curWxMpId = wxMpAuthInfo.getWxMpAppId();
        } else if (!list.isEmpty()
                && wxComMpAuthInfo != null) {
            curWxMpId = wxComMpAuthInfo.getWxMpAppId();
        }
        resp.setCurWxMpId(curWxMpId);
        UniResp<UserGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<UserGetResp> getInfoByPhone(String phone) {
        User user = userRepo.findOne(allOf(
                QUser.user.phone.eq(phone),
                QUser.user.deleted.ne(true)
        ));
        if (user == null) {
            throw new ErrStatusException(ErrStatus.USER_404_WITH_WX, "找不到用户");
        }
        UserGetResp resp = toUserGetResp(user);

        List<String> list = resp.getWxUsers().stream()
                .map(UserGetResp.WxUser::getOpenId)
                .collect(Collectors.toList());

        List<WxMpAuthInfo> wxMpAuthInfos = sessionService.getWxMpAuthInfos();

        WxMpAuthInfo wxMpAuthInfo = wxMpAuthInfos.isEmpty()
                ? null
                : wxMpAuthInfos.get(wxMpAuthInfos.size() - 1);

        List<WxComMpAuthInfo> wxComMpAuthInfos = sessionService.getWxComMpAuthInfos();

        WxComMpAuthInfo wxComMpAuthInfo = wxComMpAuthInfos.isEmpty()
                ? null
                : wxComMpAuthInfos.get(wxComMpAuthInfos.size() - 1);

        String curWxMpId = null;
        if (!list.isEmpty()
                && wxMpAuthInfo != null) {
            curWxMpId = wxMpAuthInfo.getWxMpAppId();
        } else if (!list.isEmpty()
                && wxComMpAuthInfo != null) {
            curWxMpId = wxComMpAuthInfo.getWxMpAppId();
        }
        resp.setCurWxMpId(curWxMpId);

//        List<String> list = resp.getWxUsers().stream()
//                .map(UserGetResp.WxUser::getOpenId)
//                .collect(Collectors.toList());
//
//        List<WxMpAuthInfo> wxMpAuthInfos = sessionService.getWxMpAuthInfos();
//
//        WxMpAuthInfo wxMpAuthInfo = wxMpAuthInfos.isEmpty()
//                ? null
//                : wxMpAuthInfos.get(wxMpAuthInfos.size() - 1);
//
//        List<WxComMpAuthInfo> wxComMpAuthInfos = sessionService.getWxComMpAuthInfos();
//
//        WxComMpAuthInfo wxComMpAuthInfo = wxComMpAuthInfos.isEmpty()
//                ? null
//                : wxComMpAuthInfos.get(wxComMpAuthInfos.size() - 1);
//
//        SecurityContextHolder.getContext().getAuthentication().
//        String curWxMpId = null;
//        if(!list.isEmpty()
//                && wxMpAuthInfo!=null){
//            curWxMpId = wxMpAuthInfo.getWxMpAppId();
//        }else if(!list.isEmpty()
//                && wxComMpAuthInfo!=null){
//            curWxMpId = wxComMpAuthInfo.getWxMpAppId();
//        }
//        resp.setCurWxMpId(curWxMpId);

        UniResp<UserGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<String> getOpenIdByUserId(String userId) {
        User user = userService.requireUser(userId);
        UserGetResp resp = toUserGetResp(user);
        List<String> list = resp.getWxUsers().stream()
                .map(UserGetResp.WxUser::getOpenId)
                .collect(Collectors.toList());
        List<WxMpAuthInfo> wxMpAuthInfos = sessionService.getWxMpAuthInfos();

        WxMpAuthInfo wxMpAuthInfo = wxMpAuthInfos.isEmpty()
                ? null
                : wxMpAuthInfos.get(wxMpAuthInfos.size() - 1);

        List<WxComMpAuthInfo> wxComMpAuthInfos = sessionService.getWxComMpAuthInfos();

        WxComMpAuthInfo wxComMpAuthInfo = wxComMpAuthInfos.isEmpty()
                ? null
                : wxComMpAuthInfos.get(wxComMpAuthInfos.size() - 1);
        String openId = null;
        if (!list.isEmpty()
                && wxMpAuthInfo != null) {
            openId = wxMpAuthInfo.getOpenId();
        } else if (!list.isEmpty()
                && wxComMpAuthInfo != null) {
            openId = wxComMpAuthInfo.getOpenId();
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(openId);
        return uniResp;
    }
}
