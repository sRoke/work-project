package net.kingsilk.qh.vote.server.resource.voteApp.vote.voteActivity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.vote.api.ErrStatus;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.VoteActivityApi;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.vote.core.vote.VoteStatusEnum;
import net.kingsilk.qh.vote.domain.QVoteActivity;
import net.kingsilk.qh.vote.domain.VoteActivity;
import net.kingsilk.qh.vote.repo.VoteActivityRepo;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteActivity.convert.VoteActivityConvert;
import net.kingsilk.qh.vote.service.ParamUtils;
import net.kingsilk.qh.vote.service.service.VoteRecordService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class VoteActivityResource implements VoteActivityApi {

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private VoteActivityConvert voteActivityConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;


    @Autowired
    private VoteRecordService voteRecordService;

    @Override
    public UniResp<String> add(String voteAppId, VoteActivityReq voteActivityReq) {
        Assert.notNull(voteAppId, "voteAppId不能为空");
        UniResp<String> uniResp = new UniResp<>();
        Assert.notNull(voteActivityReq, "请求参数不能为空！");
        if (voteActivityReq.getVoteName() == null) {
            uniResp.setData("请输入活动名称");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getSignUpStartTime() == null) {
            uniResp.setData("请输入报名开始时间");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getSignUpEndTime() == null) {
            uniResp.setData("请输入报名结束时间");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getVoteStartTime() == null) {
            uniResp.setData("请输入投票开始时间");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getVoteEndTime() == null) {
            uniResp.setData("请输入投票结束时间");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getMaxVotePerDay() < 0) {
            uniResp.setData("请输入每人每天最大允许的投票次数");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getTotalVoteCount() < 0) {
            uniResp.setData("请输入从同一个投票人获取的最大票数");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        //todo 是否输手机号和是否关注公众号前端默认传false
        if (voteActivityReq.getVotePeoplePerDay() == null) {
            uniResp.setData("请输入限定可投票人数,-1为不限");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }

        if (voteActivityReq.isForcePhone() == null) {
            uniResp.setData("请输入是否强制手机号");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
        }

        if (voteActivityReq.isForceFollow() == null) {
            uniResp.setData("请输入是否强制关注公众号");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }

        if (voteActivityReq.getRule() == null) {
            uniResp.setData("请输入活动比赛规则");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }

//        if (voteActivityReq.getDesp() == null) {
//            uniResp.setData("请输入活动描述");
//            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
//            return uniResp;
//        }
        if (voteActivityReq.getPrimaryImgUrl() == null) {
            uniResp.setData("请输入活动主图");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getShareTitle() == null) {
            uniResp.setData("请输入分享标题");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
//        if (voteActivityReq.getShareContent() == null) {
//            uniResp.setData("请输入分享内容");
//            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
//            return uniResp;
//        }
        if (voteActivityReq.getMaxTicketPerDay() == null) {
            uniResp.setData("请输入参赛者能从同一个获取的最大票数");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }

        //传过来的都不可能为空了
        VoteActivity voteActivity = voteActivityConvert.reqConvert(voteActivityReq);
        if (voteActivityReq.getVoteStatusEnum() == null) {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.EDITING);    //若没有传过来状态，给它初始化
        }
        voteActivity.setVoteAppId(voteAppId);
        voteActivity.setTotalVote(0);  //添加活动统计数据的初始化
        voteActivity.setTotalJoinPeople(0);
        voteActivity.setTotalVisit(0);
        voteActivityRepo.save(voteActivity);
        uniResp.setData("success");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> update(String voteAppid, String id, VoteActivityReq voteActivityReq) {
        UniResp<String> uniResp = new UniResp<String>();

        if (StringUtils.isEmpty(voteAppid) || StringUtils.isEmpty(id)) {
            uniResp.setData("voteAppid 和 id 不能为空");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        if (voteActivityReq == null) {
            uniResp.setData("voteActivityReq为空");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        if (StringUtils.isEmpty(id)) {
            uniResp.setData("id为空");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }

        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppid),
                        QVoteActivity.voteActivity.id.eq(id)
                ));


        if (voteActivityReq.getSignUpStartTime() != null) {
            voteActivity.setSignUpStartTime(voteActivityReq.getSignUpStartTime());
        }
        if (voteActivityReq.getSignUpEndTime() != null) {
            voteActivity.setSignUpEndTime(voteActivityReq.getSignUpEndTime());
        }
        if (voteActivityReq.getVoteStartTime() != null) {
            voteActivity.setVoteStartTime(voteActivityReq.getVoteStartTime());
        }
        if (voteActivityReq.getVoteEndTime() != null) {
            voteActivity.setVoteEndTime(voteActivityReq.getVoteEndTime());
        }

        if (voteActivityReq.getVoteName() != null) {
            voteActivity.setVoteActivityName(voteActivityReq.getVoteName());
        }
        if (voteActivityReq.getMaxVotePerDay() != null && voteActivityReq.getMaxVotePerDay() >= 0) {
            voteActivity.setMaxVotePerDay(voteActivityReq.getMaxVotePerDay());
        }
        if (voteActivityReq.getMaxTicketPerDay() != null && voteActivityReq.getMaxTicketPerDay() >= 0) {
            voteActivity.setMaxTicketPerDay(voteActivityReq.getMaxTicketPerDay());
        }
        if (voteActivityReq.getTotalVoteCount() != null && voteActivityReq.getTotalVoteCount() >= 0) {
            voteActivity.setTotalVoteCount(voteActivityReq.getTotalVoteCount());
        }
        if (voteActivityReq.getVoteStatusEnum() != null) {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.valueOf(voteActivityReq.getVoteStatusEnum()));
        }
        if (voteActivityReq.getVotePeoplePerDay() != null) {
            voteActivity.setVotePeoplePerDay(voteActivityReq.getVotePeoplePerDay());
        }
        if (voteActivityReq.isForcePhone() != null) {
            voteActivity.setForcePhone(voteActivityReq.isForcePhone());
        }
        if (voteActivityReq.isForceFollow() != null) {
            voteActivity.setForceFollow(voteActivityReq.isForceFollow());
        }
        if (voteActivityReq.getDesp() != null) {
            voteActivity.setDesp(voteActivityReq.getDesp());
        }
        if (voteActivityReq.getRule() != null) {
            voteActivity.setRule(voteActivityReq.getRule());
        }
        if (voteActivityReq.getPrimaryImgUrl() != null) {
            voteActivity.setPrimaryImgUrl(voteActivityReq.getPrimaryImgUrl());
        }
        if (voteActivityReq.getShareTitle() != null) {
            voteActivity.setShareTitle(voteActivityReq.getShareTitle());
        }
        if (voteActivityReq.getShareContent() != null) {
            voteActivity.setShareContent(voteActivityReq.getShareContent());
        }
        if (voteActivityReq.getWordsOfThanks() != null) {
            voteActivity.setWordsOfThanks(voteActivityReq.getWordsOfThanks());
        }
        if (voteActivityReq.getMustCheck() != null) {
            voteActivity.setMustCheck(voteActivityReq.getMustCheck());
        }

        voteActivityRepo.save(voteActivity);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    public UniResp<Void> delete(String voteApp, String id) {
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteApp),
                        QVoteActivity.voteActivity.id.eq(id)));
        voteActivity.setDeleted(true);
        voteActivityRepo.save(voteActivity);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> changeStatus(String voteAppId, String id, Boolean enable) {

        VoteActivity voteActivity = voteActivityRepo.findOne(id);

        if (enable) {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.NORMAL);
        } else {
            voteActivity.setVoteStatusEnum(VoteStatusEnum.END);
        }
        voteActivityRepo.save(voteActivity);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    public UniResp<UniPage<VoteActivityPageResp>> page(
            String voteApp,
            int size,
            int page,
            List<String> sort,
            String status,
            String keyWord) {
        sort.add("dateCreated,desc");
        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(page, size, s);
        BooleanExpression expression = null;

        if (status.equals("notStarted")) {
            expression = QVoteActivity.voteActivity.signUpStartTime.after(new Date());
        } else if (status.equals("starting")) {
            expression =
                    Expressions.allOf(QVoteActivity.voteActivity.signUpStartTime.before(new Date()),
                            QVoteActivity.voteActivity.voteEndTime.after(new Date()));
        } else if (status.equals("end")) {
            expression = QVoteActivity.voteActivity.signUpEndTime.before(new Date());
        }

        //todo 三种时间筛选
        Page<VoteActivity> votePage = voteActivityRepo.findAll(
                Expressions.allOf(
                        !status.equals("all") ?
                                QVoteActivity.voteActivity.voteStatusEnum.ne(VoteStatusEnum.END) : null,
                        QVoteActivity.voteActivity.voteAppId.eq(voteApp),
                        QVoteActivity.voteActivity.deleted.ne(true),
                        !StringUtils.isEmpty(keyWord) ? Expressions.anyOf(
                                QVoteActivity.voteActivity.voteActivityName.eq(keyWord)
                        ) : null,
                        expression), pageable
        );

        Page<VoteActivityPageResp> respPage = votePage.map(voteActivity ->
                voteActivityConvert.pageConvert(voteActivity));

        UniPage<VoteActivityPageResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());

        UniResp<UniPage<VoteActivityPageResp>> uniResp = new UniResp<>();

        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(resp);

        return uniResp;
    }

    @Override
    public UniResp<VoteActivityPageResp> info(String voteApp, String id) {

        if (StringUtils.isEmpty(id)) {
            return null;
        }

        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteApp),
                        QVoteActivity.voteActivity.id.eq(id)
                ));
        UniResp<VoteActivityPageResp> uniResp = new UniResp<>();

        Assert.notNull(voteActivity, "未找到该活动");

        VoteActivityPageResp voteActivityPageResp = voteActivityConvert.pageConvert(voteActivity);


        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(voteActivityPageResp);
        return uniResp;
    }

    @Override
    public UniResp<String> isForcePhone(String voteAppId, String id) {

        VoteActivity voteActivity = voteActivityRepo.findOne(id);
        String isForce = voteActivity.isForcePhone() ? "ture" : "false";

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(isForce);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> isForceFollow(
            String voteAppId,
            String voteId,
            String workId,
            String shareUrl) {

        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId),
                        QVoteActivity.voteActivity.id.eq(voteId),
                        QVoteActivity.voteActivity.voteStatusEnum.ne(VoteStatusEnum.EDITING)
                ));

        UniResp<String> uniResp = new UniResp<>();
        //判断用户是否需要先关注微信公众号并创建二维码
        Boolean isFollow = voteActivity.isForceFollow();
        if (isFollow) {
            Map<String, String> result = voteRecordService.forceFollow(voteAppId, voteId, workId, shareUrl);
            if (result.get("int").equals(String.valueOf(ErrStatus.WXUSER_404))) {
                uniResp.setStatus(ErrStatus.WXUSER_404);
                uniResp.setData("请重新登录");
                return uniResp;
            } else if (result.get("int").equals("0")) {
                uniResp.setStatus(ErrStatus.FOLLOWWXMP);
                uniResp.setData(result.get("url"));
                return uniResp;
            }
        }
        uniResp.setData("该用户已关注");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }
}
