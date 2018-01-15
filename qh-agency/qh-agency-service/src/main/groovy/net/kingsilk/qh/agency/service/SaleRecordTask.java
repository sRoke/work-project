package net.kingsilk.qh.agency.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerAccountLogRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SaleRecordRepo;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleRecordTask {

    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @Autowired
    private SaleRecordRepo saleRecordRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private SysConfRepo sysConfRepo;

    /**
     * 每天凌晨两点定时任务，记录每个渠道商的前一天天的销售额与本月（除当天，若为每月1号，则为上个月）销售额
     * 0 0 2 * * ? 每天凌晨两点定时任务
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void saleRecord() {
        PartnerTypeEnum[] partnerTypeEnums = new PartnerTypeEnum[]{PartnerTypeEnum.BRAND_COM, PartnerTypeEnum.GENERAL_AGENCY, PartnerTypeEnum.REGIONAL_AGENCY,
                PartnerTypeEnum.LEAGUE};
        List<String> brandAppIdList = Lists.newArrayList(partnerRepo.findAll(
                Expressions.allOf(
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        )).stream().map(Partner::getBrandAppId).distinct().collect(Collectors.toList());
        for (PartnerTypeEnum partnerTypeEnum : partnerTypeEnums) {
            for (String brandAppId : brandAppIdList) {
                saleRecordEvery(brandAppId, partnerTypeEnum);
            }
        }
    }

    private void saleRecordEvery(String brandAppId, PartnerTypeEnum partnerTypeEnum) {

        List<Partner> partners = Lists.newArrayList(partnerRepo.findAll(
                Expressions.allOf(
                        QPartner.partner.brandAppId.eq(brandAppId),
                        QPartner.partner.partnerTypeEnum.eq(partnerTypeEnum),
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        ));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date endStart = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date todayStart = calendar.getTime();
        calendar.add(Calendar.DATE, -2);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date yesterday2 = calendar.getTime();
        List<SaleRecord> saleRecordList = new LinkedList<>();
        if (!partners.isEmpty()) {
            for (int i = 0; i < partners.size(); i++) {

                Partner partner = partners.get(i);
                if(partner.getPlaceNum() == null){
                    partner.setPlaceNum(i);

                }
//        Partner partner = partnerRepo.findOne("5997e433f8fdbc75285f6149");
                List<PartnerAccountLog> accountLogs = Lists.newArrayList(partnerAccountLogRepo.findAll(
                        Expressions.allOf(
                                QPartnerAccountLog.partnerAccountLog.dateCreated.between(todayStart, endStart),
                                QPartnerAccountLog.partnerAccountLog.type.eq(AccountChangeTypeEnum.SELL),
                                QPartnerAccountLog.partnerAccountLog.partnerId.eq(partner.getId())
                        )
                ));

                Integer totalAccount = accountLogs.stream()
                        .map(PartnerAccountLog::getChangeAmount)
                        .reduce(0, (x, y) ->
                                x + y
                        );

                SaleRecord oneByPartnerId = saleRecordRepo.findOne(
                        Expressions.allOf(
                                QSaleRecord.saleRecord.partnerId.eq(partner.getId()),
                                QSaleRecord.saleRecord.saleDate.between(yesterday, yesterday2)
                        )
                );
                Integer numMouth = 0;
                //计算月销售额
                if (oneByPartnerId != null && null != oneByPartnerId.getNumMouth() && yesterday.getMonth() == endStart.getMonth()) numMouth = oneByPartnerId.getNumMouth();
                SaleRecord saleRecord = new SaleRecord();
                saleRecord.setNum(totalAccount);
                saleRecord.setPartnerId(partner.getId());
                saleRecord.setSaleDate(todayStart);
                saleRecord.setNumMouth(numMouth + totalAccount);
                saleRecord.setPartnerTypeEnum(partner.getPartnerTypeEnum());
                saleRecordList.add(saleRecord);
            }

//        Partner lastPartner = partnerRepo.findByPartnerTypeEnumAndBrandAppIdAndPlaceNumAfter(partnerTypeEnum, brandAppId,199,  new Sort(Sort.Direction.DESC,"palaceNum"));
//        Integer placeNum = lastPartner.getPlaceNum();
//        Long count = mongoTemplate.count(new Query().addCriteria(Criteria.where("partnerTypeEnum").is(partnerTypeEnum)), Partner.class);
//        int partnerCount = count.intValue();
            int partnerCount = partners.size();
            Integer placeNumMax = partners.stream().
                    max(Comparator.comparing(it ->
                            it.getPlaceNum())).
                    get().
                    getPlaceNum();
            Integer placeNum = placeNumMax == null ? 0 : placeNumMax;
            String sysConfKey = partnerTypeEnum.equals(PartnerTypeEnum.GENERAL_AGENCY) ? "generalAgencyMinPlace"
                    : (partnerTypeEnum.equals(PartnerTypeEnum.REGIONAL_AGENCY) ? "regionaLagencyMinPlace"
                    : "leagueMinPlace");

            //防止基础排名变高，而最高排名比基础排名低，需要从基础排名后开始算
            SysConf byKeyAndBrandAppId = sysConfRepo.findByKeyAndBrandAppId(sysConfKey, brandAppId);
            Integer numberSet = 0;
            if (byKeyAndBrandAppId != null) {
                numberSet = byKeyAndBrandAppId.getValueInt();
            }
            Integer number = numberSet == null ? 0 : numberSet;

            //对昨天销售额进行排序
            saleRecordList.sort((SaleRecord saleRecord1, SaleRecord saleRecord2) ->
                    saleRecord1.getNum() > saleRecord2.getNum() ? -1
                            : (saleRecord1.getNum() < saleRecord2.getNum() ? 1 : 0));
            saleRecordList.forEach((SaleRecord it) ->
                    {
                        it.setRankYesterdayReal((saleRecordList.indexOf(it)+1) + 1);

                        int index = (int)(((((float)(it.getRankYesterday()))/(placeNum+number))*100));
                        int place = index >= 100 ? 90 : (index <= 0 ? 5 : index);
                        it.setRankYesterday(((saleRecordList.indexOf(it) + 1) * 100 / partnerCount) * placeNum / 100 + number - 5 + new Random().nextInt(10));
                        it.setRankYesterdayPercent(place);
                    }
            );

            //对本月销售额进行排序
            saleRecordList.sort((SaleRecord saleRecord1, SaleRecord saleRecord2) ->
                    saleRecord1.getNumMouth() > saleRecord2.getNumMouth() ? -1
                            : (saleRecord1.getNumMouth() < saleRecord2.getNumMouth() ? 1 : 0));
            saleRecordList.forEach((SaleRecord it) ->
                    {
                        it.setRankMonthReal(saleRecordList.indexOf(it) + 1);
                        it.setRankMonth(((saleRecordList.indexOf(it)+1) * 100 / partnerCount) * placeNum / 100 + number - 5 + new Random().nextInt(10));
                        int index = (int)(((((float)(it.getRankMonth()))/(placeNum+number))*100));
                        int place = index >= 100 ? 90 : (index <= 0 ? 5 : index);
                        it.setRankMonthPercent(place);
                    }
            );

            saleRecordRepo.save(saleRecordList);
        }
    }
}

