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
        $scope.raffleAppId = $stateParams.raffleAppId;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.choosePayType = false;
        $scope.dialogPayType = 1;

        console.log('$rootScope.imgUrl',$rootScope.imgUrl);
        if($stateParams.form != 'text'){
            $rootScope.rootBragainData = {};
        }else {
            if (!$rootScope.rootBragainData) {
                $rootScope.rootBragainData = {};
            }
        }
        console.log('lotteryArray',$rootScope.lotteryList);
        //关注
        $scope.changeFollow = function (type) {
            $rootScope.rootBragainData.mustFollow = type ;
        };

        $scope.dialogChoosePayType = function (type) {
            $scope.dialogPayType = type;
        };
        $scope.cancelChoosePayType = function (data) {
            $scope.choosePayType = false;
            if (data) {
                $rootScope.rootBragainData.receiveType = $scope.dialogPayType;
                $scope.payType = $scope.dialogPayType;
            } else {
                $scope.dialogPayType = $rootScope.rootBragainData.receiveType;
            }
        };
        //领取方式
        $scope.dialogChoosePayType = function (type) {
            $scope.dialogPayType = type;
        };
        $scope.cancelChoosePayType = function (data) {
            $scope.choosePayType = false;
            if (data) {
                $rootScope.rootBragainData.receiveType = $scope.dialogPayType;
                $scope.payType = $scope.dialogPayType;
            } else {
                $scope.dialogPayType = $rootScope.rootBragainData.receiveType;
            }
        };
        $scope.sureChoosePayType = function () {
            $scope.choosePayType = true;
        };



        // 获取m(data格式)时间后n个月的时间数据
        $scope.getTimeData = function (m,n) {
            console.log(m)
            var timeData = [];
            var minuteArray = [];                       //分钟
            for (var i = 0 ;i<60 ; i++){
                minuteArray.push({
                    label: i+'分',
                    value: i
                })
            }
            var hourArray = [];
            for (var x = 0 ;x<24 ; x++){
                hourArray.push({
                    label: x+'点',
                    value: x,
                    children:minuteArray,
                })
            }
            for(var j = 0 ; j<n ;j++){
                var year = m.getFullYear();
                var month = m.getMonth() + 1 + j;
                if(month >12){
                    if(month%12){
                        year = year + parseInt(month/12);
                        month = month%12;
                    }else {
                        year = year + parseInt(month/12)-1;
                        month = 12;
                    }
                }
                var day = new Date(year, month, 0);  //本月
                //获取天数：
                var daycount1 = day.getDate();   //获取本月天数
                var monthArray = [];
                if(!j){
                    for (var k = m.getDate(); k <= daycount1; k++) {
                        if(k == m.getDate()){
                            var oneHours = [];
                            for(var h = m.getHours();h<24;h++){
                                oneHours.push({
                                    label: h+'点',
                                    value: h,
                                    children:minuteArray,
                                })
                            }
                            monthArray.push({label: k + '日',value:k,children:oneHours});
                        }else {
                            monthArray.push({label: k + '日',value:k,children:hourArray});
                        }
                    }
                }else {
                    for (var k = 1; k <= daycount1; k++) {
                        monthArray.push({label: k + '日',value:k,children:hourArray});
                    }
                }
                timeData.push({
                    label:year+'年'+month +'月',
                    value:year+'-'+month,
                    children:monthArray,
                })
            }
            console.log('result=================',timeData);
            return timeData;
        };
        $scope.startTimeData = $scope.getTimeData(new Date(),6);
        //开始时间
        $scope.chooseStartTime = function () {
            if($scope.startTimeData){
                weui.picker($scope.startTimeData, {
                    depth:2,
                    defaultValue: [new Date().getFullYear()+'-'+(new Date().getMonth() + 1), new Date().getDate()],
                    onChange: function (result) {
                        // console.log(result);
                    },
                    onConfirm: function (result) {

                        var time = result[0].value+'-'+result[1].value+' '+'00:00:00';
                        time=time.replace(/-/g,':').replace(' ',':');
                        time=time.split(':');
                        $rootScope.rootBragainData.beginTime = new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        //$rootScope.rootBragainData.beginTime=new Date(result[0].value+'-'+result[1].value+' '+'00:00:00');
                        $scope.endTimeData = $scope.getTimeData($rootScope.rootBragainData.beginTime,6);
                        $scope.$apply();
                    },
                    id: 'startTimePicker'
                });
            }
        };
        //结束时间
        $scope.chooseEndTime = function () {
            if($scope.endTimeData){
                weui.picker($scope.endTimeData, {
                    depth:2,
                    defaultValue: [$rootScope.rootBragainData.beginTime.getFullYear()+'-'+($rootScope.rootBragainData.beginTime.getMonth() + 1), $rootScope.rootBragainData.beginTime.getDate()],
                    onChange: function (result) {
                        // console.log(result);
                    },
                    onConfirm: function (result) {
                        var time = result[0].value+'-'+result[1].value+' '+'00:00:00';
                        time=time.replace(/-/g,':').replace(' ',':');
                        time=time.split(':');
                        $rootScope.rootBragainData.endTime=new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        $scope.$apply();
                    },
                    id: 'startTimePicker'
                });
            }else {
                return alertService.msgAlert('exclamation-circle', '请先选择生效时间')
            }
        };

        //图文编辑
        $scope.goEdit = function (type) {
            // var json = angular.toJson($scope.skuData);
            $state.go("main.raffleApp.raffle.textImg", {
                type: type,
                from: 'add'
            })
        };
        //设置奖项
        $scope.setLottery=function () {
            $state.go("main.raffleApp.raffle.setLottery", {
                from: 'add'
            })
        };


        $scope.cliclSave = false;

        $scope.save = function () {
            console.log('test--lottery',$rootScope.lotteryList);
            if ($scope.cliclSave) {
                return
            } else {
                $scope.cliclSave = true;
            }
            console.log('result================', $rootScope.rootBragainData);
            if (!$rootScope.rootBragainData.name) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写活动名称')
            }
            if ($rootScope.rootBragainData.name.length>15) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '活动名称的长度不得超过15')
            }
            if (!$rootScope.rootBragainData.beginTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择生效时间')
            }
            if (!$rootScope.rootBragainData.endTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择过期时间')
            }

            if($rootScope.rootBragainData.beginTime > $rootScope.rootBragainData.endTime){
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '生效时间不能大于过期时间!')
            }




            if (!$rootScope.rootBragainData.rule) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写规则说明')
            }
            // if (!$rootScope.rootBragainData.desp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写活动图文')
            // }
            if (!$rootScope.rootBragainData.freeCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写免费抽奖次数')
            }
            var reg=/^\d+$/;
            if (!reg.test($rootScope.rootBragainData.freeCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (!$rootScope.rootBragainData.shareCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写每次分享可获抽奖次数')
            }
            if (!reg.test($rootScope.rootBragainData.shareCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (!$rootScope.rootBragainData.lotteryCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写最多抽奖次数')
            }
            if (!reg.test($rootScope.rootBragainData.lotteryCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (! $rootScope.rootBragainData.receiveType) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择领取方式')
            }

            if(!$rootScope.lotteryList){
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请在奖项设置中填写奖品信息')
            }
            if($rootScope.lotteryList){
                if ($rootScope.lotteryList.length<2) {
                    $scope.cliclSave = false;
                    return alertService.msgAlert('exclamation-circle', '请在奖项设置中填写奖品信息')
                }
            }

            if(!$rootScope.imgUrl) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请在奖项设置中重新保存')
            }

            if (!$rootScope.rootBragainData.shareTitle) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写微信分享标题')
            }

            // if (!$rootScope.rootBragainData.shareDesp) {
            //     $scope.cliclSave = false;
            //     return alertService.msgAlert('exclamation-circle', '请填写微信分享描述')
            // }

//             抽奖活动免费次数 freeCount
//              shareCount

//             抽奖活动奖品 awards
// （奖品名称 name 奖品数量 num 奖品中奖概率 chance 奖品图片 picture 奖品类型
// awardType("VIRTUAL", "虚拟"  "MATERIAL", "实物")
// ）

            //
            // $rootScope.rootBragainData
            $http({
                method: "POST",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle',
                data: {
                    freeCount:$rootScope.rootBragainData.freeCount,        //抽奖活动免费次数
                    shareCount:$rootScope.rootBragainData.shareCount,    //分享可获得抽奖的次数
                    lotteryCount:$rootScope.rootBragainData.lotteryCount,  //最大抽奖次数
                    beginTime:$rootScope.rootBragainData.beginTime,      //抽奖活动开始时间
                    desp:$rootScope.rootBragainData.desp,                //抽奖活动图文说明
                    endTime:$rootScope.rootBragainData.endTime,          //抽奖活动结束时间
                    raffleName:$rootScope.rootBragainData.name,         //抽奖活动名称
                    rule:$rootScope.rootBragainData.rule,                //抽奖活动规则
                    shareTitle:$rootScope.rootBragainData.shareTitle,   //分享标题
                    shareDesp:$rootScope.rootBragainData.shareDesp,    //分享描述
                    dialImg:$rootScope.imgUrl,                             //合成图片
                    mustFollow:$rootScope.rootBragainData.mustFollow?$rootScope.rootBragainData.mustFollow:false,  //是否关注
                    awards:$rootScope.lotteryList,
                    drawType:$rootScope.rootBragainData.receiveType,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    console.log('resprespresprespresp', resp);
                    $scope.cliclSave = false;
                    if (resp.data.status == 200) {
                        $rootScope.rootBragainData = {};
                        alertService.msgAlert('exclamation-circle', '保存成功');
                        $scope.fallbackPage();
                    }
                    // $scope.dataUrl = resp.data.data;
                }, function () {
                    //error
                    $scope.cliclSave = false;
                }
            );


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
    '$rootScope',
    '$filter',
    '$templateCache'
];

export default Controller ;
