package net.kingsilk.qh.platform.server.resource.brandCom.brand;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import io.swagger.annotations.*;
import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brand.*;
import net.kingsilk.qh.platform.api.brand.dto.BrandAddReq;
import net.kingsilk.qh.platform.api.brand.dto.BrandGetResp;
import net.kingsilk.qh.platform.api.brand.dto.BrandUpdateReq;
import net.kingsilk.qh.platform.domain.*;
import net.kingsilk.qh.platform.repo.*;
import net.kingsilk.qh.platform.server.resource.brandCom.brand.convert.*;
import net.kingsilk.qh.platform.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import javax.inject.*;
import javax.ws.rs.Path;
import java.util.*;

@Component
@Singleton
@Path("/brandComp/{brandComId}/brand")
@Api
public class BrandResource implements BrandApi {


    @Autowired
    private BrandConvert brandConvert;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;


    /**
     * 新增品牌
     *
     * @param brandAddReq
     * @return
     */
    @Override
    public UniResp<String> add(BrandAddReq brandAddReq) {
        //未做同名判定
        Brand brand = new Brand();
        Brand bd = brandConvert.brandReqConvert(brand, brandAddReq);
        Brand b = brandRepo.save(bd);
        Assert.notNull(b, " 保存失败");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(b.getId());
        return uniResp;


    }


    /**
     * 删除品牌
     *
     * @param brandComId
     * @param brandId
     * @return
     */
    @Override
    public UniResp<Void> del(String brandComId, String brandId) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        Assert.isTrue(!StringUtils.isEmpty(brandId), "品牌id不能为空");
        brandRepo.deleteBrandByBrandComIdAndAndId(brandComId, brandId);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 更新品牌
     *
     * @param brandComId
     * @param brandId
     * @param brandUpdateReq
     * @return
     */
    @Override
    public UniResp<Void> update(
            String brandComId,
            String brandId,
            BrandUpdateReq brandUpdateReq
    ) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        Assert.isTrue(!StringUtils.isEmpty(brandId), "品牌id不能为空");
        Brand brand = brandRepo.findBrandByBrandComIdAndAndId(brandComId, brandId);
        Assert.notNull(brand, "查无结果");
        if (brandUpdateReq.getBrandComId().isPresent()) {
            brand.setBrandComId(brandUpdateReq.getBrandComId().get());
        }
        if (brandUpdateReq.getLogoUrl().isPresent()) {
            brand.setLogoUrl(brandUpdateReq.getLogoUrl().get());
        }
        if (brandUpdateReq.getDesp().isPresent()) {
            brand.setDesp(brandUpdateReq.getDesp().get());
        }
        if (brandUpdateReq.getFoundingTime().isPresent()) {
            brand.setFoundingTime(brandUpdateReq.getFoundingTime().get());
        }
        if (brandUpdateReq.getNameCN().isPresent()) {
            brand.setNameCN(brandUpdateReq.getNameCN().get());
        }
        if (brandUpdateReq.getNameEN().isPresent()) {
            brand.setNameEN(brandUpdateReq.getNameEN().get());
        }
        if (brandUpdateReq.getWebsite().isPresent()) {
            brand.setWebsite(brandUpdateReq.getWebsite().get());
        }
        brandRepo.save(brand);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }


    /**
     * 查找品牌
     *
     * @param brandComId
     * @param brandId
     * @return
     */
    @Override
    public UniResp<BrandGetResp> get(String brandComId, String brandId) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        Assert.isTrue(!StringUtils.isEmpty(brandId), "品牌id不能为空");
        Brand brand = brandRepo.findBrandByBrandComIdAndAndId(brandComId, brandId);
        Assert.notNull(brand, "查无结果");
        BrandGetResp brandGetResp = new BrandGetResp();
        BrandGetResp bgr = brandConvert.brandGetRespConvert(brand, brandGetResp);
        UniResp<BrandGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(bgr);
        return uniResp;
    }


    @Override
    public UniResp<UniPage<BrandGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String brandComId,
            List<String> bandIds
    ) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        Sort sortobj = null;
        if (sort != null && sort.size() > 1) {
            sortobj = ParamUtils.toSort(sort.toArray(new String[0]));
        }
        PageRequest pageRequest = new PageRequest(page, size, sortobj);
        Predicate p = null;
        if (bandIds != null && bandIds.size() > 1) {
            p = Expressions.allOf(
                    QBrand.brand.id.in(bandIds),
                    QBrand.brand.brandComId.contains(brandComId)
            );
        }
        Page<Brand> pg = null;
        if (p != null) {
            pg = brandRepo.findAll(p, pageRequest);
        } else {
            pg = brandRepo.findAll(QBrand.brand.brandComId.contains(brandComId), pageRequest);
        }
        Page<BrandGetResp> pgOne = pg.map(brand -> {
            BrandGetResp brandGetResp = new BrandGetResp();
            brandGetResp.setBrandComId(brand.getBrandComId());
            brandGetResp.setCreatedBy(brand.getCreatedBy());
            brandGetResp.setDateCreated(brand.getDateCreated());
            brandGetResp.setDesp(brand.getDesp());
            brandGetResp.setFoundingTime(brand.getFoundingTime());
            brandGetResp.setId(brand.getId());
            brandGetResp.setLastModifiedBy(brand.getLastModifiedBy());
            brandGetResp.setLogoUrl(brand.getLogoUrl());
            brandGetResp.setNameCN(brand.getNameCN());
            brandGetResp.setNameEN(brand.getNameEN());
            brandGetResp.setWebsite(brand.getWebsite());
            return brandGetResp;
        });
        UniPage<BrandGetResp> uniPage = conversionService.convert(pgOne, UniPage.class);

        UniResp<UniPage<BrandGetResp>> un = new UniResp<>();
        un.setStatus(200);
        un.setData(uniPage);
        return un;
    }
}
