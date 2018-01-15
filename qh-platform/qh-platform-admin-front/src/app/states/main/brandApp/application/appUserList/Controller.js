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
        console.log("====================", $stateParams.type);
        $scope.STAFF_GROUP_C = authService.hasAuthor("STAFF_GROUP_C");    //增

        console.log('$scope.STAFF_GROUP_C',$scope.STAFF_GROUP_C);

        $scope.STAFF_GROUP_U = authService.hasAuthor("STAFF_GROUP_U");    //改
        $scope.STAFF_GROUP_R = authService.hasAuthor("STAFF_GROUP_R");    //读
        $scope.STAFF_GROUP_D = authService.hasAuthor("STAFF_GROUP_D");    //删



        $scope.curPage = 1;

        $scope.appType = $stateParams.type;

        if($stateParams.type == "QH_PLATFORM"){
            console.log('平台支撑系统')
            $scope.urlPath = conf.apiPath + "/staffGroup";
        }else if($stateParams.type == "QH_MALL"){
            console.log('微商城')
        }else if($stateParams.type == "QH_AGENCY"){
            console.log('经销系统');
            $scope.urlPath = conf.agencyPath + '/brandApp/'+$scope.brandAppId+'/staffGroup';
        }


        $scope.pageChanged = function (curPage) {
            $http({
                method: "get",
                url: $scope.urlPath,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId.constructor,
                },
                params: {
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                }
            }).success(function (resp) {
                console.log(resp.data);
                $scope.data = resp.data;
            });
        };
        $scope.pageChanged();

        $scope.delAppUser = function (id) {
            alertService.confirm(null, "确定删除该角色？", "温馨提示", "取消", "确认").then(function (data) {
                if (data) {
                    $http({
                        method: "delete",
                        url: $scope.urlPath + "/"+id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId.constructor,
                        },
                        params: {}
                    }).success(function (resp) {
                        $scope.pageChanged();
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