package net.kingsilk.qh.raffle.server.resource.raffleApp.award.convert;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AwardRespConvert implements Converter<RaffleAward, AwardInfo> {
    @Override
    public AwardInfo convert(RaffleAward source) {
        AwardInfo awardInfo = new AwardInfo();
        awardInfo.setName(source.getName());
//        awardInfo.setTitle(source.getTitle());
        awardInfo.setNum(source.getNum());
        awardInfo.setPrompt(source.getPrompt());
        awardInfo.setChance(source.getChance());
        awardInfo.setPicture(source.getPicture());
        awardInfo.setSeqNum(source.getSeqNum());
        awardInfo.setAwardType(source.getAwardType());
        return awardInfo;
    }
}
