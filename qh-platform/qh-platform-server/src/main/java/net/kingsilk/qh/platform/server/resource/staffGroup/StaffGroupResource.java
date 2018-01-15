package net.kingsilk.qh.platform.server.resource.staffGroup;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.platform.api.UniPageResp;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.staffGroup.StaffGroupApi;
import net.kingsilk.qh.platform.api.staffGroup.dto.*;
import net.kingsilk.qh.platform.domain.QStaffGroup;
import net.kingsilk.qh.platform.domain.StaffGroup;
import net.kingsilk.qh.platform.repo.StaffGroupRepo;
import net.kingsilk.qh.platform.server.resource.staffGroup.convert.StaffGroupConvert;

import net.kingsilk.qh.platform.service.service.StaffAuthorityService;
import net.kingsilk.qh.platform.service.service.StaffGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.ws.rs.Path;
import java.util.Map;
import java.util.Set;


@Path("/staffGroup")
@Component
public class StaffGroupResource implements StaffGroupApi {

    @Autowired
    private StaffAuthorityService staffAuthorityService;

    @Autowired
    private StaffGroupService staffGroupService;

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    @Autowired
    private StaffGroupConvert staffGroupConvert;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Override
    public UniResp<StaffGroupLoadCurrentUserResp> currentUserInfo(
            String id,
            String source) {
        return null;
    }

    @Override
    public UniResp<StaffGroupInfoResp> info(String id, String source) {
        Assert.notNull(id, "请选择正确的角色");
        StaffGroup staffGroup = staffGroupRepo.findOne(
                Expressions.allOf(
                        QStaffGroup.staffGroup.id.eq(id),
                        QStaffGroup.staffGroup.deleted.in(false)
                ));
        Assert.notNull(staffGroup, "该角色不存在");
        StaffGroupInfoResp staffGroupInfoResp = staffGroupConvert.staffGroupConvert(staffGroup);
        UniResp<StaffGroupInfoResp> uniResp = new UniResp<>();
        uniResp.setData(staffGroupInfoResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> save(StaffGroupSaveReq staffGroupSaveReq) {
        StaffGroup staffGroup = new StaffGroup();
        Assert.notNull(staffGroupSaveReq.getName(), "角色名称不能为空");
        staffGroup.setName(staffGroupSaveReq.getName());
        Assert.notNull(staffGroupSaveReq.getDesp(), "请输入该角色的描述信息");
        staffGroup.setDesp(staffGroupSaveReq.getDesp());
        Assert.notNull(staffGroupSaveReq.getAuthorMap(), "请给角色选择权限");
        staffGroup.setAuthorities(staffGroupSaveReq.getAuthorMap());
        Assert.notNull(staffGroupSaveReq.getReserved(), "是否预留角色不能为空");
        staffGroup.setReserved(staffGroupSaveReq.getReserved());
        staffGroupRepo.save(staffGroup);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }

    @Override
    public UniResp<String> update(String id, StaffGroupSaveReq staffGroupSaveReq) {
        Assert.notNull(id, "请选择正确的角色");
        StaffGroup staffGroup = staffGroupRepo.findOne(
                Expressions.allOf(
                        QStaffGroup.staffGroup.id.eq(id)
//                        QStaffGroup.staffGroup.brandComId.eq(brandComId)
                ));
        Assert.notNull(staffGroup, "该角色不存在");
        if (!StringUtils.isEmpty(staffGroupSaveReq.getDesp())) {
            staffGroup.setDesp(staffGroupSaveReq.getDesp());
        }
        if (!StringUtils.isEmpty(staffGroupSaveReq.getName())) {
            staffGroup.setName(staffGroupSaveReq.getName());
        }
        if (staffGroupSaveReq.getAuthorMap() != null && !staffGroupSaveReq.getAuthorMap().isEmpty()) {
            staffGroup.setAuthorities(staffGroupSaveReq.getAuthorMap());
        }
        staffGroupRepo.save(staffGroup);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPageResp<StaffGroupPageResp>> page(StaffGroupPageReq staffGroupPageReq) {

        PageRequest pageRequest = new PageRequest(staffGroupPageReq.getPage(), staffGroupPageReq.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<StaffGroup> groups = staffGroupRepo.findAll(
                QStaffGroup.staffGroup.deleted.in(false),
                pageRequest);

        Page<StaffGroupPageResp> page = groups.map(staffGroup -> {
            StaffGroupPageResp staffGroupPageResp = new StaffGroupPageResp();
            staffGroupPageResp.setDesp(staffGroup.getDesp());
            staffGroupPageResp.setName(staffGroup.getName());
            staffGroupPageResp.setId(staffGroup.getId());
            return staffGroupPageResp;
        });
        UniResp<UniPageResp<StaffGroupPageResp>> uniResp = new UniResp<>();
        UniPageResp<StaffGroupPageResp> uniPageResp = conversionService.convert(page, UniPageResp.class);
        uniPageResp.setContent(page.getContent());
        uniResp.setData(uniPageResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<StaffGroupLoadResp> load() {
        StaffGroupLoadResp staffGroupLoadResp = new StaffGroupLoadResp();
        Map<String, Map<String, Map<String, String>>> map = staffGroupService.getAuthorMap();
        staffGroupLoadResp.setAuthorMap(map);
        UniResp<StaffGroupLoadResp> uniResp = new UniResp<>();
        uniResp.setData(staffGroupLoadResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String id) {
        Assert.notNull(id, "请选择正确的角色");
        StaffGroup staffGroup = staffGroupRepo.findOne(id);
//        Assert.isTrue(!staffGroup.getReserved(), "不能删除系统预留角色");
        staffGroup.setDeleted(true);
        staffGroupRepo.save(staffGroup);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }

    @Override
    public Set<String> currentAuth() {
        return null;
    }

    @Override
    public UniResp<String> enable(String id, String status) {
        return null;
    }
}
