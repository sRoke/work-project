package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertorySkuLogRep;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.RepertoryRepo;
import net.kingsilk.qh.shop.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepertoryLogRepConvert implements Converter<RepertoryLog, RepertoryLogRep> {

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private RepertoryRepo repertoryRepo;

    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    public RepertoryLogRep convert(RepertoryLog repertoryLog) {

        RepertoryLogRep repertorySkuLogRep = new RepertoryLogRep();
        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryLog.getRepertoryId()),
                        QRepertory.repertory.shopId.eq(repertoryLog.getShopId()),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(repertoryLog.getBrandAppId())
                )
        );
        Optional.ofNullable(repertory).ifPresent(it ->
                repertorySkuLogRep.setRepertory(it.getName())
        );

        Supplier supplier = supplierRepo.findOne(
                Expressions.allOf(
                        QSupplier.supplier.id.eq(repertoryLog.getRepertoryId()),
                        QSupplier.supplier.shopId.eq(repertoryLog.getShopId()),
                        QSupplier.supplier.deleted.eq(false),
                        QSupplier.supplier.brandAppId.eq(repertoryLog.getBrandAppId())
                )
        );
        Optional.ofNullable(supplier).ifPresent(it ->
                repertorySkuLogRep.setSupplier(it.getName())
        );
        repertorySkuLogRep.setAmount(repertoryLog.getAmount());
        repertorySkuLogRep.setMemo(repertoryLog.getMemo());
        repertorySkuLogRep.setOperate(repertoryLog.getOperate().getDesp());
        repertorySkuLogRep.setOperator(repertoryLog.getOperator());
        repertorySkuLogRep.setRealAmount(repertoryLog.getRealAmount());

        repertorySkuLogRep.setTotal(repertoryLog.getTotal());
        repertorySkuLogRep.setTotalAmount(repertoryLog.getTotalAmount());
//TODO 日期格式？ 是否自己可选？
//        repertorySkuLogRep.setRepertorySkus(
//                getRepertorySkus(repertoryLog)
//                        .stream()
//                        .map(it ->
//                                conversionService.convert(it, RepertorySkuRep.class)
//                        )
//                        .collect(Collectors.toList())
//        );
        return repertorySkuLogRep;
    }

    private List<RepertoryLog.RepertorySku> getRepertorySkus(RepertoryLog repertoryLog) {
        List<RepertoryLog.RepertorySku> list = new LinkedList<>();
        repertoryLog.getRepertoryItems().forEach(repertoryItem ->
                list.addAll(repertoryItem.getRepertorySkus())
        );
        return list;
    }
}
