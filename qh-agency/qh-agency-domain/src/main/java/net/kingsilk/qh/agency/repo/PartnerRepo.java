package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.Partner;
import org.springframework.data.domain.Sort;

/**
 * Created by lit on 17/7/18.
 */
public interface PartnerRepo extends BaseRepo<Partner, String> {
    Partner findOneByUserIdAndBrandAppId(String userId, String brandId);

    Partner findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum partnerType, String brandId);

    Partner findByInvitationCode(String InvitationCode);

    Partner findByPartnerTypeEnumAndBrandAppIdAndPlaceNumAfter(PartnerTypeEnum partnerType, String brandId, Integer num, Sort sort);
}