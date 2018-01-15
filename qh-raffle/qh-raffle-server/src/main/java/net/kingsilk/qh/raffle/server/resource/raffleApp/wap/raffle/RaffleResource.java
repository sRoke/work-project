package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.raffle;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.api.common.ErrStatusException;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.raffle.RaffleApi;
import net.kingsilk.qh.raffle.core.RaffleStatusEnum;
import net.kingsilk.qh.raffle.core.RecordHandleStatusEnum;
import net.kingsilk.qh.raffle.domain.*;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.repo.RaffleRecordRepo;
import net.kingsilk.qh.raffle.repo.RaffleRepo;
import net.kingsilk.qh.raffle.repo.UserTicketsRepo;
import net.kingsilk.qh.raffle.server.resource.raffleApp.award.convert.AwardRespConvert;
import net.kingsilk.qh.raffle.server.resource.raffleApp.raffle.convert.RaffleConvert;
import net.kingsilk.qh.raffle.service.*;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("raffleWapResource")
@Scope("prototype")
public class RaffleResource implements RaffleApi {

    @Autowired
    private RaffleRepo raffleRepo;

    @Autowired
    private RaffleRecordRepo recordRepo;
    @Autowired
    private SecService secService;

    @Autowired
    private UserTicketsRepo ticketsRepo;

    @Autowired
    private RaffleRecordRepo raffleRecordRepo;

    @Autowired
    private RaffleConvert raffleConvert;

    @Autowired
    private RaffleAppService raffleAppService;

    @Autowired
    private RaffleRecordService recordService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private RaffleAwardRepo awardRepo;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserService userService;

    @Autowired
    private AwardRespConvert awardRespConvert;

    @Override
    public UniResp<RaffleInfo> info(String raffleAppId, String id, String openId) {

//        Assert.notNull(raffleAppId, "raffleAppId为空");
//        Assert.notNull(id, "id为空");

        UniResp<RaffleInfo> uniResp = new UniResp<>();
        String curUserId = null;
        if (StringUtils.hasText(openId)) {
            curUserId = userService.getUserIdByOpenId(openId, raffleAppId);
        }
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
//
//        UserTickets tickets = ticketsRepo.findOne(allOf(
//                QUserTickets.userTickets.userId.eq(curUserId),
//                QUserTickets.userTickets.raffleId.eq(id),
//                QUserTickets.userTickets.raffleAppId.eq(raffleAppId)
//        ));
//        if (tickets == null) {
//            UserTickets newTickets = new UserTickets();
//            newTickets.setraffleAppId(raffleAppId);
//            newTickets.setRaffleId(id);
//            newTickets.setShareTotalTicket(0);
//            newTickets.setSurplusTicket(raffle.getFreeCount());
//            newTickets.setUserId(curUserId);
//            ticketsRepo.save(newTickets);
//            tickets = newTickets;
//        }
        RaffleInfo raffleInfo = new RaffleInfo();
        raffleInfo.setDialImg(raffle.getDialImg());
        if (!StringUtils.hasText(curUserId)) {
            raffleInfo.setSurplus(raffle.getFreeCount());
        } else { //已登录
            UserTickets userTickets = ticketsRepo.findOne(allOf(
                    QUserTickets.userTickets.raffleAppId.eq(raffleAppId),
                    QUserTickets.userTickets.raffleId.eq(id),
                    QUserTickets.userTickets.userId.eq(curUserId)
            ));
            if (userTickets == null) {
                UserTickets newTickets = new UserTickets();
                newTickets.setraffleAppId(raffleAppId);
                newTickets.setRaffleId(id);
                newTickets.setSurplusTicket(raffle.getFreeCount());
                newTickets.setUserId(curUserId);
//                ticketsRepo.save(newTickets);
                raffleInfo.setSurplus(raffle.getFreeCount());
                raffleInfo.setCanShare(newTickets.getShareTotalTicket() < raffle.getLotteryCount() - raffle.getFreeCount());
                raffleInfo.setUsedTotalTicket(newTickets.getUsedTotalTicket());
            } else {
                raffleInfo.setSurplus(userTickets.getSurplusTicket());
                raffleInfo.setCanShare(userTickets.getShareTotalTicket() < raffle.getLotteryCount() - raffle.getFreeCount());
                raffleInfo.setUsedTotalTicket(userTickets.getUsedTotalTicket());
            }
        }
        raffleConvert.respInfoConvert(raffle, raffleInfo, null);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(raffleInfo);
        return uniResp;
    }

