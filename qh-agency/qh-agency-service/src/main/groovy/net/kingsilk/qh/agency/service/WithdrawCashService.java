package net.kingsilk.qh.agency.service;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.api.ErrStatus;
import net.kingsilk.qh.agency.api.ErrStatusException;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.core.PayTypeEnum;
import net.kingsilk.qh.agency.core.PaymentStatusEnum;
import net.kingsilk.qh.agency.core.WithdrawCashStatusEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static java.lang.System.out;

/**
 *
 */
@Service
public class WithdrawCashService {


    @Autowired
    private SecService secService;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private QhAgencyProperties props;

    @Autowired
    private RestTemplate oClientPayWapRestTemplate;

    @Autowired
    private PartnerStaffRepo partnerStaffRepo;

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PALogService paLogService;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    public UniResp<Map> withdrawCash(WithdrawCash withdrawCash ,String brandAppId) {

        String userId = secService.curUserId();
        Staff staff = staffRepo.findOne(
                allOf(
                        QStaff.staff.deleted.ne(true),
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.userId.eq(userId)
                )
        );
        Payment payment = new Payment();
        payment.setAdjustAmount(withdrawCash.getWithdrawAmount());
        payment.setApplyTime(new Date());
        PartnerStaff partnerStaff = partnerStaffRepo.findOne(withdrawCash.getPartnerStaffId());
        payment.setUserId(partnerStaff.getUserId());
        payment.setTotalFee(withdrawCash.getWithdrawAmount());
        payment.setOperator(staff);
        payment.setSeq(commonService.getDateString());
        payment.setWithdrawCashId(withdrawCash.getId());
        payment.setPayType(PayTypeEnum.WEIXIN);
        payment.setStatus(PaymentStatusEnum.UNPAYED);
        payment.setReason("提现 ");
        payment.setDateCreated(new Date());
        paymentRepo.save(payment);

        return withdrawCashHandle(payment,brandAppId);

    }

    public UniResp<Map> withdrawCashHandle(Payment payment,String brandAppId) {

        Assert.notNull(payment, "订单错误");
        Assert.isTrue(payment.getStatus() == PaymentStatusEnum.UNPAYED, "订单状态错误");
        String appId =  userService.getAppId(brandAppId);
        Map oauthUser = userService.getOauthUserInfoByUserId(payment.getUserId());

        final String[] openId = new String[1];
        Object users = oauthUser.get("wxUsers");
        if (oauthUser != null &&
                users != null) {
            ArrayList<LinkedHashMap<String,String>> wxUsers = (ArrayList<LinkedHashMap<String,String>>) users;
            wxUsers.forEach( wxUser->
                    {
                        if(appId.equals(wxUser.get("appId"))){
                            openId[0] =  wxUser.get("openId");
                        }
                    }
            );
        }
        if(StringUtils.isBlank(openId[0])){
            throw new ErrStatusException(ErrStatus.PARTNER_401,"openId为空");
        }
        Map<String, String> map = new HashMap<>();
        map.put("amount", String.valueOf(payment.getTotalFee()));
        map.put("outTradeNo", payment.getId());
        map.put("openId", openId[0]);
        map.put("transferChannel", "WX");

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.ALL);
        reqHeaders.setAccept(type);
        HttpEntity<Map> reqEntity = new HttpEntity<>(map, reqHeaders);
        String aaa = props.getQhPay().getWap().getApi().getWithdrawCash() + brandAppId + "/transfer";
        UniResp<Map> payMap = oClientPayWapRestTemplate.postForObject(aaa, reqEntity, UniResp.class);
        Map payMapData = payMap.getData();
        out.println("--------------------支付系统返回值\n" + payMap);
        if ("SUCCESS".equals(payMapData.get("return_code"))
                && "SUCCESS".equals(payMapData.get("result_code"))) {
            payment.setStatus(PaymentStatusEnum.PAYED);
            paymentRepo.save(payment);
            WithdrawCash withdrawCash = withdrawCashRepo.findOne(payment.getWithdrawCashId());
            withdrawCash.setStatus(WithdrawCashStatusEnum.SUCCESS);
            withdrawCashRepo.save(withdrawCash);
            //TODO 明细表记录提现成功
            paLogService.withDrawLog(withdrawCash);
            Partner partner = partnerRepo.findOne(withdrawCash.getPartnerId());
            PartnerAccount partnerAccount = partnerAccountRepo.findByPartner(partner);
            partnerAccount.setFreezeBalance(partnerAccount.getFreezeBalance() - withdrawCash.getWithdrawAmount());
            partnerAccountRepo.save(partnerAccount);
            return payMap;
        } else {
            WithdrawCash withdrawCash = withdrawCashRepo.findOne(payment.getWithdrawCashId());
            withdrawCash.setStatus(WithdrawCashStatusEnum.CASHING);
            withdrawCashRepo.save(withdrawCash);
            if ("AMOUNT_LIMIT".equals(payMapData.get("err_code"))) {
                payMapData.put("err_code_des", "提现金额超出范围，每次最小1.00元，每日累积不超过20000.00元");
                payMap.setData(payMapData);
            }
            return payMap;
        }
    }

}
