package net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerAccount;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerAccount.PartnerAccountApi;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PaLogResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogReq;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogResp;
import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.core.OrderStatusEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.*;
//<<<<<<< HEAD
//<<<<<<< HEAD:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/brandApp/partnerAccount/PartnerAccountResource.java
//<<<<<<< HEAD:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/brandApp/partner/partnerAccount/PartnerAccountResource.java
//<<<<<<< HEAD:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/partnerAccount/PartnerAccountResource.java
//import net.kingsilk.qh.agency.server.resource.partnerAccount.convert.PartnerAccountConvert;
//import net.kingsilk.qh.agency.service.*;
//=======
//import net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerAccount.convert.PartnerAccountConvert;
//=======
//import net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount.convert.PartnerAccountConvert;
//>>>>>>> 代码分离:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/brandApp/partnerAccount/PartnerAccountResource.java
//=======
//import net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerAccount.convert.PartnerAccountConvert;
//>>>>>>> 调整:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/brandApp/partner/partnerAccount/PartnerAccountResource.java
//=======
import net.kingsilk.qh.agency.security.BrandAppIdFilter;
import net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount.convert.PartnerAccountConvert;
//<<<<<<< HEAD
////>>>>>>> 调整
//import net.kingsilk.qh.agency.service.AliSmsService;
//import net.kingsilk.qh.agency.service.CalOrderPriceService;
//import net.kingsilk.qh.agency.service.PartnerStaffService;
//import net.kingsilk.qh.agency.service.SecService;
////>>>>>>> 代码重构:qh-agency-server/src/main/groovy/net/kingsilk/qh/agency/server/resource/brandApp/partner/partnerAccount/PartnerAccountResource.java
//=======
import net.kingsilk.qh.agency.service.*;
//>>>>>>> 调整
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Component(value = "partnerAccount")
public class PartnerAccountResource implements PartnerAccountApi {
    @Autowired
    private PartnerStaffService partnerStaffService;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private CalOrderPriceService calOrderPriceService;

    @Autowired
    private PartnerAccountConvert accountConvert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private RefundSkuRepo refundSkuRepo;

    @Autowired
    private PALogService paLogService;

    @Autowired
    private SecService secService;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Autowired
    private PartnerService partnerService;

    @Override
    public UniResp<PartnerAccountInfoResp> info(
            String brandAppId,
            String partnerId) {
        partnerService.check();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        );

        if (partnerAccount == null) {
            partnerAccount = new PartnerAccount();
            partnerAccount.setBrandAppId(brandAppId);
            partnerAccount.setPartner(partnerStaff.getPartner());
            partnerAccount.setNoCashBalance(0);
            partnerAccount.setFreezeBalance(0);
            partnerAccount.setBalance(0);
            partnerAccount.setOwedBalance(0);
            partnerAccountRepo.save(partnerAccount);
        }
        UniResp<PartnerAccountInfoResp> infoResp = new UniResp<>();
        infoResp.setData(accountConvert.accountInfoConvert(partnerAccount));
        infoResp.setStatus(200);
        return infoResp;
    }


//    @Override
//    public UniResp<String> resetPayPassword(
//            String brandAppId,
//            String partnerId,
//            String oldPassword,
//            String newPassword) {
//
//        UniResp<String> uniResp = new UniResp<>();
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
//                Expressions.allOf(
//                        QPartnerAccount.partnerAccount.deleted.in(false),
//                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
//                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
//                )
//        );
//
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(oldPassword);
//        Assert.isTrue(isNum.matches(), "密码必须为数字");
//        Assert.isTrue(oldPassword.length() == 6, "密码必须为6位");
//
//        String oldPwd = passwordEncoder.encode(oldPassword);
//        Assert.isTrue(passwordEncoder.matches(newPassword, oldPwd), "两次密码必须相同");
//
//        partnerAccount.setPayPassword(oldPwd);
//        partnerAccountRepo.save(partnerAccount);
//
//        uniResp.setStatus(200);
//        uniResp.setData("密码重设完成");
//        return uniResp;
//    }

