import conf from "../../../../conf";

var $scope,
    $stateParams,
    $http,
    loginService,
    alertService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$http,
                _loginService,
                _alertService,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        var vm = this;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/myPurchase";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.data = {
            ALL: {},
            UNPAYED: {},
            UNSHIPPED: {},
            UNRECEIVED: {},
            FINISHED: {},
        };

        $scope.status = $stateParams.status ? $stateParams.status : "ALL";
        $scope.tableIndex = $stateParams.tableIndex ? $stateParams.tableIndex : "0";

        $scope.tabs = function (status, tableIndex) {
            $scope.status = status;
            $scope.tableIndex = tableIndex;

            if (!$scope.data[$scope.status].data) {
                $scope.data[$scope.status].data = [];
                $scope.getList()
            }
        };

        vm.size = conf.pageSize;
        // vm.size = 2;
        $scope.getList = function () {
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + '/order/list',
                params: {
                    pageSize: vm.size,
                    number: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    // status: $scope.status,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                if (!$scope.data[$scope.status].data) {
                    $scope.data[$scope.status].data = [];
                }
                //存入数据
                $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.orderInfoModel.content);
                $scope.data[$scope.status].totalNum = resp.data.data.orderInfoModel.totalElements + 1;
                $scope.data[$scope.status].number = resp.data.data.orderInfoModel.number;
                if ((resp.data.data.orderInfoModel.number + 1) * resp.data.data.orderInfoModel.size >= resp.data.data.orderInfoModel.totalElements) {
                    $scope.data[$scope.status].pageEnd = true;
                }
                $scope.data[$scope.status].number++;
            }, function (resp) {
            });
        };


        /*
         * 取消订单
         * */
        $scope.canclOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "post",
                        url: conf.apiPath + '/order/cancel',
                        data: {
                            id: id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // alertService.msgAlert('success', '取消成功');
                        $scope.data[$scope.status] = {};
                        $scope.getList()
                    }, function (resp) {
                    });
                }
            });

        };

        /*
         * 确认收货
         * */
        $scope.receive = (id) => {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/order/confirmReceive",
                        params: {
                            id: id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function () {
                        // alertService.msgAlert('success', '收货成功');
                        $scope.data[$scope.status] = {};
                        $scope.getList()
                    }, function (resp) {
                    });
                }
            });
        };

        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id) {
            $http({
                method: "GET",
                url: conf.apiPath + "/pay/order?orderId=" + id,
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
        };


        $scope.tabs($scope.status, $scope.tableIndex);

    }


    /*返回上级*/
    fallbackPage() {
        // $state.go("main.brandApp.stock", null, {reload: true});
        if ($stateParams.from == 'purchase' || ($stateParams.status || $stateParams.tableIndex)) {
            $state.go("main.brandApp.purchase", null, {reload: true});
        } else if ($stateParams.from == 'stock') {
            $state.go("main.brandApp.stock", null, {reload: true});
        } else {
            history.back();
        }
    };
}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$http',
    'loginService',
    'alertService',
    '$state',
    '$location'
];

export default Controller ;
