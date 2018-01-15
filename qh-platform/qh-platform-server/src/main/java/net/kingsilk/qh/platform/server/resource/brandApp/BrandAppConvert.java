package net.kingsilk.qh.platform.server.resource.brandApp;

import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.platform.domain.BrandApp;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

@Component
public class BrandAppConvert {

    public BrandAppResp brandAppRespConvert(BrandApp brandApp) {
        BrandAppResp brandAppResp = new BrandAppResp();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss ");
        if (!StringUtils.isEmpty(brandApp.getDateCreated())) {
            brandAppResp.setCreatDate(simpleDateFormat.format(brandApp.getDateCreated()));
        }
        if (!StringUtils.isEmpty(brandApp.getExpireDate())) {
            brandAppResp.setDate(brandApp.getExpireDate());
        }
        if (!StringUtils.isEmpty(brandApp.getShopName())) {
            brandAppResp.setName(brandApp.getShopName());
        }
        if (!StringUtils.isEmpty(brandApp.getCreatedBy())) {
            brandAppResp.setCreatBy(brandApp.getCreatedBy());
        }
        if (!StringUtils.isEmpty(brandApp.getAppId())) {
            brandAppResp.setId(brandApp.getId());
        }
        if (!StringUtils.isEmpty(brandApp.getWxMpId())) {
            brandAppResp.setWxMpId(brandApp.getWxMpId());
        }
        if (!StringUtils.isEmpty(brandApp.getWxComAppId())) {
            brandAppResp.setWxComAppId(brandApp.getWxComAppId());
        }
        return brandAppResp;
    }
}
