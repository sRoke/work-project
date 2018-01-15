package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup.convert;

import net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto.ShopStaffGroupModel;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupInfoResp;
import net.kingsilk.qh.shop.domain.ShopStaffGroup;
import org.springframework.stereotype.Component;

@Component
public class ShopStaffGroupConvert {

    public ShopStaffGroupModel staffGroupModelConvert(ShopStaffGroup staffGroup) {
        ShopStaffGroupModel groupModel = new ShopStaffGroupModel();
        groupModel.setName(staffGroup.getName());
        groupModel.setDesp(staffGroup.getDesc());
        groupModel.setReserved(staffGroup.getReserved());
        groupModel.setAuthorities(staffGroup.getAuthorities());
        groupModel.setBrandAppId(staffGroup.getBrandAppId());
        groupModel.setId(staffGroup.getId());
        groupModel.setShopId(staffGroup.getShopId());
//        staffGroup.getStaffs().each {
//            Staff staff ->
//                groupModel.getStaffs().add(staffModelConvert(staff))
//        }
        return groupModel;
    }

    public ShopStaffGroupInfoResp respConvert(ShopStaffGroup shopStaffGroup) {

        ShopStaffGroupInfoResp shopStaffGroupInfoResp = new ShopStaffGroupInfoResp();
        shopStaffGroupInfoResp.setName(shopStaffGroup.getName());
        shopStaffGroupInfoResp.setDesp(shopStaffGroup.getDesc());
        shopStaffGroupInfoResp.setAuthor(shopStaffGroup.getAuthorities());
        return shopStaffGroupInfoResp;


    }
}
