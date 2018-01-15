import conf from "../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    $timeout,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope, _$stateParams, _$httpParamSerializer, _alertService, _$http, _$timeout, _loginService, _$state, _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $timeout = _$timeout;
        $location = _$location;
        var vm = this;
        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.orderId = $stateParams.orderId;
        console.log(11111111,$scope.orderId)
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/addr",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    vm.list = resp.data.data;
                    // console.log('$scope.list.recList', $scope.list.recList);
                    // $scope.$broadcast("scroll.refreshComplete"); //请求到数据刷新页面
                }, function () {
                }
            );
        };
        $scope.getList();

        /*
         * 选择收获地址
         * */
        $scope.chooseAddr = function (addrId) {
            if (!$scope.orderId) {
                return;
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/order/" + $scope.orderId + "/chooseAddr",
                params: {
                    addrId: addrId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if ($stateParams.fromStatus == 'orderManage') {
                    $state.go("main.brandApp.orderManage", {orderId: $scope.orderId}, {reload: true});

                } else if ($stateParams.fromStatus == 'manageOrderDetail') {
                    $state.go("main.brandApp.manageOrderDetail", {id: $scope.orderId}, {reload: true});

                } else {
                    $state.go("main.brandApp.order.checkOrder", {orderId: $scope.orderId}, {reload: true});

                }
            }, function (resp) {

            });
        };


        // 设为默认地址
        $scope.setDefault = function (addressList, index) {
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/addr/" + addressList.id + "/setDefault",
                // params: {
                //     id:
                // },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    vm.list.content[index].default = true;
                    $timeout(function () {
                        $scope.getList();
                    }, 30);
                }, function () {

                }
            );
        };

        /*
         * 删除地址
         * */
        $scope.delete = function (addressList, index) {
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            alertService.confirm(null, "", "是否确认删除该地址", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/addr/" + addressList.id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                            $scope.getList();
                        }, function () {

                        }
                    );

                }
            });

        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                //history.back();
                $state.go("main.brandApp.center.userInfo", null, {reload: true});
            }
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    '$timeout',
    'loginService',
    '$state',
    '$location',
];

export default Controller ;