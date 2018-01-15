package net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.PartnerAccountApi;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PaLogResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogReq;
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountLogResp;
import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.*;
import net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount.convert.PartnerAccountConvert;
import net.kingsilk.qh.agency.service.AliSmsService;
import net.kingsilk.qh.agency.service.CalOrderPriceService;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import net.kingsilk.qh.agency.service.SecService;
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
import java.util.List;


@Component(value = "partnerAccountResource")
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
    private SecService secService;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Override
    public UniResp<PartnerAccountInfoResp> info(
            String brandAppId) {

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


    @Override
    public UniResp<List<PartnerAccountLogResp>> partnerAccountLog(
            String brandAppId,
            PartnerAccountLogReq req) {
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
            PartnerAccountLogReq req) {

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
