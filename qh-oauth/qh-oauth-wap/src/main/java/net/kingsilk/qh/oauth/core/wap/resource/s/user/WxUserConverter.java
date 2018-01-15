package net.kingsilk.qh.oauth.core.wap.resource.s.user;

import net.kingsilk.qh.oauth.api.s.user.dto.InfoResp;
import net.kingsilk.qh.oauth.domain.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;

@Component
public class WxUserConverter implements Converter<WxUser, InfoResp.WxUser> {

    @Autowired
    private ObjectProvider<ConversionService> csProvider;

    @Override
    public InfoResp.WxUser convert(WxUser source) {

        if (source == null) {
            return null;
        }
        InfoResp.WxUser target = new InfoResp.WxUser();

        target.setAppId(source.getAppId());
        target.setOpenId(source.getOpenId());
        target.setUnionId(source.getUnionId());

        return target;
    }

}
