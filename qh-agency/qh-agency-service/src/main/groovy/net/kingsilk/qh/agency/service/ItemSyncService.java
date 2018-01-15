package net.kingsilk.qh.agency.service;

import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.domain.QPartner;
import net.kingsilk.qh.agency.domain.QSku;
import net.kingsilk.qh.agency.repo.ItemRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SkuRepo;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.SkuMiniInfo;
import net.kingsilk.qh.shop.api.brandApp.sync.SyncItemApi;
import net.kingsilk.qh.shop.api.brandApp.sync.dto.ItemSyncReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Service
public class ItemSyncService {

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    private SyncItemApi syncItemApi;

    public void getShopList(String itemId) {
//        Partner

        Item item = itemRepo.findOne(itemId);
        List<String> shopIds = new ArrayList<>();
        partnerRepo.findAll(
                allOf(
                        QPartner.partner.brandAppId.eq(item.getBrandAppId()),
                        QPartner.partner.shopId.isNotNull(),
                        QPartner.partner.shopBrandAppId.isNotNull()
                )
        ).forEach(
                partner -> shopIds.add(partner.getShopId())
        );

        ItemSyncReq itemSyncReq = new ItemSyncReq();
        itemSyncReq.setId(item.getId());
        itemSyncReq.setCategorys(item.getTags());
        itemSyncReq.setCode(item.getCode());
        itemSyncReq.setDesp(item.getDesp());
        itemSyncReq.setDetail(item.getDetail());
        LinkedHashSet imgs = new LinkedHashSet();
        imgs.addAll(item.getImgs());
        itemSyncReq.setImgs(imgs);
        itemSyncReq.setShopIds(shopIds);
        itemSyncReq.setStatus(item.getStatus().getCode());
        itemSyncReq.setTitle(item.getTitle());
        itemSyncReq.setVersion(item.getVersion());

        Set<ItemSyncReq.SpecDef> specDefs = new HashSet<>();
        item.getSpecs().forEach(
                specDef -> {
                    ItemSyncReq.SpecDef spec = new ItemSyncReq.SpecDef();
                    spec.setItemPropName(specDef.getItemProp().getName());

                    Set<String> propValues = new HashSet<>();

                    specDef.getItemPropValues().forEach(
                            itemPropValue -> propValues.add(itemPropValue.getName())
                    );
                    spec.setItemPropValueNames(propValues);
                    specDefs.add(spec);
                }
        );

        itemSyncReq.setSpecs(specDefs);

        Set<SkuMiniInfo> skuMiniInfos = new HashSet<>();
        skuRepo.findAll(
                allOf(
                        QSku.sku.item.eq(item),
                        QSku.sku.deleted.ne(true)
                )
        ).forEach(
                sku -> {
                    SkuMiniInfo skuMiniInfo = new SkuMiniInfo();
                    skuMiniInfo.setId(sku.getId());
                    skuMiniInfo.setSalePrice(sku.getSalePrice());
                    skuMiniInfo.setLabelPrice(sku.getLabelPrice());

                    // TODO buyerPrice
//                    skuMiniInfo.setBuyPrice(sku.getTagPrices());
                    skuMiniInfo.setCode(sku.getCode());
                    if (sku.getImgs() != null) {
                        skuMiniInfo.setImgs(sku.getImgs().get(0));
                    }
                    List<SkuMiniInfo.SkuSpec> skuSpecSet = new ArrayList<>();
                    sku.getSpecs().forEach(
                            spec -> {
                                SkuMiniInfo.SkuSpec skuSpec = new SkuMiniInfo.SkuSpec();
                                skuSpec.setItemPropName(spec.getItemProp().getName());
                                skuSpec.setItemPropValueName(spec.getItemPropValue().getName());
                                skuSpecSet.add(skuSpec);
                            }
                    );
                    skuMiniInfo.setSpecList(skuSpecSet);
                    skuMiniInfos.add(skuMiniInfo);
                }
        );
        itemSyncReq.setSkuList(skuMiniInfos);

        syncItemApi.syncItem(item.getBrandAppId(), itemSyncReq);
    }
}
