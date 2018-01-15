import conf from "../../../../../../conf";

var $scope,
    $rootScope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    alertService,
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
                _alertService,
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
        alertService = _alertService;
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

        $http({
            method: "GET",
            url: conf.oauthPath + "/s/user/info"
        }).then(function (resp) {
            console.log("SUCCESS - ", resp)
        }, function (err) {
            console.log("ERROR", err)
        });


        $scope.login = function () {
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/login/pwd",
                data: {
                    username: $scope.username,
                    password: $scope.password
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }

            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                // $state.go("main.oauth.authorize", {
                //     loginType: $stateParams.loginType,
                //     client_id:$stateParams.client_id,
                //     redirect_uri:$stateParams.redirect_uri,
                //     scope:$stateParams.scope,
                //     state:$stateParams.state,
                // });
            }, function (err) {
                console.log("ERROR", err)
                // alertService.msgAlert('cancle','帐号密码错误')
                // alertService.msgAlert('ag-exclamation-circle','帐号密码错误')
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
    'alertService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    '$httpParamSerializer'
];

export default Controller ;
