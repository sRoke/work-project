package net.kingsilk.qh.oauth.core.wap.controller.user;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.controller.*;
import net.kingsilk.qh.oauth.core.wap.controller.user.model.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.annotation.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.*;

@RestController
@RequestMapping("/api/user")
@Deprecated
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private WxService wxService;

    @Autowired
    private SmsRepo smsRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    private QhOAuthProperties authProperties;

    @RequestMapping(path = "/phoneLoginCheck",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("permitAll")
    public UniResp<String> phoneLoginCheck(String phone,
                                           String verifyCode) {
        //验证验证码的真实性
        Sms sms = smsRepository.findOne(
                allOf(
                        QSms.sms.phone.eq(phone),
                        QSms.sms.verifyCode.eq(verifyCode),
                        QSms.sms.valid.eq(true),
                        QSms.sms.codeExpiredTime.after(new Date())
                )
        );
        if (sms == null) {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setStatus(10024);
            uniResp.setError("手机号或验证码错误");
            return uniResp;
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    @RequestMapping(path = "/wxLoginCheck",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("permitAll")
    public UniResp<String> wxLoginCheck(String code) {
        User user = wxService.getUserAtByCode(code);

        if (user == null) {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setStatus(10024);
            uniResp.setError("微信登录失败");
            return uniResp;
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(user.getId());
        return uniResp;
    }

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "获取用户/客户端信息",
            nickname = "获取用户/客户端信息",
            notes = "获取用户/客户端信息"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    })
    @ResponseBody
    //TODO 以下注解需要更换，参考qh-pay-wap
    //TODO 返回信息需修改，暂时测试试用
    @PreAuthorize("isAuthenticated()")
    public UniResp<UserModel> info(@AuthenticationPrincipal String curUserId) {
        log.debug("-----------/api/user/info   userid = " + curUserId);
        User user = userRepo.findOne(curUserId);
        Assert.notNull(user, "用户错误");
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getId());
        userModel.setPhone(user.getPhone());

        // FIXME
//        if (!StringUtils.isEmpty(user.getOpenId())) {
//            userModel.setOpenId(user.getOpenId());
//        } else {
//            userModel.setOpenId(user.getWxQyhOpenId());
//        }

        UniResp<UserModel> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(userModel);
        return uniResp;

    }

    /**
     * 绑定手机号
     * @param curUserId ;当前用户id
     * @param phone ;手机号
     * @param code ;验证码
     */
    @RequestMapping(path = "/bindPhone",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "绑定手机号",
            nickname = "绑定手机号",
            notes = "绑定手机号"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    })
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public UniResp<String> bindPhone(@AuthenticationPrincipal String curUserId, String phone, String code) {
        log.debug("-----------/api/user/bindPhone   userid = " + curUserId);
        Assert.isTrue(!StringUtils.isEmpty(phone), "手机号不能为空");
        Assert.isTrue(!StringUtils.isEmpty(code), "验证码不能为空");

        Sms sms = smsRepo.findOneByPhoneAndVerifyCodeAndIsValid(phone, code, true);
        Assert.notNull(sms, "验证码错误");
        sms.setValid(false);
        smsRepo.save(sms);
        User phoneUser = userRepo.findOneByPhone(phone);
        Assert.isNull(phoneUser, "该手机号已被使用");

        User user = userRepo.findOne(curUserId);
        Assert.notNull(user, "用户错误");
        user.setPhone(phone);
        userRepo.save(user);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;

    }

    @RequestMapping(path = "/sendVerifyCode",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "发送验证码",
            nickname = "发送验证码",
            notes = "发送验证码"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    })
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public UniResp<String> sendVerifyCode(String phone) {

        aliSmsService.sendSms(phone);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }
}
