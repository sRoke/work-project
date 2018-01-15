import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfMinPlace;
import net.kingsilk.qh.agency.core.AccountChangeTypeEnum;
import net.kingsilk.qh.agency.core.MoneyChangeEnum;
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.PartnerAccountLogRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SaleRecordRepo;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import net.kingsilk.qh.agency.server.QhAgencyServerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;


@SpringBootTest(classes = QhAgencyServerApp.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class SysConfTest {

    @Autowired
    RestTemplate restTemplate;

    @LocalServerPort
    private int prot;

    @Test
    public void test1() throws Exception {
        String path = "https://kingsilk.net/agency/rs/local/16700/brandApp/{brandAppId}/sysConf/minPlace";
        Map<String, String> map = new HashMap<>();
        map.put("brandAppId", "59e0341a46e0fb0008f24133");


        HttpHeaders heards = new HttpHeaders();
        Gson gson = new Gson();
        SysConfMinPlace sysConfMinPlace = new SysConfMinPlace();
        sysConfMinPlace.setGeneralAgencyMinPlace("300");
        sysConfMinPlace.setGeneralAgencyMinPlaceNum("500");
        sysConfMinPlace.setLeagueMinPlace("200");
        sysConfMinPlace.setLeagueMinPlaceNum("500");
        sysConfMinPlace.setRegionaLagencyMinPlace("100");
        sysConfMinPlace.setRegionaLagencyMinPlaceNum("1000");
        String json = gson.toJson(sysConfMinPlace, SysConfMinPlace.class);
        HashMap multiMap = gson.fromJson(json, HashMap.class);
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.setAll(multiMap);
        URI uri = UriComponentsBuilder.fromHttpUrl(path).queryParams(multiValueMap).buildAndExpand(map).toUri();
        heards.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(json, heards);

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(uri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });
        System.out.println(prot);
        System.out.println(responseEntity.getStatusCodeValue());
    }

    @Autowired
    private SaleRecordRepo saleRecordRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private SysConfRepo sysConfRepo;

    //初始化数据库
    @Test
    public void saleRecord() {
        //spring data不知道怎么搞单个属性 XD
        //TODO
        PartnerTypeEnum[] partnerTypeEnums = new PartnerTypeEnum[]{PartnerTypeEnum.BRAND_COM,
                PartnerTypeEnum.GENERAL_AGENCY,
                PartnerTypeEnum.REGIONAL_AGENCY,
                PartnerTypeEnum.LEAGUE};
        List<String> brandAppIdList = Lists.newArrayList(partnerRepo.findAll(
                Expressions.allOf(
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        )).stream().map(Partner::getBrandAppId).distinct().collect(Collectors.toList());

        //最老的渠道商
        List<Partner> partnerList = partnerRepo.findAll(new Sort(Sort.Direction.ASC, "dateCreated"));
        Date dateCreated = partnerList.
                stream().
                min(Comparator.comparing(Partner::getDateCreated)).
                get().
                getDateCreated();
        Long l = (new Date().getTime() - dateCreated.getTime()) / (1000 * 3600 * 24);
        int i1 = l.intValue();

        for (PartnerTypeEnum partnerTypeEnum : partnerTypeEnums) {
            for (String brandAppId : brandAppIdList) {
                for (int j = i1; j > 0; j--) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.DATE, 1 - j);
                    Date endStart = calendar.getTime();
                    calendar.add(Calendar.DATE, -1);
                    Date todayStart = calendar.getTime();
                    calendar.add(Calendar.DATE, -2);
                    calendar.set(Calendar.HOUR_OF_DAY, 1);
                    Date yesterday = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date yesterday2 = calendar.getTime();

                    //查出XX之前注册的渠道商
                    List<Partner> partners = Lists.newArrayList(partnerRepo.findAll(
                            Expressions.allOf(
                                    QPartner.partner.brandAppId.eq(brandAppId),
                                    QPartner.partner.partnerTypeEnum.eq(partnerTypeEnum),
                                    QPartner.partner.deleted.eq(false),
                                    QPartner.partner.disabled.eq(false),
                                    QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL),
                                    QPartner.partner.dateCreated.before(todayStart)
                            )
                    ));

                    List<SaleRecord> saleRecordList = new LinkedList<>();
                    if (!partners.isEmpty()) {

                        partners.sort((Partner partner1, Partner partner2) ->
                                partner1.getDateCreated().before(partner2.getDateCreated()) ? -1
                                        : (partner1.getDateCreated().after(partner2.getDateCreated())
                                        ? 1 : 0)
                        );
                        for (int i = 0; i < partners.size(); i++) {

                            Partner partner = partners.get(i);

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

//            for (int j = 0; j < accountLogs.size(); j++) {
//                totalAccount = totalAccount + accountLogs.get(j).getChangeAmount();
//            }

                            SaleRecord oneByPartnerId = saleRecordRepo.findOne(
                                    Expressions.allOf(
                                            QSaleRecord.saleRecord.partnerId.eq(partner.getId()),
                                            QSaleRecord.saleRecord.saleDate.between(yesterday, yesterday2)
                                    )
                            );
                            Integer numMouth = 0;
                            //计算月销售额
                            if (oneByPartnerId != null
                                    && null != oneByPartnerId.getNumMouth()
                                    && yesterday.getMonth() == endStart.getMonth()) {
                                numMouth = oneByPartnerId.getNumMouth();
                            }
                            SaleRecord saleRecord = new SaleRecord();
                            saleRecord.setNum(totalAccount);
                            saleRecord.setPartnerId(partner.getId());
                            saleRecord.setSaleDate(todayStart);
                            saleRecord.setNumMouth(numMouth + totalAccount);
                            saleRecord.setPartnerTypeEnum(partner.getPartnerTypeEnum());
                            saleRecordList.add(saleRecord);
                        }

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
                                    it.setRankYesterday(((saleRecordList.indexOf(it)+1) * 100 / partnerCount) * placeNum / 100 + number+1);
                                    it.setRankYesterdayPercent((int)(((((float)(it.getRankYesterday()))/(placeNum+number))*100)));
                                }
                        );
//            saleRecordList.forEach((SaleRecord it) ->
//                    it.setRankYesterday((saleRecordList.indexOf(it) * 100 / partnerCount) * placeNum / 100 + number+1)
//
//            );
                        //对本月销售额进行排序
                        saleRecordList.sort((SaleRecord saleRecord1, SaleRecord saleRecord2) ->
                                saleRecord1.getNumMouth() > saleRecord2.getNumMouth() ? -1
                                        : (saleRecord1.getNumMouth() < saleRecord2.getNumMouth() ? 1 : 0));
                        saleRecordList.forEach((SaleRecord it) ->
                                {
                                    it.setRankMonthReal(saleRecordList.indexOf(it) + 1);
                                    it.setRankMonth(((saleRecordList.indexOf(it)+1) * 100 / partnerCount) * placeNum / 100 + number);
                                    it.setRankMonthPercent((int)(((((float)(it.getRankMonth()))/(placeNum+number))*100)));
                                }
                        );

                        saleRecordRepo.save(saleRecordList);
                    }

                }
            }
        }
    }


    @Test
    public void test3() {
        PartnerTypeEnum[] partnerTypeEnums = new PartnerTypeEnum[]{PartnerTypeEnum.BRAND_COM,
                PartnerTypeEnum.GENERAL_AGENCY,
                PartnerTypeEnum.REGIONAL_AGENCY,
                PartnerTypeEnum.LEAGUE};
        List<String> brandAppIdList = Lists.newArrayList(partnerRepo.findAll(
                Expressions.allOf(
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        )).stream().map(Partner::getBrandAppId).distinct().collect(Collectors.toList());

        //最老的渠道商
        List<Partner> partnerList = partnerRepo.findAll(new Sort(Sort.Direction.ASC, "dateCreated"));
        Date dateCreated = partnerList.
                stream().
                min(Comparator.comparing(partner ->
                        partner.getDateCreated())).
                get().
                getDateCreated();
        Long l = (new Date().getTime() - dateCreated.getTime()) / (1000 * 3600 * 24);
        int i1 = l.intValue();

        for (PartnerTypeEnum partnerTypeEnum : partnerTypeEnums) {
            for (String brandAppId : brandAppIdList) {
                for (int j = i1; j > 0; j--) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.DATE, 1 - j);
                    Date endStart = calendar.getTime();
                    calendar.add(Calendar.DATE, -1);
                    Date todayStart = calendar.getTime();
                    calendar.add(Calendar.DATE, -2);
                    calendar.set(Calendar.HOUR_OF_DAY, 1);
                    Date yesterday = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date yesterday2 = calendar.getTime();

                    //查出XX之前注册的渠道商
                    List<Partner> partners = Lists.newArrayList(partnerRepo.findAll(
                            Expressions.allOf(
                                    QPartner.partner.brandAppId.eq(brandAppId),
                                    QPartner.partner.partnerTypeEnum.eq(partnerTypeEnum),
                                    QPartner.partner.deleted.eq(false),
                                    QPartner.partner.disabled.eq(false),
                                    QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL),
                                    QPartner.partner.dateCreated.before(todayStart)
                            )
                    ));

                    if (!partners.isEmpty()) {

                        partners.sort((Partner partner1, Partner partner2) ->
                                partner1.getDateCreated().before(partner2.getDateCreated()) ? -1
                                        : (partner1.getDateCreated().after(partner2.getDateCreated())
                                        ? 1 : 0)
                        );
                        //初始化员工排名
                        for (int i = 0; i < partners.size(); i++) {
                            Partner partner = partners.get(i);
                            String sysConfKey1 = partner.getPartnerTypeEnum().equals(PartnerTypeEnum.GENERAL_AGENCY) ? "generalAgencyMinPlaceNum"
                                    : (partner.getPartnerTypeEnum().equals(PartnerTypeEnum.REGIONAL_AGENCY) ? "regionaLagencyMinPlaceNum"
                                    : "leagueMinPlaceNum");

                            //防止基础排名变高，而最高排名比基础排名低，需要从基础排名后开始算
                            SysConf byKeyAndBrandAppId1 = sysConfRepo.findByKeyAndBrandAppId(sysConfKey1, brandAppId);
                            Integer numberSet1 = 0;
                            if (byKeyAndBrandAppId1 != null) {
                                numberSet1 = byKeyAndBrandAppId1.getValueInt();
                            }
                            Integer number1 = numberSet1 == null ? 0 : numberSet1;

                            partner.setPlaceNum(number1 + i + 1);
                            partnerRepo.save(partner);
                        }
                    }
                }
            }
        }
    }

    @Autowired
    private PartnerAccountLogRepo partnerAccountLogRepo;

    @Test
    public void test4(){

        //spring data不知道怎么搞单个属性 XD
        //TODO
        ArrayList<PartnerAccountLog> logs = Lists.newArrayList(partnerAccountLogRepo.findAll(Expressions.allOf(QPartnerAccountLog.partnerAccountLog.memo.eq("测试用"))));
        partnerAccountLogRepo.delete(logs);
        //最老的渠道商
        List<Partner> partnerList = partnerRepo.findAll(new Sort(Sort.Direction.ASC, "dateCreated"));
        Date dateCreated = partnerList.
                stream().
                min(Comparator.comparing(partner ->
                        partner.getDateCreated())).
                get().
                getDateCreated();
        Long l = (new Date().getTime() - dateCreated.getTime()) / (1000 * 3600 * 24);
        int i1 = l.intValue();

        List<Partner> partners = Lists.newArrayList(partnerRepo.findAll(
                Expressions.allOf(
                        QPartner.partner.deleted.eq(false),
                        QPartner.partner.disabled.eq(false),
                        QPartner.partner.partnerApplyStatus.eq(PartnerApplyStatusEnum.NORMAL)
                )
        ));
        for (Partner partner : partners) {
            for (int j = i1; j > 0; j--) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(Calendar.DATE, 1 - j);
                for (int i=0;i<20;i++) {
                    calendar.set(Calendar.MINUTE, i);
                    Date time = calendar.getTime();
                    PartnerAccountLog partnerAccountLog = new PartnerAccountLog();
                    partnerAccountLog.setBrandAppId(partner.getBrandAppId());
                    partnerAccountLog.setChangeAmount(new Random().nextInt(1000)+1);
                    partnerAccountLog.setMoneyChangeEnum(MoneyChangeEnum.ALI);
                    partnerAccountLog.setMemo("测试用");
                    partnerAccountLog.setPartnerId(partner.getId());
                    partnerAccountLog.setType(AccountChangeTypeEnum.SELL);
                    partnerAccountLog.setDateCreated(time);
                    partnerAccountLogRepo.save(partnerAccountLog);
                }
            }
        }

    }

}
