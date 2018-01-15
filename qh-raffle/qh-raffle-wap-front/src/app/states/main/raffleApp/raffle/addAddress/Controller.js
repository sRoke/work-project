import conf from "../../../../../conf";
import weui from 'weui.js';
import store from "store";
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
        $location = _$location;
        $rootScope = _$rootScope;
        $http = _$http;
        $scope.raffleAppId = $stateParams.raffleAppId;
        $scope.raffleId = $stateParams.raffleId;
        $scope.orderId=$stateParams.orderId;
        //loginService.loginCtl(true, $location.absUrl());
        var vm = this;
        //选中默认
        vm.item = {};
        vm.item.checked = false;
        $scope.changeCheck = function () {
            vm.item.checked = !vm.item.checked;
        };
        //打开地址弹窗
        $scope.openAdcDialog = function () {
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
                            vm.province = result[result.length - 3].label;
                            vm.city = result[result.length - 2].label;
                            vm.area = result[result.length - 1].label;
                        } else if (result.length == 2) {
                            vm.province = result[result.length - 2].label;
                            vm.city = result[result.length - 1].label;
                        } else {
                            vm.province = result[result.length - 1].label;
                        }
                        vm.adcNo = result[result.length - 1].value;
                    }
                    console.log('result1', result, vm.adcNo);
                    $scope.$apply();
                },
                id: 'cascadePicker'
            });
        };
        //收货人blur事件:
        vm.addressContact = function (receiver) {
            if (!receiver) {
                alertService.msgAlert("exclamation-circle", "请填写详细地址");
            }
            //收货人姓名长度不能少于2大于10
            else if (receiver.length < 2 || receiver.length > 10) {
                // $scope.contactRed = true;
                alertService.msgAlert("exclamation-circle", "收件人姓名为2到10个字符");
            }
        };
        //手机号blur事件:
        vm.addressPhone = function (phone) {
            vm.PHONE_REGEXP = /^[\d]{11}$/i;
            if (!vm.PHONE_REGEXP.test(phone)) {
                alertService.msgAlert("exclamation-circle", "请填写正确的手机号");
            }
        };
        //街道blur事件:
        vm.addressStreet = function (street) {
            if (!street) {
                alertService.msgAlert("exclamation-circle", "请填写详细地址");
            }
        };
        vm.PHONE_REGEXP = /^[\d]{11}$/i;
        vm.save = function () {
            console.log(' vm.item', vm.item);
            if (!vm.item.street) {
                alertService.msgAlert("exclamation-circle", "请填写详细地址");
                $scope.streetRed = true;
                return;
            }
            // 收货人姓名需要填写
            if (!vm.item.receiver) {
                alertService.msgAlert("exclamation-circle", "请填写收件人姓名");
                vm.item.receiver = '';
                return;
            }
            //收货人姓名长度不能少于2大于10
            if (vm.item.receiver.length < 2 || vm.item.receiver.length > 10) {
                alertService.msgAlert("exclamation-circle", "收件人姓名为2到10个字符");
                return;
            }
            // 电话需要填写
            if (!vm.item.phone) {
                alertService.msgAlert("cancle", "手机号码不能为空");
                return;
            }
            //手机号码格式必须匹配
            if (!vm.PHONE_REGEXP.test(vm.item.phone)) {
                alertService.msgAlert("cancle", "请填写正确的手机号");
                vm.item.phoneRed = true;
                return;
            }
            //验证是否选择地址
            if (!vm.adcNo) {
                alertService.msgAlert("cancle", "请选择所在地区");
                return;
            }
            $http({
                method: "POST",
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/raffle/" + $scope.raffleId + "/addr",
                headers: {
                    "raffleApp-Id": $scope.raffleAppId
                },
                data: {
                    openId:store.get('openId'),
                    adcNo: vm.adcNo,
                    receiver: vm.item.receiver,
                    street: vm.item.street,
                    phone: vm.item.phone,
                    defaultAddr: vm.item.checked,
                },
            }).then(function (resp) {
               console.log('resp',resp);
               if(resp.data.status=='200'){
                   $state.go("main.raffleApp.raffle.address", {orderId: $scope.orderId}, {reload: true});
               }
            }, function (resp) {
                //error
            });
        };
        console.log('$scope.orderId',$scope.orderId,$scope.from,)
        /*返回上级*/
        vm.fallbackPage = function () {
            $state.go("main.raffleApp.raffle.address", {orderId: $scope.orderId,from:$scope.from}, {reload: true});
            // if (history.length === 1) {
            //     $state.go("main.raffleApp.store.home", {raffleAppId: $stateParams.raffleAppId}, {reload: true});
            // } else {
            //     if($scope.orderId){
            //         $state.go("main.raffleApp.store.personalCenter.address", {orderId: $scope.orderId,from:$scope.from}, {reload: true});
            //     }else{
            //         $state.go("main.raffleApp.store.personalCenter.address", {raffleAppId: $stateParams.raffleAppId}, {reload: true});
            //     }
            // }
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
