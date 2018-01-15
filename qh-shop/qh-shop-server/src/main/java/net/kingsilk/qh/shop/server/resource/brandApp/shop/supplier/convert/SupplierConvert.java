package net.kingsilk.qh.shop.server.resource.brandApp.shop.supplier.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.supplier.dto.SupplierModel;
import net.kingsilk.qh.shop.domain.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierConvert {

    public Supplier reqConvert(SupplierModel supplierReq) {
        if (supplierReq == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        if (supplierReq.getSupplierId() != null) {
            supplier.setId(supplierReq.getSupplierId());
        }
        if (supplierReq.getBrandAppId() != null) {
            supplier.setBrandAppId(supplierReq.getBrandAppId());
        }
        if (supplierReq.getShopId() != null) {
            supplier.setShopId(supplierReq.getShopId());
        }
        if (supplierReq.getEnable() != null) {
            supplier.setEnable(supplierReq.getEnable());
        }
        if (supplierReq.getName() != null) {
            supplier.setName(supplierReq.getName());
        }
        if (supplierReq.getContacts() != null) {
            supplier.setContacts(supplierReq.getContacts());
        }
        if (supplierReq.getPhone() != null) {
            supplier.setPhone(supplierReq.getPhone());
        }
        if (supplierReq.getName() != null) {
            supplier.setName(supplierReq.getName());
        }
        if (supplierReq.getAddrDesp() != null) {
            supplier.setAddrDesp(supplierReq.getAddrDesp());

        }
        if (supplierReq.getAdcId() != null) {
            supplier.setAdcId(supplierReq.getAdcId());
        }
        if (supplierReq.getMemo() != null) {
            supplier.setMemo(supplierReq.getMemo());
        }
        if (supplierReq.getDateCreated() != null) {
            supplier.setDateCreated(supplierReq.getDateCreated());    //todo
        }
        if (supplierReq.getAdc() != null) {
            supplier.setAdc(supplierReq.getAdc());    //todo
        }

        return supplier;
    }


    public SupplierModel respConvert(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        SupplierModel supplierModel = new SupplierModel();
        if (supplier.getId() != null) {
            supplierModel.setSupplierId(supplier.getId());
            ;
        }
        if (supplier.getBrandAppId() != null) {
            supplierModel.setBrandAppId(supplier.getBrandAppId());
        }
        if (supplier.getShopId() != null) {
            supplierModel.setShopId(supplier.getShopId());       //todo  查询
        }
        if (supplier.getEnable() != null) {
            supplierModel.setEnable(supplier.getEnable());
        }
        if (supplier.getName() != null) {
            supplierModel.setName(supplier.getName());
        }
        if (supplier.getContacts() != null) {
            supplierModel.setContacts(supplier.getContacts());
        }
        if (supplier.getPhone() != null) {
            supplierModel.setPhone(supplier.getPhone());
        }
        if (supplier.getName() != null) {
            supplierModel.setName(supplier.getName());
        }
        if (supplier.getAddrDesp() != null) {
            supplierModel.setAddrDesp(supplier.getAddrDesp());
        }
        if (supplier.getAdcId() != null) {                // todo
            supplierModel.setAdcId(supplier.getAdcId());
        }
        if (supplier.getMemo() != null) {
            supplierModel.setMemo(supplier.getMemo());
        }
        if (supplier.getDateCreated() != null) {
            supplierModel.setDateCreated(supplier.getDateCreated());    //todo
        }
        if (supplier.getAdc() != null) {
            supplierModel.setAdc(supplier.getAdc());    //todo
        }

        return supplierModel;
    }
}