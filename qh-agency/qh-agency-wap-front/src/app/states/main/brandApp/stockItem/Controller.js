
import conf from "../../../../conf";

var $scope,
    $http,
    $state,
    alertService,
    $log,
    $filter,
    $mdBottomSheet,
    $templateCache,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _alertService,
                _$log,
                _$filter,
                _$mdBottomSheet,
                _$templateCache,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        alertService = _alertService;
        $stateParams = _$stateParams;
        $filter = _$filter;
        $mdBottomSheet = _$mdBottomSheet;
        $templateCache = _$templateCache;
        $location = _$location;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        let vm = this;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.itemId = $stateParams.itemId;
        //联调api,获取数据
        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/123456789/skuStore/"+$scope.itemId,
                params: {
                    // itemId: $scope.itemId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.data = resp.data.data;
                }, function () {

                }
            );
        };
        $scope.getData();








        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'alertService',
    '$log',
    '$filter',
    '$mdBottomSheet',
    '$templateCache',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
];

export default Controller ;
