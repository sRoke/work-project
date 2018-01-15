package net.kingsilk.qh.raffle.server.resource.raffleApp.staff;

import net.kingsilk.qh.raffle.api.common.UniPage;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.staff.StaffApi;
import net.kingsilk.qh.raffle.api.raffleApp.staff.dto.StaffAddReq;
import net.kingsilk.qh.raffle.api.raffleApp.staff.dto.StaffResp;

import java.util.List;

public class StaffResource implements StaffApi{
    @Override
    public UniResp<String> add(String raffleAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<String> update(String raffleAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<Void> delete(String raffleAppId, String id) {
        return null;
    }

    @Override
    public UniResp<StaffResp> info(String raffleAppId, String id) {
        return null;
    }

    @Override
    public UniResp<UniPage<StaffResp>> page(String raffleAppId, int size, int page, List<String> sort) {
        return null;
    }
}
