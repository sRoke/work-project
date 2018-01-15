var $scope,
    $state,
    loginService,
    $location;
class Controller {
    constructor(_$scope, _$state,_loginService,_$location) {
        $scope = _$scope;
        $state = _$state;
        loginService =_loginService;
        $location = _$location;

        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };

        /*静态页面用，，js联调后可删除*/
        $scope.comments = new Array(6);









    }
}

Controller.$inject = [
    '$scope',
    '$state',
    'loginService',
    '$location'
];

export default Controller ;
