package net.kingsilk.qh.shop.server.resource.brandApp.staff;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.staff.StaffApi;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffInfoResp;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffMinInfo;
import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffSaveReq;
import net.kingsilk.qh.shop.domain.Staff;
import net.kingsilk.qh.shop.domain.StaffGroup;
import net.kingsilk.qh.shop.repo.StaffRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.staff.convert.StaffConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lit on 17/11/7.
 */
@Component
public class StaffResource implements StaffApi {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private StaffConvert staffConvert;

    @Override
    public UniResp<StaffInfoResp> info(String brandAppId, String id) {

        UniResp<StaffInfoResp> uniResp = new UniResp<>();

        Staff staff = staffRepo.findOne(id);
        if (staff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("员工信息不存在");
            return uniResp;
        }
        StaffInfoResp infoResp = staffConvert.staffInfoRespConvert(staff);
//
//        staffGroupService.search(null, staff.getId()).each {
//            StaffGroup staffGroup ->
//            infoResp.staffGroupList.add(staffConvert.staffGroupModelConvert(staffGroup));
//        }
//
//        //TODO 需要oauth查其他信息
//        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> respUniResp =
//                userApi.get(staff.getUserId());
//        infoResp.setRealName(respUniResp.data.realName);
//        infoResp.setPhone(respUniResp.data.phone);

        uniResp.setStatus(200);
        uniResp.setData(infoResp);
        return uniResp;
    }

    @Override
    public UniResp<String> save(
            String brandAppId,
            StaffSaveReq staffSaveReq) {
        return null;
    }

    @Override
    public UniResp<String> update(
            String brandAppId,
            String id,
            StaffSaveReq staffSaveReq) {
        return null;
    }

    @Override
    public UniResp<UniPageResp<StaffMinInfo>> page(
            String brandAppId,
            int size,
            int page,
            List<String> sort,
            String keyWord) {
        return null;
    }

    @Override
    public UniResp<String> enable(
            String brandAppId,
            String id,
            boolean status) {
        return null;
    }
}
