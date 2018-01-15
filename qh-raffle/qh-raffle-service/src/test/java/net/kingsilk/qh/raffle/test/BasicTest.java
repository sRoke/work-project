package net.kingsilk.qh.raffle.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QhLotteryUtAppTest.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BasicTest {
}
