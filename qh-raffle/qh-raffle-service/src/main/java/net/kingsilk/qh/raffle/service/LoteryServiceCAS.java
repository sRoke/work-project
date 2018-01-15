package net.kingsilk.qh.raffle.service;

import com.google.common.util.concurrent.AtomicDouble;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.api.common.ErrStatusException;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LoteryServiceCAS {

    private volatile Map<String, AtomicInteger> LOTTERYAWARD = new LinkedHashMap<>();

    private volatile AtomicDouble MAXELEMENT = new AtomicDouble(0d);

    private volatile Map<String, Double> LOTTERYMAP = new LinkedHashMap<>();

    private volatile List<ContinuousList> LOTTERYLIST = new ArrayList<>();

    public LoteryServiceCAS(LinkedHashMap map, Map lotteryAward) {

        if (map == null || map.size() == 0) {
//            throw new IllegalArgumentException("抽奖集合不能为空！");
            return;
        }
        LOTTERYMAP.clear();
        LOTTERYMAP.putAll(map);
        LOTTERYAWARD.clear();
        LOTTERYAWARD.putAll(lotteryAward);
        Double minElement;
        ContinuousList continuousList;
        for (Object key : map.keySet()) {
            minElement = MAXELEMENT.get();
            boolean b = MAXELEMENT.compareAndSet(minElement, minElement + (Double) map.get((String) key));
            if (!b) {
                continue;
            }
            continuousList = new ContinuousList((String) key, minElement, MAXELEMENT.get());
            LOTTERYLIST.add(continuousList);
        }
    }

    /**
     * 进行抽奖操作
     * 返回：奖品的概率list集合中的下标
     */
    public synchronized AwardInfo randomColunmIndex() {
        int index = -1;
        String indexId = null;
        Random r = new Random();
        if (MAXELEMENT.get() == 0) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "您来晚了．奖品被抽完了");
        }
        Double d = r.nextDouble() * MAXELEMENT.get();  //生成0-1间的随机数
        while (d == 0d) {
            d = r.nextDouble() * MAXELEMENT.get();     //防止生成0.0
        }
        int size = LOTTERYLIST.size();
        for (int i = 0; i < size; i++) {
            ContinuousList cl = LOTTERYLIST.get(i);
            if (cl.isContainKey(d)) {
                index = i;
                indexId = cl.id;
                break;
            }
        }
        if (index == -1) {
            throw new ErrStatusException(ErrStatus.FOUNDNULL, "概率集合设置不合理！");
        }
        AtomicInteger awardNum = LOTTERYAWARD.get(indexId);
        int i1 = awardNum.get();
        for (; ; ) {
            boolean b = awardNum.compareAndSet(i1, i1 - 1);
            if (b) {
                break;
            }
        }
        if (awardNum != null && awardNum.get() <= 0) {

            int indexRemove = LOTTERYLIST.size();
            double difRemove = 0;
            for (int i = 0; i < LOTTERYLIST.size(); i++) {
                ContinuousList continuousList = LOTTERYLIST.get(i);
                if (continuousList.id.equals(indexId)) {
                    for (; ; ) {
                        boolean set = MAXELEMENT.compareAndSet(MAXELEMENT.get(), MAXELEMENT.get() - LOTTERYMAP.get(indexId));
                        if (set) {
                            break;
                        }
                    }
                    indexRemove = LOTTERYLIST.indexOf(continuousList);
                    difRemove = continuousList.maxElement - continuousList.minElement;
                    LOTTERYLIST.remove(continuousList);
                    i--;
                    continue;
                }
                if (i >= indexRemove) {
                    continuousList.minElement = continuousList.minElement - difRemove;
                    continuousList.maxElement = continuousList.maxElement - difRemove;
                    LOTTERYLIST.remove(i);
                    LOTTERYLIST.add(i, continuousList);
                }
            }

            LOTTERYAWARD.remove(indexId);
            LOTTERYMAP.remove(indexId);
        }
        AwardInfo awardInfo = new AwardInfo();
        awardInfo.setRecordId(indexId);
        awardInfo.setNum(awardNum.get());

        return awardInfo;
    }

    /**
     * 定义一个连续集合
     * 集合中元素x满足:(minElement,maxElement]
     * 数学表达式为：minElement < x <= maxElement
     */
    public class ContinuousList {

        String id;
        Double minElement;
        Double maxElement;

        ContinuousList(String id, Double minElement, Double maxElement) {
            if (minElement > maxElement) {
                throw new IllegalArgumentException("区间不合理，minElement不能大于maxElement！");
            }
            this.id = id;
            this.minElement = minElement;
            this.maxElement = maxElement;
        }

        /**
         * 判断当前集合是否包含特定元素
         *
         * @param element
         * @return
         */
        boolean isContainKey(Double element) {
            boolean flag = false;
            if (element > minElement && element <= maxElement) {
                flag = true;
            }
            return flag;
        }

    }
}
