package net.kingsilk.qh.oauth

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "net.kingsilk.qh.oauth.ut")
@CompileStatic
@ToString(includeNames = true)
class QhOAuthUtProperties {

}
