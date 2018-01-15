import conf from "../../../../../conf";


var $scope,
    loginService,
    $state,
    $http,
    $location,
    $stateParams;
class Controller {
    constructor(_$scope, _loginService, _$state, _$http, _$location, _$stateParams) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $http = _$http;
        $location = _$location;
        $stateParams = _$stateParams;
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.getUserInfo = function () {
            $http({
                method: "get",
                url: conf.oauthPath + "/api/user/info",
                // url: 'http://user/info',
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId ? $stateParams.brandAppId : '567b614be4b0f72f9f6cf02e',
                }
            }).then(function (data) {
                    $scope.user = data.data.data;
                    $scope.getPatter();
                }, function () {

                }
            );
        };
        $scope.getUserInfo();
        $scope.getPatter = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/check",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId ? $stateParams.brandAppId : '567b614be4b0f72f9f6cf02e',
                }
            }).then(function (data) {
                    $scope.patnerId = data.data.data;


                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.patnerId,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $stateParams.brandAppId,
                        }
                    }).then(function (resp) {
                            if(resp.data.data.shopName){
                                $scope.user.shopName = resp.data.data.shopName;
                            }else {
                                $scope.Prompt = '给店铺取个名称吧~';
                            }


                        }, function () {

                        }
                    );
                }
            );
        };



//退货
        $scope.getAddressStatus=function(){
            $http({

                method: "GET",   ///brandApp/{brandComId}/sysconf/{id}/brandComAddr get

                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123456/sysConf/brandAppAddr",

                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    console.log('addressstatus',resp);
                    if(resp.data.status=='10037'){
                        $scope.statusDetail='未设置';
                    }else{
                        $scope.statusDetail='退货地址';
                        $scope.id=resp.data.data;
                    }
                }, function () {

                }
            );
        };
        $scope.getAddressStatus();
        $scope.refundAddress=function(){
            if( $scope.statusDetail=='未设置'){
                $state.go("main.brandApp.center.refundAddress", null, {reload: true});
            }else{
                $state.go("main.brandApp.center.rfAddress",{id:$scope.id}, {reload: true});
            }

        };

        // 回退页面
        $scope.fallbackPage = function () {

            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                //history.back();
                $state.go("main.brandApp.center.main", null, {reload: true});
            }
        };

    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$http',
    '$location',
    '$stateParams',
];

export default Controller ;
