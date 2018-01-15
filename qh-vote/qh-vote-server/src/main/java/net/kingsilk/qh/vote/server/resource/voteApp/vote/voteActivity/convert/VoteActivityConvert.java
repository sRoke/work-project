package net.kingsilk.qh.vote.server.resource.voteApp.vote.voteActivity.convert;

import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.vote.core.vote.VoteStatusEnum;
import net.kingsilk.qh.vote.domain.VoteActivity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class VoteActivityConvert {

    public VoteActivity reqConvert(VoteActivityReq voteActivityReq) {
        if (voteActivityReq == null) {
            return null;
        }

        VoteActivity voteActivity = new VoteActivity();

        voteActivity.setVoteActivityName(voteActivityReq.getVoteName());


        if (voteActivityReq.getSignUpEndTime() != null) {
            voteActivity.setSignUpEndTime(voteActivityReq.getSignUpEndTime());
        }
        if (voteActivityReq.getSignUpStartTime() != null) {
            voteActivity.setSignUpStartTime(voteActivityReq.getSignUpStartTime());
        }
        if (voteActivityReq.getVoteStartTime() != null) {
            voteActivity.setVoteStartTime(voteActivityReq.getVoteStartTime());
        }
        if (voteActivityReq.getVoteEndTime() != null) {
            voteActivity.setVoteEndTime(voteActivityReq.getVoteEndTime());
        }

        if (voteActivityReq.getMaxVotePerDay() != null) {
            voteActivity.setMaxVotePerDay(voteActivityReq.getMaxVotePerDay());
        }
        if (voteActivityReq.getVotePeoplePerDay() != null) {
            voteActivity.setVotePeoplePerDay(voteActivityReq.getVotePeoplePerDay());
        }
        if (voteActivityReq.getTotalVoteCount() != null) {
            voteActivity.setTotalVoteCount(voteActivityReq.getTotalVoteCount());
        }
        if (voteActivityReq.isForceFollow() != null) {
            voteActivity.setForceFollow(voteActivityReq.isForceFollow());
        }
        if (voteActivityReq.isForcePhone() != null) {
            voteActivity.setForcePhone(voteActivityReq.isForcePhone());
        }
        if (voteActivityReq.getPrimaryImgUrl() != null) {
            voteActivity.setPrimaryImgUrl(voteActivityReq.getPrimaryImgUrl());
        }
        if (voteActivityReq.getDesp() != null) {
            voteActivity.setDesp(voteActivityReq.getDesp());
        }
        if (voteActivityReq.getRule() != null) {
            voteActivity.setRule(voteActivityReq.getRule());
        }
        if (voteActivityReq.getShareContent() != null) {
            voteActivity.setShareContent(voteActivityReq.getShareContent());
        }
        if (voteActivityReq.getShareTitle() != null) {
            voteActivity.setShareTitle(voteActivityReq.getShareTitle());
        }
        if (voteActivityReq.getVoteStatusEnum() != null) {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.valueOf(voteActivityReq.getVoteStatusEnum()));
        }
        if (voteActivityReq.getTotalVote() != null) {
            voteActivity.setTotalVote(voteActivityReq.getTotalVote());
        }
        if (voteActivityReq.getTotalVisit() != null) {
            voteActivity.setTotalVisit(voteActivityReq.getTotalVisit());
        }
        if (voteActivityReq.getWordsOfThanks() != null) {
            voteActivity.setWordsOfThanks(voteActivityReq.getWordsOfThanks());
        }
        if (voteActivityReq.getTotalVoteCount() != null) {
            voteActivity.setTotalVoteCount(voteActivityReq.getTotalVoteCount());
        }
        if (voteActivityReq.getVotePeoplePerDay() != null) {
            voteActivity.setVotePeoplePerDay(voteActivityReq.getVotePeoplePerDay());
        }
        if (voteActivityReq.getVoteStatusEnum() != null) {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.valueOf(voteActivityReq.getVoteStatusEnum()));
        }
        if (voteActivityReq.isForcePhone() != null) {
            voteActivity.setForcePhone(voteActivityReq.isForcePhone());
        }
        if (voteActivityReq.isForceFollow() != null) {
            voteActivity.setForceFollow(voteActivityReq.isForceFollow());
        }
        if (voteActivityReq.getMaxTicketPerDay() != null) {
            voteActivity.setMaxTicketPerDay(voteActivityReq.getMaxTicketPerDay());
        }
        if (voteActivityReq.getMustCheck() != null) {
            voteActivity.setMustCheck(voteActivityReq.getMustCheck());
        }

        return voteActivity;
    }

    public VoteActivityPageResp pageConvert(VoteActivity voteActivity) {
        if (voteActivity == null) {
            return null;
        }

        VoteActivityPageResp voteActivityPageResp = new VoteActivityPageResp();
        voteActivityPageResp.setId(voteActivity.getId());
        voteActivityPageResp.setVoteName(voteActivity.getVoteActivityName());
        voteActivityPageResp.setVoteStatusEnum(voteActivity.getVoteStatusEnum());
        voteActivityPageResp.setTotalJoinPeople(voteActivity.getTotalJoinPeople() + "");
        voteActivityPageResp.setTotalVisit(voteActivity.getTotalVisit() + "");
        voteActivityPageResp.setTotalVote(voteActivity.getTotalVote() + "");

        voteActivityPageResp.setPrimaryImgUrl(voteActivity.getPrimaryImgUrl());
        voteActivityPageResp.setShareContent(voteActivity.getShareContent());
        voteActivityPageResp.setShareTitle(voteActivity.getShareTitle());
        voteActivityPageResp.setRule(voteActivity.getRule());
        voteActivityPageResp.setDesp(voteActivity.getDesp());

        voteActivityPageResp.setVoteStartTime(voteActivity.getVoteStartTime());
        voteActivityPageResp.setVoteEndTime(voteActivity.getVoteEndTime());
        voteActivityPageResp.setSignUpStartTime(voteActivity.getSignUpStartTime());
        voteActivityPageResp.setSignUpEndTime(voteActivity.getSignUpEndTime());
        voteActivityPageResp.setCreatTime(voteActivity.getDateCreated());

        voteActivityPageResp.setMaxTicketPerDay(voteActivity.getMaxTicketPerDay());
        voteActivityPageResp.setMaxVotePerDay(voteActivity.getMaxVotePerDay());
        voteActivityPageResp.setTotalVoteCount(voteActivity.getTotalVoteCount());
        voteActivityPageResp.setVotePeoplePerDay(voteActivity.getVotePeoplePerDay());

        voteActivityPageResp.setForcePhone(voteActivity.isForcePhone());
        voteActivityPageResp.setForceFollow(voteActivity.isForceFollow());
        voteActivityPageResp.setWordsOfThanks(voteActivity.getWordsOfThanks());
        voteActivityPageResp.setMustCheck(voteActivity.getMustCheck());

        Date now = new Date();
        if (voteActivity.getSignUpStartTime().after(now)) {
            voteActivityPageResp.setVoteStatus("未开始");
        } else if (voteActivity.getVoteEndTime().before(now)) {
            voteActivityPageResp.setVoteStatus("已结束");
        }


        return voteActivityPageResp;
    }

}
