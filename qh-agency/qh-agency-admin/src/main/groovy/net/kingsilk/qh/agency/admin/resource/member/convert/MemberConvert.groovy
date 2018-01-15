package net.kingsilk.qh.agency.admin.resource.member.convert

import net.kingsilk.qh.agency.admin.api.member.dto.MemberInfoResp
import net.kingsilk.qh.agency.domain.PartnerStaff
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/25.
 */
@Component
class MemberConvert {

    MemberInfoResp convertMemberToResp(PartnerStaff member) {
        MemberInfoResp memberInfoResp=new MemberInfoResp()
        memberInfoResp.setMemo(member.getMemo());
        memberInfoResp.setUserId(member.getUserId());
        memberInfoResp.setId(member.getId());
//        memberInfoResp.setRealName(member.getRealName());
//        memberInfoResp.setPhone(member.getPhone());
//        memberInfoResp.setTags(partnerStaff.getTags()[0].code);
//        memberInfoResp.setAvatar(member.getAvatar());
        memberInfoResp.partnerType=member?.partner?.partnerTypeEnum?.desp
//        memberInfoResp.setContacts(partnerStaff.getContacts())
//        memberInfoResp.setIdNumber(member.getIdNumber());
        if (member.isDisabled()){
            memberInfoResp.setDisabled("true");
        }else {
            memberInfoResp.setDisabled("false");
        }
//        memberInfoResp.setShopAddr(member.getAddress());
        return memberInfoResp;
    }
}
