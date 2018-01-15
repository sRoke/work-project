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
        // console.log('OAuth server  >>>>>>  at');
    }, "oauth2-authorization-server-lottery-wap");

    // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
    jso.on('redirect', function (url) {
        // console.log("jso.on >>>  redirect  >>> " + url);
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


    angular.module('qh-assistance-wap-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.index", {
            url: "/?weHelpId&targetUserId&hasFollow",
            views: {
                "@": {
                    templateUrl: 'views/main/index.html',
                    controller: IndexController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    IndexController.$inject = ['$scope', '$http', 'appConfig', '$stateParams', '$mdDialog', 'wxService', '$interval', '$filter', '$rootScope', '$timeout', 'alertService', '$templateCache'];
    function IndexController($scope, $http, appConfig, $stateParams, $mdDialog, wxService, $interval, $filter, $rootScope, $timeout, alertService, $templateCache) {
        var vm = this;
        $scope.isShowBtn = false;
        $scope.tab = 1;
        $scope.tabs = function (num) {
            $scope.tab = num;
            console.log($scope.tab);
        };
        $scope.activeFalse = true;
        $scope.isTargetUserId = $stateParams.targetUserId;

        $scope.targetUserId = $stateParams.targetUserId ? $stateParams.targetUserId : '';
        $scope.weHelpId = $stateParams.weHelpId;
        $scope.hasFollow = $stateParams.hasFollow;
        $scope.userId = '';
        $rootScope.intervalStop = [];

        $scope.isShowHtml = false;

        // console.log('store.get(appConfig.token)', store.get(appConfig.token));

        if (store.get(appConfig.token)) {
            $scope.isShowHtml = true;
        }


        /*
         * 获取活动信息
         * */
        $scope.getActiveInfo = function () {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/info',
                params: {
                    'id': $scope.weHelpId,
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;


                var now = new Date().getTime();
                var endTime = new Date($filter("date")($scope.data.endTime, "yyyy/MM/dd HH:mm:ss")).getTime();
                // 倒计时到零时，停止倒计时
                var rest = endTime - now;

                console.log(rest);

                if (rest <= 0 || $scope.data.disabled) {
                    $scope.activeFalse = false;
                    console.log('$scope.activeFalse',$scope.activeFalse);
                }

                var tplUrl = "rule.html";
                $scope.tplUrl = tplUrl;
                $templateCache.put(tplUrl, $scope.data.rule);

                $scope.data.activityCheck = {};
            }, function errorCallback(response) {

            });
        };
        $scope.getActiveInfo();


        /*获取排行榜信息*/
        $scope.getRankInfo = function (isSuccess) {

            var authHeader = 'Bearer ' + store.get(appConfig.token);
            console.log("====== authHeader : ", authHeader, store.get(appConfig.token), appConfig.token);
            console.log("====== appConfig : ", appConfig);
            console.log("====== window.appConfig : ", window.appConfig);

            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/targetUserInfo',
                params: {
                    'id': $scope.weHelpId,
                    'targetUserId': $scope.targetUserId,
                },
                headers: {
                    'Authorization': authHeader,
                }
            }).then(function (resp) {
                $scope.user = resp.data;
                if ($scope.user.haveHelp == true) {
                    $scope.isShowHelpBtn = false;
                }
                if ($scope.user.targetUserId == $scope.user.userId) {
                    $scope.isMine = true;
                } else {
                    $scope.isMine = false;
                }

                if (wxService.isInWx()) {
                    $scope.$on("$destroy", function () {
                        wxService.shareRing(); // 恢复默认绑定
                        wxService.shareFriend();
                    });
                    var link;
                    if (isSuccess) {
                        link = window.location.href.substring(0, window.location.href.indexOf("#/")) + '#/?weHelpId=' + $stateParams.weHelpId + '&targetUserId=' + $scope.user.targetUserId;
                    }
                    else if ($scope.user.targetUserId != $scope.user.userId) {
                        link = window.location.href.substring(0, window.location.href.indexOf("#/")) + '#/?weHelpId=' + $stateParams.weHelpId + '&targetUserId=' + $scope.user.targetUserId;
                    } else {
                        link = window.location.href.substring(0, window.location.href.indexOf("#/")) + '#/?weHelpId=' + $stateParams.weHelpId + '&targetUserId=' + $scope.user.userId;
                    }
                    var curConf = {
                        title: $scope.data.customTitle,
                        desc: $scope.data.customDescription,
                        link: link,
                        imgUrl: 'https:' + appConfig.imgUrl + $scope.data.img,
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
                }
                $timeout(function () {
                    $scope.isShowBtn = true;
                }, 300)

            }, function errorCallback(response) {

            });
        };
        $scope.getRankInfo();

        $scope.goMine = function (userId) {
            $scope.user.targetUserId = userId;
            $scope.targetUserId = userId;
            $scope.getRankInfo();
        };
        /*去参加*/
        $scope.join = function () {

            if ($stateParams.targetUserId) {
                $scope.targetUserId = $scope.userId;
            } else {
                $scope.isShowHelpBtn = true;
            }
            $scope.getFollow();
        };
        //判断是否关注
        $scope.getUserInfo = function () {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/judgeSubscribe',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.userId = resp.data.userId;
                $scope.targetUserId = resp.data.userId;
                if (resp.data.subscribe) {
                    /*去判断用户是否关注*/
                    $scope.targetUserId = resp.data.userId;
                    $scope.assistance($scope.userId, $scope.targetUserId);
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true, '');
                }
            }, function (response) {

            });
        };
        //判断用户是否存在
        $scope.getFollow = function () {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/judgeUser',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.userId = resp.data.userId;
                $scope.targetUserId = resp.data.userId;
                // console.log($scope.targetUserId);
                if (resp.data.registerPhone) {
                    $scope.getUserInfo();
                } else {
                    $scope.showGameRules(false);
                }
            }, function (response) {
            });
        };

        /*
         * 接力加一
         * */
        $scope.assistance = function (userId, targetUserId) {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/help',
                params: {
                    'id': $scope.weHelpId,
                    'userId': userId,
                    'targetUserId': targetUserId,
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                if (resp.data.result == 'SUCCESS') {
                    // $state.reload();
                    alertService.msgAlert("success", resp.data.data);
                    $scope.getRankInfo(true);
                }
                // location.reload(true);
            }, function (response) {
            });
        };


        /*
         * 助力好友
         * */
        $scope.helpUser = function (status) {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/judgeSubscribe',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.userId = resp.data.userId;
                $scope.targetUserId = $stateParams.targetUserId;
                if (resp.data.subscribe) {
                    /* +1 */
                    $scope.isShowBtn = true;
                    $scope.assistance($scope.userId, $scope.targetUserId);
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true, $stateParams.targetUserId);
                }
            }, function errorCallback(response) {
            });
        };


        if ($stateParams.hasFollow) {
            if (!$stateParams.targetUserId) {
                $scope.isShowBtn = false;
                $scope.join();
            }
            else {
                $scope.isShowBtn = false;
                $scope.helpUser('0');
            }
        }

        if (!$stateParams.hasFollow) {
            $timeout(function () {
                $scope.isShowBtn = true;
            }, 500)
        }

        $timeout(function () {
            $rootScope.intervalStop = $interval(function () {
                var now = new Date().getTime();
                var endTime = new Date($filter("date")($scope.data.endTime, "yyyy/MM/dd HH:mm:ss")).getTime();
                // 开始倒计时到零时，停止倒计时
                var startTime = new Date($filter("date")($scope.data.startTime, "yyyy/MM/dd HH:mm:ss")).getTime();
                var last = startTime - now;
                if (last <= 0) {
                    $scope.data.isEarlier = false;

                    // 倒计时到零时，停止倒计时
                    var rest = endTime - now;
                    if (rest <= 0) {

                        $scope.data.activityCheck = null;
                        $interval.cancel($rootScope.intervalStop);
                        $rootScope.intervalStop = null;
                        return;
                    }
                    var leftsecond = parseInt(rest / 1000);
                    var day1 = Math.floor(leftsecond / (60 * 60 * 24));
                    var hour1 = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
                    var minute1 = Math.floor((leftsecond - day1 * 24 * 60 * 60 - hour1 * 3600) / 60);
                    var second1 = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour1 * 3600 - minute1 * 60);
                    $scope.data.activityCheck.day = day1;
                    $scope.data.activityCheck.hour = hour1;
                    $scope.data.activityCheck.minute = minute1;
                    $scope.data.activityCheck.second = second1;
                }
                var lastTime = parseInt(last / 1000);
                var day2 = Math.floor(lastTime / (60 * 60 * 24));
                var hour2 = Math.floor((lastTime - day2 * 24 * 60 * 60) / 3600);
                var minute2 = Math.floor((lastTime - day2 * 24 * 60 * 60 - hour2 * 3600) / 60);
                var second2 = Math.floor(lastTime - day2 * 24 * 60 * 60 - hour2 * 3600 - minute2 * 60);
                $scope.data.activityCheck.day2 = day2;
                $scope.data.activityCheck.hour2 = hour2;
                $scope.data.activityCheck.minute2 = minute2;
                $scope.data.activityCheck.second2 = second2;
            }, 1000);
        }, 300);


        //奖品列表弹窗
        $scope.showGameRules = function (status, targetUserId1) {


            $mdDialog.show({
                templateUrl: 'views/main/indexDialog.html',
                parent: angular.element(document.body).find('#qh-assistance-wap-front'),
                targetEvent: null,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {data: $scope.user},
                controller: ['$scope', '$mdDialog', 'appConfig', '$http', 'locals', 'alertService', '$timeout', function ($scope, $mdDialog, appConfig, $http, locals, alertService, $timeout) {
                    var vmd = this;
                    vmd.userId = locals.data.userId;
                    vmd.targetUserId = locals.data.targetUserId;
                    vmd.id = locals.data.id;
                    vmd.user = {};
                    vmd.flog = status;
                    vmd.targetUserId1 = targetUserId1;
                    console.log('vmd.targetUserId1', vmd.targetUserId1);
                    vmd.checkSubmit = function () {
                        $mdDialog.hide(true);
                    };
                    vmd.cancel = function () {
                        $mdDialog.cancel();

                    };
                    vmd.getQrcode = function (targetUserId) {
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/weHelp/getQrCode',
                            params: {
                                'id': vmd.id,
                                'targetUserId': targetUserId ? targetUserId : ''
                            },
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            vmd.qrCode = resp.data.ticket;
                        }, function (response) {

                        });
                    };
                    if (vmd.targetUserId1) {
                        vmd.getQrcode(vmd.targetUserId1);
                    } else {
                        vmd.getQrcode();
                    }
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
                            url: appConfig.apiPath + '/weHelp/registerPhone',
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
                                vmd.bindSuccess();
                            }
                        }, function (response) {
                        });
                    };

                    vmd.bindSuccess = function () {
                        /*
                         * 判断是否关注
                         * */
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/weHelp/judgeSubscribe',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            $scope.userId = resp.data.userId;
                            $scope.targetUserId = resp.data.userId;
                            if (resp.data.subscribe) {
                                vmd.checkSubmit(resp);
                            } else {
                                /*弹窗*/
                                vmd.flog = !vmd.flog;
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
                    $scope.assistance($scope.userId, $scope.userId);
                }
            }, function () {
                $scope.isShowBtn = true;
                // $scope.getRankInfo();
            });
        };

        //遮罩打开与关闭
        vm.mask = false;
        vm.maskShow = function () {
            vm.mask = true;
        };
        vm.maskHide = function () {
            vm.mask = false;
        };


    }
})();
