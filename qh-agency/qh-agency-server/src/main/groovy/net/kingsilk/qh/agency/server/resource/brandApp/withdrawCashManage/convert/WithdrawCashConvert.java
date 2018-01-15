package net.kingsilk.qh.agency.server.resource.brandApp.withdrawCashManage.convert;

import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto.WithdrawCashPageResp;
import net.kingsilk.qh.agency.domain.WithdrawCash;
import net.kingsilk.qh.agency.repo.PartnerStaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lit on 17/9/1.
 */
@Component
public class WithdrawCashConvert {

    @Autowired
    private PartnerStaffRepo partnerStaffRep;

    public WithdrawCashPageResp withdrawCashPageRespConvert(WithdrawCash withdrawCash) {
        WithdrawCashPageResp resp = new WithdrawCashPageResp();
        resp.setApplyTime(withdrawCash.getDateCreated());
        resp.setAvailable(withdrawCash.getAvailable());
        resp.setId(withdrawCash.getId());
        resp.setPartnerId(withdrawCash.getPartnerId());
        resp.setPartnerStaffName(partnerStaffRep.findOne(withdrawCash.getPartnerStaffId()).getPartner().getRealName());
        resp.setPartnerStaffPhone(partnerStaffRep.findOne(withdrawCash.getPartnerStaffId()).getPartner().getPhone());
        resp.setWithdrawAmount(withdrawCash.getWithdrawAmount());
        resp.setStatus(withdrawCash.getStatus().getCode());
        resp.setStatusDesp(withdrawCash.getStatus().getDesp());
        resp.setSeq(withdrawCash.getSeq());
        return resp;
    }
}
