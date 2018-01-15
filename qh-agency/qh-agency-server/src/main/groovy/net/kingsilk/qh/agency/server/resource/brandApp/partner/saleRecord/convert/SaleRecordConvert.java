package net.kingsilk.qh.agency.server.resource.brandApp.partner.saleRecord.convert;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.brandApp.partner.saleRecord.dto.SaleRecordResp;
import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerAccountLogRepo;
import net.kingsilk.qh.agency.repo.SaleRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoj
 * @Date 2017-10-23
 */
@Component
public class SaleRecordConvert {

    @Autowired
    private SaleRecordRepo saleRecordRepo;

    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @NotNull
    public SaleRecordResp saleRecordRespConver(SaleRecord saleRecord, Partner partner) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.add(Calendar.DATE, -2);
        Date todayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date endStart = calendar.getTime();
        calendar.add(Calendar.DATE, -6);
        Date lastDay = calendar.getTime();

        SaleRecordResp saleRecordResp = new SaleRecordResp();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        List<SaleRecordResp.SaleRecordEach> saleRecordeList = new ArrayList<>();

        if (saleRecord != null) {
            Integer rankYesterday = saleRecord.getRankYesterday();
            Integer rankMonth = saleRecord.getRankMonth();

            //计算名次，为空或0，说明没有被定时任务处理 XD
            if (rankYesterday == null) {
                saleRecordResp.setRankYesterday(103);
            } else {
                saleRecordResp.setRankYesterday(rankYesterday);
            }
            if (rankMonth == null) {
                saleRecordResp.setRankMonth(78);
                saleRecordResp.setRankMonthPercent(50);
            } else {
                saleRecordResp.setRankMonth(rankMonth);
                saleRecordResp.setRankMonthPercent(saleRecord.getRankMonthPercent());
            }
            //获取前六天的销售额
            String partnerId = saleRecord.getPartnerId();
            saleRecordeList.addAll(
                    Lists.newArrayList(saleRecordRepo.findAll(
                            Expressions.allOf(
                                    QSaleRecord.saleRecord.saleDate.between(lastDay, endStart),
                                    QSaleRecord.saleRecord.partnerId.eq(partnerId)
                            ), new Sort(Sort.Direction.ASC, "saleDate")
                    )).stream().
                            map(e -> saleRecordResp.new SaleRecordEach(e.getNum(), e.getSaleDate().getDate())
                            ).
                            collect(Collectors.toList())
            );
        } else {
            saleRecordResp.setRankMonth(0);
            saleRecordResp.setRankMonthPercent(0);
        }
        saleRecordeList.add(
                saleRecordResp.new SaleRecordEach(
                        Lists.newArrayList(partnerAccountLogRepo.findAll(
                                Expressions.allOf(
                                        QPartnerAccountLog.partnerAccountLog.dateCreated.lt(new Date()),
                                        QPartnerAccountLog.partnerAccountLog.dateCreated.gt(endStart),
                                        QPartnerAccountLog.partnerAccountLog.type.eq(AccountChangeTypeEnum.SELL),
                                        QPartnerAccountLog.partnerAccountLog.partnerId.eq(partner.getId())
                                )
                        )).stream().
                                mapToInt(PartnerAccountLog::getChangeAmount).
                                sum(),
                        new Date().getDate()
                )
        );
        //七天总销售额
        Integer num = saleRecordeList.stream().
                mapToInt(SaleRecordResp.SaleRecordEach::getSale).
                sum();
        saleRecordResp.setNum(num);
        saleRecordResp.setPartnerTypeEnum(partner.getPartnerTypeEnum().getDesp());
        saleRecordResp.setPlaceNum(partner.getPlaceNum());
        saleRecordResp.setSaleRecordeList(saleRecordeList);
        return saleRecordResp;
    }

}
