package net.kingsilk.qh.raffle.server.resource.raffleApp.award.convert;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.award.dto.AwardInfo;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import org.springframework.stereotype.Component;

@Component
public class AwardReqConvert {
    public RaffleAward convert(AwardInfo source, RaffleAward award) {

        award.setName(source.getName());
//        award.setTitle(source.getTitle());
        award.setNum(source.getNum());
        award.setPrompt(source.getPrompt());
        award.setChance(source.getChance());
        award.setPicture(source.getPicture());
        return award;
    }
}
