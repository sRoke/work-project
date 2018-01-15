package net.kingsilk.qh.shop.service;

import net.kingsilk.qh.shop.msg.EventPublishEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "net.kingsilk.qh.shop")
@Component
public class QhShopProperties {

    private Jwt jwt;
    private QhOAuth qhOAuth;
    private QhPay qhPay;
    private QhAgency qhAgency;
    private Http http;
    private QhShop qhShop;
    private AgencyUt agencyUt;
    private OauthUt oauthUt;
    private PlatformUt platformUt = null;
    private Msg msg;
    private Mq mq;
    private Zk zk;
    private Sms sms;
    private Wx4j wx4j;

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

    /**
     * JWT 相关配置。
     */
    public static class Jwt {

        /**
         * 校验 JWT 签名的 key
         */
        private String verifierKey;

        public String getVerifierKey() {
            return verifierKey;
        }

        public void setVerifierKey(String verifierKey) {
            this.verifierKey = verifierKey;
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
            private String userinfoByPhoneApi;
            private String getUserInfoByUserIdApi;

            public String getGetUserInfoByUserIdApi() {
                return getUserInfoByUserIdApi;
            }

            public void setGetUserInfoByUserIdApi(String getUserInfoByUserIdApi) {
                this.getUserInfoByUserIdApi = getUserInfoByUserIdApi;
            }

            public String getUserinfoByPhoneApi() {
                return userinfoByPhoneApi;
            }

            public void setUserinfoByPhoneApi(String userinfoByPhoneApi) {
                this.userinfoByPhoneApi = userinfoByPhoneApi;
            }

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
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }
    }

    /**
     * qh-pay 相关配置
     */
    public static class QhPay {

        private Wap wap;

        /**
         * qh-pay-wap 相关配置
         */
        public static class Wap {
            private String resourceId;
            private String url;
            private String appId;

            private Api api;

            /**
             * 各个API 的 URL
             */
            public static class Api {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

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

            public Api getApi() {
                return api;
            }

            public void setApi(Api api) {
                this.api = api;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }
    }

    /**
     * qh-agency 相关配置
     */
    public static class QhAgency {

        private Server server;

        public static class Server {
            private String resourceId;
            private String payNotifyUrl;
            private String clientId;
            private String url;
            private String clientSecret;
            private List<String> scopes;

            public String getResourceId() {
                return resourceId;
            }

            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }

            public String getPayNotifyUrl() {
                return payNotifyUrl;
            }

            public void setPayNotifyUrl(String payNotifyUrl) {
                this.payNotifyUrl = payNotifyUrl;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }

            public List<String> getScopes() {
                return scopes;
            }

            public void setScopes(List<String> scopes) {
                this.scopes = scopes;
            }
        }

        public Server getServer() {
            return server;
        }

        public void setServer(Server server) {
            this.server = server;
        }
    }

    public static class QhShop {

        private Api api;

        private String resourceId;
        private String clientId;
        private String clientSecret;

        private String url;

        private Html html;

        public static class Html {
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public Html getHtml() {
            return html;
        }

        public void setHtml(Html html) {
            this.html = html;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Api getApi() {
            return api;
        }

        public void setApi(Api api) {
            this.api = api;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
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

        public static class Api {
            private String payNotifyUrl;

            public String getPayNotifyUrl() {
                return payNotifyUrl;
            }

            public void setPayNotifyUrl(String payNotifyUrl) {
                this.payNotifyUrl = payNotifyUrl;
            }
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

    public static class OauthUt {
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

    public static class Msg {
        private EventPublishEnum publishTo = EventPublishEnum.MQ;

        public EventPublishEnum getPublishTo() {
            return publishTo;
        }

        public void setPublishTo(EventPublishEnum publishTo) {
            this.publishTo = publishTo;
        }
    }

    public static class Mq {

        private String prefix;

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

    public static class Zk {
        private String connStr;

        public String getConnStr() {
            return connStr;
        }

        public void setConnStr(String connStr) {
            this.connStr = connStr;
        }
    }

    public static class Sms {
        private String appKey;
        private String appSecret;

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }
    }

    public static class Wx4j {
        private Wap wap;

        public static class Wap {
            private Api api;

            public static class Api {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public Api getApi() {
                return api;
            }

            public void setApi(Api api) {
                this.api = api;
            }
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }
    }

    public QhShop getQhShop() {
        return qhShop;
    }

    public void setQhShop(QhShop qhShop) {
        this.qhShop = qhShop;
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

    public QhPay getQhPay() {
        return qhPay;
    }

    public void setQhPay(QhPay qhPay) {
        this.qhPay = qhPay;
    }

    public QhAgency getQhAgency() {
        return qhAgency;
    }

    public void setQhAgency(QhAgency qhAgency) {
        this.qhAgency = qhAgency;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
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

    public Zk getZk() {
        return zk;
    }

    public void setZk(Zk zk) {
        this.zk = zk;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public PlatformUt getPlatformUt() {
        return platformUt;
    }

    public void setPlatformUt(PlatformUt platformUt) {
        this.platformUt = platformUt;
    }

    public Wx4j getWx4j() {
        return wx4j;
    }

    public void setWx4j(Wx4j wx4j) {
        this.wx4j = wx4j;
    }
}
