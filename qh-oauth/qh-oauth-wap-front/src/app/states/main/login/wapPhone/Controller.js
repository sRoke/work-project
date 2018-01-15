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
        $scope.loginType = 'phonePwd';
        $http({
            method: "GET",
            url: conf.oauthPath + "/s/user/info"
        }).then(function (resp) {
            console.log("SUCCESS - ", resp);
            location.href =  $stateParams.backUrl;
        }, function (err) {
            console.log("ERROR", err)
        });
        $scope.user = {};
        $scope.gitCodeTime = '获取验证码';
        $scope.lastTime = 0;
        $scope.getVCcode = function () {
            if ($scope.lastTime <= 0) {
                //手机号验证
                if (!(/^1[34578]\d{9}$/.test($scope.user.phone))) {
                    // alertService.error("手机号码有误，请重填!");
                    alertService.msgAlert('cancle',"手机号码有误，请重填!");
                    return false;
                }
                //倒计时
                $scope.lastTime = 60;
                $http({
                    method: "POST",
                    url: conf.oauthPath + "/sms/verifyCode/send",
                    params: {
                        phone: $scope.user.phone,
                        type:'LOGIN'
                    },
                    transformRequest: $httpParamSerializer,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (resp) {

                        alertService.msgAlert('cancle',"已发送!");
                        $scope.gitCodeTime =$scope.lastTime + 's';
                        var gitCode = $interval(function () {
                            $scope.lastTime--;
                            $scope.gitCodeTime =$scope.lastTime + 's';
                            if ($scope.lastTime <= 0) {
                                $scope.gitCodeTime = '重新获取';
                                $interval.cancel(gitCode);
                            }
                        }, 1000)
                    }, function () {
                        //error
                        // alertService.error("发送失败请重试!");
                        alertService.msgAlert('cancle',"发送失败请重试!");
                        $scope.lastTime = 0;
                    }
                );
            }
        };
        $scope.login = function () {
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/login/phone",
                data: {
                    phone: $scope.user.phone,
                    code: $scope.user.pwd
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }

            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                if(resp.status == 200){
                    location.href =  $stateParams.backUrl;
                }
            }, function (err) {
                console.log("ERROR", err);
                alertService.msgAlert('cancle',err.data.message);
                // alertService.error(err.data.message)
            });
        }


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
