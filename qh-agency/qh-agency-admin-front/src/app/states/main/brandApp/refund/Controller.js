import conf from "../../../../conf";

var $scope,
    $http,
    $stateParams,
    loginService,
    authService;

class Controller {
    constructor(_$scope, _$http, _loginService, _authService, _$stateParams) {
        $scope = _$scope;
        $stateParams = _$stateParams;
        loginService = _loginService;
        $http = _$http;
        authService = _authService;
        loginService.loginCtl(true);

        $scope.currentNavItem = 'all';
        //权限相关
        // $scope.REFUND_C = authService.hasAuthor("REFUND_C");    //退款操作
        $scope.REFUND_R = authService.hasAuthor("REFUND_R");    //查看

//tab切换

        $scope.brandAppId = $stateParams.brandAppId;
        var vm = this;
        $scope.data = {};
        $scope.search = {};
        $scope.data.number = $scope.data.number ? $scope.data.number : 1;
        $scope.pageChanged = function (curPage) {
            if ($scope.data.number > $scope.data.totalPages) {
                return;
            }
            if (curPage == null) {
                curPage = $scope.data.number - 1;
            }
            $http.get(conf.apiPath + "/refund/page", {
                params: {
                    curPage: 1,
                    pageSize: conf.pageSize,
                    status: $scope.search.status,
                    type: $scope.search.type,
                    reason: $scope.search.reason,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log(resp);
                $scope.data = resp.data.recPage;
                $scope.data.number = resp.data.recPage.number + 1;
                $scope.refundStatusEnumMap = resp.data.refundStatusEnumMap;
                $scope.refundTypeEnumMap = resp.data.refundTypeEnumMap;
                $scope.refundReasonEnumMap = resp.data.refundReasonEnumMap;
            });
        };
        $scope.pageChanged(1);
        //rmy     tab以及退款类型联动
        $scope.activeNum = 'all';
        $scope.search.type='all';
        $scope.btnClick = function (btn) {
            $scope.activeNum = btn;
            $scope.search.type=btn;
            console.log($scope.activeNum);
        };
    }
}

Controller.$inject = [
    '$scope', '$http', 'loginService', 'authService', '$stateParams'
];

export default Controller ;
