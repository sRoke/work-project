package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.member.convert;

import net.kingsilk.qh.oauth.api.user.UserGetResp;

import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberMinPageResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberModel;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.domain.Member;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberConvert {
    @Autowired
    private UserService userService;

    @Autowired
    private AdcRepo adcRepo;
    public Member reqMinConvert(MemberModel memberReq){
        if (memberReq == null){return null;}
        Member member = new Member();
//        if(memberReq.getBrandAppId() != null){
//            member.setBrandAppId(memberReq.getBrandAppId());
//        }
//        if(memberReq.getShopId() != null){
//            member.setShopId(memberReq.getShopId());
//        }
//        if(memberReq.getUserId() != null){
//            member.setUserId(memberReq.getUserId());
//        }
        if(memberReq.getEnable() != null){
            member.setEnable(memberReq.getEnable());
        }
//        if(memberReq.getLevelId() != null){
//            member.setLevelId(memberReq.getLevelId());
//        }
        if(memberReq.getMemo() != null){
            member.setMemo(memberReq.getMemo());
        }
//        if(memberReq.getAccountId() != null){
//            member.setAccountId(memberReq.getAccountId());
//        }
        if(memberReq.getPhone() != null){
            member.setPhone(memberReq.getPhone());
        }
        return member;
    }




    public MemberModel respMinConvert(Member member){
        if (member == null){return null;}

        MemberModel memberModel = new MemberModel();
        if (member.getId() != null){
            memberModel.setId(member.getId());
        }
        if(member.getBrandAppId() != null){
            memberModel.setBrandAppId(member.getBrandAppId());
        }
        if(member.getShopId() != null){
            memberModel.setShopId(member.getShopId());
        }
        if(member.getUserId() != null){
            memberModel.setUserId(member.getUserId());
        }
        if(member.getEnable() != null){
            memberModel.setEnable(member.getEnable());
        }
        if(member.getLevelId() != null){
            memberModel.setLevelId(member.getLevelId());
        }
        if(member.getMemo() != null){
            memberModel.setMemo(member.getMemo());
        }
        if(member.getAccountId() != null){
            memberModel.setAccountId(member.getAccountId());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (member.getDateCreated() != null){
            memberModel.setDateCreated(format.format(member.getDateCreated()));
        }
        if(member.getPhone() != null){
            memberModel.setPhone(member.getPhone());
        }
        return memberModel;
    }

    public MemberModel respUserConvert(UserGetResp user,MemberModel memberModel){                                               //todo   shifou 还要
        if (user == null){ return null;}

//        if (user.getRealName() != null){
//            memberModel.setRealName(user.getRealName());
//        }
//        if (user.getPhone() != null){
//            memberModel.setPhone(user.getPhone());
//        }
//
        return memberModel;
    }

    public MemberModel respAddrConvert(AddrGetResp addr, MemberModel memberModel){
        if (addr == null){ return null;}

        if (addr.getAdc() != null){
            memberModel.setAdc(addr.getAdc());
        }
        if (addr.getStreet() != null){
            memberModel.setSrteet(addr.getStreet());
        }
        return memberModel;
    }

    public MemberMinPageResp minPageRespConvert(UserGetResp oauthUser){
        if (oauthUser == null){return null;}
        MemberMinPageResp target = new MemberMinPageResp();
        target.setAvatar(oauthUser.getAvatar());
        target.setNickName(oauthUser.getRealName());
        target.setPhone(oauthUser.getPhone());
        target.setUserId(oauthUser.getId());
        return target;
    }

    public MemberModel memberInfoiConvert(UserGetResp oauthUser,AddrGetResp oauthAddr,String wxComAppId,String wxMpAppId){
        MemberModel target = new MemberModel();
        if(oauthUser != null){
            target.setAvatar(oauthUser.getAvatar());
            target.setPhone(oauthUser.getPhone());
            target.setRealName(oauthUser.getRealName());
            target.setNickName(oauthUser.getUsername());
            //如果oauth没有信息，就从wx4j中取
            if (target.getNickName() == null || target.getAvatar() == null){
                //非空
                if (StringUtils.hasText(wxComAppId)&&StringUtils.hasText(wxMpAppId)) {
                    List<UserGetResp.WxUser> wxUsers = oauthUser.getWxUsers().stream().filter(wxUser -> wxMpAppId.equalsIgnoreCase(wxUser.getAppId())).collect(Collectors.toList());
                    String openId = wxUsers.get(0).getOpenId();
                    Map wxMpUser = userService.getWxMpUser(wxComAppId, wxMpAppId, openId);
                    target.setAvatar((String) wxMpUser.get("headImgUrl"));
                    target.setNickName((String) wxMpUser.get("nickName"));
                    target.setProvince((String) wxMpUser.get("province"));
                    target.setCity((String) wxMpUser.get("city"));
                }
            }
        }
        //地址在oauth中没有就用wx4j的，不覆盖
        if (oauthAddr != null && oauthAddr.getAdc() != null){
            target.setAdc(oauthAddr.getAdc());
            Adc area = adcRepo.findOneByNo(oauthAddr.getAdc());
            if (area != null){
                Adc city = adcRepo.findOneByNo(area.getParent().getNo());
                if (city != null) {
                    target.setCity(city.getName());
                    Adc privince = adcRepo.findOneByNo(city.getParent().getNo());
                    if (privince != null){
                        target.setProvince(privince.getName());
                    }
                }
            }
        }
        return target;
    }

}
