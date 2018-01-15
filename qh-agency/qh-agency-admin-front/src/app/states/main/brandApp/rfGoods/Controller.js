import conf from "../../../../conf";
import laydate from "layui-laydate";
var $scope,
    $http,
    $stateParams,
    loginService,
    $filter,
    authService;

class Controller {
    constructor(_$scope, _$http, _loginService,_$filter, _authService, _$stateParams) {
        $scope = _$scope;
        $stateParams = _$stateParams;
        loginService = _loginService;
        $http = _$http;
        $filter=_$filter;
        authService = _authService;
        loginService.loginCtl(true);

        $scope.currentNavItem = 'all';
        //权限相关
        $scope.REFUND_C = authService.hasAuthor("REFUND_C");    //退款操作
        $scope.REFUND_R = authService.hasAuthor("REFUND_R");    //查看

//tab切换

        $scope.brandAppId = $stateParams.brandAppId;
        var vm = this;
        $scope.data = {};
        $scope.search = {};
        $scope.curPage=1;

        //时间选择器
        laydate.render({
            elem: '#test1', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.startDate = value;
            }

        });
        laydate.render({
            elem: '#test2', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.endDate = value;
            }
        });



        $scope.pageChanged = function (curPage) {
            $scope.startTime=$filter('date')($scope.startDate,'yyyy-MM-dd');
            $scope.endTime=$filter('date')($scope.endDate,'yyyy-MM-dd');
            if ($scope.data.number > $scope.data.totalPages) {
                return;
            }
            $http({
                method:"GET",
                url:conf.apiPath+"/brandApp/"+$scope.brandAppId+"/refund",
                params: {
                    page:curPage?$scope.curPage:$scope.curPage-1,
                    size: conf.pageSize,
                    status: $scope.refundStatus,
                    startDate: $scope.startTime,
                    endDate:$scope.endTime,
                    type:'ITEM',
                    keyWord: $scope.reason,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log(resp);
                $scope.data=resp.data;
                $scope.curPage = resp.data.number + 1;
                $scope.refundStatusEnumMap = resp.data.refundStatusEnumMap;
                $scope.items=resp.data.content;
                // $scope.refundTypeEnumMap = resp.data.refundTypeEnumMap;
                // $scope.refundReasonEnumMap = resp.data.refundReasonEnumMap;
            });
        };
        $scope.pageChanged();
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
    '$scope', '$http', 'loginService', '$filter','authService', '$stateParams'
];

export default Controller ;
