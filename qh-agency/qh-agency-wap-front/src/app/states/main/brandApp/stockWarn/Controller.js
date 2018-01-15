import conf from "../../../../conf";

var $scope,
    $http,
    $state,
    $log,
    $filter,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $filter = _$filter;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.getItemList = function (categoryId) {
            $http({
                method: 'GET',
                url: conf.apiPath + "/item/search",
                params: {
                    categoryId: categoryId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.itemList = resp.data.data;
            })
        };
        $scope.getItemList();

        $scope.getBtnList = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/item/getCategory",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.btnList = resp.data.data;
                // 默认选第一个
                // $scope.btnList[0].check = true;
                $scope.check = true;
                $scope.getItemList(null);
            })

        };
        $scope.getBtnList();


        $scope.btnClick = function (btn) {
            if (btn == null) {
                $scope.check = true;
                for (var i = 0; i < $scope.btnList.length; i++) {
                    $scope.btnList[i].check = false;
                }
                $scope.getItemList(null);
            }
            else {
                for (var i = 0; i < $scope.btnList.length; i++) {
                    $scope.check = false;
                    $scope.btnList[i].check = false;
                    if (btn.name == $scope.btnList[i].name) {
                        $scope.btnList[i].check = true;
                        $scope.getItemList(btn.id);
                    }
                }
            }
        };
        //获取购物车列表
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/cart/list",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.comments = resp.data.data;
                    $scope.totlePrice = 0;
                    if (!resp.data.data) {
                        console.log('未添加');
                        $scope.totlePrice = 0;
                        return;
                    }
                    for (let i = 0; i < $scope.comments.items.length; i++) {
                        $scope.totlePrice += (Number($scope.comments.items[i].sku.price) * $scope.comments.items[i].num);
                    }
                    $scope.totlePrice = $filter('number')($scope.totlePrice, 2);

                }, function () {

                }
            );
        };
        $scope.getList();
        $scope.searchItem = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/item/search",
                params: {
                    keyWord: $scope.keyWord
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.itemList = resp.data.data;
                // $scope.getItemList(null);
            })
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.stockControl", null, {reload: true});
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$filter',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location'
];

export default Controller ;
