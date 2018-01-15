package net.kingsilk.qh.vote.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.WxSendReq;
import net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.vote.domain.QVoteActivity;
import net.kingsilk.qh.vote.domain.QVoteWorks;
import net.kingsilk.qh.vote.domain.VoteActivity;
import net.kingsilk.qh.vote.domain.VoteWorks;
import net.kingsilk.qh.vote.repo.VoteActivityRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.KfMsgApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.TextMsgReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Service
public class VoteWorkCheckService {

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private KfMsgApi kfMsgApi;

    public String ckeckService(String voteAppId, String voteId, String id, WxSendReq wxSendReq) {

        Assert.notNull(voteAppId, "voteApp不能为空");
        Assert.notNull(id, "作品id不能为空");

        VoteWorks curvoteWorks = voteWorksRepo.findOne(id);
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(QVoteActivity.voteActivity.id.eq(voteId),
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId)));

        //做一些时间处理
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String endTime = simpleDateFormat.format(voteActivity.getVoteEndTime());
        String voteTime = simpleDateFormat.format(voteActivity.getVoteStartTime());

        //审核通过，做排名初始化。
        Iterable<VoteWorks> voteWorksAll = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.deleted.ne(true)
                ));
        int lenth = 0;
        for (VoteWorks voteWorks : voteWorksAll) {
            lenth++;
        }
        curvoteWorks.setRank(lenth + 1);

        if (wxSendReq.getStatus()) {
            curvoteWorks.setStatus(VoteWorksStatusEnum.NORMAL);
            //todo 消息推送 如果没有返回url 则不发信息
            if (!StringUtils.isEmpty(wxSendReq.getUrl())) {
                net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                        wxComMpAtApi.get(wxSendReq.getWxComAppId(), wxSendReq.getWxMpAppId());
                TextMsgReq req = new TextMsgReq();
                req.setMsgType("text");
                req.setToUser(curvoteWorks.getOpenId());
                TextMsgReq.Text text = new TextMsgReq.Text();
                text.setContent("您参加的投票活动" + voteActivity.getVoteActivityName() + "已审核通过,投票时间：" + voteTime
                        + "~" + endTime + "；<a href='" + wxSendReq.getUrl() + "'>点击查看</a>");
                req.setText(text);
                kfMsgApi.send(respUniResp.getData().getAccessToken(), req);
            }
            //统计参与人数 审核通过加一
            voteActivity.setTotalJoinPeople(voteActivity.getTotalJoinPeople() + 1);
            voteActivityRepo.save(voteActivity);
        } else {
            curvoteWorks.setStatus(VoteWorksStatusEnum.REJECT);
            curvoteWorks.setRank(Integer.MAX_VALUE);
        }
        voteWorksRepo.save(curvoteWorks);
        return curvoteWorks.getId();
    }

}
