package net.kingsilk.qh.agency.msg.impl.esItem.sync;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.msg.api.search.esItem.sync.SyncApi;
import net.kingsilk.qh.agency.msg.api.search.esItem.sync.SyncEvent;
import net.kingsilk.qh.agency.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.agency.repo.ItemRepo;
import net.kingsilk.qh.agency.search.EsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("esItemSyncApi")
public class SyncHandler extends AbstractJobImpl implements SyncApi {

    @Autowired
    private QhAgencyProperties qhAgencyProperties;

    @Autowired
    private EsItemService esItemService;

    @Autowired
    private ItemRepo itemRepo;

    public String getLockKey(SyncEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
            .append(qhAgencyProperties.getMq().getPrefix())
            .append("/").append(SyncEvent.class.getName())
            .append("/").append(event.getItemId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(SyncEvent syncEvent) {

        final String itemId = syncEvent.getItemId();

        Item item = itemRepo.findOne(itemId);
        if (log.isDebugEnabled()) {
            log.debug("SyncEvent : itemId = " + itemId);
        }

        String lockKey = getLockKey(syncEvent);
        long waitLockTime = qhAgencyProperties.getMq().getDefaultConf().getLockWaitTime();

        lockAndExec(lockKey, waitLockTime, () -> esItemService.saveOrUpdate(item));

    }

}
