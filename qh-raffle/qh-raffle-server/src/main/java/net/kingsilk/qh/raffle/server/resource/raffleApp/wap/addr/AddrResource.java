package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.addr;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.addr.AddrAddReq;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrUpdateReq;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.AddrApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrModel;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrSaveReq;
import net.kingsilk.qh.raffle.domain.Adc;
import net.kingsilk.qh.raffle.repo.AdcRepo;
import net.kingsilk.qh.raffle.service.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddrResource implements AddrApi {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @Autowired
    private net.kingsilk.qh.oauth.api.user.addr.AddrApi addrApi;


    @Override
    public UniResp<List<AddrModel>> list(String raffleAppId, String raffleId, String openId) {
        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrApi.list(10, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        boolean hasDefualr = list.getData().getContent().stream().anyMatch(AddrGetResp::isDefaultAddr);
        try {
            if (!list.getData().getContent().isEmpty() && !hasDefualr) {
                addrApi.setDefault(
                        userId,
                        list.getData().getContent().get(0).getId(),
                        "USER_SHIPPING_ADDR"
                );
                list = addrApi.list(10, 0, null, userId, null);
            }
        } catch (Exception e) {

        } finally {
            List<AddrModel> content = list.getData().getContent().stream().map(it -> conversionService.convert(it, AddrModel.class)).collect(Collectors.toList());
            UniResp<List<AddrModel>> resp = new UniResp<>();
            resp.setData(content);
            return resp;
        }
    }

    @Override
    public UniResp<AddrModel> detail(String raffleAppId, String raffleId, String id, String openId) {

        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addrGetRespUniResp = addrApi.get(userId, id);
        AddrModel addrModel = conversionService.convert(addrGetRespUniResp.getData(), AddrModel.class);
        UniResp<AddrModel> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(addrModel);
        return uniResp;
    }

    @Override
    public UniResp<String> save(String raffleAppId, String raffleId, AddrSaveReq addrSaveReq) {
        String userId = userService.getUserIdByOpenId(addrSaveReq.getOpenId(), raffleAppId);
        AddrAddReq address = new AddrAddReq();
        address.setPhones(new HashSet<>());
        Optional.ofNullable(addrSaveReq).ifPresent(req ->
                {
                    Adc adc = adcRepo.findOneByNo(req.getAdcNo());
                    address.setAdc(req.getAdcNo());
                    address.setStreet(req.getStreet());
                    address.setContact(req.getReceiver());
                    address.getPhones().add(req.getPhone());
                    address.setMemo(req.getMemo());
                    address.setAddrType("USER_SHIPPING_ADDR");
                    address.setDefaultAddr(req.isDefaultAddr());
                }
        );
        //先获得之前的默认地址
        AddrGetResp shippingAddr = addrApi.getDefault(userId, "USER_SHIPPING_ADDR").getData();

        //保存地址，如果之前的已经有默认地址，就有两个默认地址
        addrApi.add(userId, address);
        //最多20条地址
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrApi.list(20, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        Optional<AddrGetResp> addrGetResp = list.getData().getContent().stream().filter(AddrGetResp::isDefaultAddr).findFirst();
        if (!addrGetResp.isPresent()) {
            //将之前的默认地址设为非默认。将现在的默认地址设为默认
            addrApi.setDefault(
                    userId,
                    list.getData().getContent().stream().filter(it ->
                            !it.getId().equals(shippingAddr.getId())
                    ).findFirst().get().getId(),
                    "USER_SHIPPING_ADDR"
            );
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> update(String raffleAppId, String raffleId, String id, AddrSaveReq addrSaveReq) {

        AddrUpdateReq addrUpdateReq = new AddrUpdateReq();
        addrUpdateReq.setAdc(Optional.ofNullable(addrSaveReq.getAdcNo()));
        addrUpdateReq.setContact(Optional.ofNullable(addrSaveReq.getReceiver()));
        addrUpdateReq.setDefaultAddr(Optional.of(false));
        addrUpdateReq.setMemo(Optional.ofNullable(addrSaveReq.getMemo()));
        addrUpdateReq.setPhones(Optional.of(new HashSet<>()));
        addrUpdateReq.getPhones().get().add(addrSaveReq.getPhone());
        addrUpdateReq.setStreet(Optional.of(addrSaveReq.getStreet()));

        String userId = userService.getUserIdByOpenId(addrSaveReq.getOpenId(), raffleAppId);
        addrApi.update(userId, id, addrUpdateReq);

        if (addrSaveReq.isDefaultAddr()) {
            addrApi.setDefault(userId, id, "USER_SHIPPING_ADDR");
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> setDefault(String raffleAppId, String raffleId, String id, String openId) {

        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        addrApi.setDefault(userId, id, "USER_SHIPPING_ADDR");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String raffleAppId, String raffleId, String id, String openId) {

        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        addrApi.del(userId, id);
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrApi.list(10, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        if (!list.getData().getContent().isEmpty()) {
            Optional<AddrGetResp> addrGetResp = list.getData().getContent().stream().filter(AddrGetResp::isDefaultAddr).findFirst();
            if (!addrGetResp.isPresent()) {

                addrApi.setDefault(userId, list.getData().getContent().get(0).getId(), "USER_SHIPPING_ADDR");
            }
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("删除成功");
        return uniResp;
    }

    @Override
    public UniResp<AddrModel> getDefault(String raffleAppId, String raffleId, String openId) {
        String userId = userService.getUserIdByOpenId(openId, raffleAppId);
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addr = addrApi.getDefault(userId, "USER_SHIPPING_ADDR");
        AddrModel addrModel = conversionService.convert(addr, AddrModel.class);
        UniResp<AddrModel> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(addrModel);
        return uniResp;
    }
}
