import conf from "../../../../conf";
import dialog from "!html-loader?minimize=true!./updateAddress.html";

var $scope,
    $http,
    loginService,
    authService,
    errorService,
    $state,
    $filter,
    $stateParams,
    $mdDialog,
    alertService;
class Controller {
    constructor(_$scope, _$http, _alertService, _loginService, _authService, _errorService,_$filter, _$state, _$stateParams, _$mdDialog) {
        $scope = _$scope;
        $http = _$http;
        alertService = _alertService;
        loginService = _loginService;
        authService = _authService;
        errorService = _errorService;
        $state = _$state;
        $filter= _$filter;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_C = authService.hasAuthor("ORDER_C");    //改价
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.DELIVERINVOICE_C = authService.hasAuthor("DELIVERINVOICE_C");    //修改地址
        $scope.DELIVERINVOICE_R = authService.hasAuthor("DELIVERINVOICE_R");    //查看发货单


        $scope.brandAppId = $stateParams.brandAppId;
        $scope.order = {};
        $scope.id = $stateParams.id;
        //获取发货单的id
        $scope.getSendId = function () {
            $http({
                method:"get",
                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/deliverInvoice/"+ $scope.id+"/deliverInvoice",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                $scope.sendId = data.data;
                //console.log('sendId',data.data);
            });
        };
        //初始化   展示当前订单详情
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/order/"+$scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function successCallback(response) {
                $scope.order = response.data.data;
               // console.log('订单详情',response);
                $scope.items = response.data.data.orderItems;   //订单商品信息
                $scope.memo = response.data.data.memo;
                if($scope.order.status==='UNSHIPPED'){
                    $scope.getSendId();
                }
            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        };
        $scope.getInfo();
        //待支付  改价
        $scope.adjustAprice = function () {
            var adjustPriceReg = /^[0-9]+(.[0-9]{1,2})?$/;
            if (!adjustPriceReg.test($scope.order.adjust)) {
                return errorService.error("请输入有效的改价！", null)
            }
            alertService.confirm(null, "确定修改价格？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        //改价接口
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/adjustPrice",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                // adjustPrice: $filter('number')()
                                adjustPrice: $scope.order.adjust*100
                            }
                        }).then(function successCallback(response) {
                           // console.log(response);
                            $scope.getInfo()
                        }, function errorCallback(response) {
                            // 请求失败执行代码     return errorService.error("！", null)
                        });
                    }
                });
        };
        //取消订单
        $scope.cancelOrder = function () {
            alertService.confirm(null, "确定取消该订单？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/cancelOrder",
                            params: {
                                id: $scope.id
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            //console.log(data);
                            if (data.status == 200) {
                                $scope.fallbackPage();
                                //$scope.getInfo();
                            }
                        });
                    }
                });

        };
        //确认接单
        $scope.confirmOrder = function () {
            alertService.confirm(null, "确定接单？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method:"PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/confirmOrder",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            //console.log(data);
                            if (data.status == 200) {
                                $scope.getInfo();
                            }
                        });
                    }
                });
        };

        //弹窗
        $scope.showPrerenderedDialog = function (ev) {
            $mdDialog.show({
                templateUrl: 'myDialog.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        //
        // send发货弹窗
        $scope.send = function (ev) {
            $mdDialog.show({
                templateUrl: 'sendDialog.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        //拒绝接单确认tk
        $scope.defaultOrd = function (ev) {
            $mdDialog.show({
                templateUrl: 'defaultOrder.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    //拒绝接单js
                    vmd.reject = function () {
                        $http({
                            method:"PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/rejectOrder",
                            params: {
                                memo: vmd.reason
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            if (data.status == '200') {
                                vmd.cancel();
                                $scope.fallbackPage();
                            }
                        });
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        //卖家备注tk
        $scope.remark = function (ev) {
            $mdDialog.show({
                templateUrl: 'remark.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                //locals: {key: $scope.memo},    //用来传数据
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    // console.log(locals);
                    // vmd.memo = locals.key;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    //卖家备注js
                    vmd.sellerMemo = function () {
                        $http({
                            method:"PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/sellerMemo",
                            params: {
                                memo: vmd.memo
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            if (data.status == '200') {
                                $scope.getInfo();
                                vmd.cancel();
                            }
                        });
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        // $scope.shipCheck = function () {
        //     if (!$scope.order.brandApp) {
        //         return errorService.error("快递公司不能为空！", null)
        //     }
        //     if (!$scope.order.expressNo) {
        //         return errorService.error("物流单号不能为空！", null)
        //     }
        //     if ($scope.order.haveRefund) {
        //         alertService.confirm(null, '订单有退款请求，确认继续发货吗？', "警告", "取消", "确认")
        //             .then(function (data) {
        //                 if (data) {
        //                     $scope.ship();
        //                 }
        //             });
        //         return;
        //     } else {
        //         $scope.ship();
        //     }
        // };

        // $scope.ship = function () {
        //     $http({
        //         method: "POST",
        //         url: conf.apiPath + "/order/ship",
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //             "brandApp-Id": $scope.brandAppId
        //         },
        //         data: {
        //             id: $scope.id,
        //             brandApp: $scope.order.brandApp,
        //             expressNo: $scope.order.expressNo,
        //         }
        //     }).then(function successCallback(response) {
        //         $scope.getInfo();
        //     }, function errorCallback(response) {
        //         // 请求失败执行代码
        //     });
        // }

        $scope.updateAddress = function (locals, ev) {
            locals = locals ? locals : {};
            $mdDialog.show({
                template: dialog,
                parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: locals,
                fullscreen: false,
                controller: [function () {
                    var vm = this;
                    vm.orderId = locals.orderId;
                    vm.id = locals.address.id;
                    vm.street = locals.address.street;
                    vm.receiver = locals.address.receiver;
                    vm.phone = locals.address.phone;
                    vm.province = locals.address.province?locals.address.province:"";
                    vm.city = locals.address.city?locals.address.city:"";
                    vm.county = locals.address.county?locals.address.county:"";
                    vm.provinceNo = locals.address.provinceNo;
                    vm.cityNo = locals.address.cityNo;
                    vm.dateCreated = locals.address.dateCreated;
                    vm.countyNo = locals.address.countyNo;

                    vm.oldAddress = vm.province + vm.city + vm.county + vm.street;
                   // vm.isPhoneNumTrue = false;
                    //限制手机号输入，只能输入以1开头的数字，不做正则验证
                    // vm.checkPhone = function () {
                    //     var phone = vm.phone + '';
                    //     console.log(phone);
                    //     //如果未输入
                    //     if (!phone || phone === '' || phone === 'null') {
                    //         vm.isPhoneNumTrue = false;
                    //         return -1;
                    //     }
                    //     //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
                    //     if (isNaN(phone) || phone.substr(0, 1) !== '1') {
                    //         vm.phone = phone.substr(0, phone.length - 1);
                    //         vm.isPhoneNumTrue = false;
                    //         return -1;
                    //     }
                    //     //如果长度超过11，抹去最后一位
                    //     if (phone.length > 11) {
                    //         vm.phone = phone.substr(0, phone.length - 1);
                    //         vm.isPhoneNumTrue = true;
                    //         return -1;
                    //     }
                    //     if (phone.length !== 11) {
                    //         vm.isPhoneNumTrue = false;
                    //         return -1;
                    //     }
                    //     vm.isPhoneNumTrue = true;
                    //     return 0;
                    // };
                    //获取地址
                    // vm.getAddress = function (type) {
                    //     $http({
                    //         method: "GET",
                    //         url: conf.apiPath + "/addr/queryAdc",
                    //         headers: {
                    //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //             "brandApp-Id": $scope.brandAppId
                    //         },
                    //         params: {
                    //             adc: type,
                    //         },
                    //     }).then(function successCallback(response) {
                    //         console.log('111111111',response);
                    //         if (response.status === 200) {
                    //             $scope.provinces = [];
                    //             $scope.citys = [];
                    //             $scope.countrys = [];
                    //             $scope.provinces.push(response.data.data);
                    //             if (!response.data.data.parentNo) {
                    //                 vm.provinces = response.data.data;
                    //             } else if (response.data.data.parentNo === type) {
                    //                 vm.citys = response.data.data;
                    //             }
                    //         }
                    //     }, function errorCallback(response) {
                    //     });
                    // };
                    var address=false;
                    vm.getAddress = function (type,level) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/common/queryAdc",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                adc: type,
                            },
                        }).then(function successCallback(response) {
                           // console.log('111111111', level,response);
                            if (response.status === 200) {
                                if (level===undefined) {
                                    vm.provinces = response.data.data;
                                } else if (level=== 1) {
                                    vm.citys = response.data.data;
                                    address=true;
                                }else if(level===2){
                                    vm.countys=response.data.data;
                                    address=true;
                                }
                            }
                        }, function errorCallback(response) {
                        });
                    };
                    // vm.getCountrys = function (type) {
                    //     $http({
                    //         method: "GET",
                    //         url: conf.apiPath + "/addr/queryAdc",
                    //         headers: {
                    //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //             "brandApp-Id": $scope.brandAppId
                    //         },
                    //         params: {
                    //             adc: type,
                    //         },
                    //     }).then(function successCallback(response) {
                    //         console.log('111111111', response);
                    //         if (response.status === 200) {
                    //             $scope.countrys = [];
                    //             vm.countys = response.data.data;
                    //         }
                    //     }, function errorCallback(response) {
                    //     });
                    // };
                       vm.getAddress();
                      // vm.getAddress(vm.provinceNo,1);
                      // vm.getAddress(vm.cityNo,2);

                    vm.save = function () {

                        //判断最后的地址;
                        var addr=null;
                        if(!address){
                            addr=vm.countyNo?vm.countyNo:vm.cityNo;
                        }else{
                            if(vm.countys.list.length>0){
                                addr =vm.countyNo;
                               // console.log(1111,addr);
                            }else if(vm.citys.list.length>0){
                                addr=vm.cityNo;
                                //console.log(2222,addr);
                            }else{
                                addr=vm.provinceNo;
                                //console.log(3333,addr);
                            };
                        };
                        console.log('hns',addr);
                        if (!vm.phone) {
                            return errorService.error("请输入手机号！", null)
                        }else{
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/order/"+$scope.id+"/updateAddress",
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                                data: {
                                    orderId: vm.orderId,
                                    receiver: vm.receiver,
                                    phone: vm.phone,
                                    countyNo: addr,
                                    street: vm.street,
                                },
                            }).then(function successCallback(response) {
                                if (response.status == 200) {
                                    $mdDialog.cancel();
                                    $scope.getInfo();
                                }
                            }, function errorCallback(response) {
                                //console.log("---------------------error aaa");
                            });
                        }
                    };

                    vm.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vm"
            }).then(function (answer) {
            }, function () {
            });
        };

        // 返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope', '$http', 'alertService', 'loginService', 'authService', 'errorService', '$filter','$state', '$stateParams', '$mdDialog'
];

export default Controller ;




