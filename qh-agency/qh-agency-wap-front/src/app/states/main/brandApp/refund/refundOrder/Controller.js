import conf from "../../../../../conf";

var $scope,
    $http,
    $timeout,
    $httpParamSerializer,
    $stateParams,
    $mdDialog,
    alertService,
    $filter,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$timeout,
                _$httpParamSerializer,
                _$stateParams,
                _$mdDialog,
                _alertService,
                _$filter,
                _loginService,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        $timeout = _$timeout;
        $stateParams = _$stateParams;
        $filter = _$filter;
        $mdDialog = _$mdDialog;
        loginService = _loginService;
        alertService = _alertService;
        $location = _$location;
        $httpParamSerializer = _$httpParamSerializer;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/order/checkOrder";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.orderId = $stateParams.orderId;
        $scope.checkOrder = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/" + $scope.orderId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.info = resp.data.data;
            }, function (resp) {
            });
        };
        $scope.checkOrder();

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };

        $scope.noAddress = function (id) {
            $mdDialog.show({
                templateUrl: 'noAddress.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.id = id;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.brandApp.address', {orderId: id}, {reload: true})
                        })
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        $scope.checkAddress = function (id) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/" + $scope.orderId,
                data: {
                    memo: $scope.memo
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $state.go('main.brandApp.refund.myRefundManage', {reload: true})
            }, function (resp) {
                // alertService.msgAlert('exclamation-circle', resp.data.message)
            });
            // $mdDialog.show({
            //     templateUrl: 'checkAddress.html',
            //     parent: angular.element(document.body).find('#qh-agency-admin-front'),
            //     clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
            //     fullscreen: false,
            //     locals: {key: $scope.info},
            //     controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
            //         var vmd = this;
            //         vmd.info = locals.key;
            //         vmd.id = id;
            //         vmd.orderId = locals.key.id;
            //         vmd.cancel = function () {
            //             $mdDialog.cancel();
            //         };
            //         vmd.accress = function () {
            //             $mdDialog.hide();
            //         };
            //         vmd.goAddress = function () {
            //             $mdDialog.cancel().then(function () {
            //                 $state.go('main.brandApp.address', {orderId: id}, {reload: true})
            //             })
            //         };
            //     }],
            //     controllerAs: "vmd"
            // }).then(function (answer) {
            //     $http({
            //         method: "PUT",
            //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/" + $scope.orderId,
            //         // data: {
            //         //     from: $stateParams.from,
            //         //     memo: $scope.memo
            //         // },
            //         headers: {
            //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //             "brandApp-Id": $scope.brandAppId
            //         }
            //     }).then(function (resp) {
            //         $state.go('main.brandApp.refund.myRefundManage', {reload: true})
            //     }, function (resp) {
            //         // alertService.msgAlert('exclamation-circle', resp.data.message)
            //     });
            // }, function () {
            // });
        };

        /*
         * 提交订单
         * */
        $scope.createOrder = function () {
            //$scope.pay($scope.orderId);       //测试支付，可把该行注释打开，下面请求注释，以免提交订单失败
            if (!$scope.info.address) {
                //$scope.noAddress($scope.info.id);
                alertService.msgAlert("exclamation-circle", "联系上级设置退货地址");
                return;
            }
            $scope.checkAddress($scope.info.id)

        };

    }


}

Controller.$inject = [
    '$scope',
    '$http',
    '$timeout',
    '$httpParamSerializer',
    '$stateParams',
    '$mdDialog',
    'alertService',
    '$filter',
    'loginService',
    '$state',
    '$location',
];

export default Controller ;
