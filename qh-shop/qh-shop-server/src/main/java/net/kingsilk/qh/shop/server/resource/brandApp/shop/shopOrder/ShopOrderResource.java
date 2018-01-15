package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopOrder;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto.NotifyQhPayReq;
import net.kingsilk.qh.shop.api.brandApp.shop.shopOrder.ShopOrderApi;
import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.ShopOrderStatusEnum;
import net.kingsilk.qh.shop.core.SysConfTypeEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.QhPayRepo;
import net.kingsilk.qh.shop.repo.ShopOrderRepo;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.repo.SysConfRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.qh.shop.service.service.CommonService;
import net.kingsilk.qh.shop.service.service.SecService;
import net.kingsilk.qh.shop.service.service.ShopOrderService;
import net.kingsilk.qh.shop.service.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component
public class ShopOrderResource implements ShopOrderApi {

    @Autowired
    private SecService secService;

    @Autowired
    private SysConfRepo sysConfRepo;


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
    private QhShopProperties prop;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private CommonService commonService;

    @Override
    public UniResp<String> buy(
            String brandAppId,
            String shopId,
            Integer duration
    ) {
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
//        Map oauthUser = userService.getOauthUserInfo(request);

        //获得openId
//        StringBuilder openId = new StringBuilder();
//        ArrayList<LinkedHashMap<String, String>> wxUserList = (ArrayList<LinkedHashMap<String, String>>) oauthUser.get("wxUsers");
//        Optional.ofNullable(wxUserList).ifPresent(wxUsers ->
//
//                wxUsers.stream().filter(wxUser ->
//                        appId.equals(wxUser.get("appId"))
//                ).findFirst().ifPresent(it ->
//                        openId.append(it.get("openId"))
//                )
//
//        );
        String openId=commonService.getOpenId(request,appId);


        StringBuffer sb = new StringBuffer();

        sb.append("门店系统").append(shopOrder.getSeq());
        Map<String, String> map = new LinkedHashMap();
        map.put("totalFee", String.valueOf(shopOrder.getPaymentAmount()));
        map.put("outTradeNo", shopOrder.getId());
        map.put("openId", openId);
        map.put("bizMemo", shopOrder.getBuyerMemo());
        map.put("itemTitle", sb.toString());
        map.put("itemDetail", shopOrder.getSeq());
        map.put("spBillCreateIp", ip);
        map.put("notifyUrl", prop.getQhShop().getUrl() + "brandApp/" + shopOrder.getBrandAppId() + "/shopOrder/notify");

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
    public boolean notifyAgain(String brandAppId, String shopId, NotifyQhPayReq req) {
        ShopOrder shopOrder = shopOrderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.id.eq(req.getBizOrderNo()),
                        QOrder.order.deleted.ne(true)
                )
        );
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        if (!StringUtils.isBlank(req.getPayTime())) {
            try {
                date = format.parse(req.getPayTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (shopOrder == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "找不到订单");
        }
        if ("PAY".equals(req.getType())) {
            if (!ShopOrderStatusEnum.UNPAYED.equals(shopOrder.getStatus())) {
                return false;
            }
            QhPay qhPay = new QhPay();
            qhPay.setOrderId(shopOrder.getId());
            qhPay.setThirdPayAmount(shopOrder.getPaymentAmount());
            qhPay.setBrandAppId(shopOrder.getBrandAppId());
            qhPay.setRefundAmount(shopOrder.getPaymentAmount());
            qhPay.setPayType(PayTypeEnum.valueOf(req.getPayType()));
            qhPay.setPayTime(date);
            qhPayRepo.save(qhPay);

            shopOrder.setQhPayId(qhPay.getId());
            shopOrder.setStatus(ShopOrderStatusEnum.FINISHED);
            shopOrderRepo.save(shopOrder);

            SysConf sysConf = sysConfRepo.findOne(
                    allOf(
                            QSysConf.sysConf.brandAppId.eq(brandAppId),
                            QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                    )
            );
            Map<String, Integer> monthly = (Map<String, Integer>) sysConf.getMapConf().get("monthly");
            Integer duration = monthly.get("mDay");
            Shop shop = shopRepo.findOne(
                    Expressions.allOf(
                            QShop.shop.id.eq(shopId),
                            QShop.shop.brandAppId.eq(brandAppId),
                            QShop.shop.deleted.ne(true)
                    )
            );

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
            calendar.setTime(shop.getExpireDate());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.add(Calendar.DATE, duration);
            shop.setExpireDate(calendar.getTime());
            shopRepo.save(shop);
        }
        return true;
    }

    @Override
    public UniResp<String> create(String brandAppId) {

        UniResp<String> uniResp = new UniResp<>();

        SysConf sysConf = sysConfRepo.findOne(
                allOf(
                        QSysConf.sysConf.brandAppId.eq(brandAppId),
                        QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                )
        );
        Map<String, Integer> monthly = (Map<String, Integer>) sysConf.getMapConf().get("free");
        Integer freeDate = monthly.get("free");

        String shopId = shopOrderService.createShop(brandAppId, freeDate, null);

        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(shopId);
        return uniResp;
    }

    @Override
    public boolean notify(String brandAppId, String shopId, NotifyQhPayReq req) {

        ShopOrder shopOrder = shopOrderRepo.findOne(
                Expressions.allOf(
                        QOrder.order.brandAppId.eq(brandAppId),
                        QOrder.order.id.eq(req.getBizOrderNo()),
                        QOrder.order.deleted.ne(true)
                )
        );
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        if (!StringUtils.isBlank(req.getPayTime())) {
            try {
                date = format.parse(req.getPayTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (shopOrder == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "找不到订单");
        }
        if ("PAY".equals(req.getType())) {
            if (!ShopOrderStatusEnum.UNPAYED.equals(shopOrder.getStatus())) {
                return false;
            }
            QhPay qhPay = new QhPay();
            qhPay.setOrderId(shopOrder.getId());
            qhPay.setThirdPayAmount(shopOrder.getPaymentAmount());
            qhPay.setBrandAppId(shopOrder.getBrandAppId());
            qhPay.setRefundAmount(shopOrder.getPaymentAmount());
            qhPay.setPayType(PayTypeEnum.valueOf(req.getPayType()));
            qhPay.setPayTime(date);
            qhPayRepo.save(qhPay);

            shopOrder.setQhPayId(qhPay.getId());

            shopOrder.setStatus(ShopOrderStatusEnum.FINISHED);


            SysConf sysConf = sysConfRepo.findOne(
                    allOf(
                            QSysConf.sysConf.brandAppId.eq(brandAppId),
                            QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                    )
            );
            Map<String, Integer> monthly = (Map<String, Integer>) sysConf.getMapConf().get("monthly");
            Integer duration = monthly.get("mDay");
//            Shop shop = shopRepo.findOne(
//                    Expressions.allOf(
//                            QShop.shop.id.eq(shopId),
//                            QShop.shop.brandAppId.eq(brandAppId),
//                            QShop.shop.deleted.ne(true)
//                    )
//            );
//            if (shop == null) {
            String shopIdNew = shopOrderService.createShop(brandAppId, duration, shopOrder.getUserId());
            shopOrder.setShopId(shopIdNew);
            shopOrderRepo.save(shopOrder);
//            } else {
//                Date expireDate = shop.getExpireDate();
//                long l = expireDate.getTime() + duration * 24 * 3600 * 1000;
//                Calendar c = Calendar.getInstance();
//                c.setTimeInMillis(l);
//                Date dateNew = c.getTime();
//                shop.setExpireDate(dateNew);
//                shopRepo.save(shop);
//            }

        }
        return true;
    }

}
