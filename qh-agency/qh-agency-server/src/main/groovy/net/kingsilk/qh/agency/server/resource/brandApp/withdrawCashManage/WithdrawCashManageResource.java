package net.kingsilk.qh.agency.server.resource.brandApp.withdrawCashManage;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.WithdrawCashManageApi;
import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto.WithdrawCashPageReq;
import net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto.WithdrawCashPageResp;
import net.kingsilk.qh.agency.core.WithdrawCashStatusEnum;
import net.kingsilk.qh.agency.domain.Partner;
import net.kingsilk.qh.agency.domain.PartnerAccount;
import net.kingsilk.qh.agency.domain.QWithdrawCash;
import net.kingsilk.qh.agency.domain.WithdrawCash;
import net.kingsilk.qh.agency.repo.PartnerAccountRepo;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.WithdrawCashRepo;
import net.kingsilk.qh.agency.server.resource.brandApp.withdrawCashManage.convert.WithdrawCashConvert;
import net.kingsilk.qh.agency.service.WithdrawCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class WithdrawCashManageResource implements WithdrawCashManageApi {

    @Autowired
    private WithdrawCashRepo withdrawCashRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private WithdrawCashConvert withdrawCashConvert;

    @Autowired
    private PartnerAccountRepo partnerAccountRepo;

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private WithdrawCashService withdrawCashService;


    @Override
    public UniResp<UniPageResp<WithdrawCashPageResp>> page(
            String brandAppId,
            WithdrawCashPageReq req
    ) {
        PageRequest pageRequest = new PageRequest(req.getPage(), req.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));


        Page<WithdrawCash> page = withdrawCashRepo.findAll(
                Expressions.allOf(
                        QWithdrawCash.withdrawCash.brandAppId.eq(brandAppId),
                        req.getPartnerStaffId() != null ? QWithdrawCash.withdrawCash.partnerStaffId.eq(req.getPartnerStaffId()) : null,
                        req.getStatus() != null ? QWithdrawCash.withdrawCash.status.eq(WithdrawCashStatusEnum.valueOf(req.getStatus())) : null
                ), pageRequest
        );

        UniResp<UniPageResp<WithdrawCashPageResp>> resp = new UniResp<>();
        resp.setData(conversionService.convert(page, UniPageResp.class));
        page.getContent().forEach(
                withdrawCash -> resp.getData().getContent().add(withdrawCashConvert.withdrawCashPageRespConvert(withdrawCash))
        );
        resp.setStatus(200);
        return resp;
    }

    /**
     * 提现
     * 1. 生成相应的Payment
     * 2. 异步调用qh-pay相应的接口，完成提现！
     * 3. 监听qh-pay回调，更改退款状态
     *
     * @return
     */
    @Override
    public UniResp<Map> agreeWithdraw(
            String brandAppId,
            String id
    ) {
        Assert.notNull(id, "");
        WithdrawCash withdrawCash = withdrawCashRepo.findOne(id);
        Assert.notNull(withdrawCash, "");
//        withdrawCash.setStatus(WithdrawCashStatusEnum.SUCCESS);
        UniResp<Map> resp;
        if (withdrawCash.getStatus() != WithdrawCashStatusEnum.SUCCESS
                && withdrawCash.getStatus() != WithdrawCashStatusEnum.REJECT) {
            resp = withdrawCashService.withdrawCash(withdrawCash,brandAppId);
        } else {
            resp = new UniResp<>();
            Map map = new HashMap();
            map.put("error", "订单已提现");
            resp.setStatus(10043);
            resp.setData(map);
        }


        return resp;
    }

    @Override
    public UniResp<String> rejectWithdraw(
            String brandAppId,
            String id
    ) {
        Assert.notNull(id, "提现单ID错误！");
        WithdrawCash withdrawCash = withdrawCashRepo.findOne(id);
        Assert.notNull(withdrawCash, "提现单错误");
        withdrawCash.setStatus(WithdrawCashStatusEnum.REJECT);
        withdrawCashRepo.save(withdrawCash);
        Partner partner = partnerRepo.findOne(withdrawCash.getPartnerId());
        PartnerAccount account = partnerAccountRepo.findByPartner(partner);
        account.setFreezeBalance(account.getFreezeBalance() - withdrawCash.getWithdrawAmount());
        account.setBalance(account.getBalance() + withdrawCash.getWithdrawAmount());
        partnerAccountRepo.save(account);
        UniResp resp = new UniResp();
        resp.setData("操作成功！");
        resp.setStatus(200);
        return resp;
    }
}
