import conf from "../../../../../conf";
import weui from 'weui.js';

import PhotoClip from 'photoclip';

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
    $rootScope,
    $filter,
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
                _$rootScope,
                _$filter,
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
        $rootScope = _$rootScope;
        $filter = _$filter;
        $scope.voteAppId = $stateParams.voteAppId;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());






        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {

                console.log('resprespresprespresp', resp);

                $scope.dataInfo = resp.data.data;
            }, function () {
                //error
            });
        };
        $scope.getData();




        $scope.currpage = 0;
        $scope.pageChange = function (currpage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/voteWork/' + $stateParams.workId + '/voteRecord',
                params: {
                    page:currpage,
                    size:5,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $scope.currpage = currpage;
                console.log('resprespresprespresp', resp);
                if(currpage){
                    $scope.voteList.number = resp.data.data.number;
                    for (var i=0;i< resp.data.data.content.length;i++){
                        $scope.voteList.content.push(resp.data.data.content[i])
                    }
                }else {
                    $scope.voteList = resp.data.data;
                }
            }, function () {
                //error
            });
        }
        $scope.pageChange(0);










        $scope.plusTicket = function () {
            $mdDialog.show({
                templateUrl: 'frends.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$http', '$stateParams', '$mdDialog', '$rootScope', function ($http, $stateParams, $mdDialog, $rootScope) {
                    var vmd = this;

                    vmd.addEventNum = function () {
                        if(!(/^[1-9]\d*$/.test(vmd.num))){
                            vmd.num = vmd.num.substring(0,vmd.num.length-1);
                            return false;
                        }
                    };


                    vmd.save = function () {
                        $mdDialog.hide({
                            status:true,
                            votes:vmd.num
                        });
                    };
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
                if(answer.status){
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId + '/addVote',
                        params: {
                            sourceType:'VIRTUAL',
                            votes:answer.votes
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken()
                        }
                    }).then(function (resp) {
                        console.log('resprespresprespresp', resp);
                        alertService.msgAlert('exclamation-circle', '加票成功');
                        $scope.getData();
                    }, function () {
                        //error
                    });
                }
            }, function () {
            });
        }




        $scope.toVoid = function () {
            alertService.confirm(null, '确定作废该作品?', '温馨提示').then(function (data) {
                if(data){
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId,
                        params: {},
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken()
                        }
                    }).then(function (resp) {
                        console.log('resprespresprespresp', resp);
                        alertService.msgAlert('exclamation-circle', '作废成功');
                        $scope.fallbackPage();
                    }, function () {
                        //error
                    });
                }
            })
        }
















        $scope.fallbackPage = function () {
            $state.go("main.voteApp.vote.sampleReels", {id:$stateParams.id}, {reload: true});
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
    '$rootScope',
    '$filter',
    '$templateCache'
];

export default Controller ;
