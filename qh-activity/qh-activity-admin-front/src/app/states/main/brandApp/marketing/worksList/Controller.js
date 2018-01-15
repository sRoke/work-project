import conf from "../../../../../conf";

import store from "store";

var $scope,
    $http,
    authService,
    $state,
    $rootScope,
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
                _$rootScope,
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
        $rootScope = _$rootScope;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $interval = _$interval;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;

        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);


        $scope.getWxInfo = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $stateParams.brandAppId,
                headers: {
                    "brandAppp-Id": $scope.brandAppId
                },
                params: {}
            }).success(function (resp) {
                console.log(resp);
                if (resp.data.wxMpId && resp.data.wxComAppId) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;

                    console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                }
            });
        };
        $scope.getWxInfo();

        $scope.exportUrl = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id +"/voteWorks/exportExcel";

        $scope.alertDialog = function (ev, workId) {
            $mdDialog.show({
                templateUrl: 'seeWorks.html',
                parent: angular.element(document.body).find('#qh-activity-admin-front'),
                targetEvent: null,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                //locals: {key: $scope.memo},    //用来传数据
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    // console.log(locals);
                    // vmd.memo = locals.key;

                    vmd.pageChanged = function (curPage) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {}
                        }).success(function (resp) {
                            vmd.data = resp.data;
                        });
                    };
                    vmd.pageChanged();

                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };

                    // vmd.data = {
                    //     name: '彤彤牛奶',
                    //     phone: '18341744444',
                    //     words: '如果你无法简洁的表达你的想法,那只能说明你还不够了解它.===阿尔伯特~爱因斯坦',
                    //     works: 'https://gss0.bdstatic.com/70cFfyinKgQIm2_p8IuM_a/daf/pic/item/838ba61ea8d3fd1fa8ed285b3b4e251f94ca5fef.jpg'
                    // }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        // @GET String brandAppId,
        //      String activityId,
        //      int size,
        //      int page,
        //      List<String> sort,     //排序的关键字，和顺序
        //      String keyWord         //搜索的关键字

        $scope.size = conf.pageSize;
        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ? curPage - 1 : $scope.curPage - 1,
                    size: conf.pageSize,
                    sort: ['order,asc', 'rank,asc'],
                    keyWord: $scope.keyWords,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
            });
        };
        $scope.pageChanged();


        $scope.addVoteDialog = function (ev, workId) {
            $mdDialog.show({
                templateUrl: 'addVote.html',
                parent: angular.element(document.body).find('#qh-activity-admin-front'),
                targetEvent: null,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                //locals: {key: $scope.memo},    //用来传数据
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    // vmd.memo = locals.key;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };

                    vmd.getData = function (curPage) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {}
                        }).success(function (resp) {
                            vmd.data = resp.data;
                        });
                    };
                    vmd.getData();

                    vmd.saveVote = function () {
                        console.log('vmd.voteNum', vmd.voteNum)
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId + "/addVote",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                id: workId,
                                votes: vmd.voteNum,
                                sourceType: 'VIRTUAL'
                            }
                        }).success(function (resp) {
                            console.log('resp', resp)
                            return $mdDialog.hide();
                        });

                    };

                    // vmd.data = {
                    //     name: '彤彤牛奶',
                    //     phone: '18341744444',
                    //     words: '如果你无法简洁的表达你的想法,那只能说明你还不够了解它.===阿尔伯特~爱因斯坦',
                    //     works: 'https://gss0.bdstatic.com/70cFfyinKgQIm2_p8IuM_a/daf/pic/item/838ba61ea8d3fd1fa8ed285b3b4e251f94ca5fef.jpg'
                    // }

                }],
                controllerAs: "vmd"
            }).then(function (answer) {
                $scope.pageChanged();
            }, function () {

            });
        };


        $scope.checkWork = function (ev, workId) {
            $rootScope.linkUrl = $scope.linkAddress = conf.linkPath + $scope.brandAppId + "/vote/" + $scope.id + "/myVote/" + workId;
            console.log('$rootScope.linkUrl', $rootScope.linkUrl);
            $mdDialog.show({
                templateUrl: 'checkWorks.html',
                parent: angular.element(document.body).find('#qh-activity-admin-front'),
                targetEvent: null,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                //locals: {key: $scope.memo},    //用来传数据
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    // console.log(locals);
                    // vmd.memo = locals.key;

                    vmd.pageChanged = function (curPage) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {}
                        }).success(function (resp) {
                            vmd.data = resp.data;
                        });
                    };
                    vmd.pageChanged();

                    vmd.check = function (status) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: {
                                // console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                                wxComAppId: $rootScope.wxComAppId,
                                wxMpAppId: $rootScope.wxMpAppId,
                                url: $rootScope.linkUrl,
                                status: status
                            }
                        }).success(function (resp) {
                            console.log('resp', resp)
                            return $mdDialog.hide();
                        });
                    };
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };

                }],
                controllerAs: "vmd"
            }).then(function (answer) {
                $scope.pageChanged();
            }, function () {
            });
        };
        //删除作品
        $scope.deleteWork = function (workId) {
            alertService.confirm(null, "确定删除该作品？", "温馨提示", "取消", "确认").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/" + workId,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).success(function (resp) {
                        console.log('resp', resp)
                        if (resp.status == 200) {
                            $scope.pageChanged();
                        }
                    });
                }
            });
        }


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
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/toGridfs/voteWorks",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        params: {
                            sort: ['order,asc', 'rank,asc'],
                            keyWord: $scope.keyWords,
                            wxComAppId:response.data.wxMpId,
                            wxMpAppId:response.data.wxComAppId,
                        }
                    }).success(function (resp) {
                        console.log('resp', resp);
                        store.set('work'+$scope.id, resp.data.id);
                        $scope.checkTaskStatus();
                    });
                };
            });
        };


        $scope.checkTaskStatus = function () {
            if (store.get('work'+$scope.id)) {
                $interval.cancel($scope.taskStatusCheck);
                $scope.taskStatusCheck = $interval(function () {
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $stateParams.id + '/task/' + store.get('work'+$scope.id),
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
                            store.remove('work'+$scope.id);
                        }else if (resp.data.data.taskStatus == 'IN_IMPORT') {
                            $scope.exportStatus = 2;                  //正在导入中
                        } else if (resp.data.data.taskStatus == 'COMPLETE_IMPORT') {
                            $scope.exportStatus = 3;                  //导入完成
                            // $interval.cancel($scope.taskStatusCheck);
                            console.log('true')
                        } else if (resp.data.data.taskStatus == 'FALL_IMPORT') {
                            $scope.exportStatus = 4;                  //导入失败
                            $interval.cancel($scope.taskStatusCheck);
                            store.remove('work'+$scope.id);
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



        $scope.exportUrl1 = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/activity/" + $scope.id + "/voteWorks/export/voteWorks/taskType/VOTEWORKS_TASK";
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$rootScope',
    '$filter',
    'authService',
    'loginService',
    'alertService',
    '$mdDialog',
    '$interval',
    '$stateParams'
];

export default Controller ;