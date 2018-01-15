import conf from "../../../../conf";
import URI from "urijs";

var $scope,
    $rootScope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    $httpParamSerializer,
    $window,
    wxService;

class Controller {
    constructor(_$scope,
                _$rootScope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _$httpParamSerializer,
                _$window,
                _wxService) {

        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        $rootScope = _$rootScope;
        $httpParamSerializer = _$httpParamSerializer;
        $window = _$window;
        wxService = _wxService;

        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());

        // 先检查是否已经登录


        $scope.checkLogin = function () {
            $http({
                method: "GET",
                url: conf.oauthPath + "/s/user/info"
            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                location.href =  $stateParams.backUrl; //已经登录返回到/oauth/aothorize进行授权
            }, function (err) {
                console.log("ERROR", err);
                if(err.data.status == 401){
                    $scope.login();
                }
            });
        };


        //  https://kingsilk.net/oauth/local/?_ddnsPort=14100#/login/wx?a=aa&b==bb
        //console.log("当前URL是", $window.location.href); //拿到的是第一次进入该域名下的路径
        console.log("当前URL是absUrl",$location.absUrl()); //拿到当前页面的路径
        var uri = new URI($location.absUrl());
        var uriSearch = uri.search(true);

        console.log("url.search(true) = ", uriSearch);


        var fragmentUri = new URI(uri.fragment());
        var fragmentUriSearch = fragmentUri.search(true);
        console.log("fragmentUri.search(true) = ", fragmentUriSearch);


        // 在当前URL的基础上，追加额外参数 "fromWx"      ------------用来传递给wxfj的方法
        function createBackUrl() {
            var url2 = uri.clone();
            var fragmentUri2 = new URI(url2.fragment());
            var fragmentUriSearch2 = fragmentUri.search(true);
            fragmentUriSearch2.fromWx = true;
            fragmentUri2.search(fragmentUriSearch2);

            url2.fragment(fragmentUri2.toString());
            return url2.toString();
        }


        // 检查是否是从微信回来的，如果是，则应该执行 调用 后台API，完成登录。 -----------no.1执行的方法
        $scope.login = function () {

            if (!(uriSearch.fromWx) && !(fragmentUriSearch.fromWx)) {
                console.log("并不是从微信服务器返回的，无法登录");
                $scope.getUrl();           //---------------------------------------调用微信fj
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
            console.log('state',state);
            $http({
                method: "POST",
                url: conf.oauthPath + "/s/login/wxMp",
                // url : "//kingsilk.net/wx/local/14100/rs/api//wxCom/{wxComAppId}/mp/{wxMpAppId}/user/auth",
                data: {
                    wxMpAppId: $stateParams.wxMpAppId,
                    code: code,
                    state: state
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }

            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                location.href =  $stateParams.backUrl;
                // if (!(resp.data.phoneVerifiedAt )) {
                //     console.log("尚未绑定手机号，将去绑定。");
                // }
            }, function (err) {
                console.log("ERROR", err)
                if(err.data.status == 10001){
                  console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                    $state.go("main.reg.phone", {
                        backUrl:$location.absUrl().replace('/login/wxMp','/user/bindWx'),
                    });
                }
            });
        };


        // 微信第三方平台 "蚕丝被推广"
        // $scope.wxComAppId = "wxcee33ae0776a9a96";
        // 微信公众号 "钱皇推广测试"
        // $stateParams.wxMpAppId = "wx7cc0b96add4254b1";   //从子系统通过传参数获取
        $scope.getUrl = function () {
            $http({
                method: "GET",
                url: conf.wx4jPath+"/wxMp/" + $stateParams.wxMpAppId + "/user/auth/url",
                // url : "//kingsilk.net/wx/local/14100/rs/api//wxCom/{wxComAppId}/mp/{wxMpAppId}/user/auth/url",
                params: {
                    wxMpAppId: $stateParams.wxMpAppId,
                    redirectUri: createBackUrl(),
                    scopes: ["snsapi_base", "snsapi_userinfo"],
                    scan: !wxService.isInWx(),
                }
            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                $scope.url = resp.data.data;
                $window.location.href = $scope.url;
            }, function (err) {
                console.log("ERROR", err)
            });
        };
    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    '$httpParamSerializer',
    "$window",
    "wxService",
];

export default Controller ;
