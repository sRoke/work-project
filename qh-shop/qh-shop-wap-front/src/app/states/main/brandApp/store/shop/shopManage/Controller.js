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
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };

        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data=resp.data.data;
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        $scope.buyPrice = {};
        $scope.getPrice = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/sysConf/shopPrice',
                // url: conf.apiPath + '/home',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $stateParams.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-----------------------', resp);
                $scope.buyPrice = resp.data.data.monthly;
            }, function (error) {

            });
        };
        $scope.getPrice();
        /*返回上级*/
        $scope.fallbackPage = function () {
          $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId},{storeId:$stateParams.storeId}, {reload: true});
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
