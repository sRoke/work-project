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
        // ACCOUNTLOG_R("查看账户流水"),
        //     ACCOUNTLOG_A("账户流水管理",ACCOUNTLOG_R),
        $scope.ACCOUNTLOG_R = authService.hasAuthor("ACCOUNTLOG_R");    //查看账户流水
        // conf.apiPath = '//agency.kingsilk.net' + `/local/16000/rs/api`;
        // conf.shopPath = '//agency.kingsilk.net' + `/shop/local/16000/rs/api`;
        $scope.listId = [];
        $scope.curPage = 1;
        //前端进行请求




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
            $scope.startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd');
            $scope.endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd');
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partnerAccount/logIds",
                params: {
                    page: curPage ? curPage - 1 : $scope.curPage - 1,
                    size: conf.pageSize,
                    status: $scope.status,
                    logType: '',
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

                for (let i in response.data.content) {
                    if (response.data.content[i].type == "RETAIL_ORDER" || response.data.content[i].type == "WITHDRAW_CASH") {
                        $scope.getShopOauthData(response.data.content[i]);
                    } else {
                        $scope.getAgencyOauthData(response.data.content[i]);
                    }
                }


            });
        };
        $scope.pageChanged();


        $scope.getShopOauthData = function (list) {
            $http({
                method: "GET",
                url: conf.shopPath + "/brandApp/" + $scope.brandAppId + "/order/log",
                params: {
                    ids: list.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                list.seq = response.data[0].seq
            });
        }

        $scope.getAgencyOauthData = function (list) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partnerAccount/log",
                params: {
                    ids: list.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                list.seq = response.data[0].seq
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

