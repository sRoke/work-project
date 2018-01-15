import conf from "../../../../../conf";


var $scope,
    $http,
    $state,
    Upload,
    $templateCache,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    authService,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _Upload,
                _$templateCache,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        Upload = _Upload;
        authService = _authService;
        $stateParams = _$stateParams;
        $templateCache = _$templateCache;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId);
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.choose = $stateParams.choose;
        $scope.from = $stateParams.from;
        $scope.editStatus = $stateParams.editStatus;
        $scope.id = $stateParams.id;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
        }
        else {
            $scope.skuData = {};
        }
        // console.log('  $scope.jsons', $scope.skuData);



        var tplUrl = "tpl.html";
        $scope.tplUrl = tplUrl;
        $templateCache.put(tplUrl, $scope.skuData.detail);



        
        
        $scope.finish = function () {
            var json = angular.toJson($scope.skuData);
            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {id: $scope.id, skuData: json, status: 'add'})
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'edit',
                    editStatus: $scope.editStatus
                })
            }
        };

        /*返回上级*/
        $scope.fallbackPage = function () {

            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {id: $scope.id, skuData: json, status: 'add'})
            } else if ($scope.from == 'itemEdit') {
                $state.go("main.brandApp.store.itemManage.itemEdit", {id: $scope.id, skuData: json, status: 'add'})
            }
            else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'Upload',
    '$templateCache',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope'
];

export default Controller ;
