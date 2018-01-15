import conf from "../../../conf";
import html from "!html-loader?minimize=true!./dialog.html";
import store from "store";

let $scope,
    $mdDialog,
    alertService,
    $rootScope,
    $interval,
    $state,
    $stateParams,
    loginService,
    $timeout,
    $http;
class Controller {
    constructor(_$scope,
                _$mdDialog,
                _$interval,
                _$rootScope,
                _alertService,
                _$state,
                _$stateParams,
                _loginService,
                _$timeout,
                _$http) {
        /////////////////////////////////////通用注入
        $scope = _$scope;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $interval = _$interval;
        alertService = _alertService;
        $rootScope = _$rootScope;
        $http = _$http;
        $timeout = _$timeout;
        loginService = _loginService;
        $stateParams = _$stateParams;

        const TAG = "main/wxLogin";
        console.log(`==> ${TAG}`);

        var vmd = $scope.vmd = {
            at: null,   // 获取的 access_token
        };
        if (loginService.getAccessToken()) {
            vmd.at = loginService.getAccessToken();
        }

        ////登录类型，参数
        const loginType = {
            WX: "loginType=WX",
            WX_SCAN: "loginType=WX_SCAN",
            WX_QYH: "loginType=WX_QYH&autoCreateUser",
            WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
            PASSWORD: "loginType=PASSWORD",
            WX_SCAN_third:'loginType=WX_SCAN_third',
            PHONE:'loginType=PHONE',
            WAPPHONE:'loginType=WAPPHONE',
        };

        ////登录
        $scope.wxLogin = function () {
            console.log(`${TAG} => wxLogin`);
            console.log('branComId',$stateParams.raffleAppId, $stateParams.backUrl);
            // $scope.wxMpAppId = "wx7cc0b96add4254b1";
            if($rootScope.wxComAppId && $rootScope.wxMpAppId){
                let type = "WX_SCAN_third";
                global.jso.config.config.redirect_uri = $stateParams.backUrl;
                global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}&wxComAppId=${$rootScope.wxComAppId}&wxMpAppId=${$rootScope.wxMpAppId}`;

                console.log('22222222222global==============',global);
                $timeout(function () {
                    console.log(`window.location.hash=============`,window.location.hash);
                    global.jso.getToken((token) => {
                        loginService.setAccessToken(token.access_token);
                        console.log("I got the token: ", token);
                        $timeout(function () {
                            location.href = $stateParams.backUrl;
                        },0)
                    }, {});
                },0)
            }else {

                $http({
                    method: "GET",
                    url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                    headers: {
                        //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "raffleApp-Id": $stateParams.raffleAppId
                    },
                }).then(function (resp) {
                    console.log('resp-raffleApp',resp);
                    $rootScope.brandAppId=resp.data.data;
                    $http({
                        method: "GET",
                        url: conf.pfApiPath + "/brandApp/"+ $rootScope.brandAppId,
                        headers: {
                            "brandApp-Id": $rootScope.brandAppId
                        },
                        data: {}
                    }).success(function (resp) {
                        console.log(resp);
                        if(resp.data.wxMpId && resp.data.wxComAppId ){
                            $rootScope.wxComAppId = resp.data.wxComAppId;
                            $rootScope.wxMpAppId = resp.data.wxMpId;
                            let type = "WX_SCAN_third";
                            global.jso.config.config.redirect_uri = $stateParams.backUrl;
                            global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}&wxComAppId=${resp.data.wxComAppId}&wxMpAppId=${resp.data.wxMpId}`;

                            console.log('global==============',global);


                            global.jso.getToken((token) => {
                                loginService.setAccessToken(token.access_token);
                                console.log("I got the token: ", token);
                                $timeout(function () {
                                    location.href = $stateParams.backUrl;
                                },0)
                            }, {});
                        }else {
                            let type = "PHONE";
                            global.jso.config.config.redirect_uri = $stateParams.backUrl;
                            global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}&wxMpAppId=${$scope.wxMpAppId}`;
                            global.jso.getToken((token) => {
                                loginService.setAccessToken(token.access_token);
                                console.log("I got the token: ", token);
                                $timeout(function () {
                                    location.href = $stateParams.backUrl;
                                },0)
                            }, {});
                        }
                    });
                }, function (resp) {
                    //error
                });


            }
        };





        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) && !ua.match(/windows/i)){
            $scope.wxLogin();
        } else {
           $scope.viewErroeShow = true;
        }










        // ////企业号登录
        // $scope.wxQyhLogin = function () {
        //     console.log(`${TAG} => wxQyhLogin`);
        //
        //     let ua = window.navigator.userAgent.toLowerCase();
        //     let type = "WX_QYH_SCAN";
        //     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
        //         type = "WX_QYH";
        //     }
        //     ////修改登录方式
        //     global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}`;
        //     global.jso.getToken((token) => {
        //         console.log(`${TAG} ====> I get the token`);
        //         vmd.at = token;
        //         console.log('111====',loginService.setAccessToken(token.access_token));
        //         loginService.setAccessToken(token.access_token);
        //         console.log('====',loginService.setAccessToken(token.access_token));
        //         $state.go("main.raffleApp.center.main");
        //         // $scope.getUserPhone();
        //     }, {});
        // };

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();

            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);

