import conf from "../../../../../../conf";
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
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        // $scope.go = function (state) {
        //     $state.go(state);
        // };
        $scope.items=[];
        $scope.page=1;
        $scope.getInfo=function (page) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/refund",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    page:page?page:$scope.page-1,
                    size:conf.pageSize,
                },
            }).then(function (resp) {
                // console.log('resp---info-list',resp.data.data);
                if(resp.data.status=='10026'){
                    $scope.noOrder=true;
                }else{
                    $scope.noOrder=false;
                    $scope.items=$scope.items.concat(resp.data.data.content);
                    $scope.dataNum=resp.data.data;
                    $scope.page=$scope.dataNum.number+1;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
                $state.go("main.brandApp.store.personalCenter.centerHome", {brandAppId:$stateParams.brandAppId}, {reload: true});
            // } else {
            //     history.back();
            // }
        };
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
