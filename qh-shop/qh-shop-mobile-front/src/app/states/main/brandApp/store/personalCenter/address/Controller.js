import conf from "../../../../../../conf";
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    alertService,
    $timeout,
    $rootScope,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _alertService,
                _$timeout,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        alertService=_alertService;
        $timeout=_$timeout;
        $rootScope=_$rootScope;
        $http = _$http;
        var vm=this;
        vm.brandAppId = $stateParams.brandAppId;
        vm.storeId = $stateParams.storeId;
        vm.orderId=$stateParams.orderId;
        vm.from=$stateParams.from;
        loginService.loginCtl(true,$location.absUrl());
        //增加收获地址
        vm.add=function () {
            // ui-sref="main.brandApp.store.personalCenter.addAddress"
            $state.go("main.brandApp.store.personalCenter.addAddress", {orderId:vm.orderId,from:vm.from}, {reload: true});
        };
        vm.getItem=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+vm.brandAppId+"/shop/"+ vm.storeId+"/addr",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": vm.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp',resp.data.data);
                vm.items=resp.data.data;

            }, function (resp) {
                //error
            });
        };
        vm.getItem();

        // 设为默认地址
        vm.setDefault = function (addressList,index) {
            // console.log('insdex',index);
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            $http({
            method: "PUT",              ///brandApp/{brandAppId}/shop/{shopId}/addr/{addrId}/setDefault
            url: conf.apiPath + "/brandApp/" + vm.brandAppId + "/shop/"+vm.storeId+"/addr/"+addressList.id+"/setDefault",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": vm.brandAppId
                }
            }).then(function (resp) {
                    vm.items[index].defaultAddr = true;
                    $timeout(function () {
                        vm.getItem();
                        // console.log('timeout')
                    }, 30);
                }, function () {

                }
            );
        };

        //从订单进来直接选择地址
        vm.selectAdd=function (addressList) {
            if(vm.orderId){
                $http({
                    method: "POST",     ///brandApp/{brandAppId}/shop/{shopId}/mall/order/{id}/addr/{addrId}
                    url: conf.apiPath + "/brandApp/" + vm.brandAppId + "/shop/"+vm.storeId+"/mall/order/"+vm.orderId+"/addr/"+addressList.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": vm.brandAppId
                    }
                }).then(function (resp) {
                        // vm.items[index].defaultAddr = true;
                        $state.go("main.brandApp.store.confirmOrder", {orderId:vm.orderId,from:vm.from,select:'addr'}, {reload: true});
                    }, function () {

                    }
                );
            };
        };
        /*
         * 删除地址
         * */
        vm.delete = function (addressList) {
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            alertService.confirm(null, "", "是否确认删除该地址", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url:conf.apiPath + "/brandApp/"+vm.brandAppId+"/shop/"+ vm.storeId+"/addr/" + addressList.id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": vm.brandAppId
                        }
                    }).then(function (resp) {
                            if(resp.data.status=='200'){
                                vm.getItem();
                            }
                    }, function () {

                    });

                }
            });

        };
        // console.log('vm.orderId',vm.orderId,vm.from);
        /*返回上级*/
        vm.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                // history.back();
                if(vm.orderId){
                    $state.go("main.brandApp.store.confirmOrder", {orderId:vm.orderId,from:vm.from,select:'addr'}, {reload: true});
                }else{
                    $state.go("main.brandApp.store.personalCenter.centerHome",{brandAppId:$stateParams.brandAppId}, {reload: true});;
                }
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
    'alertService',
    '$timeout',
    '$rootScope',
    '$http',
];

export default Controller ;