//    @Override
//    public UniResp<String> judgePayPassword(
//            String brandAppId,
//            String partnerId) {
//
//        UniResp<String> uniResp = new UniResp<>();
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
//                Expressions.allOf(
//                        QPartnerAccount.partnerAccount.deleted.in(false),
//                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
//                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
//                )
//        );
//        String payPassword = partnerAccount.getPayPassword();
//        Integer stats = (payPassword == null ? 10032 : 10031);
//        String data = (payPassword == null ? "未设置" : "修改密码");
//        uniResp.setStatus(stats);
//        uniResp.setData(data);
//
//        return uniResp;
//    }

//    @Override
//    public UniResp<String> checkPayPassword(String brandAppId, String partnerId, String id, String password) {
//        UniResp<String> uniResp = new UniResp<>();
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
//                Expressions.allOf(
//                        QPartnerAccount.partnerAccount.deleted.in(false),
//                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
//                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
//                )
//        );
//        Order order = orderRepo.findOneByPartnerStaffAndId(partnerStaff, id);
//        String pwd = partnerAccount.getPayPassword();
//        if (passwordEncoder.matches(password, pwd)) {
//            uniResp.setStatus(200);
//            uniResp.setData("密码正确");
//            order.setStatus(OrderStatusEnum.UNCONFIRMED);
//            orderRepo.save(order);
//        } else if (!passwordEncoder.matches(password, pwd)) {
//            uniResp.setStatus(10033);
//            uniResp.setData("密码错误");
//        }
//        return uniResp;
//    }

    @Override
    public UniResp<String> sendSms(String brandAppId,String partnerId, String id) {
        partnerService.check();
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        Partner partner = partnerRepo.findOneByUserIdAndBrandAppId(partnerStaff.getUserId(),brandAppId);
        Order order = orderRepo.findOne(id);
        Partner partner = order.getPartnerStaff().getPartner();
        String phone = partner.getPhone();
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String code = sb.toString();

        long count = smsRepo.countByPhone(phone);
        if(count > 5){
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setData("短信发送过于频繁");
            uniResp.setStatus(10086);
            return uniResp;
        }
        Sms sms = new Sms();
        sms.setPhone(phone);
        HashMap hashMap = new HashMap();
        Partner partnerCom = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId);
        String shopName = partnerCom.getShopName()==null?"钱皇股份":partnerCom.getShopName();
        hashMap.put("code", code);
        hashMap.put("brandCom", shopName);
