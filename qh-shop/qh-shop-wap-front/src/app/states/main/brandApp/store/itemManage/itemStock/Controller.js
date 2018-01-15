import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        loginService.loginCtl(true, $location.absUrl());

        $scope.isClick = 1;
        $scope.clickThis = function (num) {
            $scope.isClick = num;
        };

        $scope.com = new Array(3);

        $scope.stockData = {
            stock: [
                {
                    name: '自定义仓库1',
                    check: false,
                },
                {
                    name: '自定义仓库2',
                    check: false,
                },
                {
                    name: '自定义仓库3',
                    check: false,
                },
                {
                    name: '自定义仓库4',
                    check: false,
                },
            ]
        };


        $scope.openChoose = false;
        $scope.changeOpen = function () {
            $scope.openChoose = !$scope.openChoose;
        };

        $scope.stockName = '默认仓库';
        $scope.chooseName = function (name) {
            $scope.stockName = name;
            for (let i in  $scope.stockData.stock) {
                $scope.stockData.stock[i].check = false;
                if (name == $scope.stockData.stock[i].name) {
                    $scope.stockData.stock[i].check = true;
                }
            }
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", {brandAppId: $stateParams.brandAppId}, {reload: true});
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
    '$http',
];

export default Controller ;
