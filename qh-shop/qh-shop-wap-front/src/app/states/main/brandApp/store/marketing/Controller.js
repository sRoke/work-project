import conf from "../../../../../conf";


var $scope,
    $http,
    $state,
    Upload,
    $templateCache,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    authService,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _Upload,
                _$templateCache,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        Upload = _Upload;
        authService = _authService;
        $stateParams = _$stateParams;
        $templateCache = _$templateCache;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId);
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;


        $scope.getBargainAppId = function () {
            $http({
                method: "GET",
                url: conf.bargainApiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/bargain/home',
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.bargainAppId = resp.data.data;
                // console.log('===============',resp);
            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getBargainAppId();



        $scope.getVoteAppId = function () {
            $http({
                method: "GET",
                url: conf.voteApiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/vote/home',
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.voteAppId = resp.data.data;
                // console.log('===============',resp);
            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getVoteAppId();

        $scope.getRaffleAppId = function () {
            $http({
                method: "GET",
                url: conf.raffleApiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/raffle/home',
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.raffleAppId = resp.data.data;
                // console.log('===============',resp);
            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getRaffleAppId();











        $scope.toActivity = function (type) {
            if(type == 'bargain'){
                if($scope.bargainAppId){
                    location.href = conf.bargainRootUrl + 'bargainApp/' + $scope.bargainAppId + '/bargain/bargainHome';
                }
            }else if(type == 'vote'){
                if($scope.voteAppId){
                    location.href = conf.voteRootUrl + 'voteApp/' + $scope.voteAppId + '/vote/voteHome';
                }
            }else if(type == 'raffle'){
                if($scope.raffleAppId){
                    location.href = conf.raffleRootUrl + 'raffleApp/' + $scope.raffleAppId + '/raffle/raffleHome';
                }
            }
        };




        /*返回上级*/
        $scope.fallbackPage = function () {
           $state.go("main.brandApp.store.home",{},{reload:true})
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'Upload',
    '$templateCache',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope'
];

export default Controller ;
