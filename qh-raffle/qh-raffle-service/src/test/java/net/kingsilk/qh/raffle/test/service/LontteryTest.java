package net.kingsilk.qh.raffle.test.service;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.service.LotteryService;
import net.kingsilk.qh.raffle.test.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LontteryTest extends BasicTest {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Test
    public void test1() {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();

        map.put("1", 10d);
        map.put("2", 20d);
        map.put("3", 30d);
        map.put("4", 40d);

        LinkedHashMap<String, AtomicInteger> lotteryAward = new LinkedHashMap<>();
        lotteryAward.put("1", new AtomicInteger(100000));
        lotteryAward.put("2", new AtomicInteger(200000));
        lotteryAward.put("3", new AtomicInteger(300000));
        lotteryAward.put("4", new AtomicInteger(400000));

        lotteryService.register("1555", map, lotteryAward);
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (int i = 0; i < 100000; i++) {
            AwardInfo execution = lotteryService.execution("1555");
            switch (execution.getRecordId()) {
                case "1":
                    a++;
                    break;
                case "2":
                    b++;
                    break;
                case "3":
                    c++;
                    break;
                case "4":
                    d++;
                    break;
                default:
            }
        }

        System.out.println("1 :" + a + "  2 :" + b + "  3 :" + c + "  4 :" + d);
//        if (!lotteryService.isRegister("5a4c52b35e41d603c23f4753")) {
//            lotteryService.register("5a4c52b35e41d603c23f4753", map, lotteryAward);
//        }
//        String e = lotteryService.execution("5a4c52b35e41d603c23f4753");
//        System.out.println(e);
    }

    @Test
    public void test2() {

        LinkedHashMap<String, Double> map = new LinkedHashMap<>();

        map.put("1", 10d);
        map.put("2", 20d);
        map.put("3", 30d);
        map.put("4", 40d);
        map.put("5", 50d);

        LinkedHashMap<String, AtomicInteger> lotteryAward = new LinkedHashMap<>();
        lotteryAward.put("1", new AtomicInteger(1000));
        lotteryAward.put("2", new AtomicInteger(1000));
        lotteryAward.put("3", new AtomicInteger(1000));
        lotteryAward.put("4", new AtomicInteger(1000));
        lotteryAward.put("5", new AtomicInteger(1000));

        lotteryService.register("1555", map, lotteryAward);

//        ApplicationContext ct = new AnnotationConfigApplicationContext(QhLotteryUtAppTest.class);
//        LotteryThread l1 = (LotteryThread) ct.getBean("LotteryThread");
//        LotteryThread l2 = (LotteryThread) ct.getBean("LotteryThread");
//        LotteryThread l3 = (LotteryThread) ct.getBean("LotteryThread");
//        LotteryThread l4 = (LotteryThread) ct.getBean("LotteryThread");
//        LotteryThread l5 = (LotteryThread) ct.getBean("LotteryThread");


//        LotteryThread l1 = new LotteryThread(lotteryService);
//        LotteryThread l2 = new LotteryThread(lotteryService);
//        LotteryThread l3 = new LotteryThread(lotteryService);
//        LotteryThread l4 = new LotteryThread(lotteryService);
//        LotteryThread l5 = new LotteryThread(lotteryService);
//        LotteryThread l6 = new LotteryThread(lotteryService);
//        taskExecutor.execute(l1);
//        taskExecutor.execute(l2);
//        taskExecutor.execute(l3);
//        taskExecutor.execute(l4);
//        taskExecutor.execute(l5);

        List<LotteryThread> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            LotteryThread l = new LotteryThread(lotteryService, i);
            list.add(l);
        }
        for (int i = 0; i < 5000; i++) {
            taskExecutor.execute(list.get(i));
        }
        try {
            Thread.sleep(10000);
            AwardInfo execution = lotteryService.execution("1555");
            System.out.println(execution.getRecordId());
        } catch (Exception e) {

        }
    }

}
