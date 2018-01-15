package net.kingsilk.qh.agency.admin.controller.Common

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.service.EnumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by tpx on 17-3-22.
 */
@RestController()
@RequestMapping("/api/common")
@Api( // 用在类上，用于设置默认值
        tags = "common",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "通用获取信息相关API"
)
class CommonController {

    @Autowired
    EnumService enumService

    /**
     * 获取全部或指定的枚举信息
     * @return
     */
    @RequestMapping(path = "/enums",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "获取枚举信息",
            nickname = "获取枚举信息",
            notes = "获取枚举信息"
    )
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp enums(String enumType) {
        if (enumType) {
            return new UniResp(status: 200, data: enumService.getEnumMap(enumType))
        } else {
            return new UniResp(status: 200, data: enumService.getEnumMap())
        }
    }
}
