package net.kingsilk.qh.shop.service.search;

import net.kingsilk.qh.shop.domain.Item;
import net.kingsilk.qh.shop.domain.Sku;
import net.kingsilk.qh.shop.es.domain.EsItem;
import net.kingsilk.qh.shop.es.repo.EsItemRepository;
import net.kingsilk.qh.shop.repo.SkuRepo;
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
public class EsItemService {

    @Autowired
    private EsItemRepository esItemRepository;

    @Autowired
    private SkuRepo skuRepo;

    public void saveOrUpdate(Item item) {
        EsItem esItem = esItemRepository.findOneByItemId(item.getId());
        if (esItem == null) {
            esItem = new EsItem();
        }
        esItem.setItemId(item.getId());
        esItem.setBrandAppId(item.getBrandAppId());
        esItem.setCode(item.getCode());
        esItem.setStatusCode(item.getStatus().getCode());
        esItem.setStatusDesp(item.getStatus().getDesp());
        final Map<String, List<String>> specs = new HashMap<>();
//        item.getSpecs().forEach(
//                spec -> {
//                    List<String> propValues = new ArrayList<>();
//                    spec.getItemPropValueIds().forEach(
//                            propValue -> propValues.add(propValue.getName())
//
//                    );
//                    specs.put(spec.getItemProp().getName(), propValues);
//
//                }
//        );
//        Map<String, Integer> tagPrice = new HashMap<>();
        List<Sku> skuList = skuRepo.findAllByItemIdAndDeleted(item.getId(), false);

        esItem.setShopId(item.getShopId());
        esItem.setId(item.getId());
        esItem.setDateCreated(item.getDateCreated());
        esItem.setCreatedBy(item.getCreatedBy());
        esItem.setLastModifiedBy(item.getLastModifiedBy());
        esItem.setLastModifiedDate(item.getLastModifiedDate());
        esItem.setDeleted(item.isDeleted());
        esItem.setItemSpecs(specs);
        esItem.setTitle(item.getTitle());
        esItem.setDesp(item.getDesp());
        esItem.setImgs(item.getImgs());
        esItem.setCategorys(item.getCategorys());
        esItem.setDetail(item.getDetail());
        esItem.setItemUnit(item.getItemUnit());
        esItem.setOnSaleTime(item.getOnSaleTime());
        Sku minSku = skuList.stream().min(Comparator.comparing(Sku::getSalePrice)).orElse(new Sku());
        esItem.setSkuMinSalePrice(minSku.getSalePrice());
        esItemRepository.save(esItem);
    }


}
