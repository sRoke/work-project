// 构建配置的数据
(function () {
    var jso = new JSO({
        providerID: 'oauth2-authorization-server-lottery-wap',
        client_id: 'CLIENT_ID_kingsilk_qh-lottery-wap-front',
        redirect_uri: location.href,
        authorization: appConfig.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
        scopes: {request: ['LOGIN']},
        debug: true
    });
    //登录类型，参数
    var loginType = {
        WX: "loginType=WX&autoCreateUser",
        WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
        WX_QYH: "loginType=WX_QYH&autoCreateUser",
        WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
        PASSWORD: "loginType=PASSWORD"
    };
    // 检查是否是从 认证服务器回来的。
    jso.callback(location.href, function (at) {
        window.at = at;
    }, "oauth2-authorization-server-lottery-wap");

    // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
    jso.on('redirect', function (url) {
        location.href = url;
    });
    //登录
    function goOAuth() {

        // alert(2);
        var ua = window.navigator.userAgent.toLowerCase();
        var type = "WX_SCAN";
        if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
            type = "WX";
        }
        //修改登录方式
        jso.config.config.authorization = appConfig.wxloginPath + loginType[type];
        jso.getToken(function (token) {
            window.at = token;
            store.set(appConfig.token, token.access_token);
        }, {})
    }

    //自调用登录
    goOAuth();
    angular.module('qh-bargain-wap-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.bargainDetail", {
            url: "/bargainDetail/{id}/{awardId}/{userId}",
            views: {
                "@": {
                    templateUrl: 'views/main/bargainDetail/index.root.html',
                    controller: bargainDetailController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    bargainDetailController.$inject = ['$scope', '$http', 'appConfig', '$stateParams', '$mdDialog', 'wxService', '$interval', '$filter', '$rootScope', '$timeout', 'alertService', '$templateCache', '$state', '$cookies'];
    function bargainDetailController($scope, $http, appConfig, $stateParams, $mdDialog, wxService, $interval, $filter, $rootScope, $timeout, alertService, $templateCache, $state, $cookies) {
        $scope.imgUrl = appConfig.imgUrl;
        $rootScope.intervalStop = [];
        $scope.helpSuccess = false;


        $scope.showBtn = false;

        $scope.bargain = function () {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/bargain',
                params: {
                    // id: $stateParams.id,
                    awardId: $stateParams.awardId,
                    userId: $stateParams.userId,
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token)
                }
            }).then(function (resp) {
                $scope.helpPrice = resp.data.helpPrice;
                // $scope.showBargainDialog()
                $scope.helpSuccess = true;
                $scope.shareFiend();
                $scope.getInfo();

            }, function (resp) {
                $scope.helpSuccess = true;
                console.log('resp', resp);
                // $scope.isSuccess = true;
            })
        };


        $scope.getInfo = function (helpUserId) {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/getBargainResult',
                params: {
                    id: $stateParams.id,
                    awardId: $stateParams.awardId,
                    userId: $stateParams.userId,
                    helpUserId: helpUserId
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token)
                }
            }).then(function (resp) {
                // $state.go('main.bargain', {activeId: '123456'}, {reload: true})
                $scope.titleImg = resp.data.titleImg;
                $scope.sameUser = resp.data.isSameUser;
                $scope.canReceived = resp.data.canReceived;
                $scope.isSuccess = resp.data.isSuccess;
                $scope.canBargain = resp.data.canBargain;
                $scope.awardNum = resp.data.num;
                $scope.helpList = resp.data.helpList;
                $scope.bestHelperList = resp.data.bestHelperList;

                $scope.receiveType = resp.data.receiveType;

                $rootScope.skuId = resp.data.awardId;
                if (resp.data.code == 'ERROR') {
                    $state.go('main.index', {
                        id: $stateParams.id,
                        awardId: $rootScope.skuId,
                        userId: resp.data.userId,
                    }, {reload: true})
                }

                $scope.infoData = resp.data;
                $scope.infoData.activityCheck = {};
                $scope.shareInfo = resp.data.shareInfo;

                if (wxService.isInWx()) {
                    $scope.$on("$destroy", function () {
                        wxService.shareRing(); // 恢复默认绑定
                        wxService.shareFriend();
                    });
                }
                var curConf = {
                    title: $scope.shareInfo.shareTitle,
                    desc: $scope.shareInfo.shareDesp,
                    link: location.href,
                    imgUrl: 'https:' + appConfig.imgUrl + $scope.shareInfo.shareImg,
                    success: function () {
                        // 用户确认分享后执行的回调函数
                        // alertService.msgAlert('success', "分享成功");
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        // alertService.msgAlert('exclamation-circle', "取消分享");
                    }
                };
                wxService.initShareOnStart(curConf);


                var now = new Date().getTime();
                var endTime = new Date($filter("date")($scope.infoData.endTime, "yyyy/MM/dd HH:mm:ss")).getTime();
                // 倒计时到零时，停止倒计时
                var rest = endTime - now;
                if (rest <= 0 || $scope.infoData.disabled) {
                    $scope.activeFalse = false;
                }

                if (resp.data.isSameUser) {
                    $scope.bargain();
                }

                $timeout(function () {
                    $rootScope.intervalStop = $interval(function () {
                        var now = new Date().getTime();
                        var endTime = new Date($filter("date")($scope.infoData.endTime, "yyyy/MM/dd HH:mm:ss")).getTime();
                        // 倒计时到零时，停止倒计时
                        var rest = endTime - now;
                        if (rest <= 0) {

                            $scope.infoData.activityCheck = null;
                            $interval.cancel($rootScope.intervalStop);
                            $rootScope.intervalStop = null;
                            return;
                        }
                        var leftsecond = parseInt(rest / 1000);
                        var day1 = Math.floor(leftsecond / (60 * 60 * 24));
                        var hour1 = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
                        var minute1 = Math.floor((leftsecond - day1 * 24 * 60 * 60 - hour1 * 3600) / 60);
                        var second1 = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour1 * 3600 - minute1 * 60);
                        $scope.infoData.activityCheck.day = day1;
                        $scope.infoData.activityCheck.hour = hour1;
                        $scope.infoData.activityCheck.minute = minute1;
                        $scope.infoData.activityCheck.second = second1;
                    }, 1000);
                }, 30);
                $scope.showBtn = true;
                $scope.calculation();
            }, function (resp) {
                // alert(1)
            })
        };
        $scope.getInfo();

        $scope.calculation = function () {
            angular.element('.progress-speed').css("-webkit-transform", "translateX(-" +
                ((1 - (($scope.infoData.price - $scope.infoData.finalPrice) / ($scope.infoData.price - $scope.infoData.minTargetPrice)  )) * 100 ) + "%)");
        };


        //判断是否关注
        $scope.getUserInfo = function (userId, skuId) {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/bargain/judgeSubscribe',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                if (resp.data.subscribe) {
                    $scope.bargain();
                    $scope.getInfo();
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true, userId, skuId);
                }
            }, function (response) {

            });
        };

        //去砍价  //判断用户是否注册
        $scope.goBargain = function () {
            // $http({
            //     method: 'GET',
            //     url: appConfig.apiPath + '/bargain/judgeUser',
            //     headers: {
            //         'Authorization': 'Bearer ' + store.get(appConfig.token)
            //     }
            // }).then(function (resp) {
            //     $scope.userId = $stateParams.userId ? $stateParams.userId : resp.data.userId;
            //     if (resp.data.registerPhone) {
            //         $scope.getUserInfo($scope.userId, $rootScope.skuId);
            //     } else {
            //         $scope.showGameRules(false, $scope.userId, $rootScope.skuId);
            //     }
            // })
            $scope.userId = $stateParams.userId ? $stateParams.userId : resp.data.userId;
            $scope.getUserInfo($scope.userId, $rootScope.skuId);
        };


        //注册手机号弹窗
        $scope.showGameRules = function (status, userId, skuId) {
            $mdDialog.show({
                templateUrl: 'views/main/indexDialog.html',
                parent: angular.element(document.body).find('#qh-assistance-wap-front'),
                targetEvent: null,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {data: $scope.infoData},
                controller: ['$scope', '$mdDialog', 'appConfig', '$http', 'locals', 'alertService', '$timeout', function ($scope, $mdDialog, appConfig, $http, locals, alertService, $timeout) {
                    var vmd = this;
                    // vmd.userId = locals.data.userId;
                    // vmd.targetUserId = locals.data.targetUserId;
                    vmd.id = locals.data.id;
                    vmd.user = {};
                    vmd.flog = status;
                    vmd.userId = userId;
                    vmd.skuId = skuId;
                    vmd.checkSubmit = function () {
                        $mdDialog.hide(true);
                    };
                    vmd.cancel = function () {
                        $mdDialog.cancel();
                    };
                    vmd.getQrcode = function (userId, skuId) {
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/bargain/getQrCode',
                            params: {
                                'id': vmd.id,
                                'userId': userId,
                                'awardId': skuId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            vmd.qrCode = resp.data.ticket;
                            vmd.flog = true;
                        }, function (response) {

                        });
                    };
                    if (vmd.flog) {
                        vmd.getQrcode(vmd.userId, vmd.skuId);
                    }
                    // else {
                    //     vmd.getQrcode();
                    // }
                    //限制手机号输入，只能输入以1开头的数字，不做正则验证
                    vmd.checkPhone = function () {
                        var phone = vmd.user.phone + '';
                        //如果未输入
                        if (!phone || phone === '' || phone === 'null') {
                            vmd.isPhoneNumTrue = false;
                            return -1;
                        }
                        //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
                        if (isNaN(phone) || phone.substr(0, 1) !== '1') {
                            vmd.user.phone = phone.substr(0, phone.length - 1);
                            vmd.isPhoneNumTrue = false;
                            return -1;
                        }
                        if (isNaN(phone) || phone.substr(1, 1) == '0' || phone.substr(1, 1) == '1' || phone.substr(1, 1) == '2') {
                            vmd.user.phone = phone.substr(0, phone.length - 1);
                            vmd.isPhoneNumTrue = false;
                            return -1;
                        }
                        //如果长度超过11，抹去最后一位
                        if (phone.length > 11) {
                            vmd.user.phone = phone.substr(0, phone.length - 1);
                            vmd.isPhoneNumTrue = true;
                            return -1;
                        }
                        if (phone.length !== 11) {
                            vmd.isPhoneNumTrue = true;
                            return -1;
                        }
                        vmd.isPhoneNumTrue = true;
                        return 0;
                    };
                    $scope.bindPhone = function () {
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/bargain/registerPhone',
                            params: {
                                'phone': vmd.user.phone
                            },
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            if (resp.data.isExitPhone) {
                                // alert('失败');
                                alertService.msgAlert("cancle", "号码已被注册");
                            } else {
                                // vmd.userId = resp.data.userId;
                                vmd.bindSuccess(vmd.userId, vmd.skuId);
                            }
                        }, function (response) {
                        });
                    };
                    if (!vmd.flog) {
                        $scope.bindPhone();
                    }
                    vmd.bindSuccess = function (userId, skuId) {
                        /*
                         * 判断是否关注
                         * */
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/bargain/judgeSubscribe',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            // alert(1)
                            if (resp.data.subscribe) {
                                vmd.checkSubmit(resp);
                            } else {
                                /*弹窗*/
                                vmd.getQrcode(userId, skuId);
                            }
                        }, function (response) {
                        });
                    };
                    vmd.checkSubmit = function (answer) {
                        $mdDialog.hide(answer);
                    };
                }],
            }).then(function (answer) {
                $scope.userId = answer.data.userId;
                if (answer.data.userId) {
                    $scope.isShowBtn = true;
                    $scope.userId = answer.data.userId;
                    $scope.goBargain();
                }
            }, function () {
                $scope.isShowBtn = true;
                // $scope.getRankInfo();
            });
        };


        $scope.showBargainDialog = function () {
            $mdDialog.show({
                templateUrl: 'views/main/bargainDetail/dialog.index.html',
                parent: angular.element(document.body).find('#qh-assistance-wap-front'),
                targetEvent: null,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {data: $scope.infoData},
                controller: ['$scope', '$mdDialog', 'appConfig', '$http', 'locals', 'alertService', '$timeout', function ($scope, $mdDialog, appConfig, $http, locals, alertService, $timeout) {
                    var vmd = this;
                    vmd.checkSubmit = function () {
                        $mdDialog.hide(true);
                    };
                    vmd.cancel = function () {
                        $mdDialog.cancel();
                    };

                    vmd.checkSubmit = function (answer) {
                        $mdDialog.hide(answer);
                    };
                }],
            }).then(function (answer) {
                $scope.userId = answer.data.userId;
                if (answer.data.userId) {
                    $scope.isShowBtn = true;
                    $scope.userId = answer.data.userId;
                    $scope.assistance($scope.userId, $scope.userId);
                }
            }, function () {
                $scope.isShowBtn = true;
                // $scope.getRankInfo();
            });
        };

        console.log($cookies.orgId);
        //获取订单信息
        $scope.checkBargainOrder = function () {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/generateOrder',
                params: {
                    awardId: $stateParams.awardId
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.orderId = resp.data.orderId;
                $scope.unionOrderId = resp.data.unionOrderId;
                // if (resp.data.status == 'ERROR') {
                //     alertService.msgAlert("exclamation-circle", resp.data.data).then(function () {
                //         $state.go('main.unionOrder', null, {reload: true});
                //     });
                //     return;
                // }
                $scope.goBuyUrl = appConfig.buyUrl + '&orgId=' + $cookies.orgId + '#/' + 'order/checkBargainOrder?awardId=' + $stateParams.awardId + '&unionOrderId=' + $scope.unionOrderId + '&orderId=' + $scope.orderId;
                $scope.goBuy = function () {
                    window.location.href = $scope.goBuyUrl;
                };
                $scope.goBuy()
            }, function (resp) {

            })
        };

        $scope.btnNum = "1";
        $scope.btnClick = function (num) {
            $scope.btnNum = num;
        };


        $scope.dialogShow = false;
        $scope.backdrop = false;
        $scope.shareDialog1 = function () {
            $scope.dialogShow = !$scope.dialogShow;
            $scope.backdrop = !$scope.backdrop;
        };

        $scope.imgShareShow = false;
        $scope.shareFiend = function () {
            $scope.imgShareShow = !$scope.imgShareShow;
            if ($scope.imgShareShow == true) {
                $scope.dialogShow = false;
                $scope.backdrop = true;
            }
        };

        $scope.imgWrapShow = false;
        $scope.imgShow = function () {
            $scope.imgWrapShow = !$scope.imgWrapShow;
            if ($scope.imgWrapShow == true) {
                $scope.dialogShow = false;
                $scope.imgShareShow = false;
                $scope.backdrop = true;
            }

        };

        $scope.closeDialog = function () {
            $scope.imgShareShow = false;
            $scope.imgWrapShow = false;
            $scope.dialogShow = false;
            $scope.backdrop = false;
        };
    }
})();
