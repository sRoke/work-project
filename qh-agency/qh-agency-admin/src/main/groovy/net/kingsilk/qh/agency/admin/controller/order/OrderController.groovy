package net.kingsilk.qh.agency.admin.controller.order

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.service.ExcelWrite
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.*
import net.kingsilk.qh.agency.service.EnumService
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.OrderService
import net.kingsilk.qh.agency.service.RefundService
import net.kingsilk.qh.agency.util.DateUtil
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse


/**
 * Created by yanfq on 17-4-07.
 */
@RestController()
@RequestMapping("/api/order")
@Api( // 用在类上，用于设置默认值
        tags = "order",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "订单管理相关API"
)
class OrderController {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    RefundRepo refundRepo

    @Autowired
    LogisticsRepo logisticsRepo

    @Autowired
    RefundService refundService

    @Autowired
    EnumService enumService

    @Autowired
    OrderService orderService

    @Autowired
    AdcRepo adcRepo

    @Autowired
    MemberService memberService


    @Autowired
    ExcelWrite excelWrite

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息"
    )
    @ApiParam(value = "id")
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_R')")
    UniResp<OrderInfoResp> info(String id) {
        if (id == null) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        Order order = orderRepo.findOne(id);
        if (!order) {
            return new UniResp(status: 10027, message: "订单信息不存在")
        }
        List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(order.userId, BrandIdFilter.companyId)
        List<Refund> refunds = refundService.search(null,id)
        OrderInfoResp resp = new OrderInfoResp()
        return new UniResp<OrderInfoResp>(status: 200, data: resp.convertOrderToResp(order, members,refunds))
    }

    @RequestMapping(path = "/adjustAprice",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "更改价格",
            nickname = "更改价格",
            notes = "更改价格"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_C')")
    UniResp<String> adjustAprice(@RequestBody OrderAdjustReq orderAdjustReq) {
        if (orderAdjustReq.id != null) {
            Order order = orderRepo.findOne(orderAdjustReq.id);
            if (order != null && orderAdjustReq.adjustPrice != null) {
                order.paymentPrice = orderAdjustReq.adjustPrice;
                Integer adjustPrice = order.orderPrice - order.paymentPrice;
                Integer allPrice = 0;
                for (Order.OrderItem orderItem : order.orderItems) {
                    allPrice += orderItem.num * orderItem.skuPrice
                }
                for (int i = 0; i < order.orderItems.size(); i++) {
                    Integer price = order.orderItems.get(i).skuPrice * order.orderItems.get(i).num
                    if (i != 0 && i == order.orderItems.size()-1) {
                        order.orderItems.get(i).realTotalPrice = price - adjustPrice
                    } else {
                        int thisPrice = (price / allPrice) * adjustPrice
                        order.orderItems.get(i).realTotalPrice = price - thisPrice
                        adjustPrice = adjustPrice - thisPrice
                        allPrice = allPrice - price
                    }
                }
                orderRepo.save(order);
            }
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @RequestMapping(path = "/ship",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "发货",
            nickname = "发货",
            notes = "发货"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_U')")
    UniResp<String> ship(@RequestBody OrderShipReq orderShipReq) {
        if (orderShipReq.id != null) {
            Order order = orderRepo.findOne(orderShipReq.id);
            if (order != null && orderShipReq.company != null && orderShipReq.expressNo != null) {
                Logistics logistic = new Logistics();
                logistic.company = orderShipReq.company;
                logistic.expressNo = orderShipReq.expressNo;
                logisticsRepo.save(logistic)
                order.logisticses.add(logistic);
                order.status = OrderStatusEnum.UNRECEIVED;
                orderRepo.save(order);
            }
        }
        return new UniResp<String>(status: 200, data: "发货成功")
    }

    @RequestMapping(path = "/updateAddress",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "修改收货地址",
            nickname = "修改收货地址",
            notes = "修改收货地址"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_D')")
    UniResp<String> updateAddress(@RequestBody OrderAddressReq orderAddressReq) {
        if (orderAddressReq.orderId != null) {
            Order order = orderRepo.findOne(orderAddressReq.orderId);
            if (order != null) {
//                Order.OrderAddress orderAddress = order.orderAddress;
//                if (order.orderAddress == null) {
//                    orderAddress = new Order.OrderAddress()
//                }
//                Adc adc = adcRepo.findOneByNo(orderAddressReq.countyNo)
//                orderAddress.receiver = orderAddressReq.receiver
//                orderAddress.phone = orderAddressReq.phone
//                orderAddress.street = orderAddressReq.street
//                orderAddress.adc = adc
//                order.orderAddress = orderAddress;
//                orderRepo.save(order);
            }
        }
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_R')")
    UniResp<OrderPageResp> page(OrderPageReq orderPageReq) {

        PageRequest pageRequest = new PageRequest(
                orderPageReq.curPage,
                orderPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        Date startDate = DateUtil.dateToStartDateTime(orderPageReq.startDate)
        Date endDate = DateUtil.dateToEndDateTime(orderPageReq.endDate)

        Page page = orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.deleted.in([false, null]),
                        startDate ? QOrder.order.dateCreated.gt(startDate) : null,
                        endDate ? QOrder.order.dateCreated.lt(endDate) : null,
                        orderPageReq.status ? QOrder.order.status.eq(orderPageReq.status) : QOrder.order.status.ne(OrderStatusEnum.UNCOMMITED),
                        Expressions.anyOf(
                                orderPageReq.keyWord ? QOrder.order.seq.contains(orderPageReq.keyWord) : null,
                                orderPageReq.keyWord ? QOrder.order.address.phone.eq(orderPageReq.keyWord) : null,
                                orderPageReq.keyWord ? QOrder.order.orderAddress.phone.eq(orderPageReq.keyWord) : null,
                        )
                ),
                pageRequest
        )
        Page<OrderPageResp.OrderInfo> infoPage = page.map({ Order order ->
            OrderPageResp.OrderInfo info = new OrderPageResp.OrderInfo();
            info.id = order.id
            info.seq = order.seq
            List<PartnerStaff> members = memberService.findByUserIdAndCompanyId(order.userId, BrandIdFilter.companyId)
            if (members.size() > 0) {
                info.realName = members.get(0).realName
                info.phone = members.get(0).phone
            }
            info.orderPrice = order.orderPrice
            info.paymentPrice = order.paymentPrice
            info.status = order.status
            info.statusDesp = order.status.desp
            info.dateCreated = order.dateCreated
            for (Order.OrderItem orderItem : order.orderItems) {
                OrderPageResp.OrderItemInfo orderItemInfo = new OrderPageResp.OrderItemInfo()
                orderItemInfo.convertOrderItemToResp(orderItem)
                info.orderItems.add(orderItemInfo)
            }
            return info
        });

        OrderPageResp resp = new OrderPageResp()
        resp.recPage = infoPage
        resp.orderStatusEnumMap = OrderStatusEnum.getMap()
        resp.dataCountMap = orderService.getDataCountMap()
        return new UniResp<OrderPageResp<Page<OrderPageResp.OrderInfo>>>(status: 200, data: resp)
    }

    @RequestMapping(path = "/queryAdc",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "搜索adc",
            nickname = "搜索adc",
            notes = "搜索adc"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    UniResp<AddrResp> queryAdc(@RequestBody AddrReq req) {
        Adc adc = null
        if (req.typeNo) {
            adc = adcRepo.findOneByNo(req.typeNo)
        }
        List<Adc> adcList = adcRepo.findAllByParent(adc)

        AddrResp resp = new AddrResp()
        resp.convert(adc?.name, req.level, adcList)
        return new UniResp<AddrResp>(status: 200, data: resp)
    }


    @RequestMapping(path = "/export",
            method = RequestMethod.GET,
            produces = MediaType.ALL_VALUE)
    @ApiOperation(
            value = "导出订单",
            nickname = "导出订单",
            notes = "导出订单"
    )
    @ApiParam(value = "id")
    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('ORDER_E')")
    void exportExcel(String id, HttpServletResponse response) throws Exception {
        Order order = orderRepo.findOne(id)
        String title = "桐乡郝太太蚕食被销售单"
        // 输出Excel文件
        OutputStream out = response.getOutputStream();
        response.reset();
        String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
        response.setContentType("application/x-xls");
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook()
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 600);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("桐乡郝太太蚕食被销售单"));
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(range);

        row = sheet.createRow(1);
        row.setHeight((short) 500);
        range = new CellRangeAddress(1, 1, 0, 6);
        sheet.addMergedRegion(range);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("如有疑问，请拨打客服专线0573-89377511"));
        cell = row.createCell(1);
        range = new CellRangeAddress(1, 1, 8, 10);
        sheet.addMergedRegion(range);
        cell = row.createCell(8);
        cell.setCellValue(new HSSFRichTextString("NO.20170229"));

        row = sheet.createRow(2);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("客户名称"));
        cell = row.createCell(1);
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("客户地址"));
        cell = row.createCell(3);
        range = new CellRangeAddress(2, 2, 3, 6);
        sheet.addMergedRegion(range);
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("联系电话"));
        cell = row.createCell(8);
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("订单日期"));
        cell = row.createCell(10);
        cell.setCellValue(new HSSFRichTextString("2017.02.19"));

        row = sheet.createRow(3);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("发货日期"));
        cell = row.createCell(1);
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("汇款银行"));
        cell = row.createCell(3);
        range = new CellRangeAddress(3, 3, 3, 6);
        sheet.addMergedRegion(range);
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("汇款日期"));
        cell = row.createCell(8);
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("业务代表"));
        cell = row.createCell(10);
        cell.setCellValue(new HSSFRichTextString("张三"));

        row = sheet.createRow(4);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("预计到货"));
        cell = row.createCell(1);
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("物流详情"));
        cell = row.createCell(3);
        range = new CellRangeAddress(4, 4, 3, 6);
        sheet.addMergedRegion(range);
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("运费"));
        cell = row.createCell(8);
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("发货方式"));
        cell = row.createCell(10);
        cell.setCellValue(new HSSFRichTextString("款到发货"));

        row = sheet.createRow(5);
        row.setHeight((short) 400);
        range = new CellRangeAddress(5, 5, 0, 1);
        sheet.addMergedRegion(range);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("产品名称"));
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("颜色"));
        cell = row.createCell(3);
        cell.setCellValue(new HSSFRichTextString("规格"));
        cell = row.createCell(4);
        cell.setCellValue(new HSSFRichTextString("重量(kg)"));
        cell = row.createCell(5);
        cell.setCellValue(new HSSFRichTextString("数量"));
        cell = row.createCell(6);
        cell.setCellValue(new HSSFRichTextString("单位"));
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("预计毛重"));
        cell = row.createCell(8);
        cell.setCellValue(new HSSFRichTextString("单价"));
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("金额"));
        cell = row.createCell(10);
        cell.setCellValue(new HSSFRichTextString("备注"));
        int rowIndex = 5;
        for (Order.OrderItem orderItem : order.orderItems) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            row.setHeight((short) 400);
            CellRangeAddress rangeTmp = new CellRangeAddress(rowIndex, rowIndex, 0, 1);
            sheet.addMergedRegion(rangeTmp);
            cell = row.createCell(0);
            cell.setCellValue(new HSSFRichTextString(orderItem.sku.item.title));
            cell = row.createCell(2);
            cell.setCellValue(new HSSFRichTextString());
            cell = row.createCell(3);
            cell.setCellValue(new HSSFRichTextString());
            cell = row.createCell(4);
            cell.setCellValue(new HSSFRichTextString("xkg"));
            cell = row.createCell(5);
            cell.setCellValue(new HSSFRichTextString(orderItem.num.toString()));
            cell = row.createCell(6);
            cell.setCellValue(new HSSFRichTextString("单位"));
            cell = row.createCell(7);
            cell.setCellValue(new HSSFRichTextString("x"));
            cell = row.createCell(8);
            cell.setCellValue(new HSSFRichTextString("x"));
            cell = row.createCell(9);
            cell.setCellValue(new HSSFRichTextString("x"));
        }
        rowIndex += 2;
        row = sheet.createRow(rowIndex);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("实收金额"));
        cell = row.createCell(3);
        cell.setCellValue(new HSSFRichTextString("销售成本"));
        cell = row.createCell(5);
        cell.setCellValue(new HSSFRichTextString("利润"));
        cell = row.createCell(8);
        cell.setCellValue(new HSSFRichTextString("销售金额"));
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("0"));

        rowIndex++;
        row = sheet.createRow(rowIndex);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("制表："));
        cell = row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("生产："));
        cell = row.createCell(4);
        cell.setCellValue(new HSSFRichTextString("发货："));
        cell = row.createCell(7);
        cell.setCellValue(new HSSFRichTextString("审核："));
        cell = row.createCell(9);
        cell.setCellValue(new HSSFRichTextString("财务："));

        workbook.write(out)
        out.close()
        out.flush()
    }
}
