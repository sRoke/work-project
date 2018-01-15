import conf from "../../../../conf";
// import 'angular-bootstrap';
import store from "store";

var $scope,
    $rootScope,
    authService,
    loginService,
    $stateParams,
    $filter,
    $location,
    $http;

class Controller {
    constructor(_$scope,_$rootScope, _$http, _loginService, _authService, _$filter, _$location, _$stateParams) {
        $scope = _$scope;
        $rootScope=_$rootScope;
        $http = _$http;
        $filter = _$filter;
        $location = _$location;
        $stateParams = _$stateParams;
        loginService = _loginService;
        authService = _authService;
        var vm = this;


        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_U = authService.hasAuthor("ORDER_U");    //发货
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.brandAppId = $stateParams.brandAppId;
        //初始化
        // /brandApp/{id}
        // $rootScope.data={};
        $scope.chs = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId,
                params: {}
            }).success(function (response) {
                console.log(6989595985895, response);
                $scope.data = response.data;
                $scope.wxComAppId=response.data.wxComAppId;
                $rootScope.wxComAppId = response.data.wxComAppId;
                store.set(conf.wxComAppId, $rootScope.wxComAppId);
                //
                if (response.data.wxMpId && response.data.wxComAppId) {
                    $rootScope.wxMpAppId = response.data.wxMpId;
                    console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                    $http({
                        method: "GET",
                        url: conf.wxPath + "/wxCom/" + $rootScope.wxComAppId + "/mp/" + $rootScope.wxMpAppId,
                        params: {}
                    }).success(function (response) {
                        $scope.item = response.data;
                    });
                }
            });

        };

        $scope.chs();


        //去授权
        $scope.setAuthorize = function () {
            console.log(111111111111111, $location.absUrl().split("publicNumber")[0] + "publicNumber",$scope.wxComAppId);
            $http({
                method: "GET",
                url: conf.wxPath + "/wxCom/" + $scope.wxComAppId + "/auth/url",
                params: {
                    //backUrl:location.href

                    backUrl: $location.absUrl().split("publicNumber")[0] + "publicNumber?a=aa"

                }
            }).success(function (response) {
                console.log(2222222222, response.data);
                // var myString = response.data;
                // var w = myString.indexOf("redirect_uri");
                // console.log('wwwwwwwwww',w)
                // var l = myString.substring(myString.indexOf("redirect_uri") + 13, myString.indexOf("redirect_uri").length);
                // // var backurl =

                if (response.status == '200') {
                    location.href = response.data;
                }
            });
        };
        // console.log(1,$location.search()['auth_code']);
        // console.log(2,$location.search()["expires_in"]);
        //重新授权的路径
        // console.log(3,$location.absUrl().split("?")[0]);
        //保存授权信
        if ($location.search()['auth_code'] && $location.search()["expires_in"]) {
            var wxComAppId = store.get(conf.wxComAppId);
            console.log('wxComAppId ', wxComAppId);

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




    }
}

Controller.$inject = [
    '$scope', '$rootScope','$http', 'loginService', 'authService', '$filter', '$location', '$stateParams'
];

export default Controller ;
