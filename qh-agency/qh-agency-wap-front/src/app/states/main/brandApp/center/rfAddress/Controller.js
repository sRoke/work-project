import conf from "../../../../../conf";

var $scope,
    $state,
    loginService,
    $location,
    $http,
    $stateParams;
class Controller {
    constructor(_$scope, _$state,_loginService,_$location,_$http,_$stateParams) {
        $scope = _$scope;
        $state = _$state;
        loginService = _loginService;
        $location = _$location;
        $http = _$http;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;
        loginService.loginCtl(true,$location.absUrl());

        $scope.go = function (state) {
            $state.go(state);
        };
        //获取地址
        $http({
            method: 'GET',      ///brandApp/{brandAppId}/partner/{partnerId}/addr/{id} get
            url:conf.apiPath + '/brandApp/'+$scope.brandAppId+'/partner/123456/addr/'+$scope.id,
            params: {
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            console.log(11111111111111,resp);
            $scope.data=resp.data.data;
        });







        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
               // history.back();
                $state.go("main.brandApp.center.userInfo", null, {reload: true});
            }
        };
        // /*静态页面用，，js联调后可删除*/
        // $scope.comments = new Array(6);
    }
}

Controller.$inject = [
    '$scope',
    '$state',
    'loginService',
    '$location',
    '$http',
    '$stateParams',
];

export default Controller ;
