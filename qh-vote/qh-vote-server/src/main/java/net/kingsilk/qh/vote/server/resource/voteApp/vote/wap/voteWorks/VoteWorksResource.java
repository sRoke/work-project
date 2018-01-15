package net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteWorks;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.vote.api.ErrStatus;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.*;
import net.kingsilk.qh.vote.api.voteApp.vote.wap.voteWorks.VoteWorksApi;
import net.kingsilk.qh.vote.api.voteApp.vote.wap.voteWorks.dto.SignUpResp;
import net.kingsilk.qh.vote.core.vote.VoteStatusEnum;
import net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.vote.domain.QVoteActivity;
import net.kingsilk.qh.vote.domain.QVoteWorks;
import net.kingsilk.qh.vote.domain.VoteActivity;
import net.kingsilk.qh.vote.domain.VoteWorks;
import net.kingsilk.qh.vote.repo.VoteActivityRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteWorks.convert.VoteWorksConvert;
import net.kingsilk.qh.vote.service.ParamUtils;
import net.kingsilk.qh.vote.service.service.SecService;
import net.kingsilk.qh.vote.service.service.VoteAppService;
import net.kingsilk.qh.vote.service.service.VoteWorkCheckService;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

@Component("wapVoteWorksResource")
public class VoteWorksResource implements VoteWorksApi {


    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteWorksConvert voteWorksConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private UserApi userApi;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private SecService secService;

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private VoteAppService voteAppService;

    @Autowired
    private VoteWorkCheckService voteWorkCheckService;

