import conf from "../../../../../../conf";

import weui from 'weui.js';
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $http = _$http;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
        //选中
        $scope.checked=false;
        $scope.changeCheck=function () {
            $scope.checked=!$scope.checked;
        };
        //weui
        $scope.openChecked=function(){
            weui.picker([
                {
                    label: '飞机票',
                    value: 0,
                    disabled: true // 不可用
                },
                {
                    label: '火车票',
                    value: 1
                },
                {
                    label: '汽车票',
                    value: 3
                },
                {
                    label: '公车票',
                    value: 4,
                }
            ], {
                //className: 'aaa',
                //container: 'body',
                defaultValue: [3],
                onChange: function (result) {
                    // console.log(result[0].label)
                },
                onConfirm: function (result) {
                    // console.log(result)
                },
                id: 'singleLinePicker'
            });
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$http',
];

export default Controller ;
