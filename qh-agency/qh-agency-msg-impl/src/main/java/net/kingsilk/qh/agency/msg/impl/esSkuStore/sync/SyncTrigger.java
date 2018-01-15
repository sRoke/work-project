package net.kingsilk.qh.agency.msg.impl.esSkuStore.sync;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.domain.QSkuStore;
import net.kingsilk.qh.agency.domain.SkuStore;
import net.kingsilk.qh.agency.msg.EventPublisher;
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent;
import net.kingsilk.qh.agency.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.agency.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component("esSkuStoreSyncTrigger")
public class SyncTrigger extends AbstractJobImpl implements Runnable {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    @Autowired
    private QhAgencyProperties qhAgencyProperties;


    public void publishEvent(SkuStore skuStore) {
        SyncEvent event = new SyncEvent();
        event.setSkuStoreId(skuStore.getId());
        eventPublisher.publish(event);
    }


    public String getLockKey() {
        StringBuffer buf = new StringBuffer();
        buf
            .append(qhAgencyProperties.getMq().getPrefix())
            .append("/").append(SyncEvent.class.getName()).append("111");
        return buf.toString();
    }


    // 每 12 小时执行一次
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    @Override
    public void run() {

        String lockKey = getLockKey();
        Long waitLockTime = qhAgencyProperties.getMq().getDefaultConf().getLockWaitTime();

//        lockAndExec(lockKey, waitLockTime, () -> {
//
//            // 检索所有
//            Iterable<SkuStore> skuStores =skuStoreRepo.findAll(allOf(
//                    QSkuStore.skuStore.deleted.eq(false)
//            ));
//
//            StreamSupport.stream(skuStores.spliterator(), false)
//                    .forEach(this::publishEvent);
//
//        });
    }


}
