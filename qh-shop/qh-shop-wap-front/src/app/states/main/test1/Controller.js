import conf from "../../../conf";
let $scope,
    alertService,
    $rootScope,
    $interval,
    $state,
    $stateParams,
    loginService,
    $http;
class Controller {
    constructor(_$scope,
                _$interval,
                _$rootScope,
                _alertService,
                _$state,
                _$stateParams,
                _loginService,
                _$http) {
        /////////////////////////////////////通用注入
        $scope = _$scope;
        $state = _$state;
        $interval = _$interval;
        alertService = _alertService;
        $rootScope = _$rootScope;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;

        $scope.go = function (state) {
            // console.log("--------------" + state);
            $state.go(state);
        };
        ////////////////////////////////////变量定义
        const TAG = "main/test1 ";
        // console.log(`==> ${TAG}`);
    }
}

Controller
    .$inject = [
    '$scope',
    '$interval',
    '$rootScope',
    'alertService',
    '$state',
    '$stateParams',
    'loginService',
    '$http'
];

export
default
Controller;
