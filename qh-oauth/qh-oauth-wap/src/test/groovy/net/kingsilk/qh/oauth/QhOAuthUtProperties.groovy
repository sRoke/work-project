package net.kingsilk.qh.oauth

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "net.kingsilk.qh.oauth.ut")
@CompileStatic
@ToString(includeNames = true)
class QhOAuthUtProperties {

    OAuth oauth = new OAuth()

    @ToString(includeNames = true)
    static class OAuth {

        Wap wap = new Wap()

        @ToString(includeNames = true)
        static class Wap {
            /**
             * 认证服务器的主URL
             */
            String url
            String accessTokenUri
            String userAuthorizationUri
            String checkTokenUri
            String realm

        }
    }

}
