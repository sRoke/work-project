(function () {
    angular.module('qh-phone-lottery-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.lottery", {
            url: "/lottery?aa&id&phone",
            views: {
                "@": {
                    templateUrl: 'views/main/lottery/index.root.html',
                    controller: lotteryController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    lotteryController.$inject = ['$scope', 'appConfig', '$http', '$location', '$state', '$mdDialog', '$templateCache', 'wxService'];
    function lotteryController($scope, appConfig, $http, $location, $state, $mdDialog, $templateCache, wxService) {
        //用来判断是否需要显示二维码界面
        $scope.WXfollow = {};
        //控制按钮不能连点
        $scope.clickBtn = true;
        //判断是否显示领取奖品按钮
        $scope.receivePrizes = false;
        //判断是否登录
        $scope.login = false;
        //控制转盘第一层背景更换
        $scope.flog = true;
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
        //已经登录
        if ($location.search().id && $location.search().phone) {
            $scope.WXfollow.flog = false;
            $scope.data = {};
            //转盘实例 //.img1为需要旋转元素的id;
            $scope.lottery = {};
            //查看奖品按钮
            $scope.checkSubmit = function () {
                $scope.WXfollow.flog = true;
                $scope.data.isValid = true;
                // $scope.lottery.number = 1;
            };
            //登录后进页面请求数据
            $scope.shareWX = function (shareInfo) {
                //微信分享
                if (wxService.isInWx()) {
                    $scope.$on("$destroy", function () {
                        wxService.shareRing(); // 恢复默认绑定
                        wxService.shareFriend();
                    });
                    var link = shareInfo.url.split('#/?id=')[0];
                    console.log('link', link);
                    var finalLing = link + '#/?&id=' + $location.search().id;
                    console.log('finalLing', finalLing);
                    // alert('link='+link);
                    // alert(finalLing);
                    var curConf = {
                        title: shareInfo.title,
                        desc: shareInfo.desc,
                        link: finalLing,
                        imgUrl: 'https:' + appConfig.imgUrl + shareInfo.imgUrl,
                        success: function () {
                            // 用户确认分享后执行的回调函数
                            // console.log(1111111111111111111);
                            $http({
                                method: "GET",
                                url: appConfig.apiPath + '/phoneLottery/shareLottery',
                                params: {
                                    'id': $location.search().id,
                                    'userId': shareInfo.userId,
                                    'type': 'LOTTERY',
                                    'goal': 'CIRCLE_FRIENDS',
                                    'shareUrl': finalLing,
                                },
                                headers: {
                                    // 'Authorization': 'Bearer ' + store.get(appConfig.token),
                                }
                            }).then(function (resp) {
                                // console.log(resp);
                                $scope.maskHide();
                                $scope.pageChange();
                            }, function () {
                                $scope.maskHide();
                            });
                        },
                        cancel: function () {
                            // 用户取消分享后执行的回调函数
                            // alertService.msgAlert('exclamation-circle', "取消分享");
                        }
                    };

                    wxService.init().then(function (data) {
                        if (data) {
                            wxService.shareRing(curConf);
                            wxService.shareFriend(curConf);
                        }
                    });


                    // wxService.initShareOnStart(curConf);
                }
            };
            //  请求引导底部图片
            $http({
                method: 'GET',
                url: appConfig.apiPath + '/phoneLottery/getImg',
                params: {
                    id: $location.search().id,
                },
                headers: {}
            }).then(function (resp) {
                $scope.loginImg = appConfig.imgUrl + resp.data.loginImg;
                $scope.qRCodeImg = appConfig.imgUrl + resp.data.qRCodeImg;
                // $scope.shareWX(resp.data.shareInfo);
            });

            $scope.pageChange = function () {
                $http({
                    method: "GET",
                    url: appConfig.apiPath + '/phoneLottery/getInfo',
                    headers: {},
                    params: {
                        'id': $location.search().id,
                        'phone': $location.search().phone,
                    },
                }).then(function (resp) {
                    console.log(resp.data);
                    $scope.barcode = appConfig.imgUrl + resp.data.barcode;
                    if (resp.data.code == 'ERROR') {
                        console.log(resp.data.code);
                        $scope.error = true;
                        return;
                    }
                    if (resp.data.isValid == '该吊牌已被使用') {
                        $scope.isUsed = true;
                    }
                    $scope.data = resp.data;
                    $scope.data.img = appConfig.imgUrl + resp.data.dialImg;
                    $scope.login = true;
                    $scope.shareInfo = resp.data.shareInfo;
                    $scope.shareWX($scope.shareInfo);
                    //判断是否已经抽过奖从而确定可以抽奖次数    先固定死一次
                    if ($scope.data.freeCount == 0) {
                        $scope.lottery.number = 0;
                        $scope.receivePrizes = true;
                    } else {
                        $scope.lottery.number = $scope.data.freeCount;
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
                        //没有次数不发送请求
                        // if ($scope.lottery.number == 0) {
                        //     $scope.dialogNoNumber("亲,您的机会已经用完了哦~");
                        //     // return;
                        // }
                        $scope.clickBtn = false;
                        $http({
                            method: "GET",
                            url: appConfig.apiPath + '/phoneLottery/winLottery',
                            params: {
                                'id': $location.search().id,
                                'userId': $scope.data.userId,
                            },
                            headers: {
                                // 'Authorization': 'Bearer ' + store.get(appConfig.token),
                            }
                        }).then(function (resp) {
                            //后台返回的抽奖结果
                            $scope.lottery.specificPrizes = resp.data;
                            $scope.lottery.img = appConfig.imgUrl + resp.data.awardImg;
                            //注意这里的数字不能超出奖项数
                            $scope.newdraw.goto(parseInt(resp.data.seqNum) + 1);
                            $scope.receivePrizes = true;

                            console.log('resp2', resp.data)

                            if (resp.data.code == ERROR) {
                                $scope.clickBtn = true;
                                console.log('resp1', resp.data)
                                $scope.dialogNoNumber(resp.data.data);
                            }

                        }, function (resp) {
                            $scope.clickBtn = true;
                            console.log('resp1', resp.data.rawMsg.data)
                            $scope.dialogNoNumber(resp.data.rawMsg.data);
                        });
                    };


                }, function (error) {
                    // $scope.error = true;
                    // console.log(111111111)
                    // $scope.login = true;
                });
            };
            $scope.pageChange();
            function callbackB(ind) {
                $scope.clickBtn = true;
                $scope.dialogShow($scope.lottery.specificPrizes);
            }


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

            $scope.goShare = function () {
                $scope.maskShow();
            };


            //抽奖结束弹窗
            $scope.dialogShow = function (ind) {
                $mdDialog.show({
                    templateUrl: 'views/main/lottery/dialog.html',
                    parent: angular.element(document.body).find('#qh-lottery-wap-front'),
                    // targetEvent: ev,
                    clickOutsideToClose: true,
                    fullscreen: false,
                    controller: ['$scope', '$mdDialog', 'appConfig', function ($scope, $mdDialog, appConfig) {
                        var vm = this;
                        vm.ind = ind;

                        if (wxService.isInWx()) {
                            // console.log('isInWx,isInWx,isInWx,isInWx,isInWx,isInWx,isInWx');
                            vm.flog = true;
                        } else {
                            // console.log('noisInWx,noisInWx,noisInWx,noisInWx,noisInWx,noisInWx,noisInWx');
                            vm.flog = false;
                        }
                        // console.log(vm.ind.awardImg);
                        // console.log(appConfig.imgUrl);
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
                        $scope.WXfollow.flog = true;
                    } else {
                        $scope.maskShow();
                    }
                }, function (answer) {
                    $scope.receivePrizes = true;
                });
            };


            //游戏规则弹窗
            $scope.showGameRules = function (ev) {
                $mdDialog.show({
                    templateUrl: 'views/main/lottery/lotteryRules.html',
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
        } else {
        }


        //没次数弹窗
        $scope.dialogNoNumber = function (ind) {
            $mdDialog.show({
                templateUrl: 'views/main/lottery/noNumberDialog.html',
                parent: angular.element(document.body).find('#qh-lottery-wap-front'),
                // targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: false,
                controller: ['$scope', '$mdDialog', 'appConfig', function ($scope, $mdDialog, appConfig) {
                    var vm = this;
                    vm.ind = ind;
                    // console.log(vm.ind.awardImg);
                    // console.log(appConfig.imgUrl);
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

        //遮罩打开与关闭
        $scope.mask = false;
        $scope.maskShow = function () {
            $scope.mask = true;
        };
        $scope.maskHide = function () {
            $scope.mask = false;
        };


    }
})();



