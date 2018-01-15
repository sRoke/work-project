import conf from "../../../../../conf";
// import "jquery";


import store from "store";
import URI from "urijs";









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
    $window,
    $httpParamSerializer,
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
                _$window,
                _$httpParamSerializer,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        $httpParamSerializer = _$httpParamSerializer;
        $window = _$window;
        alertService = _alertService;
        $rootScope = _$rootScope;
        wxService = _wxService;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $interval = _$interval;
        $location = _$location;
        $stateParams = _$stateParams;
        $scope.voteAppId = $stateParams.voteAppId;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        $scope.voteAppId = $stateParams.voteAppId;


        //-----------------------------------------按钮文字变化;
        $scope.getbtnFont = function () {
            // if (loginService.getAccessToken()) {
            $http({
                method: 'GET',
                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id + '/voteNum',
                params: {
                    openId:store.get('openId'),
                },
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "voteApp-Id": $scope.voteAppId
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
                $scope.pageViewShow = true;
            });
            // } else {
            //     $scope.btnFont = '投他一票';
            // }
        };
        // $scope.getbtnFont();







        $scope.getWxInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.voteAppId
                },
            }).then(function (resp) {
                console.log('resp-bargainApp', resp);
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    console.log('12312312313===',resp);
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;

                });
            }, function (resp) {
                //error
            });
        };
        // $scope.getWxInfo();



        $scope.newLogin = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.voteAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    store.set('wxMpAppId', resp.data.wxMpId);
                    $http({
                        method: "GET",
                        url: conf.wx4jPath + "/wxCom/" + $rootScope.wxComAppId + "/mp/" + $rootScope.wxMpAppId + "/user/auth/url",
                        params: {
                            // wxMpAppId: $scope.wxMpAppId,
                            redirectUri: createBackUrl(),
                            scopes: ["snsapi_base", "snsapi_userinfo"],
                            scan: !wxService.isInWx(),
                        }
                    }).then(function (resp) {
                        $scope.url = resp.data.data;
                        console.log("SUCCESS - ", resp);
                        $window.location.href = $scope.url;
                    }, function (err) {
                        // alert("3333333ERROR" + JSON.stringify(err));
                        console.log("ERROR", err)
                    });
                });
            }, function (resp) {
                //error
            });
        };

        // console.log("当前URL是", $location.absUrl());
        var uri = new URI($location.absUrl());
        var uriSearch = uri.search(true);
        // console.log("url.search(true) = ", uriSearch);
        var fragmentUri = new URI(uri.fragment());
        var fragmentUriSearch = fragmentUri.search(true);
        // console.log("fragmentUri.search(true) = ", fragmentUriSearch);


        // 在当前URL的基础上，追加额外参数 "fromWx"
        function createBackUrl() {
            var url2 = uri.clone();
            var fragmentUri2 = new URI(url2.fragment());
            var fragmentUriSearch2 = fragmentUri.search(true);

            fragmentUriSearch2.fromWx = true;
            fragmentUri2.search(fragmentUriSearch2);
            // TODO 删除 hash 中查询参数（code,state）。防止多次从微信返回后，有多个 code，state 参数。
            url2.fragment(fragmentUri2.toString());
            return url2.toString();
        }

        // 检查是否是从微信回来的，如果是，则应该执行 调用 后台API，完成登录。
        $scope.login = function () {
            if (!(uriSearch.fromWx) && !(fragmentUriSearch.fromWx)) {
                console.log("并不是从微信服务器返回的，无法登录");
                $scope.newLogin();
                return;
            }
            // 提取 参数 中的 code, state
            let code = uriSearch.code
                ? uriSearch.code
                : fragmentUriSearch.code;

            let state = uriSearch.state
                ? uriSearch.state
                : fragmentUriSearch.state;

            // 调用后台API，用 code 换取 access token，并尝试登录
            // 注意：可能无法登录——尚未绑定手机号，即还要相应的用户
            // 只有当手机号绑定之后，才会创建用户。

            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "voteApp-Id": $stateParams.voteAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;

                    $http({
                        method: "POST",
                        url: conf.oauthPath + "/api/login/wxComMp",
                        data: {
                            wxComAppId: $rootScope.wxComAppId,
                            wxMpAppId: $rootScope.wxMpAppId,
                            code: code,
                            state: state
                        },
                        transformRequest: $httpParamSerializer,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }

                    }).then(function (resp) {
                        console.log('resp1',resp);
                        store.set('openId', resp.data.data);
                        var uri3 = new URI($location.absUrl());
                        uri3.removeSearch("code");
                        uri3.removeSearch("state");
                        uri3.removeSearch("appid");
                        uri3.removeSearch("fromWx");
                        console.log(' uri.href()===', uri3.href());
                        $window.location.href =uri3.href().replace('fromWx=true', "");
                        // location.reload();
                        // alert('查看控制台')
                    }, function (err) {
                        // if(err.data.status == 10001){
                        //     // console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                        //     $state.go("main.reg.phone", {
                        //         backUrl:$location.absUrl().replace('/login/wxComMp','/user/bindWx'),
                        //     });
                        // }
                    });


                });
            }, function (resp) {
                //error
            });
        };





        if(!store.get('openId')){
            $scope.login();
        }else {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.voteAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    if(store.get('wxMpAppId') == resp.data.wxMpId){
                        console.log('openId=', store.get('openId'));
                        $scope.getbtnFont();
                    }else {
                        store.remove('wxMpAppId');
                        store.remove('openId');
                        $scope.login();
                    }
                });
            }, function (resp) {
                //error
            });
        }











        //-------------------------------------------------获取活动数据
        $scope.votData = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId,
                params: '',
                headers: {
                    "voteApp-Id": $scope.voteAppId
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
            // $scope.votStartDate = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
            // $scope.votEndDate = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
            $scope.votStartDate = startDate;
            $scope.votEndDate = endDate;

            $interval(function () {
                $scope.nowDate = new Date().getTime();
                if ($scope.votStartDate >= $scope.nowDate) {
                    // console.log('投票活动未开始');
                    $scope.voteStatus = -1;
                } else if ($scope.votEndDate <= $scope.nowDate) {
                    // console.log('活动已结束');
                    $scope.voteStatus = 1;
                } else if ($scope.votStartDate < $scope.nowDate && $scope.votEndDate > $scope.nowDate) {
                    $scope.voteStatus = 0;
                    $scope.lastTime = $scope.votEndDate - $scope.nowDate;
                    $scope.lastDays = Math.floor($scope.lastTime / 1000 / 60 / 60 / 24);
                    $scope.lastHours = Math.floor($scope.lastTime / 1000 / 60 / 60 - (24 * $scope.lastDays));
                    $scope.lastMinutes = Math.floor($scope.lastTime / 1000 / 60 - (24 * 60 * $scope.lastDays) - (60 * $scope.lastHours));
                    $scope.lastSeconds = Math.floor($scope.lastTime / 1000 - (24 * 60 * 60 * $scope.lastDays) - (60 * 60 * $scope.lastHours) - (60 * $scope.lastMinutes));
                    // console.log('活动正在进行中',$scope.lastTime);
                } else {
                    $scope.voteStatus = -1;
                }
            }, 1000);
        };




