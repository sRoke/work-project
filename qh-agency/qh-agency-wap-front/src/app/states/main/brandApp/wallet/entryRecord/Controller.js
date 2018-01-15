var $scope,
    $state,
    $stateParams,
    loginService,
    $location;

class Controller {
    constructor(_$scope, _$state, _$stateParams,_loginService,_$location) {
        $scope = _$scope;
        $state = _$state;
        $stateParams = _$stateParams;
        loginService = _loginService;
        $location = _$location;

        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };


        $scope.now = new Date();
        $scope.data = [{
            time:$scope.now,
            status:'return',
            num:'15343'
        },{
            time:$scope.now,
            status:'Purchase',
            num:'15343'
        },{
            time:$scope.now,
            status:'return',
            num:'15343'
        }];








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
    '$state',
    '$stateParams',
    'loginService',
    '$location',
];

export default Controller ;
