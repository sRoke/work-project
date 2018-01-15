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
    wxService,
    $interval,
    alertService,
    $rootScope,
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
                _wxService,
                _$interval,
                _alertService,
                _$rootScope,
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
        alertService = _alertService;
        $interval = _$interval;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        wxService = _wxService;
        $rootScope = _$rootScope;
        $stateParams = _$stateParams;
        $window = _$window;
        $scope.voteAppId = $stateParams.voteAppId;
        /////////////////////////////////

        $scope.voteAppId = $stateParams.voteAppId;
        $scope.tabs = 1;
        $scope.changeTabs = true;
        $scope.checkTabs = function (tabIndex) {
            if (tabIndex == $scope.tabs) {
                return;
            }
            if ($scope.changeTabs) {
                $scope.changeTabs = false;
                $scope.tabs = tabIndex;
                $scope.currPage = 0;
                $scope.keyWord = null;
                if ($scope.tabs == 1) {
                    $scope.getList(1, ['dateCreated,desc']);
                } else if ($scope.tabs == 2) {
                    $scope.getList(1, ['dateCreated,asc']);
                } else {
                    $scope.changeTabs = true;
                }
            }
        };








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
                        if(err.data.status == 10001){
                            // console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                            $state.go("main.reg.phone", {
                                backUrl:$location.absUrl().replace('/login/wxComMp','/user/bindWx'),
                            });
                        }
                    });


                });
            }, function (resp) {
                //error
            });
        };


        if(!store.get('openId')){
            $scope.login();
        }else {
            console.log('openId=',store.get('openId'));
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
                        $http({
                            method: 'GET',
                            url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks/s/isSignup',
                            params: {
                                openId:store.get('openId'),
                            },
                            headers: {
                                // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "voteApp-Id": $scope.voteAppId
                            }
                        }).then(function (resp) {
                            if (resp.data.data) {
                                if(resp.data.data.logOut){
                                    jso.wipeTokens();
                                    loginService.setvoteAppId(null);
                                    loginService.setAccessToken(null);
                                    $http({
                                        method: "POST",
                                        url: "https:" + conf.oauthPath +"/logout",
                                        headers: {},
                                        params: {},
                                        withCredentials:true,
                                    }).success(function (resp) {
                                        location.reload();
                                    },function(resp){
                                        console.log('ERR', resp);
                                    });
                                }else {
                                    if(resp.data.data.workId){
                                        $scope.myVoteId = resp.data.data.workId;
                                    }
                                    if ($stateParams.formOauth) {
                                        $scope.getData(true);
                                    } else {
                                        $scope.pageShow = true;
                                    }
                                }
                            }else {
                                if ($stateParams.formOauth) {
                                    $scope.getData(true);
                                } else {
                                    $scope.pageShow = true;
                                }
                            }
                        });
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




















        $scope.currentPage = 1;
        $scope.changePage = function () {
            console.log($scope.currentPage);
        }

        // if (loginService.getAccessToken()) {
        //     $http({
        //         method: 'GET',
        //         url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks/s/isSignup',
        //         params: {
        //             openId:store.get('openId'),
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //             "voteApp-Id": $scope.voteAppId
        //         }
        //     }).then(function (resp) {
        //         if (resp.data.data) {
        //             if(resp.data.data.logOut){
        //                 jso.wipeTokens();
        //                 loginService.setvoteAppId(null);
        //                 loginService.setAccessToken(null);
        //                 $http({
        //                     method: "POST",
        //                     url: "https:" + conf.oauthPath +"/logout",
        //                     headers: {},
        //                     params: {},
        //                     withCredentials:true,
        //                 }).success(function (resp) {
        //                     location.reload();
        //                 },function(resp){
        //                     console.log('ERR', resp);
        //                 });
        //             }else {
        //                 if(resp.data.data.workId){
        //                     $scope.myVoteId = resp.data.data.workId;
        //                 }
        //                 if ($stateParams.formOauth) {
        //                     $scope.getData(true);
        //                 } else {
        //                     $scope.pageShow = true;
        //                 }
        //             }
        //         }else {
        //             if ($stateParams.formOauth) {
        //                 $scope.getData(true);
        //             } else {
        //                 $scope.pageShow = true;
        //             }
        //         }
        //
        //
        //
        //
        //     })
        // } else {


            // $http({
            //     method: 'GET',
            //     url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks/s/isSignup',
            //     params: {
            //         openId:store.get('openId'),
            //     },
            //     headers: {
            //         // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "voteApp-Id": $scope.voteAppId
            //     }
            // }).then(function (resp) {
            //     if (resp.data.data) {
            //         if(resp.data.data.logOut){
            //             jso.wipeTokens();
            //             loginService.setvoteAppId(null);
            //             loginService.setAccessToken(null);
            //             $http({
            //                 method: "POST",
            //                 url: "https:" + conf.oauthPath +"/logout",
            //                 headers: {},
            //                 params: {},
            //                 withCredentials:true,
            //             }).success(function (resp) {
            //                 location.reload();
            //             },function(resp){
            //                 console.log('ERR', resp);
            //             });
            //         }else {
            //             if(resp.data.data.workId){
            //                 $scope.myVoteId = resp.data.data.workId;
            //             }
            //             if ($stateParams.formOauth) {
            //                 $scope.getData(true);
            //             } else {
            //                 $scope.pageShow = true;
            //             }
            //         }
            //     }else {
            //         if ($stateParams.formOauth) {
            //             $scope.getData(true);
            //         } else {
            //             $scope.pageShow = true;
            //         }
            //     }
            //
            //
            //
            //
            // });
            // $scope.pageShow = true;
        // }


        $scope.getData = function (signup) {
            $http({
                method: 'GET',
                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId,
                params: '',
                headers: {
                    "voteApp-Id": $scope.voteAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;



                // $scope.signUpStartTime = resp.data.data.signUpStartTime.substring(0,resp.data.data.signUpStartTime.length-3) ;
                // $scope.signUpEndTime = resp.data.data.signUpEndTime.substring(0,resp.data.data.signUpEndTime.length-3);
                // $scope.voteStartTime = resp.data.data.voteStartTime.substring(0,resp.data.data.voteStartTime.length-3);
                // $scope.voteEndTime = resp.data.data.voteEndTime.substring(0,resp.data.data.voteEndTime.length-3);






                if (signup) {
                    $scope.singUp();
                    return
                }

                // if (new Date(Date.parse($scope.data.signUpEndTime.replace(/-/g, "/"))).getTime() < new Date().getTime()) {
                //     $scope.signUpisEnd = true;
                // }


                if ($scope.data.signUpEndTime < new Date().getTime()) {
                    $scope.signUpisEnd = true;
                }

                $scope.changeDate($scope.data.voteStartTime, $scope.data.voteEndTime);
                if ($scope.data.rule) {
                    $scope.tplUrlRule = "/___/store/xxxx/index/tplRule";
                    $templateCache.put($scope.tplUrlRule, $scope.data.rule);
                }
                if ($scope.data.desp) {
                    $scope.tplUrlDesp = "/___/store/xxxx/index/tplDesp";
                    $templateCache.put($scope.tplUrlDesp, $scope.data.desp);
                }

                if (wxService.isInWx()) {
                    wxService.init().then(function (data) {
                        if (data) {
                            var conf = {
                                title: $scope.data.shareTitle,
                                desc: $scope.data.shareContent,
                                link: $location.absUrl(),
                                imgUrl: $scope.data.primaryImgUrl,
                            };
                            wxService.shareRing(conf);
                            wxService.shareFriend(conf);
                        }
                    })
                }
            })
        };
        $scope.getData();


        $scope.currPage = 1;


        //-----------------------------------最新参赛
        $scope.getList = function (currPage, status) {
            if (!status) {
                if ($scope.tabs == 1) {
                    status = ['dateCreated,desc']
                } else if ($scope.tabs == 2) {
                    status = ['dateCreated,asc'];
                }
            }
            $http({
                method: 'GET',
                url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks',
                params: {
                    page: currPage - 1,
                    size: 6,
                    sort: status,
                    // ['dateCreated,desc'],  1
                    //['totalVotes,desc']     2
                    keyWord: $scope.keyWord,
                },
                headers: {
                    "voteApp-Id": $scope.voteAppId
                }
            }).then(function (resp) {
                $scope.list = resp.data.data;
                $scope.changeTabs = true;
            })
        };
        $scope.getList($scope.currPage, ['dateCreated,desc']);


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


        $scope.singUp = function () {


            if ($scope.data.voteStatusEnum == 'END') {
                return alertService.msgAlert("exclamation-circle", "该活动已被禁用!");
            }

            if ($scope.data.signUpStartTime > new Date().getTime()) {
                return alertService.msgAlert("exclamation-circle", "活动还未开始");
            }

            // 已经登录
            if (loginService.getAccessToken()) {


                /*
                if(用户尚未绑定指定的微信公众号){
                    logout();
                    login();
                }
                 */

                $http({
                    method: 'GET',
                    url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/vote/wap/' + $stateParams.voteId + '/voteWorks',
                    params: {},
                    headers: {
                        "voteApp-Id": $scope.voteAppId
                    }
                }).then(function (resp) {
                    $scope.list = resp.data.data;
                    $scope.changeTabs = true;
                })














                if ($scope.myVoteId) {
                    $state.go('main.voteApp.vote.myVote', {id: $scope.myVoteId});
                } else {
                    $state.go('main.voteApp.vote.votSignUp');
                }

                // 尚未登录
            } else {
                // logout();
                loginService.loginCtl(true, $location.absUrl() + '?q=11&formOauth=true');
            }
        };
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
    'wxService',
    '$interval',
    'alertService',
    '$rootScope',
    '$window',
    '$httpParamSerializer',
    '$templateCache'
];

export default Controller ;
