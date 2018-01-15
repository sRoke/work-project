package net.kingsilk.qh.activity.client.impl;

import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.VoteRecordApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.RecordInfoResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteNotifyInfo;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteRecordPageResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteRecordResp;
import net.kingsilk.qh.activity.client.AbstractQhPlatformApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VoteRecordApiImpl extends AbstractQhPlatformApi implements VoteRecordApi {


    @Override
    public UniResp<String> vote(String brandAppId, String activityId, String id, String wxComAppId, String wxMpAppId, String curUserId) {
        return null;
    }

    @Override
    public UniResp<UniPage<VoteRecordResp>> get(String brandAppId, String activityId, String id, int size, int page, List<String> sort) {
        return null;
    }

    @Override
    public UniResp<UniPage<VoteRecordPageResp>> page(String brandAppId, String activityId, int size, int page, String voteKeyword, String workKeyword, List<String> sort) {
        return null;
    }

    @Override
    public UniResp<RecordInfoResp> voteNum(String brandAppId, String activityId, String id) {
        return null;
    }

    @Override
    public UniResp<VoteNotifyInfo> notify(String brandAppId, String activityId, String id, String voteId) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return null;
    }

    @Override
    public UniResp voteRecordsToGridfs(String brandAppId, String activityId, List<String> sort, String voteKeyword, String workKeyword) throws IOException {
        return null;
    }

    @Override
    public UniResp exportVoteRecords(String brandAppId, String activityId, String taskTypeEnum) throws IOException {
        return null;
    }
}