            location.href = location.protocol + conf.oauthPath + "/logout";
            console.log("logout");
        };


        // //获取用户手机号
        // $scope.getUserPhone = () => {
        //     console.log(`${TAG} => getUserPhone`);
        //
        //     $http({
        //         method: "GET",
        //         url: conf.oauthPath + "/api/user/info",
        //         //url: `https://login.kingsilk.net/local/16600/rs/api/user/info`,
        //         params: {},
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //         }
        //     }).then(function (resp) {
        //             console.log(`login  => /api/user/info => ${resp.data.data}`)
        //             if (!resp.data.data.phone) {
        //                 //如果手机号不存在,跳转到绑定手机号页面
        //                 $state.go("main.bindPhone");
        //             } else {
        //                 //获取用户brandApp列表
        //
        //                 console.log('11111111111')
        //
        //                 // TODO  1. 检查 localStorage 中是否有 raffleAppId, 且该 brandAppId真是有效，且当前用户是该 raffleApp 的员工，
        //                 //          则直接跳转，return
        //                 //          清空 localStorage 中的 raffleAppId
        //
        //                 if (loginService.getbrandAppId()) {
        //                     $state.go("main.raffleApp.home");
        //                 }
        //                 else {
        //                     $scope.getbrandAppList();
        //                 }
        //             }
        //         }, function () {
        //
        //         }
        //     );
        // };

        ///获取用户brandApp列表
        $scope.getbrandAppList = () => {
            console.log(`${TAG} => getbrandAppList`);
            $state.go("main.raffleApp.home", {raffleAppId: '59782691f8fdbc1f9b2c4323'}, {reload: true});

            // $http({
            //     method: "GET",
            //     url: conf.apiPath + "/raffleApp/bindPhoneAndList",
            //     params: {},
            //     headers: {
            //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //     }
            // }).then(function (resp) {
            //         if (resp.data.data.recList.length == 0) {
            //             alert("暂无绑定的组织");
            //             return;
            //         }
            //         //TODO 选择brandApp页面
            //         //loginService.setbrandAppId(resp.data.data.recList[0].id);
            //
            //         console.log('raffleAppId ============', resp.data.data.recList[0].id);
            //
            //         $state.go("main.raffleApp.shelves", {raffleAppId: resp.data.data.recList[0].id}, {reload: true});
            //         //$scope.fallbackPage();
            //     }, function (resp) {
            //         $mdDialog.show({
            //             template: html,
            //             parent: angular.element(document.body).find('#qh-net.kingsilk.qh.activity-wap-front'),
            //             clickOutsideToClose: true,
            //             controllerAs: "vmd",
            //             fullscreen: false,
            //             controller: ['$scope', '$mdDialog', '$rootScope', "$interval", function ($scope, $mdDialog, $rootScope, $interval) {
            //                 var vmd = this;
            //                 vmd.cancel = function () {
            //                     $mdDialog.cancel();
            //                 };
            //             }],
            //             //parent: '.ks-main '
            //         })
            //     }
            // );
        };

        ///返回上级
        $scope.fallbackPage = function () {
            console.log(`${TAG} => fallbackPage`);
            $state.go("main.raffleApp.home", null, {reload: true});
            //
            // if (history.length === 1) {
            //     $state.go("main.shelves", null, {reload: true});
            // } else {
            //     history.back();
            // }
        };

        //$scope.wxLogin();
    }
}

Controller
    .$inject = [
    '$scope',
    '$mdDialog',
    '$interval',
    '$rootScope',
    'alertService',
    '$state',
    '$stateParams',
    'loginService',
    '$timeout',
    '$http'
];

export
default
Controller;
