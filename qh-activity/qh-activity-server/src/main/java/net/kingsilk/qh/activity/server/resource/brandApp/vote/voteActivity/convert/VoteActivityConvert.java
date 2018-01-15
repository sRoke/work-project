package net.kingsilk.qh.activity.server.resource.brandApp.vote.voteActivity.convert;

import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.activity.domain.VoteActivity;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Component
public class VoteActivityConvert {

    public VoteActivity reqConvert(VoteActivityReq voteActivityReq) {
        if (voteActivityReq == null) {
            return null;
        }

        VoteActivity voteActivity = new VoteActivity();

        voteActivity.setActivityName(voteActivityReq.getActivityName());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (voteActivityReq.getSignUpEndTime() != null) {
                voteActivity.setSignUpEndTime(sdf.parse(voteActivityReq.getSignUpEndTime()));
            }
            if (voteActivityReq.getSignUpStartTime() != null) {
                voteActivity.setSignUpStartTime(sdf.parse(voteActivityReq.getSignUpStartTime()));
            }
            if (voteActivityReq.getVoteStartTime() != null) {
                voteActivity.setVoteStartTime(sdf.parse(voteActivityReq.getVoteStartTime()));
            }
            if (voteActivityReq.getVoteEndTime() != null) {
                voteActivity.setVoteEndTime(sdf.parse(voteActivityReq.getVoteEndTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
            voteActivity.setVoteStatusEnum(voteActivityReq.getVoteStatusEnum());
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
            voteActivity.setVoteStatusEnum(voteActivityReq.getVoteStatusEnum());
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
        voteActivityPageResp.setActivityName(voteActivity.getActivityName());
        voteActivityPageResp.setVoteStatusEnum(voteActivity.getVoteStatusEnum());
        voteActivityPageResp.setTotalJoinPeople(voteActivity.getTotalJoinPeople() + "");
        voteActivityPageResp.setTotalVisit(voteActivity.getTotalVisit() + "");
        voteActivityPageResp.setTotalVote(voteActivity.getTotalVote() + "");

        voteActivityPageResp.setPrimaryImgUrl(voteActivity.getPrimaryImgUrl());
        voteActivityPageResp.setShareContent(voteActivity.getShareContent());
        voteActivityPageResp.setShareTitle(voteActivity.getShareTitle());
        voteActivityPageResp.setRule(voteActivity.getRule());
        voteActivityPageResp.setDesp(voteActivity.getDesp());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        voteActivityPageResp.setVoteStartTime(format.format(voteActivity.getVoteStartTime()));
        voteActivityPageResp.setVoteEndTime(format.format(voteActivity.getVoteEndTime()));
        voteActivityPageResp.setSignUpStartTime(format.format(voteActivity.getSignUpStartTime()));
        voteActivityPageResp.setSignUpEndTime(format.format(voteActivity.getSignUpEndTime()));
        voteActivityPageResp.setCreatTime(format.format(voteActivity.getDateCreated()));

        voteActivityPageResp.setMaxTicketPerDay(voteActivity.getMaxTicketPerDay());
        voteActivityPageResp.setMaxVotePerDay(voteActivity.getMaxVotePerDay());
        voteActivityPageResp.setTotalVoteCount(voteActivity.getTotalVoteCount());
        voteActivityPageResp.setVotePeoplePerDay(voteActivity.getVotePeoplePerDay());

        voteActivityPageResp.setForcePhone(voteActivity.isForcePhone());
        voteActivityPageResp.setForceFollow(voteActivity.isForceFollow());
        voteActivityPageResp.setWordsOfThanks(voteActivity.getWordsOfThanks());
        voteActivityPageResp.setMustCheck(voteActivity.getMustCheck());

        return voteActivityPageResp;
    }

}
