package net.kingsilk.qh.oauth.service;

import com.querydsl.core.types.dsl.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.security.*;
import org.hibernate.validator.internal.constraintvalidators.hv.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 *
 */
@Service
public class UserService {



    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SecService secUtils;

    @Autowired
    private WxUserRepo wxUserRepo;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private VerifyService verifyService;


    @Nonnull
    public LoginResp toLoginResp(String userId) {
        User user = requireUser(userId);
        LoginResp loginResp = new LoginResp();
        loginResp.setUserId(user.getId());
        loginResp.setPhone(user.getPhone());
        loginResp.setPhoneVerifiedAt(user.getPhoneVerifiedAt());
        return loginResp;
    }

    @Nullable
    public User getUserFromDB(String userId) {
        return userRepo.findOne(allOf(
                QUser.user.id.eq(userId),
                QUser.user.deleted.eq(false)
        ));
    }


    @Nonnull
    public User requireUser(String userId) {
        User user = getUserFromDB(userId);
        Assert.isTrue(user != null, "用户不存在");
        return user;
    }


    @Nullable
    public User getUserFromDB(String wxMpAppId, String wxOpenId) {
        WxUser wxUser = wxUserService.getWxUserFromDB(wxMpAppId, wxOpenId);
        if (wxUser == null) {
            return null;
        }

        return getUserFromDB(wxUser.getUserId());
    }

    @Nonnull
    public User requireUser(String wxMpAppId, String wxOpenId) {

        WxUser wxUser = wxUserService.requireWxUser(wxMpAppId, wxOpenId);

        return requireUser(wxUser.getUserId());
    }







    /**
     * 通过用户名和密码创建新用户
     * @param username 用户名
     * @param password 密码
     * @return 新用户
     */
    public User registerByPwd(
            String username,
            String password
    ) {

        Assert.notNull(username, "username 不能为空");
        Assert.isTrue(verifyService.isValidUsername(username), "用户名只能由3~16位英文字符、数字和下划线组成，且不能以数字开头");

        Assert.notNull(password, "password 不能为空");
        Assert.isTrue(password.length() >= 6, "密码应至少6位字符");

        // 先检查是否已经存在
        long existed = userRepo.count(
                allOf(QUser.user.username.eq(username))
        );
        Assert.isTrue(existed > 0, "用户 '" + username + "' 已存在");

        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(secUtils.encodePassword(password));
        userRepo.save(user);
        return user;
    }

//    /**
//     * 通过微信账号信息注册用户。
//     *
//     * @param openId 用户的微信 open id
//     * @return 新用户
//     */
//    public User registerByWx(String openId) {
//
//        Assert.notNull(openId, "openId 不能为空");
//
//        // 检查是否存在
//        User user = findUserByWx(openId);
//        Assert.isNull(user, "用户 openId='" + openId + "' 已存在");
//
//        // 创建新用户
//        user = new User();
//        user.setOpenId(openId);
//        userRepo.save(user);
//        return user;
//    }

//    /**
//     * 通过微信企业号账号信息注册用户。
//     *
//     * @param wxQyhOpenId 用户的微信企业号 open id
//     * @param wxQhyUserId 用户的微信企业号 userId
//     * @return 新用户
//     */
//    public User registerByWxQyh(String wxQyhOpenId) {
//
//        Assert.notNull(wxQyhOpenId, "wxQyhOpenId 不能为空");
//
//        // 检查是否存在
//        User user = findUserByWxQyhOpenId(wxQyhOpenId);
//        Assert.isNull(user, "用户 wxQyhOpenId='" + wxQyhOpenId + "' 已存在");
//
//        // 创建新用户
//        user = new User();
//        user.setWxQyhOpenId(wxQyhOpenId);
//        userRepo.save(user);
//        return user;
//    }

    /**
     * 通过微信账号信息注册用户。
     *
     * 注意：该方法只能在手机号验证码验证通过后才能调用。
     *
     * @param phone 手机号码
     * @return 新用户
     */
    public User registerByPhone(String phone) {

        Assert.notNull(phone, "phone 不能为空");
        Assert.isTrue(verifyService.isValidPhone(phone), "手机号码格式不正确");

        long existed = userRepo.count(allOf(
                QUser.user.phone.eq(phone),
                QUser.user.phoneVerifiedAt.isNotNull()
        ));
        Assert.isTrue(existed <= 0, "用户 '" + phone + "' 已存在");

        User user = new User();
        user.setPhone(phone);
        user.setPhoneVerifiedAt(new Date());
        userRepo.save(user);
        return user;
    }

