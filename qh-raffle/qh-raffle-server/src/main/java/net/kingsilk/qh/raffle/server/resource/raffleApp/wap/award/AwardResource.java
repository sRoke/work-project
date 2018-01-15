package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.award;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.award.AwardApi;
import net.kingsilk.qh.raffle.api.common.*;
import net.kingsilk.qh.raffle.domain.QRaffleRecord;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import net.kingsilk.qh.raffle.domain.RaffleRecord;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.repo.RaffleRecordRepo;
import net.kingsilk.qh.raffle.server.resource.raffleApp.award.convert.AwardRespConvert;
import net.kingsilk.qh.raffle.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static com.querydsl.core.types.dsl.Expressions.allOf;

public class AwardResource implements AwardApi{

    @Autowired
    private SecService secService;

    @Autowired
    private RaffleAwardRepo awardRepo;

    @Autowired
    private RaffleRecordRepo recordRepo;

    @Autowired
    private AwardRespConvert awardRespConvert;

    @Override
    public UniResp<UniPageResp<AwardInfo>> list(String raffleAppId, String raffleId) {
        Assert.notNull(raffleAppId,"raffleAppId为空");
        Assert.notNull(raffleId,"raffleId为空");
        String userId = secService.curUserId();
        UniResp<UniPageResp<AwardInfo>> uniResp = new UniResp<>();
        if (StringUtils.isEmpty(userId)){
            uniResp.setMessage("请先登录");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }
        Iterable<RaffleRecord> allRecords = recordRepo.findAll(allOf(
                QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                QRaffleRecord.raffleRecord.raffleId.eq(raffleId),
                QRaffleRecord.raffleRecord.userId.eq(userId),
                QRaffleRecord.raffleRecord.deleted.ne(true)
        ));
        UniPageResp<AwardInfo> awardPageResp = new UniPageResp<>();
        allRecords.forEach(record -> {
            RaffleAward award = awardRepo.findOne(record.getAwardId());
            AwardInfo awardInfo = awardRespConvert.convert(award);
            awardPageResp.getContent().add(awardInfo);
        });
        uniResp.setData(awardPageResp);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setMessage("success");
        return uniResp;
    }
}
