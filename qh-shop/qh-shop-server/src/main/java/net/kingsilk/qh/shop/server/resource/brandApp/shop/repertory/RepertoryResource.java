package net.kingsilk.qh.shop.server.resource.brandApp.shop.repertory;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.repertory.RepertoryApi;
import net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto.RepertoryCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.repertory.dto.RepertoryResp;
import net.kingsilk.qh.shop.domain.QRepertory;
import net.kingsilk.qh.shop.domain.Repertory;
import net.kingsilk.qh.shop.repo.RepertoryRepo;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepertoryResource implements RepertoryApi {

    @Autowired
    private RepertoryRepo repertoryRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> create(String brandAppId, String shopId, RepertoryCreateRep repertoryCreateRep) {
        UniResp<String> uniResp = new UniResp<>();
        if (repertoryCreateRep == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "参数为空");
        }
        Repertory repertory = new Repertory();
        if (StringUtils.isEmpty(repertoryCreateRep.getEnable())) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "是否禁用不能为空");
        }
        repertory.setBrandAppId(brandAppId);
        repertory.setEnable(repertoryCreateRep.getEnable());
        repertory.setBrandAppId(brandAppId);
        repertory.setManager(repertoryCreateRep.getManager());
        repertory.setName(repertoryCreateRep.getName());
        repertory.setMemo(repertoryCreateRep.getMemo());
        repertory.setShopId(shopId);
        repertoryRepo.save(repertory);
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertory.getId());
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<RepertoryResp> info(String brandAppId, String shopId, String repertoryId) {

        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        UniResp<RepertoryResp> uniResp = new UniResp<>();
        Optional.ofNullable(repertory).ifPresent(it ->
                {
                    RepertoryResp shopResp = conversionService.convert(repertory, RepertoryResp.class);
                    uniResp.setStatus(HttpStatus.SC_OK);
                    uniResp.setData(shopResp);
                }
        );
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(String brandAppId, String shopId, String repertoryId, RepertoryCreateRep repertoryCreateRep) {
        UniResp<String> uniResp = new UniResp<>();
        if (repertoryCreateRep == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "参数为空");
        }
        if (StringUtils.isEmpty(repertoryCreateRep.getEnable())) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "是否禁用不能为空");
        }
        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        if (repertory == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        repertory.setBrandAppId(brandAppId);
        repertory.setEnable(repertoryCreateRep.getEnable());
        repertory.setBrandAppId(brandAppId);
        repertory.setManager(repertoryCreateRep.getManager());
        repertory.setName(repertoryCreateRep.getName());
        repertory.setMemo(repertoryCreateRep.getMemo());
        repertory.setShopId(shopId);
        repertoryRepo.save(repertory);
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertory.getId());

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delet(String brandAppId, String shopId, String repertoryId) {
        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        if (repertory == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        UniResp<String> uniResp = new UniResp<>();
        repertory.setDeleted(true);
        repertoryRepo.save(repertory);
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertory.getId());
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> disable(String brandAppId, String shopId, String repertoryId, Boolean disable) {
        Repertory repertory = repertoryRepo.findOne(
                Expressions.allOf(
                        QRepertory.repertory.id.eq(repertoryId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false),
                        QRepertory.repertory.brandAppId.eq(brandAppId)
                )
        );
        if (repertory == null) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "找不到仓库");
        }
        UniResp<String> uniResp = new UniResp<>();
        repertory.setEnable(disable);
        repertoryRepo.save(repertory);
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(repertory.getId());
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<List<RepertoryResp>> list(String brandAppId, String shopId, List<String> sort, String keyWord) {
        Sort s = ParamUtils.toSort(sort);

        List<Repertory> repertorys = Lists.newArrayList(repertoryRepo.findAll(
                Expressions.allOf(
                        QRepertory.repertory.brandAppId.eq(brandAppId),
                        QRepertory.repertory.shopId.eq(shopId),
                        QRepertory.repertory.deleted.eq(false)
                ), s
        ));

        List<RepertoryResp> repertoryResps = repertorys.stream()
                .map(repertory ->
                        conversionService.convert(repertory, RepertoryResp.class)
                )
                .collect(Collectors.toList());


        UniResp<List<RepertoryResp>> uniResp = new UniResp<>();
        uniResp.setData(repertoryResps);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

}
