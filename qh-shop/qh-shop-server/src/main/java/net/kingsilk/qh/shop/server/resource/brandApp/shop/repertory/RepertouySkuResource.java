package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.RepertouySkuApi;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.*;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import net.kingsilk.qh.shop.core.RepertoryOperateEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ItemRepo;
import net.kingsilk.qh.shop.repo.RepertoryLogRpeo;
import net.kingsilk.qh.shop.repo.RepertoryRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import net.kingsilk.qh.shop.service.util.DbUtil;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepertouySkuResource implements RepertouySkuApi {

    @Autowired
    private RepertoryRepo repertoryRepo;

    @Autowired
    private SkuRepo skuRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private RepertoryLogRpeo repertoryLogRpeo;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public UniResp<RepertoryItemRep> info(String brandAppId, String shopId, String repertoryId, String itemId) {
        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        if (repertory == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }

        Item item = itemRepo.findOne(
                Expressions.allOf(
                        QItem.item.id.eq(itemId),
                        QItem.item.deleted.ne(true),
                        QItem.item.shopId.eq(shopId),
                        QItem.item.brandAppId.eq(brandAppId)
                )
        );
        if (item == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
//        List<Sku> skus = Lists.newArrayList(skuRepo.findOne(
//                Expressions.allOf(
//                        QSku.sku.itemId.eq(itemId),
//                        QSku.sku.shopId.eq(shopId),
//                        QSku.sku.brandAppId.eq(brandAppId)
//                )
//        ));
//        if (skus.isEmpty()) {
//            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
//        }
//        List<RepertorySkuRep> repertorySkuReps = skus.stream()
//                .map(sku ->
//                        conversionService.convert(sku, RepertorySkuRep.class)
//                )
//                .collect(Collectors.toList());
//
//        if (!repertory.getRepertorySkus().isEmpty()) {
//            //找到SKU对应的库存数量
//            repertorySkuReps.forEach(skuRep ->
//                    repertory.getRepertorySkus().stream()
//                            .filter(sku1 ->
//                                    sku1.getSkuId().equals(skuRep.getSkuId())
//                            )
//                            .findFirst()
//                            .ifPresent(it ->
//                                    skuRep.setNum(it.getNum()
//                                    )
//                            ));
//        }
        RepertoryItemRep repertoryItemRep = new RepertoryItemRep();
        List<Repertory.RepertoryItem> repertoryItems = repertory.getRepertoryItems();
        Optional.ofNullable(repertoryItems)
                .ifPresent(items ->
                        items.stream()
                                .filter(item1 ->
                                        itemId.equals(item1.getItemId())
                                )
                                .findFirst()
                                .ifPresent(item2 ->
                                        repertoryItemRep.setRepertorySkuReps(item2.getRepertorySkus().stream()
                                                .map(repertorySku ->
                                                        conversionService.convert(repertorySku, RepertorySkuRep.class)
                                                ).collect(Collectors.toList())
                                        )
                                )

                );

//TODO 出入库信息
        List<RepertoryLog> repertoryLogs = Lists.newArrayList(repertoryLogRpeo.findAll(
                Expressions.allOf(
                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId),
                        QRepertoryLog.repertoryLog.deleted.ne(true)
                )
        ));
        List<RepertoryLog.RepertorySku> logs = new LinkedList<>();
        repertoryLogs.forEach(it -> {
                    Optional.ofNullable(it.getRepertoryItems()).ifPresent(items ->
                            items.stream().filter(item1 ->
                                    itemId.equals(item1.getItemId())
                            ).findFirst().ifPresent(newLog ->
                                    logs.addAll(newLog.getRepertorySkus())
                            )
                    );
                }
        );

        repertoryItemRep.setCode(item.getCode());
        repertoryItemRep.setDesp(item.getDesp());
        repertoryItemRep.setImgs(Lists.newArrayList(item.getImgs()));
        repertoryItemRep.setStatus(item.getStatus());
        repertoryItemRep.setTitle(item.getTitle());
        UniResp<RepertoryItemRep> uniResp = new UniResp<>();
        uniResp.setData(repertoryItemRep);
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

    @Override
    public UniResp<RepertoryItemPageRep> page(String brandAppId, String shopId, String repertoryId, int size, int page, List<String> sort, String keyWord) {
        Sort s = ParamUtils.toSort(sort);

        PageRequest pageRequest = new PageRequest(page, size, s);

        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        UniPageResp<RepertorySkuRep> uniPageResp = new UniPageResp<>();
        if (repertory == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        List<Repertory.RepertoryItem> items = repertory.getRepertoryItems();
        RepertoryItemPageRep repertoryItemPageRep = new RepertoryItemPageRep();
        if (!items.isEmpty()) {
            UniPageResp<ItemRep> itemRepUniPageResp = new UniPageResp<>();
            List<ItemRep> itemReps = items.stream().map(it ->
                    {
                        ItemRep itemRep = new ItemRep();
                        itemRep.setItemId(it.getItemId());
                        itemRep.setSum(it.getTotalSku());
                        return itemRep;
                    }
            ).collect(Collectors.toList());
            itemRepUniPageResp.setContent(itemReps);
            itemRepUniPageResp.setNumber(0);
            itemRepUniPageResp.setSize(size);
            itemRepUniPageResp.setTotalElements(itemReps.size());
            itemRepUniPageResp.setTotalPages(itemReps.size() / size);
            //找到全部sku
//            Page<Sku> skuAll = skuRepo.findAll(
//                    Expressions.allOf(
//                            DbUtil.opIn(QSku.sku.id, skuList)
//                    ), pageRequest
//            );
//            Page<RepertorySkuRep> repertoryResps = skuAll.map(sku ->
//                    {
//                        RepertorySkuRep skuRep = conversionService.convert(sku, RepertorySkuRep.class);
//                        //找到对应的sku，将对应的库存数量传出
//                        skus.stream()
//                                .filter(sku1 ->
//                                        sku1.getSkuId().equals(sku.getId())
//                                )
//                                .findFirst()
//                                .ifPresent(it ->
//                                        skuRep.setNum(it.getNum()
//                                        )
//                                );
//                        return skuRep;
//                    }
//            );
//
//            uniPageResp = conversionService.convert(repertoryResps, UniPageResp.class);
//            uniPageResp.setContent(repertoryResps.getContent());

            //TODO
//            itemReps.stream().mapToInt()
            repertoryItemPageRep.setTotal(itemReps.size());
//            repertoryItemPageRep.setTotalPrice();
//            repertoryItemPageRep.setTotalSku();
            repertoryItemPageRep.setUniPageResp(itemRepUniPageResp);
        }
        UniResp<RepertoryItemPageRep> uniResp = new UniResp<>();
        uniResp.setData(repertoryItemPageRep);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<RepertorySkuRep> create(String brandAppId, String shopId, String repertoryId, String skuId) {
        //TODO
        return null;
    }

    @Override
    public UniResp<String> add(String brandAppId, String shopId, String repertoryId, RepertorySkuCreateRep repertorySkuCreateRep) {

        inOrOutRepertory(brandAppId, shopId, repertoryId, repertorySkuCreateRep.getDraf(), repertorySkuCreateRep.getOperator(),repertorySkuCreateRep.getMemo(), repertorySkuCreateRep.getRepertorySkuReps(), RepertoryOperateEnum.IN);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> out(String brandAppId, String shopId, String repertoryId, RepertorySkuCreateRep repertorySkuCreateRep) {

        inOrOutRepertory(brandAppId, shopId, repertoryId,  repertorySkuCreateRep.getDraf(), repertorySkuCreateRep.getOperator(),repertorySkuCreateRep.getMemo(), repertorySkuCreateRep.getRepertorySkuReps(), RepertoryOperateEnum.OUT);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<RepertoryLogRep> drafInfo(String brandAppId, String shopId, String repertoryId) {
        List<RepertoryLog> repertoryLogDrafs = Lists.newArrayList(repertoryLogRpeo.findAll(
                Expressions.allOf(
                        QRepertoryLog.repertoryLog.deleted.eq(false),
                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId),
                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.draftId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.draft.eq(true)
                ), new Sort(Sort.Direction.DESC, "dateCreated")
        ));
        UniResp<RepertoryLogRep> uniResp = new UniResp<>();
        repertoryLogDrafs.stream().findFirst().ifPresent(repertoryLogDraf ->
                uniResp.setData(conversionService.convert(repertoryLogDraf, RepertoryLogRep.class))
        );
        uniResp.setStatus(HttpStatus.SC_OK);
        return uniResp;
    }

//    private boolean inRepertory(String brandAppId, String shopId, String repertoryId,String supplierId, Boolean draf, String operator, String memo, List<RepertorySkuRep> repertorySkuReps, RepertoryOperateEnum repertoryOperateEnum){
//
//        List<RepertoryLog> repertoryLogs = Lists.newArrayList(repertoryLogRpeo.findAll(
//                Expressions.allOf(
//                        QRepertoryLog.repertoryLog.deleted.eq(false),
//                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId),
//                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
//                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
//                        QRepertoryLog.repertoryLog.draftId.eq(shopId),
//                        QRepertoryLog.repertoryLog.draft.eq(true),
//                        QRepertoryLog.repertoryLog.operate.eq(repertoryOperateEnum)
//                ), new Sort(Sort.Direction.DESC, "dateCreated")
//        ));
//        RepertoryLog repertoryLog = new RepertoryLog();
//        if(!repertoryLogs.isEmpty()){
//            repertoryLog = repertoryLogs.get(0);
//        }
//        //库存记录
//        repertoryLog.setRepertoryId(repertoryId);
//        repertoryLog.setBrandAppId(brandAppId);
//        repertoryLog.setOperate(repertoryOperateEnum);
//        repertoryLog.setSupplierId(supplierId);
//        repertoryLog.setShopId(shopId);
//        repertoryLog.setOperator(operator);
//        repertoryLog.setMemo(memo);
//        repertoryLog.setRepertoryId(shopId);
//        //记录库存数量变更
//        List<RepertoryLog.RepertoryItem> repertoryItems1 = repertoryLog.getRepertoryItems();
//        Sku sku = skuRepo.findOne(repertorySkuReps.get(0).getSkuId());
//        Optional.ofNullable(repertoryItems1).ifPresent(repertoryItems ->
//                repertoryItems.stream().filter(item ->
//                        sku.getItemId().equals(item.getItemId())
//                ).findFirst().ifPresent(item ->
//                        {
//                            List<RepertoryLog.RepertorySku> repertorySkus = item.getRepertorySkus();
//                            repertorySkus.add(conversionService.convert(RepertorySkuRep));
//                        }
//                )
//        );
//
//
//
//        repertoryLogRpeo.save(repertoryLog);
//        return true;
//    }

    private List<Repertory.RepertorySku> getRepertorySkus(Repertory repertory) {
        List<Repertory.RepertorySku> list = new LinkedList<>();
        repertory.getRepertoryItems().forEach(repertoryItem ->
                list.addAll(repertoryItem.getRepertorySkus())
        );
        return list;
    }

    //每次保存只针对一个item
    private void setRepertorySkus(Repertory repertory, List<Repertory.RepertorySku> skuList) {
        Sku sku = skuRepo.findOne(skuList.get(0).getSkuId());
        String itemId = sku.getItemId();
        List<Repertory.RepertoryItem> repertoryItems = repertory.getRepertoryItems();
        repertoryItems.stream().filter(it ->
                itemId.equals(it)
        ).findFirst().ifPresent(it ->
                {
                    it.setTotalSku(
                            skuList.stream()
                                    .mapToInt(itSku ->
                                            Integer.parseInt(itSku.getNum())
                                    )
                                    .sum()
                    );
                    it.setRepertorySkus(skuList);
                }
        );

        repertory.setRepertoryItems(repertoryItems);
    }

    //每次保存只针对一个item
    private void setRepertorySkus(RepertoryLog repertoryLog, List<RepertoryLog.RepertorySku> skuList) {
        Sku sku = skuRepo.findOne(skuList.get(0).getSkuId());
        String itemId = sku.getItemId();
        List<RepertoryLog.RepertoryItem> repertoryItems = repertoryLog.getRepertoryItems();
        repertoryItems.stream().filter(it ->
                itemId.equals(it)
        ).findFirst().ifPresent(it ->
                {
                    it.setTotalSku(
                            skuList.stream()
                                    .mapToInt(itSku ->
                                            Integer.parseInt(itSku.getNum())
                                    )
                                    .sum()
                    );
                    it.setRepertorySkus(skuList);
                }
        );

        repertoryLog.setRepertoryItems(repertoryItems);
    }

    private boolean inOrOutRepertory(String brandAppId, String shopId, String repertoryId, Boolean draf, String operator, String memo, List<RepertorySkuRep> repertorySkuReps, RepertoryOperateEnum repertoryOperateEnum) {
        Repertory repertory = new Repertory();
        RepertoryLog repertoryLog = new RepertoryLog();

        if (draf == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        if (repertorySkuReps == null || repertorySkuReps.isEmpty()) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "没有入库信息");
        }

        Repertory repertory1 = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.draftId.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        if (repertory1 == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        List<Repertory.RepertorySku> repertorySkus = getRepertorySkus(repertory1);
        List<Repertory.RepertorySku> repertorySkuListOld = new LinkedList<>();
        repertorySkuListOld.addAll(repertorySkus);
        //保存草稿
        if (draf) {

            Repertory repertoryDraf = new Repertory();
            repertoryDraf.setDraftId(repertory1.getId());
            repertory = repertoryDraf;
            repertoryLog.setDraft(true);
            repertoryLog.setDraftId(repertory1.getId());
            //保存库存
        } else {
            repertory = repertory1;
            List<Repertory> repertoryDrafs = Lists.newArrayList(repertoryRepo.findOne(
                    Expressions.allOf(
                            QRepertory.repertory.brandAppId.eq(brandAppId),
                            QRepertory.repertory.shopId.eq(shopId),
                            QRepertory.repertory.id.ne(repertoryId),
                            QRepertory.repertory.draftId.eq(repertoryId),
                            QRepertory.repertory.draft.eq(true),
                            QRepertory.repertory.deleted.eq(false)
                    )
            ));
            //如果找到草稿,删除草稿
            if (!repertoryDrafs.isEmpty()) {
                repertoryDrafs.forEach(repertoryDraf ->
                        repertoryDraf.setDeleted(true)
                );
                repertoryRepo.save(repertoryDrafs);
            }
            //可能有多条草稿
            List<RepertoryLog> repertoryLogDrafs = Lists.newArrayList(repertoryLogRpeo.findAll(
                    Expressions.allOf(
                            QRepertoryLog.repertoryLog.deleted.eq(false),
                            QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId),
                            QRepertoryLog.repertoryLog.shopId.eq(shopId),
                            QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                            QRepertoryLog.repertoryLog.draftId.eq(repertoryId),
                            QRepertoryLog.repertoryLog.draft.eq(true)
                    )
            ));
            if (!repertoryLogDrafs.isEmpty()) {
                repertoryLogDrafs.forEach(repertoryLogd ->
                        repertoryLogd.setDeleted(true)
                );
                repertoryLogRpeo.save(repertoryLogDrafs);
            }
        }
        List<String> stringList = repertorySkuReps
                .stream()
                .map(RepertorySkuRep::getSkuId)
                .collect(Collectors.toList());
        //防止sku信息错误
        List<Sku> skus = Lists.newArrayList(skuRepo.findAll(
                Expressions.allOf(
                        DbUtil.opIn(QSku.sku.id, stringList),
                        QSku.sku.shopId.eq(shopId),
                        QSku.sku.brandAppId.eq(brandAppId)
                )
        ));

        if (skus.isEmpty()) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到sku");
        }

        //找到对应的sku更新库存数量
        repertorySkus.forEach(repertorySku ->
                Optional.ofNullable(repertorySku).ifPresent(it ->
                        repertorySkuReps.stream()
                                .filter(repertorySkuRep ->
                                        it.getSkuId().equals(repertorySkuRep.getSkuId())
                                )
                                .findFirst()
                                .ifPresent(repertorySkuRep ->
                                        it.setNum(repertorySkuRep.getNum())
                                )
                )
        );
        setRepertorySkus(repertory, repertorySkus);
        repertory.setDraft(draf);
        repertoryRepo.save(repertory);
        //库存记录
        repertoryLog.setBrandAppId(brandAppId);
        repertoryLog.setOperate(repertoryOperateEnum);
        repertoryLog.setShopId(shopId);
        repertoryLog.setOperator(operator);
        repertoryLog.setMemo(memo);
        repertoryLog.setRepertoryId(repertoryId);
        List<RepertoryLog.RepertorySku> repertorySkuList = new LinkedList<>();
        //记录库存数量变更
        repertorySkuList.addAll(repertorySkuReps
                .stream()
                .map(repertorySkuRep ->
                        {
                            RepertoryLog.RepertorySku repertorySku = repertoryLog.new RepertorySku();
                            repertorySku.setSkuId(repertorySkuRep.getSkuId());
                            repertorySku.setNum(repertorySkuRep.getNum());
                            //找到原始的各个sku
                            repertorySkuListOld.stream().filter(it ->
                                    repertorySkuRep.getSkuId().equals(it.getSkuId())
                            )
                                    .findFirst()
                                    .ifPresent(it ->
                                                    //repertorySku库存记录更新
                                            {
                                                repertorySku.setNum(
                                                        String.valueOf(
                                                                Math.abs(Integer.parseInt(it.getNum()) - Integer.parseInt(repertorySkuRep.getNum())
                                                                )
                                                        )
                                                );
                                                repertorySku.setOldNum(Integer.parseInt(it.getNum()));
                                                repertorySku.setNewNum(Integer.parseInt(repertorySkuRep.getNum()));
                                            }
                                    );
                            return repertorySku;
                        }
                )
                .collect(Collectors.toList()));
        setRepertorySkus(repertoryLog, repertorySkuList);
        repertoryLogRpeo.save(repertoryLog);
        return true;
    }
}
