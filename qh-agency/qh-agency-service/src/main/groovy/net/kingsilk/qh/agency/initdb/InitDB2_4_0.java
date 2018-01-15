package net.kingsilk.qh.agency.initdb;


import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitDB2_4_0 {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemPropRepo itemPropRepo;

    @Autowired
    private ItemPropValueRepo itemPropValueRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @Autowired
    private PartnerChangeLogRepo partnerChangeLogRepo;

    @Autowired
    private PartnerStaffRepo partnerStaffRepo;

    @Autowired
    private QhPayRepo uhPayRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private SkuStoreLogRepo skuStoreLogRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Autowired
    private SysConfRepo sysConfRepo;

    public void initDB (){
        System.out.print("哈哈哈");
        List<Cart> carts = cartRepo.findAll();
        List<Category> categories = categoryRepo.findAll();
        List<Certificate> certificateRepoAll = certificateRepo.findAll();
        List<DeliverInvoice> invoiceRepoAll = deliverInvoiceRepo.findAll();
        List<Item> itemRepoAll = itemRepo.findAll();
        List<ItemProp> itemPropRepoAll = itemPropRepo.findAll();
        List<ItemPropValue> itemPropValueRepoAll = itemPropValueRepo.findAll();
        List<Order> orderRepoAll = orderRepo.findAll();
        List<Partner> partnerRepoAll = partnerRepo.findAll();
        List<PartnerAccount> partnerAccountRepoAll = partnerAccountRepo.findAll();
        List<PartnerAccountLog> accountLogRepoAll = partnerAccountLogRepo.findAll();
        List<PartnerChangeLog> partnerChangeLogRepoAll = partnerChangeLogRepo.findAll();
        List<PartnerStaff> partnerStaffRepoAll = partnerStaffRepo.findAll();
        List<QhPay> uhPayRepoAll = uhPayRepo.findAll();
        List<Refund> refundRepoAll = refundRepo.findAll();
        List<Sku> skuRepoAll = skuRepo.findAll();
        List<SkuStore> skuStoreRepoAll = skuStoreRepo.findAll();
        List<SkuStoreLog> skuStoreLogRepoAll = skuStoreLogRepo.findAll();
        List<Staff> staffRepoAll = staffRepo.findAll();
        List<StaffGroup> staffGroupRepoAll = staffGroupRepo.findAll();
        List<WithdrawCash> withdrawCashRepoAll = withdrawCashRepo.findAll();
        List<SysConf> sysConfRepoAll = sysConfRepo.findAll();
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            cart.setBrandAppId("59782691f8fdbc1f9b2c4323");
            cartRepo.save(cart);
        }

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            category.setBrandAppId("59782691f8fdbc1f9b2c4323");
            categoryRepo.save(category);
        }

        for (int i = 0; i < certificateRepoAll.size(); i++) {
            Certificate certificate = certificateRepoAll.get(i);
            certificate.setBrandAppId("59782691f8fdbc1f9b2c4323");
            certificateRepo.save(certificate);
        }

        for (int i = 0; i < invoiceRepoAll.size(); i++) {
            DeliverInvoice deliverInvoice = invoiceRepoAll.get(i);
            deliverInvoice.setBrandAppId("59782691f8fdbc1f9b2c4323");
            deliverInvoiceRepo.save(deliverInvoice);
        }

        for (int i = 0; i < itemRepoAll.size(); i++) {
            Item item = itemRepoAll.get(i);
            item.setBrandAppId("59782691f8fdbc1f9b2c4323");
            itemRepo.save(item);
        }

        for (int i = 0; i < itemPropRepoAll.size(); i++) {
            ItemProp itemProp = itemPropRepoAll.get(i);
            itemProp.setBrandAppId("59782691f8fdbc1f9b2c4323");
            itemPropRepo.save(itemProp);
        }
        for (int i = 0; i < itemPropValueRepoAll.size(); i++) {
            ItemPropValue itemPropValue = itemPropValueRepoAll.get(i);
            itemPropValue.setBrandAppId("59782691f8fdbc1f9b2c4323");
            itemPropValueRepo.save(itemPropValue);
        }
        for (int i = 0; i < orderRepoAll.size(); i++) {
            Order order = orderRepoAll.get(i);
            order.setBrandAppId("59782691f8fdbc1f9b2c4323");
            orderRepo.save(order);
        }
        for (int i = 0; i < partnerRepoAll.size(); i++) {
            Partner partner = partnerRepoAll.get(i);
            partner.setBrandAppId("59782691f8fdbc1f9b2c4323");
            partnerRepo.save(partner);

        }
        for (int i = 0; i < partnerAccountRepoAll.size(); i++) {
            PartnerAccount partnerAccount = partnerAccountRepoAll.get(i);
            partnerAccount.setBrandAppId("59782691f8fdbc1f9b2c4323");
            partnerAccountRepo.save(partnerAccount);
        }
        for (int i = 0; i < accountLogRepoAll.size(); i++) {
            PartnerAccountLog partnerAccountLog = accountLogRepoAll.get(i);
            partnerAccountLog.setBrandAppId("59782691f8fdbc1f9b2c4323");
            partnerAccountLogRepo.save(partnerAccountLog);
        }
        for (int i = 0; i < partnerChangeLogRepoAll.size(); i++) {
            PartnerChangeLog partnerChangeLog = partnerChangeLogRepoAll.get(i);
            partnerChangeLog.setBrandAppId("59782691f8fdbc1f9b2c4323");
            partnerChangeLogRepo.save(partnerChangeLog);
        }
        for (int i = 0; i < partnerStaffRepoAll.size(); i++) {
            PartnerStaff partnerStaff = partnerStaffRepoAll.get(i);
            partnerStaff.setBrandAppId("59782691f8fdbc1f9b2c4323");
            partnerStaffRepo.save(partnerStaff);
        }
        for (int i = 0; i < uhPayRepoAll.size(); i++) {
            QhPay qhPay = uhPayRepoAll.get(i);
            qhPay.setBrandAppId("59782691f8fdbc1f9b2c4323");
            uhPayRepo.save(qhPay);
        }
        for (int i = 0; i < refundRepoAll.size(); i++) {
            Refund refund = refundRepoAll.get(i);
            refund.setBrandAppId("59782691f8fdbc1f9b2c4323");
            refundRepo.save(refund);
        }
        for (int i = 0; i < skuRepoAll.size(); i++) {
            Sku sku = skuRepoAll.get(i);
            sku.setBrandAppId("59782691f8fdbc1f9b2c4323");
            skuRepo.save(sku);
        }
        for (int i = 0; i < skuStoreRepoAll.size(); i++) {
            SkuStore skuStore = skuStoreRepoAll.get(i);
            skuStore.setBrandAppId("59782691f8fdbc1f9b2c4323");
            skuStoreRepo.save(skuStore);
        }
        for (int i = 0; i < skuStoreLogRepoAll.size(); i++) {
            SkuStoreLog skuStoreLog = skuStoreLogRepoAll.get(i);
            skuStoreLog.setBrandAppId("59782691f8fdbc1f9b2c4323");
            skuStoreLogRepo.save(skuStoreLog);
        }
        for (int i = 0; i < staffRepoAll.size(); i++) {
            Staff staff = staffRepoAll.get(i);
            staff.setBrandAppId("59782691f8fdbc1f9b2c4323");
            staffRepo.save(staff);
        }
        for (int i = 0; i < staffGroupRepoAll.size(); i++) {
            StaffGroup staffGroup = staffGroupRepoAll.get(i);
            staffGroup.setBrandAppId("59782691f8fdbc1f9b2c4323");
            staffGroupRepo.save(staffGroup);
        }
        for (int i = 0; i < withdrawCashRepoAll.size(); i++) {
            WithdrawCash withdrawCash = withdrawCashRepoAll.get(i);
            withdrawCash.setBrandAppId("59782691f8fdbc1f9b2c4323");
            withdrawCashRepo.save(withdrawCash);
            System.out.print("哈哈哈");
        }
        for (int i = 0; i < sysConfRepoAll.size(); i++) {
            SysConf sysConf = sysConfRepoAll.get(i);
            sysConf.setBrandAppId("59782691f8fdbc1f9b2c4323");
            sysConfRepo.save(sysConf);
        }
        System.out.print("哈哈哈");
    }
}

