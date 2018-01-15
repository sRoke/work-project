(function () {

    angular.module('MyApp', ['ngMaterial', 'ngMessages', 'ngMdIcons'])
        .controller('DemoCtrl', ['$scope', '$http', '$log', function ($scope, $http, $log) {

            var qhAgencyWapUrl = "http://192.168.0.41:10070/net.kingsilk.qh.agency.admin/local/14100/rs";
            var qhOAuthWapUrl = "https://login.kingsilk.net/local/14100/rs";

            var vm = $scope.vm = {
                at: null,   // 获取的 access_token
                photos: null // 图片列表
            };

            // 构建配置的数据
            var jso = new JSO({
                providerID: "qh-oauth-wap",
                client_id: "CLIENT_ID_qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin-front",
                redirect_uri: qhAgencyWapUrl + "/implicit.html",
                //authorization: qhOAuthWapUrl + "/oauth/authorize?loginType=WX_SCAN&autoCreateUser",
                // authorization: qhOAuthWapUrl + "/oauth/authorize?loginType=PASSWORD",
                //authorization: qhOAuthWapUrl + "/oauth/authorize?loginType=WX&autoCreateUser",
                authorization: qhOAuthWapUrl + "/oauth/authorize?loginType=PHONE",
                scopes: {request: ["LOGIN"]},
                debug: true
            });

            // 检查是否是从 认证服务器回来的。
            jso.callback(location.href, function (at) {
                vm.at = at;
                $log.log("------------ return from OAuth server", at);
            }, "oauth2-authorization-server");


            // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
            jso.on('redirect', function (url) {
                $log.info("-------redirect : " + url);
                location.href = url;
                //JSO.redirect(url)
            });

            $scope.goOAuth = function () {

                // 由 JSO 生成相应的的URL
                jso.getToken(function (token) {
                    vm.at = token;
                    console.log("I got the token: ", token);
                }, {});
            };

            $scope.clearAt = function () {
                jso.wipeTokens();
                vm.at = null;
            };


            $scope.getResource = function () {
                $http({
                    method: "GET",
                    url: qhAgencyWapUrl + "/api/xxx/userSec",
                    headers: {
                        'Authorization': 'Bearer ' + vm.at.access_token,
                        "Company-Id": "58e59234785a82000005a143"
                    }
                }).then(function (resp) {
                    $log.error("------- getResource: OK : ", resp);
                    vm.photos = resp.data;
                }, function (resp) {
                    $log.error("------- getResource: ERROR : ", resp);

                    // TODO 如果OAuth 出错的话，应该 jso.wipeTokens(); 并重新获取 AT
                })
            };

            $scope.getOAuthResource = function () {
                $http({
                    method: "GET",
                    url: "https://login.kingsilk.net/local/14100/rs/api/xxx/userSec",
                    headers: {
                        'Authorization': 'Bearer ' + vm.at.access_token
                    }
                }).then(function (resp) {
                    $log.error("------- getResource: OK : ", resp);
                    vm.oauthRsc = resp.data;
                }, function (resp) {
                    $log.error("------- getResource: ERROR : ", resp);

                    // TODO 如果OAuth 出错的话，应该 jso.wipeTokens(); 并重新获取 AT
                })
            };


            $scope.passAt = function () {

                $http({
                    method: "GET",
                    url: "./api/xxx/passAt",
                    headers: {
                        'Authorization': 'Bearer ' + vm.at.access_token
                    }
                }).then(function (resp) {
                    $log.error("------- getResource: OK : ", resp);
                    vm.passAt = resp.data;
                }, function (resp) {
                    $log.error("------- getResource: ERROR : ", resp);

                    // TODO 如果OAuth 出错的话，应该 jso.wipeTokens(); 并重新获取 AT
                })
            };

            $scope.logout = function () {
                $http({
                    method: "GET",
                    url: qhOAuthWapUrl+"/logout",
                    // headers: {
                    //     'Authorization': 'Bearer ' + vm.at.access_token
                    // }
                }).then(function (resp) {
                    $log.error("------- logout: OK : ", resp);
                    vm.passAt = resp.data;
                }, function (resp) {
                    $log.error("------- logout: ERROR : ", resp);

                    // TODO 如果OAuth 出错的话，应该 jso.wipeTokens(); 并重新获取 AT
                })

            };

        }])
})();