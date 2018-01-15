package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.PartnerStaff;

import java.util.List;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface PartnerStaffRepo extends BaseRepo<PartnerStaff, String> {
    //
    @Deprecated
//    List<PartnerStaff> findAllByPhone(String phone);

//    List<PartnerStaff> findAllByPhoneAndDeleted(String phone, boolean deleted);

    List<PartnerStaff> findAllByUserIdAndDisabledAndDeleted(String userId, boolean disabled, boolean deleted);

    PartnerStaff findOneByUserIdAndBrandAppIdAndDisabledAndDeleted(
            String userId, String brandAppId, boolean disabled, boolean deleted);
}