//---------------------------------------------获取个人参赛数据
        $scope.getData = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks/' + $stateParams.id,
                params: '',
                headers: {
                    "voteApp-Id": $scope.voteAppId
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
                    url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks/' + $stateParams.id + '/shareInfo',
                    params: '',
                    headers: {
                        "voteApp-Id": $scope.voteAppId
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
                                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id,
                                params: {
                                    page: vmd.currPage,
                                    size: 6,
                                    wxComAppId: $rootScope.wxComAppId,
                                    wxMpAppId: $rootScope.wxMpAppId
                                    // sort: ['totalVotes,desc','lastVoteTime,desc'],
                                },
                                headers: {
                                    "voteApp-Id": $scope.voteAppId
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
                                url: conf.apiPath + "/voteApp/"+ $stateParams.voteAppId,
                                headers: {
                                    "voteApp-Id": $stateParams.voteAppId
                                },
                                data: {}
                            }).success(function (resp) {
                                $http({
                                    method: "GET",
                                    url: conf.pfApiPath + "/brandApp/" + resp.data,
                                    headers: {
                                        "brandApp-Id": resp.data
                                    },
                                    data: {}
                                }).success(function (resp) {
                                    $rootScope.wxComAppId = resp.data.wxComAppId;
                                    $rootScope.wxMpAppId = resp.data.wxMpId;
                                    $http({
                                        method: 'GET',
                                        url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id,
                                        params: {
                                            page: vmd.currPage,
                                            size: 2,
                                            wxComAppId: $rootScope.wxComAppId,
                                            wxMpAppId: $rootScope.wxMpAppId
                                            // sort: ['totalVotes,desc','lastVoteTime,desc'],
                                        },
                                        headers: {
                                            "voteApp-Id": $scope.voteAppId
                                        }
                                    }).then(function (resp) {
                                        vmd.friendList = resp.data.data;
                                        // vmd.friendList.push.apply(vmd.friendList,resp.data.data);
                                        console.log(vmd.friendList);
                                        vmd.currPage++;
                                    })
                                })

                            })



                        }
                    };
                    vmd.getFriendList();

                    vmd.getMore = function () {
                        $http({
                            method: 'GET',
                            url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id,
                            params: {
                                page: vmd.currPage,
                                size: 6,
                                // sort: ['totalVotes,desc','lastVoteTime,desc'],
                            },
                            headers: {
                                "voteApp-Id": $scope.voteAppId
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
                            $state.go('main.voteApp.addAddress', {orderId: id}, {reload: true})
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
                            url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks',
                            params: {
                                page: 0,
                                size: 50,
                                sort: ['totalVotes,desc', 'lastVoteTime,asc'],
                            },
                            headers: {
                                "voteApp-Id": $scope.voteAppId
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
                            $state.go('main.voteApp.addAddress', {orderId: id}, {reload: true})
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


            if($scope.votdata.voteStatusEnum == 'END'){
                $scope.clickVoting = false;
                return alertService.msgAlert("exclamation-circle", "该活动已被禁用!");
            }

            if ($scope.btnFont == '立即分享,喊人投票') {
                $scope.clickVoting = false;
                $scope.maskShow();
            } else if (loginService.getAccessToken()) {
                $http({
                    method: 'GET',
                    url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/admin/' + $stateParams.voteId + '/isFollow',
                    params: {
                        shareUrl: $location.absUrl(),
                        workId: $stateParams.id
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "voteApp-Id": $scope.voteAppId
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
                            url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id,
                            params: {
                                wxComAppId: $rootScope.wxComAppId,
                                wxMpAppId: $rootScope.wxMpAppId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "voteApp-Id": $scope.voteAppId
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
                                                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteRecord/' + $stateParams.id + '/notify',
                                                params: {
                                                    voteId: $scope.votRecordId,
                                                },
                                                headers: {
                                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                                    "voteApp-Id": $scope.voteAppId
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
    '$window',
    '$httpParamSerializer',
    '$templateCache'
];

export default Controller ;
