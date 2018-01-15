import conf from "../../../../../conf";
// import 'jquery';
import weui from 'weui.js';


var $scope,
    $http,
    $state,
    Upload,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    $templateCache,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _Upload,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _$templateCache,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        Upload = _Upload;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        $templateCache = _$templateCache;
        wxService = _wxService;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        $rootScope.voteAppId = $scope.voteAppId = $stateParams.voteAppId;
        $scope.from = $stateParams.from;
        $scope.type = $stateParams.type;
        $scope.gotoTop = function () {
            window.scrollTo(0, 0);//滚动到顶部
        };
        $scope.gotoTop();

        // 获取m(data格式)时间后n个月的时间数据
        $scope.getTimeData = function (m,n) {
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



        if ($scope.from == 'add') {
            if (!$rootScope.rootVoteData) {
                $rootScope.rootVoteData = {};
            }else {
                if($scope.type == 'signUp'){
                    $scope.startTime = $rootScope.rootVoteData.signUpStartTime;
                    $scope.endTime = $rootScope.rootVoteData.signUpEndTime;
                }else if($scope.type == 'voting'){
                    $scope.startTime = $rootScope.rootVoteData.voteStartTime;
                    $scope.endTime = $rootScope.rootVoteData.voteEndTime;
                }

                if($scope.startTime){
                    $scope.endTimeData = $scope.getTimeData($scope.startTime,6);
                }
            }

            $scope.startTimeData = $scope.getTimeData(new Date(),6);
        } else if ($scope.from == 'edit') {
            if (!$rootScope.rootVoteDataEdit) {
                $rootScope.rootVoteDataEdit = {};
            }else {
                if($scope.type == 'signUp'){
                    $scope.startTime = new Date($rootScope.rootVoteDataEdit.signUpStartTime);
                    $scope.endTime = new Date($rootScope.rootVoteDataEdit.signUpEndTime);
                }else if($scope.type == 'voting'){
                    $scope.startTime = new Date($rootScope.rootVoteDataEdit.voteStartTime) ;
                    $scope.endTime = new Date($rootScope.rootVoteDataEdit.voteEndTime) ;
                }

                console.log(1111,$scope.startTime,22222,$scope.endTime);
                if($scope.startTime){
                    $scope.endTimeData = $scope.getTimeData($scope.startTime,6);
                }
            }
            $scope.startTimeData = $scope.getTimeData(new Date(),6);
            // $state.go("main.voteApp.vote.voteEdit", {id: $scope.id, form: 'text'})
        }

        console.log('$scope.startTimeData=================',$scope.startTimeData);
        $scope.chooseStartTime = function () {
            if($scope.startTimeData){
                weui.picker($scope.startTimeData, {
                    depth:3,
                    defaultValue: [new Date().getFullYear()+'-'+(new Date().getMonth() + 1), new Date().getDate(),new Date().getHours()],
                    onChange: function (result) {
                        // console.log(result);
                    },
                    onConfirm: function (result) {
                        console.log('result++++++++++++++------', result);
                        console.log('result++++++++++++++------', result[0].value+'-'+result[1].value+' '+result[2].value+':00:00');

                        var time = result[0].value+'-'+result[1].value+' '+result[2].value+':00:00';
                        time=time.replace(/-/g,':').replace(' ',':');
                        time=time.split(':');
                        $scope.startTime = new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        // $scope.startTime=new Date(result[0].value+'-'+result[1].value+' '+result[2].value+':00:00');
                        // $scope.startTime=new Date(result[0].value+'-'+result[1].value+' '+result[2].value+':00:00').getTime();










                        $scope.endTimeData = $scope.getTimeData($scope.startTime,6);
                        console.log('$scope.startTime++++++++++++++------', $scope.startTime);
                        $scope.$apply();
                    },
                    id: 'startTimePicker'
                });
            }
        };


        $scope.chooseEndTime = function () {
            if ($scope.endTimeData) {
                weui.picker($scope.endTimeData, {
                    depth:3,
                    defaultValue: [$scope.startTime.getFullYear()+'-'+($scope.startTime.getMonth() + 1), $scope.startTime.getDate(),$scope.startTime.getHours()],
                    onChange: function (result) {
                        // console.log(result);
                    },
                    onConfirm: function (result) {
                        console.log('result++++++++++++++------', result);
                        console.log('result++++++++++++++------', result[0].value+'-'+result[1].value+' '+result[2].value+':00:00');

                        var time = result[0].value+'-'+result[1].value+' '+result[2].value+':00:00';
                        time=time.replace(/-/g,':').replace(' ',':');
                        time=time.split(':');
                        $scope.endTime = new Date(time[0],(time[1]-1),time[2],time[3],time[4],time[5]);
                        // $scope.endTime=new Date(result[0].value+'-'+result[1].value+' '+result[2].value+':00:00');
                        // $scope.startTime=new Date(result[0].value+'-'+result[1].value+' '+result[2].value+':00:00').getTime();
                        console.log('$scope.endTime++++++++++++++------', $scope.endTime);
                        $scope.$apply();
                    },
                    id: 'endTimePicker'
                });
            } else {
                return alertService.msgAlert('exclamation-circle', '请先选择生效时间')
            }
        };





        $scope.finish = function () {

            if(!$scope.startTime){
                return alertService.msgAlert('exclamation-circle', '请先选择开始时间')
            }

            if(!$scope.endTime){
                return alertService.msgAlert('exclamation-circle', '请先选择截止时间')
            }

            if($scope.endTime.getTime() < $scope.startTime.getTime()){
                return alertService.msgAlert('exclamation-circle', '截止时间不能小于开始时间')
            }


            if ($scope.from == 'add') {
                if($scope.type == 'signUp'){
                    $rootScope.rootVoteData.signUpStartTime = $scope.startTime;
                    $rootScope.rootVoteData.signUpEndTime = $scope.endTime;
                }else if($scope.type == 'voting'){
                    $rootScope.rootVoteData.voteStartTime = $scope.startTime;
                    $rootScope.rootVoteData.voteEndTime = $scope.endTime;
                }
            }else if($scope.from == 'edit'){
                if($scope.type == 'signUp'){
                    $rootScope.rootVoteDataEdit.signUpStartTime = $scope.startTime;
                    $rootScope.rootVoteDataEdit.signUpEndTime = $scope.endTime;
                }else if($scope.type == 'voting'){
                    $rootScope.rootVoteDataEdit.voteStartTime = $scope.startTime;
                    $rootScope.rootVoteDataEdit.voteEndTime = $scope.endTime;
                }
            }


            $scope.fallbackPage()
        };






        /*返回上级*/
        $scope.fallbackPage = function () {
            if ($scope.from == 'add') {
                $state.go("main.voteApp.vote.voteAdd", {form: 'text'}, {reload: true})
            } else if ($scope.from == 'edit') {
                $state.go("main.voteApp.vote.voteEdit", {id:$stateParams.id,form: 'text'}, {reload: true})
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'Upload',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    '$templateCache',
    '$rootScope'
];

export default Controller ;
