import conf from "../../../../../conf";
// import "jquery";
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    Upload,
    $interval,
    wxService,
    $rootScope,
    alertService,
    $templateCache;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _Upload,
                _$interval,
                _wxService,
                _$rootScope,
                _alertService,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        alertService = _alertService;
        $rootScope = _$rootScope;
        wxService = _wxService;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $interval = _$interval;
        $location = _$location;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        //-------------------------------------------------获取活动数据
        $scope.votData = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/voteActivity/' + $stateParams.activityId,
                params: '',
                headers: {
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(resp);
                $scope.votdata = resp.data.data;
                $scope.changeDate($scope.votdata.voteStartTime, $scope.votdata.voteEndTime);
                if ($scope.votdata.rule) {
                    $scope.tplUrlRule = "/___/store/xxxx/index/tplRule";
                    $templateCache.put($scope.tplUrlRule, $scope.votdata.rule);
                }
                if ($scope.votdata.desp) {
                    $scope.tplUrlDesp = "/___/store/xxxx/index/tplDesp";
                    $templateCache.put($scope.tplUrlDesp, $scope.votdata.desp);
                }
            })
        };
        $scope.votData();
        //-----------------------时间变化
        $scope.changeDate = function (startDate, endDate) {
            $scope.votStartDate = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
            $scope.votEndDate = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
            $interval(function () {
                $scope.nowDate = new Date().getTime();
                if ($scope.votStartDate >= $scope.nowDate) {
                    // console.log('投票活动未开始');
                    $scope.activityStatus = -1;
                } else if ($scope.votEndDate <= $scope.nowDate) {
                    // console.log('活动已结束');
                    $scope.activityStatus = 1;
                } else if ($scope.votStartDate < $scope.nowDate && $scope.votEndDate > $scope.nowDate) {
                    $scope.activityStatus = 0;
                    $scope.lastTime = $scope.votEndDate - $scope.nowDate;
                    $scope.lastDays = Math.floor($scope.lastTime / 1000 / 60 / 60 / 24);
                    $scope.lastHours = Math.floor($scope.lastTime / 1000 / 60 / 60 - (24 * $scope.lastDays));
                    $scope.lastMinutes = Math.floor($scope.lastTime / 1000 / 60 - (24 * 60 * $scope.lastDays) - (60 * $scope.lastHours));
                    $scope.lastSeconds = Math.floor($scope.lastTime / 1000 - (24 * 60 * 60 * $scope.lastDays) - (60 * 60 * $scope.lastHours) - (60 * $scope.lastMinutes));
                    // console.log('活动正在进行中',$scope.lastTime);
                } else {
                    $scope.activityStatus = -1;
                }
            }, 1000);
        };


        //-----------------------------------------按钮文字变化;
        $scope.getbtnFont = function () {
            if (loginService.getAccessToken()) {
                $http({
                    method: 'GET',
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id + '/voteNum',
                    params: '',
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    if (resp.data.data.self) {
                        if (resp.data.data.status == 'NORMAL') {
                            $scope.btnFont = '立即分享,喊人投票';
                        } else if (resp.data.data.status == 'APPLYING') {
                            $scope.btnFont = '审核中,通过后即可分享拉票';
                        } else if (resp.data.data.status == 'REJECT') {
                            $scope.btnFont = 'sorry,你的作品未通过审核';
                        }
                    } else {
                        if (resp.data.data.status == 'NORMAL') {
                            if (resp.data.data.voteNum != 0) {
                                $scope.btnFont = '成功为TA投了' + resp.data.data.voteNum + '票,今天还可以投' + resp.data.data.residueNum + '票';
                            } else {
                                $scope.btnFont = '投他一票';
                            }

                        } else if (resp.data.data.status == 'APPLYING') {
                            $scope.btnFont = '审核中,通过后即可投票';
                        } else if (resp.data.data.status == 'REJECT') {
                            $scope.btnFont = 'sorry,该作品未通过审核';
                        }
                    }
                })
            } else {
                $scope.btnFont = '投他一票';
            }
        };
        $scope.getbtnFont();