    public User registerByEmail(String email) {

        Assert.notNull(email, "email 不能为空");
        Assert.isTrue(verifyService.isValidEmail(email), "电子邮箱格式不正确");

        long existed = userRepo.count(allOf(
                QUser.user.email.eq(email),
                QUser.user.emailVerifiedAt.isNotNull()
        ));
        Assert.isTrue(existed > 0, "用户 '" + email + "' 已存在");

        User user = new User();
        user.setEmail(email);
        user.setEmailVerifiedAt(new Date());
        userRepo.save(user);
        return user;
    }

    /**
     * 给老用户绑定用户名。不可重复绑定。
     * @param user 要绑定的用户
     * @param username 要绑定的用户名
     */
    public void bindUsername(
            User user,
            String username
    ) {
        Assert.notNull(user, "user 不能为空");
        Assert.isNull(user.getUsername(), "已经设定过用户名");
        Assert.notNull(username, "username 不能为空");
        Assert.isTrue(verifyService.isValidUsername(username), "用户名只能由3~16位英文字符、数字和下划线组成，且不能以数字开头");

        long existed = userRepo.count(allOf(
                QUser.user.deleted.isFalse(),
                QUser.user.id.ne(user.getId()),
                QUser.user.username.eq(username)
        ));
        Assert.isTrue(existed > 0, "用户 '" + username + "' 已存在");

        user.setUsername(username);
        userRepo.save(user);
    }


    /**
     * 给老用户绑定手机号。可重复绑定。
     * @param user 老用户
     * @param phone 手机号码
     */
    public void bindPhone(
            User user,
            String phone
    ) {
        Assert.notNull(user, "user 不能为空");
        Assert.isTrue(verifyService.isValidPhone(phone), "手机号码格式不正确");

        long existed = userRepo.count(allOf(
                QUser.user.deleted.isFalse(),
                QUser.user.id.ne(user.getId()),
                QUser.user.phone.eq(phone),
                QUser.user.phoneVerifiedAt.isNotNull()
        ));
        Assert.isTrue(existed > 0, "手机号 '" + phone + "' 已被占用");

        user.setPhone(phone);
        userRepo.save(user);
    }

    public void bindEmail(
            User user,
            String email
    ) {
        Assert.notNull(user, "user 不能为空");
        Assert.isTrue(verifyService.isValidEmail(email), "电子邮箱格式正确");

        long existed = userRepo.count(allOf(
                QUser.user.deleted.isFalse(),
                QUser.user.id.ne(user.getId()),
                QUser.user.email.eq(email),
                QUser.user.emailVerifiedAt.isNotNull()
        ));
        Assert.isTrue(existed > 0, "电子邮箱 '" + email + "' 已被占用");

        user.setEmail(email);
        user.setEmailVerifiedAt(new Date());
        userRepo.save(user);
    }

    /**
     * 更新密码。
     *
     * @param user
     * @param newPassword
     */
    public void updatePassword(User user, String newPassword) {
        Assert.notNull(user, "user 不能为空");
        Assert.notNull(newPassword, "密码不能为空");
        Assert.isTrue(newPassword.length() >= 6, "密码应至少6位字符");

        user.setPassword(secUtils.encodePassword(newPassword));
        userRepo.save(user);
    }


    public boolean isPhoneInUse(String phone, String... exceptUserIds) {

        List<BooleanExpression> exprs = Arrays.asList(
                QUser.user.deleted.isFalse(),
                exceptUserIds != null && exceptUserIds.length > 0
                        ? QUser.user.id.in(exceptUserIds)
                        : null,
                QUser.user.phone.eq(phone),
                QUser.user.phoneVerifiedAt.isNotNull()
        ).stream()
                .filter(expr -> expr != null)
                .collect(Collectors.toList());

        long count = userRepo.count(allOf(
                exprs.toArray(new BooleanExpression[0])
        ));

        return count > 0;
    }
//
//    /**
//     * 根据微信 openId 获取用户
//     * @param openId
//     * @return
//     */
//    public User findUserByWx(String openId) {
//        User user = userRepo.findOne(allOf(
//                QUser.user.openId.eq(openId)
//        ));
//        return user;
//    }
//
//    /**
//     * 根据微信企业号 openId 获取用户
//     * @param wxQyhOpenId
//     * @return
//     */
//    public User findUserByWxQyhOpenId(String wxQyhOpenId) {
//        User user = userRepo.findOne(allOf(
//                QUser.user.wxQyhOpenId.eq(wxQyhOpenId)
//        ));
//        return user;
//    }

}
