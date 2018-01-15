package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertoryLogRep;
import net.kingsilk.qh.shop.domain.RepertoryLog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepertorySkuLogConvert implements Converter<RepertoryLog.RepertorySku, RepertoryLogRep> {

    @Override
    public RepertoryLogRep convert(RepertoryLog.RepertorySku repertorySku) {

        return null;
    }

}
