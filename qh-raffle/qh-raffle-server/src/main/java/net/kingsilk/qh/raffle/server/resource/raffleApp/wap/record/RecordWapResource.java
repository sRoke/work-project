package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.record;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.api.common.ErrStatusException;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordDetail;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record.RecordWapApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record.dto.RecordInfoResp;
import net.kingsilk.qh.raffle.domain.*;
import net.kingsilk.qh.raffle.repo.AdcRepo;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.repo.RaffleRecordRepo;
import net.kingsilk.qh.raffle.repo.RaffleRepo;
import net.kingsilk.qh.raffle.service.RaffleAppService;
import net.kingsilk.qh.raffle.service.UserService;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class RecordWapResource implements RecordWapApi {

    @Autowired
    private RaffleRecordRepo raffleRecordRepo;

    @Autowired
    private RaffleAwardRepo raffleAwardRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrApi addrApi;

    @Autowired
    private RaffleRepo raffleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private RaffleAppService raffleAppService;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Override
    public UniResp<List<AwardInfo>> list(String raffleAppId, String raffleId, String openId) {

        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);

        List<RaffleRecord> records = Lists.newLinkedList(raffleRecordRepo.findAll(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId),
                        QRaffleRecord.raffleRecord.userId.eq(curUserId)
                )
        ));
        List<AwardInfo> awardInfos = records.stream().map(it ->
                {
                    AwardInfo awardInfo = conversionService.convert(raffleAwardRepo.findOne(it.getAwardId()), AwardInfo.class);
                    awardInfo.setRecordId(it.getId());
                    awardInfo.setAccept(it.getAccept());
                    awardInfo.setHandleStatus(it.getHandleStatus());
                    return awardInfo;
                }
        ).collect(Collectors.toList());
        UniResp<List<AwardInfo>> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(awardInfos);
        return uniResp;
    }

    @Override
    public UniResp<RecordInfoResp> info(String raffleAppId, String raffleId, String id, String openId) {
        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);
        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(id),
                        QRaffleRecord.raffleRecord.userId.eq(curUserId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );
        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(raffleId),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (record == null || !StringUtils.hasText(record.getAwardId())) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "找不到商品");

        }
        if (record.getAddr() == null) {
            net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addrapiDefault = addrApi.getDefault(curUserId, "USER_SHIPPING_ADDR");

            RaffleRecord.ShippingAddress shippingAddress = record.new ShippingAddress();
            shippingAddress.setAdc(addrapiDefault.getData().getAdc());
            shippingAddress.setReceiver(addrapiDefault.getData().getContact());
            if (addrapiDefault.getData().getPhones() != null && !addrapiDefault.getData().getPhones().isEmpty()) {
                shippingAddress.setPhone(Lists.newArrayList(addrapiDefault.getData().getPhones()).stream().findFirst().get());
            }
            shippingAddress.setStreet(addrapiDefault.getData().getStreet());
            record.setAddr(shippingAddress);
        }
        raffleRecordRepo.save(record);
        RaffleAward award = raffleAwardRepo.findOne(
                Expressions.allOf(
                        QRaffleAward.raffleAward.deleted.ne(true),
                        QRaffleAward.raffleAward.id.eq(record.getAwardId())
//                        DbUtil.opIn(QRaffleAward.raffleAward.id, Arrays.asList(record.getAwardId()))
                )
        );
        if (award == null) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "找不到商品");

        }

        ShopResp data = raffleAppService.getShopInfo(raffleAppId).getData();

        AwardInfo awardInfo = conversionService.convert(award, AwardInfo.class);
        UniResp<RecordInfoResp> uniResp = new UniResp<>();
        RecordInfoResp resp = new RecordInfoResp();
        resp.getAwardInfoList().add(awardInfo);
        if (data != null) {
            resp.setShopAddress(data.getAddress());
            resp.setShopName(data.getName());
            resp.setPhone(data.getPhone());
        }
        resp.setDrawType(raffle.getDrawType());
        resp.setMemo(record.getMemo());
        Optional.ofNullable(record.getAddr()).ifPresent(it ->
                {
                    RecordInfoResp.AddrModel addrModel = new RecordInfoResp.AddrModel();
                    BeanUtils.copyProperties(it, addrModel);
                    addrModel.setAdcNo(it.getAdc());
                    Adc adc = adcRepo.findOneByNo(it.getAdc());
                    Optional.ofNullable(adc).ifPresent(addrAdc ->
                            {
                                addrModel.setCountyNo(addrAdc.getNo());
                                addrModel.setArea(addrAdc.getName());
                                Optional.ofNullable(adc.getParent()).ifPresent(adcParent ->
                                        {
                                            addrModel.setCityNo(adcParent.getNo());
                                            addrModel.setCity(adcParent.getName());
                                            Optional.ofNullable(adcParent.getParent()).ifPresent(adcParents ->
                                                    {
                                                        addrModel.setProvince(adcParents.getName());
                                                        addrModel.setProvinceNo(adcParents.getNo());
                                                    }
                                            );
                                        }
                                );
                            }
                    );
                    resp.setAddress(addrModel);
                }
        );
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<RaffleRecordDetail> detail(String raffleAppId, String raffleId, String id, String openId) {

        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);
        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(id),
                        QRaffleRecord.raffleRecord.userId.eq(curUserId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );

        if (record == null || !StringUtils.hasText(record.getAwardId())) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "找不到商品");

        }
        ShopResp data = raffleAppService.getShopInfo(raffleAppId).getData();
        RaffleRecordDetail resp = new RaffleRecordDetail();
        resp.setMemo(record.getMemo());
        resp.setLogisticsCompany(record.getLogisticsCompany());
        resp.setLogisticsId(record.getExpressNo());
        resp.setDrawType(record.getDrawType());
        if (data != null) {
            resp.setShopAddress(data.getAddress());
            resp.setShopName(data.getName());
            resp.setShopPhone(data.getPhone());
        }
        Optional.ofNullable(record.getAddr()).ifPresent(it ->
                {
                    RecordInfoResp.AddrModel addrModel = new RecordInfoResp.AddrModel();
                    BeanUtils.copyProperties(it, addrModel);
                    addrModel.setAdcNo(it.getAdc());
                    Adc adc = adcRepo.findOneByNo(it.getAdc());
                    Optional.ofNullable(adc).ifPresent(addrAdc ->
                            {
                                addrModel.setCountyNo(addrAdc.getNo());
                                addrModel.setArea(addrAdc.getName());
                                Optional.ofNullable(adc.getParent()).ifPresent(adcParent ->
                                        {
                                            addrModel.setCityNo(adcParent.getNo());
                                            addrModel.setCity(adcParent.getName());
                                            Optional.ofNullable(adcParent.getParent()).ifPresent(adcParents ->
                                                    {
                                                        addrModel.setProvince(adcParents.getName());
                                                        addrModel.setProvinceNo(adcParents.getNo());
                                                    }
                                            );
                                        }
                                );
                            }
                    );
                    resp.setAddress(addrModel);
                }
        );
        UniResp<RaffleRecordDetail> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> accept(String raffleAppId, String raffleId, String id, String openId, String memo) {
        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);
        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(id),
                        QRaffleRecord.raffleRecord.userId.eq(curUserId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );

        if (record == null || !StringUtils.hasText(record.getAwardId())) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "找不到商品");

        }
        if (!record.getAccept()) {
            record.setAccept(true);
        }
        record.setMemo(memo);
        raffleRecordRepo.save(record);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("领取成功");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> chooseAddr(String raffleAppId, String raffleId, String id, String addrId, String openId) {

        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.id.eq(id)
                )
        );
        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addrGetRespUniResp = addrApi.get(userId, addrId);
        RaffleRecord.ShippingAddress shippingAddress = record.new ShippingAddress();
        shippingAddress.setAdc(addrGetRespUniResp.getData().getAdc());
        shippingAddress.setMemo(addrGetRespUniResp.getData().getMemo());
        shippingAddress.setPhone(addrGetRespUniResp.getData().getPhones().isEmpty() ? null : addrGetRespUniResp.getData().getPhones().iterator().next());
        shippingAddress.setReceiver(addrGetRespUniResp.getData().getContact());
        shippingAddress.setStreet(addrGetRespUniResp.getData().getStreet());
        record.setAddr(shippingAddress);
        raffleRecordRepo.save(record);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("修改成功");
        return uniResp;
    }
}
