package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.brandCom.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {UtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
public abstract class BaseTest {

    final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BrandComApi brandComApi;
}
