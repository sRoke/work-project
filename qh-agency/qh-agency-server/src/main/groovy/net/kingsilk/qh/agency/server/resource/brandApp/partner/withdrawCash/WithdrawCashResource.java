package net.kingsilk.qh.agency.server.resource.brandApp.partner.withdrawCash;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.withdrawCash.WithdrawCashApi;
import net.kingsilk.qh.agency.core.WithdrawCashStatusEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerAccountRepo;
import net.kingsilk.qh.agency.repo.WithdrawCashRepo;
import net.kingsilk.qh.agency.service.CommonService;
import net.kingsilk.qh.agency.service.PartnerService;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import net.kingsilk.qh.agency.service.SysConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class WithdrawCashResource implements WithdrawCashApi {

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Autowired
    private PartnerStaffService partnerStaffService;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SysConfService sysConfService;

    @Autowired
    private PartnerService partnerService;

    @Override
    public UniResp<String> apply(
            String brandAppId,
            String partnerId,
            Integer applyAmount
//            String password
    ) {
        partnerService.check();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        PartnerAccount account = partnerAccountRepo.findByPartner(partnerStaff.getPartner());
        UniResp resp = new UniResp();
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                Expressions.allOf(
                        QPartnerAccount.partnerAccount.deleted.in(false),
                        QPartnerAccount.partnerAccount.brandAppId.eq(brandAppId),
                        QPartnerAccount.partnerAccount.partner.id.eq(partnerStaff.getPartner().getId())
                )
        );
        SysConf sysConf = sysConfService.getSysConf("withdrawalMinAmount",brandAppId);
//        String pwd = partnerAccount.getPayPassword();
//        if (!passwordEncoder.matches(password, pwd)) {
//            resp.setStatus(10033);
//            resp.setData("密码错误！");
//            return resp;
//        }
        if (account.getBalance() < sysConf.getValueInt()) {
            resp.setData("提现额度不能低于最低额度！");
            resp.setStatus(10041);
            return resp;
        }
        if (account.getBalance() > partnerAccount.getBalance()) {
            resp.setData("请输入正确提现金额！");
            resp.setStatus(10042);
            return resp;
        }
        Integer available = account.getBalance() - applyAmount;
        WithdrawCash withdrawCash = new WithdrawCash();
        withdrawCash.setStatus(WithdrawCashStatusEnum.CASHING);
        withdrawCash.setAvailable(available);
        withdrawCash.setBrandAppId(brandAppId);
        withdrawCash.setPartnerId(partnerStaff.getPartner().getId());
        withdrawCash.setSeq(commonService.getDateString());
        withdrawCash.setWithdrawAmount(applyAmount);
        withdrawCash.setPartnerStaffId(partnerStaff.getId());
        withdrawCashRepo.save(withdrawCash);

        account.setBalance(available);
        account.setFreezeBalance(account.getFreezeBalance() + applyAmount);
        partnerAccountRepo.save(account);

        resp.setData("提交成功！");
        resp.setStatus(200);
        return resp;
    }


}
