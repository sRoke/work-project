import conf from "../../../../conf";
import moment from "moment";
// import 'angular-bootstrap';
import laydate from "layui-laydate";

var $scope,
    authService,
    loginService,
    $stateParams,
    $filter,
    $http;

class Controller {
    constructor(_$scope, _$http, _loginService, _authService, _$filter,_$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $filter=_$filter;
        $stateParams = _$stateParams;
        loginService = _loginService;
        authService = _authService;
        var vm = this;
        $scope.curPage = 1;
        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_C = authService.hasAuthor("ORDER_C");    //改价
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.data = {};
        $scope.statusMap = {};
        $scope.search = {};
        // $scope.data.number = $scope.data.number?$scope.data.number:1;
        // $scope.data.totalPages = $scope.data.totalPages?$scope.data.totalPages:1;
        // var currentMonth = moment().month() + 1;
        // var currentYear = moment().year();
        // var lastMonth = currentMonth - 3;
        // lastMonth = (currentMonth - 3) < 10 ? '0' + lastMonth : lastMonth;
        // var startDate = currentYear + '-' + lastMonth + '-01';
        // $scope.search.startDate = moment(startDate).format('YYYY-MM-DD');
        // $scope.search.endDate = moment().format('YYYY-MM-DD');

        // $scope.pageChanged = function (curPage) {
        //     if ($scope.data.number >$scope.data.totalPages){
        //         return;
        //     };
        //
        //     if (curPage == null) {
        //         curPage = $scope.data.number-1;
        //         console.log($scope.data.number)
        //     }
        //     $scope.search.startDate = moment($scope.search.startDate).format('YYYY-MM-DD');
        //     $scope.search.endDate = moment($scope.search.endDate).format('YYYY-MM-DD');
        //     $http.get(conf.apiPath + "/order/page", {
        //         params: {
        //             curPage: 1,
        //             pageSize: conf.pageSize,
        //             keyWord: $scope.search.keyword,
        //             status: $scope.search.status,
        //             // startDate: $scope.search.startDate,
        //             // endDate: $scope.search.endDate,
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //             "brandApp-Id": $scope.brandAppId
        //         },
        //     }).success(function (resp) {
        //         $scope.data = resp.data.recPage;
        //         $scope.data.number = resp.data.recPage.number + 1;
        //         $scope.orderStatusEnumMap = resp.data.orderStatusEnumMap;
        //         $scope.dataCountMap = resp.data.dataCountMap;
        //     });
        // };
        // $scope.pageChanged(0);
        //
        // var showList = {
        //     'UNPAYED': true,
        //     'UNSHIPPED': true,
        //     'UNRECEIVED': true,
        //     'FINISHED': true,
        // };
        // $scope.currentNavItem = "all";
        // $scope.look = function () {
        //     if ($scope.search.status === "" || showList[$scope.search.status] !== true) {
        //         $scope.currentNavItem = "all";
        //     } else {
        //         $scope.currentNavItem = "" + $scope.search.status;
        //     }
        //     $scope.pageChanged(0);
        // };
        //
        // $scope.export = function (id) {
        //     window.location.href = conf.apiPath + "/order/export?id=" + id
        // };
        //时间选择器
        laydate.render({
            elem: '#test1', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.search.startDate = value;
            }

        });
        laydate.render({
            elem: '#test2', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.search.endDate = value;
            }
        });
        //rmy 07-21
        $scope.pageChanged = (curPage) => {
            //console.log($scope.search.startDate);
            $scope.startTime=$filter('date')($scope.search.startDate,'yyyy-MM-dd');
            $scope.endTime=$filter('date')($scope.search.endDate,'yyyy-MM-dd');
            $http({
                method:"GET",
                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/order",
                params:{
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                    keyWord: $scope.search.keyword,
                    status: $scope.search.status === "all" ? null : $scope.search.status,
                    startDate: $scope.startTime,
                    endDate:$scope.endTime,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
               // console.log(response.data);
                $scope.orderStatus = response.data.orderStatusEnumMap;
                $scope.items = response.data.content;
                $scope.statusNum = response.data.dataCountMap; //取各状态的数量对象
                $scope.page = response.data;
                $scope.curPage = response.data.number + 1;
                $scope.activeNum = $scope.search.status;
            });
        };
        $scope.pageChanged();
        // $scope.btnClick = function (btn) {
        //     for (let i = 0; i < $scope.btnData.length; i++) {
        //         $scope.btnData[i].check = false;
        //         if (btn.name == $scope.btnData[i].name) {
        //             btn.check = true;
        //         }
        //     }
        // };
        //
        $scope.search.status = 'all';
        $scope.activeNum = 'all';
        $scope.btnClick = function (btn) {
            $scope.activeNum = btn;
            $scope.search.status = btn;
            $scope.pageChanged('0');
        };


    }
}

Controller.$inject = [
    '$scope', '$http', 'loginService', 'authService','$filter','$stateParams'
];

export default Controller ;
