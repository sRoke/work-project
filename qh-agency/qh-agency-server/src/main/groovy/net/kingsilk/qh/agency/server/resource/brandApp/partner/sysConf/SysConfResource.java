package net.kingsilk.qh.agency.server.resource.brandApp.partner.sysConf;

import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.partner.sysConf.SysConfApi;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.Adc;
import net.kingsilk.qh.agency.domain.Address;
import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.domain.PartnerStaff;
import net.kingsilk.qh.agency.repo.AdcRepo;
import net.kingsilk.qh.agency.repo.AddressRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import net.kingsilk.qh.agency.server.resource.brandApp.sysConf.convert.SysConfConvert;
import net.kingsilk.qh.agency.service.PartnerService;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import net.kingsilk.qh.agency.service.SysConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 */

@Component(value = "sysConf")
public class SysConfResource implements SysConfApi {

    @Autowired
    private SysConfService sysConfService;

    @Autowired
    private SysConfConvert sysConfConvert;

    @Autowired
    private SysConfRepo sysConfRepo;

    @Autowired
    private PartnerStaffService partnerStaffService;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private PartnerService partnerService;

    @Override
    public UniResp<SysConfInfoResp> info(String brandAppId, String key) {

        UniResp<SysConfInfoResp> sysConfInfoRespUniResp = new UniResp<>();

        SysConfInfoResp sysConf = sysConfConvert.sysConfInfoConvert(sysConfService.getSysConf(key,brandAppId));
        sysConfInfoRespUniResp.setStatus(200);
        sysConfInfoRespUniResp.setData(sysConf);
        return sysConfInfoRespUniResp;
    }


    @Override
    public UniResp<String> setBrandAppAddr(
            String brandAppId,
            String partnerId,
            AddrSaveReq req
    ) {
        partnerService.check();
        Assert.notNull(req, "地址不能为空");
        String street = req.getStreet();
        String adcNo = req.getAdcNo();
        String receiver = req.getReceiver();
        String phone = req.getPhone();
        Assert.notNull(street, "街道不能为空");
        Assert.notNull(adcNo, "地址编码不能为空");
        Assert.notNull(receiver, "收货人不能为空");
        Assert.notNull(phone, "手机号不能为空");
        UniResp<String> uniResp = new UniResp<>();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        Partner partner;
        if (partnerStaff == null) {
            partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId);
        } else {
            partner = partnerStaff.getPartner();
        }
        Address tmpAddr = addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partner, true, false);
        if (tmpAddr != null) {
            Adc adc = adcRepo.findOneByNo(adcNo);
            tmpAddr.setAdc(adc);
            tmpAddr.setStreet(street);
            tmpAddr.setReceiver(receiver);
            tmpAddr.setPhone(phone);
            tmpAddr.setMemo(req.getMemo());
            tmpAddr.setPartner(partner);
            tmpAddr.setDefaultAddr(true);
            addressRepo.save(tmpAddr);
        } else {
            Address address = new Address();
            Adc adc = adcRepo.findOneByNo(adcNo);
            address.setAdc(adc);
            address.setStreet(street);
            address.setReceiver(receiver);
            address.setPhone(phone);
            address.setMemo(req.getMemo());
            address.setPartner(partner);
            address.setDefaultAddr(true);
            addressRepo.save(address);
        }
        uniResp.setStatus(200);
        uniResp.setData(addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partner, true, false).getId());
        return uniResp;
    }

    @Override
    public UniResp<String> judgeBrandAppAddr(
            String brandAppId,
            String partnerId
    ) {
        partnerService.check();
        UniResp<String> uniResp = new UniResp<>();
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
        Partner partner;
        if (partnerStaff == null) {
            partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId);
        } else {
            partner = partnerStaff.getPartner();
        }
        Address tmpAddr = addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partner, true, false);
        Integer stats = (tmpAddr == null ? 10037 : 10038);
        String data = (tmpAddr == null ? "" : tmpAddr.getId());
        uniResp.setStatus(stats);
        uniResp.setData(data);
        return uniResp;
    }
}
