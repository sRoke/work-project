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
        loginService.loginCtl(true, $location.absUrl());


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
        }


        $scope.getData();





        $scope.auditing = function (type) {
            if(type){
                var msg = '确认审核通过?';
                var alertTs = '已通过!'
            }else {
                var msg = '确认审核拒绝?';
                var alertTs = '已拒绝!'
            }



            // https://kingsilk.net/vote/local/?_ddnsPort=17100#/voteApp/5a44552c1c087d73523611ec/vote/5a4495e21c087d0335e9fa73/myVote/5a45b6372126151632e7e890
            if($rootScope.wxComAppId && $rootScope.wxMpAppId){

                alertService.confirm(null, msg, '温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId,
                            data: {
                                wxComAppId:$rootScope.wxComAppId,
                                wxMpAppId:$rootScope.wxMpAppId,
                                status:type,
                                url:'https://' + conf.voteWapRootUrl + 'voteApp/'+$stateParams.voteAppId+'/vote/'+$stateParams.id+'/myVote/' + $stateParams.workId,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            console.log('resprespresprespresp', resp);
                            alertService.msgAlert('exclamation-circle', alertTs);
                            $scope.fallbackPage();
                        }, function () {
                            //error
                        });
                    }
                })






            }else {
                $http({
                    method: "GET",
                    url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId,
                    headers: {
                        "voteAppp-Id": $stateParams.voteAppId
                    },
                    data: {}
                }).success(function (resp) {
                    console.log('1221212121212121=====',resp)
                    $http({
                        method: "GET",
                        url: conf.pfApiPath + "/brandApp/" +resp.data,
                        headers: {
                            "brandApp-Id": resp.data
                        },
                        data: {}
                    }).success(function (resp) {
                        $rootScope.wxComAppId = resp.data.wxComAppId;
                        $rootScope.wxMpAppId = resp.data.wxMpId;



                        alertService.confirm(null, msg, '温馨提示').then(function (data) {
                            if(data){
                                $http({
                                    method: "PUT",
                                    url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId,
                                    data: {
                                        wxComAppId:$rootScope.wxComAppId,
                                        wxMpAppId:$rootScope.wxMpAppId,
                                        status:type,
                                        url:'https://' + conf.voteWapRootUrl + 'voteApp/'+$stateParams.voteAppId+'/vote/'+$stateParams.id+'/myVote/' + $stateParams.workId,
                                    },
                                    headers: {
                                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                                    }
                                }).then(function (resp) {
                                    console.log('resprespresprespresp', resp);
                                    alertService.msgAlert('exclamation-circle', alertTs);
                                    $scope.fallbackPage();
                                }, function () {
                                    //error
                                });
                            }
                        })
                        // $http({
                        //     method: "PUT",
                        //     url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/'+$stateParams.id + '/vote/voteWorks/' + $stateParams.workId,
                        //     data: {
                        //         wxComAppId:$rootScope.wxComAppId,
                        //         wxMpAppId:$rootScope.wxMpAppId,
                        //         status:type,
                        //         url: 'https://' +  conf.voteWapRootUrl + 'voteApp/'+$stateParams.voteAppId+'/vote/'+$stateParams.id+'/myVote/' + $stateParams.workId,
                        //     },
                        //     headers: {
                        //         'Authorization': 'Bearer ' + loginService.getAccessToken()
                        //     }
                        // }).then(function (resp) {
                        //     $scope.fallbackPage();
                        //     console.log('resprespresprespresp', resp);
                        //
                        // }, function () {
                        //     //error
                        // });

                    });

                });

            }
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
