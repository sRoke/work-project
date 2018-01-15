package net.kingsilk.qh.raffle.server.resource.raffleApp.raffle.convert;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaddleInfoAdmin;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.raffle.dto.RaffleMinResp;
import net.kingsilk.qh.raffle.core.RaffleStatusEnum;
import net.kingsilk.qh.raffle.domain.Raffle;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import net.kingsilk.qh.raffle.domain.UserTickets;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
public class RaffleConvert {

    @Autowired
    private RaffleAwardRepo awardRepo;

    public RaffleInfo respInfoConvert(Raffle raffle, RaffleInfo target, UserTickets tickets) {

        if (raffle == null) {
            return null;
        }
//        target.setBarcode(raffle.Barcode);
//        SimpleDateFormat format = new SimpleDateFormat();//TODO
        target.setBeginTime(raffle.getBeginTime());
        target.setEndTime(raffle.getEndTime());
        target.setRaffleAppId(raffle.getRaffleAppId());
        target.setDesp(raffle.getDesp());
        target.setRule(raffle.getRule());
//        target.setDialImg(raffle.getDialImg());
        target.setFreeCount(raffle.getFreeCount());
        target.setLotteryCount(raffle.getLotteryCount());
        target.setShareCount(raffle.getShareCount());
        target.setShareDesp(raffle.getShareDesc());
        target.setMustFollow(raffle.getMustFollow());
        target.setShareTitle(raffle.getShareTitle());
        target.setRaffleStatus(raffle.getRaffleStatus());
        target.setRaffleName(raffle.getRaffleName());

        if (tickets != null) {
            target.setCanShare(tickets.getShareTotalTicket() < raffle.getLotteryCount() - raffle.getFreeCount());
            target.setSurplus(tickets.getSurplusTicket());
            target.setUsedTotalTicket(tickets.getUsedTotalTicket());
        }

        raffle.getAwardIds().forEach(awardId -> {
            RaffleAward award = awardRepo.findOne(awardId);
            AwardInfo awardInfo = new AwardInfo();
            awardInfo.setSeqNum(award.getSeqNum());
            awardInfo.setChance(award.getChance());
            awardInfo.setName(award.getName());
            awardInfo.setNum(award.getNum());
            if (!award.getPicture().contains("imageView2")) {
                awardInfo.setPicture(award.getPicture() + "?imageView2/0/w/35/h/35/q/50");
            } else {
                awardInfo.setPicture(award.getPicture());
            }
            awardInfo.setPrompt(award.getPrompt());
            awardInfo.setAwardType(award.getAwardType());
            target.getAwards().add(awardInfo);
        });
        return target;
    }

    public RaffleInfo respInfoNormalConvert(Raffle raffle, RaffleInfo target) {

        if (raffle == null) {
            return null;
        }
//        target.setBarcode(raffle.Barcode);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        target.setBeginTime(raffle.getBeginTime());
        target.setEndTime(raffle.getEndTime());
        target.setRaffleAppId(raffle.getRaffleAppId());
        target.setDesp(raffle.getDesp());
        target.setRule(raffle.getRule());
//        target.setDialImg(raffle.getDialImg());
        target.setDrawType(raffle.getDrawType());
        target.setFreeCount(raffle.getFreeCount());
        target.setLotteryCount(raffle.getLotteryCount());
        target.setShareCount(raffle.getShareCount());
        target.setShareDesp(raffle.getShareDesc());
        target.setMustFollow(raffle.getMustFollow());
        target.setShareTitle(raffle.getShareTitle());
        target.setRaffleStatus(raffle.getRaffleStatus());
        target.setRaffleName(raffle.getRaffleName());

        raffle.getAwardIds().forEach(awardId -> {
            RaffleAward award = awardRepo.findOne(awardId);
            AwardInfo awardInfo = new AwardInfo();
            awardInfo.setSeqNum(award.getSeqNum());
            awardInfo.setChance(award.getChance());
            awardInfo.setName(award.getName());
            awardInfo.setNum(award.getNum());
            awardInfo.setPicture(award.getPicture());
            awardInfo.setPrompt(award.getPrompt());
            awardInfo.setAwardType(award.getAwardType());
            target.getAwards().add(awardInfo);
        });
        return target;
    }

    public RaddleInfoAdmin respInfoAdminNormalConvert(Raffle raffle, RaddleInfoAdmin target) {

        if (raffle == null) {
            return null;
        }
//        target.setBarcode(raffle.Barcode);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        target.setBeginTime(format.format(raffle.getBeginTime()));
        target.setEndTime(format.format(raffle.getEndTime()));
        target.setRaffleAppId(raffle.getRaffleAppId());
        target.setDesp(raffle.getDesp());
        target.setRule(raffle.getRule());
//        target.setDialImg(raffle.getDialImg());
        target.setDrawType(raffle.getDrawType());
        target.setFreeCount(raffle.getFreeCount());
        target.setLotteryCount(raffle.getLotteryCount());
        target.setShareCount(raffle.getShareCount());
        target.setShareDesp(raffle.getShareDesc());
        target.setMustFollow(raffle.getMustFollow());
        target.setDialImg(raffle.getDialImg());
        target.setShareTitle(raffle.getShareTitle());
        target.setRaffleStatus(raffle.getRaffleStatus());
        target.setRaffleName(raffle.getRaffleName());

        raffle.getAwardIds().forEach(awardId -> {
            RaffleAward award = awardRepo.findOne(awardId);
            AwardInfo awardInfo = new AwardInfo();
            awardInfo.setSeqNum(award.getSeqNum());
            awardInfo.setChance(award.getChance());
            awardInfo.setName(award.getName());
            awardInfo.setNum(award.getNum());
            awardInfo.setPicture(award.getPicture());
            awardInfo.setPrompt(award.getPrompt());
            awardInfo.setAwardType(award.getAwardType());
            target.getAwards().add(awardInfo);
        });
        return target;
    }

    public RaffleMinResp respMinConvert(Raffle raffle) {
        RaffleMinResp target = new RaffleMinResp();
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        if (raffle.getBeginTime() != null) {
            target.setBeginTime(sfd.format(raffle.getBeginTime()));
        }
        if (raffle.getEndTime() != null) {
            target.setEndTime(sfd.format(raffle.getEndTime()));
        }
        target.setDialImg(raffle.getDialImg());
        target.setShareDesc(raffle.getShareDesc());
        target.setShareTitle(raffle.getShareTitle());
        target.setRaffleAppid(raffle.getRaffleAppId());
        target.setId(raffle.getId());

        target.setJoins(Optional.ofNullable(raffle.getJoins()).orElse(0));
        target.setRaffleName(raffle.getRaffleName());
        target.setStatus(raffle.getRaffleStatus());
        if (RaffleStatusEnum.EDITING.equals(raffle.getRaffleStatus())) {
            target.setStatusDesp("未开始");
        }
        if (RaffleStatusEnum.ENABLE.equals(raffle.getRaffleStatus())) {
            target.setStatusDesp("进行中");
        }
        if (RaffleStatusEnum.CLOSED.equals(raffle.getRaffleStatus())) {
            target.setStatusDesp("已关闭");
        }
        return target;
    }
}
