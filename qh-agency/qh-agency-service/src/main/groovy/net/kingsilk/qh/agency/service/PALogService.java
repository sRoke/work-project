package net.kingsilk.qh.agency.service;


import net.kingsilk.qh.agency.core.*;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerAccountLogRepo;
import net.kingsilk.qh.agency.repo.PartnerAccountRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.QhPayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

@Service
public class PALogService {


    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    public String pALogConvert(PartnerAccount account, Order order, String type) {

        QhPay qhPay = qhPayRepo.findByOrder(order);

        Boolean isSell = account.getPartner().getId().equals(order.getSellerPartnerId());

        AccountChangeTypeEnum paymentType = isSell
                ? (AccountChangeTypeEnum.SELL)
                : (AccountChangeTypeEnum.ORDER);

        String targetId = isSell ? order.getBuyerPartnerId() : order.getSellerPartnerId();

        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(targetId)
        );
        Integer changeAmout = 0;
        Integer balance = account.getBalance();
        Integer noCashBalance = account.getNoCashBalance();
        Integer owedBalance = account.getOwedBalance();

        // TODO 不同情况下账户明细的变更记录
        if (isSell && type.equals(MoneyChangeEnum.BALANCE.getCode())) {
            balance = balance + order.getBalancePrice()+order.getPaymentPrice();
            changeAmout = order.getBalancePrice()+order.getPaymentPrice();
        } else if (isSell && type.equals(MoneyChangeEnum.OWEDBALANCE.getCode())) {
            owedBalance = owedBalance - order.getNoCashBalancePrice();
            changeAmout = order.getNoCashBalancePrice();
        } else if (!isSell && type.equals(MoneyChangeEnum.BALANCE.getCode())) {
            balance = balance - order.getBalancePrice();
            changeAmout = order.getBalancePrice();
        } else if (!isSell && type.equals(MoneyChangeEnum.NOCASHBALANCE.getCode())) {
            noCashBalance = noCashBalance - order.getNoCashBalancePrice();
            changeAmout = order.getNoCashBalancePrice();
        } else if (isSell && type.equals("confirmReceive")) {
            balance = balance + order.getPaymentPrice();
            changeAmout = order.getPaymentPrice();
            type = "BALANCE";
        } else if (type.equals(MoneyChangeEnum.ALIPAY.getCode())) {
            changeAmout = order.getPaymentPrice();
        } else if (type.equals(MoneyChangeEnum.WX.getCode())) {
            changeAmout = order.getPaymentPrice();
        }


        PartnerAccountLog partnerAccountLog = new PartnerAccountLog(
                account.getId(),
                account.getPartner().getId(),
                account.getBrandAppId(),
                null,
                paymentType,
                changeAmout,
                account.getFreezeBalance(),
                balance,
                noCashBalance,
                owedBalance,
                account.getFreezeBalance(),
                account.getBalance(),
                account.getNoCashBalance(),
                account.getOwedBalance(),
                isSell ? order.getSellerMemo() : order.getBuyerMemo(),
                partnerAccount.getId(),
                qhPay != null ? qhPay.getId() : null,
                null,
                order.getId(),
                null,
                null,
                MoneyChangeEnum.valueOf(type)
        );

        PartnerAccountLog palog =
                partnerAccountLogRepo.save(partnerAccountLog);
        if (palog.getId() != null) {
            return "success";
        }
        return "fail";
    }


    public String refundPartnerAccountLog(PartnerAccount account, Refund refund, String type) {

        Boolean isBuy = account.getPartner().getId().equals(refund.getBuyerPartner().getId());

        String targetId = isBuy ? refund.getSellerPartner().getId() : refund.getBuyerPartner().getId();

        AccountChangeTypeEnum refundType = refund.getRefundType() == RefundTypeEnum.MONEY_ONLY
                ? AccountChangeTypeEnum.ORDER_CANCLE
                : AccountChangeTypeEnum.REFUND;
        refundType = isBuy
                ? refundType
                : (AccountChangeTypeEnum.SELL_REFUND);
        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(targetId)
        );
        Integer changeAmout = 0;
        Integer balance = account.getBalance();
        Integer noCashBalance = account.getNoCashBalance();
        Integer owedBalance = account.getOwedBalance();
        Order order = refund.getOrder();
//        if (!isBuy && order != null && (MoneyChangeEnum.BALANCE.getCode()).equals(type)) {
//            balance = balance + refund.getOrder().getBalancePrice();
//            changeAmout = order.getBalancePrice();

