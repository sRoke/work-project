import conf from "../../../../conf";
// import 'angular-bootstrap';
import store from "store";

import laydate from "layui-laydate";

var $scope,
    $rootScope,
    authService,
    loginService,
    $stateParams,
    $filter,
    $location,
    alertService,
    $http;

class Controller {
    constructor(_$scope,_$rootScope, _$http, _loginService, _authService, _$filter, _$location,_alertService, _$stateParams) {
        $scope = _$scope;
        $rootScope=_$rootScope;
        $http = _$http;
        $filter = _$filter;
        $location = _$location;
        $stateParams = _$stateParams;
        loginService = _loginService;
        alertService = _alertService;
        authService = _authService;
        var vm = this;


        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_U = authService.hasAuthor("ORDER_U");    //发货
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.brandAppId = $stateParams.brandAppId;
        //初始化
        // /brandApp/{id}
        // $rootScope.data={};
        $scope.activeNum = '1';
        $scope.changeTab = function (num) {
            $scope.activeNum = num;
        };
        $scope.chs = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId,
                params: {

                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).success(function (response) {
                console.log(6989595985895, response);
            });

        };
        //时间控制
        laydate.render({
            elem: '#startTime',           //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: '#666',                      //自定义主题颜色
            // format: 'yyyy-MM-dd HH:mm',      //可任意组合
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.startTime = value;
            }
        });

        laydate.render({
            elem: '#endTime',             //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: 'qqqq',                   //自定义主题颜色
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.endTime = value;
            }
        });


        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/shop',
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ?curPage:$scope.curPage-1,
                    size: conf.pageSize,
                    keyWord:$scope.keyWords,
                    // keyWordsPeople:$scope.keyWordsPeople

                },
            }).then(function (response) {
                $scope.items = response.data.data;
                console.log(response);
            }, function (response) {

            });
        };
        $scope.pageChanged(0);


        $scope.changeStatus = function (id,status) {

            if(status){
                var ts = '确定启用该门店?'
            }else {
                var ts = '确定关闭该门店?'
            }


            alertService.confirm(null, ts, "温馨提示", "取消", "确认").then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/shop/' +id+'/enable',
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                enable:status,
                            },
                        }).then(function (response) {
                            console.log(response);
                            if(response.data.status == 200){
                                $scope.pageChanged();
                            }
                        }, function (response) {

                        });
                    }
            });
        }
















    }
}

Controller.$inject = [
    '$scope', '$rootScope','$http', 'loginService', 'authService', '$filter', '$location','alertService', '$stateParams'
];

export default Controller ;
