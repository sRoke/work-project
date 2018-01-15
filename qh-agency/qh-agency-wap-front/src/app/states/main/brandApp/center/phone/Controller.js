import conf from "../../../../../conf";
import store from "store";

var $scope,
    loginService,
    alertService,
    $http,
    $interval,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope, _loginService,_alertService,_$http,_$interval,_$stateParams, _$state,_$location) {
        $scope = _$scope;
        loginService = _loginService;
        alertService=_alertService;
        $interval=_$interval;
        $http=_$http;
        $stateParams=_$stateParams;
        $state = _$state;
        $location = _$location;
        loginService.loginCtl(true,$location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.about=$stateParams.about;
        $scope.id=$stateParams.id;
        $scope.count=$stateParams.count;
        $scope.go = function (state) {
            $state.go(state);
        };
        //$scope.first=true;
        $scope.second=true;
        $scope.getPhone=function(){
            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/info",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                }
            }).then(function (resp) {
               // console.log(111,resp.data.data.phone);
                $scope.checkPhone=resp.data.data.phone;
                }, function () {
                    //error
                console.log('error',resp);
                }
            );
        };
        $scope.getPhone();

        //验证手机号格式
        $scope.formatPhone = function (number) {
            if (!(/^1[34578]\d{9}$/.test(number))) {
                alertService.msgAlert("exclamation-circle", "请输入正确的手机号");
                return false;
            } else {
                return true;
            }
        };
        $scope.checkCode=(vcCode)=>{
            //验证码验证
            if(vcCode.length==6){
               $scope.second=false;
            }else{
                $scope.second=true;
            };
        };
        $scope.gitCodeTime = '获取验证码';
        $scope.lastTime = 0;
        $scope.getVCcode = function () {
            if ($scope.lastTime <= 0) {
                //手机号验证
                if (!(/^1[34578]\d{9}$/.test($scope.checkPhone))) {
                    alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                    return false;
                }
                //倒计时
                $scope.lastTime = 60;
                $http({
                    method: "GET",
                    url: conf.oauthPath + "/api/user/sendVerifyCode",
                    params: {
                        phone: $scope.checkPhone
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                    }
                }).then(function (resp) {
                        //设置下一步点击
                        //$scope.first=false;
                        alertService.msgAlert("success", "已发送");
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
                        alertService.msgAlert("exclamation-circle", "发送失败请重试!");
                        $scope.lastTime = 0;
                    }
                );
            }
        };
        //密码跳转判断
        $scope.dataStatus='';
        $scope.getStatus=function(){
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount",
                params: {
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                $scope.dataStatus=resp.data.status;
                console.log($scope.dataStatus);
                }, function (resp) {
                    //error
                    console.log('error',resp);
                    //alertService.msgAlert("exclamation-circle", "验证码错误");
                }
            );
        };
        $scope.getStatus();

        //下一步
        $scope.next = function () {
            //手机号验证
            if (!(/^1[34578]\d{9}$/.test($scope.checkPhone))) {
                alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                return false;
            }
            //验证码验证
            if (!$scope.vcCode) {
                alertService.msgAlert("exclamation-circle", "请输入验证码!");
                return false;
            }
            //--------------------------------------------------------------------------验证验证码
            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/bindPhone",
                params: {
                    code: $scope.vcCode,
                    phone: $scope.checkPhone
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    // $scope.apply.phone = $scope.firstStep.phone;
                    // $scope.page();
                    if($scope.dataStatus=='10032'){
                        $state.go("main.brandApp.center.setPsword", {about:$scope.about,id:$scope.id,count:$scope.count}, {reload: true});
                    }else{
                        $state.go("main.brandApp.center.modifyPassword", {about:$scope.about,id:$scope.id,count:$scope.count}, {reload: true});
                    };
                }, function (resp) {
                    //error
                     console.log(123456,resp.data.message);
                    alertService.msgAlert("exclamation-circle", "验证码错误");
                    $scope.vcCode = '';

                }
            );
        };
        $scope.exit=function () {                 //测试登出按钮
            if (loginService.getAccessToken()) {
                loginService.setAccessToken(null);
                alert(loginService.getAccessToken());
                store.remove(conf.token);
                jso.wipeTokens();
                alert('AccessToken清除');
            }else{
               alert('AccessToken不存在')
            }
        };


        // 回退页面
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.index", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    'alertService',
    '$http',
    '$interval',
    '$stateParams',
    '$state',
    '$location',
];

export default Controller ;
