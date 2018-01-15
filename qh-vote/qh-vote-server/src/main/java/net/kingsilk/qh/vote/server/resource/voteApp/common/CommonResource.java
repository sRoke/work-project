package net.kingsilk.qh.vote.server.resource.voteApp.common;

import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.common.CommonApi;
import net.kingsilk.qh.vote.api.voteApp.vote.common.dto.BackInfo;
import net.kingsilk.qh.vote.domain.VoteApp;
import net.kingsilk.qh.vote.repo.VoteAppRepo;
import net.kingsilk.qh.vote.service.service.VoteAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonResource implements CommonApi {

    @Autowired
    private VoteAppService voteAppService;

    @Autowired
    private VoteAppRepo voteAppRepo;

    @Override
    public UniResp<String> get(String voteAppId) {
        String brandAppId = voteAppService.getBrandAppId(voteAppId);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(brandAppId);
        uniResp.setStatus(200);

        return uniResp;
    }

    @Override
    public UniResp<BackInfo> backToShop(String voteAppId) {
        VoteApp voteApp = voteAppRepo.findOne(voteAppId);

        BackInfo backInfo = new BackInfo();

        backInfo.setShopId(voteApp.getShopId());
        backInfo.setBrandAppId(voteApp.getBrandAppId());

        UniResp<BackInfo> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(backInfo);
        return uniResp;
    }
}
