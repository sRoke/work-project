import conf from "../../../../conf";
import store from "store";
import E from "wangeditor";


var $scope,
    $http,
    authService,
    $state,
    $rootScope,
    $location,
    alertService,
    loginService,
    errorService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _authService,
                _$state,
                _$log,
                _$location,
                _alertService,
                _loginService,
                _errorService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $location = _$location;
        alertService = _alertService;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        $rootScope = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        // console.log("====================", $stateParams);
        //
        // console.log(1111111111);
        //定义链接地址
        $scope.linkAddress = conf.linkPath + $scope.brandAppId + "/home";

        $scope.copyUrl2 = function () {
            var Url2 = document.getElementById("addr");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            return errorService.error("链接地址已复制好，可贴粘。", null);
        };
        $scope.copy = function () {
            var Url2 = document.getElementById("addr");
            Url2.select(); // 选择对象
        };
        $scope.addressShow = false;
        $scope.isShow = function () {
            $scope.addressShow = true;
        };
        $scope.isHide = function () {
            $scope.addressShow = false;
        };

        $scope.chs = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId,
                params: {}
            }).success(function (response) {
                $rootScope.wxComAppId = response.data.wxComAppId;
                store.set(conf.wxComAppId, $rootScope.wxComAppId);

                if (response.data.wxMpId && response.data.wxComAppId) {
                    $rootScope.wxMpAppId = response.data.wxMpId;
                    $scope.data = response.data;
                    $http({
                        method: "GET",
                        url: conf.wxPath + "/wxCom/" + $rootScope.wxComAppId + "/mp/" + $rootScope.wxMpAppId,
                        params: {}
                    }).success(function (response) {
                        $scope.item = response.data;
                    });
                }

                /* $http({
                 method:"GET",
                 url:conf.wxPath + "/wxCom/"+conf.wxAppid +"/mp/"+response.data.wxMpId,
                 params: {
                 //  Com/{wxComAppId}/mp/{wxMpAppId}

                 }
                 }).success(function (response) {
                 console.log(55555,response);
                 $scope.item=response.data;
                 });*/
            });

        };

        $scope.chs();


        //去授权
        $scope.setAuthorize = function () {
            console.log('111111');
            $http({
                method: "GET",
                url: conf.wxPath + "/wxCom/" + $rootScope.wxComAppId + "/auth/url",
                params: {
                    //backUrl:location.href
                    backUrl:$location.absUrl().split("home")[0] + "home?a=aa"
                }
            }).success(function (response) {
                console.log(response);
                if (response.status == '200') {
                    location.href = response.data;
                }
            });
        };
        // console.log(1,$location.search()['auth_code']);
        // console.log(2,$location.search()["expires_in"]);
        //重新授权的路径
        // console.log(3,$location.absUrl().split("?")[0]);
        //保存授权信息
        if ($location.search()['auth_code'] && $location.search()["expires_in"]) {
            var wxComAppId = store.get(conf.wxComAppId);
            $http({
                method: "GET",
                url: conf.wxPath + "/wxCom/" + wxComAppId + "/auth",
                params: {
                    expiresIn: $location.search()["expires_in"],
                    authCode: $location.search()['auth_code'],
                    wxComAppId: wxComAppId
                }
            }).success(function (response) {
                console.log(111111, response);
                // /brandApp/{brandAppId}/wxMpId/{wxMpId}
                $http({
                    method: "PUT",
                    url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId + "/wxMpId/" + response.data,
                    params: {}
                }).success(function (response) {
                    $scope.chs();
                });


            });

        }


        // // 创建编辑器
        // var editor = new E('#div1')
        // // var $text1 = $('#text1')
        // // editor.customConfig.onchange = function (html) {
        // //     // 监控变化，同步更新到 textarea
        // //     $text1.val(html)
        // //     $scope.vvvv = editor.txt.html();
        // //     console.log('vvvv1', $scope.vvvv)
        // //
        // // };
        // editor.create()
        // // 初始化 textarea 的值
        // // $text1.val(editor.txt.html())
        // $scope.showTxt = false;
        // $scope.yuanma= function () {
        //     $scope.showTxt = !$scope.showTxt;
        //     $scope.vvvv = editor.txt.html();
        // };
        //
        // $scope.aaa = function () {
        //     console.log('vvvv', $scope.vvvv)
        //     editor.txt.html($scope.vvvv)
        // }


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    'authService',
    '$state',
    '$rootScope',
    '$location',
    'alertService',
    'loginService',
    'errorService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
