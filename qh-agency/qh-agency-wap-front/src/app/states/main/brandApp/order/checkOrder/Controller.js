import conf from "../../../../../conf";

var $scope,
    $http,
    $timeout,
    $httpParamSerializer,
    $stateParams,
    $mdDialog,
    alertService,
    loginService,
    $interval,
    $filter,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$timeout,
                _$httpParamSerializer,
                _$stateParams,
                _$mdDialog,
                _alertService,
                _loginService,
                _$interval,
                _$filter,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        $timeout = _$timeout;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        loginService = _loginService;
        $interval = _$interval;
        $filter = _$filter;
        alertService = _alertService;
        $location = _$location;
        $httpParamSerializer = _$httpParamSerializer;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/order/checkOrder";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.orderId = $stateParams.orderId;
        $scope.checkOrder = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/detail",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.info = resp.data.data;
                console.log(4444444444444, $scope.info);
            }, function (resp) {
            });
        };
        $scope.checkOrder();
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                //history.back();
                $state.go('main.brandApp.purchase', null, {reload: true})
            }
        };
        //用余额的接口
        $scope.noCash = false;
        $scope.balanceCount = false;
        $scope.getCount = function (status) {
            $http({
                method: 'PUT',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123456789/order/" + $scope.orderId + "/calculatePrice",
                params: {
                    type: status
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('ceshi count', resp);
                $scope.checkOrder();
            }, function (resp) {
                //error
                // alertService.msgAlert('exclamation-circle', resp.data.message)
            });
        };
        $scope.noAddress = function (id) {
            $mdDialog.show({
                templateUrl: 'noAddress.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.id = id;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.brandApp.addAddress', {orderId: id}, {reload: true})
                        })
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        $scope.checkAddress = function (id) {
            $mdDialog.show({
                templateUrl: 'checkAddress.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                locals: {key: $scope.info},
                controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
                    var vmd = this;
                    vmd.info = locals.key;
                    vmd.id = id;
                    vmd.orderId = locals.key.id;
                    vmd.cancel1 = function () {
                        $mdDialog.hide();
                    };
                    // vmd.changeAddress = function () {
                    //     console.log(3333333);
                    //     $mdDialog.cancel().then(function () {
                    //         $state.go('main.brandApp.address', {orderId: id}, {reload: true})
                    //     })
                    // };
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.brandApp.address', {orderId: id}, {reload: true})
                        })
                    };
                    // vmd.goAddress1 = function () {
                    //     console.log(3333333);
                    //     $mdDialog.cancel().then(function () {
                    //         $state.go('main.brandApp.address', {orderId: id}, {reload: true})
                    //     })
                    // }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/create",
                    data: {
                        from: $stateParams.from,
                        memo: $scope.memo
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    //  console.log('payCount123',resp );
                    if ($filter('number')($scope.info.paymentPrice / 100, 2).replace(/,/g, "") == 0.00) {
                        $state.go('main.brandApp.wallet.payPassword', {id: $scope.orderId}, {reload: true});
                    } else {
                        $scope.pay($scope.orderId);
                    }

                }, function (resp) {
                    // alertService.msgAlert('exclamation-circle', resp.data.message)
                });
            }, function () {
            });
        };


        //针对改版
        $scope.checkCode1 = function (id) {
            //直接create    提交订单    然后去判断
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/create",
                data: {
                    from: $stateParams.from,
                    memo: $scope.memo
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                //  console.log('payCount123',resp );
                if ($filter('number')($scope.info.paymentPrice / 100, 2).replace(/,/g, "") == 0.00) {
                    // $state.go('main.brandApp.wallet.payPassword', {id: $scope.orderId}, {reload: true});
                    //之前跳转支付密码     现在调整为出现短信验证码弹窗
                    $mdDialog.show({
                        templateUrl: 'messageCode.html',
                        parent: angular.element(document.body).find('#qh-agency-admin-front'),
                        clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                        fullscreen: false,
                        locals: {key: $scope.info},
                        controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
                            var vmd = this;
                            vmd.info = locals.key;
                            vmd.id = id;
                            vmd.orderId = locals.key.id;
                            vmd.codeShow = false;
                            //取消
                            vmd.cance = function () {
                                $mdDialog.cancel();
                            };

                            vmd.gitCodeTime = '获取验证码';
                            vmd.getCodeCon = '余额支付需要手机短信验证';
                            vmd.lastTime = 0;
                            //获取短信验证码
                            vmd.getVCcode = function () {
                                if (vmd.lastTime <= 0) {
                                    // //手机号验证
                                    // if (!(/^1[34578]\d{9}$/.test($scope.checkPhone))) {
                                    //     alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                                    //     return false;
                                    // }
                                    //倒计时
                                    vmd.lastTime = 60;
                                    $http({
                                        method: "GET",      ///brandApp/{brandAppId}/partner/{partnerId}/partnerAccount/sendSms  GET    发送验证码
                                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123/partnerAccount/" + $scope.orderId + "/sendSms",
                                        params: {},
                                        headers: {
                                            'Authorization': 'Bearer ' + loginService.getAccessToken()
                                        }
                                    }).then(function (resp) {
                                            //设置下一步点击
                                            console.log(1, resp);


                                            if (resp.status == '10086') {
                                                // alert();
                                                vmd.getCodeCon = "短信发送过于频繁，请稍后重试!";
                                                return false;
                                            }
                                            console.log(111111);
                                            var str = resp.data.data;
                                            //$scope.first=false;
                                            // alertService.msgAlert("success", "已发送");
                                            vmd.getCodeCon = '已将短信发送至尾号' + str.substr(str.length - 4);
                                            vmd.gitCodeTime = vmd.lastTime + 's';
                                            var gitCode = $interval(function () {
                                                vmd.lastTime--;
                                                vmd.gitCodeTime = vmd.lastTime + 's';
                                                if (vmd.lastTime <= 0) {
                                                    vmd.gitCodeTime = '重新获取';
                                                    $interval.cancel(gitCode);
                                                }
                                            }, 1000)
                                        }, function () {
                                            //error
                                            //alertService.msgAlert("exclamation-circle", "发送失败请重试!");
                                            vmd.lastTime = 0;
                                        }
                                    );
                                }
                            };
                            vmd.getCodeShow = function () {
                                vmd.codeShow = false;
                            };


                            //确认
                            vmd.confirm = function () {
                                //点击确认后调用短信验证码    核对接口
                                if (!vmd.code) {
                                    vmd.codeShow = true;
                                    return false;
                                }
                                if (vmd.code.length != 6) {
                                    vmd.codeShow = true;
                                    return false;
                                }
                                // $mdDialog.hide();
                                $http({
                                    method: "GET",
                                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123/partnerAccount/" + $scope.orderId + "/checkSms",
                                    params: {
                                        code: vmd.code
                                    },
                                    headers: {
                                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                                    }
                                }).then(function (resp) {
                                        //设置下一步点击
                                        console.log(resp);
                                        if (resp.data.status == '10080') {
                                            //alertService.msgAlert("exclamation-circle", "验证码错误!");
                                            vmd.codeShow = true;
                                            return false;
                                        } else if (resp.data.status == '200') {
                                            // alertService.msgAlert("exclamation-circle", "验证码成功!");
                                            $mdDialog.cancel();
                                            $state.go("main.brandApp.unionOrder", null, {reload: true})
                                            return false;
                                        }
                                    }, function () {
                                        //error
                                        //alertService.msgAlert("exclamation-circle", "发送失败请重试!");

                                    }
                                );


                            };
                        }],
                        controllerAs: "vmd"
                    }).then(function (answer) {


                    }, function () {
                        //error
                    });
                } else {
                    //走正常支付
                    $scope.pay($scope.orderId);
                }

            }, function (resp) {
                // alertService.msgAlert('exclamation-circle', resp.data.message)
            });
        };

        //针对改版
        $scope.checkCode = function (id) {
            //直接create    提交订单    然后去判断
            if ($filter('number')($scope.info.paymentPrice / 100, 2).replace(/,/g, "") == 0.00) {
                // $state.go('main.brandApp.wallet.payPassword', {id: $scope.orderId}, {reload: true});
                //之前跳转支付密码     现在调整为出现短信验证码弹窗
                $mdDialog.show({
                    templateUrl: 'messageCode.html',
                    parent: angular.element(document.body).find('#qh-agency-admin-front'),
                    clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                    fullscreen: false,
                    locals: {key: $scope.info},
                    controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
                        var vmd = this;
                        vmd.info = locals.key;
                        vmd.id = id;
                        vmd.orderId = locals.key.id;
                        vmd.codeShow = false;
                        //取消
                        vmd.cance = function () {
                            $mdDialog.cancel();
                        };

                        vmd.gitCodeTime = '获取验证码';
                        vmd.getCodeCon = '余额支付需要手机短信验证';
                        vmd.lastTime = 0;
                        //获取短信验证码
                        vmd.getVCcode = function () {
                            if (vmd.lastTime <= 0) {
                                // //手机号验证
                                // if (!(/^1[34578]\d{9}$/.test($scope.checkPhone))) {
                                //     alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                                //     return false;
                                // }
                                //倒计时
                                vmd.lastTime = 60;
                                $http({
                                    method: "GET",      ///brandApp/{brandAppId}/partner/{partnerId}/partnerAccount/sendSms  GET    发送验证码
                                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123/partnerAccount/" + $scope.orderId + "/sendSms",
                                    params: {},
                                    headers: {
                                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                                    }
                                }).then(function (resp) {
                                        //设置下一步点击
                                        console.log(111111, resp);
                                        if (resp.data.status == '10086') {
                                            //alert();
                                            // alertService.msgAlert("exclamation-circle", "短信发送过于频繁，请稍后重试!");
                                            vmd.getCodeCon = "短信发送过于频繁";
                                            return false;
                                        } else {
                                            var str = resp.data.data;
                                            //$scope.first=false;
                                            // alertService.msgAlert("success", "已发送");
                                            vmd.getCodeCon = '已将短信发送至尾号' + str.substr(str.length - 4);
                                            vmd.gitCodeTime = vmd.lastTime + 's';
                                            var gitCode = $interval(function () {
                                                vmd.lastTime--;
                                                vmd.gitCodeTime = vmd.lastTime + 's';
                                                if (vmd.lastTime <= 0) {
                                                    vmd.gitCodeTime = '重新获取';
                                                    $interval.cancel(gitCode);
                                                }
                                            }, 1000)
                                        }

                                    }, function () {
                                        //error
                                        //alertService.msgAlert("exclamation-circle", "发送失败请重试!");
                                        vmd.lastTime = 0;
                                    }
                                );
                            }
                        };
                        vmd.getCodeShow = function () {
                            vmd.codeShow = false;
                        };
                        //确认
                        vmd.confirm = function () {
                            //点击确认后调用短信验证码    核对接口
                            if (!vmd.code) {
                                vmd.codeShow = true;
                                return false;
                            }
                            else if (vmd.code.length != 6) {
                                vmd.codeShow = true;
                                return false;
                            } else {
                                vmd.codeShow = false;
                            }
                            // $mdDialog.hide();
                            $http({
                                method: "GET",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123/partnerAccount/" + $scope.orderId + "/checkSms",
                                params: {
                                    code: vmd.code
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                                }
                            }).then(function (resp) {
                                    //设置下一步点击
                                    console.log(resp);
                                    if (resp.data.status == '10080') {
                                        //alertService.msgAlert("exclamation-circle", "验证码错误!");
                                        vmd.codeShow = true;
                                        return false;
                                    } else if (resp.data.status == '200') {
                                        // alertService.msgAlert("exclamation-circle", "验证码成功!");
                                        //验证码通过生成订单    生成订单成功后再到采购管理看其状态
                                        vmd.codeShow = false;
                                        $http({
                                            method: "PUT",
                                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/create",
                                            data: {
                                                from: $stateParams.from,
                                                memo: $scope.memo
                                            },
                                            headers: {
                                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                                "brandApp-Id": $scope.brandAppId
                                            }
                                        }).then(function (resp) {
                                            console.log('短信支付生成订单状态', resp);
                                            $mdDialog.cancel();
                                            $state.go("main.brandApp.unionOrder", null, {reload: true})
                                            return false;
                                        });
                                    }
                                }, function () {
                                    //error
                                    //alertService.msgAlert("exclamation-circle", "发送失败请重试!");

                                }
                            );


                        };
                    }],
                    controllerAs: "vmd"
                }).then(function (answer) {


                }, function () {
                    //error
                });
            } else {
                //走正常支付
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/create",
                    data: {
                        from: $stateParams.from,
                        memo: $scope.memo
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    console.log('zhifubao生成订单状态', resp);
                    $scope.pay($scope.orderId);
                });

            }
        };


        /*
         * 提交订单
         * */
        $scope.createOrder = function () {
            //$scope.pay($scope.orderId);       //测试支付，可把该行注释打开，下面请求注释，以免提交订单失败
            if (!$scope.info.address) {
                $scope.noAddress($scope.info.id);
                return;
            }
            //$scope.checkAddress($scope.info.id);
            //测试短信验证码
            $scope.checkCode($scope.info.id);

        };

        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {

                // 跳转到支付页
                /////backurl 转到我的订单页面unionOrder
                let back = 'brandApp/' + $scope.brandAppId + '/unionOrder';
                location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$scope.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
            }, function (resp) {
                //TODO 失败页
            });
        }
    }


}

Controller.$inject = [
    '$scope',
    '$http',
    '$timeout',
    '$httpParamSerializer',
    '$stateParams',
    '$mdDialog',
    'alertService',
    'loginService',
    '$interval',
    '$filter',
    '$state',
    '$location',
];

export default Controller ;
