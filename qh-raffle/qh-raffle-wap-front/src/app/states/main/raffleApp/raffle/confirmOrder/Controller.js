import conf from "../../../../../conf";
import weui from 'weui.js';
import store from "store";
import URI from "urijs";
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _$rootScope,) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        $rootScope = _$rootScope;
        /////////////////////////////////
        //loginService.loginCtl(true, $location.absUrl());
        $scope.raffleAppId = $stateParams.raffleAppId;
        $scope.raffleId = $stateParams.raffleId;
        $scope.orderId = $stateParams.orderId;


//微信授权
        $scope.newLogin = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.raffleAppId
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
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": $stateParams.raffleAppId
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
                        console.log('resp1', resp);
                        store.set('openId', resp.data.data);
                        var uri3 = new URI($location.absUrl());
                        uri3.removeSearch("code");
                        uri3.removeSearch("state");
                        uri3.removeSearch("appid");
                        uri3.removeSearch("fromWx");
                        // console.log(' uri.href()===', uri3.href());
                        $window.location.href =uri3.href().replace('fromWx=true', "");
                       // location.reload();
                        // alert('查看控制台')
                    }, function (err) {
                        if (err.data.status == 10001) {
                            // console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                            $state.go("main.reg.phone", {
                                backUrl: $location.absUrl().replace('/login/wxComMp', '/user/bindWx'),
                            });
                        }
                    });


                });
            }, function (resp) {
                //error
            });
        };

        $scope.getInfo = function () {
            $http({
                method: "GET",     ///raffleApp/{raffleAppId}/raffle/{raffleId}/wap/record/{recordId} GET openId
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/raffle/" + $scope.raffleId + "/wap/record/" + $scope.orderId,
                params:{
                    openId:store.get('openId'),
                },
                headers: {
                    "raffleApp-Id": $scope.raffleAppId
                }
            }).then(function (resp) {
                console.log('order--info',resp);
                $scope.items=resp.data.data;
            }, function (resp) {
                //error
            });
        };
        if (!store.get('openId')) {
            $scope.login();
        } else {
            // $http({
            //     method: "GET",        // /raffleApp/{raffleAppId}/wap/raffle/judge GET openId
            //     url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/judge",
            //     params: {
            //         openId: store.get('openId'),
            //     },
            //     headers: {
            //         //'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "raffleApp-Id": $scope.raffleAppId
            //     },
            // }).then(function (resp) {
            //     if(resp.data.data){
            //         $scope.getInfo();
            //     }else{
            //         $scope.login();
            //     }
            // }, function (resp) {
            //     //error
            // });
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.raffleAppId
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
                        $scope.getInfo();
                    }else{
                        store.remove('wxMpAppId');
                        store.remove('openId');
                        $scope.login();
                    }
                })
            })

        }






        //选择地址
        $scope.selectAddress = function () {
           $state.go("main.raffleApp.raffle.address",{orderId:$scope.orderId},{reload: true});
        };

        $scope.submitOrder = function () {
            //快递配送
            if($scope.items.drawType!='SINCE'){
                if (!$scope.items.address.receiver) {
                    alertService.msgAlert("exclamation-circle", "请填写您的收货地址");
                    return;
                };
            }
            $http({
                method: "PUT",   ///raffleApp/{raffleAppId}/raffle/{raffleId}/wap/record/{recordId} PUT openId
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/raffle/" + $scope.raffleId + "/wap/record/" + $scope.orderId,
                params:{
                    openId:store.get('openId'),
                    memo: $scope.buyerMemo,
                },
                headers: {
                    "raffleApp-Id": $scope.raffleAppId
                }
            }).then(function (resp) {
                console.log('resp', resp);
                if (resp.data.status == '200') {
                    $state.go("main.raffleApp.raffle.home", {raffleAppId: $stateParams.raffleAppId}, {reload: true});
                }else{
                    alertService.msgAlert('cancle', resp.data.data);
                }
            }, function (resp) {
                //error
            });
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.raffleApp.raffle.home", {raffleAppId: $stateParams.raffleAppId}, {reload: true});
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
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    '$rootScope',
];

export default Controller ;
