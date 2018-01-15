package net.kingsilk.qh.agency.server.resource.brandApp.partnerAccount.convert

import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PaLogResp
import net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto.PartnerAccountInfoResp
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.server.resource.brandApp.refund.convert.RefundConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

import java.text.SimpleDateFormat

@Component
class PartnerAccountConvert {

    @Autowired
    private RefundConvert refundConvert

    @Autowired
    private OrderRepo orderRepo

    @Autowired
    private RefundRepo refundRepo

    @Autowired
    private RefundSkuRepo refundSkuRepo

    @Autowired
    private PartnerRepo partnerRepo

    @Autowired
    private PartnerAccountRepo partnerAccountRepo

    PartnerAccountInfoResp accountInfoConvert(PartnerAccount partnerAccount) {

        PartnerAccountInfoResp accountInfoResp = new PartnerAccountInfoResp()

        accountInfoResp.setBrandAppId(partnerAccount.getBrandAppId())
        accountInfoResp.setBalance(partnerAccount.getBalance())
        accountInfoResp.setFreezeBalance(partnerAccount.getFreezeBalance())
        accountInfoResp.setNoCashBalance(partnerAccount.getNoCashBalance())
        accountInfoResp.setOwedBalance(partnerAccount.getOwedBalance());
        accountInfoResp.setPartnerId(partnerAccount.getPartner().getId())

        accountInfoResp.setTotalBalance(partnerAccount.getNoCashBalance() +
                partnerAccount.getBalance() -
                partnerAccount.getOwedBalance())
        return accountInfoResp
    }

    PaLogResp paLogRespConvert(
            PaLogResp paLogResp,
            PartnerAccountLog pALog) {
        paLogResp.partnerAccountLogId = pALog.id
        if (!StringUtils.isEmpty(pALog.orderId)) {
            paLogResp.id = pALog.orderId
        }
        if (!StringUtils.isEmpty(pALog.refundId)) {
            paLogResp.id = pALog.refundId
        }
        if (!StringUtils.isEmpty(pALog.withdrawId)) {
            paLogResp.id = pALog.withdrawId
        }
        if (!StringUtils.isEmpty(pALog.retailOrderId)) {
            paLogResp.id = pALog.retailOrderId
        }
        Partner partner = partnerRepo.findOne(
                QPartner.partner.id.eq(pALog.partnerId)
        )
        if (pALog.targetAccountId != null) {
            PartnerAccount targetPat = partnerAccountRepo.findOne(
                    QPartnerAccount.partnerAccount.id.eq(pALog.targetAccountId)
            )

            Partner targetPartner = partnerRepo.findOne(
                    QPartner.partner.id.eq(targetPat.partner.id)
            )
            paLogResp.setTargetAccount(targetPartner.realName + "  " + partner.phone)
        }
        paLogResp.type = pALog.type ? pALog.type.code : null
        paLogResp.typeDetil = pALog.type ? pALog.type.desp : null
        SimpleDateFormat formatAll = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss ")
        SimpleDateFormat formatDay = new SimpleDateFormat("YYYY-MM-dd")
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss ")
        paLogResp.date = formatAll.format(pALog.dateCreated)
        paLogResp.datestamp = formatDay.format(pALog.dateCreated)
        paLogResp.dateminstamp = formatHour.format(pALog.dateCreated)
        paLogResp.freezeBalance = pALog.freezeBalance
        paLogResp.balance = pALog.balance
        paLogResp.noCashBalance = pALog.noCashBalance
        paLogResp.owedBalance = pALog.owedBalance
        paLogResp.srcFreezeBalance = pALog.srcFreezeBalance
        paLogResp.srcBalance = pALog.srcBalance
        paLogResp.srcNoCashBalance = pALog.srcNoCashBalance
        paLogResp.srcOwedBalance = pALog.srcOwedBalance
        paLogResp.qhPayId = pALog.qhPayId
        paLogResp.memo = pALog.memo
        paLogResp.changeAmount = pALog.changeAmount
        paLogResp.status = pALog.status ? pALog.status.code : null
        paLogResp.setPartnerAccount(partner.realName + "  " + partner.phone)
        paLogResp.moneyChangeDetil = pALog.moneyChangeEnum ? pALog.moneyChangeEnum.desp : null
        paLogResp.moneyChangeStatus = pALog.moneyChangeEnum ? pALog.moneyChangeEnum.code : null
        return paLogResp

    }


}
