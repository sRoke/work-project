package net.kingsilk.qh.raffle.server.resource.raffleApp.record.convert;

import net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto.RaffleRecordMinResp;
import net.kingsilk.qh.raffle.domain.RaffleRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordConvert {

//    @Autowired
//    private RaffleAwardRepo awardRepo;
//
//    @Autowired
//    private WeiXinUserRepo userRepo;

    public RaffleRecordMinResp recordMinResp(RaffleRecord record) {
        if (record == null) {
            return null;
        }
        RaffleRecordMinResp target = new RaffleRecordMinResp();
        target.setHandleStatus(record.getHandleStatus());
        target.setHandleStausDesp(record.getHandleStatus().getDesp());
        target.setAvatar(record.getAvatar());
        target.setPhone(record.getPhone());
        target.setRealName(record.getNickName());
        target.setAwardName(record.getAwardName());
        target.setId(record.getId());
        return target;
    }
}
