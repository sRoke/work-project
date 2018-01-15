package net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteRecord;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto.RecordInfoResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto.VoteNotifyInfo;
import net.kingsilk.qh.vote.api.voteApp.vote.voteRecord.dto.VoteRecordResp;
import net.kingsilk.qh.vote.api.voteApp.vote.wap.voteRecord.VoteRecordApi;
import net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.vote.domain.*;
import net.kingsilk.qh.vote.repo.VoteActivityRepo;
import net.kingsilk.qh.vote.repo.VoteRecordRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import net.kingsilk.qh.vote.service.ParamUtils;
import net.kingsilk.qh.vote.service.service.SecService;
import net.kingsilk.qh.vote.service.service.VoteAppService;
import net.kingsilk.qh.vote.service.service.VoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component("wapVoteRecordResource")
public class VoteRecordResource implements VoteRecordApi {

    @Autowired
    private VoteRecordRepo voteRecordRepo;

    @Autowired
    private VoteRecordService voteRecordService;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SecService secService;

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private VoteAppService voteAppService;


    @Override
    public UniResp<String> vote(
            String voteAppId,
            String voteId,
            String id,
            String wxComAppId,
            String wxMpAppId,
            String curUserId) {
        String respId =
                voteRecordService.voteService(voteAppId, voteId, id, wxComAppId, wxMpAppId, curUserId);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(respId);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<VoteRecordResp>> get(
            String voteAppId,
            String voteId,
            String id,
            int size,
            int page,
            List<String> sort) {

        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(page, 999, s);

        Page<VoteRecord> voteRecordPage = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voteActivityId.eq(voteId),
                        QVoteRecord.voteRecord.voteWorksId.eq(id)
                )
                , pageable
        );

        Map<String, List<VoteRecord>> map = voteRecordPage.getContent().stream().collect(
                Collectors.groupingBy(VoteRecord::getVoterUserId, Collectors.toList())
        );

        UniPage<VoteRecordResp> resp = conversionService.convert(voteRecordPage, UniPage.class);
        List<VoteRecordResp> list = new ArrayList<>();

        for (String key : map.keySet()) {

            VoteRecordResp voteRecordResp = new VoteRecordResp();
            voteRecordResp.setUserId(key);
            voteRecordResp.setWxHeaderImg(map.get(key).get(0).getVoterHeaderImgUrl());
            voteRecordResp.setName(map.get(key).get(0).getVoterNickName());
            voteRecordResp.setVoteNum(String.valueOf(map.get(key).size()));
            list.add(voteRecordResp);
        }
        resp.setContent(list);

        UniResp<UniPage<VoteRecordResp>> uniResp = new UniResp<>();

        uniResp.setStatus(200);
        uniResp.setData(resp);

        return uniResp;
    }

    @Override
    public UniResp<VoteNotifyInfo> notify(
            String voteAppId,
            String voteActivityId,
            String id,
            String voteId
    ) {
        VoteWorks voteWorks = voteWorksRepo.findOne(
                Expressions.allOf(
                        QVoteWorks.voteWorks.id.eq(id),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL)
                )
        );

        VoteRecord voteRecord = voteRecordRepo.findOne(voteId);

        VoteNotifyInfo voteNotifyInfo = new VoteNotifyInfo();

        voteNotifyInfo.setNickName(voteWorks.getNickName());
        voteNotifyInfo.setVoteNickName(voteRecord.getVoterNickName());
        voteNotifyInfo.setWorkerHeaderImgUrl(voteWorks.getWorkerHeaderImgUrl());
        UniResp<VoteNotifyInfo> uniResp = new UniResp<>();
        uniResp.setData(voteNotifyInfo);
        uniResp.setStatus(200);
        return uniResp;
    }


    @Override
    public UniResp<RecordInfoResp> voteNum(
            String voteAppId,
            String voteId,
            String id,
            String openId) {

        String userId = secService.curUserId();
        String brandAppId = voteAppService.getBrandAppId(voteAppId);
        //获取微信相关id
        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> respUniResp = brandAppApi.info(brandAppId);
        if (StringUtils.isEmpty(userId)) {
            net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.getUserIdByOpenId(openId, respUniResp.getData().getWxMpId());
            if (resp.getData() != null) {
                userId = resp.getData();
            }
        }


        RecordInfoResp recordInfoResp = new RecordInfoResp();

        //先判断当前作品是否属于此人
        VoteWorks voteWorks = voteWorksRepo.findOne(
                Expressions.allOf(
                        QVoteWorks.voteWorks.deleted.ne(true),
                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                        QVoteWorks.voteWorks.id.eq(id)
                ));

        recordInfoResp.setStatus(voteWorks.getStatus().getCode());
        recordInfoResp.setSelf(false);
        if (!StringUtils.isEmpty(userId)) {
            if (userId.equals(voteWorks.getUserId())) {
                recordInfoResp.setSelf(true);
            }
            //再计算今天此人今日剩余票
            Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
            todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
            Date todayStartTime = todayStartCalendar.getTime();
            Calendar todayEndCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
            todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayEndCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE), 23, 59, 59);
            Date todayEndTime = todayEndCalendar.getTime();
            List<VoteRecord> page = Lists.newArrayList(voteRecordRepo.findAll(
                    Expressions.allOf(
                            QVoteRecord.voteRecord.voterUserId.eq(userId),
                            QVoteRecord.voteRecord.voteActivityId.eq(voteId),
                            QVoteRecord.voteRecord.dateCreated.lt(todayEndTime),
                            QVoteRecord.voteRecord.dateCreated.gt(todayStartTime)
                    )));

            VoteActivity voteActivity = voteActivityRepo.findOne(
                    allOf(QVoteActivity.voteActivity.id.eq(voteId),
                            QVoteActivity.voteActivity.voteAppId.eq(voteAppId)));
            recordInfoResp.setResidueNum(
                    String.valueOf(voteActivity.getMaxVotePerDay() - page.size()));

            Map<String, Long> map = page.stream().collect(
                    Collectors.groupingBy(VoteRecord::getVoteWorksId, Collectors.counting())
            );
            recordInfoResp.setVotePeopleToday(String.valueOf(map.size()));
            //计算给当前作品投了多少票
            List<VoteRecord> list = Lists.newArrayList(voteRecordRepo.findAll(
                    Expressions.allOf(
                             QVoteRecord.voteRecord.voterUserId.eq(userId),
                            QVoteRecord.voteRecord.voteWorksId.eq(id),
                            QVoteRecord.voteRecord.voteActivityId.eq(voteId)
                    )));
            recordInfoResp.setVoteNum(String.valueOf(list.size()));
        }else {
            recordInfoResp.setResidueNum(String.valueOf(0));
            recordInfoResp.setVotePeopleToday(String.valueOf(0));
            recordInfoResp.setVoteNum(String.valueOf(0));
        }
        UniResp<RecordInfoResp> uniResp = new UniResp<>();
        uniResp.setData(recordInfoResp);
        uniResp.setStatus(200);
        return uniResp;
    }


}
