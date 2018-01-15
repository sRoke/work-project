import conf from "../../../../conf";

var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $rootScope,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope = _$rootScope;
        $http = _$http;
        //loginService.loginCtl(true,$location.absUrl());
        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/" + $stateParams.storeId + '/home/getName',
            params: {},
            headers: {
                "brandApp-Id": $stateParams.brandAppId
            },
        }).then(function (resp) {
            // console.log('111111111111resp---order--create', resp);
            if(resp.data.data){
                $rootScope.rootScopeShopName = resp.data.data;
            }else {
                $rootScope.rootScopeShopName = '我的小店';
            }


        }, function (resp) {
            //error
        });



        if(loginService.getAccessToken()){
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
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/" + $stateParams.storeId + "/member/add",
                        params: {
                            wxComAppId: $rootScope.wxComAppId,
                            wxMpAppId: $rootScope.wxMpAppId
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $stateParams.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA===========', resp)
                    }, function (resp) {
                        //alertService.msgAlert("exclamation-circle", resp.data.message);
                    });
                }
            });
        }else {
            // console.log('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb===========',)
        }







    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
