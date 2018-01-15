package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuRep;
import net.kingsilk.qh.shop.domain.Sku;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RepertorySkuConvert implements Converter<Sku, RepertorySkuRep> {


    @Override
    public RepertorySkuRep convert(Sku sku) {
        RepertorySkuRep repertorySkuRep = new RepertorySkuRep();

        repertorySkuRep.setBuyPrice(sku.getBuyPrice());
        repertorySkuRep.setCode(sku.getCode());
        repertorySkuRep.setDesp(sku.getDesp());
        repertorySkuRep.setDetail(sku.getDetail());
        repertorySkuRep.setEnbale(sku.getEnable());
        repertorySkuRep.setImgs(sku.getImgs());
        repertorySkuRep.setItemUnit(sku.getItemUnit());
        repertorySkuRep.setLabelPrice(sku.getLabelPrice());
        repertorySkuRep.setSalePrice(sku.getSalePrice());
        repertorySkuRep.setItemUnit(sku.getItemUnit());
        repertorySkuRep.setDesp(sku.getDesp());
        repertorySkuRep.setTitle(sku.getTitle());

        repertorySkuRep.setSpecs(
                sku.getSpecs()
                        .stream()
                        .map(spec ->
                                {
                                    RepertorySkuRep.Spec spec1 = new RepertorySkuRep.Spec();
                                    spec1.setItemPropId(spec.getItemPropId());
                                    spec1.setItemPropValue(spec.getItemPropValueId());
                                    return spec1;
                                }
                        ).collect(Collectors.toSet()
                )
        );
        return repertorySkuRep;
    }
}
