package net.kingsilk.qh.shop.msg.impl.esItem.sync;

import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.domain.Item;
import net.kingsilk.qh.shop.domain.QItem;
import net.kingsilk.qh.shop.msg.EventPublisher;
import net.kingsilk.qh.shop.msg.api.search.esItem.sync.SyncEvent;
import net.kingsilk.qh.shop.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.shop.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component("esItemTrigger")
public class SyncTrigger extends AbstractJobImpl implements Runnable {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private QhShopProperties qhShopProperties;


    public void publishEvent(Item item) {
        SyncEvent event = new SyncEvent();
        event.setItemId(item.getId());
        eventPublisher.publish(event);
    }


    public String getLockKey() {
        StringBuffer buf = new StringBuffer();
        buf
            .append(qhShopProperties.getMq().getPrefix())
            .append("/").append(SyncEvent.class.getName()).append("111");
        return buf.toString();
    }


    // 每 12 小时执行一次
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    @Override
    public void run() {

        String lockKey = getLockKey();
        Long waitLockTime = qhShopProperties.getMq().getDefaultConf().getLockWaitTime();

        lockAndExec(lockKey, waitLockTime, () -> {

            // 检索所有
            Iterable<Item> items =itemRepo.findAll(allOf(
                    QItem.item.deleted.eq(false)
            ));

            StreamSupport.stream(items.spliterator(), false)
                    .forEach(this::publishEvent);

        });
    }


}
