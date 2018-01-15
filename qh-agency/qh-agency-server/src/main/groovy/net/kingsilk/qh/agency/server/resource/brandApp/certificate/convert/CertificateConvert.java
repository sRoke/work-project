package net.kingsilk.qh.agency.server.resource.brandApp.certificate.convert;

import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificateInfoResp;
import net.kingsilk.qh.agency.domain.Certificate;
import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.repo.AdcRepo;
import net.kingsilk.qh.agency.repo.CertificateRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class CertificateConvert {

    @Autowired
    public AddrService addrService;

    @Autowired
    public CertificateRepo certificateRepo;

    @Autowired
    public PartnerRepo partnerRepo;

    @Autowired
    public AdcRepo adcRepo;

    public CertificateInfoResp certificateConvert(Certificate certificate) {

        CertificateInfoResp certificateInfoResp = new CertificateInfoResp();

//        Adc abc = adcRepo.findOneByNo(certificate.getAdcNum());
//        certificateInfoResp.setShopAdress(addrService.getAdcInfo(abc.getNo()).toString());
//        certificateInfoResp.setAdcNum(abc.getNo());

        certificateInfoResp.setRealName(certificate.getRealName());

        certificateInfoResp.setCertificateTemplateId(certificate.getCertificateTemplateId());
        certificateInfoResp.setCertificateImg(certificate.getCertificateImg());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(certificate.getStartTime());
        int sDay = startTime.get(Calendar.DATE);
        int sMonth = startTime.get(Calendar.MONTH) + 1;
        int sYear = startTime.get(Calendar.YEAR);

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(certificate.getEndTime());
        int eDay = endTime.get(Calendar.DATE);
        int eMonth = endTime.get(Calendar.MONTH) + 1;
        int eYear = endTime.get(Calendar.YEAR);

        certificateInfoResp.setProx(certificate.getPartnerType().getDesp());
        certificateInfoResp.setStartYear(sYear + "");
        certificateInfoResp.setStartMonth(sMonth + "");
        certificateInfoResp.setStartDay(sDay + "");
        certificateInfoResp.setEndDay(eDay + "");
        certificateInfoResp.setEndMonth(eMonth + "");
        certificateInfoResp.setEndYear(eYear + "");
        certificateInfoResp.setSignDate(sdf.format(certificate.getSignDate()));
        certificateInfoResp.setBrandAppId(certificate.getBrandAppId());
        certificateInfoResp.setCertificateImg(certificate.getCertificateImg());
        certificateInfoResp.setAddress(certificate.getShopAdress());
        Partner partner = partnerRepo.findOne(certificate.getPartnerId());
        String partnerId = (partner == null ? certificate.getPartnerId() : partner.getId());
        certificateInfoResp.setPartnerId(partnerId);
        certificateInfoResp.setSeq(certificate.getSeq());

        return certificateInfoResp;
    }

}
