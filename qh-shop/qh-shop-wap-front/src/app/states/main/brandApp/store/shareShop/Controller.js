import conf from "../../../../../conf";
import Clipboard from "clipboard"

import html2canvas from 'html2canvas';
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
        };
        $scope.getShop();

        //要将 canvas 的宽高设置成容器宽高的 2 倍       创建canvas备用
        $scope.canvas = document.createElement("canvas");
        $scope.getShortUrl = function (url) {
            $http({
                method: "post",
                url: conf.dwzApiPath,
                data: {
                    url:url,
                }
            }).then(function (resp) {
                var data = resp.data;
                // console.log('data', data);
                $scope.qrCodeKey = data.data;
                $scope.qrCodeUrl = conf.dwzUrlPath + $scope.qrCodeKey;
                // console.log('qrCodeUrl===',$scope.qrCodeUrl);
                // console.log('canvasSimple===',document.getElementById('img-wrap'));
                // console.log('canvasSimple222===',angular.element(document.getElementById('img-wrap')));
                $scope.canvas.width = angular.element(document.getElementById('img-wrap'))[0].clientWidth*2;
                $scope.canvas.height = angular.element(document.getElementById('img-wrap'))[0].clientHeight*2;
                $scope.canvas.style.width = angular.element(document.getElementById('img-wrap'))[0].clientWidth + "px";
                $scope.canvas.style.height = angular.element(document.getElementById('img-wrap'))[0].clientHeight + "px";
                var context = $scope.canvas.getContext("2d");
                //然后将画布缩放，将图像放大两倍画到画布上
                context.scale(2,2);
                setTimeout(function () {
                    html2canvas(document.getElementById('img-wrap'), {
                        canvas:$scope.canvas,
                        onrendered: function (canvas) {
                            // console.log('canvas', canvas);
                            // var ctx=canvas.getContext("2d");
                            // ctx.scale(2,2);
                            var dataUrl = canvas.toDataURL("image/jpeg", 1.0);
                            $scope.dataUrl = dataUrl;
                            var newImg = document.createElement("img");
                            newImg.src = dataUrl;
                            $scope.cancassuccess = true;
                            $scope.$digest()
                            // setTimeout(function () {
                            //     $scope.popularityList($scope.dataUrl);
                            // }, 100)
                        },
                        // width: 600,
                        // height: 998,
                        background: '#ba9f6e',
                        logging: true,
                        allowTaint: false,
                        useCORS:true,
                        // timeout: 5000,
                    });
                }, 0)
            });
        };




        $scope.getUserInfo = function () {                                        //获取用户信息
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + "/shopStaff/currentInfo",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.userInfo = resp.data.data;
                $scope.getShortUrl(conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/home');
            }, function (resp) {
            });
        };
        $scope.getUserInfo();







        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", null, {reload: true});
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
