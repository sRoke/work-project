import conf from "../../../../../../conf";
import weui from 'weui.js';
var $scope,
    alertService,
    loginService,
    $state,
    $stateParams,
    $location,
    $rootScope,
    $http;
class Controller {
    constructor(_$scope,
                _alertService,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        alertService=_alertService;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope=_$rootScope;
        $http = _$http;
        var vm = this;
        vm.brandAppId = $stateParams.brandAppId;
        vm.storeId = $stateParams.storeId;
        vm.addrId=$stateParams.addrId;
        vm.orderId=$stateParams.orderId;
        vm.from=$stateParams.from;
        loginService.loginCtl(true,$location.absUrl());

        vm.item={};
        vm.getItem=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+vm.brandAppId+"/shop/"+ vm.storeId+"/addr/"+vm.addrId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": vm.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp',resp.data.data);
                vm.item=resp.data.data;

            }, function (resp) {
                //error
            });
        };
        vm.getItem();
        //打开地址弹窗
        vm.openAdcDialog = function () {
            weui.picker($rootScope.adc.data, {
                depth: 3,
                defaultValue: [0, 1, 1],
                onChange: function (result) {
                    for (let i = 0; i < result.length; i++) {
                        $scope.adcNo = result[result.length - 1].value;
                    }
                },
                onConfirm: function (result) {            //点击确定
                    for (let i = 0; i < result.length; i++) {
                        if (result.length == 3) {
                            vm.item.province = result[result.length - 3].label;
                            vm.item.city = result[result.length - 2].label;
                            vm.item.area = result[result.length - 1].label;
                        } else if (result.length == 2) {
                            vm.item.province = result[result.length - 2].label;
                            vm.item.city = result[result.length - 1].label;
                        } else {
                            vm.item.province = result[result.length - 1].label;
                        }
                        vm.item.adcNo = result[result.length - 1].value;
                    }
                    // console.log('result1', result, vm.item.adcNo);
                    $scope.$apply();
                },
                id: 'cascadePicker'
            });
        };
        vm.changeCheck=function () {
            vm.item.defaultAddr=!vm.item.defaultAddr;
        };
        vm.save = function () {
            // console.log(' vm.item');
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + vm.brandAppId + "/shop/" + vm.storeId + "/addr/"+vm.addrId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    adcNo: vm.item.adcNo,
                    receiver: vm.item.receiver,
                    street: vm.item.street,
                    phone: vm.item.phone,
                    defaultAddr: vm.item.defaultAddr,
                },
            }).then(function (resp) {
                // console.log('resp',resp);
                if(resp.data.status=='200'){
                    $state.go("main.brandApp.store.personalCenter.address", {orderId: vm.orderId,from:vm.from}, {reload: true});
                }
            }, function (resp) {
                //error
            });
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
                            $state.go("main.brandApp.store.personalCenter.address", {orderId: vm.orderId,from:vm.from}, {reload: true});
                        }
                    }, function () {

                    });

                }
            });

        };
        /*返回上级*/
        vm.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId: $stateParams.brandAppId}, {reload: true});
            } else {
                if(vm.orderId){
                    $state.go("main.brandApp.store.personalCenter.address", {orderId: vm.orderId,from:vm.from}, {reload: true});
                }else{
                    $state.go("main.brandApp.store.personalCenter.address", {brandAppId: $stateParams.brandAppId}, {reload: true});
                }
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'alertService',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
