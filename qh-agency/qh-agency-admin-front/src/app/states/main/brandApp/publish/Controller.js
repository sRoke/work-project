
var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        console.log("====================", $stateParams);
        $scope.infoId = $state.params.infoId;
        console.log($scope.infoId);
        // js控制写在此处

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;

