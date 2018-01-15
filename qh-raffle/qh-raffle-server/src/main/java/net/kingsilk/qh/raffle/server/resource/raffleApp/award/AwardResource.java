package net.kingsilk.qh.raffle.server.resource.raffleApp.award;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.AwardApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.api.common.UniOrder;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.domain.QRaffleAward;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.server.resource.raffleApp.award.convert.AwardReqConvert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class AwardResource implements AwardApi{

    @Autowired
    private RaffleAwardRepo awardRepo;

    @Autowired
    private AwardReqConvert awardReqConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Override
    public UniResp<String> save(String raffleAppId, String raffleId, AwardInfo awardReq) {
        UniResp<String> uniResp = new UniResp<>();
        if (raffleId == null){
            uniResp.setMessage("raffleId错误");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        RaffleAward award = new RaffleAward();
        BeanUtils.copyProperties(awardReq,award);
        award.setRaffleId(raffleId);
        award.setraffleAppId(raffleAppId);
//        award.setSeqNum(commonService.getDateString());
        awardRepo.save(award);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("success");
        return uniResp;
    }

    @Override
    public UniResp<String> update(String raffleAppId, String raffleId, String awardId, AwardInfo awardReq) {
        UniResp<String> uniResp = new UniResp<>();
        if (awardId == null){
            uniResp.setMessage("awardId错误");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        RaffleAward award = awardRepo.findOne(allOf(
                QRaffleAward.raffleAward.id.eq(awardId),
                QRaffleAward.raffleAward.deleted.ne(true)
        ));
        if (award == null){
            uniResp.setMessage("奖品错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        awardReqConvert.convert(awardReq,award);
        awardRepo.save(award);

        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("success");
        return uniResp;
    }

    @Override
    public UniResp<AwardInfo> info(String raffleAppId, String raffleId, String awardId) {
        UniResp<AwardInfo> uniResp = new UniResp<>();
        if (awardId == null){
            uniResp.setMessage("awardId错误");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        RaffleAward award = awardRepo.findOne(allOf(
                QRaffleAward.raffleAward.id.eq(awardId),
                QRaffleAward.raffleAward.deleted.ne(true)
        ));
        if (award == null){
            uniResp.setMessage("奖品错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        AwardInfo awardInfo = conversionService.convert(award, AwardInfo.class);
        uniResp.setData(awardInfo);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String raffleAppId, String raffleId, String awardId) {

        UniResp<String> uniResp = new UniResp<>();
        if (awardId == null){
            uniResp.setMessage("awardId错误");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        RaffleAward award = awardRepo.findOne(allOf(
                QRaffleAward.raffleAward.id.eq(awardId),
                QRaffleAward.raffleAward.deleted.ne(true)
        ));
        if (award == null){
            uniResp.setMessage("奖品错误");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        award.setDeleted(true);

        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp<String> list(String raffleAppId, String raffleId, List<UniOrder> orders) {
        return null;
    }
}
