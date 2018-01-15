import conf from "../../../../conf";
// import dialog from "!html-loader?minimize=true!./updateAddress.html";

var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    errorService,
    $filter,
    $templateCache,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _errorService,
                _$filter,
                _$templateCache,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        $filter = _$filter;
        $templateCache = _$templateCache;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.shopId = $stateParams.storeId;
        console.log('id', $stateParams.id);
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        var vm = this;
        $scope.activeNum = '1';
        $scope.changeTab = function (num) {
            $scope.activeNum = num;
        };
        //初始化接口
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.shopId + "/item/" + $stateParams.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(4422222222222222, resp.data.data);
                $scope.items = resp.data.data;
                var tplUrl = "tpl.html";
                $scope.tplUrl = tplUrl;
                $templateCache.put(tplUrl, resp.data.data.detail);
            }, function (resp) {
                //error
            });
        };
        $scope.getList();

        $scope.fallBack = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.basicData", {
                    brandAppId: $stateParams.brandAppId,
                    shopId: $stateParams.shopId
                }, {reload: true});
            } else {
                history.back();
            }
        }


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    'errorService',
    '$filter',
    '$templateCache',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
