package net.kingsilk.qh.shop.server.resource.brandApp.shop.supplier;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.supplier.SupplierApi;
import net.kingsilk.qh.shop.api.brandApp.shop.supplier.dto.SupplierModel;
import net.kingsilk.qh.shop.domain.QSupplier;
import net.kingsilk.qh.shop.domain.Supplier;
import net.kingsilk.qh.shop.repo.SupplierRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.supplier.convert.SupplierConvert;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
@Component
public class SupplierRecource implements SupplierApi{

    @Autowired
    private SupplierConvert supplierConvert;

    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> add(
            String brandAppId,
            String shopId,
            SupplierModel supplierReq) {
        UniResp<String> uniResp = new UniResp<>();
        if (supplierReq == null) {
            uniResp.setData("supplierReq为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierReq.getShopId() == null) {
            uniResp.setData("shopId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierReq.getBrandAppId() == null) {
            uniResp.setData("BrandAppId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        Supplier supplier = supplierConvert.reqConvert(supplierReq);
        supplier.setDateCreated(new Date());

        Supplier isSave = supplierRepo.save(supplier);
        if (isSave == null){
            uniResp.setData("false");
            uniResp.setStatus(ErrStatus.UNKNOWN);
            return uniResp;
        }
        uniResp.setData("success");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delete(
            String brandAppId, 
            String shopId, 
            String supplierId) {

        UniResp<String> uniResp = new UniResp<>();
        if (brandAppId == null && StringUtils.isEmpty(brandAppId)) {
            uniResp.setData("brandAppId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (shopId == null && StringUtils.isEmpty(shopId)) {
            uniResp.setData("shopId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierId == null && StringUtils.isEmpty(supplierId)) {
            uniResp.setData("supplierId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        Supplier supplier = supplierRepo.findOne(
                Expressions.allOf(
                        QSupplier.supplier.brandAppId.eq(brandAppId),
                        QSupplier.supplier.shopId.eq(shopId),
                        QSupplier.supplier.id.eq(supplierId),
                        QSupplier.supplier.deleted.ne(true)
                ));
        if (supplier != null){
            supplier.setDeleted(true);;
            supplierRepo.save(supplier);
            uniResp.setData("successs");
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }
        uniResp.setData("找不到该supplierId会员");
        uniResp.setStatus(ErrStatus.UNKNOWN);      //todo
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId, 
            String shopId, 
            SupplierModel supplierReq) {

        UniResp<String> uniResp = new UniResp<>();
        if (supplierReq == null) {
            uniResp.setData("supplierReq为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierReq.getShopId() == null) {
            uniResp.setData("shopId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierReq.getBrandAppId() == null) {
            uniResp.setData("brandAppId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (supplierReq.getSupplierId() == null) {
            uniResp.setData("SupplierId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }

        Supplier supplier = supplierConvert.reqConvert(supplierReq);
        Supplier isSave = supplierRepo.save(supplier);                //todo  确定是否先查

        if (isSave != null){
            uniResp.setData("successs");
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }
        uniResp.setStatus(ErrStatus.UNKNOWN);
        uniResp.setData("更新失败");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<SupplierModel> info(String brandAppId, String shopId, String supplierId) {

        UniResp<SupplierModel> uniResp = new UniResp<>();

        if (brandAppId == null){
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("brandAppId为空");
            return uniResp;
        }

        if (shopId == null){
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("shopId为空");
            return uniResp;
        }

        if (supplierId == null){
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("supplierId为空");
            return uniResp;
        }
        Supplier supplier = supplierRepo.findOne(
                Expressions.allOf(
                        QSupplier.supplier.brandAppId.eq(brandAppId),
                        QSupplier.supplier.shopId.eq(shopId),
                        QSupplier.supplier.id.eq(supplierId),
                        QSupplier.supplier.deleted.ne(true)
                )
        );
        if (supplier == null){
            uniResp.setStatus(10001);
            uniResp.setException("没有该会员");
            return uniResp;
        }

        SupplierModel supplierModel = supplierConvert.respConvert(supplier);
        uniResp.setData(supplierModel);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<SupplierModel>> page(
            String brandAppId, 
            String shopId, 
            int size, int page, 
            List<String> sort) {

        UniResp<UniPageResp<SupplierModel>> uniResp = new UniResp<>();

        if (brandAppId == null){
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("brandAppId为空");
            return uniResp;
        }

        if (shopId == null){
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("shopId为空");
            return uniResp;
        }

        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);
        Page<Supplier> supplierPage = supplierRepo.findAll(pageable);

        if (supplierPage != null){
            UniPageResp<SupplierModel> modelUniPageResp = new UniPageResp<>();
            List<Supplier> suppliers = supplierPage.getContent();

            if (suppliers != null){
                for (Supplier m : suppliers){
                    SupplierModel supplierModel = supplierConvert.respConvert(m);
                    modelUniPageResp.getContent().add(supplierModel);
                }
                uniResp.setData(modelUniPageResp);
                uniResp.setStatus(ErrStatus.OK);
                return uniResp;
            }
        }

        uniResp.setStatus(10001);               //todo
        uniResp.setMessage("找不到会员数据");
        return uniResp;
    }
}
