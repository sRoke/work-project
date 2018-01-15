package net.kingsilk.qh.shop.server.resource.brandApp.sysConf;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.sysConf.SysConfApi;
import net.kingsilk.qh.shop.api.brandApp.sysConf.dto.ShopPriceReq;
import net.kingsilk.qh.shop.core.SysConfTypeEnum;
import net.kingsilk.qh.shop.domain.QSysConf;
import net.kingsilk.qh.shop.domain.SysConf;
import net.kingsilk.qh.shop.repo.SysConfRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Component
public class SysConfResource implements SysConfApi {


    @Autowired
    private SysConfRepo sysConfRepo;

    @Override
    public UniResp<String> shopPrice(
            String brandAppId,
            ShopPriceReq shopPrice
    ) {

        SysConf sysConf = sysConfRepo.findOne(
                allOf(
                        QSysConf.sysConf.brandAppId.eq(brandAppId),
                        QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                )
        );
        if (sysConf == null) {
            sysConf = new SysConf();
            sysConf.setDateCreated(new Date());
        }
        sysConf.setBrandAppId(brandAppId);
        sysConf.setLastModifiedDate(new Date());
        sysConf.setType(SysConfTypeEnum.shopPrice);

        Map<String, Integer> monthly = new HashMap<>();
        monthly.put("mDay", shopPrice.getmDay());
        monthly.put("mOldPrice", shopPrice.getmOldPrice());
        monthly.put("mSallPrice", shopPrice.getmSallPrice());

//        Map<String, Integer> quarter = new HashMap<>();
//        quarter.put("tDay", shopPrice.gettDay());
//        quarter.put("tOldPrice", shopPrice.gettOldPrice());
//        quarter.put("tSallPrice", shopPrice.gettSallPrice());
//
//        Map<String, Integer> year = new HashMap<>();
//        year.put("yDay", shopPrice.getyDay());
//        year.put("yOldPrice", shopPrice.getyOldPrice());
//        year.put("ySallPrice", shopPrice.getySallPrice());

        Map<String, Integer> free = new HashMap<>();
        free.put("free", shopPrice.getFree());

        Map<String, Map<String, Integer>> price = new HashMap<>();
        price.put("monthly", monthly);
//        price.put("quarter", quarter);
//        price.put("year", year);
        price.put("free", free);

        sysConf.setMapConf(price);
        sysConfRepo.save(sysConf);

        UniResp resp = new UniResp();
        resp.setData("设置成功");
        resp.setStatus(200);
        return resp;
    }

    @Override
    public UniResp<Map<String, Map<String, Integer>>> shopPrice(
            String brandAppId
    ) {

        SysConf sysConf = sysConfRepo.findOne(
                allOf(
                        QSysConf.sysConf.brandAppId.eq(brandAppId),
                        QSysConf.sysConf.type.eq(SysConfTypeEnum.shopPrice)
                )
        );
        if (sysConf == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "请联系管理员配置后台价格");
        }
        UniResp resp = new UniResp();
        resp.setData(sysConf.getMapConf());
        resp.setStatus(200);
        return resp;
    }
}
