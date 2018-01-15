package net.kingsilk.qh.agency.admin.resource.itemProp

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.itemProp.ItemPropApi
import net.kingsilk.qh.agency.admin.api.itemProp.dto.*
import net.kingsilk.qh.agency.admin.resource.itemProp.convert.ItemPropConvert
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.domain.ItemPropValue
import net.kingsilk.qh.agency.domain.QItemProp
import net.kingsilk.qh.agency.domain.QItemPropValue
import net.kingsilk.qh.agency.repo.ItemPropRepo
import net.kingsilk.qh.agency.repo.ItemPropValueRepo
import net.kingsilk.qh.agency.service.ItemPropValueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Api(
        tags = "itemProp",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品属性相关API"
)
@Path("/itemProp")
@Component
class ItemPropResource implements ItemPropApi{

    @Autowired
    ItemPropRepo itemPropRepo

    @Autowired
    ItemPropValueService itemPropValueService

    @Autowired
    ItemPropValueRepo itemPropValueRepo

    @Autowired
    ItemPropConvert itemPropConvert

    @ApiOperation(
            value = "商品属性分页信息",
            nickname = "商品属性分页信息",
            notes = "商品属性分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropPageResp> page(@BeanParam ItemPropPageReq itemPropPageReq) {
        Page<ItemProp> itemPropPage = itemPropRepo.findAll(
                Expressions.allOf(
                        QItemProp.itemProp.deleted.in([null, false]),
                        itemPropPageReq.name ? QItemProp.itemProp.name.contains(itemPropPageReq.name) : null
                ), new PageRequest(itemPropPageReq.getCurPage(), itemPropPageReq.getPageSize()))
        Page<ItemPropPageResp.ItemPropResp> propPage = itemPropPage.map({ ItemProp itemProp ->
            ItemPropPageResp.ItemPropResp prop = new ItemPropPageResp.ItemPropResp();
            prop.id = itemProp.id
            prop.code = itemProp.code
            prop.id = itemProp.id
            prop.type = itemProp.itemPropType
            prop.name = itemProp.name
//            prop.companyId = itemProp.company == null ? null : itemProp.company.getId();
//            prop.companyName = itemProp.company == null ? null : itemProp.company.getName();
            prop.unit = itemProp.unit
            prop.memName = itemProp.memName
            prop.memo = itemProp.memo
            prop.propValues = itemPropValueService.search(itemProp.getId()).asList()
            return prop
        });
        ItemPropPageResp resp = new ItemPropPageResp()
        resp.recPage = propPage
        return new UniResp<ItemPropPageResp>(status: 200, data: resp)
    }

    @ApiOperation(
            value = "保存商品属性信息",
            nickname = "保存商品属性信息",
            notes = "保存商品属性信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(ItemPropSaveReq itemPropSaveReq) {
        ItemProp itemProp = null;
        Set<ItemPropSaveReq.ItemPropValues> newItemPropValues = itemPropSaveReq.itemPropValues;
        if (itemPropSaveReq.id == null) {
            itemProp = new ItemProp();
//            itemProp.setCompany(companyRepo.findOne(BrandIdFilter.companyId));
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
        itemProp = itemPropConvert.itemPropSaveReqConvert(itemPropSaveReq, itemProp)
        itemPropRepo.save(itemProp);

        List<ItemPropValue> list = new ArrayList<ItemPropValue>();
        newItemPropValues.each {
            if (it.getId() == null) {
                ItemPropValue itemPropValue = new ItemPropValue()
                itemPropValue = itemPropConvert.convertToItemPropValue(itemPropValue, itemProp, it)
                list.add(itemPropValue)
            }
        }
        itemPropValueRepo.save(list)
        return new UniResp(status: 200, data: "保存成功");
    }

    @ApiOperation(
            value = "删除商品属性信息",
            nickname = "删除商品属性信息",
            notes = "删除商品属性信息"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@ApiParam(value = "商品属性的ID", required = true)
                           @PathParam(value = "id")
                                   String id) {
        ItemProp itemProp = itemPropRepo.findOne(id);
        if (!itemProp) {
            return new UniResp(status: 10025, data: "数据不存在");
        } else {
            itemProp.deleted = true;
            itemPropRepo.save(itemProp)
        }
        return new UniResp(status: 200, data: "保存成功");
    }

    @ApiOperation(
            value = "商品属性信息详情",
            nickname = "商品属性信息详情",
            notes = "商品属性信息详情"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropInfoResp> info(@ApiParam(value = "商品属性的ID", required = true)
                                   @PathParam(value = "id")
                                           String id) {
        ItemProp itemProp = itemPropRepo.findOne(id);
        if (!itemProp) {
            return new UniResp(status: 10025, data: "数据不存在")
        }
        def list = itemPropValueService.search(itemProp.getId()).asList()
        return new UniResp(status: 200, data: itemPropConvert.itemPropInfoRespConvert(itemProp, list));
    }

    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @Path("/itemPropList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropListResp> list(@BeanParam ItemPropListReq req) {
        String itemPropKeyword = req.itemPropKeyword

        Iterable<ItemProp> itemPropList = itemPropRepo.findAll(
                Expressions.allOf(
                        itemPropKeyword ? QItemProp.itemProp.name.contains(itemPropKeyword) : null,
                        QItemProp.itemProp.deleted.in([false, null])),
        )

        return new UniResp<ItemPropListResp>(status: 200, data: itemPropConvert.itemPropListRespConvert(itemPropList, req))
    }

    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @Path("/itemPropListItem")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropValueListResp> itemPropListItem(@BeanParam ItemPropListReq req) {
        String ItemPropValue = req.itemPropKeyword
        Iterable<ItemPropValue> itemPropValueList = itemPropValueRepo.findAll(
                Expressions.allOf(
                        QItemPropValue.itemPropValue.itemProp.id.eq(ItemPropValue),
                        QItemPropValue.itemPropValue.deleted.in([false, null])),
        )

        return new UniResp<ItemPropValueListResp>(status: 200, data: itemPropConvert.itemPropValueListRespConvert(itemPropValueList, req))
    }

}
