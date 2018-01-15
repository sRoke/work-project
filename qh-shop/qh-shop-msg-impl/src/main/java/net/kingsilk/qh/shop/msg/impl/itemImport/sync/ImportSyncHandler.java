package net.kingsilk.qh.shop.msg.impl.itecImport.sync;


import net.kingsilk.qh.shop.msg.api.search.ItemsImport.sync.ImportSyncApi;
import net.kingsilk.qh.shop.msg.api.search.ItemsImport.sync.ImportSyncEvent;
import net.kingsilk.qh.shop.msg.impl.AbstractJobImpl;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.ExcelReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("itemsImportSyncApi")
public class ImportSyncHandler extends AbstractJobImpl implements ImportSyncApi {

    @Autowired
    private QhShopProperties qhShopProperties;

    @Autowired
    private ExcelReadService excelReadService;



    public String getLockKey(ImportSyncEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
            .append(qhShopProperties.getMq().getPrefix())
            .append("/").append(ImportSyncEvent.class.getName())
            .append("/").append(event.getDbFileId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(ImportSyncEvent syncEvent) {

        final String dbFilename = syncEvent.getDaFileName();

//        Item item = itemRepo.findOne(itemId);
//        if (log.isDebugEnabled()) {
//            log.debug("ImportSyncEvent : itemId = " + itemId);
//        }

        String lockKey = getLockKey(syncEvent);
        long waitLockTime = qhShopProperties.getMq().getDefaultConf().getLockWaitTime();

        lockAndExec(lockKey, waitLockTime, () -> excelReadService.readExcel(syncEvent));

    }

}
