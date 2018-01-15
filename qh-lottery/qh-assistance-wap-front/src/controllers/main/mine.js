// 构建配置的数据
(function () {
    angular.module('qh-assistance-wap-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.mine", {
            url: "/mine?weHelpId&targetUserId&hasFollow",
            views: {
                "@": {
                    templateUrl: 'views/main/mine/index.root.html',
                    controller: mineController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    mineController.$inject = ['$scope', '$http', 'appConfig', '$stateParams', '$mdDialog', 'wxService', '$interval', '$filter', '$rootScope', '$timeout'];
    function mineController($scope, $http, appConfig, $stateParams, $mdDialog, wxService, $interval, $filter, $rootScope, $timeout) {

        var vm = this;
        $scope.targetUserId = $stateParams.targetUserId ? $stateParams.targetUserId : '';
        $scope.weHelpId = $stateParams.weHelpId;
        $scope.hasFollow = $stateParams.hasFollow;
        console.log($stateParams.hasFollow);
        $scope.userId = '';
        console.log('$stateParams', $stateParams.id);

        $rootScope.intervalStop = [];
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
                $scope.data.activityCheck = {};
            }, function errorCallback(response) {

            });
        };
        $scope.getActiveInfo();
        //如果是分享过来的肯定存在targettargetUserId;进入页面
        if ($stateParams.targetUserId) {
            // $http({
            //     method: "GET",
            //     url: appConfig.apiPath + '/weHelp/help',
            //     params: {
            //         'id': '592e666568fd9e43c2f47803',
            //         'targetUserId': $scope.userId,
            //     },
            //     headers: {
            //         // 'Authorization': 'Bearer ' + store.get(appConfig.token),
            //     }
            // }).then(function (resp) {
            //     console.log(resp+"11111111111");
            //     location.reload(true);
            // }, function errorCallback(response) {
            //
            // });
        }

        /*获取排行榜信息*/
        $scope.getRankInfo = function (isSuccess) {
            console.log(isSuccess);
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/targetUserInfo',
                params: {
                    'id': $scope.weHelpId,
                    'targetUserId': $scope.targetUserId,
                },
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                $scope.user = resp.data;

                if ($scope.user.targetUserId == $scope.user.userId) {
                    $scope.isMine = true;
                    console.log($scope.isMine);
                } else {
                    $scope.isMine = false;
                    console.log($scope.isMine);
                }

                if (wxService.isInWx()) {
                    $scope.$on("$destroy", function () {
                        wxService.shareRing(); // 恢复默认绑定
                        wxService.shareFriend();
                    });
                    var link;
                    console.log('window.location.href.indexOf("targetUserId=")', window.location.href.indexOf("targetUserId="));
                    if (isSuccess) {
                        if (window.location.href.indexOf("targetUserId=") > 0) {
                            link = window.location.href.substring(0, window.location.href.indexOf("targetUserId=")) + 'targetUserId=' + $scope.user.userId;
                            console.log('link1', link);
                        } else {
                            link = window.location.href.substring(0, window.location.href.indexOf("hasFollow=")) + 'targetUserId=' + $scope.user.userId;
                            console.log('link2', link);
                        }
                    }
                    else {
                        if (window.location.href.indexOf("hasFollow=") > 0) {
                            link = window.location.href.substring(0, window.location.href.indexOf("hasFollow=")) + 'targetUserId=' + $scope.user.userId;
                            console.log('link3', link);
                        }
                        else if (window.location.href.indexOf("targetUserId=") < 0 && window.location.href.indexOf("hasFollow=") < 0) {
                            link = window.location.href + '&targetUserId=' + $scope.user.userId;
                            console.log('link4', link);
                        } else {
                            link = window.location.href;
                            console.log('link5', link);
                        }
                    }
                    console.log('finalLing', link);
                    var curConf = {
                        title: $scope.data.customTitle,
                        desc: $scope.data.customDescription,
                        link: link,
                        imgUrl: 'https:' + appConfig.imgUrl + $scope.data.img,
                        success: function () {
                            // 用户确认分享后执行的回调函数
                        },
                        cancel: function () {
                            // 用户取消分享后执行的回调函数
                            // alertService.msgAlert('exclamation-circle', "取消分享");
                        }
                    };
                    wxService.initShareOnStart(curConf);
                }

            }, function errorCallback(response) {

            });
        };
        $scope.getRankInfo();

        $scope.goMine = function () {
            $scope.user.targetUserId = $scope.user.userId;

            console.log($scope.user.targetUserId);
            $scope.getRankInfo();
        };
        /*去参加*/
        $scope.join = function () {
            // console.log(store.get(appConfig.token));
            $scope.targetUserId = $scope.userId;
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
                console.log('resp2=', resp);
                // resp.data.registerPhone = false;
                $scope.userId = resp.data.userId;
                $scope.targetUserId = resp.data.userId;
                if (resp.data.subscribe) {
                    /*去判断用户是否关注*/
                    $scope.targetUserId = resp.data.userId;
                    $scope.assistance($scope.userId, $scope.targetUserId);
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true);
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
                console.log($scope.targetUserId);
                if (resp.data.registerPhone) {
                    //进行页面跳转
                    // $state.go('main.index', {id: '', userId: resp.data.userId});
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
            console.log(targetUserId);
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
                    $scope.getRankInfo(true);
                }
                // location.reload(true);
            }, function (response) {

            });
        };


        $scope.helpUser = function () {
            $http({
                method: "GET",
                url: appConfig.apiPath + '/weHelp/judgeSubscribe',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                }
            }).then(function (resp) {
                console.log('resp2=', resp);
                // resp.data.registerPhone = false;
                $scope.userId = resp.data.userId;
                $scope.targetUserId = $stateParams.targetUserId;
                if (resp.data.subscribe) {
                    /* +1 */
                    $scope.assistance($scope.userId, $scope.targetUserId);
                } else {
                    /*弹窗*/
                    $scope.showGameRules(true);
                }
            }, function errorCallback(response) {

            });

        };


        if ($stateParams.hasFollow) {
            // alert($stateParams.hasFollow);
            if (!$stateParams.targetUserId) {
                $scope.join();
            }
            else {
                $scope.helpUser();
            }
        }

        $rootScope.intervalStop = $interval(function () {

            var now = new Date().getTime();
            var endTime = new Date($filter("date")($scope.data.endTime, "yyyy/MM/dd HH:mm:ss")).getTime();

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
        }, 1000);


        //奖品列表弹窗
        $scope.showGameRules = function (status) {
            console.log('$scope.data', $scope.data);
            $mdDialog.show({
                templateUrl: 'views/main/indexDialog.html',
                parent: angular.element(document.body).find('#qh-assistance-wap-front'),
                targetEvent: null,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {data: $scope.user},
                controller: ['$scope', '$mdDialog', 'appConfig', '$http', 'locals', function ($scope, $mdDialog, appConfig, $http, locals) {
                    var vmd = this;
                    vmd.userId = locals.data.userId;
                    vmd.targetUserId = locals.data.targetUserId;
                    vmd.id = locals.data.id;


                    vmd.user = {};
                    vmd.flog = status;

                    console.log('locals', locals);
                    vmd.checkSubmit = function () {
                        $mdDialog.hide(true);
                    };
                    vmd.cancel = function () {
                        $mdDialog.cancel();
                    };
                    vmd.getQrcode = function () {
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/weHelp/getQrCode',
                            params: {
                                'id': vmd.id,
                                'targetUserId': vmd.targetUserId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {

                            vmd.qrCode = resp.data.ticket;
                            console.log('adsfsdfasdf', resp);
                        }, function (response) {

                        });
                    };
                    vmd.getQrcode();
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
                            vmd.isPhoneNumTrue = false;
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
                            console.log('resp3=', resp);
                            // $mdDialog.hide(resp);

                            $http({
                                method: "GET",
                                url: appConfig.apiPath + '/weHelp/judgeSubscribe',
                                params: {},
                                headers: {
                                    'Authorization': 'Bearer ' + store.get(appConfig.token),
                                }
                            }).then(function (resp) {
                                console.log('resp2=', resp);
                                $scope.userId = resp.data.userId;
                                $scope.targetUserId = resp.data.userId;
                                if (resp.data.subscribe) {
                                    $mdDialog.hide(resp);
                                } else {
                                    /*弹窗*/
                                    vmd.flog = !vmd.flog;
                                }
                            }, function (response) {

                            });


                        }, function (response) {

                        });
                    };

                }],
            }).then(function (answer) {
                console.log('answer', answer);
                $scope.userId = answer.data.userId;
                if (answer.data.userId) {
                    $scope.userId = answer.data.userId;
                    $scope.assistance($scope.userId, $scope.userId);
                }
            }, function () {
                // alert(answer)
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
