package net.kingsilk.qh.activity.service;


import msg.EventPublishEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "net.kingsilk.qh.activity")
@Component
public class QhActivityProperties {

    private Jwt jwt = null;
    private QhOAuth qhOAuth = null;
    private QhActivity qhActivity = null;
    private Http http = null;
    private AgencyUt agencyUt = null;
    private OauthUt oauthUt = null;
    private Wx4jUt wx4jUt = null;
    private PlatformUt platformUt = null;
    private Zk zk;
    private Mq mq;
    private Msg msg;

    public Zk getZk() {
        return zk;
    }

    public void setZk(Zk zk) {
        this.zk = zk;
    }

    public AgencyUt getAgencyUt() {
        return agencyUt;
    }

    public void setAgencyUt(AgencyUt agencyUt) {
        this.agencyUt = agencyUt;
    }

    public OauthUt getOauthUt() {
        return oauthUt;
    }

    public void setOauthUt(OauthUt oauthUt) {
        this.oauthUt = oauthUt;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public QhOAuth getQhOAuth() {
        return qhOAuth;
    }

    public void setQhOAuth(QhOAuth qhOAuth) {
        this.qhOAuth = qhOAuth;
    }

    public QhActivity getQhActivity() {
        return qhActivity;
    }

    public void setQhActivity(QhActivity qhActivity) {
        this.qhActivity = qhActivity;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Wx4jUt getWx4jUt() {
        return wx4jUt;
    }

    public void setWx4jUt(Wx4jUt wx4jUt) {
        this.wx4jUt = wx4jUt;
    }

    public PlatformUt getPlatformUt() {
        return platformUt;
    }

    public void setPlatformUt(PlatformUt platformUt) {
        this.platformUt = platformUt;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public Mq getMq() {
        return mq;
    }

    public void setMq(Mq mq) {
        this.mq = mq;
    }


    /**
     * http相关设置
     */
    public static class Http {

        private Proxy proxy;

        /**
         * 代理设置
         */
        public static class Proxy {
            private boolean enabled;
            private String host;
            private int port;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }
        }

        public Proxy getProxy() {
            return proxy;
        }

        public void setProxy(Proxy proxy) {
            this.proxy = proxy;
        }
    }

    public static class Jwt {

        /**
         * 校验 JWT 签名的 key
         */
        private Jks jks;


        public static class Jks {
            private String path;
            private String storePass;
            private String keyAlias;
            private String keyPass;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getStorePass() {
                return storePass;
            }

            public void setStorePass(String storePass) {
                this.storePass = storePass;
            }

            public String getKeyAlias() {
                return keyAlias;
            }

            public void setKeyAlias(String keyAlias) {
                this.keyAlias = keyAlias;
            }

            public String getKeyPass() {
                return keyPass;
            }

            public void setKeyPass(String keyPass) {
                this.keyPass = keyPass;
            }
        }

        public Jks getJks() {
            return jks;
        }

        public void setJks(Jks jks) {
            this.jks = jks;
        }
    }

    /**
     * qh-oauth 相关配置。
     */
    public static class QhOAuth {
        private Wap wap;

        /**
         * qh-oauth-wap 相关配置
         */
        public static class Wap {
            private String url;
            private String accessTokenUri;
            private String userAuthorizationUri;
            private String checkTokenUri;
            private String userinfoApi;
            private String wxLoginUrl;
            private String wxScanLoginUrl;
            private String wxQyhLoginUrl;
            private String wxQyhScanLoginUrl;
            private String phoneLoginUrl;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAccessTokenUri() {
                return accessTokenUri;
            }

            public void setAccessTokenUri(String accessTokenUri) {
                this.accessTokenUri = accessTokenUri;
            }

            public String getUserAuthorizationUri() {
                return userAuthorizationUri;
            }

            public void setUserAuthorizationUri(String userAuthorizationUri) {
                this.userAuthorizationUri = userAuthorizationUri;
            }

            public String getCheckTokenUri() {
                return checkTokenUri;
            }

            public void setCheckTokenUri(String checkTokenUri) {
                this.checkTokenUri = checkTokenUri;
            }

            public String getUserinfoApi() {
                return userinfoApi;
            }

            public void setUserinfoApi(String userinfoApi) {
                this.userinfoApi = userinfoApi;
            }

            public String getWxLoginUrl() {
                return wxLoginUrl;
            }

            public void setWxLoginUrl(String wxLoginUrl) {
                this.wxLoginUrl = wxLoginUrl;
            }

            public String getWxScanLoginUrl() {
                return wxScanLoginUrl;
            }

            public void setWxScanLoginUrl(String wxScanLoginUrl) {
                this.wxScanLoginUrl = wxScanLoginUrl;
            }

            public String getWxQyhLoginUrl() {
                return wxQyhLoginUrl;
            }

            public void setWxQyhLoginUrl(String wxQyhLoginUrl) {
                this.wxQyhLoginUrl = wxQyhLoginUrl;
            }

            public String getWxQyhScanLoginUrl() {
                return wxQyhScanLoginUrl;
            }

            public void setWxQyhScanLoginUrl(String wxQyhScanLoginUrl) {
                this.wxQyhScanLoginUrl = wxQyhScanLoginUrl;
            }

            public String getPhoneLoginUrl() {
                return phoneLoginUrl;
            }

            public void setPhoneLoginUrl(String phoneLoginUrl) {
                this.phoneLoginUrl = phoneLoginUrl;
            }
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }
    }


    public static class QhActivity {

        private Server server;

        public static class Server {

            private String resourceId;

            private String url;

            private String clientId;

            private String clientSecret;

            public String getResourceId() {
                return resourceId;
            }

            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }

        public Server getServer() {
            return server;
        }

        public void setServer(Server server) {
            this.server = server;
        }
    }

    public static class OauthUt {
        private String basePath;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }

    public static class AgencyUt {
        private String basePath;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }

    public static class PlatformUt {
        private String basePath;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }


    public static class Wx4jUt {
        private String basePath;

        private String tplId;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }

        public String getTplId() {
            return tplId;
        }

        public void setTplId(String tplId) {
            this.tplId = tplId;
        }
    }

    public static class Zk {
        private String connStr;

        public String getConnStr() {
            return connStr;
        }

        public void setConnStr(String connStr) {
            this.connStr = connStr;
        }
    }

    public static class Mq {

        private String prefix;
//        private String adminBean = "amqpAdmin";

        private Conf defaultConf;
        private Map<Class, Conf> confs;


        public static class Conf {

            /**
             * 最长加锁等待时间：毫秒
             */
            private Long lockWaitTime = 1000L;


            public Long getLockWaitTime() {
                return lockWaitTime;
            }

            public void setLockWaitTime(Long lockWaitTime) {
                this.lockWaitTime = lockWaitTime;
            }
        }


        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public Conf getDefaultConf() {
            return defaultConf;
        }

        public void setDefaultConf(Conf defaultConf) {
            this.defaultConf = defaultConf;
        }

        public Map<Class, Conf> getConfs() {
            return confs;
        }

        public void setConfs(Map<Class, Conf> confs) {
            this.confs = confs;
        }

    }

    public static class Msg {
        private EventPublishEnum publishTo = EventPublishEnum.MQ;

        public EventPublishEnum getPublishTo() {
            return publishTo;
        }

        public void setPublishTo(EventPublishEnum publishTo) {
            this.publishTo = publishTo;
        }
    }


}
