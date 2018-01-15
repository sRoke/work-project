import conf from "../../../../../../conf";

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

        // 先检查是否已经登录

        // $http({
        //     method: "GET",
        //     url: conf.oauthPath + "/s/user/info"
        // }).then(function (resp) {
        //
        //     console.log("SUCCESS - ", resp)
        //
        // }, function (resp) {
        //     console.log("ERROR", resp);
        //
        //
        // });


        $scope.clientId = "CLIENT_ID_qh-agency-server";
        $scope.redirectUri = "https://kingsilk.net/404?a=aa&b=bb#/xxx/yyy?c=cc&d=dd";
        $scope.scope = "LOGIN";
        $scope.state = "randomStrXxx";

        $scope.oldImplicitAuthorize = function(){
            $http({
                method: "POST",
                url: conf.rootPath + "/oauth/authorize",
                params: {
                    response_type: "token",
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
        };

        $scope.goImplicitAuthorize = function () {
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/oauth/authorize",
                data: {
                    response_type: "token",
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
        };

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
