import net.kingsilk.qh.raffle.domain.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.raffle.domain.VoteWorks;
import net.kingsilk.qh.raffle.repo.VoteWorksRepo;
import net.kingsilk.qh.raffle.server.QhRaffleServerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@SpringBootTest(classes = QhActivityServerApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"base", "dev"})
public class TestInsert {

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Test
    public void test() {
        for (int i = 1; i < 100000; i++) {
            VoteWorks voteWorks = new VoteWorks();
            voteWorks.setSignUpTime(new Date());
            voteWorks.setRank(i);
            voteWorks.setVirtualVotes(0);
            voteWorks.setOrder(Integer.MAX_VALUE);
            voteWorks.setStatus(VoteWorksStatusEnum.NORMAL);
            voteWorks.setTotalVotes(32);
            voteWorks.setName("anmo");
            voteWorks.setSeq("seq:" + i);
            voteWorks.setPhone("110");
            voteWorks.setPv(10);
            voteWorks.setSlogan("touwoba");
            voteWorks.setWorkerHeaderImgUrl("111111111111111111111111");
            voteWorks.setUserId("anmo");
            voteWorks.setNickName("anmoxiaohao");
            voteWorks.setVoteActivityId("59f67fe665220500083332cf");
            voteWorks.setLastVoteTime(new Date());
            voteWorks.setOpenId("opp7G095DIRJ44ZbRf3i258u_ozI");
            voteWorksRepo.save(voteWorks);
        }
    }

}
