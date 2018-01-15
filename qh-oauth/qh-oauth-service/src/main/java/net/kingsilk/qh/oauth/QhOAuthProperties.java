package net.kingsilk.qh.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.KeyStore;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "net.kingsilk.qh.oauth")
public class QhOAuthProperties {

    private QhOAuth qhOAuth;
    private QhCommon qhCommon;
    private Wx4j wx4j;
    private Http http;

    public QhOAuth getQhOAuth() {
        return qhOAuth;
    }

    public void setQhOAuth(QhOAuth qhOAuth) {
        this.qhOAuth = qhOAuth;
    }

    public QhCommon getQhCommon() {
        return qhCommon;
    }

    public void setQhCommon(QhCommon qhCommon) {
        this.qhCommon = qhCommon;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Wx4j getWx4j() {
        return wx4j;
    }

    public void setWx4j(Wx4j wx4j) {
        this.wx4j = wx4j;
    }

    /**
     * http相关设置
     */
    public static class Http {
        private Proxy proxy;

        public Proxy getProxy() {
            return proxy;
        }

        public void setProxy(Proxy proxy) {
            this.proxy = proxy;
        }

        /**
         * 代理设置
         */
        public static class Proxy {

            private boolean enabled;
            private String host;
            private int port;

            public boolean getEnabled() {
                return enabled;
            }

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
    }

    public static class Wx4j {

        private Broker broker;

        public Broker getBroker() {
            return broker;
        }

        public void setBroker(Broker broker) {
            this.broker = broker;
        }

        public static class Broker {

            String apiBaseUri;

            public String getApiBaseUri() {
                return apiBaseUri;
            }

            public void setApiBaseUri(String apiBaseUri) {
                this.apiBaseUri = apiBaseUri;
            }
        }


    }

    public static class QhOAuth {

        private Admin admin = new Admin();
        private Wap wap = new Wap();

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }


        public static class Admin {
        }

        public static class Wap {
            private String url;
            /**
             * 密码登录的URL。
             */
            private String passwordLoginUrl;
            /**
             * 微信登录的URL。
             */
            private String wxLoginUrl;
            /**
             * 微信扫码登录的URL。
             */
            private String wxScanLoginUrl;
            /**
             * 微信企业号登录的URL。
             */
            private String wxQyhLoginUrl;
            /**
             * 微信企业号扫码登录的URL。
             */
            private String wxQyhScanLoginUrl;
            /**
             * 手机号码、验证码登录的URL。
             */
            private String phoneLoginUrl;
            private String accessTokenUrl;
            private String clientId;
            private String clientSecret;
            /**
             * 作为 oauth client， 向 qh-oauth-wap 请求授权时所携带的 scope。
             */
            private List<String> scopes;
            private Wx wx;
            private WxQyh wxQyh;
            private Jwt jwt;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPasswordLoginUrl() {
                return passwordLoginUrl;
            }

            public void setPasswordLoginUrl(String passwordLoginUrl) {
                this.passwordLoginUrl = passwordLoginUrl;
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

            public String getAccessTokenUrl() {
                return accessTokenUrl;
            }

            public void setAccessTokenUrl(String accessTokenUrl) {
                this.accessTokenUrl = accessTokenUrl;
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

            public List<String> getScopes() {
                return scopes;
            }

            public void setScopes(List<String> scopes) {
                this.scopes = scopes;
            }

            public Wx getWx() {
                return wx;
            }

            public void setWx(Wx wx) {
                this.wx = wx;
            }

            public WxQyh getWxQyh() {
                return wxQyh;
            }

            public void setWxQyh(WxQyh wxQyh) {
                this.wxQyh = wxQyh;
            }

            public Jwt getJwt() {
                return jwt;
            }

            public void setJwt(Jwt jwt) {
                this.jwt = jwt;
            }


            /**
             * JWT 相关配置。
             */
            public static class Jwt {

                private Jks jks;

                public Jks getJks() {
                    return jks;
                }

                public void setJks(Jks jks) {
                    this.jks = jks;
                }

                /**
                 * 用以校验 JWT 签名的 JKS 格式的 keyStore 的相关信息。
                 *
                 * @see KeyStore
                 */
                public static class Jks {
                    /**
                     * keyStore 的路径
                     *
                     * @see Resource
                     */
                    private Resource path;
                    /**
                     * keyStore 的 store 密码
                     */
                    private String storePass;
                    /**
                     * keyStore 的 key 的别名
                     */
                    private String keyAlias;
                    /**
                     * keyStore 的 key 密码
                     */
                    private String keyPass;

                    public Resource getPath() {
                        return path;
                    }

                    public void setPath(Resource path) {
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
            }

            public static class Wx {

                private String appId;
                private String appSecret;
                /**
                 * 微信授权登录时的 scope
                 */
                private List<String> scopes;

                public String getAppId() {
                    return appId;
                }

                public void setAppId(String appId) {
                    this.appId = appId;
                }

                public String getAppSecret() {
                    return appSecret;
                }

                public void setAppSecret(String appSecret) {
                    this.appSecret = appSecret;
                }

                public List<String> getScopes() {
                    return scopes;
                }

                public void setScopes(List<String> scopes) {
                    this.scopes = scopes;
                }

            }

            public static class WxQyh {

                private String corpId;
                private String corpSecret;
                private String providerSecret;
                /**
                 * 微信授权登录时的 scope
                 */
                private List<String> scopes;

                public String getCorpId() {
                    return corpId;
                }

                public void setCorpId(String corpId) {
                    this.corpId = corpId;
                }

                public String getCorpSecret() {
                    return corpSecret;
                }

                public void setCorpSecret(String corpSecret) {
                    this.corpSecret = corpSecret;
                }

                public String getProviderSecret() {
                    return providerSecret;
                }

                public void setProviderSecret(String providerSecret) {
                    this.providerSecret = providerSecret;
                }

                public List<String> getScopes() {
                    return scopes;
                }

                public void setScopes(List<String> scopes) {
                    this.scopes = scopes;
                }

            }
        }
    }

    public static class QhCommon {

        private Admin admin = new Admin();
        private Wap wap = new Wap();

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }


        public static class Admin {

            private String url;
            private String resourceId;
            private Api api = new Api();

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getResourceId() {
                return resourceId;
            }

            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }

            public Api getApi() {
                return api;
            }

            public void setApi(Api api) {
                this.api = api;
            }

            public static class Api {

                private String wx_selfAt;
                private String wx_selfUserAt;

                public String getWx_selfAt() {
                    return wx_selfAt;
                }

                public void setWx_selfAt(String wx_selfAt) {
                    this.wx_selfAt = wx_selfAt;
                }

                public String getWx_selfUserAt() {
                    return wx_selfUserAt;
                }

                public void setWx_selfUserAt(String wx_selfUserAt) {
                    this.wx_selfUserAt = wx_selfUserAt;
                }

            }
        }

        public static class Wap {

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }
    }

}
