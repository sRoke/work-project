package net.kingsilk.qh.agency.admin.controller.item

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.controller.itemProp.*
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import net.kingsilk.qh.agency.domain.QItemProp
import net.kingsilk.qh.agency.domain.QItemPropValue
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.ItemPropRepo
import net.kingsilk.qh.agency.repo.ItemPropValueRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.ItemPropValueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api/itemProp")
@Api( // 用在类上，用于设置默认值
        tags = "itemProp",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "商品属性相关API"
)
class ItemPropController {

    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    ItemPropValueService itemPropValueService

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品属性分页信息",
            nickname = "商品属性分页信息",
            notes = "商品属性分页信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_PROP_R')")
    UniResp<ItemPropPageResp> page(ItemPropPageReq itemPropPageReq) {
        Page<ItemProp> itemPropPage = itemPropRepo.findAll(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([null, false]),
                        itemPropPageReq.name ? QItemProp.itemProp.name.contains(itemPropPageReq.name) : null
                ), new PageRequest(itemPropPageReq.curPage,itemPropPageReq.pageSize))
        Page<ItemPropPageResp.ItemPropResp> propPage =  itemPropPage.map({ ItemProp itemProp ->
            ItemPropPageResp.ItemPropResp prop = new ItemPropPageResp.ItemPropResp();
            prop.id = itemProp.id
            prop.code = itemProp.code
            prop.id = itemProp.id
            prop.type = itemProp.itemPropType
            prop.name = itemProp.name
            prop.companyId = itemProp.company == null ? null : itemProp.company.getId();
            prop.companyName = itemProp.company == null ? null : itemProp.company.getName();
            prop.unit = itemProp.unit
            prop.memName = itemProp.memName
            prop.memo = itemProp.memo
            prop.propValues = itemPropValueService.search(itemProp.getId())
            return prop
        });
        ItemPropPageResp resp = new ItemPropPageResp()
        resp.recPage = propPage
        return new UniResp<ItemPropPageResp<Page<ItemPropPageResp.ItemPropResp>>>(status: 200,  data:resp)
    }


    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存商品属性信息",
            nickname = "保存商品属性信息",
            notes = "保存商品属性信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_PROP_C','ITEM_PROP_U')")
    UniResp<String> save(@RequestBody ItemPropSaveReq itemPropSaveReq) {
        ItemProp itemProp = null;
        Set<ItemPropSaveReq.ItemPropValues> newItemPropValues = itemPropSaveReq.itemPropValues;
        if (itemPropSaveReq.id == null) {
            itemProp = new ItemProp();
            itemProp.setCompany(companyRepo.findOne(BrandIdFilter.companyId));
        } else {
            itemProp = itemPropRepo.findOne(itemPropSaveReq.getId())
            if (!itemProp) {
                return new UniResp(status: 10025, data: "数据不存在");
            }
            def list = itemPropValueService.search(itemProp.getId());
            List<String> oldIdList = new ArrayList();
            List<String> haveIdList = new ArrayList();
            for (ItemPropValue oldPropValue : list) {
                oldIdList.add(oldPropValue.getId())
                for (ItemPropSaveReq.ItemPropValues newPropValue : newItemPropValues) {
                    if (oldPropValue.getId().equals(newPropValue.getId())) {
                        haveIdList.add(oldPropValue.getId());
                    }
                }
            }
            oldIdList.removeAll(haveIdList);
            if (oldIdList.size() > 0) {
                for (String itemPropValueId : oldIdList) {
                    ItemPropValue itemPropValue = itemPropValueRepo.findOne(itemPropValueId);
                    itemPropValue.setDeleted(true);
                    itemPropValueRepo.save(itemPropValue)
                }
            }
        }
        itemProp = itemPropSaveReq.convertItemPropReqToItemProp(itemProp);
        itemPropRepo.save(itemProp);

        List<ItemPropValue> list = new ArrayList<ItemPropValue>();
        newItemPropValues.each {
            if (it.getId() == null) {
                ItemPropValue itemPropValue = new ItemPropValue()
                itemPropValue = it.convertToItemPropValue(itemPropValue, itemProp)
                list.add(itemPropValue)
            }
        }
        itemPropValueRepo.save(list)
        return new UniResp(status: 200, data: "保存成功");
    }

    @RequestMapping(path = "/delete",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "删除商品属性信息",
            nickname = "删除商品属性信息",
            notes = "删除商品属性信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_PROP_D')")
    UniResp<String> delete(ItemPropDeleteReq itemPropDeleteReq) {
        ItemProp itemProp = itemPropRepo.findOne(itemPropDeleteReq.id);
        if (!itemProp) {
            return new UniResp(status: 10025, data: "数据不存在");
        } else {
            itemProp.deleted = true;
            itemPropRepo.save(itemProp)
        }
        return new UniResp(status: 200, data: "保存成功");
    }

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "商品属性信息详情",
            nickname = "商品属性信息详情",
            notes = "商品属性信息详情"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF' ) && hasAnyAuthority('ITEM_PROP_R')")
    UniResp<ItemPropInfoResp> info(ItemPropInfoReq itemPropInfoReq) {
        ItemProp itemProp = itemPropRepo.findOne(itemPropInfoReq.getId());
        if (!itemProp) {
            return new UniResp(status: 10025, data: "数据不存在")
        }
        def list = itemPropValueService.search(itemProp.getId());
        return new UniResp(status: 200, data: ItemPropInfoResp.convertItemPropToItemPropInfoResp(itemProp, list));
    }


    @RequestMapping(path = "/itemPropList",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_R')")
    UniResp<ItemPropListResp> list(ItemPropListReq req) {
        //String curUserId = userService.curUser?.username
        String itemPropKeyword = req.itemPropKeyword



        Iterable<ItemProp> itemPropList = itemPropRepo.findAll(
                Expressions.allOf(
                        itemPropKeyword ? QItemProp.itemProp.name.contains(itemPropKeyword) : null,
                        QItemProp.itemProp.deleted.in([false, null])),
        )

        ItemPropListResp resp = new ItemPropListResp()
        resp.convert(itemPropList, req)
        return new UniResp<ItemPropListResp>(status: 200, data: resp)
    }

    @RequestMapping(path = "/itemPropListItem",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ITEM_R')")
    UniResp<ItemPropValueListResp> itemPropListItem(ItemPropListReq req) {
        String ItemPropValue = req.itemPropKeyword


        Iterable<ItemPropValue> itemPropValueList = itemPropValueRepo.findAll(
                Expressions.allOf(
                        QItemPropValue.itemPropValue.itemProp.id.eq(ItemPropValue),
                        QItemPropValue.itemPropValue.deleted.in([false, null])),
        )

        ItemPropValueListResp resp = new ItemPropValueListResp()
        resp.convert(itemPropValueList, req)
        return new UniResp<ItemPropValueListResp>(status: 200, data: resp)
    }
}
