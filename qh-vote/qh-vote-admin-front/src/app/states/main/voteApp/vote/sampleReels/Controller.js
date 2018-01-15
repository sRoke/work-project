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
        $scope.voteAppId = $stateParams.voteAppId;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = null;
            $scope.pageChange(0);
        };

        $scope.focus = function (status) {
            if(status){
                $scope.searchShow = true;
            }else {
                $scope.searchShow = false;
            }
        };


        $scope.status = $stateParams.status ? $stateParams.status : null;
        $scope.isClick = $stateParams.isClick ? $stateParams.isClick : "1";
        $scope.tabs = function (status, tableIndex, search) {
            $scope.status = status;
            // $scope.tableIndex = tableIndex;
            $scope.isClick = tableIndex;
            $scope.pageChange(0);
        };


        $scope.listDialogShow = function (vote) {
            for(var i = 0 ; i< $scope.voteList.content.length;i++){
                if($scope.voteList.content[i].id == vote.id){
                    vote.listDialog = !vote.listDialog;
                }else {
                    $scope.voteList.content[i].listDialog = false;
                }
            }
        };






        $http({
            method: "GET",
            url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/checkNum',
            params: {},
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken()
            }
        }).then(function (resp) {
                $scope.tatleNum = resp.data.data;
        }, function () {
                //error
        });















        $scope.currpage = 0;
        $scope.pageChange = function (currpage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks',
                params: {
                    page:currpage,
                    size:conf.pageSize,
                    status:$scope.status,
                    keyWord:$scope.keyWord,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $scope.currpage = currpage;
                if(currpage){
                    $scope.voteList.number = resp.data.data.number;
                    for (var i=0;i< resp.data.data.content.length;i++){
                        $scope.voteList.content.push(resp.data.data.content[i])
                    }
                }else {
                    $scope.voteList = resp.data.data;
                }
            }, function () {
                $scope.cliclSave = false;
            });
        }


        $scope.pageChange(0);


        
        $scope.viewDetail = function (data) {
            if(data.status == 'APPLYING'){
                $state.go("main.voteApp.vote.auditing",{id:$stateParams.id,workId:data.id},{reload: true})
            }else if(data.status == 'NORMAL'){
                $state.go("main.voteApp.vote.workInfo",{id:$stateParams.id,workId:data.id},{reload: true})
            }
        };




        $scope.fallbackPage = function () {
            // $state.go("main.voteApp.vote.voteHome", {}, {reload: true});
            if($stateParams.form == 'home'){
                $state.go("main.voteApp.vote.voteHome",{},{reload: true})
            }else if($stateParams.form == 'view'){
                $state.go("main.voteApp.vote.voteView",{id:$stateParams.id},{reload: true})
            }else {
                $state.go("main.voteApp.vote.voteHome",{},{reload: true})
            }
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
