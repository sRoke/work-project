package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.repertorySkuLog.dto.RepertorySkuLogRep;
import net.kingsilk.qh.shop.domain.RepertoryLog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepertorySkuLogRepConvert implements Converter<RepertoryLog, RepertorySkuLogRep> {
    @Override
    public RepertorySkuLogRep convert(RepertoryLog repertoryLog) {
        return null;
    }
}
