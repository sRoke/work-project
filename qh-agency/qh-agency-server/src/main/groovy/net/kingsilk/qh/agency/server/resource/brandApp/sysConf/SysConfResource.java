package net.kingsilk.qh.agency.server.resource.brandApp.sysConf;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.sysConf.SysConfApi;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfListRep;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfListResp;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfMinPlace;
import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import net.kingsilk.qh.agency.domain.*;
import net.kingsilk.qh.agency.repo.AdcRepo;
import net.kingsilk.qh.agency.repo.AddressRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import net.kingsilk.qh.agency.server.resource.brandApp.sysConf.convert.SysConfConvert;
import net.kingsilk.qh.agency.service.PartnerStaffService;
import net.kingsilk.qh.agency.service.SysConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */

@Component(value = "sysConfResource")
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

    @Override
    public UniResp<SysConfInfoResp> info(String brandAppId, String key) {

        UniResp<SysConfInfoResp> sysConfInfoRespUniResp = new UniResp<>();

        SysConfInfoResp sysConf = sysConfConvert.sysConfInfoConvert(sysConfService.getSysConf(key, brandAppId));
        sysConfInfoRespUniResp.setStatus(200);
        sysConfInfoRespUniResp.setData(sysConf);
        return sysConfInfoRespUniResp;
    }

    @Override
    public UniResp<SysConfListResp> list(String brandAppId, SysConfListRep req) {

        UniResp<SysConfListResp> uniPageRespUniResp = new UniResp<>();
        uniPageRespUniResp.setStatus(200);
        SysConfListResp sysConfListResp = new SysConfListResp();

        if (sysConfRepo.findByKeyAndBrandAppId("cashierDiscount", brandAppId) == null) {
            SysConf sysConf = new SysConf();
            sysConf.setKey("cashierDiscount");
            sysConf.setValueInt(7);
            sysConf.setDefaultValue("7");
            sysConfRepo.save(sysConf);
        }
        if (sysConfRepo.findByKeyAndBrandAppId("withdrawalMinAmount", brandAppId) == null) {
            SysConf sysConf = new SysConf();
            sysConf.setKey("withdrawalMinAmount");
            sysConf.setValueInt(1);
            sysConf.setDefaultValue("1");
            sysConfRepo.save(sysConf);
        }

        String[] partnerType = new String[]{
                PartnerTypeEnum.LEAGUE.getCode(), PartnerTypeEnum.REGIONAL_AGENCY.getCode(), PartnerTypeEnum.GENERAL_AGENCY.getCode()
        };
        SysConf types = sysConfRepo.findByKeyAndBrandAppId("partnerTypes", brandAppId);

        if (types == null) {
            types = new SysConf();
            types.setKey("partnerTypes");
            ArrayList<String> list = new ArrayList<>();

//            Collections.addAll(list, partnerType);
            types.setValueList(list);
            sysConfRepo.save(types);
        }
        List valueList = types.getValueList();
        Map<String, Boolean> partnerTypes = new LinkedHashMap<>();
        Arrays.stream(partnerType).forEach(it -> partnerTypes.put(it, valueList.contains(it)));

        sysConfListResp.setPartnerTypes(partnerTypes);
        sysConfListResp.setGeneralAgencyMinPlaceNum(sysConfConvert.sysConfConvert("generalAgencyMinPlaceNum", brandAppId, "0"));
        sysConfListResp.setRegionaLagencyMinPlaceNum(sysConfConvert.sysConfConvert("regionaLagencyMinPlaceNum", brandAppId, "0"));
        sysConfListResp.setLeagueMinPlaceNum(sysConfConvert.sysConfConvert("leagueMinPlaceNum", brandAppId, "0"));
        sysConfListResp.setGeneralAgencyMinPlace(sysConfConvert.sysConfConvert("generalAgencyMinPlace", brandAppId, "0"));
        sysConfListResp.setRegionaLagencyMinPlace(sysConfConvert.sysConfConvert("regionaLagencyMinPlace", brandAppId, "0"));
        sysConfListResp.setLeagueMinPlace(sysConfConvert.sysConfConvert("leagueMinPlace", brandAppId, "0"));
        sysConfListResp.setWithdrawalMinAmount(sysConfConvert.sysConfConvert("withdrawalMinAmount", brandAppId, "1"));
        sysConfListResp.setCashierDiscount(sysConfConvert.sysConfConvert("cashierDiscount", brandAppId, "7"));
        uniPageRespUniResp.setData(sysConfListResp);
        return uniPageRespUniResp;
    }

    @Override
    public UniResp<String> update(String brandAppId, String disCount, String minAmount, String partnerTypeMap) {
        Gson gson = new Gson();
        Map<String, Boolean> map = gson.fromJson(partnerTypeMap, Map.class);
        UniResp<String> uniResp = new UniResp<>();
        ArrayList<String> partnerTypes = Lists.newArrayList(
                map.entrySet()
                        .stream()
                        .map(entry ->
                                entry.getValue() ? entry.getKey() : null
                        )
                        .collect(Collectors.toList())
        );
        Boolean cashierDiscount = sysConfService.updata(brandAppId, "cashierDiscount", Integer.parseInt(disCount));
        Boolean withdrawalMinAmount = sysConfService.updata(brandAppId, "withdrawalMinAmount", Integer.parseInt(minAmount));
        Boolean partnerTypeSet = sysConfService.updata(brandAppId, "partnerTypes", partnerTypes);
        if (withdrawalMinAmount
                && cashierDiscount
                && partnerTypeSet) {
            uniResp.setStatus(200);
            uniResp.setData("更新完成");
        } else {
            uniResp.setStatus(10035);
            uniResp.setData("更新失败");
        }
        return uniResp;
    }

    @Override
    public UniResp<String> updateMinPlace(String brandAppId, SysConfMinPlace sysConfMinPlacee) {
        UniResp<String> uniResp = new UniResp<>();
        Boolean updataGeneralNum = sysConfService.updata(brandAppId, "generalAgencyMinPlaceNum", Integer.parseInt(sysConfMinPlacee.getGeneralAgencyMinPlaceNum()));
        Boolean updataRegionaNum = sysConfService.updata(brandAppId, "regionaLagencyMinPlaceNum", Integer.parseInt(sysConfMinPlacee.getRegionaLagencyMinPlaceNum()));
        Boolean updataLeagueNum = sysConfService.updata(brandAppId, "leagueMinPlaceNum", Integer.parseInt(sysConfMinPlacee.getLeagueMinPlaceNum()));
        Boolean updataGeneral = sysConfService.updata(brandAppId, "generalAgencyMinPlace", Integer.parseInt(sysConfMinPlacee.getGeneralAgencyMinPlace()));
        Boolean updataRegiona = sysConfService.updata(brandAppId, "regionaLagencyMinPlace", Integer.parseInt(sysConfMinPlacee.getRegionaLagencyMinPlace()));
        Boolean updataLeague = sysConfService.updata(brandAppId, "leagueMinPlace", Integer.parseInt(sysConfMinPlacee.getLeagueMinPlace()));
        if (updataGeneral && updataRegiona
                && updataLeague && updataGeneralNum
                && updataRegionaNum && updataLeagueNum) {
            uniResp.setStatus(200);
            uniResp.setData("更新完成");
        } else {
            uniResp.setStatus(10035);
            uniResp.setData("更新失败");
        }
        return uniResp;
    }

    @Override
    public UniResp<String> setBrandAppAddr(String brandAppId, AddrSaveReq req) {

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
//        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff();
//        Partner partner = partnerStaff.getPartner();
//        Partner partner=partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM,brandAppId);
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
    public UniResp<String> judgeBrandAppAddr(String brandAppId) {
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
