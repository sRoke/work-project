package net.kingsilk.qh.agency.wap

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "net.kingsilk.qh.agency.ut")
@CompileStatic
@ToString(includeNames = true)
class QhAgencyUtProperties {

    OAuth oauth = new OAuth()
    Agency agency = new Agency()

    /**
     * qh-oauth-wap 相关配置信息
     */
    @ToString(includeNames = true)
    static class OAuth {

        Wap wap = new Wap()


        static class Wap {

            /**
             * 认证服务器的主URL
             */
            String url
            String accessTokenUri
            String userAuthorizationUri
            String checkTokenUri
        }

    }

    /**
     * qh-agency-wap 相关配置信息
     */
    @ToString(includeNames = true)
    static class Agency {

        Wap wap = new Wap()
//        Admin net.kingsilk.qh.agency.admin = new Admin()

        /**
         * qh-agency-wap 相关配置信息
         */
        @ToString(includeNames = true)
        static class Wap {


            String url

            Front front = new Front()

            @ToString(includeNames = true)
            static class Front {

                String url
                String clientId

            }
        }

//        /**
//         * qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin 相关配置信息
//         */
//        @ToString(includeNames = true)
//        static class Admin {
//
//            String url
//
//            Front front = new Front()
//
//            @ToString(includeNames = true)
//            static class Front {
//
//                String url
//                String clientId
//
//            }
//        }
    }

}
