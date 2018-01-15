package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.RepertorySkuLogApi;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogResp;
import net.kingsilk.qh.shop.core.RepertoryOperateEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.RepertoryLogRepo;
import net.kingsilk.qh.shop.repo.RepertoryRepo;
import net.kingsilk.qh.shop.repo.SkuRepo;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class RepertorySkuLogResource implements RepertorySkuLogApi {

    @Autowired
    private RepertoryLogRepo repertoryLogRepo;

    @Autowired
    private RepertoryRepo repertoryRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SkuRepo skuRepo;

    @Override
    public UniResp<RepertoryLogRep> info(String brandAppId, String shopId, String repertoryId, String skuLogId) {

        RepertoryLog repertoryLog = repertoryLogRepo.findOne(
                Expressions.allOf(
                        QRepertoryLog.repertoryLog.id.eq(skuLogId),
                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
                        QRepertoryLog.repertoryLog.deleted.eq(false),
                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId)
                )
        );
        UniResp<RepertoryLogRep> uniResp = new UniResp<>();
        Optional.ofNullable(repertoryLog).ifPresent(it ->
                {
                    RepertoryLogRep repertorySkuLogRep = conversionService.convert(it, RepertoryLogRep.class);
                    uniResp.setStatus(HttpStatus.SC_OK);
                    uniResp.setData(repertorySkuLogRep);
                }
        );
        return uniResp;
    }

    @Override
    public UniResp<String> update(String brandAppId, String shopId, String repertoryId, String skuLogId, RepertoryLogResp repertoryLogResp) {

        UniResp<String> uniResp = new UniResp<>();
        if (repertoryLogResp == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "参数为空");
        }
        RepertoryLog repertoryLog = repertoryLogRepo.findOne(
                Expressions.allOf(
                        QRepertoryLog.repertoryLog.id.eq(skuLogId),
                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
                        QRepertoryLog.repertoryLog.deleted.eq(false),
                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId)
                )
        );
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
        //更新前的值
        List<RepertoryLog.RepertorySku> repertorySkuListOld = getRepertorySkus(repertoryLog);
        List<RepertoryLog.RepertorySku> repertorySkus = new LinkedList<>();
        repertorySkus.addAll(repertorySkuListOld);
        repertoryLog.setBrandAppId(brandAppId);
        repertoryLog.setBrandAppId(brandAppId);
        repertoryLog.setMemo(repertoryLogResp.getMemo());
        repertoryLog.setShopId(shopId);
        List<RepertoryLogResp.RepertorySku> respRepertorySkus = repertoryLogResp.getRepertorySkus();
        repertorySkuListOld.forEach(repertorySku ->
                Optional.ofNullable(repertorySku).ifPresent(it ->
                        respRepertorySkus.stream()
                                .filter(repertorySkuRep ->
                                        it.getSkuId().equals(repertorySkuRep.getSkuId())
                                )
                                .findFirst()
                                .ifPresent(repertorySkuRep ->
                                        it.setNum(repertorySkuRep.getNum())
                                )
                )
        );
        repertoryLogRepo.save(repertoryLog);
        RepertoryLog.RepertorySku r = new RepertoryLog().new RepertorySku();
        r.setNum("0");
        List<Repertory.RepertorySku> repertorySkuList = getRepertorySkus(repertory);
        repertorySkuList
                .forEach(repertorySku ->
                        Optional.ofNullable(repertorySku)
                                .ifPresent(it ->
                                        respRepertorySkus.stream()
                                                .filter(repertorySkuRep ->
                                                        it.getSkuId().equals(repertorySkuRep.getSkuId())
                                                )
                                                .findFirst()
                                                .ifPresent(repertorySkuRep ->
                                                        it.setNum(it.getNum() +
                                                                (
                                                                        //TODO 记录中加了oldNum字段
                                                                        //减去原始记录的值，增加更新后的值
                                                                        RepertoryOperateEnum.IN.equals(repertoryLogResp.getOperate())
                                                                                ?
                                                                                Integer.parseInt(repertorySkuRep.getNum()) -
                                                                                        Integer.parseInt(
                                                                                                //找到对应SKU原始记录的值
                                                                                                repertorySkus.stream().filter(it1 ->
                                                                                                        it.getSkuId().equals(it1.getSkuId())
                                                                                                )
                                                                                                        .findFirst()
                                                                                                        .orElse(r)
                                                                                                        .getNum()
                                                                                        )
                                                                                :
                                                                                Integer.parseInt(
                                                                                        repertorySkus.stream().filter(it1 ->
                                                                                                it.getSkuId().equals(it1.getSkuId())
                                                                                        )
                                                                                                .findFirst()
                                                                                                .orElse(r)
                                                                                                .getNum()
                                                                                ) - Integer.parseInt(repertorySkuRep.getNum())
                                                                )
                                                        )
                                                )
                                )
                );
        repertoryRepo.save(repertory);
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertory.getId());

        return uniResp;
    }

    @Override
    public UniResp<String> delet(String brandAppId, String shopId, String repertoryId, String skuLogId) {
        UniResp<String> uniResp = new UniResp<>();
        RepertoryLog repertoryLog = repertoryLogRepo.findOne(
                Expressions.allOf(
                        QRepertoryLog.repertoryLog.id.eq(skuLogId),
                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
                        QRepertoryLog.repertoryLog.deleted.eq(false),
                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId)
                )
        );
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
        if (repertoryLog == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        repertoryLog.setDeleted(true);
        repertoryLogRepo.save(repertoryLog);

        List<Repertory.RepertorySku> repertorySkuList = getRepertorySkus(repertory);
        repertorySkuList
                .forEach(repertorySku ->
                        Optional.ofNullable(repertorySku).ifPresent(it ->
                               getRepertorySkus( repertoryLog).stream()
                                        .filter(repertorySkuRep ->
                                                it.getSkuId().equals(repertorySkuRep.getSkuId())
                                        )
                                        .findFirst()
                                        .ifPresent(repertorySkuRep ->
                                                it.setNum(it.getNum() +
                                                        (
                                                                RepertoryOperateEnum.IN.equals(repertoryLog.getOperate())
                                                                        ?
                                                                        -Integer.parseInt(repertorySkuRep.getNum())
                                                                        :
                                                                        Integer.parseInt(repertorySkuRep.getNum())
                                                        )
                                                )
                                        )
                        )
                );
        repertoryRepo.save(repertory);

        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertoryLog.getId());
        return uniResp;
    }

