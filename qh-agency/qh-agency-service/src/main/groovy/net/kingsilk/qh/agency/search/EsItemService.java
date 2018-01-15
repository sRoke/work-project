package net.kingsilk.qh.agency.search;

import net.kingsilk.qh.agency.domain.Item;
import net.kingsilk.qh.agency.domain.Sku;
import net.kingsilk.qh.agency.es.domain.EsItem;
import net.kingsilk.qh.agency.es.repo.EsItemRepository;
import net.kingsilk.qh.agency.repo.ItemRepo;
import net.kingsilk.qh.agency.repo.SkuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class EsItemService {

    @Autowired
    private EsItemRepository esItemRepository;

    @Autowired
    private SkuRepo skuRepo;
    public void saveOrUpdate(Item item)  {
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
        item.getSpecs().forEach(
                spec -> {
                    List<String> propValues = new ArrayList<>();
                    spec.getItemPropValues().forEach(
                            propValue -> propValues.add(propValue.getName())

                    );
                    specs.put(spec.getItemProp().getName(), propValues);

                }
        );
        Map<String, Integer> tagPrice = new HashMap<>();
        List<Sku> skuList=skuRepo.findAllByItemAndDeleted(item,false);

        if (skuList.size()>0){
            skuList.get(0).getTagPrices().forEach(
                    price -> tagPrice.put(price.getTag().getCode(), price.getPrice())
            );
        }

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
        esItem.setTags(item.getTags());
        esItem.setDetail(item.getDetail());
        esItem.setItemUnit(item.getItemUnit());
        esItem.setOnSaleTime(item.getOnSaleTime());
        esItem.setTagPrice(tagPrice);
        Sku minSku = skuList.stream().min(Comparator.comparing(Sku::getSalePrice)).get();
        esItem.setSkuMinSalePrice(minSku.getSalePrice());
        esItemRepository.save(esItem);
    }


}
