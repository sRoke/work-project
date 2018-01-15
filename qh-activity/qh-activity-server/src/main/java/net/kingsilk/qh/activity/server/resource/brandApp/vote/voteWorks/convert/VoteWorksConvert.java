package net.kingsilk.qh.activity.server.resource.brandApp.vote.voteWorks.convert;

import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.VoteWorksResp;
import net.kingsilk.qh.activity.domain.VoteWorks;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class VoteWorksConvert {

    public VoteWorksResp RespConvert(VoteWorks works) {
        VoteWorksResp voteWorksResp = new VoteWorksResp();

        //todo 作品id
        voteWorksResp.setId(works.getId());
        voteWorksResp.setName(works.getName());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //todo 日期转换
        voteWorksResp.setOrder(works.getOrder());
//        voteWorksResp.setSignUpTime(ft.format(works.getSignUpTime()));
        voteWorksResp.setWorksImgUrl(works.getWorksImgUrl());
        voteWorksResp.setPhone(works.getPhone());
        voteWorksResp.setPv(works.getPv());
        voteWorksResp.setSeq(works.getSeq());
        voteWorksResp.setStatus(works.getStatus());
        voteWorksResp.setTotalVotes(works.getTotalVotes());
        voteWorksResp.setUserId(works.getUserId());
        voteWorksResp.setSlogan(works.getSlogan());
        voteWorksResp.setRank(works.getRank());
        // 为int 不可能为null
        voteWorksResp.setVirtualVotes(works.getVirtualVotes());
        return voteWorksResp;
    }
}
