import conf from "../../../../../../conf";
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    alertService,
    $rootScope,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _alertService,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location = _$location;
        alertService = _alertService;
        $rootScope = _$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;


        $scope.getChange = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/" + $stateParams.storeId + '/home/getName',
                params: {},
                headers: {
                    "brandApp-Id": $stateParams.brandAppId
                },
            }).then(function (resp) {
                // console.log('111111111111resp---order--create', resp);
                if (resp.data.data) {
                    $rootScope.rootScopeShopName = resp.data.data;
                } else {
                    $rootScope.rootScopeShopName = '我的小店';
                }


            }, function (resp) {
                //error
            });
        };
        $scope.getChange();

        $scope.getNum = function () {
            ///brandApp/{brandAppId}/shop/{shopId}/home/getNum
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/" + $stateParams.storeId + '/home/getNum',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function (resp) {
                console.log('111111111111resp---order--create', resp);

                $scope.numData = resp.data.data;


            }, function (resp) {
                //error
            });
        };
        $scope.getNum();


        //获取微信id
        $scope.getWxInfo = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $stateParams.brandAppId,
                headers: {
                    "brandAppp-Id": $scope.brandAppId
                },
                params: {}
            }).success(function (resp) {
                // console.log(resp);
                if (resp.data.wxMpId && resp.data.wxComAppId) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    if (loginService.getAccessToken()) {
                        $scope.getInfo();
                    }
                    // console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                }
            });
        };


        $scope.getInfo = function () {
            ///brandApp/{brandAppId}/shop/{shopId}/member
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/member/add",
                params: {
                    wxComAppId: $rootScope.wxComAppId,
                    wxMpAppId: $rootScope.wxMpAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {


                $scope.shopData = resp.data;
                // console.log('$scope.phone', $scope.shopData)

            }, function (resp) {
                //error
                //alertService.msgAlert("exclamation-circle", resp.data.message);
            });
        };


        $scope.getWxInfo();



        $scope.goto = function (data) {
            var path = 'https:'+conf.shopUrl+'brandApp/'+$scope.brandAppId+data;
            console.log(path);
            window.location=path;
        };



        $scope.goLogin = function () {
            $state.go("main.wxLogin", ({backUrl: $location.absUrl(), brandAppId: $stateParams.brandAppId}))
        }
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    'alertService',
    '$rootScope',
    '$http',
];

export default Controller ;
