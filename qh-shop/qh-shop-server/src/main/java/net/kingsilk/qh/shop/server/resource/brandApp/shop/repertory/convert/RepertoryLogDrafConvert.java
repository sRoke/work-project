package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.repertorySku.dto.RepertorySkuRep;
import net.kingsilk.qh.shop.domain.RepertoryLog;
import org.springframework.core.convert.converter.Converter;

public class RepertoryLogDrafConvert implements Converter<RepertoryLog, RepertorySkuRep> {

    @Override
    public RepertorySkuRep convert(RepertoryLog repertoryLog) {

        RepertorySkuRep repertorySkuRep = new RepertorySkuRep();

        return null;
    }
}
