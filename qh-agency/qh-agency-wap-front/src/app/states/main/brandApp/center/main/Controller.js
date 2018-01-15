import conf from '../../../../../conf'
var $scope,
    loginService,
    $stateParams,
    $state,
    $http,
    $location;
class Controller {
    constructor(_$scope,
                _loginService,
                _$stateParams,
                _$state,
                _$http,
                _$location) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $location =_$location;
        $http=_$http;
        $stateParams=_$stateParams;
        //判断是否授权-通用方法-依赖
        loginService.loginCtl(true,$location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.go = function (state) {
            $state.go(state);
        };
        $scope.count = 100;
        $scope.clickThis = function () {
            //alertService.msgAlert("cancle", "您尚未安装微信!");
        };
        $scope.backshow = false;
        $scope.show = function () {
            $scope.backshow = !$scope.backshow;
        };


        //联系客服
        $scope.contactService = function () {
            location.href = ysf.url();
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$stateParams',
    '$state',
    '$http',
    '$location',
];

export default Controller ;
