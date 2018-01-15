package net.kingsilk.qh.agency.msg.impl.esSkuStore.sync;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.domain.SkuStore;
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncApi;
import net.kingsilk.qh.agency.msg.api.search.esSkuStore.sync.SyncEvent;
import net.kingsilk.qh.agency.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.agency.repo.SkuStoreRepo;
import net.kingsilk.qh.agency.search.EsSkuStoreService;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("esSkuStoreSyncApi")
public class SyncHandler extends AbstractJobImpl implements SyncApi {

    @Autowired
    private QhAgencyProperties qhAgencyProperties;

    @Autowired
    private EsSkuStoreService esSkuStoreService;

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    public String getLockKey(SyncEvent event) {
        StrBuilder buf = new StrBuilder();
        buf
            .append(qhAgencyProperties.getMq().getPrefix())
            .append("/").append(SyncEvent.class.getName())
            .append("/").append(event.getSkuStoreId());
        return buf.toString();
    }

    @Override
    public void handle(SyncEvent syncEvent) {

        final String skuStoreId = syncEvent.getSkuStoreId();

        SkuStore skuStore=skuStoreRepo.findOne(skuStoreId);
        if (log.isDebugEnabled()) {
            log.debug("SyncEvent : itemId = " + skuStoreId);
        }

        String lockKey = getLockKey(syncEvent);
        long waitLockTime = qhAgencyProperties.getMq().getDefaultConf().getLockWaitTime();

        lockAndExec(lockKey, waitLockTime, () -> esSkuStoreService.saveOrUpdate(skuStore));

    }

}
