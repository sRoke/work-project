// 构建配置的数据
(function () {



    angular.module('qh-bargain-wap-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.index", {
            url: "/{id}",
            views: {
                "@": {
                    templateUrl: 'views/main/index.html',
                    controller: IndexController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    IndexController.$inject = ['$scope', '$http', 'appConfig', '$stateParams', '$mdDialog', 'wxService', '$interval', '$filter', '$rootScope', '$timeout', '$state', '$templateCache'];
    function IndexController($scope, $http, appConfig, $stateParams, $mdDialog, wxService, $interval, $filter, $rootScope, $timeout, $state, $templateCache) {
        $scope.getInfo = function () {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/info?id=' + $stateParams.id,
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token)
                }
            }).then(function (resp) {
                // $state.go('main.bargain', {activeId: '123456'}, {reload: true})
                $scope.infoData = resp.data;
                console.log(resp);
                if ($scope.infoData.code == 'SUCCESS') {
                    $state.go('main.bargain', {id: $scope.infoData.id}, {reload: true})
                }
            })
        };
        if (store.get(appConfig.token)) {
            $scope.getInfo();
        }



        var jso = new JSO({
            providerID: 'oauth2-authorization-server-lottery-wap',
            client_id: 'CLIENT_ID_kingsilk_qh-lottery-wap-front',
            redirect_uri: location.href,
            authorization: appConfig.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
            scopes: {request: ['LOGIN']},
            debug: true
        });
        //登录类型，参数
        var loginType = {
            WX: "loginType=WX&autoCreateUser",
            WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
            WX_QYH: "loginType=WX_QYH&autoCreateUser",
            WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
            PASSWORD: "loginType=PASSWORD"
        };
        // 检查是否是从 认证服务器回来的。
        jso.callback(location.href, function (at) {
            window.at = at;
            // console.log('OAuth server  >>>>>>  at');
        }, "oauth2-authorization-server-lottery-wap");

        // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
        jso.on('redirect', function (url) {
            // console.log("jso.on >>>  redirect  >>> " + url);
            location.href = url;
        });
        //登录
        function goOAuth() {

            // alert(2);
            var ua = window.navigator.userAgent.toLowerCase();
            var type = "WX_SCAN";
            if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
                type = "WX";
            }
            //修改登录方式
            jso.config.config.authorization = appConfig.wxloginPath + loginType[type];
            jso.getToken(function (token) {
                window.at = token;
                store.set(appConfig.token, token.access_token);
            }, {})
        }

        //自调用登录
        goOAuth();









    }
})();
