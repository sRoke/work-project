package net.kingsilk.qh.platform.server.resource.brandCom.brand.convert;

import net.kingsilk.qh.platform.api.brand.dto.BrandAddReq;
import net.kingsilk.qh.platform.api.brand.dto.BrandGetResp;
import net.kingsilk.qh.platform.domain.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Component
public class BrandConvert {

    public Brand brandReqConvert(Brand brand, BrandAddReq brandAddReq) {

        String brandComId = brandAddReq.getBrandComId();
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌所属商家id不能为空");
        brand.setBrandComId(brandComId);

        String desp = brandAddReq.getDesp();
        Assert.isTrue(!StringUtils.isEmpty(desp), "品牌说明不能为空");
        brand.setDesp(desp);

        String namecn = brandAddReq.getNameCN();
        Assert.isTrue(!StringUtils.isEmpty(namecn), "品牌中文名不能为空");
        brand.setNameCN(namecn);

        String nameen = brandAddReq.getNameEN();
        Assert.isTrue(!StringUtils.isEmpty(nameen), "品牌英文名不能为空");
        brand.setNameEN(nameen);

        String logourl = brandAddReq.getLogoUrl();
        Assert.isTrue(!StringUtils.isEmpty(logourl), "品牌logo地址不能为空");
        brand.setLogoUrl(logourl);

        String website = brandAddReq.getWebsite();
        Assert.isTrue(!StringUtils.isEmpty(website), "品牌网站地址不能为空");
        brand.setWebsite(website);

        String time = brandAddReq.getFoundingTime();
        Assert.isTrue(!StringUtils.isEmpty(time), "品牌所创建时间不能为空");
        brand.setFoundingTime(time);

        return brand;
    }

    public BrandGetResp brandGetRespConvert(Brand brand, BrandGetResp brandGetResp) {

        brandGetResp.setBrandComId(brand.getBrandComId());

        brandGetResp.setDesp(brand.getDesp());

        brandGetResp.setNameCN(brand.getNameCN());

        brandGetResp.setNameEN(brand.getNameEN());

        brandGetResp.setLogoUrl(brand.getLogoUrl());

        brandGetResp.setWebsite(brand.getWebsite());

        brandGetResp.setFoundingTime(brand.getFoundingTime());

        return brandGetResp;
    }


}
