package net.kingsilk.qh.agency.server.resource.brandApp.certificate;

import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.CertificateApi;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificateInfoResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificatePageResp;
import net.kingsilk.qh.agency.api.brandApp.certificate.dto.CertificateSaveReq;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.Certificate;
import net.kingsilk.qh.agency.domain.PartnerStaff;
import net.kingsilk.qh.agency.repo.CertificateRepo;
import net.kingsilk.qh.agency.server.resource.brandApp.certificate.convert.CertificateConvert;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class CertificateResource implements CertificateApi {

    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private CertificateConvert certificateConvert;

    @Autowired
    private PartnerStaffService partnerStaffService;

    @Override
    public UniResp<CertificatePageResp> page(String brandAppId, UniPageReq uniPageReq) {
        return null;
    }

    @Override
    public UniResp<CertificateInfoResp> info(String brandAppId, String id) {

        UniResp<CertificateInfoResp> uniResp = new UniResp<>();

        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        if (partnerStaff != null) {
            id = partnerStaff.getPartner().getId();
        }
        Certificate certificate = certificateRepo.findOneByBrandAppIdAndPartnerId(brandAppId, id);
        if (certificate != null) {
            CertificateInfoResp certificateInfoResp = certificateConvert.certificateConvert(certificate);

            uniResp.setData(certificateInfoResp);
            uniResp.setStatus(200);
        } else {
            uniResp.setMessage("授权书还没有生成");
            uniResp.setStatus(10047);
        }
        return uniResp;
    }

    @Override
    public UniResp<String> save(String brandAppId, CertificateSaveReq certificateSaveReq) {

        Certificate certificate = new Certificate();

        Assert.notNull(certificateSaveReq.getRealName(), "请输入名字");
        Assert.notNull(certificateSaveReq.getAddress(), "请输入地区");
        Assert.notNull(certificateSaveReq.getPartnerId(), "请输入渠道商信息");

        String sDate = certificateSaveReq.getStartYear() + "年"
                + certificateSaveReq.getStartMonth() + "月"
                + certificateSaveReq.getStartDay() + "日";

        String eDate = certificateSaveReq.getEndYear() + "年"
                + certificateSaveReq.getEndMonth() + "月"
                + certificateSaveReq.getEndDay() + "日";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date startDate = sdf.parse(sDate);
            Date endDate = sdf.parse(eDate);
            Date signDate = sdf.parse(certificateSaveReq.getSignDay());
            certificate.setStartTime(startDate);
            certificate.setEndTime(endDate);
            certificate.setSignDate(signDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String prox = certificateSaveReq.getProx();
        PartnerTypeEnum partnerTypeEnum = PartnerTypeEnum.BRAND_COM;
        PartnerTypeEnum[] values = PartnerTypeEnum.values();
        for (PartnerTypeEnum value : values) {
            if (value.getDesp().equals(prox)) {
                partnerTypeEnum = value;
            }
        }
        certificate.setPartnerType(partnerTypeEnum);
        certificate.setRealName(certificateSaveReq.getRealName());
        certificate.setShopAdress(certificateSaveReq.getAddress());
        certificate.setBrandAppId(brandAppId);
        certificate.setCertificateImg(certificateSaveReq.getCertificateImg());
        certificate.setPartnerId(certificateSaveReq.getPartnerId());
        certificate.setSeq(certificateSaveReq.getSeq());
        certificate.setCertificateImg(certificateSaveReq.getCertificateImg());
        certificateRepo.save(certificate);

        UniResp uniResp = new UniResp<String>();
        uniResp.setStatus(200);
        uniResp.setData("保存成功");

        return uniResp;
    }

    @Override
    public UniResp<String> update(String brandAppId, String id, CertificateSaveReq certificateSaveReq) {
        return null;
    }

    @Override
    public UniResp<String> changeStatus(String brandAppId, String id, String status) {
        return null;
    }
}
