import conf from "../../../../../conf";

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
            $scope.viewShow = data;
        });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.id = $stateParams.id;

        //获取微信id
        $scope.getWxInfo = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $stateParams.brandAppId,
                headers: {
                    "brandAppp-Id": $scope.brandAppId
                },
                params: {}
            }).success(function (resp) {
                // console.log(resp);
                if (resp.data.wxMpId && resp.data.wxComAppId) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    if (loginService.getAccessToken()) {
                        $scope.getMember();
                    }
                    // console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                }
            });
        };


        //获取会员信息
        $scope.getMember = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/member/" + $stateParams.id,
                params: {
                    wxComAppId: $rootScope.wxComAppId,
                    wxMpAppId: $rootScope.wxMpAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;


                $scope.member.realName= $scope.data.realName;
                $scope.member.phone= $scope.data.phone;
                $scope.member.phone1= $scope.data.contacts[0];
                $scope.member.phone2= $scope.data.contacts[1];


                console.log('$scope.data', $scope.data);

            }, function (resp) {
                //TODO 错误处理
            });
        };


        $scope.getWxInfo();

        ////////////////////////////////////

        //编辑保存切换
        $scope.isSave = false;
        $scope.changeStatus = function () {
            $scope.isSave = !$scope.isSave;
        };

        $scope.member = {};
        //编辑
        $scope.editSave = function () {

            ///brandApp/{brandAppId}op/{shopId}/member/{id}

            /*
             * /brandApp/{brandAppId}op/{shopId}/member/{id}
             　memberReq
             有　realName
             phone;
             contacts
             * */

            $scope.saveData = {
                contacts: []
            };

            $scope.saveData.realName = $scope.member.realName;
            $scope.saveData.phone = $scope.member.phone;
            $scope.saveData.contacts.push($scope.member.phone1);
            $scope.saveData.contacts.push($scope.member.phone2);

            console.log('$scope.saveData', $scope.saveData);
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/member/" + $stateParams.id,
                data: $scope.saveData,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('resp', resp);
                $scope.getMember();
                $scope.isSave = !$scope.isSave;
                $scope.member = {};
            }, function (resp) {
                //TODO 错误处理
            });
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.memberInfo", {id: $scope.id}, {reload: true})
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
