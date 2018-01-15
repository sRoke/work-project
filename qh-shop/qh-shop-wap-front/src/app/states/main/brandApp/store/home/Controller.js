import conf from "../../../../../conf";
import Clipboard from "clipboard"
var $scope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
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
                _$log,
                _$mdBottomSheet,
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
        $log = _$log;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId).then(function (data) {
            // console.log('55555555555555', data)
            $scope.viewShow = data;
        });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;

        // console.log('$scope.storeId', $scope.storeId);
        var clipboard = new Clipboard('.btn', {});
        clipboard.on('success', function (e) {
            // console.log(e);
            // console.info('Action:', e.action);
            // console.info('Text:', e.text);
            // console.info('Trigger:', e.trigger);
            document.getElementById('copyCode').innerHTML = '复制成功';
        });
        clipboard.on('error', function (e) {
            // console.error('Action:', e.action);
            // console.error('Trigger:', e.trigger);
            document.getElementById('copyCode').innerHTML = '复制失败，请长按复制';
        });


        // /brandApp/{brandAppId}/shop/{shopId}


        $scope.getShop = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {

                $scope.data = resp.data.data;
                // console.log('$scope.data', $scope.data);
                $scope.day = ($scope.data.expireDate - $scope.data.curDate) / (24 * 60 * 60 * 1000);
                if ($scope.day <= 7 && $scope.day > 0) {
                    $scope.time1 = true;
                    $scope.time2 = false;
                }
                ;
                if ($scope.day <= 0) {
                    $scope.time1 = false;
                    $scope.time2 = true;
                }
            }, function (resp) {
                //TODO 错误处理
            });

            ///brandApp/{brandAppId}/shop/{shopId}/getNum
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/getNum",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('resp', resp);
                $scope.numData = resp.data.data;
            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getShop();
        $scope.closeTs = function () {
            $scope.time1 = false;
            $scope.time2 = false;
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
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
