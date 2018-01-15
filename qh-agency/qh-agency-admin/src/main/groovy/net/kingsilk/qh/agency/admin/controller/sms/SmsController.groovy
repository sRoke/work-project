package net.kingsilk.qh.agency.admin.controller.sms

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.domain.Sms
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.util.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by zcw on 17-3-16.
 */
@RestController()
@RequestMapping("/api/sms")
@Api(
        tags = "sms",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "短信记录相关API"
)
class SmsController {

    @Autowired
    CommonService commonService

    @RequestMapping(path = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "短信列表",
            nickname = "短信列表",
            notes = "短信列表"
    )
    @ResponseBody
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<SmsListResp> list(@RequestParam SmsListReq req) {

        Criteria criteria = commonService.searchDate(req.beginDate, req.endDate, "dateCreated", null)
        criteria = commonService.regex(req.keyword, "phone", criteria)
        Query query = Query.query(criteria)
        query.with(new Sort(Sort.Direction.DESC, "dateCreated"))
        Page<Sms> page = commonService.domainPages(Sms, query, req.curPage, req.pageSize)
        SmsListResp resp = new SmsListResp()
        resp.convert(page)
        return new UniResp<SmsListResp>(status: 200, data: resp)
    }
}
