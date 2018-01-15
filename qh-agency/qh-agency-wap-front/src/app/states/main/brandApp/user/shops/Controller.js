import conf from "../../../../../conf";
import weui from "weui.js";
var $scope,
    alertService,
    $mdDialog,
    $http,
    loginService,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope, _alertService, _$mdDialog, _$http, _loginService, _$stateParams, _$state, _$location) {
        $scope = _$scope;
        alertService = _alertService;
        $mdDialog = _$mdDialog;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $state = _$state;
        $location = _$location;

        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.patnerId = $stateParams.patnerId;

        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.patnerId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    $scope.info = resp.data.data;
                }, function () {

                }
            );
        };
        $scope.getData();

        //地址选择控件
        $scope.openAddress = function () {
            weui.picker($scope.provinces, $scope.citys, $scope.areas, {
                defaultValue: [$scope.provinces[0].ProID, $scope.citys[0].CityID, $scope.areas[0].CityID],
                onChange: function (result) {
                },
                onConfirm: function (result) {
                },
                id: 'multiPickerBtn'
            });
        };


        //判断编辑页面还晒查看页面
        $scope.edit = {};
        $scope.edit.shopName = '';
        $scope.viewStatus = true; //查看页面
        $scope.edit = function () {
            $scope.edit.shopName = '';
            $scope.edit.realName = '';
            $scope.edit.phone = '';
            $scope.viewStatus = false; //编辑页面
            $scope.placeholder = $scope.info;

        };


        //验证手机号格式
        $scope.formatPhone = function (number) {
            if (!(/^1[34578]\d{9}$/.test(number))) {
                alertService.msgAlert("exclamation-circle", "请输入正确的手机号");
                return false;
            } else {
                return true;
            }
        };


        $scope.save = function () {
            if ($scope.edit.phone) {
                if (!(/^1[34578]\d{9}$/.test($scope.edit.phone))) {
                    alertService.msgAlert("exclamation-circle", "请输入正确的手机号");
                    return false;
                }
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.info.id,
                data: {
                    realName: $scope.edit.realName == ''? $scope.info.realName : $scope.edit.shopName,
                    shopName: $scope.edit.shopName == ''? $scope.info.shopName : $scope.edit.shopName,
                    phone: $scope.edit.phone == ''? $scope.info.phone : $scope.edit.shopName,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (data) {
                    $scope.getData();
                    $scope.viewStatus = true;
                }, function () {

                }
            );


        };


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
    'alertService',
    '$mdDialog',
    '$http',
    'loginService',
    '$stateParams',
    '$state',
    '$location',
];

export default Controller ;
