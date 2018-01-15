package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Certificate;

public interface CertificateRepo extends BaseRepo<Certificate, String> {

    Certificate findOneByBrandAppIdAndPartnerId(String brandAppId,String partnerId);

}
