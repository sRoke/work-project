import conf from "../../../../../../conf";
var $scope,
    $state,
    $stateParams,
    loginService,
    alertService,
    $filter,
    $http,
    $location;

class Controller {
    constructor(_$scope, _$state, _$stateParams, _loginService, _alertService, _$filter, _$http, _$location) {
        $scope = _$scope;
        $state = _$state;
        $stateParams = _$stateParams;
        loginService = _loginService;
        alertService = _alertService;
        $filter = _$filter;
        $http = _$http;
        $location = _$location;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        loginService.loginCtl(true, $location.absUrl());
        // $scope.go = function (state) {
        //     $state.go(state);
        // };
        $scope.data = {};
        //获取可提现额度
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/shopAccount",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;
                // console.log('$scope.data', $scope.data);

            }, function (resp) {
                //TODO 错误处理
            });
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/withdraw/ali",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.ali = resp.data.data.default;
                // console.log('$scope.data12', $scope.ali);

            }, function (resp) {
                // console.log('$scope.resp', resp);
                alertService.msgAlert("exclamation-circle", resp.data.message);

                //TODO 错误处理
            });
        };
        $scope.getInfo();
        $scope.check = false;
        $scope.checkMoney = function (money) {
            // console.log(money, $scope.data.balance);
            if (/^\d+(\.\d{1,2})?$/.test(money) && money >= 1.00 && money <= $scope.data.balance / 100) {
                $scope.check = true;
            } else {
                $scope.check = false;
            }
            ;
        };
        $scope.getAll = function () {
            $scope.txMoney = $filter('number')($scope.data.balance / 100, 2).replace(/,/g, "");
            if (/^\d+(\.\d{1,2})?$/.test($scope.txMoney) && $scope.txMoney >= 1.00) {
                $scope.check = true;
            } else {
                $scope.check = false;
            }
            ;
            $scope.txMoney = Number($scope.txMoney);
        };
        //确认提现
        $scope.checkCount = function () {
            if (!$scope.txMoney) {
                alertService.msgAlert("exclamation-circle", "提现金额不得为0");
                // $scope.txMoney='';
                return;
            }
            ;
            if ($scope.txMoney > $scope.data.balance / 100) {
                alertService.msgAlert("exclamation-circle", "提现金额不得超过可提现金额");
                // $scope.txMoney='';
                return;
            }
            ;
            if ($scope.txMoney > 20000.00) {
                alertService.msgAlert("exclamation-circle", "单笔最高提现额度不得超过20000.00");
                $scope.txMoney = '';
                return;
            }
            ;
            // $state.go("main.brandApp.wallet.wdSuccess",{count:$scope.txMoney},{reload:true})
            //当确认体现直接调用提现接口，
            //提现接口
            $http({
                method: "PUT",   ///brandApp/{brandAppId}/shop/{shopId}/withdraw  POST  applyAmount（提现金额)
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/withdraw",
                params: {
                    applyAmount: ($scope.txMoney * 100).toFixed(0)
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    // console.log('success', resp);
                    if (resp.data.status != '200') {
                        alertService.msgAlert("exclamation-circle", resp.data.data)
                    } else {
                        $state.go("main.brandApp.store.shop.myIncome", {storeId: $scope.storeId}, {reload: true});
                    };
                }, function (resp) {
                    alertService.msgAlert("exclamation-circle", resp.data.message);
                    //error
                }
            );
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", null, {reload: true});
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
    'alertService',
    '$filter',
    '$http',
    '$location',
];

export default Controller ;
