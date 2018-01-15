package net.kingsik.qh.oauth.client;

import net.kingsik.qh.oauth.client.util.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {UtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UtProps utProps;


    @Test
    public void test01() {

        assertThat(utProps.getA()).isEqualTo("aaabbb");

    }

}
