package net.kingsilk.qh.raffle.service;

import net.kingsilk.qh.raffle.domain.RaffleApp;
import net.kingsilk.qh.raffle.repo.RaffleAppRepo;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaffleAppService {

    @Autowired
    private ShopApi shopApi;

    @Autowired
    private RaffleAppRepo raffleAppRepo;

    public String getBrandAppId(String raffleAppId) {

        RaffleApp raffleApp = raffleAppRepo.findOne(raffleAppId);
        return raffleApp.getBrandAppId();
    }

    public String getShopName(String raffleAppId) {

        String shopName = getShopInfo(raffleAppId).getData().getName();
        return shopName;
    }

    public String getShopTel(String raffleAppId) {
        String tel = getShopInfo(raffleAppId).getData().getTel();
        return tel;
    }

    public UniResp<ShopResp> getShopInfo(String raffleAppId) {
        RaffleApp raffleApp = raffleAppRepo.findOne(raffleAppId);

        String shopId = raffleApp.getShopId();
        UniResp<ShopResp> uniResp =
                shopApi.info(raffleApp.getBrandAppId(), shopId);
        return uniResp;
    }
}
