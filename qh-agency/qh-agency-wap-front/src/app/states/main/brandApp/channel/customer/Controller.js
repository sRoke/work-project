var $scope,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$location) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $location = _$location;
        loginService.loginCtl(true,$location.absUrl());

        $scope.go = function (state) {
            $state.go(state);
        };


        $scope.data = [{
            id: '11111111',
            number: '123',
            name: '哈哈',
            contacts: '陈某某',
            phone: '12345678901',
            type: '加盟商',
        }, {
            id: '22222222222',
            number: '456',
            name: '啦啦',
            contacts: '李某某',
            phone: '12345678901',
            type: '加盟商',
        }, {
            id: '4444444444',
            number: '789',
            name: '嘿嘿',
            contacts: '张某某',
            phone: '12345678901',
            type: '加盟商',
        }];

        $scope.search = '';
        $scope.page = function (search) {
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
    'loginService',
    '$state',
    '$location',
];

export default Controller ;
