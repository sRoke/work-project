import conf from "../../../../../../conf";
var $scope,
    loginService,
    $state,
    alertService,
    $stateParams,
    $location,
    $rootScope,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _alertService,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        alertService=_alertService;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.orderId = $stateParams.orderId;
        loginService.loginCtl(true,$location.absUrl());

        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/order/"+$scope.orderId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp---info',resp.data.data);
                $scope.items=resp.data.data;
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //支付---付款
        $scope.pay = function (id) {
            // console.log('支付')
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('支付2',resp);
                // 跳转到支付页
                /////backurl 转到我的订单页面unionOrder
                let back = 'brandApp/' + $scope.brandAppId + "/store/"+$scope.storeId +"/personalCenter/allOrder";
                location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$scope.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
            }, function (resp) {
                //TODO 失败页
            });
        };
        /*
         * 取消订单
         * */
        $scope.canclOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/cancelOrder",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $state.go("main.brandApp.store.personalCenter.allOrder", {reload: true});
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        //申请退款
        $scope.refundOrder = function (orderId,skuId) {
            // console.log(orderId,skuId)
            $state.go("main.brandApp.store.personalCenter.applyRefund", {orderID:orderId,skuID:skuId}, {reload: true});
        };
        /*
         * 确认收获
         * */
        $scope.confirmReceive  = function (id) {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/confirmReceive",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $state.go("main.brandApp.store.personalCenter.allOrder", {reload: true});
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        /*
         * 确认已自提
         * */
        $scope.confirmSince  = function (id) {
            alertService.confirm(null, "", "确认已自提？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/confirmSince",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $scope.getInfo();
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                // history.back();
                $state.go("main.brandApp.store.personalCenter.allOrder", {brandAppId:$stateParams.brandAppId}, {reload: true});
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    'alertService',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