    /**
     * 分页查询
     */
    @Override
    public UniResp<UniPage<VoteWorksResp>> page(

            String voteAppId, String voteId, int size, int page, List<String> sort, String keyWord) {


        Sort s = ParamUtils.toSort(sort);

        PageRequest pageRequest = new PageRequest(page, size, s);

        Page<VoteWorks> pageData = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        !StringUtils.isEmpty(keyWord) ? anyOf(
                                QVoteWorks.voteWorks.phone.eq(keyWord),
                                QVoteWorks.voteWorks.seq.eq(keyWord),
                                QVoteWorks.voteWorks.name.eq(keyWord)
                        ) : null), pageRequest
        );

        Page<VoteWorksResp> respPage = pageData.map(voteWorks -> {
            VoteWorksResp voteWorksResp = new VoteWorksResp();
            voteWorksResp.setWorksImgUrl(voteWorks.getWorksImgUrl());
            voteWorksResp.setName(voteWorks.getName());
            voteWorksResp.setTotalVotes(voteWorks.getTotalVotes());
            voteWorksResp.setSeq(voteWorks.getSeq());
            voteWorksResp.setId(voteWorks.getId());
            voteWorksResp.setDateCreated(voteWorks.getDateCreated());
            voteWorksResp.setPhone(voteWorks.getPhone());
            voteWorksResp.setStatus(voteWorks.getStatus());
            if (voteWorks.getRank() != null) {
                voteWorksResp.setRank(voteWorks.getRank());
            }
            return voteWorksResp;
        });
        UniPage<VoteWorksResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());

        UniResp<UniPage<VoteWorksResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);

        return uniResp;
    }

    /**
     * 作品详情
     */
    @Override

    public UniResp<VoteWorksResp> info(String voteAppId, String voteId, String id) {

        UniResp<VoteWorksResp> uniResp = new UniResp<>();
        VoteWorks voteWorks = voteWorksRepo.findOne(
                allOf(
                        QVoteWorks.voteWorks.status.ne(VoteWorksStatusEnum.REJECT),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        QVoteWorks.voteWorks.id.eq(id)
                )
        );
        Assert.notNull(voteWorks, "该作品可能已被拒绝或删除");
//        List<String> sort = new ArrayList<>();
//        sort.add("totalVotes,desc");
//        sort.add("lastVoteTime,asc");
//        Sort s = ParamUtils.toSort(sort);
//        List<VoteWorks> list = voteWorksRepo.findAll();
//        PageRequest pageRequest = new PageRequest(0, list.size(), s);
//        Page<VoteWorks> page = voteWorksRepo.findAll(
//                allOf(
//                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
//                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
//                        QVoteWorks.voteWorks.deleted.ne(true)
//                ), pageRequest);

        VoteWorksResp voteWorksResp = voteWorksConvert.RespConvert(voteWorks);


        //排名和总票数
        voteWorksResp.setRanking(voteWorks.getRank());
        if (voteWorks.getRank() != 1) {
            VoteWorks voteWorks1 = voteWorksRepo.findOne(
                    Expressions.allOf(
                            QVoteWorks.voteWorks.rank.eq(voteWorks.getRank() - 1),
                            QVoteWorks.voteWorks.voteActivityId.eq(voteId)
                    ));

            if (voteWorks1 != null) {
                voteWorksResp.setLessVoteNum(voteWorks1.getTotalVotes() - voteWorks.getTotalVotes());
            }
        }
//        Integer i = page.getContent().indexOf(voteWorks);
//        if (i > 0) {
//            Integer num = page.getContent().get(i - 1).getTotalVotes();
//            voteWorksResp.setLessVoteNum(num - voteWorks.getTotalVotes());
//        }
        uniResp.setData(voteWorksResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<VoteShareResp> shareInfo(
            String voteAppId,
            String voteId,
            String id) {

        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId),
                        QVoteActivity.voteActivity.voteStatusEnum.ne(VoteStatusEnum.EDITING),
                        QVoteActivity.voteActivity.id.eq(voteId)
                )
        );

        Assert.notNull(voteActivity, "未找到该活动");

        VoteWorks voteWorks = voteWorksRepo.findOne(
                allOf(
                        QVoteWorks.voteWorks.id.eq(id),
                        QVoteWorks.voteWorks.status.ne(VoteWorksStatusEnum.REJECT)
                ));
        Assert.notNull(voteWorks, "未找到该作品");

        VoteShareResp voteShareResp = new VoteShareResp();
        voteShareResp.setShareContent(voteActivity.getShareContent());
        voteShareResp.setShareTitle(voteActivity.getShareTitle());
        voteShareResp.setWorksImgUrl(voteWorks.getWorksImgUrl());
        voteShareResp.setStatus(voteWorks.getStatus().getCode());
        voteShareResp.setPrimaryImgUrl(voteActivity.getPrimaryImgUrl());
        UniResp<VoteShareResp> uniResp = new UniResp<>();
        uniResp.setData(voteShareResp);
        return uniResp;
    }


    @Override
    public UniResp<SignupWorkResp> isSignup(String voteAppId, String voteId, String openId) {


        UniResp<SignupWorkResp> uniResp = new UniResp<>();

        SignupWorkResp signupWorkResp = new SignupWorkResp();

        String brandAppId = voteAppService.getBrandAppId(voteAppId);
        //获取微信相关id
        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> respUniResp = brandAppApi.info(brandAppId);
        String userId = secService.curUserId();
        if (StringUtils.isEmpty(userId)) {
            net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.getUserIdByOpenId(openId, respUniResp.getData().getWxMpId());
            userId = resp.getData();
        }
        if (!StringUtils.isEmpty(userId)) {
            VoteWorks voteWorks = voteWorksRepo.findOne(
                    Expressions.allOf(
                            QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                            QVoteWorks.voteWorks.userId.eq(userId),
                            QVoteWorks.voteWorks.status.ne(VoteWorksStatusEnum.REJECT),
                            QVoteWorks.voteWorks.deleted.ne(true)
                    ));


            if (voteWorks != null) {
                signupWorkResp.setWorkId(voteWorks.getId());
            }
        }
        net.kingsilk.wx4j.broker.api.UniResp<GetResp> wxResp
                = wxComMpUserApi.get(respUniResp.getData().getWxComAppId(), respUniResp.getData().getWxMpId(), openId);
        if (wxResp.getStatus() == 10001) {
            signupWorkResp.setLogOut(true);
            uniResp.setData(signupWorkResp);
            return uniResp;
        }
        signupWorkResp.setNickName(wxResp.getData().getNickName());
        signupWorkResp.setOpenId(openId);
        signupWorkResp.setWxheadImg(wxResp.getData().getHeadImgUrl());
        signupWorkResp.setLogOut(false);
        signupWorkResp.setWxComAppId(respUniResp.getData().getWxComAppId());
        signupWorkResp.setWxMpAppId(respUniResp.getData().getWxMpId());
        uniResp.setData(signupWorkResp);
        uniResp.setStatus(200);
        return uniResp;
    }


    //参加活动，创建作品
    @Override
    public UniResp<SignUpResp> join(
            String voteAppId,
            String voteId,
            VoteworkReq voteworkReq) {

        String userId = secService.curUserId();

        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.id.eq(voteId),
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId)));
        UniResp<SignUpResp> uniResp = new UniResp<>();
        Date date = new Date();
        SignUpResp signUpResp = new SignUpResp();
        if (voteActivity.getVoteStatusEnum().equals(VoteStatusEnum.END)) {
            signUpResp.setMsg("活动已被禁用");
            uniResp.setData(signUpResp);
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }
        if (voteActivity.getSignUpStartTime().after(date)) {
            signUpResp.setMsg("活动尚未开始");
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            uniResp.setData(signUpResp);
            return uniResp;
        }
        if (voteActivity.getVoteEndTime().before(date)) {
            signUpResp.setMsg("投票已结束");
            uniResp.setData(signUpResp);
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            return uniResp;
        }


        String workerHeaderImgUrl = voteworkReq.getWxheadImg();
        String nickName = voteworkReq.getNickName();
        PageRequest pageRequest = new PageRequest(0, 9, null);

        Page<VoteWorks> page = voteWorksRepo.findAll(
                QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                pageRequest);

        VoteWorks voteWorks = new VoteWorks();
        voteWorks.setName(voteworkReq.getName());
        voteWorks.setPhone(voteworkReq.getPhone());
        voteWorks.setWorksImgUrl(voteworkReq.getWorksImgUrl());
        voteWorks.setSlogan(voteworkReq.getSlogan());
        voteWorks.setSignUpTime(new Date());
        voteWorks.setVoteActivityId(voteId);
        voteWorks.setUserId(userId);
        voteWorks.setWorkerHeaderImgUrl(workerHeaderImgUrl);
        voteWorks.setNickName(nickName);
        voteWorks.setSeq(String.valueOf(page.getTotalElements() + 1));
        voteWorks.setUserId(userId);
        voteWorks.setOpenId(voteworkReq.getOpenId());
        voteWorks.setTotalVotes(0);
        voteWorks.setPv(0);
        voteWorks.setVirtualVotes(0);

        VoteWorks isSignup = voteWorksRepo.findOne(
                allOf(
                        QVoteWorks.voteWorks.userId.eq(userId),
                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                        QVoteWorks.voteWorks.status.ne(VoteWorksStatusEnum.REJECT),
                        QVoteWorks.voteWorks.deleted.ne(true)
                ));
        if (isSignup != null) {
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            signUpResp.setMsg("你已经报过名了");
            uniResp.setData(signUpResp);
            return uniResp;
        }

        voteWorks = voteWorksRepo.save(voteWorks);

        if (!voteActivity.getMustCheck()) {
            WxSendReq wxSendReq = new WxSendReq();
            wxSendReq.setStatus(true);
            wxSendReq.setWxComAppId(voteworkReq.getWxComAppId());
            wxSendReq.setWxMpAppId(voteworkReq.getWxMpAppId());
            String voteWorkId = voteWorkCheckService.ckeckService(voteAppId, voteId, voteWorks.getId(), wxSendReq);
            voteWorks = voteWorksRepo.findOne(voteWorkId);
            voteWorks.setStatus(VoteWorksStatusEnum.NORMAL);
        } else {
            voteWorks.setRank(0);//带审核的排在最上边
            voteWorks.setStatus(VoteWorksStatusEnum.APPLYING);
        }
        voteWorksRepo.save(voteWorks);
        if (voteActivity.getMustCheck()) {
            signUpResp.setMsg("check");
            signUpResp.setVoteWorkId(voteWorks.getId());
            uniResp.setData(signUpResp);

        } else {
            signUpResp.setMsg("unCheck");
            signUpResp.setVoteWorkId(voteWorks.getId());
            uniResp.setData(signUpResp);
        }
        uniResp.setStatus(200);
        return uniResp;
    }

}
