package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto.RepertoryResp;
import net.kingsilk.qh.shop.domain.Repertory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RepertoryConvert implements Converter<Repertory, RepertoryResp> {

    @Override
    public RepertoryResp convert(Repertory source) {

        RepertoryResp repertoryResp = new RepertoryResp();
        repertoryResp.setEnable(source.getEnable());
        repertoryResp.setManager(source.getManager());
        repertoryResp.setMemo(source.getMemo());
        repertoryResp.setName(source.getName());
        return repertoryResp;
    }
}
