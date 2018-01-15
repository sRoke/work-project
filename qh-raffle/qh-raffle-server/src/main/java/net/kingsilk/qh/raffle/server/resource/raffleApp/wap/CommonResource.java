package net.kingsilk.qh.raffle.server.resource.raffleApp.wap;

import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.bargain.wap.CommonApi;
import net.kingsilk.qh.raffle.api.raffleApp.bargain.wap.dto.BackInfo;
import net.kingsilk.qh.raffle.core.LogisticsCompanyEnum;
import net.kingsilk.qh.raffle.domain.RaffleApp;
import net.kingsilk.qh.raffle.repo.RaffleAppRepo;
import net.kingsilk.qh.raffle.service.RaffleAppService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonResource implements CommonApi {

    @Autowired
    private RaffleAppService raffleAppService;

    @Autowired
    private RaffleAppRepo raffleAppRepo;

    @Override
    public UniResp<String> get(String raffleAppId) {

        String brandAppId = raffleAppService.getBrandAppId(raffleAppId);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(brandAppId);
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }

    @Override
    public UniResp<Map<String, String>> getLogisticsCompanyEnum(String raffleAppId) {
        Map<String, String> LogisticsCompanyEnumMap = LogisticsCompanyEnum.getMap();
        UniResp<Map<String, String>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(LogisticsCompanyEnumMap);
        return uniResp;
    }

    @Override
    public UniResp<BackInfo> backToShop(String raffleAppId) {

        RaffleApp raffleApp = raffleAppRepo.findOne(raffleAppId);

        BackInfo backInfo = new BackInfo();

        backInfo.setShopId(raffleApp.getShopId());
        backInfo.setBrandAppId(raffleApp.getBrandAppId());

        UniResp<BackInfo> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(backInfo);
        return uniResp;
    }

    @Override
    public UniResp<String> getShopName(String raffleAppId) {

        String shopName = raffleAppService.getShopName(raffleAppId);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData(shopName);
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }
}
