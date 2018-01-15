package net.kingsilk.qh.activity.client.impl;

import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.VoteWorksApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.*;
import net.kingsilk.qh.activity.client.AbstractQhPlatformApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VoteWordsApiImpl extends AbstractQhPlatformApi implements VoteWorksApi {

    @Override
    public UniResp<String> check(String brandAppId, String activityId, String id, WxSendReq wxSendReq) {
        return null;
    }

    @Override
    public UniResp<UniPage<VoteWorksResp>> page(String brandAppId, String activityId, int size, int page, List<String> sort, String keyWord) {
        return null;
    }

    @Override
    public UniResp<String> addVote(String brandAppId, String activityId, String id, String votes, String sourceType) {
        return null;
    }

    @Override
    public UniResp<VoteWorksResp> info(String brandAppId, String activityId, String id) {
        return null;
    }

    @Override
    public UniResp<Void> delete(String brandAppId, String activityId, String id) {
        return null;
    }

    @Override
    public UniResp<String> join(String brandAppId, String activityId, VoteworkReq voteworkReq) {
        return null;
    }

    @Override
    public UniResp<SignupWorkResp> isSignup(String brandAppId, String activityId) {
        return null;
    }

    @Override
    public UniResp<VoteShareResp> shareInfo(String brandAppId, String activityId, String id) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return null;
    }

    @Override
    public UniResp voteWorksToGridsf(String brandAppId, String activityId, List<String> sort, String workKeyword) throws IOException {
        return null;
    }

    @Override
    public UniResp exportVoteworks(String brandAppId, String activityId, String taskTypeEnum) throws IOException {
        return null;
    }
}
