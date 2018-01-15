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

        ////////////////////////////////////变量定义
        const TAG = "main/test3 ";
        // console.log(`==> ${TAG}`);
        $scope.go = function (state, params, reload) {
            // console.log("--------------" + state);
            // console.log("--------------" + params);
            // console.log("--------------" + reload);

            $state.go(state, {params: params}, {reload: reload});
        };
        $scope.goTest1 = function () {


            // console.log('test1', $state.current.name);

            // if ($state.current.name == 'main.test3')
            //     $state.transitionTo($state.current, $stateParams, {reload: true, inherit: true, notify: false})
            // else
            //     $state.go('main.test2', null, {reload: true});

            $state.go('main.test2', {
                // params
            }, {
                // options
                reload: true
            });

        };
        $scope.goTest2 = function () {
            // console.log('test2');
            // $state.go('main.test2', {}, {reload: true})


            $state.go('main.test2').then(function (result) {
                $state.go('main.test2', {
                    // params
                }, {
                    // options
                    reload: true
                });
            });
        };


        // $state.go("main.test2", {}, {reload: true});


        // $state.go('main.test2').then(function (result) {
        //     $state.go('main.test2', {
        //         // params
        //     }, {
        //         // options
        //         reload: true
        //     });
        // });

        // history.back();


        // if ($state.current.name == 'main.test2')
        //     $state.transitionTo($state.current, $stateParams, {reload: true, inherit: true, notify: false})
        // else
        //     $state.go('main.test2', null, {reload: true});


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
