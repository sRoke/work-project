var $scope,
    loginService,
    $state;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
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
    '$state'
];

export default Controller ;
