//先导入info.html
import conf from "../../../../conf";
import laydate from "layui-laydate";
import info from "!html-loader?minimize=true!./info.html";
var $scope,
    $http,
    authService,
    $state,
    $log,
    alertService,
    errorService,
    loginService,
    $mdDialog,
    $filter,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _alertService,
                _errorService,
                _authService,
                _loginService,
                _$mdDialog,
                _$filter,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter,
            alertService = _alertService;
        errorService = _errorService;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        $scope.REFUNDMONEY_U = authService.hasAuthor("REFUNDMONEY_U");    //退款

        //初始化列表
        $scope.curPage = 1;




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
        $scope.getInfo = function (curPage) {
            $scope.startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd');
            $scope.endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd');
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/refund",
                params: {
                    page: curPage ? $scope.curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                    status: $scope.refundStatus,
                    startDate: $scope.startTime,
                    endDate: $scope.endTime,
                    keyWord: $scope.reason,
                    source: "manageRefund"
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log(resp);
                $scope.data = resp.data;
                $scope.curPage = resp.data.number + 1;
                // $scope.refundStatusEnumMap = resp.data.refundStatusEnumMap;
                $scope.items = resp.data.content;
            });
        };
        $scope.getInfo();


        var $dateTime=document.getElementsByClassName('dateTime');
       // console.log($dateTime);

        // $dateTime.datepicker({
        //     format: "yyyy-mm-dd",
        //     todayBtn: "linked",
        //     language: "zh-CN",
        //     orientation: "auto",
        //     autoclose: true
        // });

        //rmy 2017-07-20 同意退款弹窗
        $scope.openDialog = function (item, ev) {
            if (item.status != 'UNPAYED') {
                return errorService.error("当前状态下不可操作！", null)
            } else {
                $mdDialog.show({
                    template: info,
                    parent: angular.element(document.body).find('#qh-agency-admin-front'),
                    targetEvent: ev,
                    clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                    fullscreen: false,
                    locals: item,
                    controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
                        var vm = this;
                        vm.price = item.refundAmount;
                        vm.realName = item.buyerPartnerName;
                        vm.phone = item.buyerPartnerPhone;
                        vm.id = item.id;
                        vm.cancel = function () {
                            return $mdDialog.cancel();
                        };
                        vm.agree = function () {
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/refund/" + vm.id + "/refundHandle",
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).success(function (resp) {
                                console.log(resp);
                                if (resp.status == '200') {
                                    vm.cancel();
                                    $scope.getInfo();
                                }
                            });
                        };
                    }],
                    controllerAs: "vm"
                }).then(function (answer) {
                }, function () {
                });
            }
        };
        //拒绝退款
        $scope.reject = function (item) {
            if (item.status != 'UNPAYED') {
                return errorService.error("当前状态下不可操作！", null)
            }else if(item.type=='ITEM'){
                return errorService.error("当前状态下不可操作！", null)
            }
            else {
                alertService.confirm(null, "确定拒绝退款？", "温馨提示", "取消", "确认")
                    .then(function (data) {
                        if (data) {
                            //拒绝接口
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/refund/" + item.id + "/reject",
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).then(function successCallback(response) {
                                console.log(response);
                                $scope.getInfo();
                            }, function errorCallback(response) {
                                // 请求失败执行代码
                                return errorService.error(response.data, null)
                            });
                        }
                    });
            }

        };


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'alertService',
    'errorService',
    'authService',
    'loginService',
    '$mdDialog',
    '$filter',
    '$stateParams'
];

export default Controller ;

