// 构建配置的数据
(function () {
    // var jso = new JSO({
    //     providerID: 'oauth2-authorization-server-lottery-wap',
    //     client_id: 'CLIENT_ID_kingsilk_qh-lottery-wap-front',
    //     redirect_uri: location.href,
    //     authorization: appConfig.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
    //     scopes: {request: ['LOGIN']},
    //     debug: true
    // });
    // //登录类型，参数
    // var loginType = {
    //     WX: "loginType=WX&autoCreateUser",
    //     WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
    //     WX_QYH: "loginType=WX_QYH&autoCreateUser",
    //     WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
    //     PASSWORD: "loginType=PASSWORD"
    // };
    // // 检查是否是从 认证服务器回来的。
    // jso.callback(location.href, function (at) {
    //     window.at = at;
    //     // console.log('OAuth server  >>>>>>  at');
    // }, "oauth2-authorization-server-lottery-wap");
    //
    // // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
    // jso.on('redirect', function (url) {
    //     // console.log("jso.on >>>  redirect  >>> " + url);
    //     location.href = url;
    // });
    // //登录
    // function goOAuth() {
    //
    //     // alert(2);
    //     var ua = window.navigator.userAgent.toLowerCase();
    //     var type = "WX_SCAN";
    //     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
    //         type = "WX";
    //     }
    //     //修改登录方式
    //     jso.config.config.authorization = appConfig.wxloginPath + loginType[type];
    //     jso.getToken(function (token) {
    //         window.at = token;
    //         store.set(appConfig.token, token.access_token);
    //     }, {})
    // }
    //
    // //自调用登录
    // goOAuth();



    angular.module('qh-bargain-wap-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.bargain", {
            url: "/bargain/{id}",
            views: {
                "@": {
                    templateUrl: 'views/main/bargain/index.root.html',
                    controller: bargainController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    bargainController.$inject = ['$scope', '$http', 'appConfig', '$stateParams', '$mdDialog', 'wxService', '$interval', '$filter', '$rootScope', '$timeout', 'alertService', '$templateCache', '$rootScope', '$state'];
    function bargainController($scope, $http, appConfig, $stateParams, $mdDialog, wxService, $interval, $filter, $rootScope, $timeout, alertService, $templateCache, $rootScope, $state) {

        console.log('55555555555555',store.get(appConfig.token));
        if(!store.get(appConfig.token)){
            $state.go('main.index');
        }

        console.log('this is bargain');
        console.log('$stateParams.activeId', $stateParams.id);
        $scope.imgUrl = appConfig.imgUrl;


        $timeout(function () {
            var swiper3 = $rootScope.swiper3 = new Swiper('.swiper-container3', {
                wrapperClass: 'my-wrapper3',
                slideClass: 'my-slide3',
                prevButton: '.swiper-button-prev',
                nextButton: '.swiper-button-next',
                slidesPerView: 1,
                centeredSlides: true,
                paginationClickable: true,
                spaceBetween: 20,
                iOSEdgeSwipeDetection: true,
                setWrapperSize: true,
                // loop: true,
                onSlideChangeEnd: function (swiper) {
                    console.log(swiper.activeIndex);
                    $scope.getJoinStatus($scope.item[swiper.activeIndex].id);
                    $rootScope.skuId = $scope.item[swiper.activeIndex].id;
                }
            });
        }, 50);

        $scope.getInfo = function () {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/info?id=' + $stateParams.id,
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token)
                }
            }).then(function (resp) {
                // $state.go('main.bargain', {activeId: '123456'}, {reload: true})
                $scope.infoData = resp.data;
                $scope.item = resp.data.awards;
                console.log(resp);
                $scope.getJoinStatus($scope.item[0].id);
                $rootScope.skuId = $scope.item[0].id;
                $scope.num = $scope.item[0].num;
                if (wxService.isInWx()) {
                    $scope.$on("$destroy", function () {
                        wxService.shareRing(); // 恢复默认绑定
                        wxService.shareFriend();
                    });
                }
                var curConf = {
                    title: $scope.infoData.shareTitle,
                    desc: $scope.infoData.shareDesp,
                    link: location.href,
                    imgUrl: 'https:' + appConfig.imgUrl + $scope.infoData.shareImg.id,
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


                angular.element('.rule-detail').html($scope.infoData.rule);
                angular.element('.detail-bottom').html($scope.infoData.desp);

                var tplUrl = "rule.html";
                $scope.tplUrl = tplUrl;
                $templateCache.put(tplUrl, $scope.infoData.rule);

                // var tplUrl1 = "rule.html";
                // $scope.tplUrl1 = tplUrl1;
                // $templateCache.put(tplUrl1, $scope.infoData.desp);
            })
        };
        $scope.getInfo();


        //判断是否参加过该活动
        $scope.getJoinStatus = function (awardId) {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/bargain/judgeJoined',
                params: {
                    awardId:awardId,
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.isJoined = resp.data.isJoined;
            }, function (response) {

            });
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
                    /*去判断用户是否关注*/
                    // $scope.targetUserId = resp.data.userId;
                    // $scope.assistance($scope.userId, $scope.targetUserId);
                    $state.go('main.bargainDetail', {
                        id: $stateParams.id,
                        awardId: $rootScope.skuId,
                        userId: resp.data.userId,

                    }, {reload: true})
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true, userId, skuId);
                }
            }, function (response) {

            });
        };
        //去砍价
        $scope.goBargain = function () {
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/bargain/judgeUser',
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token)
                }
            }).then(function (resp) {
                console.log(resp);
                $scope.userId = resp.data.userId;
                if (resp.data.registerPhone) {
                    $scope.getUserInfo($scope.userId, $rootScope.skuId);
                } else {
                    $scope.showGameRules(false, $scope.userId, $rootScope.skuId);
                }
            })
        };


        //奖品列表弹窗
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
                    console.log('locals', locals.data);
                    // vmd.userId = locals.data.userId;
                    // vmd.targetUserId = locals.data.targetUserId;
                    vmd.id = locals.data.id;
                    vmd.user = {};
                    vmd.flog = status;
                    vmd.userId = userId;
                    vmd.skuId = skuId;

                    console.log('vmd.userId', vmd.userId);
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
                    if (vmd.userId) {
                        vmd.getQrcode(vmd.userId, vmd.skuId);
                    }
                    // else {
                    //     vmd.getQrcode();
                    // }
                    //限制手机号输入，只能输入以1开头的数字，不做正则验证
                    vmd.checkPhone = function () {
                        var phone = vmd.user.phone + '';
                        //如果未输入
                        // console.log(phone)
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
                                vmd.userId = resp.data.userId;
                                vmd.bindSuccess(vmd.userId, vmd.skuId);
                            }
                        }, function (response) {
                        });
                    };

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
                                console.log('resp.data.userId', userId);
                                console.log('resp.data.userId', skuId);

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
    }
})();
