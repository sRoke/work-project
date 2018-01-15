import conf from "../../../../../conf"

var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
    alertService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _authService,
                _loginService,
                _alertService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);
        $scope.selectedIndex = 0;


        //获取基础信息
        $scope.page = function () {
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/"+ $stateParams.appId,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $scope.data = resp.data;
                    $scope.page2(resp.data.wxMpId);
                });
        };
        $scope.page();



        //获取绑定威信公众好信息
        $scope.page2 = function (id) {
            $http({
                method: "GET",
                url: conf.wx4jPath + "/wxCom/"+ conf.thirdWXId + '/mp/'+id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {}
            }).success(function (resp) {
                $scope.data2 = resp.data;
            });
        };


        //返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
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
    '$log',
    '$filter',
    'authService',
    'loginService',
    'alertService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;