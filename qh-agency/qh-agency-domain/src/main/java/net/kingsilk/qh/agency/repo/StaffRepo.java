package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Staff;

import java.util.List;

/**
 *
 */
public interface StaffRepo extends BaseRepo<Staff, String> {

//    public abstract List<Staff> findAllByPhone(String phone);

//    public abstract List<Staff> findAllByPhoneAndDisabledAndDeleted(String phone, boolean disabled, boolean deleted);

    public abstract List<Staff> findAllByUserIdAndDisabledAndDeleted(String userId, boolean disabled, boolean deleted);
    public abstract Staff findOneByUserIdAndDisabledAndDeletedAndBrandAppId(String userId, boolean disabled, boolean deleted,String brandAppId);
}
