package net.kingsilk.qh.shop.server.resource.brandApp.shop.withdraw;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.withdraw.WithDrawApi;
import net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto.AliRep;
import net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto.WithdrawInfo;
import net.kingsilk.qh.shop.core.*;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.service.service.AliSmsService;
import net.kingsilk.qh.shop.service.service.CommonService;
import net.kingsilk.qh.shop.service.service.PayService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WithdrawResource implements WithDrawApi {

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private PayService payService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WithdrawRepo withdrawRepo;

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private ShopAccountLogRepo shopAccountLogRepo;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> apply(String brandAppId, String shopId, Integer applyAmount) {

        ShopAccount shopAccount = shopAccountRepo.findOne(
                Expressions.allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId),
                        QShopAccount.shopAccount.deleted.ne(true)
                )
        );

        UniResp<String> uniResp = new UniResp<>();

        //统计今日已经提现总额度
        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        ArrayList<Withdraw> withdraws = Lists.newArrayList(withdrawRepo.findAll(
                Expressions.allOf(
                        QWithdraw.withdraw.shopId.eq(shopId),
                        QWithdraw.withdraw.brandAppId.eq(brandAppId),
                        QWithdraw.withdraw.deleted.ne(true),
                        QWithdraw.withdraw.status.eq(WithdrawStatusEnum.FINISH),
                        QWithdraw.withdraw.applyTime.after(todayStartCalendar.getTime())
                )
        ));
        int sum = withdraws.stream().mapToInt(Withdraw::getRefundFee).sum();

        Withdraw withdraw = new Withdraw();
        withdraw.setBrandAppId(brandAppId);
        withdraw.setShopId(shopId);
        withdraw.setSeq(commonService.getDateString());
        withdraw.setApplyTime(new Date());
        withdraw.setInitType(WithdrawInitTypeEnum.INIT_BY_USER);
        withdraw.setRefundFee(applyAmount);
        withdraw.setStatus(WithdrawStatusEnum.CASHING);
        if (applyAmount < 100) {
            uniResp.setData("单笔提现金额至少1元");
            uniResp.setStatus(10011);
            //TODO
            shopAccount.setFreezeBalance(0);
            shopAccountRepo.save(shopAccount);
            return uniResp;
        }
        //TODO
        shopAccount.setFreezeBalance(Optional.ofNullable(shopAccount.getFreezeBalance()).orElse(0) + applyAmount);
        shopAccountRepo.save(shopAccount);
        if (shopAccount.getFreezeBalance() > applyAmount || applyAmount > shopAccount.getBalance()) {
            uniResp.setData("您的余额不够");
            uniResp.setStatus(10011);
            return uniResp;
        }
        ShopAccount.AliAccount aliAccount = Optional.ofNullable(shopAccount.getAliAccount()).orElse(new ShopAccount.AliAccount());
        if (aliAccount.getDefault() == null || !aliAccount.getDefault()) {
            //微信提现的情况
            if (sum > 2000000) {
                uniResp.setData("您今天提现已超20000额度，请明天再来提现");
                uniResp.setStatus(10011);
                //TODO
                shopAccount.setFreezeBalance(0);
                shopAccountRepo.save(shopAccount);
                return uniResp;
            } else if (
                    withdraws.stream()
                            .filter(it ->
                                    PayTypeEnum.WX.equals(it.getPayType())
                            )
                            .collect(Collectors.toList()).size() >= 10
                    ) {
                uniResp.setData("微信提现不能超过10次");
                uniResp.setStatus(10011);
                //TODO
                shopAccount.setFreezeBalance(0);
                shopAccountRepo.save(shopAccount);
                return uniResp;
            } else {
                withdraw.setPayType(PayTypeEnum.WX);
            }
        } else {
            //支付宝提现的情况
            if (sum > 1000000000) {
                uniResp.setData("您今天提现已超100万额度，请明天再来提现");
                uniResp.setStatus(10011);
                //TODO
                shopAccount.setFreezeBalance(0);
                shopAccountRepo.save(shopAccount);
                return uniResp;
            } else if (applyAmount > 5000000) {
                uniResp.setData("单笔提现最多5万元，请稍后再来提现");
                uniResp.setStatus(10011);
                //TODO
                shopAccount.setFreezeBalance(0);
                shopAccountRepo.save(shopAccount);
                return uniResp;
            } else {
                withdraw.setPayType(PayTypeEnum.ALIPAY);
            }
        }
        Map payMapData = payService.withdrawCash(withdraw, shopAccount);

        if (WithdrawStatusEnum.FINISH.equals(withdraw.getStatus())) {

            shopAccount.setBalance(shopAccount.getBalance() - withdraw.getRefundFee());
            shopAccount.setTotalWithdraw(shopAccount.getTotalWithdraw() + withdraw.getRefundFee());
            ShopAccountLog accountLog = conversionService.convert(shopAccount, ShopAccountLog.class);
            if (PayTypeEnum.WX.equals(withdraw.getPayType())) {
                accountLog.setType(AccountChangeTypeEnum.WITHDRAW_WX);
            } else {
                accountLog.setType(AccountChangeTypeEnum.WITHDRAW_ALIPAY);
            }
            accountLog.setChangeAmount(withdraw.getRefundFee());
            accountLog.setMoneyChangeEnum(MoneyChangeEnum.BALANCE);
            shopAccountLogRepo.save(accountLog);
            //TODO
            shopAccount.setFreezeBalance(0);
            shopAccountRepo.save(shopAccount);
            uniResp.setStatus(200);
            uniResp.setData("success");
            return uniResp;
        }
