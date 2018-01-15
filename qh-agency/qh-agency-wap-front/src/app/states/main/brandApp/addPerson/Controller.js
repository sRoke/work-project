import conf from "../../../../conf";
import weui from 'weui.js';


var $scope,
    $rootScope,
    addressService,
    $http,
    $timeout,
    $stateParams,
    alertService,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$rootScope,
                _addressService,
                _$http,
                _$timeout,
                _$stateParams,
                _alertService,
                _loginService,
                _$state,
                _$location) {
        $scope = _$scope;
        $rootScope = _$rootScope;
        $state = _$state;
        addressService = _addressService;
        $http = _$http;
        $timeout = _$timeout;
        alertService = _alertService;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $location = _$location;
        const TAG = "main/addAddress ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        var vm = this;
        ////////////////////////////////////

        $scope.editId = $stateParams.id;
        $scope.orderId = $stateParams.orderId;
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
               // history.back();
                $state.go("main.brandApp.personalAddress", null, {reload: true});
            }
        };

        $scope.newadd = 0;
        $scope.address = {};

        $scope.default = {};
        /**
         * 开始执行js，在最后一行调用了,控制js顺序
         */
        $scope.start = function () {
            if ($stateParams.id) {
                $scope.newadd = 1;
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111/addr/" + $stateParams.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    var d = resp;
                    $scope.address = d.data.data;
                    $scope.init();
                }, function (resp) {
                    // $scope.init(); //接口完善后删除
                    // var data = resp.data;
                    // $scope.address = data.address;
                });
            } else {
                $scope.init();
            }
        };


        //判断是新增地址还是修改地址 newadd 为0的时候就是新增地址 为1的时候就是修改地址
        $scope.init = function () {
            if ($scope.newadd === 0) {
                $scope.addrTitle = "新增收货地址";
                $scope.phone = null;
                $scope.memo = '';
                $scope.defaultAddr = false;
            } else if ($scope.newadd === 1) {
                //当点击修改的时候，
                $scope.addrTitle = "修改收货地址";
                vm.adcNo = $scope.address.adcNo;
                $scope.street = $scope.address.street;
                $scope.receiver = $scope.address.receiver;
                $scope.phone = $scope.address.phone;
                vm.province = $scope.address.province;
                vm.city = $scope.address.city;
                vm.area = $scope.address.area;
                $scope.editId = $scope.address.id;

                $scope.memo = '';
                $scope.defaultAddr = $scope.address.default;
                if ($stateParams.defaultAddr === 'true') {
                    $scope.address.defaultAddr = true;
                    $scope.default = true;
                }
            }
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
                onConfirm: function (result) {
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
                    $scope.$apply();
                },
                id: 'cascadePicker'
            });
        };

        $scope.PHONE_REGEXP = /^[\d]{11}$/i;
        //收货人blur事件:
        $scope.addressContact = function () {
            $scope.contactRed = false;
            if (!$scope.receiver) {
                alertService.msgAlert("exclamation-circle", "请填写收货人姓名");
                $scope.contactRed = true;
            }
            //收货人姓名长度不能少于2大于10
            else if ($scope.receiver.length < 2 || $scope.receiver.length > 10) {
                $scope.contactRed = true;
                alertService.msgAlert("exclamation-circle", "收货人姓名为2到10个字符");
            }
        };
        //手机号blur事件:
        $scope.addressPhone = function () {
            $scope.phoneRed = false;
            $scope.PHONE_REGEXP = /^[\d]{11}$/i;
            if (!$scope.PHONE_REGEXP.test($scope.phone)) {
                alertService.msgAlert("exclamation-circle", "请填写正确的手机号");
            }
        };
        //街道blur事件:
        $scope.addressStreet = function () {
            $scope.streetRed = false;
            if (!$scope.street) {
                alertService.msgAlert("exclamation-circle", "请填写街道地址");
                $scope.streetRed = true;
            }
        };


        $scope.save = function () {

            if (!$scope.street) {
                alertService.msgAlert("exclamation-circle", "请填写街道");
                $scope.streetRed = true;
                return;
            }
            // 收货人姓名需要填写
            if (!$scope.receiver) {
                $scope.contactRed = true;
                alertService.msgAlert("exclamation-circle", "请填写收货人姓名");
                $scope.receiver = '';
                return;
            }
            //收货人姓名长度不能少于2大于10
            if ($scope.receiver.length < 2 || $scope.receiver.length > 10) {
                $scope.contactRed = true;
                alertService.msgAlert("exclamation-circle", "收货人姓名为2到10个字符");
                return;
            }
            // 电话需要填写
            if (!$scope.phone) {
                alertService.msgAlert("cancle", "手机号码不能为空");
                $scope.phoneRed = true;
                return;
            }
            //手机号码格式必须匹配
            if (!$scope.PHONE_REGEXP.test($scope.phone)) {
                alertService.msgAlert("cancle", "请填写正确的手机号");
                $scope.phoneRed = true;
                return;
            }
            // 地址不能为空
            // if (!$scope.provinceId) {
            //     alertService.msgAlert("exclamation-circle", "请选择完整地址");
            //     return;
            // }

            // if ($scope.areas != null && $scope.areas.length > 0) {
            //     if (!$scope.areaId) {
            //         alertService.msgAlert("exclamation-circle", "请选择完整地址");
            //         return;
            //     } else {
            //         $scope.adcNo = $scope.areaId;
            //     }
            // } else if ($scope.citys && $scope.citys.length > 0) {
            //     //  直辖区等地
            //     if (!$scope.cityId) {
            //         alertService.msgAlert("exclamation-circle", "请选择完整地址");
            //         return;
            //     } else {
            //         $scope.adcNo = $scope.cityId;
            //     }
            // } else {
            //     // 进行对省赋值 (香港 澳门等地)
            //     if (!$scope.provinceId) {
            //         alertService.msgAlert("exclamation-circle", "请选择完整地址");
            //         return;
            //     } else {
            //         $scope.adcNo = $scope.provinceId;
            //     }
            // }


            if ($stateParams.id) {
                $scope.default.defaultAddr = $scope.address.default;
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/addr/" + $stateParams.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        'brandApp-Id': $scope.brandAppId
                    },
                    data: {
                        adcNo: vm.adcNo,
                        id: $scope.editId ? $scope.editId : null,
                        street: $scope.street,
                        receiver: $scope.receiver,
                        phone: $scope.phone,
                        memo: '',
                        defaultAddr: $scope.default.defaultAddr ? $scope.default.defaultAddr : false,
                    },
                }).then(function () {
                    $state.go("main.brandApp.personalAddress", {orderId: $scope.orderId}, {reload: true})
                }, function (resp) {
                });

            } else {
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/addr",
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        'brandApp-Id': $scope.brandAppId
                    },
                    data: {
                        adcNo: vm.adcNo,
                        id: $scope.editId ? $scope.editId : null,
                        street: $scope.street,
                        receiver: $scope.receiver,
                        phone: $scope.phone,
                        memo: '',
                        defaultAddr: $scope.default.defaultAddr ? $scope.default.defaultAddr : false,
                    },
                }).then(function () {
                    $state.go("main.brandApp.personalAddress", {orderId: $scope.orderId}, {reload: true})
                }, function (resp) {
                });
            }

        };
        $scope.start();

    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    'addressService',
    '$http',
    '$timeout',
    '$stateParams',
    'alertService',
    'loginService',
    '$state',
    '$location',
];

export default Controller ;
