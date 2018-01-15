package net.kingsilk.qh.agency.server.resource.brandApp.partner.report

import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.partner.report.ReportApi
import net.kingsilk.qh.agency.api.brandApp.partner.report.dto.PurchaseReq
import net.kingsilk.qh.agency.api.brandApp.partner.report.dto.PurchaseResp
import net.kingsilk.qh.agency.core.OrderStatusEnum
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.service.PartnerService
import net.kingsilk.qh.agency.service.PartnerStaffService
import net.kingsilk.qh.agency.service.ReportService
import net.kingsilk.qh.agency.util.DbUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class ReportResource implements ReportApi {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    DbUtil dbUtil

    @Autowired
    ReportService reportService

    @Autowired
    PartnerService partnerService

    @Override
    UniResp<PurchaseResp> purchase(String brandAppId,
                                   String partnerId,
                                   PurchaseReq purchaseReq) {

        partnerService.check()

        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"))
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0)
        Date todayStartTime = todayStartCalendar.getTime()

        Calendar yesterdayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"))
        todayStartCalendar.set(yesterdayStartCalendar.get(Calendar.YEAR), yesterdayStartCalendar.get(Calendar.MONTH), yesterdayStartCalendar.get(Calendar.DATE) - 2, 24, 0, 0)
        Date yesterdayStartTime = todayStartCalendar.getTime()

        Calendar monthStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"))
        monthStartCalendar.set(Calendar.DAY_OF_MONTH, 1)
        monthStartCalendar.set(monthStartCalendar.get(Calendar.YEAR), monthStartCalendar.get(Calendar.MONTH), monthStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0)
        Date monthStartTime = monthStartCalendar.getTime()
        int MaxDay = monthStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        Date monthEndTime = monthStartTime + MaxDay
        PurchaseResp resp = new PurchaseResp()
        List<OrderStatusEnum> olist = new ArrayList<>()
        olist.add(OrderStatusEnum.valueOf("UNCOMMITED"))
        olist.add(OrderStatusEnum.valueOf("UNPAYED"))
        olist.add(OrderStatusEnum.valueOf("REJECTED"))
        olist.add(OrderStatusEnum.valueOf("CANCELED"))
        olist.add(OrderStatusEnum.valueOf("CLOSED"))
        resp.todayPurchaseNum = reportService.purchase(todayStartTime, todayStartTime + 1, olist).get("price")
        resp.monthPpurchaseNum = reportService.purchase(monthStartTime, monthEndTime, olist).get("price")
        resp.yesterdaySaleNum = reportService.purchase(yesterdayStartTime, yesterdayStartTime + 1, olist).get("price")
        resp.todaySaleNum = reportService.purchase(todayStartTime, todayStartTime + 1, olist).get("num")

        return new UniResp<PurchaseResp>(status: 200, data: resp)
    }
}
