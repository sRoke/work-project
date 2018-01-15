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
                console.log("ERROR", err)
                if(err.data.status == 401){
                    $scope.login();
                }
            });
        };
        $scope.checkLogin();



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
                $scope.getUrl();
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
                method: "POST",
                url: conf.oauthPath + "/s/login/wxComMp",
                data: {
                    wxComAppId: $stateParams.wxComAppId,
                    wxMpAppId: $stateParams.wxMpAppId,
                    code: code,
                    state: state
                },
                transformRequest: $httpParamSerializer,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }

            }).then(function (resp) {
                console.log(resp);
                location.href =  $stateParams.backUrl;
            }, function (err) {
                if(err.data.status == 10001){
                    // console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                    $state.go("main.reg.phone", {
                        backUrl:$location.absUrl().replace('/login/wxComMp','/user/bindWx'),
                    });
                }
            });
        };


        // 微信第三方平台 "蚕丝被推广"
        // $scope.wxComAppId = "wx2ad540a81da1f8db";
        // 微信公众号 "钱皇推广测试"
        // $scope.wxMpAppId = "wx7cc0b96add4254b1";
        $scope.getUrl = function () {
            $http({
                method: "GET",
                // url: "https://kingsilk.net/wx/local/14100/rs/api/wxCom/" + $scope.wxComAppId + "/mp/" + $scope.wxMpAppId + "/user/auth/url",
                url : conf.wx4jPath+"/wxCom/"+ $stateParams.wxComAppId +"/mp/" + $stateParams.wxMpAppId + "/user/auth/url",
                params: {
                    // wxMpAppId: $scope.wxMpAppId,
                    redirectUri: createBackUrl(),
                    scopes: ["snsapi_base", "snsapi_userinfo"],
                    scan: !wxService.isInWx(),
                }
            }).then(function (resp) {
                $scope.url = resp.data.data;
                console.log("SUCCESS - ",resp);
                $window.location.href = $scope.url;
            }, function (err) {
                // alert("3333333ERROR" + JSON.stringify(err));
                console.log("ERROR", err)
            });
        };

        $scope.goWx = function () {
            $window.location.href = $scope.url;
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
    'wxService',
];

export default Controller ;
