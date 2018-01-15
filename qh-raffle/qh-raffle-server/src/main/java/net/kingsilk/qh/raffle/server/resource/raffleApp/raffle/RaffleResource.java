package net.kingsilk.qh.raffle.server.resource.raffleApp.raffle;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.raffle.api.common.*;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.RaffleApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaddleInfoAdmin;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleMinResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RafflePageReq;
import net.kingsilk.qh.raffle.core.RaffleStatusEnum;
import net.kingsilk.qh.raffle.domain.QRaffle;
import net.kingsilk.qh.raffle.domain.Raffle;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.repo.RaffleRepo;
import net.kingsilk.qh.raffle.server.resource.raffleApp.raffle.convert.RaffleConvert;
import net.kingsilk.qh.raffle.service.LotteryService;
import net.kingsilk.qh.raffle.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class RaffleResource implements RaffleApi {

    @Autowired
    private RaffleRepo raffleRepo;

    @Autowired
    private RaffleAwardRepo awardRepo;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private RaffleConvert raffleConvert;

    @Autowired
    private UniPageRespConverter uniPageRespConverter;

    @Override
    public UniResp<String> save(String raffleAppId, RaffleInfo req) {

//        try {
//            Assert.notNull(req, "参数不能为空");
//            Assert.hasText(req.getRaffleName(), "活动名称不能为空.");
//            Assert.hasText(req.getBeginTime().toString(), "活动开始时间 不能为空");
//            Assert.hasText(req.getEndTime().toString(), "活动结束时间 不能为空");
//            Assert.hasText(req.getBarcode(), "公众号二维码 不能为空");
//            Assert.hasText(req.getRaffleStatus().getCode(), "状态不能为空");
//            Assert.hasText(req.getLotteryCount().toString(), "最大抽奖次数不能为空");
//            RaffleStatusEnum status = RaffleStatusEnum.valueOf(req.getRaffleStatus().getCode());
//            String raffleName = req.getRaffleName();
//            Assert.notNull(req.getShareCount(), "分享最大次数不能为空");
//            Assert.hasText(req.getShareTitle(), "分享标题不能为空");
//            Assert.hasText(req.getShareDesc(), "分享描述不能为空");
//            Assert.notNull(req.getFreeCount(), "免费次数只能为数字，必填项");
//        } catch (Exception e) {
//            throw new ErrStatusException(ErrStatus.FOUNDNULL, "参数出错");
//        }

        req.getBeginTime().setHours(0);
        req.getBeginTime().setMinutes(0);
        req.getBeginTime().setSeconds(0);
        req.getEndTime().setDate(req.getEndTime().getDate());
        req.getEndTime().setHours(23);
        req.getEndTime().setMinutes(59);
        req.getEndTime().setSeconds(59);
        Raffle raffle = new Raffle();
        raffle.setRaffleName(req.getRaffleName());
        raffle.setFreeCount(req.getFreeCount());
        raffle.setLotteryCount(req.getLotteryCount());
        raffle.setDesp(req.getDesp());
        raffle.setDrawType(req.getDrawType());
        raffle.setRaffleStatus(Optional.ofNullable(req.getRaffleStatus()).orElse(RaffleStatusEnum.ENABLE));
        raffle.setShareTitle(req.getShareTitle());
        raffle.setShareDesc(req.getShareDesp());
        raffle.setBeginTime(req.getBeginTime());
        raffle.setDialImg(req.getDialImg());
        raffle.setShareCount(req.getShareCount());
        raffle.setRule(req.getRule());
        raffle.setEndTime(req.getEndTime());
        raffle.setRaffleAppId(raffleAppId);
        raffle.setMustFollow(req.getMustFollow());

        List<AwardInfo> awards = req.getAwards();

        //检查奖项
        double totalChance = 0;
        for (AwardInfo awardInfo : awards) {
            try {
                Assert.hasText(awardInfo.getName(), "奖品名称为空");
                Assert.hasText(awardInfo.getNum().toString(), "奖品数量为空");
                Assert.isTrue(awardInfo.getNum() >= 0, "奖品数量不能为负数");
                Assert.hasText(awardInfo.getChance().toString(), "奖品概率为空");
                Assert.isTrue(awardInfo.getChance() >= 0, "奖品概率不能为负数");
                Assert.hasText(awardInfo.getPicture(), "请上传图片");
                totalChance += awardInfo.getChance();
            } catch (Exception e) {
                throw new ErrStatusException(ErrStatus.FOUNDNULL, "奖品参数错误");
            }
        }
        for (AwardInfo awardInfo : awards) {

            RaffleAward award = new RaffleAward();
            award.setSeqNum(awardInfo.getSeqNum());
            award.setName(awardInfo.getName());
            award.setNum(awardInfo.getNum());
            award.setChance(awardInfo.getChance());
            award.setPrompt(awardInfo.getPrompt());
            award.setPicture(awardInfo.getPicture());
            award.setRaffleId(raffle.getId());
            award.setAwardType(awardInfo.getAwardType());
            awardRepo.save(award);
            raffle.getAwardIds().add(award.getId());
        }

        raffleRepo.save(raffle);
        if (lotteryService.isRegister(raffle.getId())) {
            lotteryService.finish(raffle.getId());
        }
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        LinkedHashMap<String, AtomicInteger> awardMap = new LinkedHashMap<>();
        for (String awardId : raffle.getAwardIds()) {
            RaffleAward award = awardRepo.findOne(awardId);
            if (award != null && award.getNum() != null && award.getNum() > 0) {
                //构造概率集合时，将没有奖品的奖项剔除
                map.put(awardId, award.getChance());
                awardMap.put(awardId, new AtomicInteger(award.getNum()));
            }
        }
        lotteryService.register(raffle.getId(), map, awardMap);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<RaddleInfoAdmin> info(String raffleAppId, String id) {
        Assert.notNull(raffleAppId, "raffleAppId为空");
        Assert.notNull(id, "id为空");
        UniResp<RaddleInfoAdmin> uniResp = new UniResp<>();
        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(id),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (raffle == null) {
            uniResp.setMessage("活动错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        RaddleInfoAdmin raffleInfo = new RaddleInfoAdmin();
        raffleConvert.respInfoAdminNormalConvert(raffle, raffleInfo);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(raffleInfo);
        return uniResp;
    }

    @Override
    public UniResp<String> enable(String raffleAppId, String id) {
        Assert.notNull(raffleAppId, "raffleAppId为空");
        Assert.notNull(id, "id为空");
        UniResp<String> uniResp = new UniResp<>();
        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(id),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (raffle == null) {
            uniResp.setMessage("活动错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }

        if (!RaffleStatusEnum.ENABLE.equals(raffle.getRaffleStatus())) {
            raffle.setRaffleStatus(RaffleStatusEnum.ENABLE);
            if (lotteryService.isRegister(raffle.getId())) {
                lotteryService.finish(raffle.getId());
            }
            HashMap<String, Double> map = new HashMap<>();
            HashMap<String, AtomicInteger> awardMap = new HashMap<>();
            for (String awardId : raffle.getAwardIds()) {
                RaffleAward award = awardRepo.findOne(awardId);
                if (award != null && award.getNum() != null && award.getNum() > 0) {
                    //构造概率集合时，将没有奖品的奖项剔除
                    map.put(awardId, award.getChance());
                    awardMap.put(awardId, new AtomicInteger(award.getNum()));
                }
            }
            uniResp.setMessage("启用成功");
        } else {
            raffle.setRaffleStatus(RaffleStatusEnum.CLOSED);
            if (lotteryService.isRegister(raffle.getId())) {
                lotteryService.finish(raffle.getId());
            }
            uniResp.setMessage("关闭成功");
        }
        raffleRepo.save(raffle);

        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String raffleAppId, String id) {
        Assert.notNull(raffleAppId, "raffleAppId为空");
        Assert.notNull(id, "id为空");
        UniResp<String> uniResp = new UniResp<>();
        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(id),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (raffle == null) {
            uniResp.setMessage("活动错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        raffle.setDeleted(true);
        if (lotteryService.isRegister(raffle.getId())) {
            lotteryService.finish(raffle.getId());
        }
        raffleRepo.save(raffle);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("success");
        return uniResp;
    }

    @Override
    public UniResp<UniPageResp<RaffleMinResp>> page(String raffleAppId, RafflePageReq pageReq) {
        Assert.notNull(raffleAppId, "raffleAppId为空");
        UniResp<UniPageResp<RaffleMinResp>> uniResp = new UniResp<>();
        LinkedList sort = pageReq != null && !pageReq.getSort().isEmpty() ? Lists.newLinkedList(ParamUtils.toSort(pageReq.getSort())) : Lists.newLinkedList(ParamUtils.toSort("dateCreated,desc"));
        Pageable pageable = new PageRequest(pageReq.getPage(), pageReq.getSize(), new Sort((Sort.Order) sort.get(0)));
        BooleanExpression expression = null;
        if (pageReq.getStatus().equals("notStarted")) {
            expression = QRaffle.raffle.beginTime.after(new Date());
        } else if (pageReq.getStatus().equals("starting")) {
            expression =
                    Expressions.allOf(QRaffle.raffle.beginTime.before(new Date()),
                            QRaffle.raffle.endTime.after(new Date()));
        } else if (pageReq.getStatus().equals("end")) {
            expression = QRaffle.raffle.endTime.before(new Date());
        }

        Page<Raffle> pageData = raffleRepo.findAll(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.deleted.ne(true),
//                !StringUtils.isEmpty(pageReq.getStatus())
//                        ? ("all".equals(pageReq.getStatus())
//                        ? DbUtil.opIn(QRaffle.raffle.raffleStatus, Arrays.asList(RaffleStatusEnum.CLOSED, RaffleStatusEnum.EDITING, RaffleStatusEnum.ENABLE))
//                        : QRaffle.raffle.raffleStatus.eq(RaffleStatusEnum.valueOf(pageReq.getStatus())))
//                        : null,
//                pageReq.getStatus().equals("all") ?
//                        QRaffle.raffle.raffleStatus.ne(RaffleStatusEnum.CLOSED) : null,
                !StringUtils.isEmpty(pageReq.getKeyWord()) ?
                        QRaffle.raffle.raffleName.like("%" + pageReq.getKeyWord() + "%") : null,
                expression
        ), pageable);

        if (pageData == null || pageData.getContent() == null) {
            uniResp.setStatus(ErrStatus.OK);
            uniResp.setMessage("没有匹配的数据");
            return uniResp;
        }
        UniPageResp pageResp = uniPageRespConverter.convert(pageData);
        pageData.getContent().forEach(it -> {
            RaffleMinResp minResp = raffleConvert.respMinConvert(it);
            pageResp.getContent().add(minResp);
        });
        uniResp.setData(pageResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> update(String raffleAppId, String raffleId, RaffleInfo req) {

        try {
            Assert.notNull(req, "参数不能为空");
            Assert.hasText(req.getRaffleName(), "活动名称不能为空.");
            Assert.hasText(req.getBeginTime().toString(), "活动开始时间 不能为空");
            Assert.hasText(req.getEndTime().toString(), "活动结束时间 不能为空");
            Assert.hasText(req.getLotteryCount().toString(), "最大抽奖次数不能为空");
            Assert.notNull(req.getShareCount(), "分享最大次数不能为空");
            Assert.hasText(req.getShareTitle(), "分享标题不能为空");
            Assert.notNull(req.getFreeCount(), "免费次数只能为数字，必填项");
        } catch (Exception e) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "参数错误");
        }
        req.getBeginTime().setHours(0);
        req.getBeginTime().setMinutes(0);
        req.getBeginTime().setSeconds(0);
        req.getEndTime().setDate(req.getEndTime().getDate());
        req.getEndTime().setHours(23);
        req.getEndTime().setMinutes(59);
        req.getEndTime().setSeconds(59);
        Raffle raffle = raffleRepo.findOne(raffleId);
        raffle.setRaffleName(req.getRaffleName());
        raffle.setFreeCount(req.getFreeCount());
        raffle.setLotteryCount(req.getLotteryCount());
        raffle.setDesp(req.getDesp());
        raffle.setDrawType(req.getDrawType());
        raffle.setRaffleStatus(Optional.ofNullable(req.getRaffleStatus()).orElse(RaffleStatusEnum.ENABLE));
        raffle.setShareTitle(req.getShareTitle());
        raffle.setShareDesc(req.getShareDesp());
        raffle.setDialImg(req.getDialImg());
        raffle.setBeginTime(req.getBeginTime());
        raffle.setShareCount(req.getShareCount());
        raffle.setRule(req.getRule());
        raffle.setEndTime(req.getEndTime());
        raffle.setRaffleAppId(raffleAppId);
        raffle.setMustFollow(req.getMustFollow());

        List<AwardInfo> awards = req.getAwards();

        //检查奖项
        for (AwardInfo awardInfo : awards) {
            Assert.hasText(awardInfo.getName(), "奖品名称为空");
            Assert.hasText(awardInfo.getNum().toString(), "奖品数量为空");
            Assert.isTrue(awardInfo.getNum() >= 0, "不能没有奖品");
            Assert.hasText(awardInfo.getChance().toString(), "奖品概率为空");
            Assert.isTrue(awardInfo.getChance() >= 0, "奖品概率不能为负数");
            Assert.hasText(awardInfo.getPicture(), "请上传图片");
        }
        raffle.getAwardIds().clear();
        for (AwardInfo awardInfo : awards) {

            RaffleAward award = new RaffleAward();
            award.setSeqNum(awardInfo.getSeqNum());
//            award.setTitle(awardInfo.getTitle());
            award.setName(awardInfo.getName());
            award.setNum(awardInfo.getNum());
            award.setChance(awardInfo.getChance());
            award.setAwardType(awardInfo.getAwardType());
            award.setPicture(awardInfo.getPicture());
            award.setPrompt(awardInfo.getPrompt());
            award.setRaffleId(raffle.getId());

            awardRepo.save(award);
            raffle.getAwardIds().add(award.getId());
        }

        raffleRepo.save(raffle);

        if (lotteryService.isRegister(raffle.getId())) {
            lotteryService.finish(raffle.getId());
        }
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        LinkedHashMap<String, AtomicInteger> awardMap = new LinkedHashMap<>();
        for (String awardId : raffle.getAwardIds()) {
            RaffleAward award = awardRepo.findOne(awardId);
            if (award != null && award.getNum() != null && award.getNum() > 0) {
                //构造概率集合时，将没有奖品的奖项剔除
                map.put(awardId, award.getChance());
                awardMap.put(awardId, new AtomicInteger(award.getNum()));
            }
        }
        lotteryService.register(raffle.getId(), map, awardMap);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

}
