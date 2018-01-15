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
        $scope.BRANDAPP_U = authService.hasAuthor("BRANDAPP_U");    //改
        $scope.BRANDAPP_R = authService.hasAuthor("BRANDAPP_R");    //读
        $scope.BRANDAPP_D = authService.hasAuthor("BRANDAPP_D");    //删
        $scope.BRANDAPP_Y = authService.hasAuthor("BRANDAPP_Y");    //应用
        $scope.BRANDAPP_R = authService.hasAuthor("BRANDAPP_R");    //应用


        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "get",
                url: conf.apiPath + "/brandCom/search",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                },
                params: {
                    page: curPage ? curPage-1 : $scope.curPage - 1,
                    size: conf.pageSize,
                    sort:['dateCreated,desc'],
                    q:$scope.keyWords,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
            });
        };
        $scope.pageChanged();

        $scope.delBusiness = function (id) {
            alertService.confirm(null, "确定删除该商家？", "温馨提示", "取消", "确认").then(function (data) {
                    if (data) {
                        $http({
                            method: "DELETE",
                            url: conf.apiPath + "/brandCom/" + id,
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