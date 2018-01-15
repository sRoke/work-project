package net.kingsilk.qh.oauth

import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner)
@ActiveProfiles(["default", "ut"])
@SpringBootTest(
        classes = [UtApp],
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
abstract class BaseTest {

    final String logPrefix = "█" * 40 + " "
    final Logger log = LoggerFactory.getLogger(getClass());

//    @LocalServerPort
//    int port;

    TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES)
//    @Autowired
//    TestRestTemplate wwwRestTemplate


    @Autowired
    QhOAuthUtProperties ut


    @Configuration
    @EnableConfigurationProperties
//    @SpringBootApplication(scanBasePackages = 'net.kingsilk.qh.oauth')
    static class UtApp {

        @Bean
        QhOAuthUtProperties qhOAuthUtProperties() {
            return new QhOAuthUtProperties()
        }

    }

}
