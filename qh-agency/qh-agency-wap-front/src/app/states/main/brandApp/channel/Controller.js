import conf from "../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;

        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true);


        $scope.orderId = $stateParams.orderId;
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/addr/list",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.list = resp.data.data;
                    $scope.$broadcast("scroll.refreshComplete"); //请求到数据刷新页面
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
                method: "POST",
                url: conf.apiPath + "/order/chooseAddr",
                data: {
                    orderId: $scope.orderId,
                    addrId: addrId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $state.go("main.brandApp.order.checkOrder", {orderId: $scope.orderId}, {reload: true});
            }, function (resp) {

            });
        };


        // 设为默认地址
        $scope.setDefault = function (addressList, index) {
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            $http({
                method: "GET",
                url: conf.apiPath + '/addr/setDefault',
                params: {
                    id: addressList.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.list.recList[index].isDefault = true;
                    $scope.getList();
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
                        method: "GET",
                        url: conf.apiPath + '/addr/delete',
                        params: {
                            id: addressList.id
                        },
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
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
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
    'loginService',
    '$state'
];

export default Controller ;
