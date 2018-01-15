package net.kingsilk.qh.agency.server.resource.brandApp.partner.addr

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageReq
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrModel
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq
import net.kingsilk.qh.agency.api.brandApp.partner.addr.AddrApi
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QAddress
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.AddressRepo
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.service.AddrService
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import static com.querydsl.core.types.dsl.Expressions.allOf

@Component(value = "addr")
class AddrResource implements AddrApi {

    @Autowired
    AddrService addrService

    @Autowired
    AddressRepo addressRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    AddressConvert addressConvert

    @Autowired
    AdcRepo adcRepo

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    UniResp<UniPageResp<AddrModel>> list(
            String brandAppId,
            String partnerId,
            UniPageReq req
    ) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<Address> address = addressRepo.findAll(
                allOf(
                        QAddress.address.partner.eq(partnerStaff.partner),
                        QAddress.address.deleted.in([false, null])), pageRequest
        )
        UniResp<UniPageResp<AddrModel>> resp = new UniResp<UniPageResp<AddrModel>>()
        resp.status = 200
        resp.data = conversionService.convert(address, UniPageResp)
        address.each { Address it ->
            AddrModel info = addressConvert.addrModelConvert(it)
            resp.data.content.add(info)
        }
        return resp
    }


    @Override
    UniResp<AddrModel> detail(String brandAppId,
                              String partnerId,
                              String id) {
        Address address = addressRepo.findOne(id)
        Assert.notNull(address, "地址错误")
        AddrModel resp = addressConvert.addrModelConvert(address)
        return new UniResp<AddrModel>(status: 200, data: resp)
    }

    @Override
    UniResp<String> save(String brandAppId, String partnerId, AddrSaveReq req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = new Address()
        Adc adc = adcRepo.findOneByNo(req.adcNo)
        address.adc = adc
        address.street = req.street
        address.receiver = req.receiver
        address.phone = req.phone
        address.memo = req.memo
        address.partnerStaff = partnerStaff
        address.partner = partnerStaff.partner
        address.defaultAddr = false

        //判断是否有默认地址，没有则自动添加
        def tmpAddr = addressRepo.findOne(
                allOf(
                        QAddress.address.partner.eq(partnerStaff.partner),
                        QAddress.address.deleted.ne(true),
                        QAddress.address.defaultAddr.eq(true)
                )
        )
        if (!tmpAddr) {
            req.defaultAddr = true
        }

        if (req.defaultAddr) {
            addrService.updateDefaultAddr(address)
        }
        addressRepo.save(address)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<String> update(
            String brandAppId,
            String partnerId,
            String id, AddrSaveReq req) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = addressRepo.findOneByIdAndPartner(id, partnerStaff.partner)
        Assert.isTrue(!address.deleted, "该地址已删除")
        Assert.notNull(address, "原地址错误")
        Adc adc = adcRepo.findOneByNo(req.adcNo)
        address.adc = adc
        address.street = req.street
        address.receiver = req.receiver
        address.phone = req.phone
        address.memo = req.memo
        address.partnerStaff = partnerStaff
        address.partner = partnerStaff.partner
        address.defaultAddr = false

        //判断是否有默认地址，没有则自动添加
        Address tmpAddr = addressRepo.findOneByPartnerAndDefaultAddrAndDeleted(partnerStaff.partner, true, false)
        if (!tmpAddr) {
            req.defaultAddr = true
        }

        if (req.defaultAddr) {
            addrService.updateDefaultAddr(address)
        }
        addressRepo.save(address)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<String> setDefault(String brandAppId,
                               String partnerId,
                               String id) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = addressRepo.findOneByIdAndPartner(id, partnerStaff.partner)
        Assert.isTrue(!address.deleted, "该地址已删除")
        Assert.notNull(address, "未找到收货地址")
        addrService.updateDefaultAddr(address)
        addressRepo.save(address)
        return new UniResp<String>(status: 200, data: "保存成功")
    }


    @Override
    UniResp<String> delete(String brandAppId,
                           String partnerId,
                           String id) {
        PartnerStaff partnerStaff = partnerStaffService.getCurPartnerStaff()
        Address address = addressRepo.findOneByIdAndPartner(id, partnerStaff.partner)
        Assert.notNull(address, "地址错误")
        address.deleted = true
        addressRepo.save(address)
        List<Address> addressList = addressRepo.findAllByPartnerStaffAndDeleted(partnerStaff, false)
        if (addressList && addressList.size() != 0) {
            addrService.updateDefaultAddr(addressList.get(0))
        }
        return new UniResp<String>(status: 200, data: "删除成功")
    }


}
