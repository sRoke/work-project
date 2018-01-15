import conf from "../../../../../conf";

var $scope,
    $http,
    $stateParams,
    loginService,
    alertService,
    $state,
    $filter,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$stateParams,
                _loginService,
                _alertService,
                _$state,
                _$filter,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        $filter = _$filter;
        $scope.brandAppId = $stateParams.brandAppId;


        const TAG = "main/refund/refundOrderDetail ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.orderId = $stateParams.id;
        $scope.checkOrder = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/" + $scope.orderId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
                /*headers: {
                 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                 }*/
            }).then(function (resp) {
                $scope.info = resp.data.data;
            }, function (resp) {
            });
        };
        $scope.checkOrder();

        /*
         * 确认收货
         * */
        $scope.receive = (id) => {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order/" + id + "/confirmReceive",
                        params: {
                            id: id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function () {
                        alertService.msgAlert('success', '收货成功');
                        history.back();
                    }, function (resp) {
                    });
                }
            });
        };

        /*
         * 取消订单
         * */
        $scope.cancleOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/cancelRefund",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // alertService.msgAlert('success', '取消成功');
                        //$scope.data[$scope.status] = {};
                        $scope.checkOrder();
                    }, function (resp) {
                    });
                }
            });
        };

        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = (id) => {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // 跳转到支付页
                location.href = `${conf.payUrl}${resp.data.data}&backUrl=${encodeURIComponent(location.href)}`;
            }, function (resp) {
                //TODO 失败页
            });
        }
    }

    /*返回上级*/
    fallbackPage() {
        if ($stateParams.status || $stateParams.tableIndex) {
            $state.go("main.brandApp.unionOrder", {
                status: $stateParams.status,
                tableIndex: $stateParams.tableIndex
            }, {reload: true});
        } else {
            history.back();
        }
    };
}

Controller.$inject = [
    '$scope',
    '$http',
    '$stateParams',
    'loginService',
    'alertService',
    '$state',
    '$filter',
    '$location',
];

export default Controller ;
