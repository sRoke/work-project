package net.kingsilk.qh.agency.msg.impl.syncItem.sync;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.domain.QItem;
import net.kingsilk.qh.agency.domain.QPartner;
import net.kingsilk.qh.agency.msg.EventPublisher;
import net.kingsilk.qh.agency.msg.api.sync.item.sync.SyncEvent;
import net.kingsilk.qh.agency.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.agency.repo.ItemRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component("itemSyncTrigger")
public class SyncTrigger extends AbstractJobImpl implements Runnable {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private QhAgencyProperties qhAgencyProperties;

    @Autowired
    private PartnerRepo partnerRepo;

    public void publishEvent(Item item) {
        SyncEvent event = new SyncEvent();
        event.setSyncItemId(item.getId());
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

        lockAndExec(lockKey, waitLockTime, () -> {

            List<String> brandAppIds = new ArrayList<>();
            partnerRepo.findAll(
                    QPartner.partner.partnerTypeEnum.eq(PartnerTypeEnum.BRAND_COM)
            ).forEach(
                    partner -> brandAppIds.add(partner.getBrandAppId())
            );
            // 检索所有

            Iterable<Item> items = itemRepo.findAll(allOf(
                    DbUtil.opIn(QItem.item.brandAppId, brandAppIds),
                    QItem.item.deleted.eq(false)
            ));

            StreamSupport.stream(items.spliterator(), false)
                    .forEach(this::publishEvent);

        });
    }


}
