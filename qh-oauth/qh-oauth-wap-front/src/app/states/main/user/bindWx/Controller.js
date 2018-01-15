import conf from "../../../../conf";

var $scope,
    $rootScope,
    $http,
    $state,
    loginService,
    alertService,
    $timeout,
    $stateParams,
    $location,
    $httpParamSerializer,
    $interval;
class Controller {
    constructor(_$scope,
                _$rootScope,
                _$http,
                _$state,
                _loginService,
                _alertService,
                _$timeout,
                _$stateParams,
                _$location,
                _$httpParamSerializer,
                _$interval) {
        $scope = _$scope;
        $http = _$http;
        $state = _$state;
        $timeout = _$timeout;
        alertService = _alertService;
        loginService = _loginService;
        $interval = _$interval;
        $stateParams = _$stateParams;
        $location = _$location;
        $rootScope = _$rootScope;
        $httpParamSerializer = _$httpParamSerializer;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        // 先检查是否已经登录
        $scope.bindSessionWxMp = function(){
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/user/bind/sessionWxMp"
            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                location.href =  $stateParams.backUrl;
            }, function (err) {
                console.log("ERROR", err)
            });
        };


        $http({
            method: "GET",
            url: conf.oauthPath + "/s/user/info"
        }).then(function (resp) {
            console.log("SUCCESS - ", resp);
            if(resp.data.data.phone){
                $scope.bindSessionWxMp();
            }else {
                $state.go("main.reg.phone", {
                    backUrl:$location.absUrl(),
                });
            }
        }, function (err) {
            console.log("ERROR", err);
            // history.back();
        });
    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    '$http',
    '$state',
    'loginService',
    'alertService',
    '$timeout',
    '$stateParams',
    '$location',
    '$httpParamSerializer',
    '$interval'
];

export default Controller ;
