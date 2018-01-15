var $scope,
    loginService,
    $state,
    $stateParams,
    $location;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        loginService.loginCtl(true,$location.absUrl());

        $scope.go = function (state) {
            $state.go(state);
        };


        $scope.data = {
            number : '1123',
            name   : '欧阳',
            type   : '加盟商',
            lv     :  '一级',
            address: '...................',
            contacts:'哈哈哈',
            phone :'12345678901',
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
];

export default Controller ;
