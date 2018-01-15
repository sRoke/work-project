// 构建配置的数据
// var jso = new JSO({
//     providerID: 'oauth2-authorization-server-lottery-wap',
//     client_id: 'CLIENT_ID_kingsilk_qh-lottery-wap-front',
//     redirect_uri: location.href,
//     authorization: appConfig.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
//     scopes: {request: ['LOGIN']},
//     debug: true
// });
//
// ////登录类型，参数
// const loginType = {
//     WX: "loginType=WX&autoCreateUser",
//     WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
//     WX_QYH: "loginType=WX_QYH&autoCreateUser",
//     WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
//     PASSWORD: "loginType=PASSWORD"
// };
//
// // 检查是否是从 认证服务器回来的。
// jso.callback(location.href, function (at) {
//     window.at = at;
//     // console.log(`OAuth server  >>>>>>  at`);
// }, "oauth2-authorization-server-lottery-wap");
//
// // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
// jso.on('redirect', function (url) {
//     // console.log("jso.on >>>  redirect  >>> " + url);
//     location.href = url;
// });
//
//
// ////登录
// function goOAuth() {
//     // console.log(`=> wxLogin`);
//     // console.log('store.get(appConfig.token)========', store.get(appConfig.token));
//
//     let ua = window.navigator.userAgent.toLowerCase();
//     let type = "WX_SCAN";
//     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
//         type = "WX";
//     }
//     ////修改登录方式
//     // console.log(`${appConfig.wxloginPath}${loginType[type]}`);
//     jso.config.config.authorization = `${appConfig.wxloginPath}${loginType[type]}`;
//     jso.getToken((token) => {
//         // console.log("-------------------------------getToken");
//         // console.log(` ====> I get the token`, token);
//         window.at = token;
//         store.set(appConfig.token, token.access_token);
//
//     }, {});
//     // $scope.login = true;
//
// }
// // console.log("--------------------------------window.location.href", window.location.href)
// goOAuth();
angular.module('qh-lottery-wap-front', ['ngMaterial', 'ngAnimate'])
    .config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(false);
        // console.log("-------------------------------config")
    }]).controller('appCtrl', ['$scope', '$mdDialog', '$http', '$location', '$templateCache', '$timeout', '$q',
    function ($scope, $mdDialog, $http, $location, $templateCache, $timeout, $q) {


        // var vm = $scope.vm = {
        //     at: window.at,   // 获取的 access_token
        //     photos: null // 图片列表
        // };
        //微信分享初始化配置
        /*
         * 判断是否在微信
         * */
        function isInWx() {
            var ua = window.navigator.userAgent.toLowerCase();
            return ua.match(/MicroMessenger/i) && !ua.match(/windows/i)
        }

        //初始化
        function init() {
            var deferred = $q.defer();
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
                $http.get(appConfig.apiPath + '/weiXin/jsSdkConf', {
                    params: {
                        url: location.href.split('#')[0]
                    }
                }).then(function (resp) {
                    // console.log('111111111111', resp)
                    resp.data.jsApiConf.debug = false;
                    console.info("已经获取了微信JS SDK 的配置对象", resp.data.jsApiConf);
                    //$scope.wxReady = true;
                    wx.config(resp.data.jsApiConf);
                    wx.isConfig = true;
                    wx.error(function (res) {
                        console.info("微信调用出错了 ", res);
                    });
                    wx.ready(function () {
                        deferred.resolve(true);
                    });
                });
            } else {
                deferred.resolve(false);
            }
            return deferred.promise;
        }

        /*
         * 设置默认分享模板
         * */
        var defaultShareRingConf = {
            title: "幸运抽奖",
            desc: "您的朋友分享了一个链接给你",
            link: location.href,
            imgUrl: "https://img.kingsilk.net/FipIBAo39SLMX04AAwxV-RrjV2G1",
        };
        // 分享到朋友圈
        function shareRing(listener) {
            if (!isInWx()) {
                return;
            }
            wx.onMenuShareTimeline(listener ? listener : defaultShareRingConf);
        }

        // 发送给朋友
        function shareFriend(listener) {
            if (!isInWx()) {
                return;
            }
            wx.onMenuShareAppMessage(listener ? listener : defaultShareRingConf);
        }

        /*
         * 初始化微信分享
         * */
        function initShareOnStart(jsonWx) {
            $timeout(function () {
                init().then(function (data) {
                    if (data) {
                        shareRing(jsonWx);
                        shareFriend(jsonWx);
                    }
                });
            }, 1000);
        }

        // initShareOnStart();
        //转盘类
        function turntableDraw(obj, jsn) {
            "use strict";
            this.draw = {};
            this.draw.currNumber = 1;
            // this.draw.obj = $(obj);
            // console.log(obj);
            this.draw.obj = document.getElementById(obj);
            // console.log(this.draw.obj);
            // this.draw.objClass = $(obj).attr("class");
            this.draw.objClass = document.getElementById(obj).className;
            // this.draw.objClass = document.getElementById(obj).getAttribute("class");
            // console.log(this.draw.objClass)
            this.draw.newClass = "rotary" + "new" + parseInt(Math.random() * 1000);
            var _jiaodu = parseInt(360 / jsn.share);
            var _yuan = 360 * (jsn.weeks || 4) + parseInt(_jiaodu / 2);
            var _str = "";
            var _speed = jsn.speed || "2s";
            var _velocityCurve = jsn.velocityCurve || "ease";
            var _this = this;
            for (var i = 1; i <= jsn.share; i++) {
                _str += "." + this.draw.newClass + i + "{";
                _str += "transform:rotate(" + ((i - 1) * _jiaodu + _yuan) + "deg);";
                _str += "-ms-transform:rotate(" + ((i - 1) * _jiaodu + _yuan) + "deg);";
                _str += "-moz-transform:rotate(" + ((i - 1) * _jiaodu + _yuan) + "deg);";
                _str += "-webkit-transform:rotate(" + ((i - 1) * _jiaodu + _yuan) + "deg);";
                _str += "-o-transform:rotate(" + ((i - 1) * _jiaodu + _yuan) + "deg);";
                _str += "transition: transform " + _speed + " " + _velocityCurve + ";";
                _str += "-moz-transition: -moz-transform " + _speed + " " + _velocityCurve + ";";
                _str += "-webkit-transition: -webkit-transform " + _speed + " " + _velocityCurve + ";";
                _str += "-o-transition: -o-transform " + _speed + " " + _velocityCurve + ";";
                _str += "}";
                _str += "." + this.draw.newClass + i + "stop{";
                _str += "transform:rotate(" + ((i - 1) * _jiaodu + parseInt(_jiaodu / 2)) + "deg);";
                _str += "-ms-transform:rotate(" + ((i - 1) * _jiaodu + parseInt(_jiaodu / 2)) + "deg);";
                _str += "-moz-transform:rotate(" + ((i - 1) * _jiaodu + parseInt(_jiaodu / 2)) + "deg);";
                _str += "-webkit-transform:rotate(" + ((i - 1) * _jiaodu + parseInt(_jiaodu / 2)) + "deg);";
                _str += "-o-transform:rotate(" + ((i - 1) * _jiaodu + parseInt(_jiaodu / 2)) + "deg);";
                _str += "}";
            }
            // console.log('<style></style>==>', _str)
            angular.element(document.head).append("<style>" + _str + "</style>");
            _speed = _speed.replace(/s/, "") * 1000;
            this.draw.startTurningOk = false;
            this.draw.number = jsn.number;
            this.draw.goto = function (ind) {
                if (_this.draw.startTurningOk) {
                    return false
                }
                if (_this.draw.currNumber > _this.draw.number) {
                    // callbackA();
                    return false
                } else {
                    _this.draw.currNumber++
                }
                // console.log(' _this.draw.newClass==>', _this.draw.newClass, ind)
                _this.draw.obj.setAttribute('class', _this.draw.objClass + " " + _this.draw.newClass + ind);
                // _this.draw.obj.attr("class",_this.draw.objClass+" "+_this.draw.newClass+ind);
                _this.draw.startTurningOk = true;
                $scope.lottery.number--;
                var timer = setInterval(function () {
                    $scope.flog = !$scope.flog;
                    // console.log($scope.flog);
                    $scope.$digest();
                }, 200);
                setTimeout(function () {
                    clearInterval(timer)
                    _this.draw.obj.setAttribute('class', _this.draw.objClass + " " + _this.draw.newClass + ind + "stop");
                    // _this.draw.obj.attr("class",_this.draw.objClass+" "+_this.draw.newClass+ind+"stop");
                    if (jsn.callback) {
                        _this.draw.startTurningOk = false;
                        jsn.callback(ind);
                    }
                    ;
                }, _speed + 10);
                return _this.draw;
            };
            return this.draw;
        };
        //----------------------------------------------------------------------------------以上为页面初始化需加载内容
        //用来判断是否需要显示二维码界面
        $scope.WXfollow = {};
        //判断是否输入手机号
        $scope.inputPhone = false;
        //控制按钮不能连点
        $scope.clickBtn = true;
        //判断是否显示领取奖品按钮
        $scope.receivePrizes = false;
        // //判断是否登录
        // $scope.login = false;
        //控制转盘第一层背景更换
        $scope.flog = true;
        //获取中奖信息用来轮播
        console.log($location.search().sn);
        $http({
            method: 'GET',
            url: appConfig.apiPath + '/lottery/getImg',
            params: {
                sn: $location.search().sn,
            },
            headers: {}
        }).then(function (resp) {
            $scope.loginImg = appConfig.imgUrl + resp.data.loginImg;
            $scope.qRCodeImg = appConfig.imgUrl + resp.data.qRCodeImg;
            if (isInWx()) {
                var curConf = {
                    title: resp.data.shareInfo.title,
                    desc: resp.data.shareInfo.desc,
                    link: resp.data.shareInfo.url,
                    imgUrl: 'https:' + appConfig.imgUrl + resp.data.shareInfo.imgUrl,
                    success: function () {
                        // 用户确认分享后执行的回调函数
                        // console.log(1111111111111111111);
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        // alertService.msgAlert('exclamation-circle', "取消分享");
                    }
                };
                initShareOnStart(curConf);
            }
        });
        //限制手机号输入，只能输入以1开头的数字，不做正则验证
        $scope.user = {};
        $scope.checkPhone = function () {
            var phone = $scope.user.phone + '';
            //如果未输入
            // console.log(phone)
            if (!phone || phone === '' || phone === 'null') {
                $scope.isPhoneNumTrue = false;
                return -1;
            }
            //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
            if (isNaN(phone) || phone.substr(0, 1) !== '1') {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                $scope.isPhoneNumTrue = false;
                return -1;
            }
            if (isNaN(phone) || phone.substr(1, 1) == '0' || phone.substr(1, 1) == '1' || phone.substr(1, 1) == '2') {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                $scope.isPhoneNumTrue = false;
                return -1;
            }
            //如果长度超过11，抹去最后一位
            if (phone.length > 11) {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                $scope.isPhoneNumTrue = true;
                return -1;
            }
            if (phone.length !== 11) {
                $scope.isPhoneNumTrue = false;
                return -1;
            }
            $scope.isPhoneNumTrue = true;
            return 0;
        };
        $scope.goLottery = function () {
            if (!$scope.user.phone || $scope.user.phone.length <= 10) {
                return;
            } else {
                console.log(_hmt);
                _hmt.push(['_trackEvent','lottery','click','/qh/lottery/lottery']);
                // clearInterval(getRandomPeople);
                $scope.inputPhone = true;
                //已经登录
                if ($scope.inputPhone) {
                    $scope.WXfollow.flog = false;
                    $scope.data = {};
                    //转盘实例 //.img1为需要旋转元素的id;
                    $scope.lottery = {};
                    //默认每次进页面可以转5次
                    // setTimeout(function () {
                    //查看奖品按钮
                    $scope.checkSubmit = function () {
                        $scope.WXfollow.flog = true;
                        $scope.data.isValid = true;
                        // $scope.lottery.number = 1;
                    };

                    $scope.changeWXfollow = function () {
                        $scope.WXfollow.flog = false;
                        $scope.data.isValid = false;
                    };

                    // $scope.lottery.number = 0;
                    //登录后进页面请求数据
                    $scope.pageChange = function () {
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/lottery/getInfo',
                            headers: {},
                            params: {
                                'sn': $location.search().sn,
                                phone: $scope.user.phone,

                            },
                        }).then(function (resp) {
                            console.log(resp.data);
                            $scope.data = resp.data;
                            $scope.data.img = appConfig.imgUrl + resp.data.dialImg;
                            $scope.login = true;
                            $scope.user.userId = resp.data.userId;
                            $scope.barcode = appConfig.imgUrl + resp.data.barcode;
                            if (resp.data.code == 'ERROR') {
                                if (resp.data.isValid == "未获取到token信息，请重新登录") {
                                    // lottertExit=10;
                                    // $scope.aaa();
                                    console.log(resp.data.code);
                                    $scope.error = true;
                                    // alert('出错了!!!请重新进入');
                                    return;
                                } else {
                                    console.log(resp.data.code);
                                    $scope.error = true;
                                    // alert('出错了!!!请重新进入');
                                    return;
                                }
                            }
                            if (resp.data.isValid == '该吊牌已被使用') {
                                $scope.isUsed = true;
                                $scope.data.remainCount = 0;
                            }
                            $scope.shareInfo = resp.data.shareInfo;
                            if (isInWx()) {
                                // var link =$scope.shareInfo.url.split('#/?id=')[0];
                                // var finalLing = link + '#/?&id=' + $scope.shareInfo.shareRaffleId;
                                // console.log('finalLing==='+finalLing)
                                var curConf = {
                                    title: $scope.shareInfo.title,
                                    desc: $scope.shareInfo.desc,
                                    link: $scope.shareInfo.url,
                                    imgUrl: 'https:' + appConfig.imgUrl + $scope.shareInfo.imgUrl,
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                        // console.log(1111111111111111111);

                                        if ($scope.isUsed == true) {
                                            // return;
                                        } else {
                                            $http({
                                                method: "GET",
                                                url: appConfig.apiPath + '/lottery/shareLottery',
                                                params: {
                                                    'sn': $location.search().sn,
                                                    'userId': $scope.user.userId,
                                                    'type': 'LOTTERY',
                                                    'goal': 'CIRCLE_FRIENDS',
                                                    'shareUrl': $scope.shareInfo.url,
                                                },
                                                headers: {
                                                    // 'Authorization': 'Bearer ' + store.get(appConfig.token),
                                                }
                                            }).then(function (resp) {
                                                console.log(resp);
                                                $scope.pageChange();
                                            }, function () {
                                                // $scope.maskHide();
                                            });
                                        }
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                        // alertService.msgAlert('exclamation-circle', "取消分享");
                                    }
                                };
                                initShareOnStart(curConf);
                            }
                            //判断抽奖次数
                            if ($scope.data.remainCount == 0) {
                                $scope.lottery.number = 0;
                                $scope.receivePrizes = true;
                            } else {
                                $scope.lottery.number = $scope.data.remainCount;
                            }
                            // console.log('$scope.data.awards=====>', $scope.data.awards);

                            $scope.newdraw = new turntableDraw('img01', {
                                //几个奖项
                                share: $scope.data.awards.length,
                                // share: 6,
                                // 旋转几秒
                                speed: "3s",
                                //变速
                                velocityCurve: "ease",
                                //先转6圈
                                weeks: 6,
                                //可以旋转次数
                                number: $scope.lottery.number,
                                // number: 5,
                                //旋转完成后的回调函数
                                callback: function (num) {
                                    callbackB(num);
                                },
                            });


                            //点击旋转
                            $scope.turn = function (ev) {
                                // ev. style.disabled = true ;
                                if ($scope.isUsed == true) {
                                    $scope.dialogNoNumber("该二维码已被使用");
                                    return;
                                }
                                //没有次数不发送请求
                                if ($scope.lottery.number == 0) {
                                    $scope.dialogNoNumber("亲,您的机会已经用完了哦~");
                                    return;
                                }
                                // if (resp.data.isValid == '该吊牌已被使用') {
                                //     $scope.dialogNoNumber("该二维码已被使用");
                                // }

                                $scope.clickBtn = false;
                                $http({
                                    method: "GET",
                                    url: appConfig.apiPath + '/lottery/winLottery',
                                    params: {
                                        'sn': $location.search().sn,
                                        userId: $scope.user.userId,
                                    },
                                    headers: {
                                        // 'Authorization': 'Bearer ' + store.get(appConfig.token),
                                    }
                                }).then(function (resp) {
                                    //后台返回的抽奖结果
                                    $scope.lottery.specificPrizes = resp.data;
                                    $scope.lottery.img = appConfig.imgUrl + resp.data.awardImg.id;
                                    // console.log('resp==>', resp);
                                    // console.log('$scope.data.awards.length==>', $scope.data.awards.length);
                                    // console.log('$scope.lottery.number', $scope.lottery.number);

                                    // $scope.newdraw.goto(parseInt(Math.random() * $scope.data.awards.length) + 1);
                                    //注意这里的数字不能超出奖项数
                                    $scope.newdraw.goto(parseInt(resp.data.seqNum) + 1);
                                    $scope.receivePrizes = true;
                                }, function () {

                                });
                            };
                        }, function (error) {
                            $scope.error = true;
                            console.log(111111111)
                        });
                    };
                    $scope.pageChange();

                    // }, 0);
                    //抽奖回调
                    // function callbackA() {
                    //     // alert('次数用完了');
                    // }
                    function callbackB(ind) {
                        $scope.clickBtn = true;
                        $scope.dialogShow($scope.lottery.specificPrizes);
                    }

                    //抽奖结束弹窗
                    $scope.dialogShow = function (ind) {
                        $mdDialog.show({
                            templateUrl: 'dialog.html',
                            parent: angular.element(document.body).find('#qh-lottery-wap-front'),
                            // targetEvent: ev,
                            clickOutsideToClose: true,
                            fullscreen: false,
                            controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                                var vm = this;


                                if (isInWx()) {
                                    // console.log('isInWx,isInWx,isInWx,isInWx,isInWx,isInWx,isInWx');
                                    vm.flog = true;
                                } else {
                                    // console.log('noisInWx,noisInWx,noisInWx,noisInWx,noisInWx,noisInWx,noisInWx');
                                    vm.flog = false;
                                }
                                vm.ind = ind;
                                vm.imgUrl = appConfig.imgUrl;
                                // console.log(vm);
                                $scope.appConfig = appConfig;
                                vm.checkSubmit = function () {
                                    $mdDialog.hide(true);
                                };
                                vm.cancel = function () {
                                    $mdDialog.cancel();
                                };
                                vm.check = function () {
                                    $mdDialog.hide(false);
                                }
                            }],
                            controllerAs: "vm"
                        }).then(function (answer) {
                            // console.log(answer)
                            if (answer == true) {
                                //显示二维码界面,隐藏抽奖界面
                                $scope.checkSubmit();
                                // $scope.WXfollow.flog = true;
                            } else {
                                $scope.maskShow();
                            }

                        }, function (answer) {
                            $scope.receivePrizes = true;
                        });
                    };


                    $scope.goShare = function () {
                        $scope.maskShow();
                    };

                    //没次数弹窗
                    $scope.dialogNoNumber = function (ind) {
                        $mdDialog.show({
                            templateUrl: 'noNumberDialog.html',
                            parent: angular.element(document.body).find('#qh-lottery-wap-front'),
                            // targetEvent: ev,
                            clickOutsideToClose: true,
                            fullscreen: false,
                            controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                                var vm = this;
                                vm.ind = ind;
                                // console.log(vm.ind.awardImg);
                                console.log(appConfig.imgUrl);
                                $scope.appConfig = appConfig;
                                vm.checkSubmit = function () {
                                    $mdDialog.hide(true);
                                };
                                vm.cancel = function () {
                                    $mdDialog.cancel();

                                };
                            }],
                            controllerAs: "vm"
                        }).then(function (answer) {
                            // alert(answer)
                            if (answer) {
                                //显示二维码界面,隐藏抽奖界面
                                $scope.WXfollow.flog = true;
                            }
                        }, function (answer) {
                            $scope.receivePrizes = true;
                        });
                    };


                    //游戏规则弹窗
                    $scope.showGameRules = function (ev) {
                        $mdDialog.show({
                            templateUrl: 'lotteryRules.html',
                            parent: angular.element(document.body).find('#qh-lottery-wap-front'),
                            // targetEvent: ev,
                            clickOutsideToClose: true,
                            fullscreen: false,
                            controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                                var vm = this;
                                vm.ind = ev;
                                // console.log(vm);
                                /*
                                 * 将后台数据存入模板并绑定到页面
                                 * */
                                // console.log('====================', vm.ind.desp);
                                var tplUrl = "/___/store/xxxx/index/tpl";
                                $scope.tplUrl = tplUrl;
                                $templateCache.put(tplUrl, vm.ind.desp);
                                vm.checkSubmit = function () {
                                    $mdDialog.hide(true);
                                };
                                vm.cancel = function () {
                                    $mdDialog.cancel();
                                };
                            }],
                            controllerAs: "vm"
                        }).then(function () {
                            // alert(answer)
                        }, function () {
                            // alert(answer)
                        });
                    }


                    //遮罩打开与关闭
                    $scope.mask = false;
                    $scope.maskShow = function () {
                        $scope.mask = true;
                    };
                    $scope.maskHide = function () {
                        $scope.mask = false;
                    };

                } else {
                }
            }
        };


    }]);