//---------------------------------------------获取个人参赛数据
        $scope.getData = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks/' + $stateParams.id,
                params: '',
                headers: {
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('1231231231', resp);
                $scope.data = resp.data.data;
            })
        };

        $scope.getData();


        // var tplUrl = "/___/store/xxxx/index/tpl";
        // $scope.tplUrl = tplUrl;
        // $templateCache.put(tplUrl,'<img src="http://f.hiphotos.baidu.com/image/pic/item/0824ab18972bd40798becce071899e510fb309b5.jpg" width="100%">');
//////=-----------------------------------------------分享设置,获取分享配置

        $scope.shareFn = function (data) {
            if (wxService.isInWx()) {
                $http({
                    method: 'GET',
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks/' + $stateParams.id + '/shareInfo',
                    params: '',
                    headers: {
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    wxService.init().then(function (data) {
                        if (data) {
                            if (resp.data.data.status == 'NORMAL') {
                                wx.showOptionMenu();
                                var conf = {
                                    title: resp.data.data.shareTitle,
                                    desc: resp.data.data.shareContent,
                                    link: $location.absUrl(),
                                    imgUrl: resp.data.data.worksImgUrl,
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                        // alert('success');
                                        $scope.maskHide()
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                        // alert('cancel');
                                        $scope.maskHide()
                                    }
                                };
                                wxService.shareRing(conf);
                                wxService.shareFriend(conf);
                            } else {
                                wx.hideOptionMenu();
                            }
                        }
                    })
                });
            }

        };
        $scope.shareFn();


        $scope.friendsList = function (id) {
            $mdDialog.show({
                templateUrl: 'frends.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$http', '$stateParams', '$mdDialog', '$rootScope', function ($http, $stateParams, $mdDialog, $rootScope) {
                    var vmd = this;
                    vmd.id = id;
                    vmd.currPage = 0;
                    vmd.friendList = [];
                    vmd.getFriendList = function () {
                        if ($rootScope.wxComAppId && $rootScope.wxMpAppId) {
                            $http({
                                method: 'GET',
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id,
                                params: {
                                    page: vmd.currPage,
                                    size: 6,
                                    wxComAppId: $rootScope.wxComAppId,
                                    wxMpAppId: $rootScope.wxMpAppId
                                    // sort: ['totalVotes,desc','lastVoteTime,desc'],
                                },
                                headers: {
                                    "brandApp-Id": $scope.brandAppId
                                }
                            }).then(function (resp) {
                                vmd.friendList = resp.data.data;
                                // vmd.friendList.push.apply(vmd.friendList,resp.data.data);
                                console.log(vmd.friendList);

                                vmd.currPage++;
                            })
                        } else {
                            $http({
                                method: "GET",
                                url: conf.pfApiPath + "/brandApp/" + $stateParams.brandAppId,
                                headers: {
                                    "brandAppp-Id": $stateParams.brandAppId
                                },
                                data: {}
                            }).success(function (resp) {
                                $rootScope.wxComAppId = resp.data.wxComAppId;
                                $rootScope.wxMpAppId = resp.data.wxMpId;
                                $http({
                                    method: 'GET',
                                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id,
                                    params: {
                                        page: vmd.currPage,
                                        size: 2,
                                        wxComAppId: $rootScope.wxComAppId,
                                        wxMpAppId: $rootScope.wxMpAppId
                                        // sort: ['totalVotes,desc','lastVoteTime,desc'],
                                    },
                                    headers: {
                                        "brandApp-Id": $scope.brandAppId
                                    }
                                }).then(function (resp) {
                                    vmd.friendList = resp.data.data;
                                    // vmd.friendList.push.apply(vmd.friendList,resp.data.data);
                                    console.log(vmd.friendList);
                                    vmd.currPage++;
                                })
                            })
                        }
                    };
                    vmd.getFriendList();

                    vmd.getMore = function () {
                        $http({
                            method: 'GET',
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id,
                            params: {
                                page: vmd.currPage,
                                size: 6,
                                // sort: ['totalVotes,desc','lastVoteTime,desc'],
                            },
                            headers: {
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            vmd.friendList.number = resp.data.data.number;
                            vmd.friendList.content.push.apply(vmd.friendList.content, resp.data.data.content);

                            console.log(vmd.friendList);

                            vmd.currPage++;
                        })
                    };

                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.brandApp.addAddress', {orderId: id}, {reload: true})
                        })
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        $scope.popularityList = function (id) {
            $mdDialog.show({
                templateUrl: 'popularity.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                hasBackdrop: false,
                controller: ['$http', '$stateParams', '$mdDialog', function ($http, $stateParams, $mdDialog) {
                    var vmd = this;
                    vmd.id = id;


                    vmd.getTopList = function () {
                        $http({
                            method: 'GET',
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks',
                            params: {
                                page: 0,
                                size: 50,
                                sort: ['totalVotes,desc', 'lastVoteTime,asc'],
                            },
                            headers: {
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            vmd.topList = resp.data.data;
                        })
                    };
                    vmd.getTopList();

                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.brandApp.addAddress', {orderId: id}, {reload: true})
                        })
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };



        $scope.clickVoting = false;

        $scope.voting = function (id) {


            if($scope.clickVoting){
                return;
            }else {
                $scope.clickVoting = true;
            }



            if ($scope.btnFont == '立即分享,喊人投票') {
                $scope.clickVoting = false;
                $scope.maskShow();
            } else if (loginService.getAccessToken()) {
                $http({
                    method: 'GET',
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/voteActivity/' + $stateParams.activityId + '/isFollow',
                    params: {
                        shareUrl: $location.absUrl(),
                        workId: $stateParams.id
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    if (resp.data.status == 10028) {
                        $scope.clickVoting = false;

                        $mdDialog.show({
                            templateUrl: 'qrCode.html',
                            parent: angular.element(document.body),
                            clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                            fullscreen: false,
                            controller: ['$mdDialog', function ($mdDialog) {
                                var vmd = this;
                                vmd.qrCode = resp.data.data;
                                vmd.cancel = function () {
                                    return $mdDialog.cancel();
                                };
                                vmd.goAddress = function () {
                                }
                            }],
                            controllerAs: "vmd"
                        }).then(function (answer) {
                        }, function () {
                        });
                    } else {
                        $http({
                            method: 'PUT',
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id,
                            params: {
                                wxComAppId: $rootScope.wxComAppId,
                                wxMpAppId: $rootScope.wxMpAppId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            if (resp.data.status == 10024) {
                                $scope.clickVoting = false;

                                alertService.msgAlert("exclamation-circle", resp.data.data);
                                // alertService.msgAlert("exclamation-circle", '你的票数已用完!');
                                return;
                            }else if (resp.data.status == 200) {
                                $scope.getbtnFont();
                                $scope.votRecordId = resp.data.data;
                                $mdDialog.show({
                                    templateUrl: 'thank.html',
                                    parent: angular.element(document.body),
                                    clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                                    fullscreen: false,
                                    controller: ['$mdDialog', function ($mdDialog) {
                                        var vmd = this;
                                        vmd.getInfo = function () {
                                            $http({
                                                method: 'GET',
                                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id + '/notify',
                                                params: {
                                                    voteId: $scope.votRecordId,
                                                },
                                                headers: {
                                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                                    "brandApp-Id": $scope.brandAppId
                                                }
                                            }).then(function (resp) {
                                                console.log(resp);
                                                vmd.dataInfo = resp.data.data;
                                            })
                                        };
                                        vmd.getInfo();

                                        vmd.cancel = function () {
                                            return $mdDialog.cancel();
                                        };
                                        vmd.dialoghide = function () {
                                            $mdDialog.hide();
                                        }
                                    }],
                                    controllerAs: "vmd"
                                }).then(function (answer) {
                                    $scope.maskShow()
                                }, function (answer) {
                                });

                                $scope.clickVoting = false;

                            }
                        },function (error) {
                            $scope.clickVoting = false;
                            if(error.data.status == 10024){
                                alertService.msgAlert("exclamation-circle", error.data.message);
                            }
                        })
                    }
                },function (error) {
                    $scope.clickVoting = false;
                })
            } else {
                $scope.clickVoting = false;
                loginService.loginCtl(true, $location.absUrl() + '?formOauth=true');
            }
        };


        //遮罩打开与关闭
        $scope.mask = false;
        $scope.maskShow = function () {
            $scope.mask = true;
        };
        $scope.maskHide = function () {
            $scope.mask = false;
        };


        $scope.fallBack = function (route, params) {
            $state.go(route, params)
        }

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$q',
    'Upload',
    '$interval',
    'wxService',
    '$rootScope',
    'alertService',
    '$templateCache'
];

export default Controller ;
