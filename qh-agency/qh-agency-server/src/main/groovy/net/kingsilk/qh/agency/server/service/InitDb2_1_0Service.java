package net.kingsilk.qh.agency.server.service;

import groovy.transform.CompileStatic;
import net.kingsilk.qh.agency.domain.Order;
import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.domain.PartnerAccount;
import net.kingsilk.qh.agency.domain.SkuStore;
import net.kingsilk.qh.agency.repo.OrderRepo;
import net.kingsilk.qh.agency.repo.PartnerAccountRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
@CompileStatic
public class InitDb2_1_0Service {


    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private OrderRepo orderRepo;

    /**
     * initPartnerStaffAccount
     */
    public void initPartnerStaffAccount() {

        List<Partner> partners = partnerRepo.findAll();
        partners.forEach(
                partner -> {
                    PartnerAccount partnerAccount = partnerAccountRepo.findByPartner(partner);
                    if (partnerAccount != null) {
                        if (partnerAccount.getOwedBalance() == null) {
                            partnerAccount.setOwedBalance(0);
                            partnerAccountRepo.save(partnerAccount);
                        }
                    } else {
                        partnerAccount = new PartnerAccount();
                        partnerAccount.setOwedBalance(0);
                        partnerAccount.setBalance(0);
                        partnerAccount.setPartner(partner);
                        partnerAccount.setFreezeBalance(0);
                        partnerAccount.setNoCashBalance(0);
                        partnerAccount.setBrandAppId(partner.getBrandAppId());
                        partnerAccountRepo.save(partnerAccount);
                    }
                }
        );
    }

    public void initSkuStore() {
        List<SkuStore> skuStores = skuStoreRepo.findAll();
        skuStores.forEach(
                skuStore -> {
                    if (skuStore.getSalesVolume() == null) {
                        skuStore.setSalesVolume(0);
                        skuStoreRepo.save(skuStore);
                    }
                }
        );
    }

    public void initOrder() {
        List<Order> orders = orderRepo.findAll();
        orders.forEach(
                order -> {
                    if (order.getBalancePrice() == null) {
                        order.setBalancePrice(0);
                    }
                    if (order.getNoCashBalancePrice() == null) {
                        order.setNoCashBalancePrice(0);
                    }
                    orderRepo.save(order);

                }
        );
    }

}
