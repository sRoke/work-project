package net.kingsilk.qh.raffle.test.service;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.service.LotteryService;

public class LotteryThread implements Runnable {

    private LotteryService lotteryService;

    private Integer i;

    public LotteryThread(LotteryService lotteryService, Integer i) {
        this.lotteryService = lotteryService;
        this.i = i;
    }

    @Override
    public void run() {
        AwardInfo execution = lotteryService.execution("1555");
        System.out.println(i);
    }
}
