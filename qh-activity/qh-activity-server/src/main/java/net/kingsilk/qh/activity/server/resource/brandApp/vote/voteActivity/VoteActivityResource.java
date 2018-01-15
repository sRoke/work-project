package net.kingsilk.qh.activity.server.resource.brandApp.vote.voteActivity;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.activity.api.ErrStatus;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.VoteActivityApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.activity.core.vote.VoteStatusEnum;
import net.kingsilk.qh.activity.domain.QVoteActivity;
import net.kingsilk.qh.activity.domain.VoteActivity;
import net.kingsilk.qh.activity.repo.VoteActivityRepo;
import net.kingsilk.qh.activity.server.resource.brandApp.vote.voteActivity.convert.VoteActivityConvert;
import net.kingsilk.qh.activity.service.ParamUtils;
import net.kingsilk.qh.activity.service.service.VoteRecordService;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

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
    public UniResp<String> add(String brandAppId, VoteActivityReq voteActivityReq) {
        Assert.notNull(brandAppId, "brandAppId不能为空");
        UniResp<String> uniResp = new UniResp<>();
        Assert.notNull(voteActivityReq, "请求参数不能为空！");
        if (voteActivityReq.getActivityName() == null) {
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

        if (voteActivityReq.getDesp() == null) {
            uniResp.setData("请输入活动描述");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
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
        if (voteActivityReq.getShareContent() == null) {
            uniResp.setData("请输入分享内容");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
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
        voteActivity.setBrandAppId(brandAppId);
        voteActivity.setTotalVote(0);  //添加活动统计数据的初始化
        voteActivity.setTotalJoinPeople(0);
        voteActivity.setTotalVisit(0);
        voteActivityRepo.save(voteActivity);
        uniResp.setData("success");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> update(String brandAppid, String id, VoteActivityReq voteActivityReq) {
        UniResp<String> uniResp = new UniResp<String>();

        if (StringUtils.isEmpty(brandAppid) || StringUtils.isEmpty(id)) {
            uniResp.setData("brandAppid 和 id 不能为空");
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
                        QVoteActivity.voteActivity.brandAppId.eq(brandAppid),
                        QVoteActivity.voteActivity.id.eq(id)
                ));
        //String日期 转换成 Date日期格式 转换格式： 年-月-日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (voteActivityReq.getSignUpStartTime() != null) {
                voteActivity.setSignUpStartTime(sdf.parse(voteActivityReq.getSignUpStartTime()));
            }
            if (voteActivityReq.getSignUpEndTime() != null) {
                voteActivity.setSignUpEndTime(sdf.parse(voteActivityReq.getSignUpEndTime()));
            }
            if (voteActivityReq.getVoteStartTime() != null) {
                voteActivity.setVoteStartTime(sdf.parse(voteActivityReq.getVoteStartTime()));
            }
            if (voteActivityReq.getVoteEndTime() != null) {
                voteActivity.setVoteEndTime(sdf.parse(voteActivityReq.getVoteEndTime()));
            }
        } catch (Exception e) {
            uniResp.setData("请输入的日期格式为:yyyy-MM-dd HH:mm:ss");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivityReq.getActivityName() != null) {
            voteActivity.setActivityName(voteActivityReq.getActivityName());
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
            voteActivity.setVoteStatusEnum(voteActivityReq.getVoteStatusEnum());
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
    public UniResp<Void> delete(String brandApp, String id) {
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.brandAppId.eq(brandApp),
                        QVoteActivity.voteActivity.id.eq(id)));
        voteActivity.setDeleted(true);
        voteActivityRepo.save(voteActivity);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<VoteActivityPageResp>> page(
            String brandApp,
            int size,
            int page,
            List<String> sort,
            String keyWord) {

        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(page, size, s);

        Page<VoteActivity> activityPage = voteActivityRepo.findAll(
                Expressions.allOf(
                        QVoteActivity.voteActivity.brandAppId.eq(brandApp),
                        QVoteActivity.voteActivity.deleted.ne(true),
                        !StringUtils.isEmpty(keyWord) ? anyOf(
                                QVoteActivity.voteActivity.activityName.eq(keyWord)
                        ) : null), pageable
        );

        Page<VoteActivityPageResp> respPage = activityPage.map(voteActivity ->
                voteActivityConvert.pageConvert(voteActivity));

        UniPage<VoteActivityPageResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());

        UniResp<UniPage<VoteActivityPageResp>> uniResp = new UniResp<>();

        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(resp);

        return uniResp;
    }

    @Override
    public UniResp<VoteActivityPageResp> info(String brandApp, String id) {

        if (StringUtils.isEmpty(id)) {
            return null;
        }

        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.brandAppId.eq(brandApp),
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
    public UniResp<String> isForcePhone(String brandAppId, String id) {

        VoteActivity voteActivity = voteActivityRepo.findOne(id);
        String isForce = voteActivity.isForcePhone() ? "ture" : "false";

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(isForce);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> isForceFollow(
            String brandAppId,
            String activityId,
            String workId,
            String shareUrl) {

        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.brandAppId.eq(brandAppId),
                        QVoteActivity.voteActivity.id.eq(activityId),
                        QVoteActivity.voteActivity.voteStatusEnum.eq(VoteStatusEnum.NORMAL)
                ));

        UniResp<String> uniResp = new UniResp<>();
        //判断用户是否需要先关注微信公众号并创建二维码
        Boolean isFollow = voteActivity.isForceFollow();
        if (isFollow) {
            Map<String, String> result = voteRecordService.forceFollow(brandAppId, activityId, workId, shareUrl);
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
