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

        if(window.screen.availWidth<380){
            $scope.timeHide = true;
        }




        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id ,
                params: {},
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('resprespresprespresp', resp);
                $rootScope.rootVoteDataEdit = resp.data.data;

            }, function () {

            });
        };





        $scope.listDialog1 = false;
        $scope.listDialog2 = false;
        $scope.listDialog3 = false;
        $scope.listDialogShow = function (num) {

            if(num==1){
                $scope.listDialog1 = !$scope.listDialog1;
            }else if(num==2){
                $scope.listDialog2 = !$scope.listDialog2;
            }else if(num==3){
                $scope.listDialog3 = !$scope.listDialog3;
            }
        };






        if($stateParams.form != 'text'){
            $scope.getData();
            console.log( 'rootVoteDataEdit222222222222=========',$rootScope.rootVoteDataEdit);
        }else {
            console.log( 'rootVoteDataEdit1111111111=========',$rootScope.rootVoteDataEdit);
            if (!$rootScope.rootVoteDataEdit) {
                $rootScope.rootVoteDataEdit = {};
            }
        }







        $scope.changeFollow = function (type) {
            $rootScope.rootVoteDataEdit.forceFollow = type ;
        };

        $scope.changeCheck = function (type) {
            $rootScope.rootVoteDataEdit.mustCheck = type ;
        };


        $scope.chooseTime = function (type) {
            $state.go("main.voteApp.vote.chooseTime", {
                id:$stateParams.id,
                type: type,
                from: 'edit'
            })
        };


        $scope.AddEventInput = function (type) {
            var reg=/^[1-9]\d*$/;
            if(type == 'maxVotePerDay'){
                if(!reg.test($rootScope.rootVoteDataEdit.maxVotePerDay)){
                    $rootScope.rootVoteDataEdit.maxVotePerDay = $rootScope.rootVoteDataEdit.maxVotePerDay.substring(0,$rootScope.rootVoteDataEdit.maxVotePerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'totalVoteCount'){
                if(!reg.test($rootScope.rootVoteDataEdit.totalVoteCount)){
                    $rootScope.rootVoteDataEdit.totalVoteCount = $rootScope.rootVoteDataEdit.totalVoteCount.substring(0,$rootScope.rootVoteDataEdit.totalVoteCount.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'maxTicketPerDay'){
                if(!reg.test($rootScope.rootVoteDataEdit.maxTicketPerDay)){
                    $rootScope.rootVoteDataEdit.maxTicketPerDay = $rootScope.rootVoteDataEdit.maxTicketPerDay.substring(0,$rootScope.rootVoteDataEdit.maxTicketPerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'votePeoplePerDay'){
                if(!reg.test($rootScope.rootVoteDataEdit.votePeoplePerDay)){
                    $rootScope.rootVoteDataEdit.votePeoplePerDay = $rootScope.rootVoteDataEdit.votePeoplePerDay.substring(0,$rootScope.rootVoteDataEdit.votePeoplePerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }
        };






        $scope.choosePhote = false;
        // 图片裁剪
        var pc = new PhotoClip('#clipArea', {
            size: [260, 260],
            outputSize: 640,
            // adaptive: ['70','40'],
            file: '#file,#file2',
            view: '#view',
            ok: '#clipBtn',
            style: {
                maskColor: 'rgba(0,0,0,0.7)',
                // jpgFillColor:''
            },
            loadStart: function () {
                console.log('开始读取照片');
            },
            loadComplete: function () {
                console.log('照片读取完成', $scope);
                $scope.choosePhote = true;
                $scope.$apply();
            },
            done: function (dataURL) {
                console.log('base64裁剪完成,正在上传');
                $scope.saveImg(dataURL);
            },
            fail: function (msg) {
                alert(msg);
            }
        });
        $scope.saveImg = function (dataUrl) {
            $http({
                method: "POST",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
                data: {
                    base64DataUrl: dataUrl
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.dataUrl = resp.data.data;
                    $scope.getImg($scope.dataUrl);
                    console.log(resp.data.data)

                }, function () {
                    //error
                }
            );
        };
        $scope.getImg = function (id) {
            $http({
                method: "GET",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    console.log(resp.data.data);
                    // $scope.imgs = resp.data.data.cdnUrls[0].url ;
                    $rootScope.rootVoteDataEdit.primaryImgUrl = resp.data.data.cdnUrls[0].url;
                    console.log('12333333333==============',$rootScope.rootVoteDataEdit.primaryImgUrl);
                    $scope.choosePhote = false;
                }, function () {
                    //error
                }
            );
        };
        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        };


        $scope.goEdit = function (type) {
            // var json = angular.toJson($scope.skuData);
            $state.go("main.voteApp.vote.textImg", {
                type: type,
                from: 'edit',
                id:$stateParams.id,
            })
        }


        $scope.cliclSave = false;

        $scope.save = function () {
            if ($scope.cliclSave) {
                return
            } else {
                $scope.cliclSave = true;
            }
            console.log('result================', $rootScope.rootVoteDataEdit);


            if (!$rootScope.rootVoteDataEdit.primaryImgUrl) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择活动主图')
            }


            // if (!$rootScope.rootVoteDataEdit.shopName) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写店铺名称')
            // }


            if (!$rootScope.rootVoteDataEdit.voteName) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写活动名称')
            }

            if (!$rootScope.rootVoteDataEdit.signUpStartTime || !$rootScope.rootVoteDataEdit.signUpEndTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择报名时间')
            }
            if (!$rootScope.rootVoteDataEdit.voteStartTime || !$rootScope.rootVoteDataEdit.voteEndTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择投票时间')
            }

            if (!$rootScope.rootVoteDataEdit.rule) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写规则说明')
            }
            // if (!$rootScope.rootVoteDataEdit.desp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写活动图文')
            // }
            if (!$rootScope.rootVoteDataEdit.maxVotePerDay) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每天投票次数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteDataEdit.maxVotePerDay))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每天投票次数必须为大于0的正整数')
            }


            if (!$rootScope.rootVoteDataEdit.totalVoteCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每人总投票数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteDataEdit.totalVoteCount))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每人总投票数必须为大于0的正整数')
            }

            if (!$rootScope.rootVoteDataEdit.maxTicketPerDay) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每人被投票数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteDataEdit.maxTicketPerDay))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每人被投票数必须为大于0的正整数')
            }

            if ($rootScope.rootVoteDataEdit.votePeoplePerDay) {
                if (!(/^[1-9]\d*$/.test($rootScope.rootVoteDataEdit.votePeoplePerDay))) {
                    $scope.cliclSave = false;
                    return alertService.msgAlert('exclamation-circle', '能给多少人投票必须为大于0的正整数')
                }
            }

            if (!$rootScope.rootVoteDataEdit.shareTitle) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写微信分享标题')
            }
            // if (!$rootScope.rootVoteDataEdit.shareDesp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写微信分享描述')
            // }


            $rootScope.rootVoteDataEdit.voteStatusEnum = 'NORMAL';
            $rootScope.rootVoteDataEdit.forcePhone = true;

            $http({
                method: "PUT",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/'+ $stateParams.id,
                data: {
                    // shopName:$rootScope.rootVoteDataEdit.shopName,
                    primaryImgUrl:$rootScope.rootVoteDataEdit.primaryImgUrl,
                    desp:$rootScope.rootVoteDataEdit.desp,
                    rule:$rootScope.rootVoteDataEdit.rule,
                    shareTitle:$rootScope.rootVoteDataEdit.shareTitle,
                    shareContent:$rootScope.rootVoteDataEdit.shareContent,
                    voteName:$rootScope.rootVoteDataEdit.voteName,
                    signUpStartTime:$rootScope.rootVoteDataEdit.signUpStartTime,
                    signUpEndTime:$rootScope.rootVoteDataEdit.signUpEndTime,
                    voteStartTime:$rootScope.rootVoteDataEdit.voteStartTime,
                    voteEndTime:$rootScope.rootVoteDataEdit.voteEndTime,
                    maxVotePerDay:$rootScope.rootVoteDataEdit.maxVotePerDay,
                    totalVoteCount:$rootScope.rootVoteDataEdit.totalVoteCount,
                    maxTicketPerDay:$rootScope.rootVoteDataEdit.maxTicketPerDay,
                    votePeoplePerDay:$rootScope.rootVoteDataEdit.votePeoplePerDay,
                    forceFollow:$rootScope.rootVoteDataEdit.forceFollow?$rootScope.rootVoteDataEdit.forceFollow:false,
                    mustCheck:$rootScope.rootVoteDataEdit.mustCheck?$rootScope.rootVoteDataEdit.mustCheck:false,
                    voteStatusEnum:$rootScope.rootVoteDataEdit.voteStatusEnum,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('resprespresprespresp', resp);
                $scope.cliclSave = false;
                if (resp.data.status == 200) {
                    $rootScope.rootVoteDataEdit = {};
                    alertService.msgAlert('exclamation-circle', '编辑成功');
                    $scope.fallbackPage();
                }else if(resp.data.status == 10024){
                    alertService.msgAlert('exclamation-circle', resp.data.data);
                }
                // $scope.dataUrl = resp.data.data;
            }, function () {
                //error
                $scope.cliclSave = false;
            });
        };

        $scope.fallbackPage = function () {

            if($stateParams.form == 'HOME'){
                $state.go("main.voteApp.vote.voteHome", {id:$stateParams.id}, {reload: true});

            }else if($stateParams.form == 'VIEW'){
                $state.go("main.voteApp.vote.voteView", {id:$stateParams.id}, {reload: true});
            }else {
                $state.go("main.voteApp.vote.voteView", {id:$stateParams.id}, {reload: true});
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
    '$rootScope',
    '$filter',
    '$templateCache'
];

export default Controller ;