    @Override
    public UniResp<String> addTickets(String raffleAppId, String id, String openId) {

        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);

        UserTickets tickets = ticketsRepo.findOne(allOf(
                QUserTickets.userTickets.userId.eq(curUserId),
                QUserTickets.userTickets.raffleId.eq(id),
                QUserTickets.userTickets.raffleAppId.eq(raffleAppId)
        ));

        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(id),
                QRaffle.raffle.deleted.ne(true)
        ));

        if (tickets == null) {
            UserTickets newTickets = new UserTickets();
            newTickets.setraffleAppId(raffleAppId);
            newTickets.setRaffleId(id);
            newTickets.setSurplusTicket(raffle.getFreeCount());
            newTickets.setUserId(curUserId);
            tickets = newTickets;
        }

        if (tickets.getShareTotalTicket() < raffle.getLotteryCount() - raffle.getFreeCount()) {
            tickets.setShareTotalTicket(Optional.ofNullable(tickets.getShareTotalTicket()).orElse(0) + raffle.getShareCount());
            tickets.setSurplusTicket(tickets.getSurplusTicket() + raffle.getShareCount());
            ticketsRepo.save(tickets);
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("加票成功");
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<String> isForceFollow(
            String raffleAppId,
            String raffleId,
            String recordId,
            String openId,
            String shareUrl) {
        String curUserId = userService.getUserIdByOpenId(openId, raffleAppId);

        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(raffleId),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (raffle == null) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "没有找到活动");
        }

