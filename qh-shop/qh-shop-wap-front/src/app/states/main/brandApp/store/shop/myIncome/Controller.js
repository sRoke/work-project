import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $http = _$http;
        $scope.brandAppId=$stateParams.brandAppId;
        $scope.storeId=$stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId+"/shopAccount",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;
                // console.log('$scope.data', $scope.data);

            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getInfo();
        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            // } else {
            //     history.back();
            // }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$http',
];

export default Controller ;
