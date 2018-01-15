angular.module('qh-assistance-wap-front').factory('payService', ['appConfig', '$http', '$q', 'alertService', '$timeout', '$httpParamSerializer', "$state", '$mdDialog', '$rootScope', '$mdBottomSheet', '$interval', function (appConfig, $http, $q, alertService, $timeout, $httpParamSerializer, $state, $mdDialog, $rootScope, $mdBottomSheet, $interval) {
    var fallbackUrlALl = null;
    var paramsALL = null;
    /*抢单弹框*/
    // @param payType ;支付的类型,APP使用app支付,SCAN扫码支付,WAP网关支付(支付宝独有),qhPub公众号支付(微信独有);;默认SCAN
    function pay(qhPay, type, fallbackUrl, params) {
        fallbackUrlALl = fallbackUrl;
        paramsALL = params;
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        if (type === 'BALANCE') {
            payPassword().then(function (pwd) {
                $http({
                    method: 'POST',
                    url: appConfig.apiPath + '/payment/pay',
                    data: $httpParamSerializer({
                        out_trade_no: qhPay,
                        password: pwd
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                }).then(function (resp) {
                    var data = resp.data;
                    if (data.code === "SUCCESS" || data.code === "AMOUNT_ZERO") {
                        alertService.msgAlert("success", "支付成功");
                        $timeout(function () {
                            fallbackPage();
                        }, 1000);
                    } else if (data.code === "BALANCE") {
                        alertService.confirm(null, "", "余额不足,请充值!", "取消", "去充值").then(function (data) {
                            if (data) {
                                $state.go("main.wallet.recharge");
                            }
                        });
                    } else {
                        alertService.msgAlert("ks-cancle", data.msg);
                        deferred.reject(false);
                    }
                }, function (resp) {
                    var data = resp.data;
                    if (data.code === "NOT_LOGINED") {
                        $state.go("main.newLogin", {backUrl: window.location.href});
                    } else if (data.code === "AMOUNT_ZERO") {
                        $timeout(function () {
                            fallbackPage();
                        }, 500);
                    }
                });
            });
        } else if (type === 'ALIPAY') {
            if (window.cordova && window.alipay) {
                // 支付宝支付
                // 服务订单使用其他url
                var url = null;
                url = "/payment/aliPay?type=APP&out_trade_no=" + qhPay;
                $http({
                    method: 'POST',
                    url: appConfig.apiPath + '/payment/aliPay',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    },
                    data: $httpParamSerializer({
                        type: 'APP',
                        out_trade_no: qhPay,
                        version: 2
                    })
                }).then(function (resp) {
                    window.alipay.pay(resp.data, function () {
                        alertService.msgAlert("success", "支付成功");
                        $timeout(function () {
                            fallbackPage();
                        }, 1000);
                    }, function () {
                        fallbackPage();
                    });
                }, function (resp) {
                    var data = resp.data;
                    if (data.code === "NOT_LOGINED") {
                        $state.go("main.newLogin", {backUrl: window.location.href});
                    } else if (data.code === "AMOUNT_ZERO") {
                        $timeout(function () {
                            fallbackPage();
                        }, 500);
                    }
                });
            } else {
                // 支付宝支付
                // 服务订单使用其他url
                var url = "/payment/aliPay?version=2&out_trade_no=" + qhPay;
                $http({
                    method: 'POST',
                    url: appConfig.apiPath + url,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                }).then(function (resp) {
                    var data = resp.data;
                    var urldecode = decodeURIComponent(data.uri);
                    window.location.href = urldecode;
                }, function (resp) {
                    var data = resp.data;
                    if (data.code === "NOT_LOGINED") {
                        $state.go("main.newLogin", {backUrl: window.location.href});
                    } else if (data.code === "AMOUNT_ZERO") {
                        $timeout(function () {
                            fallbackPage();
                        }, 500);
                    }
                });
            }
        } else if (type === 'WEIXIN') {
            // 微信支付是否在微信内的浏览器打开，在其他浏览器暂时不给显示
            var payWXWay = false;
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
                // 处于微信浏览器，打开微信支付
                payWXWay = true;
            }
            if (window.cordova && window.Wechat) {
                window.Wechat.isInstalled(function (installed) {
                    if (!installed) {
                        alertService.msgAlert("cancle", "您尚未安装微信!");
                        return;
                    }
                    $http({
                        method: 'POST',
                        url: appConfig.apiPath + '/weiXin/buy',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                        },
                        data: $httpParamSerializer({
                            orderId: qhPay,
                            wxType: 'qhApp'
                        })
                    }).then(function (resp) {
                        var data = resp.data;
                        var payParams = data.payParams;
                        Wechat.sendPaymentRequest(payParams, function () {
                            alertService.msgAlert("success", "支付成功");
                            $timeout(function () {
                                fallbackPage();
                            }, 1000);
                        }, function () {
                            fallbackPage();
                        });
                    }, function (data) {
                        data = data.data;
                        if (data.code === 'NOT_WEIXIN') {
                            var scope = "snsapi_userinfo";
                            window.Wechat.isInstalled(function () {
                                // 获取登录用的 state
                                $http({
                                    method: "POST",
                                    url: appConfig.apiPath + '/weiXin/genLoginState',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                                    }
                                }).success(function (genState) {
                                    // 调用微信 APP 进行登录
                                    window.Wechat.auth(scope, genState.state, function (response) {
                                        // you may use response.code to get the access token.
                                        // 获取该商品的属性
                                        $http({
                                            method: 'GET',
                                            url: appConfig.apiPath + "/weiXin/wxLoginVerify?code=" + response.code + "&state=" + response.state + "&wxType=qhApp"
                                        }).then(function () {
                                            fallbackPage();
                                        }, function (resp) {
                                            var data = resp.data;
                                            if (data.code === "NOT_WEIXIN") {
                                                $state.go("main.newLogin", {backUrl: $state.params.backUrl}, null);
                                            }
                                        });
                                    }, function () {
                                    });
                                }).error(function () {
                                });

                            }, function () {
                                alertService.msgAlert("cancle", "您尚未安装微信");
                            });
                        } else if (data.code === "AMOUNT_ZERO") {
                            $timeout(function () {
                                fallbackPage();
                            }, 500);
                        }
                    });
                }, function () {
                    alertService.msgAlert("cancle", "您尚未安装微信!");
                });
            } else if (payWXWay) {
                // 微信内支付
                $http({
                    method: 'POST',
                    url: appConfig.apiPath + '/weiXin/buy',
                    data: $httpParamSerializer({
                        orderId: qhPay
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                }).then(function (resp) {
                    var data = resp.data;

                    var payParams = data.payParams;
                    payParams.success = function () {
                        fallbackPage();
                    };
                    payParams.fail = function () {
                        fallbackPage();
                    };
                    payParams.cancel = function () {
                        fallbackPage();
                    };
                    wx.chooseWXPay(payParams);
                }, function (resp) {
                    var data = resp.data;
                    if (data.code === 'NOT_WEIXIN') {
                        var url = encodeURIComponent(location.href);
                        $http({
                            method: 'GET',
                            url: appConfig.apiPath + '/weiXin/wxOauthLogin?backUrl=' + url
                        }).then(function (resp) {
                            var data = resp.data;
                            window.location.href = data.uri;
                        }, function () {

                        });
                    } else if (data.code === "AMOUNT_ZERO") {
                        $timeout(function () {
                            fallbackPage();
                        }, 500);
                    }
                });
            } else {
                // 扫码支付
                wxScanPay(qhPay);
            }
        } else if (type === 'ALICODE') {
            var url = "/payment/aliPay?version=2&type=SCAN&out_trade_no=" + qhPay;
            $http({
                method: 'POST',
                url: appConfig.apiPath + url,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                }
            }).then(function (resp) {
                var data = resp.data;
                var datas = {code_url: data.scan.qr_code, id: qhPay};
                // 扫码支付
                scanFindBuy(datas, "ALIPAY");
            }, function () {
                deferred.reject("error");
            });
        } else if (type === 'WXCODE') {
            wxScanPay(qhPay);
        }
        return deferred.promise;
    };
    function payPassword() {
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        $mdBottomSheet.show({
            templateUrl: 'views/main/unionOrder/pay/payPasswordDialog.html',
            controllerAs: "vm",
            controller: ["$httpParamSerializer", "FileUploader", function ($httpParamSerializer, FileUploader) {
                var vm = this;
                vm.cancel = function () {
                    $mdBottomSheet.hide(false);
                };
                //设置密码存储
                vm.pwds = [];
                vm.pwdsString = vm.pwds.join(".");
                //设置密码显示方式
                vm.dots = [false, false, false, false, false, false];
                //点击数字
                vm.num = function (num) {
                    if (vm.pwds.length < 6) {
                        vm.pwds.push(num);
                    }
                    var pwd = "";
                    for (var i = 0; i < vm.pwds.length; i++) {
                        vm.dots[i] = true;
                        pwd += vm.pwds[i];
                    }
                    if (vm.pwds.length === 6) {
                        //向后台发请求
                        vm.submit(pwd);
                    }
                };
                /**
                 *  跳转
                 */
                vm.uisref = function () {
                    $state.go("main.user.setPay", {status: 0});
                }
                //删除密码
                vm.deletePwds = function () {
                    vm.pwds.pop();
                    vm.dots = [false, false, false, false, false, false];
                    for (var i = 0; i < vm.pwds.length; i++) {
                        vm.dots[i] = true;
                    }
                };

                //进行支付
                vm.submit = function (pwd) {
                    $mdBottomSheet.hide(pwd);
                };

                vm.updateAvatar = function () {
                    angular.element("#uploaderFile").click();
                };
                var uploader = vm.uploader = new FileUploader({
                    url: appConfig.apiPath + '/common/uploadImgS',
                    autoUpload: true
                });
                // FILTERS
                uploader.filters.push({
                    name: 'customFilter',
                    fn: function () {
                        return this.queue.length < 30;
                    }
                });

                uploader.onSuccessItem = function (fileItem, response) {
                    $http({
                        method: "POST",
                        url: appConfig.apiPath + '/user/updateUserInfo',
                        data: $httpParamSerializer({yunFileId: response.id}),
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                        }
                    }).then(function () {
                        $mdBottomSheet.hide(response);
                    }, function (resp) {
                        var data = resp.data;
                        if (data.code === "NOT_LOGINED") {
                            $state.go("main.newLogin", {backUrl: window.location.href});
                        }
                    });
                };
            }],
            parent: '.ks-main'
        }).then(function (response) {
            if (response) {
                deferred.resolve(response);
            } else {
                fallbackPage();
            }
        });
        return deferred.promise;
    }

    /**
     * 扫码支付，同事判断是否支付成功
     * @param data
     */
    function scanFindBuy(data, type) {
        $mdDialog.show({
            templateUrl: 'views/main/unionOrder/pay/dialog/index.root.html',
            parent: angular.element(document.body).find('#qh-wap'),
            targetEvent: null,
            clickOutsideToClose: true,
            fullscreen: false,
            controller: [function () {
                var vm = this;
                vm.cancel = function () {
                    $mdDialog.cancel();
                };
                vm.payUrl = data;
                // 不同支付，展示不同文字
                if (type === 'ALIPAY') {
                    vm.payUrl.msg = "用支付宝扫一扫，才能完成支付";
                } else {
                    vm.payUrl.msg = "用微信扫一扫，才能完成支付";
                }
                $rootScope.intervalStop = $interval(function () {
                    $http({
                        method: 'GET',
                        url: appConfig.apiPath + '/payment/findPay?id=' + vm.payUrl.id
                    }).then(function (resp) {
                        var data = resp.data;
                        if (data.pay) {
                            $timeout(function () {
                                $mdDialog.hide(true);
                            }, 1000);
                            $interval.cancel($rootScope.intervalStop);
                            $rootScope.intervalStop = undefined;
                        }
                    }, function () {

                    });
                }, 1500);
            }],
            controllerAs: "vm"
        }).then(function (answer) {
            if (answer) {
                if ($rootScope.intervalStop) {
                    $interval.cancel($rootScope.intervalStop);
                    $rootScope.intervalStop = undefined;
                }
            }
            fallbackPage();
        }, function () {
            if ($rootScope.intervalStop) {
                $interval.cancel($rootScope.intervalStop);
                $rootScope.intervalStop = undefined;
            }
            fallbackPage();
        });
    }

    /**
     * 回退
     * @param url
     * @param params
     */
    function fallbackPage() {
        var param = angular.fromJson(paramsALL);
        if (fallbackUrlALl) {
            $state.go(fallbackUrlALl, param, {reload: true});
        } else if (history.length === 1) {
            $state.go("main.index", null, {reload: true});
        } else {
            history.back();
        }
    }

    /**
     * 修改微信支付的二维码
     * @param qhPay
     */
    function wxScanPay(qhPay) {
        $http({
            method: "POST",
            url: appConfig.apiPath + '/weiXin/buy',
            data: $httpParamSerializer({
                orderId: qhPay,
                wxType: "qhPubScan"
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            notShowError: true
        }).success(function (resp) {
            if (resp.code === "AMOUNT_ZERO") {
                $timeout(function () {
                    fallbackPage();
                }, 500);
                return;
            }
            var datas = {
                id: qhPay,
                code_url: resp.code_url
            }
            // 扫码支付
            scanFindBuy(datas, "WEIXIN");
        }).error(function (data) {
            if (data.code === 'NOT_WEIXIN') {
                var url = encodeURIComponent(location.href);
                var ua = window.navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i)) {
                    alertService.msgAlert("exclamation-circle", "该订单已经支付完成,返回商品订单中查看");
                    $http({
                        method: 'POST',
                        url: appConfig.apiPath + '/weiXin/wxOauthLogin',
                        data: $httpParamSerializer({
                            backUrl: url
                        }),
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                        }
                    }).then(function (resp) {
                        var data = resp.data;
                        window.location.href = data.uri;
                    }, function () {

                    });
                } else {
                    $http({
                        method: 'POST',
                        url: appConfig.apiPath + '/weiXin/wxWebLogin',
                        data: $httpParamSerializer({
                            backUrl: url
                        }),
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                        }
                    }).then(function (resp) {
                        var data = resp.data;
                        window.location.href = data.uri;
                    }, function () {

                    });
                }
            } else if (data.code === "AMOUNT_ZERO") {
                $timeout(function () {
                    fallbackPage();
                }, 500);
            }
        });
    }

    /**
     * 扫码支付,返回二维码链接
     * @param payType;
     */
    function scanPay(payType, qhPay) {
        var url = "/payment/aliPay?version=2&type=SCAN&out_trade_no=" + qhPay;
        if (payType === 'WEIXIN') {
            url = "/weiXin/buy?wxType=qhPubScan&qhPayId=" + qhPay;
        }
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        $http({
            method: 'POST',
            url: appConfig.apiPath + url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            }
        }).then(function (resp) {
            var data = resp.data;
            if (payType === 'WEIXIN') {
                deferred.resolve(data.code_url);
            } else {
                deferred.resolve(data.scan.qr_code);
            }
        }, function () {
            deferred.reject("error");
        });
        return deferred.promise;
    }

    return {
        pay: pay,
        payPassword: payPassword,
        scanPay: scanPay,
        WEIXIN: {
            name: '微信支付',
            payType: "WEIXIN",
            icon: 'ks-weixin'
        },
        ALIPAY: {
            name: '支付宝支付',
            payType: "ALIPAY",
            icon: 'ks-alipay'
        }, BALANCE: {
            name: '余额支付',
            payType: "BALANCE",
            icon: 'ks-rmb-symbol'
        }, JF_PAY: {
            name: '积分支付',
            payType: "JF_PAY",
            icon: 'ks-sign-circular'
        }, WXCODE: {
            name: '生成微信支付码',
            payType: "WXCODE",
            icon: "ks-qrcode"
        }, ALICODE: {
            name: '生成支付宝支付码',
            payType: "ALICODE",
            icon: "ks-qrcode"
        }, RMB: {
            name: '现金支付',
            payType: "RMB",
            icon: 'ks-alipay'
        }
    };
}]);