//        else if ("NOTENOUGH".equals(payMapData.get("err_code"))) {
//            uniResp.setData("前方提现人数太多，请稍后再试");
//            uniResp.setStatus(10012);
//        }
        else {
            //TODO
            shopAccount.setFreezeBalance(0);
            shopAccountRepo.save(shopAccount);
            uniResp.setData("前方提现人数太多，请稍后再试");
            uniResp.setStatus(10013);
            return uniResp;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<WithdrawInfo> info(String brandAppId, String shopId) {
        ShopAccount shopAccount = shopAccountRepo.findOne(
                Expressions.allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId),
                        QShopAccount.shopAccount.deleted.ne(true)
                )
        );

        //TODO 明细
        ArrayList<Withdraw> withdraws = Lists.newArrayList(withdrawRepo.findAll(
                Expressions.allOf(
                        QWithdraw.withdraw.shopId.eq(shopId),
                        QWithdraw.withdraw.brandAppId.eq(brandAppId),
                        QWithdraw.withdraw.deleted.ne(true),
                        QWithdraw.withdraw.status.eq(WithdrawStatusEnum.FINISH),
                        QWithdraw.withdraw.initType.eq(WithdrawInitTypeEnum.INIT_BY_USER)
                )
        ));
        int sum = withdraws.stream().mapToInt(Withdraw::getRefundFee).sum();

        WithdrawInfo withdrawInfo = new WithdrawInfo();
        withdrawInfo.setAccount(shopAccount.getBalance());
        withdrawInfo.setSum(sum);
        UniResp<WithdrawInfo> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(withdrawInfo);

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> setAli(String brandAppId, String shopId, AliRep aliRep) {

        String account = aliRep.getAccount();
        String realName = aliRep.getRealName();
//        SendReq sendReq = new SendReq();

        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.ne(true),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );

        String phone = shop.getPhone();
        long count = smsRepo.countByPhone(phone);
        if (count > 25) {
            throw new ErrStatusException(ErrStatus.NOTONLY, "短信发送过于频繁");
        }

        Sms sms = new Sms();
        sms.setPhone(phone);
        HashMap hashMap = new HashMap();
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String code = sb.toString();
        hashMap.put("code", code);
        sms = aliSmsService.sendSms(sms, phone, "SMS_19640001", aliSmsService.convertMap2Json(hashMap), null, "小皇叔");
        UniResp<String> uniResp = new UniResp<>();
        if (sms.getaLiSendStatus().getSuccess()) {
            sms.setContent(code);
            sms.setValid(true);
            smsRepo.save(sms);
            ShopAccount temAcount = new ShopAccount();
            temAcount.setBalance(0);
            temAcount.setBrandAppId(brandAppId);
            temAcount.setShopId(shopId);
            temAcount.setFreezeBalance(0);
            temAcount.setNoCashBalance(0);
            ShopAccount.AliAccount aliAccount = new ShopAccount.AliAccount();
            aliAccount.setDefault(false);
            temAcount.setAliAccount(aliAccount);
            temAcount.setTotalBalance(0);
            temAcount.setTotalWithdraw(0);
            ShopAccount shopAccount = Optional.ofNullable(shopAccountRepo.findOne(
                    Expressions.allOf(
                            QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                            QShopAccount.shopAccount.shopId.eq(shopId),
                            QShopAccount.shopAccount.deleted.ne(true)
                    )
            )).orElse(temAcount);
            if (shopAccount.getAliAccount() == null) {
                shopAccount.setAliAccount(aliAccount);
            }
            shopAccount.getAliAccount().setAccount(account);
            shopAccount.getAliAccount().setRealName(realName);
            shopAccount.getAliAccount().setDefault(false);
            shopAccountRepo.save(shopAccount);
            StringBuilder sbb = new StringBuilder(phone.substring(0, 3));
            sbb.append("****").append(phone.substring(7));
            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData(sbb.toString());
        } else {
            sms.setContent(code);
            sms.setValid(false);
            smsRepo.save(sms);
            uniResp.setStatus(10086);
            uniResp.setData("短信发送失败");
        }
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> checkAli(String brandAppId, String shopId, String code) {
        Shop shop = shopRepo.findOne(
                Expressions.allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.ne(true),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (!StringUtils.hasText(code)) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "输入的短信验证码为空");
        }
        Sms sms = smsRepo.findOne(
                Expressions.allOf(
                        QSms.sms.phone.eq(shop.getPhone()),
                        QSms.sms.content.eq(code),
                        QSms.sms.deleted.ne(true),
                        QSms.sms.valid.eq(true)
                )
        );
        if (sms == null) {

            uniResp.setData("验证码错误");
            uniResp.setStatus(10080);
            return uniResp;
        } else {

            sms.setValid(false);
            smsRepo.save(sms);

            ShopAccount shopAccount = shopAccountRepo.findOne(
                    Expressions.allOf(
                            QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                            QShopAccount.shopAccount.shopId.eq(shopId),
                            QShopAccount.shopAccount.deleted.ne(true)
                    )
            );
            shopAccount.getAliAccount().setDefault(true);
            shopAccountRepo.save(shopAccount);

            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData("验证码正确");
        }
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<AliRep> aliInfo(String brandAppId, String shopId) {

        ShopAccount shopAccount = shopAccountRepo.findOne(
                Expressions.allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId),
                        QShopAccount.shopAccount.deleted.ne(true)
                )
        );
        if (shopAccount.getAliAccount() == null) {
            ShopAccount.AliAccount aliAccount = new ShopAccount.AliAccount();
            aliAccount.setDefault(false);
            shopAccount.setAliAccount(aliAccount);
        }
        AliRep aliRep = new AliRep();
        aliRep.setAccount(shopAccount.getAliAccount().getAccount());
        aliRep.setRealName(shopAccount.getAliAccount().getRealName());
        aliRep.setDefault(shopAccount.getAliAccount().getDefault());
        UniResp<AliRep> uniResp = new UniResp<>();
        uniResp.setData(aliRep);
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> bandingAli(String brandAppId, String shopId, Boolean binding) {

        ShopAccount shopAccount = shopAccountRepo.findOne(
                Expressions.allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId),
                        QShopAccount.shopAccount.deleted.ne(true)
                )
        );
        shopAccount.getAliAccount().setDefault(binding);
        shopAccountRepo.save(shopAccount);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("绑定成功");
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> deletAli(String brandAppId, String shopId) {

        ShopAccount shopAccount = shopAccountRepo.findOne(
                Expressions.allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId),
                        QShopAccount.shopAccount.deleted.ne(true)
                )
        );
        shopAccount.getAliAccount().setRealName(null);
        shopAccount.getAliAccount().setDefault(false);
        shopAccount.getAliAccount().setAccount(null);
        shopAccountRepo.save(shopAccount);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("绑定成功");
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }
}
