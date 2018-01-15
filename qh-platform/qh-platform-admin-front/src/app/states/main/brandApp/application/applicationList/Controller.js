import conf from "../../../../../conf";

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
        $scope.STAFF_GROUP_R = authService.hasAuthor("STAFF_GROUP_R");    //角色
        $scope.APPLICATION_U = authService.hasAuthor("APPLICATION_U");    //改
        $scope.APPLICATION_R = authService.hasAuthor("APPLICATION_R");    //读
        // $scope.ITEM_D = authService.hasAuthor("ITEM_D");    //删



        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "get",
                url: conf.apiPath + "/app",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
                $scope.curPage = $scope.data.number + 1;

                console.log('data',  $scope.data );
            });
        };
        $scope.pageChanged();

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