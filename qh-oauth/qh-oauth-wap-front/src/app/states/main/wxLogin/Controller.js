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
                _$http) {
        /////////////////////////////////////通用注入
        $scope = _$scope;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $interval = _$interval;
        alertService = _alertService;
        $rootScope = _$rootScope;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;

        const TAG = "main/wxLogin";
        console.log(`==> ${TAG}`);

        // var vmd = $scope.vmd = {
        //     at: null,   // 获取的 access_token
        // };
        // if (loginService.getAccessToken()) {
        //     vmd.at = loginService.getAccessToken();
        // }
        //
        // ////登录类型，参数
        // const loginType = {
        //     WX: "loginType=WX&autoCreateUser",
        //     WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
        //     WX_QYH: "loginType=WX_QYH&autoCreateUser",
        //     WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
        //     PASSWORD: "loginType=PASSWORD"
        // };
        //
        // ////登录
        // $scope.wxLogin = function () {
        //     console.log(`${TAG} => wxLogin`);
        //     let ua = window.navigator.userAgent.toLowerCase();
        //     let type = "WX_SCAN";
        //     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
        //         type = "WX";
        //     }
        //     ////修改登录方式
        //     global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}`;
        //     global.jso.getToken((token) => {
        //         console.log(`${TAG} ====> I get the token`,token);
        //         vmd.at = token;
        //         loginService.setAccessToken(token.access_token);
        //         // location.href = $stateParams.backUrl;
        //         location.href = store.get('login_backUrl');
        //         store.remove('login_backUrl');
        //         // $scope.getUserPhone();
        //     }, {});
        // };
        // $scope.wxLogin();
        //
        //
        // // ////企业号登录
        // // $scope.wxQyhLogin = function () {
        // //     console.log(`${TAG} => wxQyhLogin`);
        // //
        // //     let ua = window.navigator.userAgent.toLowerCase();
        // //     let type = "WX_QYH_SCAN";
        // //     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
        // //         type = "WX_QYH";
        // //     }
        // //     ////修改登录方式
        // //     global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}`;
        // //     global.jso.getToken((token) => {
        // //         console.log(`${TAG} ====> I get the token`);
        // //         vmd.at = token;
        // //         console.log('111====',loginService.setAccessToken(token.access_token));
        // //         loginService.setAccessToken(token.access_token);
        // //         console.log('====',loginService.setAccessToken(token.access_token));
        // //         $state.go("main.brandApp.center.main");
        // //         // $scope.getUserPhone();
        // //     }, {});
        // // };
        //
        // ////登出
        // $scope.logout = () => {
        //     jso.wipeTokens();
        //
        //     loginService.setAccessToken(null);
        //     loginService.setBrandAppId(null);
        //
        //     location.href = location.protocol + conf.oauthPath + "/logout";
        //     console.log("logout");
        // };
        //
        //
        // // //获取用户手机号
        // // $scope.getUserPhone = () => {
        // //     console.log(`${TAG} => getUserPhone`);
        // //
        // //     $http({
        // //         method: "GET",
        // //         url: conf.oauthPath + "/api/user/info",
        // //         //url: `https://login.kingsilk.net/local/16600/rs/api/user/info`,
        // //         params: {},
        // //         headers: {
        // //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        // //         }
        // //     }).then(function (resp) {
        // //             console.log(`login  => /api/user/info => ${resp.data.data}`)
        // //             if (!resp.data.data.phone) {
        // //                 //如果手机号不存在,跳转到绑定手机号页面
        // //                 $state.go("main.bindPhone");
        // //             } else {
        // //                 //获取用户brandApp列表
        // //
        // //                 console.log('11111111111')
        // //
        // //                 // TODO  1. 检查 localStorage 中是否有 brandAppId, 且该 BrandAppId真是有效，且当前用户是该 brandApp 的员工，
        // //                 //          则直接跳转，return
        // //                 //          清空 localStorage 中的 brandAppId
        // //
        // //                 if (loginService.getBrandAppId()) {
        // //                     $state.go("main.brandApp.home");
        // //                 }
        // //                 else {
        // //                     $scope.getbrandAppList();
        // //                 }
        // //             }
        // //         }, function () {
        // //
        // //         }
        // //     );
        // // };
        //
        // ///获取用户brandApp列表
        // $scope.getbrandAppList = () => {
        //     console.log(`${TAG} => getbrandAppList`);
        //     $state.go("main.brandApp.home", {brandAppId: '59782691f8fdbc1f9b2c4323'}, {reload: true});
        //
        //     // $http({
        //     //     method: "GET",
        //     //     url: conf.apiPath + "/brandApp/bindPhoneAndList",
        //     //     params: {},
        //     //     headers: {
        //     //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //     //     }
        //     // }).then(function (resp) {
        //     //         if (resp.data.data.recList.length == 0) {
        //     //             alert("暂无绑定的组织");
        //     //             return;
        //     //         }
        //     //         //TODO 选择brandApp页面
        //     //         //loginService.setBrandAppId(resp.data.data.recList[0].id);
        //     //
        //     //         console.log('brandAppId ============', resp.data.data.recList[0].id);
        //     //
        //     //         $state.go("main.brandApp.shelves", {brandAppId: resp.data.data.recList[0].id}, {reload: true});
        //     //         //$scope.fallbackPage();
        //     //     }, function (resp) {
        //     //         $mdDialog.show({
        //     //             template: html,
        //     //             parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-wap-front'),
        //     //             clickOutsideToClose: true,
        //     //             controllerAs: "vmd",
        //     //             fullscreen: false,
        //     //             controller: ['$scope', '$mdDialog', '$rootScope', "$interval", function ($scope, $mdDialog, $rootScope, $interval) {
        //     //                 var vmd = this;
        //     //                 vmd.cancel = function () {
        //     //                     $mdDialog.cancel();
        //     //                 };
        //     //             }],
        //     //             //parent: '.ks-main '
        //     //         })
        //     //     }
        //     // );
        // };
        //
        // ///返回上级
        // $scope.fallbackPage = function () {
        //     console.log(`${TAG} => fallbackPage`);
        //     $state.go("main.brandApp.home", null, {reload: true});
        //     //
        //     // if (history.length === 1) {
        //     //     $state.go("main.shelves", null, {reload: true});
        //     // } else {
        //     //     history.back();
        //     // }
        // };

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
    '$http'
];

export
default
Controller;
