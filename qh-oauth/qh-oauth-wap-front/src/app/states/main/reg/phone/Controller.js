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
            if(resp.data.data.phone){
                location.href = $stateParams.backUrl;
            }else {
                console.log('用户信息未绑定手机号');
            }

        }, function (err) {
            console.log("ERROR", err)
        });

        console.log('backurl=========', $stateParams.backUrl);

        $scope.user = {};
        $scope.gitCodeTime = '获取验证码';
        $scope.lastTime = 0;
        $scope.getVCcode = function () {
            if ($scope.lastTime <= 0) {
                //手机号验证
                if (!(/^1[34578]\d{9}$/.test($scope.user.phone))) {
                    // alertService.error("手机号码有误，请重填!");
                    alertService.msgAlert('cancle', "手机号码有误，请重填!");
                    return false;
                }
                //倒计时
                $scope.lastTime = 60;
                $http({
                    method: "POST",
                    url: conf.oauthPath + "/sms/verifyCode/send",
                    params: {
                        phone: $scope.user.phone,
                        type: 'LOGIN'
                    },
                    transformRequest: $httpParamSerializer,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (resp) {
                        // alertService.error("");
                        alertService.msgAlert('cancle', "已发送");
                        $scope.gitCodeTime = $scope.lastTime + 's';
                        var gitCode = $interval(function () {
                            $scope.lastTime--;
                            $scope.gitCodeTime = $scope.lastTime + 's';
                            if ($scope.lastTime <= 0) {
                                $scope.gitCodeTime = '重新获取';
                                $interval.cancel(gitCode);
                            }
                        }, 1000)
                    }, function () {
                        //error
                        // alertService.error("");
                        alertService.msgAlert('cancle', "发送失败请重试!");
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
                if (resp.status == 200) {
                    location.href = $stateParams.backUrl;
                }
            }, function (err) {
                console.log("ERROR", err);
                // alertService.msgAlert('cancle','帐号密码错误')
                // alertService.error()
                alertService.msgAlert('cancle', err.data.message);
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
