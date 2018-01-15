package net.kingsilk.qh.vote.service.service;

import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import net.kingsilk.qh.vote.domain.VoteApp;
import net.kingsilk.qh.vote.repo.VoteAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteAppService {

    @Autowired
    private ShopApi shopApi;

    @Autowired
    private VoteAppRepo voteAppRepo;

    public String getBrandAppId(String voteAppId) {

        VoteApp voteApp = voteAppRepo.findOne(voteAppId);

        return voteApp.getBrandAppId();
    }

    public String getShopName(String voteAppId) {

        String shopName = getShopInfo(voteAppId).getData().getName();

        return shopName;
    }

    public String getShopTel(String voteAppId) {
        String tel = getShopInfo(voteAppId).getData().getTel();
        return tel;
    }

    public UniResp<ShopResp> getShopInfo(String voteAppId) {
        VoteApp voteApp = voteAppRepo.findOne(voteAppId);

        String shopId = voteApp.getShopId();
        UniResp<ShopResp> uniResp =
                shopApi.info(voteApp.getBrandAppId(), shopId);


        return uniResp;
    }
}
