package net.kingsik.qh.activity.client;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {UtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
//    protected UtProps utProps;


    @Test
    public void test01() {

//        assertThat(utProps.getA()).isEqualTo("aaabbb");

    }

}
