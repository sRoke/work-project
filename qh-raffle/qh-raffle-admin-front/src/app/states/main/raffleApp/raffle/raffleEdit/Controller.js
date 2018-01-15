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
        //loginService.loginCtl(true, $location.absUrl());
        console.log('end----edit',$rootScope.lotteryList);
        //设置奖项
        $scope.setLottery=function () {
            $state.go("main.raffleApp.raffle.setLottery", {
                from: 'edit',
                id:$stateParams.id,
            })
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
        //获取活动信息
        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id ,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $rootScope.rootBragainDataEdit = resp.data.data;
                $rootScope.imgUrl=resp.data.data.dialImg;
                $rootScope.lotteryList = resp.data.data.awards;
                var time = resp.data.data.beginTime+' '+'00:00:00';
                time=time.replace(/-/g,':').replace(' ',':');
                time=time.split(':');
                $rootScope.rootBragainDataEdit.beginTime = new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                $rootScope.endTimeData = $scope.getTimeData($rootScope.rootBragainDataEdit.beginTime,6);
            }, function () {
                    //error
                $scope.cliclSave = false;
            });
        };



        if($stateParams.form != 'text'){
            $scope.getData();
        }else {
            console.log('$rootScope.rootBragainDataEdit',$rootScope.rootBragainDataEdit)
            if (!$rootScope.rootBragainDataEdit) {
                $scope.getData();
                //$rootScope.rootBragainDataEdit = {};
            }
        }


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
                        $rootScope.rootBragainDataEdit.beginTime = new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        //$rootScope.rootBragainDataEdit.beginTime=new Date(result[0].value+'-'+result[1].value+' '+'00:00:00');
                        $rootScope.endTimeData = $scope.getTimeData($rootScope.rootBragainDataEdit.beginTime,6);
                        $scope.$apply();
                    },
                    id: 'startTimePicker'
                });
            }
        };

        //结束时间
        $scope.chooseEndTime = function () {
            if($rootScope.rootBragainDataEdit.beginTime){
                weui.picker($rootScope.endTimeData, {
                    depth:2,
                    defaultValue: [$rootScope.rootBragainDataEdit.beginTime.getFullYear()+'-'+($rootScope.rootBragainDataEdit.beginTime.getMonth() + 1), $rootScope.rootBragainDataEdit.beginTime.getDate()],
                    onChange: function (result) {
                        // console.log(result);
                    },
                    onConfirm: function (result) {
                        var time = result[0].value+'-'+result[1].value+' '+'00:00:00';
                        time=time.replace(/-/g,':').replace(' ',':');
                        time=time.split(':');
                        $rootScope.rootBragainDataEdit.endTime=new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        // $rootScope.rootBragainDataEdit.endTime=new Date(result[0].value+'-'+result[1].value);
                        $scope.$apply();
                    },
                    id: 'startTimePicker'
                });
            }else {
                return alertService.msgAlert('exclamation-circle', '请先选择生效时间')
            }
        };


        //领取方式
        $scope.dialogChoosePayType = function (type) {
            console.log('type',type);
            $scope.drawType = type;
        };
        $scope.cancelChoosePayType = function (data) {
            $scope.choosePayType = false;
            if (data) {
                console.log('1111111',$scope.drawType);
                $rootScope.rootBragainDataEdit.drawType = $scope.drawType;
                // $scope.payType = $scope.drawType;
            } else {
                $scope.drawType = $rootScope.rootBragainDataEdit.drawType;
            }
        };
        $scope.sureChoosePayType = function () {
            $scope.choosePayType = true;
        };




        $scope.changeFollow = function (type) {
            $rootScope.rootBragainDataEdit.mustFollow = type ;
        };
        //图文，规则
        $scope.goEdit = function (type) {
            // var json = angular.toJson($scope.skuData);
            $state.go("main.raffleApp.raffle.textImg", {
                type: type,
                from: 'edit',
                id:$stateParams.id,
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
            if (!$rootScope.rootBragainDataEdit.raffleName) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写活动名称')
            }
            if ($rootScope.rootBragainDataEdit.raffleName.length>15) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '活动名称不得超过15')
            }
            if (!$rootScope.rootBragainDataEdit.beginTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择生效时间')
            }
            if (!$rootScope.rootBragainDataEdit.endTime) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请选择过期时间')
            }

            if($rootScope.rootBragainDataEdit.beginTime > $rootScope.rootBragainDataEdit.endTime){
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '生效时间不能大于过期时间!')
            }
            if (!$rootScope.rootBragainDataEdit.rule) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写规则说明')
            }
            if (!$rootScope.rootBragainDataEdit.freeCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写免费抽奖次数')
            }
            var reg=/^\d+$/;
            if (!reg.test($rootScope.rootBragainDataEdit.freeCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (!$rootScope.rootBragainDataEdit.shareCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写分享可获抽奖次数')
            }
            if (!reg.test($rootScope.rootBragainDataEdit.shareCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (!$rootScope.rootBragainDataEdit.lotteryCount) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写最多抽奖次数')
            }
            if (!reg.test($rootScope.rootBragainDataEdit.lotteryCount)) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请输入正确的抽奖次数')
            }
            if (! $rootScope.rootBragainDataEdit.drawType) {
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
            if (!$rootScope.rootBragainDataEdit.shareTitle) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请填写微信分享标题')
            }
            if(!$rootScope.imgUrl) {
                $scope.cliclSave = false;
                return alertService.msgAlert('exclamation-circle', '请在奖项设置中重新保存')
            }

//             抽奖活动免费次数 freeCount
//              shareCount

//             抽奖活动奖品 awards
// （奖品名称 name 奖品数量 num 奖品中奖概率 chance 奖品图片 picture 奖品类型
// awardType("VIRTUAL", "虚拟"  "MATERIAL", "实物")
// ）

            //
            // $rootScope.rootBragainDataEdit
            $http({
                method: "PUT",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/'+$stateParams.id,
                data: {
                    freeCount:$rootScope.rootBragainDataEdit.freeCount,        //抽奖活动免费次数
                    shareCount:$rootScope.rootBragainDataEdit.shareCount,    //分享可获得抽奖的次数
                    lotteryCount:$rootScope.rootBragainDataEdit.lotteryCount,  //最大抽奖次数
                    beginTime:$rootScope.rootBragainDataEdit.beginTime,      //抽奖活动开始时间
                    desp:$rootScope.rootBragainDataEdit.desp,                //抽奖活动图文说明
                    endTime:$rootScope.rootBragainDataEdit.endTime,          //抽奖活动结束时间
                    raffleName:$rootScope.rootBragainDataEdit.raffleName,         //抽奖活动名称
                    rule:$rootScope.rootBragainDataEdit.rule,                //抽奖活动规则
                    shareTitle:$rootScope.rootBragainDataEdit.shareTitle,   //分享标题
                    shareDesp:$rootScope.rootBragainDataEdit.shareDesp,    //分享描述
                    mustFollow:$rootScope.rootBragainDataEdit.mustFollow?$rootScope.rootBragainDataEdit.mustFollow:false,  //是否关注
                    awards:$rootScope.lotteryList,
                    drawType:$rootScope.rootBragainDataEdit.drawType,
                    dialImg:$rootScope.imgUrl,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    console.log('resprespresprespresp', resp);
                    $scope.cliclSave = false;
                    if (resp.data.status == 200) {
                        $rootScope.rootBragainDataEdit = {};
                        alertService.msgAlert('exclamation-circle', '保存成功');
                        $scope.fallbackPage();
                    }
                    // $scope.dataUrl = resp.data.data;
            }, function (resp) {
                //error
                $scope.cliclSave = false;
                alertService.msgAlert('exclamation-circle', resp.data.message);
            });


        };
        $scope.fallbackPage = function () {
            $rootScope.rootBragainDataEdit={};
            $state.go("main.raffleApp.raffle.raffleView", {id:$stateParams.id}, {reload: true});
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
