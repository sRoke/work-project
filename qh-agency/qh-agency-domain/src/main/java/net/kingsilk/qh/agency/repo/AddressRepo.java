package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Address;
import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.domain.PartnerStaff;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface AddressRepo extends BaseRepo<Address, String> {

    public abstract Address findOneByIdAndPartner(String id, Partner partner);

//    @Query(value = "{ partner : ?0, defaultAddr : ?1 ,deleted : ?2}")
    public abstract Address findOneByPartnerAndDefaultAddrAndDeleted(Partner partner, boolean defaultAddr, boolean deleted);

    public abstract List<Address> findAllByPartner(Partner partner);
    List<Address> findAllByPartnerStaffAndDeleted(PartnerStaff partnerStaff,boolean deleted);
}