//        if (!RaffleStatusEnum.ENABLE.equals(raffle.getRaffleStatus()) || raffle.getEndTime().before(new Date())) {
//            throw new ErrStatusException(ErrStatus.FOUNDNULL, "活动已经过时");
//        } else if (raffle.getBeginTime().after(new Date())) {
//            throw new ErrStatusException(ErrStatus.FOUNDNULL, "活动还没开始");
//        }

        UniResp<String> uniResp = new UniResp<>();
        //判断用户是否需要先关注微信公众号并创建二维码

        Boolean isFollow = raffle.getMustFollow();
        if (isFollow) {
            Map<String, String> result = recordService.forceFollow(raffleAppId, raffleId, recordId, curUserId, shareUrl);
            if (result.get("int").equals(String.valueOf(ErrStatus.WXUSER_404))) {
                uniResp.setStatus(ErrStatus.WXUSER_404);
                uniResp.setData("请重新登录");
                return uniResp;
            } else if (result.get("int").equals("0")) {
                uniResp.setStatus(ErrStatus.FOLLOWWXMP);
                uniResp.setData(result.get("url"));
                return uniResp;
            } else {
                uniResp.setStatus(HttpStatus.SC_OK);
                uniResp.setData("true");

            }
        } else {
            uniResp.setData("");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
        }

        return uniResp;
    }

    @Override
    public UniResp<AwardInfo> lottery(String raffleAppId, String id) {

        String userId = secService.curUserId();
        UniResp<AwardInfo> uniResp = new UniResp<>();
        Raffle raffle = raffleRepo.findOne(allOf(
                QRaffle.raffle.raffleAppId.eq(raffleAppId),
                QRaffle.raffle.id.eq(id),
                QRaffle.raffle.deleted.ne(true)
        ));
        if (raffle == null) {
            uniResp.setMessage("抽奖活动为空错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        if (!RaffleStatusEnum.ENABLE.equals(raffle.getRaffleStatus())) {
            uniResp.setMessage("该活动已被禁用");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        Date date = new Date();
        long curTime = date.getTime();
        if (curTime > raffle.getEndTime().getTime() || curTime < raffle.getBeginTime().getTime()) {
            uniResp.setMessage("抽奖活动不再活动时间范围");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        UserTickets tickets = ticketsRepo.findOne(allOf(
                QUserTickets.userTickets.userId.eq(userId),
                QUserTickets.userTickets.raffleId.eq(id),
                QUserTickets.userTickets.raffleAppId.eq(raffleAppId)
        ));
        if (tickets == null) {
            UserTickets newTickets = new UserTickets();
            newTickets.setraffleAppId(raffleAppId);
            newTickets.setRaffleId(id);
            newTickets.setSurplusTicket(raffle.getFreeCount());
            newTickets.setShareTotalTicket(0);
            newTickets.setUsedTotalTicket(0);
            newTickets.setUserId(userId);
            ticketsRepo.save(newTickets);

            tickets = newTickets;
        }
        if (tickets.getSurplusTicket() <= 0) {
            uniResp.setMessage("亲，你的抽奖次数不够了．");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (tickets.getUsedTotalTicket() >= raffle.getLotteryCount()) {
            uniResp.setMessage("亲，你抽奖的次数不能超出规定的总次数．");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }

        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        LinkedHashMap<String, AtomicInteger> awardMap = new LinkedHashMap<>();

        if (!lotteryService.isRegister(id)) {
            for (String awardId : raffle.getAwardIds()) {
                RaffleAward award = awardRepo.findOne(awardId);
                if (award != null && award.getNum() != null && award.getNum() > 0) { //构造概率集合时，将没有奖品的奖项剔除
                    map.put(awardId, award.getChance());
                    awardMap.put(awardId, new AtomicInteger(award.getNum()));
                }
            }
            if (map == null || map.size() == 0) {
                uniResp.setMessage("亲．来晚了．奖品被抽完了．．");
                uniResp.setStatus(ErrStatus.ACTIVITYERROR);
                return uniResp;
            }
            lotteryService.register(id, map, awardMap);
        }
        AwardInfo awardInfo = lotteryService.execution(id);
        String randomAwardId = awardInfo.getRecordId();
        Integer awardInfoNum = awardInfo.getNum();
//        for (String awardId : raffle.getAwardIds()) {`
//            map = new HashMap<>();
//            RaffleAward award = awardRepo.findOne(awardId);
//            if (award != null && award.getNum() > 0) { //构造概率集合时，将没有奖品的奖项剔除
//                map.put(award.getId(), award.getChance());
//            }
//        }

//        LotteryChanceService lottery = new LotteryChanceService(map);
//        String randomAwardId = lottery.randomColunmIndex();

        RaffleAward randomAward = awardRepo.findOne(awardInfo.getRecordId());
        randomAward.setNum(awardInfoNum);
        awardRepo.save(randomAward);

        if (awardInfoNum <= 0) {
            raffle.getAwardIds().remove(randomAward);
            raffleRepo.save(raffle);
        }

        String brandAppId = raffleAppService.getBrandAppId(raffleAppId);
        boolean isNew = Lists.newArrayList(raffleRecordRepo.findAll(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.userId.eq(userId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(id)
                )
        )).isEmpty();

        if (isNew) {
            raffle.setJoins(Optional.ofNullable(raffle.getJoins()).orElse(0) + 1);
            raffleRepo.save(raffle);
        }
        //投票记录
        RaffleRecord record = new RaffleRecord();
        record.setAwardId(randomAwardId);
//        record.setAccept(randomAward.get);
        record.setAwardName(randomAward.getName());
        record.setRaffleAppId(raffleAppId);
        record.setHandleStatus(RecordHandleStatusEnum.HANDLING);
        record.setAccept(false);
        record.setDrawType(raffle.getDrawType());
        record.setRaffleId(id);
        record.setUserId(userId);

        Map<String, String> mapInfo = userService.getNickName(brandAppId, userId);

        record.setNickName(mapInfo.get("nickName"));
        record.setAvatar(mapInfo.get("avatar"));
        record.setPhone(mapInfo.get("phone"));
        recordRepo.save(record);

        //减掉剩余的票数
        tickets.setSurplusTicket(tickets.getSurplusTicket() - 1);
        tickets.setUsedTotalTicket(Optional.ofNullable(tickets.getUsedTotalTicket()).orElse(0) + 1);
        ticketsRepo.save(tickets);

        AwardInfo awardInfo1 = awardRespConvert.convert(randomAward);
        awardInfo1.setRecordId(record.getId());

        uniResp.setData(awardInfo1);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<Boolean> judgeOpenId(String raffleAppId, String openId) {
        String brandAppId = raffleAppService.getBrandAppId(raffleAppId);
        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> info = brandAppApi.info(brandAppId);
        String wxMpId = info.getData().getWxMpId();
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp.WxUser> wxuserByOpenId = userApi.getWxuserByOpenId(openId, wxMpId);
        String appId = wxuserByOpenId.getData().getAppId();
        UniResp<Boolean> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(wxMpId.equals(appId));
        return uniResp;
    }
}
