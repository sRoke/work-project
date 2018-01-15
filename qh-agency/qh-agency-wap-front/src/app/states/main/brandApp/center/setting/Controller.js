import conf from "../../../../../conf";
import store from "store";

var $scope,
    loginService,
    $http,
    alertService,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope, _loginService,_$http, _alertService,_$stateParams,_$state,_$location) {
        $scope = _$scope;
        loginService = _loginService;
        $http=_$http;
        alertService=_alertService;
        $stateParams=_$stateParams;
        $state = _$state;
        $location = _$location;
        loginService.loginCtl(true,$location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.go = function (state) {
            $state.go(state);
        };
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
                    console.log(resp);
                    $scope.data=resp.data;
                }, function (resp) {
                    //error
                    console.log('error',resp);
                    //alertService.msgAlert("exclamation-circle", "验证码错误");
                }
            );
        };
       // $scope.getStatus();
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
               // history.back();
                $state.go("main.brandApp.center.main", null, {reload: true});
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$http',
    'alertService',
    '$stateParams',
    '$state',
    '$location',
];

export default Controller ;
