package net.kingsilk.qh.agency.wap.resource.report

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.repo.OrderRepo
import net.kingsilk.qh.agency.repo.QhPayRepo
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.ReportService
import net.kingsilk.qh.agency.util.DbUtil
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.report.dto.PurchaseReq
import net.kingsilk.qh.agency.wap.api.report.dto.PurchaseResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.BeanParam
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by lit on 17/7/28.
 */
@Api(
        tags = "report",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "报表相关接口"
)
@Path("/report")
@Component
class ReportResource {

    @Autowired
    OrderRepo orderRepo

    @Autowired
    MemberService memberService

    @Autowired
    QhPayRepo qhPayRepo

    @Autowired
    DbUtil dbUtil

    @Autowired
    ReportService reportService

    @ApiOperation(
            value = "首页统计",
            nickname = "首页统计",
            notes = "首页统计"
    )
    @Path("/purchase")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PurchaseResp> purchase(@BeanParam PurchaseReq purchaseReq) {
        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        Date todayStartTime = todayStartCalendar.getTime()

        Calendar monthStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        monthStartCalendar.set(Calendar.DAY_OF_MONTH, 1);
        monthStartCalendar.set(monthStartCalendar.get(Calendar.YEAR), monthStartCalendar.get(Calendar.MONTH), monthStartCalendar.get(Calendar.DATE) - 1, 16, 0, 0);
        Date monthStartTime = monthStartCalendar.getTime()
        reportService.purchase(todayStartTime)
        PurchaseResp resp = new PurchaseResp()
        resp.todayPurchaseNum = reportService.purchase(todayStartTime)
        resp.monthPpurchaseNum = reportService.purchase(monthStartTime)
        return new UniResp<PurchaseResp>(status: 200, data: resp)
    }
}
