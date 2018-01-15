//package net.kingsilk.qh.agency.wap.controller.addr
//
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import io.swagger.annotations.ApiResponse
//import io.swagger.annotations.ApiResponses
//import net.kingsilk.qh.agency.wap.api.UniResp
//import net.kingsilk.qh.agency.domain.Adc
//import net.kingsilk.qh.agency.domain.Address
//import net.kingsilk.qh.agency.domain.QAddress
//import net.kingsilk.qh.agency.repo.AdcRepo
//import net.kingsilk.qh.agency.repo.AddressRepo
//import net.kingsilk.qh.agency.service.CommonService
//import net.kingsilk.qh.agency.service.SecService
//
//import net.kingsilk.qh.agency.wap.controller.addr.model.AddrModel
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Sort
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.*
//
//import static com.querydsl.core.types.dsl.Expressions.allOf
//
///**
// * Created by zcw on 17-3-16.
// */
//@RestController()
//@RequestMapping("/api/addr")
//@Api(
//        tags = "addr",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "地址管理相关API"
//)
//class AddrController {
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//    @Autowired
//    AdcRepo adcRepository
//
//    @Autowired
//    AddressRepo addressRepo
//
//    @Autowired
//    SecService secService
//
//    @RequestMapping(path = "/list",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "获取收货地址列表",
//            nickname = "获取收货地址列表",
//            notes = "获取收货地址列表"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<AddrListResp> list(AddrListReq req) {
//        String curUserId = secService.curUserId()
//
//        PageRequest pageRequest = new PageRequest(req.curPage, req.pageSize,
//                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
//        Page page = addressRepo.findAll(
//                allOf(
//                        QAddress.address.userId.eq(curUserId),
//                        QAddress.address.deleted.in([false, null])),
//                pageRequest
//        )
//        AddrListResp resp = new AddrListResp()
//        resp.convert(page, req)
//        return new UniResp<AddrListResp>(status: 200, data: resp)
//    }
//
//    @RequestMapping(path = "/detail",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "获取收货地址详情",
//            nickname = "获取收货地址详情",
//            notes = "获取收货地址详情"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<AddrModel> detail(AddrDetailReq req) {
//        String curUserId = secService.curUserId()
//        Address address = addressRepo.findOneByIdAndUserId(req.id, curUserId)
//        Assert.notNull(address, "地址错误")
//        AddrModel resp = new AddrModel()
//        resp.convert(address)
//        return new UniResp<AddrModel>(status: 200, data: resp)
//    }
//
//    @RequestMapping(path = "/save",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "保存收货地址",
//            nickname = "保存收货地址",
//            notes = "保存收货地址"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<String> save(@RequestBody AddrSaveReq req) {
//        String curUserId = secService.curUserId()
//        Address address = new Address()
//        if (req.id) {
//            address = addressRepo.findOneByIdAndUserId(req.id, curUserId)
//            Assert.isTrue(!address.deleted, "该地址已删除")
//            Assert.notNull(address, "原地址错误")
//        }
//        Adc adc = adcRepository.findOneByNo(req.adcNo)
//        address.adc = adc
//        address.street = req.street
//        address.receiver = req.receiver
//        address.phone = req.phone
//        address.memo = req.memo
//        address.userId = curUserId
//        address.setDefault(false)
//
//        //判断是否有默认地址，没有则自动添加
//        Address tmpAddr = addressRepo.findOneByUserIdAndIsDefaultAndDeleted(curUserId, false, false)
//        if (!tmpAddr) {
//            req.isDefault = true
//        }
//
//        if (req.isDefault) {
//            this.updateDefaultAddr(address)
//        }
//        mongoTemplate.save(address)
//        return new UniResp<String>(status: 200, data: "保存成功")
//    }
//
//    @RequestMapping(path = "/setDefault",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "设置默认收货地址",
//            nickname = "设置默认收货地址",
//            notes = "设置默认收货地址"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<String> setDefault(AddrSetDefaultReq req) {
//        String curUserId = secService.curUserId()
//        Address address = addressRepo.findOneByIdAndUserId(req.id, curUserId)
//        Assert.isTrue(!address.deleted, "该地址已删除")
//        Assert.notNull(address, "未找到收货地址")
//        this.updateDefaultAddr(address)
//        mongoTemplate.save(address)
//        return new UniResp<String>(status: 200, data: "保存成功")
//    }
//
//    @RequestMapping(path = "/queryAdc",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "搜索adc",
//            nickname = "搜索adc",
//            notes = "搜索adc"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<AddrQueryAdcResp> queryAdc(AddrQueryAdcReq req) {
//        Adc adc = null
//        if (req.adc) {
//            adc = adcRepository.findOneByNo(req.adc)
//        }
//        List<Adc> adcList = adcRepository.findAllByParent(adc)
//        AddrQueryAdcResp resp = new AddrQueryAdcResp()
//        resp.convert(adc?.name, adcList)
//        return new UniResp<AddrQueryAdcResp>(status: 200, data: resp)
//    }
//
//    @RequestMapping(path = "/delete",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "删除收货地址",
//            nickname = "删除收货地址",
//            notes = "删除收货地址"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
//    UniResp<String> delete(AddrDeleteReq req) {
//        String curUserId = secService.curUserId()
//        Address address = addressRepo.findOneByIdAndUserId(req.id, curUserId)
//        Assert.notNull(address, "地址错误")
//        address.deleted = true
//        mongoTemplate.save(address)
//        return new UniResp<String>(status: 200, data: "删除成功")
//    }
//
//    /**
//     * 设置默认地址，当前地址不save
//     */
//    private void updateDefaultAddr(Address address) {
//        if (!address) {
//            return
//        }
//        List<Address> list = addressRepo.findAllByUserId(address.userId)
//        list.each { Address it ->
//            if (it.default && it.id != address.id) {
//                it.default = false
//                mongoTemplate.save(it)
//            }
//        }
//        address.default = true
//        //mongoTemplate.save(address)
//    }
//}
