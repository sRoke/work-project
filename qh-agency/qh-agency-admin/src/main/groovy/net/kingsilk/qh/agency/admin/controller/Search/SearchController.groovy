package net.kingsilk.qh.agency.admin.controller.Search

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.repo.StaffRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by lit on 17-4-5.
 */

@RestController()
@RequestMapping("/api/search")
@Api( // 用在类上，用于设置默认值
        tags = "search",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "模糊查询相关API"
)
class SearchController {


    @Autowired
    StaffRepo staffRepo


    @RequestMapping(path = "/staffList",
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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<StaffListResp> list(StaffListReq req) {
        //String curUserId = userService.curUser?.username
        String staffKeyword = req.staffKeyword


        PageRequest pageRequest = new PageRequest(req.curPage, req.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        Page page = staffRepo.findAll(
                Expressions.allOf(
                        staffKeyword?QStaff.staff.realName.contains(staffKeyword):null,
//                        QStaff.staff.realName.contains(staffKeyword),
                        QStaff.staff.deleted.in([false, null])),
                pageRequest
        )

        StaffListResp resp = new StaffListResp()
        resp.convert(page, req)
        return new UniResp<StaffListResp>(status: 200, data: resp)
    }
}
