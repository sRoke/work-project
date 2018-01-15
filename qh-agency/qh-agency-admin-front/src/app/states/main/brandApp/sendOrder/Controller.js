import conf from "../../../../conf";
import moment from "moment";
import laydate from "layui-laydate";
var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
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
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter=_$filter;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);
        //     DELIVERINVOICE_C("修改地址"),
        //     DELIVERINVOICE_U("发货"),
        //     DELIVERINVOICE_R("查看发货单"),
        $scope.DELIVERINVOICE_U = authService.hasAuthor("DELIVERINVOICE_U");    //发货
        $scope.DELIVERINVOICE_R = authService.hasAuthor("DELIVERINVOICE_R");    //查看发货单
        // js控制写在此处
        $scope.curPage = 1;
        $scope.aaa = '';



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
            $http({
                method:"GET",
                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/deliverInvoice",
                params: {
                        page: curPage ? curPage : $scope.curPage - 1,
                        size: conf.pageSize,
                        keyWords:$scope.keyword,
                        status: $scope.refundStatus==="all"?null:$scope.refundStatus,
                        startDate: $scope.startTime,
                        endDate: $scope.endTime
                    },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                //console.log(response.data.content);
                $scope.items=response.data.content;
                // $scope.orderStatus = response.data.orderStatusEnumMap;
                // $scope.items = response.data.recPage.content;
                // $scope.statusNum = response.data.dataCountMap; //取各状态的数量对象
                   $scope.page = response.data;
                   $scope.curPage =response.data.number+1;
                   $scope.activeNum=$scope.refundStatus;
            });
        };
        $scope.pageChanged();
        $scope.refundStatus = 'all';
        $scope.activeNum = 'all';
        $scope.btnClick = function (btn) {
            $scope.activeNum = btn;
            $scope.refundStatus = btn;
            $scope.pageChanged('0');
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
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
