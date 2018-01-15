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
    alertService,
    $mdDialog,
    errorService,
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
                _errorService,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        errorService=_errorService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);
        // WITHDRAWCASH_R("查看提现记录"),
        //     WITHDRAWCASH_U("提现审核"),
            $scope.WITHDRAWCASH_U = authService.hasAuthor("WITHDRAWCASH_U");    //提现审核





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





        $scope.curPage = 1;
        //前端进行请求
        $scope.pageChanged = function (curPage) {
            $scope.startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd');
            $scope.endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd');
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/withdrawCash",
                params: {
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                    status: $scope.status,
                    // source:'APPLY',
                    // applyType:$scope.appType,
                    startDate: $scope.startTime,
                    endDate: $scope.endTime,
                    // keyWord:$scope.keyWords
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                console.log('response', response);
                // console.log(response.data.content);
                $scope.data = response.data.content;
                $scope.page = response.data;
                $scope.curPage = response.data.number + 1;
            });
        };
        $scope.pageChanged();


        //拒绝
        $scope.reject = function (id) {
            alertService.confirm(null, "确定拒绝？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/withdrawCash/" + id + "/rejectWithdraw",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            console.log(data);
                            if (data.status == 200) {
                                $scope.pageChanged();
                            }
                        });
                    }
                });
        }

        //同意
        $scope.agree = function (id) {
            alertService.confirm(null, "同意打款？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/withdrawCash/" + id + "/agreeWithdraw",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            console.log(data);
                            // if (data.status == 200) {
                            //     $scope.pageChanged();
                            // }
                            if(data.data.err_code){
                                return errorService.error(data.data.err_code_des,null);
                            }else{
                                $scope.pageChanged();
                            }
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
    'errorService',
    '$stateParams'
];

export default Controller ;

