import conf from "../../../../conf";

import store from "store";

var $scope,
    $rootScope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    $httpParamSerializer;

class Controller {
    constructor(_$scope,
                _$rootScope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _$httpParamSerializer) {

        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        $rootScope = _$rootScope;
        $httpParamSerializer = _$httpParamSerializer;

        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());


        //判断手机段移动段
        // if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
        //    console.log('手机端')
        // }else{
        //     console.log('pc端')
        // }


        // console.log('$stateParams.redirect_uri',$stateParams.redirect_uri);
        // 先检查是否已经登录
        $scope.checkLogin = function () {
            $http({
                method: "GET",
                url: conf.oauthPath + "/s/user/info"
            }).then(function (resp) {
                console.log("SUCCESS - ", resp);

                if(resp.data.data.phone){
                    $scope.goImplicitAuthorize();
                }else {
                    console.log('url=-=', $location.absUrl().split('/oauth/authorize')[0] + '/user/bindWx?backUrl=' + encodeURIComponent($location.absUrl()) );
                    $state.go("main.reg.phone", {
                        backUrl:$location.absUrl().split('/oauth/authorize')[0] + '/user/bindWx?backUrl=' + encodeURIComponent($location.absUrl()),
                    });
                }
            }, function (resp) {
                console.log("oauthorize-ERROR", resp);
                // 未登录
                if (resp.status == 401) {
                    // 判断登录方式进行跳转
                    loginService.getLoginType($stateParams.loginType);
                }
            });
        };
        $scope.checkLogin();



        $scope.clientId = "CLIENT_ID_qh-agency-wap-front";
        // $scope.redirectUri = "https://kingsilk.net/404?a=aa&b=bb#/xxx/yyy?c=cc&d=dd";
        // $scope.scope = "LOGIN";
        // $scope.state = "randomStrXxx";


        //--------------------------------------------------老版获取token
        $scope.oldImplicitAuthorize = function(){
            $http({
                method: "POST",
                url: conf.rootPath + "/oauth/authorize",
                params: {
                    response_type: "token",
                    client_id: $stateParams.client_id,
                    // client_id:$scope.clientId,
                    redirect_uri: $stateParams.redirect_uri,
                    scope: $stateParams.scope,
                    state: $stateParams.state
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (resp) {
                console.log("SUCCESS - ", resp)
            }, function (resp) {
                console.log("ERROR", resp);
            });
        };
        //--------------------------------------------------新版获取token--现在都是response_type: "token",
        $scope.goImplicitAuthorize = function () {
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/oauth/authorize",
                data: {
                    response_type: "token",
                    client_id: $stateParams.client_id,
                    // client_id:$scope.clientId,
                    redirect_uri: $stateParams.redirect_uri,
                    scope: $stateParams.scope,
                    state: $stateParams.state
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (resp) {
                // console.log("SUCCESS - ", resp);
                location.href = resp.data.data.redirectUri;
            }, function (resp) {
                // console.log("ERROR", resp);
                // history.back();
            });
        };
        //----------------------------------------暂时用不上
        $scope.goCodeAuthorize = function () {
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/oauth/authorize",
                data: {
                    response_type: "code",
                    client_id: $scope.clientId,
                    redirect_uri: $scope.redirectUri,
                    scope: $scope.scope,
                    state: $scope.state
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (resp) {

                console.log("SUCCESS - ", resp)

            }, function (resp) {
                console.log("ERROR", resp);

            });
        }


    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    '$httpParamSerializer'
];

export default Controller ;
