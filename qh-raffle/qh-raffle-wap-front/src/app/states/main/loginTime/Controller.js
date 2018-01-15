import conf from "../../../conf";
import html from "!html-loader?minimize=true!./dialog.html";

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

        ////////////////////////////////////变量定义
        const TAG = "main/loginTime ";
        console.log(`==> ${TAG}`);



        $scope.brandApps = [{
           id:11111111111111111,
            name:'钱皇',
        },{
            id:222222222222222,
            name:'三月三',
        },{
            id:3333333333333,
            name:'江南古韵',
        },{
            id:4444444444444,
            name:'更多',
        }];


        //获取用户手机号
        $scope.getUserPhone = () => {
            console.log(`${TAG} => getUserPhone`);
            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/info",
                //url: `https://login.kingsilk.net/local/16600/rs/api/user/info`,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                }
            }).then(function (resp) {
                    console.log(`login  => /api/user/info => ${resp.data.data}`);
                    if (!resp.data.data.phone) {
                        //如果手机号不存在,跳转到绑定手机号页面
                        $state.go("main.raffleApp.center.main");
                    } else {
                        //获取用户brandApp列表
                        // TODO  1. 检查 localStorage 中是否有 raffleAppId, 且该 brandAppId真是有效，且当前用户是该 raffleApp 的员工，
                        //          则直接跳转，return
                        //          清空 localStorage 中的 raffleAppId
                        if (loginService.getbrandAppId()) {
                            $state.go("main.raffleApp.home");
                        }
                        else {
                            $scope.getbrandAppList();
                        }
                    }
                }, function () {

                }
            );
        };

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
            //         $state.go("main.raffleApp.shelves", {raffleAppId: resp.data.data.recList[0].id}, {reload: true});
            //         //$scope.fallbackPage();
            //     }, function (resp) {
            //         // alertService.alert('您不是代理商无权访问', null, '确定');
            //         $mdDialog.show({
            //             template: html,
            //             parent: angular.element(document.body).find('#qh-activity-wap-front'),
            //             clickOutsideToClose: true,
            //             controllerAs: "vmd",
            //             fullscreen: false,
            //             controller: ['$scope', '$mdDialog', '$rootScope', "$interval", function ($scope, $mdDialog, $rootScope, $interval) {
            //                 var vmd = this;
            //                 vmd.cancel = function () {
            //                     $mdDialog.cancel();
            //                 };
            //             }],
            //         })
            //     }
            // );
        };

        // $scope.getUserPhone();

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);
            location.href = location.protocol + conf.oauthPath + "/logout";
            console.log("logout");
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
