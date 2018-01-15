package net.kingsilk.qh.raffle.service;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LotteryService {

    private final Map<String, LoteryServiceCAS> map = new HashMap<>();

    public boolean isRegister(String lotteryId) {
        synchronized (map) {
            return map.containsKey(lotteryId);
        }
    }

    public void register(String lotteryId, LinkedHashMap lotteryMap, LinkedHashMap lotteryAward) {
        synchronized (map) {
//            Assert.isTrue(!map.containsKey(lotteryId), "已经注册了 tag 为 `" + lotteryId + "` 的 LoteryServiceCAS");
            map.put(lotteryId, new LoteryServiceCAS(lotteryMap,lotteryAward));
        }
    }

    public AwardInfo execution(String lotteryId) {
        return map.get(lotteryId).randomColunmIndex();
    }

    public void finish(String lotteryId) {
        map.remove(lotteryId);
    }

}
