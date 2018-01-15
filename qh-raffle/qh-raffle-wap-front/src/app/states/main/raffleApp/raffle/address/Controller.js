import conf from "../../../../../conf";
import store from "store";
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
        vm.raffleAppId = $stateParams.raffleAppId;
        vm.raffleId = $stateParams.raffleId;
        vm.orderId=$stateParams.orderId;
        //增加收获地址
        vm.add=function () {
            $state.go("main.raffleApp.raffle.addAddress",{orderId:vm.orderId}, {reload: true});
        };
        vm.getItem=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/"+vm.raffleAppId+"/raffle/"+ vm.raffleId+"/addr",
                params:{
                    openId:store.get('openId'),
                },
                headers: {
                   // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": vm.raffleAppId
                }
            }).then(function (resp) {
                console.log('resp',resp.data.data);
                vm.items=resp.data.data;

            }, function (resp) {
                //error
            });
        };
        vm.getItem();

        // 设为默认地址
        vm.setDefault = function (addressList,index) {
            console.log('insdex',index);
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            $http({
                method: "PUT",              ///raffleApp/{raffleAppId}/shop/{shopId}/addr/{addrId}/setDefault
                url: conf.apiPath + "/raffleApp/" + vm.raffleAppId + "/raffle/"+vm.raffleId+"/addr/"+addressList.id+"/setDefault",
                params:{
                    openId:store.get('openId'),
                },
                headers: {
                    "raffleApp-Id": vm.raffleAppId
                }
            }).then(function (resp) {
                    vm.items[index].defaultAddr = true;
                    $timeout(function () {
                        vm.getItem();
                    }, 30);
                }, function () {

                }
            );
        };

        //从订单进来直接选择地址
        vm.selectAdd=function (addressList) {
            if(vm.orderId){
                $http({
                    method: "POST",     ///raffleApp/{raffleAppId}/shop/{shopId}/mall/order/{id}/addr/{addrId}
                    url: conf.apiPath + "/raffleApp/" + vm.raffleAppId + "/raffle/"+vm.raffleId+"/wap/record/"+vm.orderId+"/addr/"+addressList.id,
                    params:{
                        openId:store.get('openId'),
                    },
                    headers: {
                        "raffleApp-Id": vm.raffleAppId
                    }
                }).then(function (resp) {
                        // vm.items[index].defaultAddr = true;
                        $state.go("main.raffleApp.raffle.confirmOrder", {orderId:vm.orderId}, {reload: true});
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
                        url:conf.apiPath + "/raffleApp/"+vm.raffleAppId+"/raffle/"+ vm.raffleId+"/addr/" + addressList.id,
                        params:{
                            openId:store.get('openId'),
                        },
                        headers: {
                            "raffleApp-Id": vm.raffleAppId
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

        /*返回上级*/
        vm.fallbackPage = function () {
            $state.go("main.raffleApp.raffle.confirmOrder", {orderId:vm.orderId}, {reload: true});
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
