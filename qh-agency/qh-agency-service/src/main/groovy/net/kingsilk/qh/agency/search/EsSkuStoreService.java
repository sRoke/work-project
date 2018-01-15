package net.kingsilk.qh.agency.search;

import net.kingsilk.qh.agency.domain.Sku;
import net.kingsilk.qh.agency.domain.SkuStore;
import net.kingsilk.qh.agency.es.domain.EsSkuStore;
import net.kingsilk.qh.agency.es.repo.EsSkuStoreRepository;
import net.kingsilk.qh.agency.repo.SkuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class EsSkuStoreService {

    @Autowired
    private EsSkuStoreRepository esSkuStroreRepository;

    @Autowired
    private SkuRepo skuRepo;

    public void saveOrUpdate(SkuStore skuStore) {
        EsSkuStore esSkuStore = esSkuStroreRepository.findOneBySkuStoreId(skuStore.getId());
        if (esSkuStore == null) {
            esSkuStore = new EsSkuStore();
        }
        esSkuStore.setSkuStoreId(skuStore.getId());
        esSkuStore.setBrandAppId(skuStore.getBrandAppId());
        esSkuStore.setPartnerId(skuStore.getPartner().getId());
        esSkuStore.setNum(skuStore.getNum());
        esSkuStore.setSalesVolume(skuStore.getSalesVolume());
        esSkuStore.setSkuId(skuStore.getSku().getId());
        if (skuStore.getPartner().getParent()!=null){
        esSkuStore.setParentPartnerId(skuStore.getPartner().getParent().getId());
        }
        esSkuStore.setUserId(skuStore.getPartner().getUserId());
        esSkuStore.setPartnerTypeEnum(skuStore.getPartner().getPartnerTypeEnum().getDesp());
        esSkuStore.setShopName(skuStore.getPartner().getShopName());
        esSkuStore.setItemId(skuStore.getSku().getItem().getId());
        esSkuStore.setLabelPrice(skuStore.getSku().getLabelPrice());
        esSkuStore.setSalePrice(skuStore.getSku().getSalePrice());
        esSkuStore.setCode(skuStore.getSku().getCode());
        Map<String, Integer> tagPrice = new HashMap<>();
        skuStore.getSku().getTagPrices().forEach(
                price -> tagPrice.put(price.getTag().getCode(), price.getPrice())
        );
//        List<Sku> skuList = skuRepo.findAllByItemAndDeleted(skuStore.getSku().getItem(), false);
//        Sku minSku = skuList.stream().min(Comparator.comparing(Sku::getSalePrice)).get();
//        esSkuStore.setSkuMinSalePrice(minSku.getSalePrice());
        esSkuStore.setTagPrices(tagPrice);
        esSkuStore.setTitle(skuStore.getSku().getItem().getTitle());
        esSkuStore.setDesp(skuStore.getSku().getItem().getDesp());
        esSkuStore.setImgs(skuStore.getSku().getItem().getImgs());
        esSkuStore.setTags(skuStore.getSku().getItem().getTags());
        Map<String,String> map=new HashMap();
        skuStore.getSku().getSpecs().forEach(
                spec -> map.put(spec.getItemProp().getName(),spec.getItemPropValue().getName())
        );
        esSkuStore.setSpecs(map);
        esSkuStroreRepository.save(esSkuStore);
    }
}
