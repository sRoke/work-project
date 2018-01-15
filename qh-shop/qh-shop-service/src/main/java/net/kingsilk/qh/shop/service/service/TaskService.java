package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.QSkuStore;
import net.kingsilk.qh.shop.domain.SkuStore;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class TaskService {

//    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    QhShopProperties qhShopProperties;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    LockRegistry lockRegistry;

    @Autowired
    SkuStoreRepo skuStoreRepo;


    public String getLockKey(String key) {
        StringBuilder buf = new StringBuilder();
        buf
                .append(qhShopProperties.getMq().getPrefix())
                .append("/").append(TaskService.class.getName())
                .append("/").append(key).append("111");
        return buf.toString();
    }


    public void run(String orderId) {
        String lockKey = getLockKey(orderId);
        long waitLockTime = qhShopProperties.getMq().getDefaultConf().getLockWaitTime();
        Lock lock = lockRegistry.obtain(lockKey);
        try {
            if (!lock.tryLock(waitLockTime, TimeUnit.MILLISECONDS)) {
//                log.info("加锁失败，任务中止");
                return;
            }
        } catch (InterruptedException e) {
//            log.warn("在等待加锁时被中止", e);
            return;
        }
        try {
            Order order = orderRepo.findOne(orderId);
            if (order.getStatus().equals(OrderStatusEnum.UNPAYED)) {
                order.setStatus(OrderStatusEnum.CLOSED);
                // TODO 加库存
                // TODO 减销量
                List<Order.OrderItem> skus = order.getOrderItems();

                skus.forEach(
                        sku -> {
                            SkuStore skuStore = skuStoreRepo.findOne(
                                    Expressions.allOf(
                                            QSkuStore.skuStore.brandAppId.eq(order.getBrandAppId()),
                                            QSkuStore.skuStore.shopId.eq(order.getShopId()),
                                            QSkuStore.skuStore.skuId.eq(sku.getSkuId())
                                    )
                            );
                            skuStore.setNum(skuStore.getNum() + Integer.valueOf(sku.getNum()));
                            skuStore.setSalesVolume(skuStore.getSalesVolume() - Integer.valueOf(sku.getNum()));
                            skuStoreRepo.save(skuStore);
                        }
                );
            }
            orderRepo.save(order);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

}
