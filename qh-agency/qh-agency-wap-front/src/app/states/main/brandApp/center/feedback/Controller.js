import conf from "../../../../../conf";
import store from "store";

var $scope,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope, _loginService, _$state,_$location) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $location = _$location;
        loginService.loginCtl(true,$location.absUrl());

        $scope.go = function (state) {
            $state.go(state);
        };





        // 回退页面
        $scope.fallbackPage = function () {
                $state.go("main.brandApp.center.setting", null, {reload: true});
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$location',
];

export default Controller ;
