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

        console.log('window=============',window.screen.availWidth);

        if(window.screen.availWidth<380){
            $scope.timeHide = true;
        }

        if($stateParams.form != 'text'){
            $rootScope.rootVoteData = {};
        }

        console.log('$rootScope.rootVoteData========',$rootScope.rootVoteData);

        $scope.getShopName = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/getShopName',
                params: {},
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $rootScope.rootVoteData.shopName = resp.data.data;
            }, function () {
                //error
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









        // if(!$rootScope.rootVoteData.shopName){
        //     $scope.getShopName();
        // }


        $scope.AddEventInput = function (type) {
            var reg=/^[1-9]\d*$/;
            if(type == 'maxVotePerDay'){
                if(!reg.test($rootScope.rootVoteData.maxVotePerDay)){
                    $rootScope.rootVoteData.maxVotePerDay = $rootScope.rootVoteData.maxVotePerDay.substring(0,$rootScope.rootVoteData.maxVotePerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'totalVoteCount'){
                if(!reg.test($rootScope.rootVoteData.totalVoteCount)){
                    $rootScope.rootVoteData.totalVoteCount = $rootScope.rootVoteData.totalVoteCount.substring(0,$rootScope.rootVoteData.totalVoteCount.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'maxTicketPerDay'){
                if(!reg.test($rootScope.rootVoteData.maxTicketPerDay)){
                    $rootScope.rootVoteData.maxTicketPerDay = $rootScope.rootVoteData.maxTicketPerDay.substring(0,$rootScope.rootVoteData.maxTicketPerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }else if(type == 'votePeoplePerDay'){
                if(!reg.test($rootScope.rootVoteData.votePeoplePerDay)){
                    $rootScope.rootVoteData.votePeoplePerDay = $rootScope.rootVoteData.votePeoplePerDay.substring(0,$rootScope.rootVoteData.votePeoplePerDay.length-1);
                    console.log("请输入大于0的整数")
                }
            }
        };



        $scope.changeFollow = function (type) {
            $rootScope.rootVoteData.forceFollow = type ;
        };

        $scope.changeCheck = function (type) {
            $rootScope.rootVoteData.mustCheck = type ;
        }



        $scope.chooseTime = function (type) {
            $state.go("main.voteApp.vote.chooseTime", {
                type: type,
                from: 'add'
            })
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
                    $rootScope.rootVoteData.primaryImgUrl = resp.data.data.cdnUrls[0].url;
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
                from: 'add'
            })
        }


        $scope.cliclSave = false;
        $scope.save = function () {
            if ($scope.cliclSave) {
                return
            } else {
                $scope.cliclSave = true;
            }
            console.log('result================', $rootScope.rootVoteData);


            if (!$rootScope.rootVoteData.primaryImgUrl) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择活动主图')
            }


            // if (!$rootScope.rootVoteData.shopName) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写店铺名称')
            // }


            if (!$rootScope.rootVoteData.voteName) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写活动名称')
            }

            if (!$rootScope.rootVoteData.signUpStartTime || !$rootScope.rootVoteData.signUpEndTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择报名时间')
            }
            if (!$rootScope.rootVoteData.voteStartTime || !$rootScope.rootVoteData.voteEndTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择投票时间')
            }

            if (!$rootScope.rootVoteData.rule) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写规则说明')
            }
            // if (!$rootScope.rootVoteData.desp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写活动图文')
            // }
            if (!$rootScope.rootVoteData.maxVotePerDay) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每天投票次数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteData.maxVotePerDay))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每天投票次数必须为大于0的正整数')
            }


            if (!$rootScope.rootVoteData.totalVoteCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每人总投票数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteData.totalVoteCount))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每人总投票数必须为大于0的正整数')
            }

            if (!$rootScope.rootVoteData.maxTicketPerDay) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每人被投票数')
            }

            if (!(/^[1-9]\d*$/.test($rootScope.rootVoteData.maxTicketPerDay))) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '每人被投票数必须为大于0的正整数')
            }

            if ($rootScope.rootVoteData.votePeoplePerDay) {
                if (!(/^[1-9]\d*$/.test($rootScope.rootVoteData.votePeoplePerDay))) {
                    $scope.cliclSave = false;
                    return alertService.msgAlert('exclamation-circle', '能给多少人投票必须为大于0的正整数')
                }
            }

            if (!$rootScope.rootVoteData.shareTitle) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写微信分享标题')
            }
            // if (!$rootScope.rootVoteData.shareDesp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写微信分享描述')
            // }


            $rootScope.rootVoteData.voteStatusEnum = 'NORMAL';
            $rootScope.rootVoteData.forcePhone = true;

            $http({
                method: "POST",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin',
                data: {
                    // shopName:$rootScope.rootVoteData.shopName,
                    primaryImgUrl:$rootScope.rootVoteData.primaryImgUrl,
                    desp:$rootScope.rootVoteData.desp,
                    rule:$rootScope.rootVoteData.rule,
                    shareTitle:$rootScope.rootVoteData.shareTitle,
                    shareContent:$rootScope.rootVoteData.shareContent,
                    voteName:$rootScope.rootVoteData.voteName,
                    signUpStartTime:$rootScope.rootVoteData.signUpStartTime,
                    signUpEndTime:$rootScope.rootVoteData.signUpEndTime,
                    voteStartTime:$rootScope.rootVoteData.voteStartTime,
                    voteEndTime:$rootScope.rootVoteData.voteEndTime,
                    maxVotePerDay:$rootScope.rootVoteData.maxVotePerDay,
                    totalVoteCount:$rootScope.rootVoteData.totalVoteCount,
                    maxTicketPerDay:$rootScope.rootVoteData.maxTicketPerDay,
                    votePeoplePerDay:$rootScope.rootVoteData.votePeoplePerDay,
                    forceFollow:$rootScope.rootVoteData.forceFollow?$rootScope.rootVoteData.forceFollow:false,
                    mustCheck:$rootScope.rootVoteData.mustCheck?$rootScope.rootVoteData.mustCheck:false,
                    voteStatusEnum:$rootScope.rootVoteData.voteStatusEnum,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('resprespresprespresp', resp);
                $scope.cliclSave = false;
                if (resp.data.status == 200) {
                    $rootScope.rootVoteData = {};
                    alertService.msgAlert('exclamation-circle', '保存成功');
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
            $state.go("main.voteApp.vote.voteHome", {}, {reload: true});
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
