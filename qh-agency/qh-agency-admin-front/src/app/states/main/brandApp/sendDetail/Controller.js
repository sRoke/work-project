// import conf from "../../../../conf";
// import dialog from "!html-loader?minimize=true!./updateAddress.html";
//
// var $scope,
//     $http,
//     loginService,
//     authService,
//     errorService,
//     $state,
//     $stateParams,
//     $mdDialog,
//     alertService;
// class Controller {
//     constructor(_$scope, _$http, _alertService, _loginService, _authService, _errorService, _$state, _$stateParams, _$mdDialog) {
//         $scope = _$scope;
//         $http = _$http;
//         alertService = _alertService;
//         loginService = _loginService;
//         authService = _authService;
//         errorService = _errorService;
//         $state = _$state;
//         $stateParams = _$stateParams;
//         $mdDialog = _$mdDialog;
//         loginService.loginCtl(true);
//         //权限相关
//         $scope.ORDER_U = authService.hasAuthor("ORDER_U");    //发货
//         $scope.ORDER_C = authService.hasAuthor("ORDER_C");    //改价
//         $scope.ORDER_D = authService.hasAuthor("ORDER_D");    //修改地址
//
//         $scope.brandAppId = $stateParams.brandAppId;
//         $scope.order = {};
//         $scope.id = $stateParams.id;
//         $scope.getInfo = function () {
//             $http({
//                 method: "GET",
//                 url: conf.apiPath + "/order/info",
//                 headers: {
//                     'Authorization': 'Bearer ' + loginService.getAccessToken(),
//                     "brandApp-Id": $scope.brandAppId
//                 },
//                 params: {
//                     id: $scope.id,
//                 }
//             }).then(function successCallback(response) {
//                 $scope.order = response.data.data;
//             }, function errorCallback(response) {
//                 // 请求失败执行代码
//             });
//         }
//         $scope.getInfo();
//         $scope.adjustAprice = function () {
//             var adjustPriceReg = /^[0-9]+(.[0-9]{1,2})?$/;
//             if (!adjustPriceReg.test($scope.order.adjust)) {
//                 return errorService.error("请输入有效的改价！", null)
//             }
//             $http({
//                 method: "POST",
//                 url: conf.apiPath + "/order/adjustAprice",
//                 headers: {
//                     'Authorization': 'Bearer ' + loginService.getAccessToken(),
//                     "brandApp-Id": $scope.brandAppId
//                 },
//                 data: {
//                     id: $scope.id,
//                     adjustPrice: $scope.order.adjust * 100
//                 }
//             }).then(function successCallback(response) {
//                 //$scope.order = response.data.data;
//                 $scope.getInfo()
//             }, function errorCallback(response) {
//                 // 请求失败执行代码
//             });
//         }
//
//         $scope.shipCheck = function () {
//             if (!$scope.order.brandApp) {
//                 return errorService.error("快递公司不能为空！", null)
//             }
//             if (!$scope.order.expressNo) {
//                 return errorService.error("物流单号不能为空！", null)
//             }
//             if ($scope.order.haveRefund) {
//                 alertService.confirm(null, '订单有退款请求，确认继续发货吗？', "警告", "取消", "确认")
//                     .then(function (data) {
//                         if(data){
//                             $scope.ship();
//                         }
//                     });
//                 return;
//             }else{
//                 $scope.ship();
//             }
//         };
//
//         $scope.ship = function () {
//             $http({
//                 method: "POST",
//                 url: conf.apiPath + "/order/ship",
//                 headers: {
//                     'Authorization': 'Bearer ' + loginService.getAccessToken(),
//                     "brandApp-Id": $scope.brandAppId
//                 },
//                 data: {
//                     id: $scope.id,
//                     brandApp: $scope.order.brandApp,
//                     expressNo: $scope.order.expressNo,
//                 }
//             }).then(function successCallback(response) {
//                 $scope.getInfo();
//             }, function errorCallback(response) {
//                 // 请求失败执行代码
//             });
//         }
//
//         $scope.updateAddress = function (locals, ev) {
//             locals = locals ? locals : {};
//             $mdDialog.show({
//                 template: dialog,
//                 parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-admin-front'),
//                 targetEvent: ev,
//                 clickOutsideToClose: true,
//                 locals: locals,
//                 fullscreen: false,
//                 controller: [function () {
//                     var vm = this;
//                     vm.orderId = locals.orderId;
//                     vm.id = locals.address.id;
//                     vm.street = locals.address.street;
//                     vm.receiver = locals.address.receiver;
//                     vm.phone = locals.address.phone;
//                     vm.province = locals.address.province;
//                     vm.city = locals.address.city;
//                     vm.county = locals.address.county;
//                     vm.provinceNo = locals.address.provinceNo;
//                     vm.cityNo = locals.address.cityNo;
//                     vm.dateCreated = locals.address.dateCreated;
//                     vm.countyNo = locals.address.countyNo;
//                     vm.oldAddress = vm.province + vm.city + vm.county + vm.street;
//
//                     vm.isPhoneNumTrue = false;
//                     //限制手机号输入，只能输入以1开头的数字，不做正则验证
//                     vm.checkPhone = function () {
//                         var phone = vm.phone + '';
//                         //如果未输入
//                         if (!phone || phone === '' || phone === 'null') {
//                             vm.isPhoneNumTrue = false;
//                             return -1;
//                         }
//                         //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
//                         if (isNaN(phone) || phone.substr(0, 1) !== '1') {
//                             vm.phone = phone.substr(0, phone.length - 1);
//                             vm.isPhoneNumTrue = false;
//                             return -1;
//                         }
//                         //如果长度超过11，抹去最后一位
//                         if (phone.length > 11) {
//                             vm.phone = phone.substr(0, phone.length - 1);
//                             vm.isPhoneNumTrue = true;
//                             return -1;
//                         }
//                         if (phone.length !== 11) {
//                             vm.isPhoneNumTrue = false;
//                             return -1;
//                         }
//                         vm.isPhoneNumTrue = true;
//                         return 0;
//                     };
//                     vm.getAddress = function (isInit, typeNo, level) {
//                         console.log("===" + typeNo + "===" + level);
//                         $http({
//                             method: "POST",
//                             url: conf.apiPath + "/order/queryAdc",
//                             headers: {
//                                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
//                                 "brandApp-Id": $scope.brandAppId
//                             },
//                             data: {
//                                 typeNo: typeNo,
//                                 level: level,
//                             },
//                         }).then(function successCallback(response) {
//                             if (response.status == 200) {
//                                 if (response.data.data.level == 0) {
//                                     vm.provinces = response.data.data;
//                                 } else if (response.data.data.level == 1) {
//                                     vm.citys = response.data.data;
//                                     if (!isInit) {
//                                         vm.countys = [];
//                                     }
//                                 } else if (response.data.data.level == 2) {
//                                     vm.countys = response.data.data;
//                                 }
//                             }
//                         }, function errorCallback(response) {
//                             console.log("---------------------error aaa");
//                         });
//                     };
//                     vm.getAddress(true, null, 0);
//                     vm.getAddress(true, vm.provinceNo, 1);
//                     vm.getAddress(true, vm.cityNo, 2);
//                     vm.save = function () {
//                         if (!vm.isPhoneNumTrue) {
//                             alertService.msgAlert("cancle", "正确输入手机号");
//                             return;
//                         }
//                         $http({
//                             method: "POST",
//                             url: conf.apiPath + "/order/updateAddress",
//                             headers: {
//                                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
//                                 "brandApp-Id": $scope.brandAppId
//                             },
//                             data: {
//                                 orderId: vm.orderId,
//                                 receiver: vm.receiver,
//                                 phone: vm.phone,
//                                 countyNo: vm.countyNo,
//                                 street: vm.street,
//                             },
//                         }).then(function successCallback(response) {
//                             if (response.status == 200) {
//                                 $mdDialog.cancel();
//                                 $scope.getInfo();
//                             }
//                         }, function errorCallback(response) {
//                             console.log("---------------------error aaa");
//                         });
//                     };
//
//                     vm.cancel = function () {
//                         return $mdDialog.cancel();
//                     };
//                 }],
//                 controllerAs: "vm"
//             }).then(function (answer) {
//             }, function () {
//             });
//         };
//
//         // 返回按钮
//         $scope.fallbackPage = function () {
//             if (history.length === 1) {
//                 $state.go("main.brandApp.index", null, {reload: true});
//             } else {
//                 history.back();
//             }
//         };
//     }
// }
//
// Controller.$inject = [
//     '$scope', '$http', 'alertService', 'loginService', 'authService', 'errorService', '$state', '$stateParams', '$mdDialog'
// ];
//
// export default Controller ;
import conf from "../../../../conf";
import moment from "moment";
import info from "!html-loader?minimize=true!./info.html";
var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _authService,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $state.params.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $state.params.id);
        $scope.id = $state.params.id;
        $scope.DELIVERINVOICE_U = authService.hasAuthor("DELIVERINVOICE_U");    //发货
        $scope.DELIVERINVOICE_R = authService.hasAuthor("DELIVERINVOICE_R");    //查看发货单
        // js控制写在此处
        //当状态为已经发货时，返回发货单管理
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.sendOrder", null, {reload: true});
            } else {
                history.back();
            }
        };
        //初始化
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/deliverInvoice/"+$scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function successCallback(response) {
                //console.log( '发货单详情',response.data);
                $scope.orderId=response.data.data.orderId;
                $scope.sendXq=response.data.data;
                $scope.logisticsCompanys=response.data.data.logisticsCompanys;
                //根据发货人ID，去Oauth查询信息
                if(response.data.data.deliverStaffId){
                    $http({
                        method: "GET",
                        url: conf.oauthPath + '/api/user/info',
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        params: {
                            keyWords: response.data.data.deliverStaffId,
                        },
                    }).then(function successCallback(response) {
                        $scope.sendName=response.data.data;
                    }, function errorCallback(response) {
                        // 请求失败执行代码 return errorService.error(response.message, null);

                    });
                }
                //根据orderID获取订单详情
                $http({
                    method: "GET",
                    url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/order/"+$scope.orderId,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function successCallback(response) {
                    //console.log('订单详情',response.data);
                    $scope.order = response.data.data;
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });


            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        };
        $scope.getInfo();
        //发货出现弹框,
        $scope.openDialog = function (ev) {
            $mdDialog.show({
                template: info,
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: false,
                controller: [function () {
                    var vm = this;
                    vm.logisticsCompanys=$scope.logisticsCompanys;
                    console.log($scope.logisticsCompanys);
                    vm.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vm.ship=function(){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/deliverInvoice/"+ $scope.id,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: {
                                company:vm.code,
                                expressNo:vm.expressNo
                            }
                        }).then(function successCallback(response) {
                            //console.log('123',response);
                            vm.cancel();
                            $scope.getInfo();
                        }, function errorCallback(response) {
                            // 请求失败执行代码 return errorService.error(response.message, null);

                        });
                    }
                }],
                controllerAs: "vm"
            }).then(function (answer) {
            }, function () {
            });
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    'authService',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;