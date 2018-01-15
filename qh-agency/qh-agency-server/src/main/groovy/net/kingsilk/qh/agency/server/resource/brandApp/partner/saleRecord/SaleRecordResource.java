package net.kingsilk.qh.agency.server.resource.brandApp.partner.saleRecord;

import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.saleRecord.SaleRecordApi;
import net.kingsilk.qh.agency.api.brandApp.partner.saleRecord.dto.SaleRecordResp;
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SaleRecordRepo;
import net.kingsilk.qh.agency.server.resource.brandApp.partner.saleRecord.convert.SaleRecordConvert;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import net.kingsilk.qh.agency.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class SaleRecordResource implements SaleRecordApi {

    @Autowired
    private SaleRecordRepo saleRecordRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private PartnerStaffService partnerStaffService;

    @Autowired
    private SaleRecordConvert saleRecordConvert;
//    @Autowired
//    private SaleRecordTask saleRecordTask;
    @Override
    public UniResp<SaleRecordResp> getSale(String brandAppId, String partnerId) {
        String userId = secService.curUserId();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        String id = partnerStaff.getPartner().getId();
        Partner partner = partnerRepo.findOne(
                allOf(
                        QPartner.partner.id.eq(id),
                        QPartner.partner.brandAppId.eq(brandAppId),
                        QPartner.partner.userId.eq(userId),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        );
//        Partner partner = partnerRepo.findOne("5997e433f8fdbc75285f6149");
//        saleRecordTask.saleRecord();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.add(Calendar.DATE,-2);
        Date todayStart = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        Date todayStart2 = calendar.getTime();
        SaleRecord saleRecord = saleRecordRepo.findOne(
                allOf(
                        QSaleRecord.saleRecord.partnerId.eq(partner.getId()),
                        QSaleRecord.saleRecord.saleDate.between(todayStart,todayStart2)
                )
        );
        SaleRecordResp saleRecordResp = saleRecordConvert.saleRecordRespConver(saleRecord, partner);

        UniResp<SaleRecordResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(saleRecordResp);
        return uniResp;
    }

}

