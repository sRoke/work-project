import conf from "../../../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $mdDialog,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$mdDialog,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true);


        // $scope.popularityList = function (id) {
        //     $mdDialog.show({
        //         templateUrl: 'changePrice.html',
        //         parent: angular.element(document.body),
        //         clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
        //         fullscreen: false,
        //         hasBackdrop: false,
        //         controller: ['$http', '$stateParams', '$mdDialog', function ($http, $stateParams, $mdDialog) {
        //             var vmd = this;
        //             vmd.id = id;
        //             vmd.cancel = function () {
        //                 return $mdDialog.cancel();
        //             };
        //         }],
        //         controllerAs: "vmd"
        //     }).then(function (answer) {
        //     }, function () {
        //     });
        // };
        // $scope.popularityList('1231');


        $scope.getOrderDetail = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/' + $stateParams.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.orderDetail = resp.data.data;
                // console.log($scope.orderDetail)
                if ($scope.orderDetail.orderType == 'SINCE') {
                    var times = new Date($scope.orderDetail.sinceTakeTime);
                    $scope.sinceTimeYear = times.getFullYear();
                    $scope.sinceTimeMonth = times.getMonth();
                    $scope.sinceTimeDate = times.getDate();
                }

            }, function (resp) {
                //TODO 错误处理
            });

        }

        $scope.getOrderDetail();

        //改价弹窗
        $scope.changePrice = function (item) {
            $mdDialog.show({
                templateUrl: 'changePrice.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                hasBackdrop: false,
                controller: ['$http', '$stateParams', '$mdDialog', 'alertService', function ($http, $stateParams, $mdDialog, alertService) {
                    var vmd = this;
                    vmd.item = item;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };

                    vmd.changePrice = function () {

                        if (vmd.price <= 0) {
                            alertService.msgAlert("exclamation-circle", '金额必须大于0');
                            return;
                        }

                        $http({
                            method: "put",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/' + $stateParams.id + '/sku/' + vmd.item.skuId + '/adjustPrice',
                            params: {
                                adjustPrice: vmd.price * 100,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log(resp);
                            return $mdDialog.hide(true);
                        }, function (resp) {

                            alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    };


                    //-----------------------------------实时监听控制输入内容
                    vmd.oldPrice = vmd.item.realPayPrice * vmd.item.num / 100;
                    vmd.price = vmd.item.realPayPrice * vmd.item.num / 100;
                    vmd.AddEventInput = function () {
                        // console.log('vmd.price=====', vmd.price)
                        // console.log('typeofvmd.price=====', typeof vmd.price)
                        var reg = /(^[1-9]{1}[0-9]*$)|(^[0-9]+\.[0-9]{0,2}$)|(^[0]$)/;
                        if (!reg.test(vmd.price)) {

                            // if( vmd.price === undefined){
                            //     console.log('222222222222222')
                            //     console.log('vmd.oldPrice==',vmd.oldPrice)
                            //     vmd.price = vmd.oldPrice;
                            // }else if(vmd.price === null){
                            //     console.log('11111111111111')
                            //     vmd.oldPrice = '';
                            // }

                            if (vmd.price === null) {
                                // console.log('11111111111111')
                                vmd.oldPrice = null;
                            } else {
                                // console.log('222222222222222')
                                // console.log('vmd.oldPrice==', vmd.oldPrice)
                                vmd.price = vmd.oldPrice;
                            }

                            // else {
                            //     console.log('222222222222222')
                            //     vmd.price = vmd.oldPrice;
                            // }
                            // console.log("请输入大于0的整数或者保留")
                        } else {
                            if (vmd.price * 100 > vmd.item.skuPrice * vmd.item.num) {
                                vmd.price = vmd.item.skuPrice * vmd.item.num / 100;
                            }
                            vmd.oldPrice = vmd.price;
                            // console.log("输入正确");
                        }
                        ;
                    }


                }],
                controllerAs: "vmd"
            }).then(function (answer) {
                // console.log('answer',answer)
                if (answer) {
                    $scope.getOrderDetail();
                }
            }, function (error) {
                // console.log('error',error)
            });
        };


        //确认拒绝接单
        $scope.confirmOrder = function (status) {
            if (status) {
                alertService.confirm(null, '确认接单?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/' + $stateParams.id + '/confirmOrder',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log('resp-------------', resp);
                            $scope.getOrderDetail();
                        }, function (resp) {
                            //TODO 错误处理
                            if (resp.data.status == 10026) {
                                alertService.confirm(null, '请先绑定收款支付宝帐号', '温馨提示', null, '去绑定').then(function (data) {
                                    if (data) {
                                        $state.go("main.brandApp.store.shop.setPay", {form: 'main.brandApp.store.order.orderDetail'}, {reload: true});
                                    }
                                });
                            } else {
                                alertService.msgAlert("exclamation-circle", resp.data.message);
                            }
                        });
                    }
                });
            } else {

                alertService.confirm(null, '拒绝接单?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/' + $stateParams.id + '/rejectOrder',
                            params: {
                                memo: null,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log('resp-------------', resp);
                            $scope.getOrderDetail();
                        }, function (resp) {
                            //TODO 错误处理
                            alertService.msgAlert("exclamation-circle", resp.data.message);
                        });
                    }
                });
                // $http({
                //     method: "PUT",
                //     url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/'+$stateParams.id + '/rejectOrder',
                //     params: {
                //         memo:null,
                //     },
                //     headers: {
                //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                //         "brandApp-Id": $scope.brandAppId
                //     }
                // }).then(function (resp) {
                //     console.log('resp-------------',resp);
                //     $scope.fallbackPage();
                // }, function (resp) {
                //     //TODO 错误处理
                // });
            }
        };

        //deliver 确认发货
        $scope.deliver = function () {

            if ($scope.orderDetail.logisticses.length <= 0) {
                return alertService.msgAlert("exclamation-circle", "请选择快递公司");
            }
            alertService.confirm(null, '确认发货?', '温馨提示').then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/deliverInvoice/' + $stateParams.id + '/ship',
                        data: {},
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp-------------', resp);
                        $scope.fallbackPage();
                    }, function (resp) {
                        //TODO 错误处理
                    });
                }
            });
        };


        // $scope.pageTitle = '订单详情';
        /*返回上级*/
        $scope.fallbackPage = function () {
            // console.log(history);
            if (history.length == 1) {
                $state.go("main.brandApp.store.order.orderCenter", null, {reload: true});
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
    '$mdDialog',
    '$state'
];

export default Controller ;
