package net.kingsilk.qh.raffle.server.resource.raffleApp.record;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.raffle.api.common.*;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.RaffleRecordApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.DeliverInvoiceShipReq;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordDetail;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordMinResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordReq;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.record.dto.RecordInfoResp;
import net.kingsilk.qh.raffle.core.DeliverStatusEnum;
import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;
import net.kingsilk.qh.raffle.domain.*;
import net.kingsilk.qh.raffle.repo.*;
import net.kingsilk.qh.raffle.server.resource.raffleApp.record.convert.RecordConvert;
import net.kingsilk.qh.raffle.service.CommonService;
import net.kingsilk.qh.raffle.util.DbUtil;
import net.kingsilk.qh.raffle.util.ParamUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class RecordResource implements RaffleRecordApi {

    @Autowired
    private RaffleRecordRepo recordRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private RaffleAwardRepo raffleAwardRepo;

    @Autowired
    private RecordConvert recordConvert;

    @Autowired
    private UniPageRespConverter uniPageRespConverter;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private DeliverInvoiceRepo deliverInvoiceRepo;

    @Autowired
    private RaffleRecordRepo raffleRecordRepo;

    @Autowired
    private LogisticsRepo logisticsRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private CommonService commonService;

    @Override
    public UniResp<UniPageResp<RaffleRecordMinResp>> page(String raffleAppId, String raffleId, RaffleRecordReq pageReq) {

        Assert.notNull(raffleAppId, "raffleAppId为空");
        Assert.notNull(raffleId, "raffleId为空");
        UniResp<UniPageResp<RaffleRecordMinResp>> uniResp = new UniResp<>();
        LinkedList sort = pageReq != null && !pageReq.getSort().isEmpty() ? Lists.newLinkedList(ParamUtils.toSort(pageReq.getSort())) : Lists.newLinkedList(ParamUtils.toSort("dateCreated,desc"));
        Pageable pageable = new PageRequest(pageReq.getPage(), pageReq.getSize(), new Sort((Sort.Order) sort.get(0)));
//        String userId = null;
//        if (!StringUtils.isEmpty(pageReq.getKeyWord())) {
//            net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> pageUniResp =
//                    userApi.search(10, 0, null, pageReq.getKeyWord());
//            if (pageUniResp.getData().getContent().size() > 0) {
//                userId = pageUniResp.getData().getContent().get(0).getId();
//            } else {
//                uniResp.setStatus(200);
//                return uniResp;
//            }
//        }
        Page<RaffleRecord> pageData = recordRepo.findAll(allOf(
                QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                QRaffleRecord.raffleRecord.raffleId.eq(raffleId),
                QRaffleRecord.raffleRecord.deleted.ne(true),
                !StringUtils.isEmpty(pageReq.getHandleStatus()) ?
                        (pageReq.getHandleStatus().equals("all") ? DbUtil.opIn(QRaffleRecord.raffleRecord.handleStatus, Arrays.asList(RecordHandleStatusEnum.HANDLING, RecordHandleStatusEnum.HANDLED))
                                : QRaffleRecord.raffleRecord.handleStatus.eq(RecordHandleStatusEnum.valueOf(pageReq.getHandleStatus())))
                        : null,
                !StringUtils.isEmpty(pageReq.getKeyWord()) ?
                        QRaffleRecord.raffleRecord.phone.like( "%"+pageReq.getKeyWord()+"%" ) : null
        ), pageable);

        if (pageData == null || pageData.getContent() == null) {
            uniResp.setStatus(ErrStatus.OK);
            uniResp.setMessage("没有匹配的数据");
            return uniResp;
        }
        UniPageResp pageResp = uniPageRespConverter.convert(pageData);
        pageData.getContent().forEach(it -> pageResp.getContent().add(recordConvert.recordMinResp(it)));
        uniResp.setData(pageResp);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("success");
        return uniResp;
    }

    @Override
    public UniResp<RaffleRecordDetail> detail(String raffleAppId, String raffleId, String id) {
        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(id),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );

        if (record == null || !StringUtils.hasText(record.getAwardId())) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "找不到商品");

        }
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
        AwardInfo awardInfo = conversionService.convert(award, AwardInfo.class);
        RaffleRecordDetail resp = new RaffleRecordDetail();
        resp.setMemo(record.getMemo());
        resp.setPhone(record.getPhone());
        resp.setDrawType(record.getDrawType());
        resp.setHandleStatus(record.getHandleStatus());
        resp.setName(record.getNickName());
        resp.getAwardInfoList().add(awardInfo);
        resp.setLogisticsCompany(record.getLogisticsCompany());
        resp.setExpressNo(record.getExpressNo());
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
    public UniResp<String> ship(String raffleAppId, String raffleId, String id, DeliverInvoiceShipReq deliverInvoiceShipReq) {

        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(id),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );
        DeliverInvoice deliverInvoice = deliverInvoiceRepo.findOne(QDeliverInvoice.deliverInvoice.recordId.eq(id));
        if (deliverInvoice == null) {
            deliverInvoice = new DeliverInvoice();
        }
        DeliverInvoice.OrderAddress orderAddress = deliverInvoice.new OrderAddress();
        if (record.getAddr() != null) {
            orderAddress.setAdc(record.getAddr().getAdc());
            orderAddress.setPhone(record.getAddr().getPhone());
            orderAddress.setReceiver(record.getAddr().getReceiver());
            orderAddress.setStreet(record.getAddr().getStreet());
            deliverInvoice.setReceiverAddr(orderAddress);
            deliverInvoice.setDeliverStatus(DeliverStatusEnum.UNRECEIVED);
            deliverInvoice.setRecordId(record.getId());
            deliverInvoice.setRaffleAppId(record.getRaffleAppId());
            deliverInvoice.setRecordId(record.getRaffleId());
            deliverInvoice.setSeq(commonService.getDateString());
            deliverInvoice.setDateCreated(new Date());
            deliverInvoiceRepo.save(deliverInvoice);
        }
        Logistics logistics = null;
        if (!StringUtils.isEmpty(deliverInvoice.getLogisticsesId())) {
            logistics = logisticsRepo.findOne(deliverInvoice.getLogisticsesId());
        }
        if (logistics == null) {
            logistics = new Logistics();
        }
        if (deliverInvoiceShipReq != null) {
            logistics.setCompany(deliverInvoiceShipReq.getCompany());
            logistics.setExpressNo(deliverInvoiceShipReq.getExpressNo());
            logisticsRepo.save(logistics);
            if (record.getAddr() != null) {
                deliverInvoice.setLogisticsesId(logistics.getId());
                deliverInvoiceRepo.save(deliverInvoice);
            }
            record.setLogisticsCompany(deliverInvoiceShipReq.getCompany());
            record.setLogisticsId(logistics.getId());
            record.setExpressNo(deliverInvoiceShipReq.getExpressNo());
        }

        record.setSellerMemo(deliverInvoiceShipReq.getMemo());
        record.setHandleStatus(RecordHandleStatusEnum.HANDLED);
        record.setAccept(true);
        UniResp<String> uniResp = new UniResp<>();
        raffleRecordRepo.save(record);
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }
}
