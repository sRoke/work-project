package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.domain.QSkuStore;
import net.kingsilk.qh.shop.domain.Sku;
import net.kingsilk.qh.shop.domain.SkuStore;
import net.kingsilk.qh.shop.repo.SkuStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Service
public class SkuStoreService {

    @Autowired
    private SkuStoreRepo skuStoreRepo;

    public boolean checkSkuStoreNum(Sku sku, int num){

        //检查库存
        SkuStore skuStore = skuStoreRepo.findOne(QSkuStore.skuStore.skuId.eq(sku.getId()));
        if (skuStore.getNum() >= num){
            return true;
        }
        return false;
    }

    //检查库存，并锁库存
    public SkuStore reduceSkuStoreNum(String skuId,int num){

        SkuStore skuStore = skuStoreRepo.findOne(QSkuStore.skuStore.skuId.eq(skuId));
        if (skuStore.getNum() < num){
            return null;
        }else {
            skuStore.setNum(skuStore.getNum() - num);
            return skuStore;
        }
    }

    //释放库存
    public void refreshSkuStore(Order order,String brandAppId){
        if (order == null){return;}
        for (Order.OrderItem item : order.getOrderItems()) {
            SkuStore skuStore = skuStoreRepo.findOne(allOf(
                    QSkuStore.skuStore.brandAppId.eq(brandAppId),
                    QSkuStore.skuStore.skuId.eq(item.getSkuId())));
            skuStore.setNum(skuStore.getNum() + Integer.parseInt(item.getNum()));
            skuStore.setSalesVolume(skuStore.getSalesVolume() - Integer.parseInt(item.getNum()));
            skuStoreRepo.save(skuStore);
        }
    }
}
