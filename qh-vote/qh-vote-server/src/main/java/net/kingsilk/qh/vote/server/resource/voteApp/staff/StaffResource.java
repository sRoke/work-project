package net.kingsilk.qh.vote.server.resource.voteApp.staff;

import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.staff.StaffApi;
import net.kingsilk.qh.vote.api.voteApp.staff.dto.StaffAddReq;
import net.kingsilk.qh.vote.api.voteApp.staff.dto.StaffResp;

import java.util.List;

public class StaffResource implements StaffApi{
    @Override
    public UniResp<String> add(String voteAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<String> update(String voteAppId, StaffAddReq staffAddReq) {
        return null;
    }

    @Override
    public UniResp<Void> delete(String voteAppId, String id) {
        return null;
    }

    @Override
    public UniResp<StaffResp> info(String voteAppId, String id) {
        return null;
    }

    @Override
    public UniResp<UniPage<StaffResp>> page(String voteAppId, int size, int page, List<String> sort) {
        return null;
    }
}
