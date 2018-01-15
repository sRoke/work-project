package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.member;

import com.google.common.collect.Lists;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.MemberApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberMinPageResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberMinReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberModel;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto.MemberPageReq;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.MemberAccountRepo;
import net.kingsilk.qh.shop.repo.MemberRepo;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.member.convert.MemberConvert;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.MemberService;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.service.UserService;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import org.bson.types.ObjectId;
import org.elasticsearch.common.inject.internal.ErrorsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class MemberResource implements MemberApi {

    @Autowired
    private MemberConvert memberConvert;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private MemberAccountRepo memberAccountRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserService userService;

    @Context
    private HttpServletRequest request;

    @Autowired
    private AddrService addrService;

    @Autowired
    private MemberService memberService;

    @Override
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            MemberMinReq minReq) {

        if (minReq == null){
            throw new ErrStatusException(ErrStatus.PARAMNUll,"空参数");
        }
        if (minReq.getPhone()==null || !memberService.isPhone(minReq.getPhone())){
            throw new ErrStatusException(ErrStatus.PARAMNUll,"请正确输入手机号");
        }
        //在oauth创建会员
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setRealName(minReq.getRealName());
        addUserReq.setPhone(minReq.getPhone());
        String userId = ObjectId.get().toString();
        addUserReq.setUserId(userId);
        userApi.addUser(addUserReq);
        //创建会员
        Member member = new Member();
        member.setBrandAppId(brandAppId);
        member.setShopId(shopId);
        member.setUserId(userId);
        System.out.println("new member的userId:"+userId);
        member.setEnable(true);
        member.setPhone(minReq.getPhone());
        member.setContacts(minReq.getContacts());
        member.setOrder(4000);
        //会员账户
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setBrandAppId(brandAppId);
        memberAccount.setShopId(shopId);
        memberRepo.save(member);
        memberAccount.setMemberId(member.getId());
        memberAccountRepo.save(memberAccount);
        member.setAccountId(memberAccount.getId());
        memberRepo.save(member);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setMessage("success");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<Map<String, String>> add(
            String brandAppId,
            String shopId,
            String wxComAppId,
            String wxMpAppId) {
        UniResp<Map<String, String>> uniResp = new UniResp<>();
        HashMap<String, String> map = new HashMap<>();
        //商家手机号
        Shop shop = shopRepo.findOne(allOf(
                QShop.shop.brandAppId.eq(brandAppId),
                QShop.shop.id.eq(shopId),
                QShop.shop.deleted.ne(true)
        ));
        if (shop != null) {
            map.put("shopPhone", shop.getPhone());
            map.put("shopName", shop.getName());
        }


        //从wx4j 中获取member的头像和名字
        Map oauthUser = userService.getOauthUserInfo(request);
        ArrayList<LinkedHashMap<String, String>> wxUserList = (ArrayList<LinkedHashMap<String, String>>) oauthUser.get("wxUsers");
        StringBuilder openId = new StringBuilder();
        Optional.ofNullable(wxUserList).ifPresent(wxUsers ->

                wxUsers.stream().filter(wxUser ->
                        wxMpAppId.equals(wxUser.get("appId"))
                ).findFirst().ifPresent(it ->
                        openId.append(it.get("openId"))
                )

        );
        Map wxMpUser = userService.getWxMpUser(wxComAppId, wxMpAppId, openId.toString());
        map.put("memberImg", (String) wxMpUser.get("headImgUrl"));
        map.put("memberName", (String) wxMpUser.get("nickName"));

        //创建会员
        String userId = secService.curUserId();
        if (!StringUtils.hasText(userId)) {
            uniResp.setMessage("用户未登录");
            uniResp.setStatus(ErrStatus.FINDNULL);
            return uniResp;
        }
        Member cheMember = memberRepo.findOne(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.userId.eq(userId)
//                        QMember.member.deleted.ne(true)
                )
        );

        if (cheMember != null) {
            uniResp.setStatus(ErrStatus.MEMBERERROR);
            uniResp.setMessage("已经注册过会员");
            uniResp.setData(map);
            return uniResp;
        } else {
            //创建会员
            Member member = new Member();
            member.setUserId(userId);
            member.setBrandAppId(brandAppId);
            member.setShopId(shopId);
            member.setEnable(true);     //默认启用
            net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp = userApi.get(userId);
            if (resp.getData() != null) {
                member.setPhone(resp.getData().getPhone());
            }
            //获取首字，并判断
            String nickName = (String) wxMpUser.get("nickName");
            memberService.setOrder(member,nickName);
            memberRepo.save(member);

            MemberAccount memberAccount = new MemberAccount();
            memberAccount.setBrandAppId(brandAppId);
            memberAccount.setShopId(shopId);
            memberAccount.setMemberId(member.getId());
            memberAccountRepo.save(memberAccount);

            member.setAccountId(memberAccount.getId());
            memberRepo.save(member);

            uniResp.setData(map);
            uniResp.setMessage("会员注册成功");
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }
    }

    @Override
    public UniResp<String> disable(     //todo
                                        String brandAppId,
                                        String shopId,
                                        String id) {

        UniResp<String> uniResp = new UniResp<>();
        if (brandAppId == null && StringUtils.isEmpty(brandAppId)) {
            uniResp.setData("brandAppId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (shopId == null && StringUtils.isEmpty(shopId)) {
            uniResp.setData("shopId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        if (id == null && StringUtils.isEmpty(id)) {
            uniResp.setData("memberId为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        Member member = memberRepo.findOne(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.id.eq(id),
                        QMember.member.deleted.ne(true)
                ));
        if (member != null) {
            member.setEnable(false);
            memberRepo.save(member);
            uniResp.setData("successs");
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }
        uniResp.setData("找不到该memberId会员");
        uniResp.setStatus(ErrStatus.MEMBERERROR);
        return uniResp;
    }

    @Override
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            MemberModel memberReq) {

        UniResp<String> uniResp = new UniResp<>();
        if (memberReq == null) {
            uniResp.setData("memberReq为空");
            uniResp.setStatus(ErrStatus.PARAMNUll);
            return uniResp;
        }
        Member member = memberRepo.findOne(allOf(
                QMember.member.brandAppId.eq(brandAppId),
                QMember.member.shopId.eq(shopId),
                QMember.member.id.eq(id)
        ));
        if (member==null){
            throw new ErrStatusException(ErrStatus.FINDNULL,"会员错误");
        }
        if (StringUtils.hasText(memberReq.getRealName())) {
            AddUserReq addUserReq = new AddUserReq();
            addUserReq.setUserId(member.getUserId());
            addUserReq.setPhone(member.getPhone());   //手机号不允许更新
            addUserReq.setRealName(memberReq.getRealName());
            userApi.update(addUserReq);
        }
        member.setPhone(memberReq.getPhone());
        member.setContacts(memberReq.getContacts());
        Member isSave = memberRepo.save(member);

        uniResp.setData("successs");
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<MemberModel> info(
            String brandAppId,
            String shopId,
            String id,
            String wxComAppId,
            String wxMpAppId) {

        UniResp<MemberModel> uniResp = new UniResp<>();

        if (brandAppId == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("brandAppId为空");
            return uniResp;
        }

        if (shopId == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("shopId为空");
            return uniResp;
        }

        if (id == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("memberId为空");
            return uniResp;
        }
        Member member = memberRepo.findOne(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.id.eq(id),
                        QMember.member.deleted.ne(true)
                )
        );
        if (member == null) {
            uniResp.setStatus(10001);
            uniResp.setException("没有该会员");
            return uniResp;
        }
        //从oauth中取用户信息和地址信息
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp = userApi.get(member.getUserId());
        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> userAddrResp = addrService.getDefault(member.getUserId(), "USER_SHIPPING_ADDR");
        MemberModel memberModel = memberConvert.memberInfoiConvert(resp.getData(), userAddrResp.getData(),wxComAppId,wxMpAppId);
        memberModel.setContacts(member.getContacts());
//        net.kingsilk.qh.oauth.api.UniResp<AddrGetResp> userAddrResp = addrApi.getDefault(id, "USER_SHIPPING_ADDR");
//        AddrGetResp addr = userAddrResp.getData();
//        if (addr == null){
//            uniResp.setStatus(10001);
//            uniResp.setException("在oauth中没找到该会员地址");
//            return uniResp;
//        }
//        memberConvert.respAddrConvert(addr,memberModel);

        uniResp.setData(memberModel);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<UniPageResp<MemberMinPageResp>> page(
            String brandAppId,
            String shopId,
            MemberPageReq pageReq) {

        UniResp<UniPageResp<MemberMinPageResp>> uniResp = new UniResp<>();

        if (brandAppId == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("brandAppId为空");
            return uniResp;
        }
        if (shopId == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("shopId为空");
            return uniResp;
        }
        if (pageReq == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setException("请求的参数为空");
            return uniResp;
        }
        //分页条件构造
        List sort = pageReq.getSort();
        if (sort == null && sort.size() == 0) {
            sort = Lists.newArrayList(ParamUtils.toSort("order,asc"));
//            sort = Lists.newArrayList(ParamUtils.toSort("dateCreated,desc"));
        }
        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(pageReq.getPage(), pageReq.getSize(), s);
        String keyword = pageReq.getKeyword();
//        //判断是否是手机号
//        boolean isPhone = memberService.isPhone(pageReq.getKeyword());
        UniPageResp<MemberMinPageResp> memberPageResp = new UniPageResp<>();
        Page<Member> memberPage = memberRepo.findAll(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.deleted.ne(true),
                        StringUtils.hasText(keyword) ? QMember.member.phone.like("%" + keyword + "%") : null,
                        QMember.member.enable.eq(pageReq.getEnable())
                ), pageable);

        ArrayList<String> userIds = new ArrayList<>();
        if (memberPage != null && memberPage.getContent() != null) {
            memberPage.getContent().forEach(member -> userIds.add(member.getUserId()));
        }
        if (userIds==null || userIds.size()==0){
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("没有符合的数据");
            UniPageResp<MemberMinPageResp> nullcontant = new UniPageResp<>();
            uniResp.setData(nullcontant);
            return uniResp;
        }
        net.kingsilk.qh.oauth.api.UniResp<UniPage<UserGetResp>> list = userApi.list(pageReq.getSize(), pageReq.getPage(), sort, userIds);
        UniPage<UserGetResp> data = list.getData();
        if (data != null && data.getContent() != null) {
            for (UserGetResp oauthUser : data.getContent()) {
                MemberMinPageResp minResp = memberConvert.minPageRespConvert(oauthUser);
                //如果oauth没有头像等信息，从wx4j中取
                if (oauthUser.getAvatar() == null || minResp.getNickName() ==null){
                    List<UserGetResp.WxUser> wxUsers = oauthUser.getWxUsers().stream().filter(wxUser -> pageReq.getWxMpAppId().equalsIgnoreCase(wxUser.getAppId())).collect(Collectors.toList());
                    if (wxUsers!=null||wxUsers.get(0)!=null) {
                        String openId = wxUsers.get(0).getOpenId();
                        Map wxMpUser = userService.getWxMpUser(pageReq.getWxComAppId(), pageReq.getWxMpAppId(), openId);
                        minResp.setAvatar((String) wxMpUser.get("headImgUrl"));
                        minResp.setNickName((String) wxMpUser.get("nickName"));
                    }
                }
                minResp.setBrandAppId(brandAppId);
                minResp.setShopId(shopId);
                //暂时这样写，调试用　(首拼排序)
                Member member = memberRepo.findOne(allOf(
                        QMember.member.userId.eq(minResp.getUserId()),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.brandAppId.eq(brandAppId)
                    ));
                if (member.getOrder() == null){
                    memberService.setOrder(member,minResp.getNickName());
                    memberRepo.save(member);
                }
                //传回对象ｉd 用于ｉnfo 接口
                minResp.setId(member.getId());
                minResp.setOrder(member.getOrder());
                minResp.setContacts(member.getContacts());
                memberPageResp.getContent().add(minResp);
            }
        }
        if (memberPageResp != null && memberPageResp.getContent() != null) {
            UniPageResp<MemberModel> modelUniPageResp = new UniPageResp<>();
            List<MemberMinPageResp> members = memberPageResp.getContent();
            if (members != null) {
                for (MemberMinPageResp m : members) {
                    //兼容更新老数据（没有手机号）
                    if (m.getPhone() == null) {
                        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp = userApi.get(m.getUserId());
                        Member member = memberRepo.findOne(QMember.member.userId.eq(m.getUserId()));
                        member.setPhone(resp.getData().getPhone());
                        memberRepo.save(member);
                    }
                }
            }
            uniResp.setData(memberPageResp);
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }

        uniResp.setStatus(10001);               //todo
        uniResp.setMessage("找不到会员数据");
        return uniResp;
    }

    @Override
    public String getMemberIdByUserId(
            String brandAppId,
            String shopId,
            String userId) {

        if (userId == null) {
            return null;
        }
        Member member = memberRepo.findOne(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.userId.eq(userId),
                        QMember.member.deleted.ne(true)
                )
        );
        if (member == null) {
            return null;
        }
        return member.getId();
//        return "88591911110";
    }

    @Override
    public UniResp<String> isMember(
            String brandAppId,
            String shopId) {

        UniResp<String> uniResp = new UniResp<>();
        String userId = secService.curUserId();
        if (StringUtils.isEmpty(userId)) {
            uniResp.setData("false");
            uniResp.setMessage("用户未登录");
            uniResp.setStatus(ErrStatus.FINDNULL);
        }
        Member member = memberRepo.findOne(
                allOf(
                        QMember.member.brandAppId.eq(brandAppId),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.userId.eq(userId),
                        QMember.member.deleted.ne(true)
                )
        );

        if (member == null) {
            uniResp.setData("false");
            uniResp.setMessage("用户不是门店有效的会员");
            uniResp.setStatus(ErrStatus.FINDNULL);
            return uniResp;
        } else {
            uniResp.setData("true");
            uniResp.setMessage("有效会员");
            uniResp.setStatus(ErrStatus.OK);
            return uniResp;
        }
    }
}
