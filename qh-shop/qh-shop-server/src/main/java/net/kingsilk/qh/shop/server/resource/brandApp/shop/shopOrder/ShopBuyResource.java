package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopOrder;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopOrder.ShopBuyApi;
import net.kingsilk.qh.shop.api.brandApp.shop.shopOrder.dto.ShopOrderResp;
import net.kingsilk.qh.shop.core.ShopOrderStatusEnum;
import net.kingsilk.qh.shop.core.SysConfTypeEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.QhPayRepo;
import net.kingsilk.qh.shop.repo.ShopOrderRepo;
import net.kingsilk.qh.shop.repo.SysConfRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.CommonService;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.service.UserService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class ShopBuyResource implements ShopBuyApi {

    @Autowired
    private SecService secService;

    @Autowired
    private SysConfRepo sysConfRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private ShopOrderRepo shopOrderRepo;

    @Autowired
    @Qualifier("oauthRestTemplate")
    private RestTemplate restTemplate;

    @Context
    HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private QhShopProperties prop;

    @Override
    public UniResp<String> buyAgain(String brandAppId, String shopId, Integer duration) {
        Assert.notNull(brandAppId, "brandAppId不能为空");
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setBrandAppId(brandAppId);
        shopOrder.setShopId(shopId);
        shopOrder.setSeq(commonService.getDateString());
        shopOrder.setStatus(ShopOrderStatusEnum.UNPAYED);


        String userId = secService.curUserId();
        Assert.notNull(userId, "当前用户未登陆");
        shopOrder.setUserId(userId);


        SysConf sysConf = sysConfRepo.findOne(
                allOf(
                        QSysConf.sysConf.brandAppId.eq(brandAppId),
                        QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                )
        );
        Map<String, Integer> monthly = (Map<String, Integer>) sysConf.getMapConf().get("monthly");
        shopOrder.setPaymentAmount(monthly.get("mSallPrice") * duration);

        shopOrderRepo.save(shopOrder);


        String ip = request.getRemoteAddr();
        String appId = userService.getAppId(shopOrder.getBrandAppId());
        if (shopOrder.getStatus() != ShopOrderStatusEnum.UNPAYED && shopOrder.getStatus() != ShopOrderStatusEnum.UNCOMMITED) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "订单状态错误");
        }
        Map oauthUser = userService.getOauthUserInfo(request);

        //获得openId
        StringBuilder openId = new StringBuilder();
        ArrayList<LinkedHashMap<String, String>> wxUserList = (ArrayList<LinkedHashMap<String, String>>) oauthUser.get("wxUsers");
        Optional.ofNullable(wxUserList).ifPresent(wxUsers ->

                wxUsers.stream().filter(wxUser ->
                        appId.equals(wxUser.get("appId"))
                ).findFirst().ifPresent(it ->
                        openId.append(it.get("openId"))
                )

        );

        if (!StringUtils.hasText(openId.toString())) {
            throw new ErrStatusException(ErrStatus.PARTNER_401, "openId为空");
        }

        StringBuffer sb = new StringBuffer();

        sb.append("门店系统").append(shopOrder.getSeq());
        Map<String, String> map = new LinkedHashMap();
        map.put("totalFee", String.valueOf(shopOrder.getPaymentAmount()));
        map.put("outTradeNo", shopOrder.getId());
        map.put("openId", openId.toString());
        map.put("bizMemo", shopOrder.getBuyerMemo());
        map.put("itemTitle", sb.toString());
        map.put("itemDetail", shopOrder.getSeq());
        map.put("spBillCreateIp", ip);
        map.put("notifyUrl", prop.getQhShop().getUrl() + "brandApp/" + shopOrder.getBrandAppId() + "/shopOrder/notify/" + shopId);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(type);

        HttpEntity<Map> reqEntity = new HttpEntity<>(map, reqHeaders);
        String url = prop.getQhPay().getWap().getApi().getUrl() + shopOrder.getBrandAppId() + "/trade";
        UniResp<Map> payMap = restTemplate.postForObject(url, reqEntity, UniResp.class);
        System.out.println("--------------------支付系统返回值\n${payMap}");
        Map<String, String> data = (Map) payMap.getData();
        String resp = data.get("tradeId");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<List<ShopOrderResp>> log(String brandAppId, String shopId) {
        List<ShopOrderStatusEnum> statusEnums = Lists.newArrayList(new ShopOrderStatusEnum[]{ShopOrderStatusEnum.UNPAYED, ShopOrderStatusEnum.FINISHED});
        List<ShopOrder> shopOrders = Lists.newLinkedList(shopOrderRepo.findAll(
                Expressions.allOf(
                        QShopOrder.shopOrder.brandAppId.eq(brandAppId),
                        QShopOrder.shopOrder.shopId.eq(shopId),
                        DbUtil.opIn(QShopOrder.shopOrder.status, statusEnums),
                        QShopOrder.shopOrder.deleted.ne(true)
                )
        ));
        List<ShopOrderResp> respList = shopOrders.stream()
                .filter(resp ->
                        StringUtils.hasText(resp.getQhPayId())
                ).map(it ->
                        {
                            ShopOrderResp shopOrderResp = new ShopOrderResp();
                            QhPay qhPay = qhPayRepo.findOne(it.getQhPayId());
                            Optional.ofNullable(qhPay).ifPresent(pay ->
                                    {
                                        shopOrderResp.setStatus(ShopOrderStatusEnum.FINISHED.equals(it.getStatus()) ? "已支付" : it.getStatus().getDesp());
                                        shopOrderResp.setBuyDate(qhPay.getPayTime());
                                        shopOrderResp.setTotalPrice(qhPay.getThirdPayAmount());
                                    }
                            );
                            return shopOrderResp;
                        }
                ).collect(Collectors.toList());
        UniResp<List<ShopOrderResp>> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(respList);
        return uniResp;
    }
}
