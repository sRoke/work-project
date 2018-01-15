package net.kingsilk.qh.activity.server.resource.brandApp.staff;

import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.staff.StaffApi;
import net.kingsilk.qh.activity.api.brandApp.staff.dto.StaffAddReq;
import net.kingsilk.qh.activity.api.brandApp.staff.dto.StaffResp;

import java.util.List;

public class StaffResource implements StaffApi{
    @Override
    public UniResp<String> add(String brandAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<String> update(String brandAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<Void> delete(String brandAppId, String id) {
        return null;
    }

    @Override
    public UniResp<StaffResp> info(String brandAppId, String id) {
        return null;
    }

    @Override
    public UniResp<UniPage<StaffResp>> page(String brandAppId, int size, int page, List<String> sort) {
        return null;
    }
}
