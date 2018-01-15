import conf from "../../../../../conf";

import store from "store";

var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
    alertService,
    $mdDialog,
    $interval,
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
                _$interval,
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
        $interval = _$interval;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;

        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);

        /*
         *
         *
         *
         * @Path("/brandApp/{brandAppId}/activity/{activityId} oteRecord")

         @Path("/exportExcel")
         @PathParam(value = "brandAppId")
         String brandAppId,

         @PathParam("activityId")
         String activityId,

         @QueryParam("sort")
         List<String> sort,

         @QueryParam("voteKeyword")
         String voteKeyword,

         @QueryParam("workKeyword")
         String workKeyword

         投票记录Excel 导出
         *
         *
         * */
        $scope.exportUrl = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteRecord/exportExcel";


        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteRecord",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ? curPage - 1 : $scope.curPage - 1,
                    size: conf.pageSize,
                    voteKeyword: $scope.voteKeyword,
                    workKeyword: $scope.workKeyword,
                    sort: ['dateCreated,desc'],
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
                        if (resp.status == 200) {
                            $scope.pageChanged();
                        }
                    }, function (resp) {

                    });
                }
            });
        };


        $scope.read = function (id) {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId,
                params: {},
                headers: {
                    "brandAppp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                // $scope.data = response.data;
                // $rootScope.wxComAppId = response.data.wxComAppId;
                // store.set(conf.wxComAppId, $rootScope.wxComAppId);
                if (response.data.wxMpId && response.data.wxComAppId) {
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteRecord/toGridfs/voteRecords",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        params: {
                            voteKeyword: $scope.voteKeyword,
                            workKeyword: $scope.workKeyword,
                            sort: ['dateCreated,desc'],
                            wxComAppId:response.data.wxMpId,
                            wxMpAppId:response.data.wxComAppId,
                        }
                    }).success(function (resp) {
                        console.log('resp', resp);
                        store.set('Record'+$scope.id, resp.data.id);
                        $scope.checkTaskStatus();
                    });
                };
            });
        };









        $scope.checkTaskStatus = function () {
            if (store.get('Record'+$scope.id)) {
                $interval.cancel($scope.taskStatusCheck);
                $scope.taskStatusCheck = $interval(function () {
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $stateParams.id + '/task/' + store.get('Record'+$scope.id),
                        params: {},
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        console.log('error',resp)
                        if(resp.data.status == 11006){
                            $scope.exportStatus = 1;
                            $interval.cancel($scope.taskStatusCheck);
                            store.remove('Record'+$scope.id);
                        }else if (resp.data.data.taskStatus == 'IN_IMPORT') {
                            $scope.exportStatus = 2;                  //正在导入中
                        } else if (resp.data.data.taskStatus == 'COMPLETE_IMPORT') {
                            $scope.exportStatus = 3;                  //导入完成
                            // $interval.cancel($scope.taskStatusCheck);
                            console.log('true')
                        } else if (resp.data.data.taskStatus == 'FALL_IMPORT') {
                            $scope.exportStatus = 4;                  //导入失败
                            $interval.cancel($scope.taskStatusCheck);
                            store.remove('Record'+$scope.id);
                            console.log('false')
                        }
                    }, function (resp) {
                        //TODO 错误处理
                    });
                }, 3000);
            } else {
                $scope.exportStatus = 1;
            }
        };

        $scope.checkTaskStatus();



















        $scope.exportUrl1 = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteRecord/export/voteRecords/taskType/VOTERECORDS_TASK?voteKeyword=" + $scope.voteKeyword + "&workKeyword=" + $scope.workKeyword;

        $scope.test = function () {
            // $scope.exportUrl1 = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteRecord/export/voteRecords/taskType/VOTERECORDS_TASK?voteKeyword=" + $scope.voteKeyword + "&workKeyword=" + $scope.workKeyword;
            //
            // console.log('$scope.exportUrl1', $scope.exportUrl1);
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
    '$interval',
    '$stateParams'
];

export default Controller ;