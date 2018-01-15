import conf from "../../../../../conf";
// import "jquery";
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    Upload,
    wxService,
    $interval,
    alertService,
    $templateCache;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _Upload,
                _wxService,
                _$interval,
                _alertService,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        alertService = _alertService;
        $interval = _$interval;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        wxService = _wxService;
        $stateParams = _$stateParams;
        $scope.raffleAppId = $stateParams.raffleAppId;
        /////////////////////////////////
        //loginService.loginCtl(true, $location.absUrl());

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = [];
            $scope.pageChange(0);
        };

        $scope.focus = function (status) {
            if (status) {
                $scope.searchShow = true;
            } else {
                $scope.searchShow = false;
            }
        };
        //调出选择框
        $scope.maskShow=function () {
            $scope.selectShow=true;
            $scope.maskDrop=true;
        };
        //关闭选择框
        $scope.maskHide=function () {
            $scope.selectShow=false;
            $scope.maskDrop=false;
        };
        $scope.status = $stateParams.status ? $stateParams.status : "all";
        $scope.selectStatus=function (status) {
            $scope.status=status;
            $scope.maskHide();
            $scope.pageChange(0);
        };


        $scope.currpage = 0 ;
        $scope.pageChange = function (currpage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/admin/' + $stateParams.id + '/getraffleRecord',
                params: {
                    page:currpage?currpage:$scope.currpage,
                    size:conf.pageSize,
                    keyWord:$scope.keyWord,
                    handleStatus:$scope.status
                },
                headers: {
                   'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.currpage = currpage;
                    console.log('resprespresprespresp', resp);
                    if(currpage){
                        $scope.pageList.number = resp.data.data.number;
                        for (var i=0;i< resp.data.data.content.length;i++){
                            $scope.pageList.content.push(resp.data.data.content[i])
                        }
                    }else {
                        $scope.pageList = resp.data.data;
                    }
                }, function () {
                    //error
                    $scope.cliclSave = false;
                }
            );
        };
        $scope.pageChange(0);


        $scope.sendLottery=function (recordId) {
            $state.go("main.raffleApp.raffle.priceList", {id:$stateParams.id,recordId:recordId}, {reload: true});
        };

        $scope.fallbackPage = function () {
            $state.go("main.raffleApp.raffle.raffleHome", {}, {reload: true});
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$q',
    'Upload',
    'wxService',
    '$interval',
    'alertService',
    '$templateCache'
];

export default Controller ;
