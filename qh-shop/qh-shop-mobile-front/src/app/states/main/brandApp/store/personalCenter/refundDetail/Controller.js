import conf from "../../../../../../conf";
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $rootScope,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.refundId = $stateParams.refundId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/refund/"+$scope.refundId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp---inf',resp.data.data);
                $scope.items=resp.data.data;
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.shop.shopMange", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
