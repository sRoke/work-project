package net.kingsilk.qh.oauth.core.wap.resource.s.user;

import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.s.attr.WxComMpAuthInfo;
import net.kingsilk.qh.oauth.api.s.attr.WxMpAuthInfo;
import net.kingsilk.qh.oauth.api.s.user.BindSessionWxMpResp;
import net.kingsilk.qh.oauth.api.s.user.UserApi;
import net.kingsilk.qh.oauth.api.s.user.dto.InfoResp;
import net.kingsilk.qh.oauth.api.s.user.dto.UserAddreq;
import net.kingsilk.qh.oauth.api.s.user.dto.UserInfoResp;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.SmsRepo;
import net.kingsilk.qh.oauth.repo.UserInfoRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.repo.WxUserRepo;
import net.kingsilk.qh.oauth.security.SecService;
import net.kingsilk.qh.oauth.service.SessionService;
import net.kingsilk.qh.oauth.service.UserService;
import net.kingsilk.qh.oauth.service.WxUserService;
import net.kingsilk.wx4j.broker.api.wxMp.user.auth.VerifyAuthCodeResp;
import net.kingsilk.wx4j.broker.api.wxMp.user.auth.WxMpUserAuthApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

/**
 *
 */
@Component("sUserResource")
@Path("/s/user")
public class UserResource implements UserApi {

    @Autowired
    private WxUserRepo wxUserRepo;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private SmsRepo smsRepo;


    @Autowired
    private ConversionService cs;

    @Autowired
    private SecService secService;

    @Autowired
    private UserInfoConvert userInfoConvert;

    @Autowired
    private UserService userService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private WxMpUserAuthApi wxMpUserAuthApi;

    @Override
    public UniResp<InfoResp> info() {


        String curUserId = secService.curUserId();
        User user = userService.requireUser(curUserId);

        InfoResp infoResp = cs.convert(user, InfoResp.class);

        // 获取绑定的微信用户信息。
        Iterable<WxUser> wxUserIt = wxUserRepo.findAll(allOf(
                QWxUser.wxUser.userId.eq(curUserId),
                QWxUser.wxUser.deleted.eq(false)
        ));

        List<InfoResp.WxUser> wxUsers = StreamSupport.stream(wxUserIt.spliterator(), false)
                .map(wxUser -> cs.convert(wxUser, InfoResp.WxUser.class))
                .collect(Collectors.toList());

        infoResp.setWxUsers(wxUsers);

        UniResp<InfoResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(infoResp);
        return uniResp;
    }

    @Override
    public UniResp<Void> bindPhone(
            String phone,
            String code
    ) {
        Assert.isTrue(StringUtils.hasText(phone), "手机号不能为空");
        Assert.isTrue(StringUtils.hasText(code), "手机号不能为空");


        String curUserId = secService.curUserId();
        User user = userService.requireUser(curUserId);

        // 验证手机号是否已经被占用
        boolean inUse = userService.isPhoneInUse(phone, curUserId);
        Assert.isTrue(!inUse, "手机号已经被占用");


        // TODO 验证短信验证码
        // TODO 更新短信验证码——已使用


        user.setPhone(phone);
        user.setPhoneVerifiedAt(new Date());
        userRepo.save(user);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setData(null);
        uniResp.setStatus(200);
        return uniResp;
    }

    private Object findLastWxAuthInfo() {
        List<WxMpAuthInfo> wxMpAuthInfos = sessionService.getWxMpAuthInfos();

        WxMpAuthInfo wxMpAuthInfo = wxMpAuthInfos.isEmpty()
                ? null
                : wxMpAuthInfos.get(wxMpAuthInfos.size() - 1);

        List<WxComMpAuthInfo> wxComMpAuthInfos = sessionService.getWxComMpAuthInfos();

        WxComMpAuthInfo wxComMpAuthInfo = wxComMpAuthInfos.isEmpty()
                ? null
                : wxComMpAuthInfos.get(wxComMpAuthInfos.size() - 1);


        if (wxMpAuthInfo == null) {
            if (wxComMpAuthInfo == null) {
                return null;
            } else {
                return wxComMpAuthInfo;
            }

        } else {
            if (wxComMpAuthInfo == null) {
                return wxMpAuthInfo;
            } else {
                return wxMpAuthInfo.getTime().after(wxComMpAuthInfo.getTime())
                        ? wxMpAuthInfo
                        : wxComMpAuthInfo;
            }
        }

    }

