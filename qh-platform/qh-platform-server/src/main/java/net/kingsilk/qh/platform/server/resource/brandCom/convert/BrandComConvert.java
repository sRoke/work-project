package net.kingsilk.qh.platform.server.resource.brandCom.convert;

import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import net.kingsilk.qh.platform.domain.BrandCom;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BrandComConvert {
    public BrandComGetResp brandComGetRespConvert(BrandComGetResp brandComGetResp, BrandCom brandCom) {
        brandComGetResp.setStatus(brandCom.getStatus());
        brandComGetResp.setId(brandCom.getId());
        brandComGetResp.setTitle(brandCom.getBrandComName());
        brandComGetResp.setLogUrl(brandCom.getLogo());
        brandComGetResp.setPhone(brandCom.getPhone());
        brandComGetResp.setCategory(brandCom.getCategory());
        brandComGetResp.setDateCreated(brandCom.getDateCreated());
        brandComGetResp.setCreatedBy(brandCom.getCreatedBy());
        brandComGetResp.setLastModifiedDate(brandCom.getLastModifiedDate());
        brandComGetResp.setLastModifiedBy(brandCom.getLastModifiedBy());
        return brandComGetResp;
    }

    public BrandCom brandComAddReqConvert(BrandCom brandCom, BrandComAddReq brandComAddReq) {
        brandCom.setBrandComName(brandComAddReq.getTitle());
        brandCom.setLogo(brandComAddReq.getLogoUrl());
        brandCom.setCategory(brandComAddReq.getCategoryId());
        brandCom.setPhone(brandComAddReq.getPhone());
        return brandCom;
    }


    public BrandCom brandComUpdateReqConvert(BrandCom brandCom, BrandComUpdateReq brandComUpdateReq) {
        if (!StringUtils.isEmpty(brandComUpdateReq.getTitle())) {
            brandCom.setBrandComName(brandComUpdateReq.getTitle());
        }
        if (!StringUtils.isEmpty(brandComUpdateReq.getLogoUrl())) {
            brandCom.setLogo(brandComUpdateReq.getLogoUrl());
        }
        if (!brandComUpdateReq.getCategoryId().isEmpty()) {
            brandCom.setCategory(brandComUpdateReq.getCategoryId());
        }
        return brandCom;
    }


}
