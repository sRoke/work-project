import conf from "../../../../../conf";
var $scope,
    $state,
    $stateParams,
    loginService,
    alertService,
    $http,
    $location;

class Controller {
    constructor(_$scope, _$state, _$stateParams,_loginService,_alertService,_$http,_$location) {
        $scope = _$scope;
        $state = _$state;
        $stateParams = _$stateParams;
        loginService = _loginService;
        alertService=_alertService;
        $http=_$http;
        $location = _$location;
        $scope.id=$stateParams.id;
        $scope.brandAppId=$stateParams.brandAppId;
        loginService.loginCtl(true,$location.absUrl());
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
                    $scope.data=resp.data.status;
                    console.log(resp, $scope.data);
                    if($scope.data==10031){
                        $scope.content='忘记密码？';
                    }else{
                        $scope.content='没有密码，去设置';
                    }
                }, function (resp) {
                    //error
                    console.log('error',resp);
                    //alertService.msgAlert("exclamation-circle", "验证码错误");
                }
            );
        };
        $scope.getStatus();
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
        $scope.agreePay=function(){
            if(!/^\d{6}$/.test($scope.pswd)){
                alertService.msgAlert("exclamation-circle", "请输入6位数字密码");
                return false;
            }
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount/"+$scope.id,
                params: {
                    password:$scope.pswd
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    console.log(resp);
                    if(resp.data.status==200){
                        $state.go("main.brandApp.unionOrder",null,{reload:true})
                    }else{
                         alertService.msgAlert("exclamation-circle", "密码错误");
                    }
                }, function (resp) {
                    //error
                    //console.log('error',resp);
                    //
                }
            );
        };






        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$state',
    '$stateParams',
    'loginService',
    'alertService',
    '$http',
    '$location',
];

export default Controller ;