//        } else
        if (!isBuy && order == null && (MoneyChangeEnum.OWEDBALANCE.getCode()).equals(type)) {
        owedBalance = owedBalance + refund.getRefundAmount();
        changeAmout = refund.getRefundAmount();

//        } else if (!isBuy && order != null && type.equals(MoneyChangeEnum.OWEDBALANCE.getCode())) {
//            owedBalance = owedBalance - order.getNoCashBalancePrice();
//            changeAmout = order.getNoCashBalancePrice();

        } else if (isBuy && order != null && type.equals(MoneyChangeEnum.BALANCE.getCode())) {
            balance = balance - order.getBalancePrice();
            changeAmout = order.getBalancePrice();

        } else if (isBuy && order == null && type.equals(MoneyChangeEnum.NOCASHBALANCE.getCode())) {
            noCashBalance = noCashBalance + refund.getRefundAmount();
            changeAmout = refund.getRefundAmount();

        } else if (isBuy && order != null && type.equals(MoneyChangeEnum.NOCASHBALANCE.getCode())) {
            noCashBalance = noCashBalance - order.getNoCashBalancePrice();
            changeAmout = order.getNoCashBalancePrice();

        } else if (!isBuy && order != null && type.equals("confirmReceive")) {
            balance = balance + order.getPaymentPrice();
            changeAmout = order.getPaymentPrice();
            type = "BALANCE";
        } else if (type.equals(MoneyChangeEnum.ALIPAY.getCode())) {
            changeAmout = refund.getAliAmount();
        } else if (type.equals(MoneyChangeEnum.WX.getCode())) {
            changeAmout = refund.getWxAmount();
        }

        PartnerAccountLog partnerAccountLog = new PartnerAccountLog(
                account.getId(),
                account.getPartner().getId(),
                account.getBrandAppId(),
                null,
                refundType,
                changeAmout,
                account.getFreezeBalance(),
                balance,
                noCashBalance,
                owedBalance,
                account.getFreezeBalance(),
                account.getBalance(),
                account.getNoCashBalance(),
                account.getOwedBalance(),
                refund.getMemo(),
                partnerAccount.getId(),
                null,
                refund.getId(),
                null,
                null,
                null,
                MoneyChangeEnum.valueOf(type)
        );

        PartnerAccountLog palog =
                partnerAccountLogRepo.save(partnerAccountLog);
        if (palog.getId() != null) {
            return "success";
        }
        return "fail";
    }

    public String pAShopLog(PartnerAccount account, Integer price, Map<String, String> shopInfo) {

        Boolean isSell = shopInfo.get("type").equals("PAY");

        PartnerAccountLog partnerAccountLog = new PartnerAccountLog(
                account.getId(),
                account.getPartner().getId(),
                account.getBrandAppId(),
                null,
                isSell ? AccountChangeTypeEnum.RETAIL_ORDER :
                        AccountChangeTypeEnum.RETAIL_REFUND,
                price,
                account.getFreezeBalance(),
                isSell ? account.getBalance() + price :
                        account.getBalance() - price,
                account.getNoCashBalance(),
                account.getOwedBalance(),
                account.getFreezeBalance(),
                account.getBalance(),
                account.getNoCashBalance(),
                account.getOwedBalance(),
                isSell ? shopInfo.get("memo")
                        : null,
                null,
                null,
                null,
                null,
                shopInfo.get("retailOrderId"),
                null,
                MoneyChangeEnum.valueOf(shopInfo.get("payType"))
        );

        PartnerAccountLog palog =
                partnerAccountLogRepo.save(partnerAccountLog);
        if (palog.getId() != null) {
            return "success";
        }
        return "fail";
    }

    public void withDrawLog(WithdrawCash withdrawCash) {

        PartnerAccount partnerAccount = partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(withdrawCash.getPartnerId())
        );
        Partner partner = partnerRepo.
                findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, partnerAccount.getBrandAppId());

        PartnerAccount targetPartnerAccount = partnerAccountRepo.findByPartner(partner);

        PartnerAccountLog partnerAccountLog = new PartnerAccountLog(
                partnerAccount.getId(),
                partnerAccount.getPartner().getId(),
                partnerAccount.getBrandAppId(),
                WithdrawCashStatusEnum.SUCCESS,
                AccountChangeTypeEnum.WITHDRAW_CASH,
                withdrawCash.getWithdrawAmount(),
                partnerAccount.getFreezeBalance(),
                partnerAccount.getBalance() - withdrawCash.getWithdrawAmount(),
                partnerAccount.getNoCashBalance(),
                partnerAccount.getOwedBalance(),
                partnerAccount.getFreezeBalance(),
                partnerAccount.getBalance(),
                partnerAccount.getNoCashBalance(),
                partnerAccount.getOwedBalance(),
                null,
                targetPartnerAccount.getId(),
                null,
                null,
                null,
                null,
                withdrawCash.getId(),
                MoneyChangeEnum.WX
        );

        PartnerAccountLog palog =
                partnerAccountLogRepo.save(partnerAccountLog);
        Assert.notNull(palog.getId(), "明细保存失败");

    }

}
