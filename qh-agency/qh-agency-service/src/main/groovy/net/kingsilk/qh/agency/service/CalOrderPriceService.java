package net.kingsilk.qh.agency.service;

import net.kingsilk.qh.agency.core.OrderStatusEnum;
import net.kingsilk.qh.agency.core.RefundStatusEnum;
import net.kingsilk.qh.agency.core.RefundTypeEnum;
import net.kingsilk.qh.agency.domain.Order;
import net.kingsilk.qh.agency.domain.PartnerAccount;
import net.kingsilk.qh.agency.domain.QPartnerAccount;
import net.kingsilk.qh.agency.domain.Refund;
import net.kingsilk.qh.agency.repo.PartnerAccountRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.RefundRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CalOrderPriceService {

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

//    订单日志
    @Autowired
    private PALogService paLogService;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private RefundOnlyMoney refundOnlyMoney;

    /**
     * 使用账户可用余额抵扣
     */
    public void calBalancePrice(Order order, PartnerAccount partnerAccount) {
        Integer balance = partnerAccount.getBalance();

        Integer paymentPrice = order.getPaymentPrice();

        if (paymentPrice >= balance) {
            order.setBalancePrice(balance);
        } else if (paymentPrice < balance) {
            order.setBalancePrice(paymentPrice);
        }
        partnerAccountRepo.save(partnerAccount);
    }

    /**
     * 使用账户不可用余额抵扣
     */
    public void calNoCashBalancePrice(Order order, PartnerAccount partnerAccount) {

        Integer noCashBalance = partnerAccount.getNoCashBalance();
        Integer paymentPrice = order.getPaymentPrice();

        if (paymentPrice >= noCashBalance) {
            order.setNoCashBalancePrice(noCashBalance);
        } else if (paymentPrice < noCashBalance) {
            order.setNoCashBalancePrice(paymentPrice);
        }
    }

    /**
     * 计算总共需要支付的金额
     */
    public void calOrderPrice(Order order) {
        Integer orderPrice = order.getOrderPrice();
        Integer balancePrice = order.getBalancePrice();
        Integer noCashBalancePrice = order.getNoCashBalancePrice();
        order.setPaymentPrice(orderPrice - balancePrice);
        order.setPaymentPrice(order.getPaymentPrice() - noCashBalancePrice);
    }

    /**
     * 根据订单状态实现余额支付过程中上下级资金的流转
     *
     * @param order
     * @param partnerAccount
     */
    public void calParnterAccount(Order order, PartnerAccount partnerAccount) {

        PartnerAccount sellerAccount =partnerAccountRepo.findOne(
                QPartnerAccount.partnerAccount.partner.id.eq(order.getSellerPartnerId())
        );
        Integer balanceSeller = sellerAccount.getBalance();
        Integer owedBalanceSeller = sellerAccount.getOwedBalance();
        Integer balance = partnerAccount.getBalance();
        Integer noCashBalance = partnerAccount.getNoCashBalance();
        Integer noCashBalancePrice = order.getNoCashBalancePrice();
        Integer balancePrice = order.getBalancePrice();

        if (order.getStatus().equals(OrderStatusEnum.UNPAYED)||order.getStatus().equals(OrderStatusEnum.UNCONFIRMED)) {
            partnerAccount.setNoCashBalance(noCashBalance - noCashBalancePrice);
            partnerAccount.setBalance(balance - balancePrice);
        } else if (order.getStatus().equals(OrderStatusEnum.FINISHED)) {
            if (sellerAccount != null) {
                Integer thirdPayAmount = order.getPaymentPrice();
                sellerAccount.setBalance(balanceSeller + balancePrice + thirdPayAmount);
                sellerAccount.setOwedBalance(owedBalanceSeller - noCashBalancePrice);
//                paLogService.pALogConvert(sellerAccount, order, "BALANCE");
            }
        }else if (
                (order.getStatus().equals(OrderStatusEnum.REJECTED)
                &&order.getLastStatus().equals(OrderStatusEnum.UNCONFIRMED))
//                ||(order.getStatus().equals(OrderStatusEnum.CANCELED)
//                        &&order.getLastStatus().equals(OrderStatusEnum.UNCONFIRMED))
            ) {
            partnerAccount.setNoCashBalance(noCashBalance + noCashBalancePrice);
            partnerAccount.setBalance(balance + balancePrice);
        }else if(order.getStatus().equals(OrderStatusEnum.CANCELED)
                &&order.getLastStatus().equals(OrderStatusEnum.UNPAYED)){
            partnerAccount.setNoCashBalance(noCashBalance + noCashBalancePrice);
            partnerAccount.setBalance(balance + balancePrice);
        }
        partnerAccountRepo.save(partnerAccount);
        partnerAccountRepo.save(sellerAccount);
    }

    /**
     * 退货成功后实现余额支付过程中上下级资金的流转
     * 重载calParnterAccount(Order order, PartnerAccount partnerAccount)
     *
     * @param refund
     */
    public void calParnterAccount(Refund refund) {

        PartnerAccount sellerAccount = partnerAccountRepo.findByPartner(refund.getSellerPartner());
        PartnerAccount partnerAccount = partnerAccountRepo.findByPartner(refund.getBuyerPartner());
        Integer balance = partnerAccount.getBalance();

        Integer owedBalanceSeller = sellerAccount.getOwedBalance();
        Integer noCashBalance = partnerAccount.getNoCashBalance();
        Integer refundAmount = refund.getRefundAmount();
//        Integer applyPrice = refund.getApplyPrice();
        if (refund.getStatus().equals(RefundStatusEnum.UNPAYED)
                &&refund.getRefundType().equals(RefundTypeEnum.ITEM)) {
            sellerAccount.setOwedBalance(owedBalanceSeller + refundAmount);
            partnerAccount.setNoCashBalance(noCashBalance + refundAmount );
            partnerAccount.setBalance(balance);

        }else if (refund.getStatus().equals(RefundStatusEnum.UNPAYED)
                &&refund.getRefundType().equals(RefundTypeEnum.MONEY_ONLY)) {
            Order order = refund.getOrder();
            Integer noCashBalancePrice = order.getNoCashBalancePrice();
            Integer balancePrice = order.getBalancePrice();
            partnerAccount.setNoCashBalance(noCashBalance + noCashBalancePrice);
            partnerAccount.setBalance(balance + balancePrice);
            if(refund.getAliAmount()+refund.getWxAmount()!=0) {
                refundOnlyMoney.refund(refund.getId(),refund.getBrandAppId());
            }
        }
        partnerAccountRepo.save(partnerAccount);
        partnerAccountRepo.save(sellerAccount);
    }

    /**
     * 取消可用余额抵扣
     */
    public void cancelBalanceDeductible(PartnerAccount partnerAccount, Order order) {
        order.setBalancePrice(0);
    }

    /**
     * 取消不可用余额抵扣
     */
    public void cancelNoCashBalanceDeductible(PartnerAccount partnerAccount, Order order) {
        order.setNoCashBalancePrice(0);
    }
}
