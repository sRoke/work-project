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
        loginService.loginCtl(true,$location.absUrl());

        $scope.go = function (state) {
            $state.go(state);
        };
        //获取账户money
        $http({
            method: 'GET',
            url:conf.apiPath + '/brandApp/'+$scope.brandAppId+'/partner/123456789/partnerAccount/info',
            params: {
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            console.log(resp.data.data);
            $scope.count = resp.data.data;
        });
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
               // history.back();
                $state.go("main.brandApp.center.main", null, {reload: true});
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
