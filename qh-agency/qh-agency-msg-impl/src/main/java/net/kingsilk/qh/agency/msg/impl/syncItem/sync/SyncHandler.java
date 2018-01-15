package net.kingsilk.qh.agency.msg.impl.syncItem.sync;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.msg.api.sync.item.sync.SyncApi;
import net.kingsilk.qh.agency.msg.api.sync.item.sync.SyncEvent;
import net.kingsilk.qh.agency.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.agency.repo.ItemRepo;
import net.kingsilk.qh.agency.service.ItemSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("syncItemApi")
public class SyncHandler extends AbstractJobImpl implements SyncApi {

    @Autowired
    private QhAgencyProperties qhAgencyProperties;

    @Autowired
    private ItemSyncService itemSyncService;

    @Autowired
    private ItemRepo itemRepo;

    public String getLockKey(SyncEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
            .append(qhAgencyProperties.getMq().getPrefix())
            .append("/").append(SyncEvent.class.getName())
            .append("/").append(event.getSyncItemId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(SyncEvent syncEvent) {

        final String itemId = syncEvent.getSyncItemId();

        Item item = itemRepo.findOne(itemId);
        if (log.isDebugEnabled()) {
            log.debug("SyncEvent : itemId = " + itemId);
        }

        String lockKey = getLockKey(syncEvent);
        long waitLockTime = qhAgencyProperties.getMq().getDefaultConf().getLockWaitTime();

        lockAndExec(lockKey, waitLockTime, () -> itemSyncService.getShopList(item.getId()));

    }

}
