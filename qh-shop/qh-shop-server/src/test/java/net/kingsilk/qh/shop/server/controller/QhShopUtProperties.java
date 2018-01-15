package net.kingsilk.qh.shop.server.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "net.kingsilk.qh.shop.ut")
public class QhShopUtProperties {


    public OAuth oauth = new OAuth();
    public Shop shop = new Shop();

    public static class OAuth {

        Wap wap = new Wap();

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }

        static class Wap {

            /**
             * 认证服务器的主URL
             */
            String url;
            String accessTokenUri;
            String userAuthorizationUri;
            String checkTokenUri;

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
        }


    }

    public static class Shop {

        Wap wap = new Wap();
        Admin admin = new Admin();

        public Wap getWap() {
            return wap;
        }

        public void setWap(Wap wap) {
            this.wap = wap;
        }

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

        /**
         * qh-agency-wap 相关配置信息
         */
        static class Wap {


            String url;

            Front front = new Front();

            static class Front {

                String url;
                String clientId;

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
            }


            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Front getFront() {
                return front;
            }

            public void setFront(Front front) {
                this.front = front;
            }
        }

        /**
         * qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin 相关配置信息
         */
        public static class Admin {

            String url;

            Front front = new Front();

            static class Front {

                String url;
                String clientId;

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
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Front getFront() {
                return front;
            }

            public void setFront(Front front) {
                this.front = front;
            }
        }
    }


    public OAuth getOauth() {
        return oauth;
    }

    public void setOauth(OAuth oauth) {
        this.oauth = oauth;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
