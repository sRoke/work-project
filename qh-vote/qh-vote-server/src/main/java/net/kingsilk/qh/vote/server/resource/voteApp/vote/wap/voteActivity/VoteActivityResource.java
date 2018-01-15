package net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteActivity;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.vote.api.voteApp.vote.wap.voteActivity.VoteActivityApi;
import net.kingsilk.qh.vote.core.vote.VoteStatusEnum;
import net.kingsilk.qh.vote.domain.QVoteActivity;
import net.kingsilk.qh.vote.domain.VoteActivity;
import net.kingsilk.qh.vote.repo.VoteActivityRepo;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteActivity.convert.VoteActivityConvert;
import net.kingsilk.qh.vote.service.ParamUtils;
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

import java.util.List;

@Component("wapVoteActivityResource")
public class VoteActivityResource implements VoteActivityApi {

    @Autowired
    private VoteActivityRepo voteActivityRepo;


    @Autowired
    private VoteActivityConvert voteActivityConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;


    @Override
    public UniResp<UniPage<VoteActivityPageResp>> page(
            String voteAppId,
            int size,
            int page,
            List<String> sort,
            String keyWord,
            String status) {
        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(page, size, s);

        Page<VoteActivity> votePage = voteActivityRepo.findAll(
                Expressions.allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId),
                        QVoteActivity.voteActivity.deleted.ne(true),
                        QVoteActivity.voteActivity.voteStatusEnum.eq(VoteStatusEnum.NORMAL),
                        !StringUtils.isEmpty(keyWord) ? Expressions.anyOf(
                                QVoteActivity.voteActivity.voteActivityName.eq(keyWord)
                        ) : null), pageable
        );

        Page<VoteActivityPageResp> respPage = votePage.map(voteActivity ->
                voteActivityConvert.pageConvert(voteActivity));

        UniPage<VoteActivityPageResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());

        UniResp<UniPage<VoteActivityPageResp>> uniResp = new UniResp<>();

        uniResp.setStatus(200);
        uniResp.setData(resp);

        return uniResp;
    }

    @Override
    public UniResp<VoteActivityPageResp> info(
            String voteAppId,
            String id) {


        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.voteAppId.eq(voteAppId),
                        QVoteActivity.voteActivity.id.eq(id),
                        QVoteActivity.voteActivity.voteStatusEnum.ne(VoteStatusEnum.EDITING)
                ));
        UniResp<VoteActivityPageResp> uniResp = new UniResp<>();

        Assert.notNull(voteActivity, "未找到该活动");

        VoteActivityPageResp voteActivityPageResp = voteActivityConvert.pageConvert(voteActivity);


        uniResp.setStatus(200);
        uniResp.setData(voteActivityPageResp);
        return uniResp;
    }
}
