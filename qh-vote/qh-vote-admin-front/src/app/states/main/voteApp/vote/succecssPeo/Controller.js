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
        // wxService.init();

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




        $scope.currpage = 0 ;
        $scope.pageChange = function (currpage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/getSuccessUser',
                params: {
                    page:currpage,
                    size:conf.pageSize,
                    keyWord:$scope.keyWord,
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
                }
            );
        };
        $scope.pageChange(0);

























        $scope.friendsList = function (data) {
            if(data.payStatus == false){
                return;
            }else if(data.payType == 'LINEBUY'){
                $mdDialog.show({
                    templateUrl: 'frends.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                    fullscreen: false,
                    controller: ['$http', '$stateParams', '$mdDialog', '$rootScope', function ($http, $stateParams, $mdDialog, $rootScope) {
                        var vmd = this;
                        vmd.data = data;

                        console.log('111111111111==',vmd.data)

                        vmd.getData = function () {
                            $http({
                                method: "GET",
                                url:conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/user/' + data.userId  + '/order/show',
                                // url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/user/' + data.userId + '/order/takeOff',
                                params: {},
                                headers: {
                                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                                }
                            }).then(function (resp) {
                                    console.log('resprespresprespresp', resp);
                                    vmd.dataInfo = resp.data.data;
                                }, function () {
                                    //error
                                }
                            );
                        };
                        vmd.getData();


                        vmd.save = function () {
                            console.log(vmd.dataInfo.memo);
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/user/' + data.userId + '/order/takeOff',
                                params: {
                                    memo:vmd.dataInfo.memo
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                                }
                            }).then(function (resp) {
                                    console.log('resprespresprespresp', resp);

                                    $mdDialog.hide('true');
                                }, function () {
                                    //error
                                }
                            );
                        }










                        vmd.cancel = function () {
                            return $mdDialog.cancel();
                        };


                    }],
                    controllerAs: "vmd"
                }).then(function (answer) {
                    if(answer){
                        $scope.pageChange(0);
                    }
                }, function () {
                });
            }else {
                $state.go("main.voteApp.vote.priceList",{id:$stateParams.id,userId:data.userId},{reload: true})
            }
        };

        // $scope.friendsList('123123')




        $scope.fallbackPage = function () {
            // $state.go("main.voteApp.vote.voteHome", {}, {reload: true});
            if($stateParams.form == 'home'){
                $state.go("main.voteApp.vote.voteHome",{},{reload: true})
            }else if($stateParams.form == 'detail'){
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