//        sendSms(Sms sms, String mobile, String template, String params, String extent,String freeSign,
        Sms resp = aliSmsService.sendSms(sms, phone, "SMS_96600028", aliSmsService.convertMap2Json(hashMap), null, "生意参谋", false);
        if (resp.getaLiSendStatus().getSuccess()) {
            sms.setContent(code);
            sms.setValid(true);
            smsRepo.save(sms);
        }else {
            sms.setContent(code);
            sms.setValid(false);
            smsRepo.save(sms);
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(phone);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> checkSms(String brandAppId, String partnerId,String id, String code) {
        partnerService.check();
        Order order = orderRepo.findOne(id);
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        Partner partner = partnerRepo.findOneByUserIdAndBrandAppId(partnerStaff.getUserId(),brandAppId);
        Partner partner = partnerRepo.findOne(order.getBuyerPartnerId());
        String phone = partner.getPhone();
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(order.getBuyerPartnerId())
                )
        );
        if (org.apache.commons.lang3.StringUtils.isBlank(code)) {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setData("手机号不能为空");
            uniResp.setStatus(10082);
            return uniResp;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(code)) {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setData("验证码不能为空");
            uniResp.setStatus(10081);
            return uniResp;
        }
        Sms sms = smsRepo.findOneByPhoneAndContentAndValid(phone, code, true);
        if (sms == null) {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setData("验证码错误");
            uniResp.setStatus(10080);
            return uniResp;
        }
        sms.setValid(false);
        smsRepo.save(sms);
        order.setStatus(OrderStatusEnum.UNCONFIRMED);
        orderRepo.save(order);
        calOrderPriceService.calParnterAccount(order, partnerAccount);
        if (order.getNoCashBalancePrice()!=null&&order.getNoCashBalancePrice()!=0) {
            paLogService.pALogConvert(partnerAccount, order, "NOCASHBALANCE");
        }
        if (order.getBalancePrice()!=null&&order.getBalancePrice()!=0) {
            paLogService.pALogConvert(partnerAccount, order, "BALANCE");
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<List<PartnerAccountLogResp>> partnerAccountLog(
            String brandAppId,
            String partnerId,
            PartnerAccountLogReq req) {
        partnerService.check();
        List<PartnerAccountLogResp> plist = new ArrayList<>();

        for (String key : req.getIds()) {
            PartnerAccountLogResp paLogResp = new PartnerAccountLogResp();
            Order order = orderRepo.findOne(key);
            if (order != null) {
                paLogResp.setSeq(order.getSeq());
                paLogResp.setId(key);
                plist.add(paLogResp);
            }
            Refund refund = refundRepo.findOne(key);
            if (refund != null) {
                paLogResp.setSeq(refund.getSeq());
                paLogResp.setId(key);
                plist.add(paLogResp);
            }
            WithdrawCash withdrawCash = withdrawCashRepo.findOne(key);
            if (withdrawCash != null) {
                paLogResp.setSeq(withdrawCash.getSeq());
                paLogResp.setId(key);
                plist.add(paLogResp);
            }
        }
        UniResp<List<PartnerAccountLogResp>> uniResp = new UniResp<>();
        uniResp.setData(plist);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPageResp<PaLogResp>> getIds(
            String brandAppId,
            String partnerId,
            PartnerAccountLogReq req) {
        partnerService.check();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        Boolean isAdmin = false;
        if (partnerStaff == null) {
            String curUserId = secService.curUserId();
            if (!StringUtils.isEmpty(curUserId)) {
                Staff staff =
                        staffRepo.
                                findOneByUserIdAndDisabledAndDeletedAndBrandAppId(curUserId, false, false, brandAppId);
                Assert.notNull(staff, "请先登录");
                isAdmin = true;

            }
        }

        PageRequest pageRequest = new PageRequest(req.getPage(), req.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<PartnerAccountLog> page = partnerAccountLogRepo.findAll(
                Expressions.allOf(
                        QPartnerAccountLog.partnerAccountLog.deleted.in(false),
                        isAdmin ?
                                (req.getParnterId() != null ? QPartnerAccountLog.partnerAccountLog.partnerId.eq(req.getParnterId()) : null) :
                                QPartnerAccountLog.partnerAccountLog.partnerId.eq(partnerStaff.getPartner().getId()),
                        QPartnerAccountLog.partnerAccountLog.brandAppId.eq(brandAppId),
                        !StringUtils.isEmpty(req.getLogType()) ?
                                QPartnerAccountLog.partnerAccountLog.type.eq(AccountChangeTypeEnum.valueOf(req.getLogType())) : null
                ), pageRequest
        );
        UniResp<UniPageResp<PaLogResp>> uniResp = new UniResp<>();

        Page<PaLogResp> uniPage = page.map(partnerAccountLog -> {
            PaLogResp paLogResp = new PaLogResp();
            accountConvert.paLogRespConvert(paLogResp, partnerAccountLog);
            return paLogResp;
        });
        uniResp.setData(conversionService.convert(uniPage, UniPageResp.class));
        uniResp.getData().setContent(uniPage.getContent());
        uniResp.setStatus(200);
        return uniResp;
    }
}
