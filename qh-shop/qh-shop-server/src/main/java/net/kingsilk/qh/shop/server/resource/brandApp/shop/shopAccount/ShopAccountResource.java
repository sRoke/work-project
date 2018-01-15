package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopAccount;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.ShopAccountApi;
import net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto.ShopAccountInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto.ShopAccountPageResp;
import net.kingsilk.qh.shop.core.AccountChangeTypeEnum;
import net.kingsilk.qh.shop.core.MoneyChangeEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;

import java.util.Optional;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 * Created by lit on 17/11/29.
 */
public class ShopAccountResource implements ShopAccountApi {

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Autowired
    private OrderLogRepo orderLogRepo;

    @Autowired
    private ShopAccountLogRepo shopAccountLogRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private WithdrawRepo withdrawRepo;

    @Autowired
    private QhPayRepo qhPayRepo;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ShopAccountInfoResp> info(
            String brandAppId,
            String shopId
    ) {
        ShopAccount account = shopAccountRepo.findOne(
                allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId)
                )
        );

        Assert.notNull(account, "账户信息错误！");

        ShopAccountInfoResp infoResp = new ShopAccountInfoResp();
        infoResp.setBalance(account.getBalance());
        infoResp.setTotalBalance(account.getTotalBalance());
        infoResp.setTotalWithdraw(account.getTotalWithdraw());

        UniResp resp = new UniResp();
        resp.setData(infoResp);
        resp.setStatus(200);
        return resp;

    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ShopAccountPageResp>> page(
            String brandAppId,
            String shopId,
            UniPageReq req
    ) {

        PageRequest pageRequest = new PageRequest(req.getPage(), req.getSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        Page<ShopAccountLog> logPage = shopAccountLogRepo.findAll(
                allOf(
                        QShopAccountLog.shopAccountLog.brandAppId.eq(brandAppId),
                        QShopAccountLog.shopAccountLog.shopId.eq(shopId)
                ), pageRequest
        );

        UniResp<UniPageResp<ShopAccountPageResp>> resp = new UniResp<>();

        resp.setData(conversionService.convert(logPage, UniPageResp.class));
        logPage.getContent().forEach(
                log -> {
                    ShopAccountPageResp accountPageResp = new ShopAccountPageResp();
                    accountPageResp.setAmount(log.getChangeAmount());
                    accountPageResp.setCreateDate(log.getDateCreated());
                    accountPageResp.setType(log.getType());
                    if (log.getWithdrawId() != null) {
                        Withdraw withdraw = withdrawRepo.findOne(log.getWithdrawId());
                        accountPageResp.setPayType(withdraw.getPayType().getDesp());
                    }
                    if (log.getQhPayId() != null) {
                        QhPay qhPay = qhPayRepo.findOne(log.getQhPayId());
                        accountPageResp.setPayType(qhPay.getPayType().getDesp());
                    }
                    resp.getData().getContent().add(accountPageResp);
                }
        );

        return resp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<Boolean> transfer(String brandAppId, String shopId, Integer money, String orderId) {

        OrderLog log = orderLogRepo.findOne(
                Expressions.allOf(
                        QOrderLog.orderLog.orderId.eq(orderId),
                        QOrderLog.orderLog.deleted.ne(true)
                )
        );
        UniResp<Boolean> uniResp = new UniResp<>();
        uniResp.setStatus(HttpStatus.SC_OK);
        if (log != null) {
            uniResp.setData(true);
            return uniResp;
        }
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);

        ShopAccount account = shopAccountRepo.findOne(
                allOf(
                        QShopAccount.shopAccount.brandAppId.eq(brandAppId),
                        QShopAccount.shopAccount.shopId.eq(shopId)
                )
        );
        account.setBalance(Optional.ofNullable(account.getBalance()).orElse(0) + money);
        account.setTotalBalance(Optional.ofNullable(account.getTotalBalance()).orElse(0) + money);
        orderLogRepo.save(orderLog);
        shopAccountRepo.save(account);
        ShopAccountLog accountLog = conversionService.convert(account, ShopAccountLog.class);
        accountLog.setType(AccountChangeTypeEnum.BARGAINSELL);
        accountLog.setChangeAmount(money);
        accountLog.setMoneyChangeEnum(MoneyChangeEnum.BALANCE);
        shopAccountLogRepo.save(accountLog);
        uniResp.setData(true);
        return uniResp;
    }
}
