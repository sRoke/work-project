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
        $scope.BRANDAPP_C = authService.hasAuthor("BRANDAPP_C");    //增
        $scope.BRANDAPP_R = authService.hasAuthor("BRANDAPP_R");    //读
        $scope.BRANDAPP_D = authService.hasAuthor("BRANDAPP_D");    //删
        $scope.BRANDAPP_L = authService.hasAuthor("BRANDAPP_L");    //商家应用付费记录

        $scope.curPage = 1;
        $scope.id = $stateParams.id;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "get",
                url: conf.apiPath  + "/brandApp",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                    brandComId:$stateParams.id,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
                $scope.curPage = $scope.data.number + 1;

                console.log('data',  $scope.data );
            });
        };
        $scope.pageChanged();
        $scope.delBusApplication = function (appId) {
            alertService.confirm(null, "确定删除该应用？", "温馨提示", "取消", "确认").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/"+appId,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        params: {}
                    }).then(function (resp) {
                        if (resp.status ==200){
                            $scope.pageChanged();
                        }
                    }, function (resp) {

                    });
                }
            });
        }
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