package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.addr;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.addr.AddrAddReq;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrUpdateReq;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.AddrApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrSaveReq;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.repo.AddressRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.SecService;
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
    private AddressRepo addressRepo;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SecService secService;

    @Autowired
    private AddrService addrService;

    @Override
    public UniResp<List<AddrModel>> list(String brandAppId, String shopId) {
//        //TODO 与用户关联
////        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
//
//        PageRequest pageRequest = new PageRequest(req.getPage(), req.getSize(),
//                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
//        Page<Address> address = addressRepo.findAll(
//                allOf(
//                        //TODO
////                        QAddress.address.partner.eq(partnerStaff.partner),
//                        QAddress.address.deleted.ne(true)), pageRequest
//        );
//        UniResp<UniPageResp<AddrModel>> resp = new UniResp<>();
//        resp.setStatus(HttpStatus.SC_OK);
//        resp.setData(conversionService.convert(address, UniPageResp.class));
//        UniPageResp<AddrModel> respData = resp.getData();
//        List<AddrModel> content = respData.getContent();
//        address.forEach(it -> {
//                    AddrModel info = conversionService.convert(address, AddrModel.class);
//                    content.add(info);
//                }
//        );
//        respData.setContent(content);
//        resp.setData(respData);
//        return resp;


        String userId = secService.curUserId();
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrService.list(10, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        List<AddrModel> content = list.getData().getContent().stream().map(it -> conversionService.convert(it, AddrModel.class)).collect(Collectors.toList());
        UniResp<List<AddrModel>> resp = new UniResp<>();
        resp.setData(content);
        return resp;
    }

    @Override
    public UniResp<AddrModel> detail(String brandAppId, String shopId, String id) {
//        Address address = addressRepo.findOne(id);
//        Assert.notNull(address, "地址错误");
//        AddrModel resp = conversionService.convert(address, AddrModel.class);
//        UniResp<AddrModel> uniResp = new UniResp<>();
//        uniResp.setStatus(HttpStatus.SC_OK);
//        uniResp.setData(resp);
//        return uniResp;

        String userId = secService.curUserId();
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addrGetRespUniResp = addrService.get(userId, id);
        AddrModel addrModel = conversionService.convert(addrGetRespUniResp.getData(), AddrModel.class);
        UniResp<AddrModel> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(addrModel);
        return uniResp;
    }

    @Override
    public UniResp<String> save(String brandAppId, String shopId, AddrSaveReq addrSaveReq) {
//        //TODO
////        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
//        Address address = new Address();
//        Optional.ofNullable(addrSaveReq).ifPresent(req ->
//                {
//                    Adc adc = adcRepo.findOneByNo(req.getAdcNo());
//                    address.setAdc(adc);
//                    address.setStreet(req.getStreet());
//                    address.setReceiver(req.getReceiver());
//                    address.setPhone(req.getPhone());
//                    address.setMemo(req.getMemo());
//                    //TODO
////                    address.partnerStaff = partnerStaff
////                    address.partner = partnerStaff.partner
//                    address.setDefaultAddr(false);
//
//                    //判断是否有默认地址，没有则自动添加
//                    Address tmpAddr = addressRepo.findOne(
//                            allOf(
//                                    //TODO
////                        QAddress.address.partner.eq(partnerStaff.partner),
//                                    QAddress.address.deleted.ne(true),
//                                    QAddress.address.defaultAddr.eq(true)
//                            )
//                    );
//                    Optional.ofNullable(tmpAddr).ifPresent(teAddr ->
//                            req.setDefaultAddr(true)
//                    );
//                    if (req.isDefaultAddr()) {
//                        addrService.updateDefaultAddr(address);
//                    }
//                }
//        );
//        addressRepo.save(address);
//        UniResp<String> uniResp = new UniResp<>();
//        uniResp.setStatus(HttpStatus.SC_OK);
//        uniResp.setData("保存成功");
//        return uniResp;


        String userId = secService.curUserId();
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
                    //TODO
//                    address.partnerStaff = partnerStaff
//                    address.partner = partnerStaff.partner
                    address.setDefaultAddr(req.isDefaultAddr());

                    //判断是否有默认地址，没有则自动添加
//                    Address tmpAddr = addressRepo.findOne(
//                            allOf(
//                                    //TODO
////                        QAddress.address.partner.eq(partnerStaff.partner),
//                                    QAddress.address.deleted.ne(true),
//                                    QAddress.address.defaultAddr.eq(true)
//                            )
//                    );

//                    if (req.isDefaultAddr()) {
//                        addrService.updateDefaultAddr(userId, address);
//                    }
                }
        );
        //先获得之前的默认地址
        AddrGetResp shippingAddr = addrService.getDefault(userId, "USER_SHIPPING_ADDR").getData();

        //保存地址，如果之前的已经有默认地址，就有两个默认地址
        addrService.add(userId, address);
        //最多20条地址
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrService.list(20, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        Optional<AddrGetResp> addrGetResp = list.getData().getContent().stream().filter(AddrGetResp::isDefaultAddr).findFirst();
        if (!addrGetResp.isPresent()) {
            //将之前的默认地址设为非默认。将现在的默认地址设为默认
            addrService.setDefault(
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
    public UniResp<String> update(String brandAppId, String shopId, String id, AddrSaveReq addrSaveReq) {
//        //TODO
////        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
//        Address address = addressRepo.findOne(
//                allOf(
//                        //TODO
////                        partnerStaff.partner
//                        QAddress.address.id.eq(id),
//                        QAddress.address.deleted.ne(true)
//                )
//        );
//        Assert.notNull(address, "原地址错误");
//        Optional.ofNullable(addrSaveReq).ifPresent(req ->
//                {
//                    Adc adc = adcRepo.findOneByNo(req.getAdcNo());
//                    address.setAdc(adc);
//                    address.setStreet(req.getStreet());
//                    address.setReceiver(req.getReceiver());
//                    address.setPhone(req.getPhone());
//                    address.setMemo(req.getMemo());
//                    //TODO
////                    address.partnerStaff = partnerStaff
////                    address.partner = partnerStaff.partner
//                    address.setDefaultAddr(false);
//
//                    //判断是否有默认地址，没有则自动添加
//                    Address tmpAddr = addressRepo.findOne(
//                            allOf(
//                                    //TODO
////                                    partnerStaff.partner
//                                    QAddress.address.defaultAddr.eq(true),
//                                    QAddress.address.deleted.ne(true)
//                            )
//                    );
//                    Optional.ofNullable(tmpAddr).ifPresent(teAddr ->
//                            req.setDefaultAddr(true)
//                    );
//
//                    if (req.isDefaultAddr()) {
//                        addrService.updateDefaultAddr(address);
//                    }
//                }
//        );
//        addressRepo.save(address);
//        UniResp<String> uniResp = new UniResp<>();
//        uniResp.setStatus(HttpStatus.SC_OK);
//        uniResp.setData("保存成功");
//        return uniResp;

        AddrUpdateReq addrUpdateReq = new AddrUpdateReq();
        addrUpdateReq.setAdc(Optional.ofNullable(addrSaveReq.getAdcNo()));
        addrUpdateReq.setContact(Optional.ofNullable(addrSaveReq.getReceiver()));
        addrUpdateReq.setDefaultAddr(Optional.of(false));
        addrUpdateReq.setMemo(Optional.ofNullable(addrSaveReq.getMemo()));
        addrUpdateReq.setPhones(Optional.of(new HashSet<>()));
        addrUpdateReq.getPhones().get().add(addrSaveReq.getPhone());
        addrUpdateReq.setStreet(Optional.of(addrSaveReq.getStreet()));

        String userId = secService.curUserId();
        addrService.update(userId, id, addrUpdateReq);

        if (addrSaveReq.isDefaultAddr()) {
            addrService.setDefault(userId, id, "USER_SHIPPING_ADDR");
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> setDefault(String brandAppId, String shopId, String id) {
//        //TODO
////        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
//
//        Address address = addressRepo.findOne(
//                allOf(
//                        //TODO
////                        partnerStaff.partner
//                        QAddress.address.id.eq(id),
//                        QAddress.address.deleted.ne(true)
//                )
//        );
//        Assert.notNull(address, "未找到收货地址");
//        addrService.updateDefaultAddr(address);
//        addressRepo.save(address);
//        UniResp<String> uniResp = new UniResp<>();
//        uniResp.setStatus(HttpStatus.SC_OK);
//        uniResp.setData("保存成功");
//        return uniResp;

        String userId = secService.curUserId();
        addrService.setDefault(userId, id, "USER_SHIPPING_ADDR");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("保存成功");
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String brandAppId, String shopId, String id) {
//        //TODO
//        //PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
//        Address address = addressRepo.findOne(
//                allOf(
//                        //TODO
////                        partnerStaff.partner
//                        QAddress.address.id.eq(id),
//                        QAddress.address.deleted.ne(true)
//                )
//        );
//        Assert.notNull(address, "地址错误");
//        address.setDeleted(true);
//        addressRepo.save(address);
//        List<Address> addressList = Lists.newArrayList(addressRepo.findAll(
//                allOf(
//                        //TODO
////                        partnerStaff.partner
//                        QAddress.address.deleted.ne(true)
//                )
//        ));
//        if (!addressList.isEmpty()) {
//            addrService.updateDefaultAddr(addressList.get(0));
//        }
//        UniResp<String> uniResp = new UniResp<>();
//        uniResp.setStatus(HttpStatus.SC_OK);
//        uniResp.setData("删除成功");
//        return uniResp;

        String userId = secService.curUserId();
        addrService.del(userId, id);
        net.kingsilk.qh.oauth.api.UniResp<UniPage<AddrGetResp>> list = addrService.list(10, 0, null, userId, null);
        if (list == null || list.getData() == null) {
            UniPage<AddrGetResp> uniPage = new UniPage<>();
            uniPage.setContent(new LinkedList<>());
            list = new net.kingsilk.qh.oauth.api.UniResp<>();
            list.setData(uniPage);
        }
        if (!list.getData().getContent().isEmpty()) {
            Optional<AddrGetResp> addrGetResp = list.getData().getContent().stream().filter(AddrGetResp::isDefaultAddr).findFirst();
            if (!addrGetResp.isPresent()) {

                addrService.setDefault(userId, list.getData().getContent().get(0).getId(), "USER_SHIPPING_ADDR");
            }
        }
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData("删除成功");
        return uniResp;
    }

    @Override
    public UniResp<AddrModel> getDefault(String brandAppId, String shopId) {
        String userId = secService.curUserId();
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> addr = addrService.getDefault(userId, "USER_SHIPPING_ADDR");
        AddrModel addrModel = conversionService.convert(addr, AddrModel.class);
        UniResp<AddrModel> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(addrModel);
        return uniResp;
    }
}
