package net.kingsilk.qh.platform.server.resource.authorities;

import io.swagger.annotations.Api;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.authorities.AuthoritiesApi;
import net.kingsilk.qh.platform.core.StaffAuthorityEnum;
import net.kingsilk.qh.platform.domain.QStaff;
import net.kingsilk.qh.platform.domain.Staff;
import net.kingsilk.qh.platform.repo.StaffRepo;
import net.kingsilk.qh.platform.service.service.SecService;
import net.kingsilk.qh.platform.service.service.StaffAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.ws.rs.Path;
import java.util.Set;

/**
 *
 */
@Api( // 用在类上，用于设置默认值
        tags = "authorities",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "权限管理相关API"
)
@Component
@Path("/authorities")
public class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private SecService secService;

//    @Autowired
//    private StaffService staffService;

    @Autowired
    private StaffAuthorityService staffAuthorityService;

    @Override
    public UniResp<Set<String>> getAuthorities(
    ) {
        String userId = secService.curUserId();

        UniResp<Set<String>> uniResp = new UniResp<>();

        Staff staff = staffRepo.findOne(
                QStaff.staff.userId.eq(userId)
        );
        Set<String> authorities;
        Assert.notNull(staff, "该员工不存在");
        authorities = staffAuthorityService.getAuthorities(staff);
        authorities.forEach(
                it ->
                        staffAuthorityService.fillAuth(authorities, StaffAuthorityEnum.valueOf(it))
        );
        uniResp.setStatus(200);
        uniResp.setData(authorities);

        return uniResp;
    }
}