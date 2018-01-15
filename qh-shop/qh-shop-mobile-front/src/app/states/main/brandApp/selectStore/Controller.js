import conf from "../../../../conf";
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $http = _$http;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };

        $http({
            method: "GET",
            url: conf.apiPath + "/brandAppId/" + $stateParams.brandAppId + '/home',
            // url: conf.apiPath + '/home',
            params: {},
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $stateParams.brandAppId
            }
        }).then(function (resp) {
            if(resp.data.data.length ==1){
                $state.go('main.brandApp.store.home',{storeId : resp.data.data[0].shopId},{reload:true})
            }else if(resp.data.data.length ==0){
                $scope.shopList = [];
            }else if(resp.data.data.length >1){
                $scope.shopList = resp.data.data;
            }
            // console.log('resp-----------------------', resp);
        }, function (error) {

        });








    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$http',
];

export default Controller ;
