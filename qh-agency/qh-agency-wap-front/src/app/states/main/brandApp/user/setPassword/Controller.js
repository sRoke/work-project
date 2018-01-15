import conf from "../../../../../conf";

var $scope,
    alertService,
    $mdDialog,
    $http,
    loginService,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope, _alertService, _$mdDialog, _$http, _loginService, _$stateParams, _$state,_$location) {
        $scope = _$scope;
        alertService = _alertService;
        $mdDialog = _$mdDialog;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $state = _$state;
        $location = _$location;

        loginService.loginCtl(true, $location.absUrl());

        if ($stateParams.status == 'modify') {
            $scope.setPassword = false;
        } else if ($stateParams.status == 'set') {
            $scope.setPassword = true;
        }

        $scope.set = {};
        $scope.change = {};

        //验证密码格式
        $scope.formatPassword =function (password) {
            if(!/^\d{6}$/.test(password)){
                alertService.msgAlert("exclamation-circle", "请输入6位数字密码").then(
                    function (data) {
                        if (data) {
                        }
                    }
                );
            }
        };


        $scope.save = function () {
            //设置密码
            if ($scope.setPassword) {
                if ($scope.set.password !== $scope.set.surePassword) {
                    alertService.msgAlert("exclamation-circle", "密码不一致!请重新设置").then(
                        function (data) {
                            if (data) {
                                // $scope.set.password = '';
                                // $scope.set.surePassword = '';
                                // console.log('11111111111111111')
                                $scope.set = {};
                            }
                        }
                    );
                    return;
                } else {
                    $http({
                        method: "POST",
                        url: conf.apiPath + "/user/setPassword",
                        data: {
                            password: $scope.set.password,
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function (data) {
                            alertService.msgAlert("success", "设置成功").then(
                                function (data) {
                                    if (data) {
                                        $scope.setPassword = false;
                                    }
                                }
                            );
                        }, function () {

                        }
                    );
                }
            } else {  //修改密码
                if ($scope.change.new !== $scope.change.sure ) {
                    alertService.msgAlert("exclamation-circle", "密码不一致!请重新设置").then(
                        function (data) {
                            if (data) {
                                $scope.change.new= '';
                                $scope.change.sure = '';
                            }
                        }
                    );
                    return;
                } else {
                    $http({
                        method: "POST",
                        url: conf.apiPath + "/user/changePassword",
                        data: {
                            password: $scope.change.new,
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function (data) {
                            alertService.msgAlert("success", "修改成功").then(
                                function (data) {
                                    if (data) {
                                        $scope.change= {};
                                    }
                                }
                            );
                        }, function () {

                        }
                    );
                }

            }
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                history.back();
            }
        };


    }
}

Controller.$inject = [
    '$scope',
    'alertService',
    '$mdDialog',
    '$http',
    'loginService',
    '$stateParams',
    '$state',
    '$location'
];

export default Controller ;
