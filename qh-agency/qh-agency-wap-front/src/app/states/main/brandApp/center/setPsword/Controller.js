import conf from "../../../../../conf";
import store from "store";

var $scope,
    loginService,
    alertService,
    $http,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope, _loginService,_alertService,_$http,_$stateParams, _$state,_$location) {
        $scope = _$scope;
        loginService = _loginService;
        alertService=_alertService;
        $http=_$http;
        $stateParams=_$stateParams,
        $state = _$state;
        $location = _$location;
        loginService.loginCtl(true,$location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.about = $stateParams.about;
        $scope.id = $stateParams.id;
        $scope.count = $stateParams.count;
        $scope.go = function (state) {
            $state.go(state);
        };

        //验证密码格式
        $scope.formatPassword =function (password) {
            if(!/^\d{6}$/.test(password)){
                alertService.msgAlert("exclamation-circle", "请输入6位数字密码").then(
                    function (data) {
                        if (data) {
                        }
                    }
                );
            }
        };
        $scope. formatPswd =function (password) {
            if(!/^\d{6}$/.test(password)){
                alertService.msgAlert("exclamation-circle", "请输入6位数字密码").then(
                    function (data) {
                        if (data) {
                        }
                    }
                );
            }else if(password!=$scope.psword){
                $scope.confirmPswd='';
                alertService.msgAlert("exclamation-circle", "两次密码不一致").then(
                    function (data) {
                        if (data) {
                        }
                    }
                );
            }
        };
        $scope.getCheck=function(){
           // console.log($scope.confirmPswd);
            if($scope.psword==undefined){
                alertService.msgAlert("exclamation-circle", "密码不能为空");
                return false;
            }else if($scope.confirmPswd==undefined){
                alertService.msgAlert("exclamation-circle", "确认密码不能为空");
                return false;
            }else if(!/^\d{6}$/.test($scope.confirmPswd) || !/^\d{6}$/.test($scope.psword) ){
                alertService.msgAlert("exclamation-circle", "请输入6位数字密码");
                return false;
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount",
                params: {
                    oldPassword:$scope.psword,
                    newPassword:$scope.confirmPswd
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    console.log(resp);
                   if(resp.data.status==200){
                       // $state.go("main.brandApp.center.setting", null, {reload: true});
                       if($scope.about=='setting'){
                           $state.go("main.brandApp.center.setting", null, {reload: true});
                       }else if($scope.about=='withdraw'){
                           $state.go("main.brandApp.wallet.txPassword", {count:$scope.count}, {reload: true});
                       }else{
                           $state.go("main.brandApp.wallet.payPassword", {id:$scope.id}, {reload: true});
                       };
                   }
                }, function (resp) {
                    //error
                    console.log('error',resp);
                    alertService.msgAlert("exclamation-circle", resp.data.message);
                }
            );
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
    '$stateParams',
    '$state',
    '$location',
];

export default Controller ;
