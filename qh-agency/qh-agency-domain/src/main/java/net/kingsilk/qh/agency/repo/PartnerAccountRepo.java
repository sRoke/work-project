package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.domain.PartnerAccount;

/**
 *
 */
public interface PartnerAccountRepo extends BaseRepo<PartnerAccount, String> {
    PartnerAccount findByPartner(Partner partner);

    PartnerAccount findByPartnerAndBrandAppId(Partner partner,String brandAppId);

}