    @Override
    public UniResp<BindSessionWxMpResp> bindSessionWxMp(

    ) {

        Object lastWxAuthInfo = findLastWxAuthInfo();

        if (lastWxAuthInfo == null) {
            UniResp<BindSessionWxMpResp> uniResp = new UniResp<>();
            uniResp.setData(null);
            uniResp.setStatus(200);
            return uniResp;
        }

        String wxMpAppId;
        String openId;

        if (lastWxAuthInfo instanceof WxMpAuthInfo) {
            WxMpAuthInfo info = (WxMpAuthInfo) lastWxAuthInfo;
            wxMpAppId = info.getWxMpAppId();
            openId = info.getOpenId();
        } else {
            WxComMpAuthInfo info = (WxComMpAuthInfo) lastWxAuthInfo;
            wxMpAppId = info.getWxMpAppId();
            openId = info.getOpenId();
        }

        String curUserId = secService.curUserId();
        User user = userService.requireUser(curUserId);

        // 绑定
        wxUserService.bindWx(user, wxMpAppId, openId);


        // 返回
        BindSessionWxMpResp resp = new BindSessionWxMpResp();
        resp.setUserId(curUserId);
        resp.setWxMpAppId(wxMpAppId);
        resp.setOpenId(openId);

        UniResp<BindSessionWxMpResp> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<Void> bindWxMp(
            String wxMpAppId,
            String code,
            String state
    ) {


        String curUserId = secService.curUserId();
        User user = userService.requireUser(curUserId);

        net.kingsilk.wx4j.broker.api.UniResp<VerifyAuthCodeResp> verifyUniResp = wxMpUserAuthApi.verifyAuthCode(wxMpAppId, code, state);

        Assert.isTrue(verifyUniResp != null
                && verifyUniResp.getStatus() == 200, "调用 API 出错");


        VerifyAuthCodeResp verifyResp = verifyUniResp.getData();

        wxUserService.bindWx(user, verifyResp.getWxMpAppId(), verifyResp.getOpenId());

        WxMpAuthInfo authInfo = new WxMpAuthInfo();
        authInfo.setWxMpAppId(wxMpAppId);
        authInfo.setOpenId(verifyResp.getOpenId());
        authInfo.setCode(code);
        authInfo.setState(state);
        authInfo.setTime(new Date());
        sessionService.add(authInfo);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setData(null);
        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    public UniResp<String> save(UserAddreq userAddreq) {
        Assert.notNull(userAddreq.getPhone(), "手机号不能为空");
        User user = userRepo.findOneByPhone(userAddreq.getPhone());
        Assert.isNull(user, "该手机号已经被注册");
        user = new User();
        user.setPhone(userAddreq.getPhone());
        Assert.notNull(userAddreq.getPassword(), "密码不能为空");
        Assert.isTrue(userAddreq.getPassword().length() > 6, "密码不能下于6位");
        user.setPassword(userAddreq.getPassword());
        userRepo.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setRealName(userAddreq.getRealName());
        userInfo.setUserId(user.getId());
        userInfoRepo.save(userInfo);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }

    @Override
    public UniResp<String> update(UserAddreq userAddreq) {
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
            if (!StringUtils.isEmpty(userAddreq.getRealName())) {
                userInfo.setRealName(userAddreq.getRealName());
            }
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<List<UserInfoResp>> list(List<String> userIds) {
        Iterable<UserInfo> list = userInfoRepo.findAll(
                allOf(
                        QUserInfo.userInfo.deleted.in(false),
                        QUserInfo.userInfo.userId.in(userIds)
                )
        );
        UniResp<List<UserInfoResp>> lt = new UniResp<>();
        lt.setData(new ArrayList<>());
        list.forEach(
                userInfo -> {
                    UserInfoResp infoResp = userInfoConvert.userInfoModelConvert(userInfo);
                    User user = userRepo.findOne(
                            QUser.user.id.eq(userInfo.getUserId())
                    );
                    infoResp.setPhone(user.getPhone());
                    lt.getData().add(infoResp);
                }
        );
        lt.setStatus(200);
        return lt;
    }

    @Override
    public UniResp<List<String>> search(String keyWords) {

        Iterable<UserInfo> list = userInfoRepo.findAll(
                allOf(
                        QUserInfo.userInfo.deleted.in(false),
                        anyOf(
                                !StringUtils.isEmpty(keyWords) ? QUserInfo.userInfo.userId.like("%" + keyWords + "%") : null,
                                !StringUtils.isEmpty(keyWords) ? QUserInfo.userInfo.realName.like("%" + keyWords + "%") : null
                        )
                )
        );

        Iterable<User> userList = userRepo.findAll(
                allOf(
                        QUser.user.deleted.in(false),
                        anyOf(
                                !StringUtils.isEmpty(keyWords) ? QUser.user.phone.like("%" + keyWords + "%") : null,
                                !StringUtils.isEmpty(keyWords) ? QUser.user.username.like("%" + keyWords + "%") : null
                        )

                )
        );

        UniResp<List<String>> lt = new UniResp<>();
        lt.setData(new ArrayList<>());

        if (list != null) {

            list.forEach(
                    userInfo ->
                            lt.getData().add(userInfo.getUserId())

            );
        }

        if (userList != null) {

            userList.forEach(
                    user ->
                            lt.getData().add(user.getId())
            );

        }
        lt.setStatus(200);
        return lt;

    }

}