//    @Override
//    public UniResp<UniPageResp<RepertorySkuLogRep>> page(String brandAppId, String shopId, String repertoryId, int size, int page, List<String> sort, String keyWord) {
//        Sort s = ParamUtils.toSort(sort);
//
//        PageRequest pageRequest = new PageRequest(page, size, s);
//
//        Page<RepertoryLog> repertoryLogPage = repertoryLogRepo.findAll(
//                Expressions.allOf(
//                        QRepertoryLog.repertoryLog.repertoryId.eq(repertoryId),
//                        QRepertoryLog.repertoryLog.shopId.eq(shopId),
//                        QRepertoryLog.repertoryLog.deleted.eq(false),
//                        QRepertoryLog.repertoryLog.brandAppId.eq(brandAppId)
//                ),pageRequest
//        );
//        if (repertoryLogPage.getContent().isEmpty()) {
//            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
//        }
//        Page<RepertorySkuLogRep> repertorySkuLogReps = repertoryLogPage.map(pepertoryLog ->
//            conversionService.convert(pepertoryLog, RepertorySkuLogRep.class)
//        );
//        UniPageResp<RepertorySkuLogRep> uniPageResp = conversionService.convert(repertorySkuLogReps, UniPageResp.class);
//        uniPageResp.setContent(repertorySkuLogReps.getContent());
//        UniResp<UniPageResp<RepertorySkuLogRep>> uniResp = new UniResp<>();
//        uniResp.setData(uniPageResp);
//        uniResp.setStatus(ErrStatus.OK);
//        return uniResp;
//    }

    private List<Repertory.RepertorySku> getRepertorySkus(Repertory repertory) {
        List<Repertory.RepertorySku> list = new LinkedList<>();
        repertory.getRepertoryItems().forEach(repertoryItem ->
                list.addAll(repertoryItem.getRepertorySkus())
        );
        return list;
    }

    private List<RepertoryLog.RepertorySku> getRepertorySkus(RepertoryLog repertoryLog) {
        List<RepertoryLog.RepertorySku> list = new LinkedList<>();
        repertoryLog.getRepertoryItems().forEach(repertoryItem ->
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

}
