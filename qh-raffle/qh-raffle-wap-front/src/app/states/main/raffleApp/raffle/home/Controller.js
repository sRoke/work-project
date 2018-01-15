import conf from "../../../../../conf";
// import PhotoClip from 'photoclip';
import store from "store";
import URI from "urijs";
import './awardRotate';
var $scope,
    $http,
    $state,
    $templateCache,
    loginService,
    $rootScope,
    $stateParams,
    $location,
    $window,
    $httpParamSerializer,
    $timeout,
    wxService,
    alertService;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$templateCache,
                _loginService,
                _$rootScope,
                _$stateParams,
                _$location,
                _$window,
                _$httpParamSerializer,
                _$timeout,
                _wxService,
                _Upload) {
        $scope = _$scope;
        $http = _$http;
        $rootScope = _$rootScope;
        $state = _$state;
        loginService = _loginService;
        $templateCache = _$templateCache;
        $timeout = _$timeout;
        alertService = _Upload;
        wxService = _wxService;
        $location = _$location;
        $window = _$window;
        $httpParamSerializer = _$httpParamSerializer;
        $stateParams = _$stateParams;
        $scope.raffleAppId = $stateParams.raffleAppId;
        $scope.raffleId = $stateParams.raffleId;
        /////////////////////////////////
        $scope.aaaa=false;
        $scope.newLogin = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.raffleAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    store.set('wxMpAppId', resp.data.wxMpId);
                    $http({
                        method: "GET",
                        url: conf.wx4jPath + "/wxCom/" + $rootScope.wxComAppId + "/mp/" + $rootScope.wxMpAppId + "/user/auth/url",
                        params: {
                            // wxMpAppId: $scope.wxMpAppId,
                            redirectUri: createBackUrl(),
                            scopes: ["snsapi_base", "snsapi_userinfo"],
                            scan: !wxService.isInWx(),
                        }
                    }).then(function (resp) {
                        $scope.url = resp.data.data;
                        console.log("SUCCESS - ", resp);
                        $window.location.href = $scope.url;
                    }, function (err) {
                        // alert("3333333ERROR" + JSON.stringify(err));
                        console.log("ERROR", err)
                    });
                });
            }, function (resp) {
                //error
            });
        };

        // console.log("当前URL是", $location.absUrl());
        var uri = new URI($location.absUrl());
        var uriSearch = uri.search(true);
        // console.log("url.search(true) = ", uriSearch);
        var fragmentUri = new URI(uri.fragment());
        var fragmentUriSearch = fragmentUri.search(true);
        // console.log("fragmentUri.search(true) = ", fragmentUriSearch);


        // 在当前URL的基础上，追加额外参数 "fromWx"
        function createBackUrl() {
            var url2 = uri.clone();
            var fragmentUri2 = new URI(url2.fragment());
            var fragmentUriSearch2 = fragmentUri.search(true);

            fragmentUriSearch2.fromWx = true;
            fragmentUri2.search(fragmentUriSearch2);
            // TODO 删除 hash 中查询参数（code,state）。防止多次从微信返回后，有多个 code，state 参数。
            url2.fragment(fragmentUri2.toString());
            return url2.toString();
        }

        // 检查是否是从微信回来的，如果是，则应该执行 调用 后台API，完成登录。
        $scope.login = function () {
            if (!(uriSearch.fromWx) && !(fragmentUriSearch.fromWx)) {
                console.log("并不是从微信服务器返回的，无法登录");
                $scope.newLogin();
                return;
            }
            // 提取 参数 中的 code, state
            let code = uriSearch.code
                ? uriSearch.code
                : fragmentUriSearch.code;

            let state = uriSearch.state
                ? uriSearch.state
                : fragmentUriSearch.state;

            // 调用后台API，用 code 换取 access token，并尝试登录
            // 注意：可能无法登录——尚未绑定手机号，即还要相应的用户
            // 只有当手机号绑定之后，才会创建用户。

            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": $stateParams.raffleAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;

                    $http({
                        method: "POST",
                        url: conf.oauthPath + "/api/login/wxComMp",
                        data: {
                            wxComAppId: $rootScope.wxComAppId,
                            wxMpAppId: $rootScope.wxMpAppId,
                            code: code,
                            state: state
                        },
                        transformRequest: $httpParamSerializer,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }

                    }).then(function (resp) {
                        console.log('resp1', resp);
                        store.set('openId', resp.data.data);
                        var uri3 = new URI($location.absUrl());
                        uri3.removeSearch("code");
                        uri3.removeSearch("state");
                        uri3.removeSearch("appid");
                        uri3.removeSearch("fromWx");
                       // console.log(' uri.href()===', uri3.href());
                        $window.location.href =uri3.href().replace('fromWx=true', "");

                        //location.reload();
                        // alert('查看控制台')
                    }, function (err) {
                        if (err.data.status == 10001) {
                            // console.log('url=-=', $location.absUrl().replace('/login/wxMp','/user/bindWx'));
                            $state.go("main.reg.phone", {
                                backUrl: $location.absUrl().replace('/login/wxComMp', '/user/bindWx'),
                            });
                        }
                    });


                });
            }, function (resp) {
                //error
            });
        };

        $scope.getInfo = function () {
            $http({
                method: "GET",    ////raffleApp/{raffleAppId}/wap/raffle/{id}  GET
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/" + $scope.raffleId,
                params: {
                    openId: store.get('openId'),
                },
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": $scope.raffleAppId
                },
            }).then(function (resp) {
                console.log('resp', resp);
                $scope.items = resp.data.data;
                $scope.lottery = resp.data.data.awards;
                $scope.shareTitle = resp.data.data.shareTitle;
                $scope.shareDesp = resp.data.data.shareDesp;
                $scope.imgUrl= resp.data.data.dialImg;
                $scope.cshLottery();
                $scope.wxCsh();
                // $timeout(function () {
                //     $scope.drawRouletteWheel();
                // }, 300);

                var tplUrl = "rule.html";
                $scope.tplUrl = tplUrl;
                $templateCache.put(tplUrl, $scope.items.rule);
                if($scope.items.desp){
                    var tplUrl1 = "desp.html";
                    $scope.tplUrl1 = tplUrl1;
                    $templateCache.put(tplUrl1, $scope.items.desp);
                }

            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //获取奖品列表  /raffleApp/{raffleAppId}/raffle/{raffleId}wap/record/list GET openId
        $scope.lotteryList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId +"/raffle/"+$scope.raffleId+"/wap/record/list",
                params: {
                    openId: store.get('openId'),
                },
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": $scope.raffleAppId
                },
            }).then(function (resp) {
                console.log('respadd----list', resp);
                $scope.recordList=resp.data.data;
            }, function (resp) {
                //error
            })
        };
        if (!store.get('openId')) {
            $scope.login();
        } else {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "bargainApp-Id": $stateParams.raffleAppId
                },
            }).then(function (resp) {
                $rootScope.brandAppId = resp.data.data;
                $http({
                    method: "GET",
                    url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                    headers: {
                        "brandApp-Id": $rootScope.brandAppId
                    },
                    data: {}
                }).success(function (resp) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    if(store.get('wxMpAppId') == resp.data.wxMpId){
                        $scope.getInfo();
                        $scope.lotteryList();
                        $scope.aaaa = true;
                    }else{
                        store.remove('wxMpAppId');
                        store.remove('openId');
                        $scope.login();
                    }
                })
            })
            // $http({
            //     method: "GET",        // /raffleApp/{raffleAppId}/wap/raffle/judge GET openId
            //     url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/judge",
            //     params: {
            //         openId: store.get('openId'),
            //     },
            //     headers: {
            //         //'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "raffleApp-Id": $scope.raffleAppId
            //     },
            // }).then(function (resp) {
            //     console.log('resp---openId', resp);
            //     if(resp.data.data){
            //         $scope.getInfo();
            //         $scope.lotteryList();
            //         $scope.aaaa=true;
            //     }else{
            //         $scope.login();
            //     }
            // }, function (resp) {
            //     //error
            // });

        }


        var turnplate = {
            restaraunts: [],				//大转盘奖品名称
            colors: [],					//大转盘奖品区块对应背景颜色
            outsideRadius: 114,			//大转盘外圆的半径
            textRadius: 90,				//大转盘奖品位置距离圆心的距离
            insideRadius: 0,			//大转盘内圆的半径
            startAngle: 0,				//开始角度

            bRotate: false				//false:停止;ture:旋转
        };

        $scope.cshLottery = function () {
            console.log('csh------lottery');
            turnplate.restaraunts = [];//清空，防止分享过后调取getInfo时重复添加一个奖品
            // $(document).ready(function(){
            //动态添加大转盘的奖品与奖品区域背景颜色
            for (var i = 0; i < $scope.lottery.length; i++) {
                turnplate.restaraunts.push($scope.lottery[i].name);
            }
            // turnplate.restaraunts = ["碧根果一袋", "年货红包", "水果拼盘", "2元现金红包", "夏威夷果一袋"];
            turnplate.colors = ["#fefe9e", "#fee394", "#fff3c9", "#fefe9e", "#fee394", "#fff3c9",];



            var rotateTimeOut = function () {
                $('#wheelcanvas').rotate({
                    angle: 0,
                    animateTo: 2160,
                    duration: 8000,
                    callback: function () {
                        alert('网络超时，请检查您的网络设置！');
                    }
                });
            };

            //旋转转盘 item:奖品位置; txt：提示语;
            var rotateFn = function (item) {
                var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
                console.log('angles', angles);
                if (angles < 270) {
                    angles = 270 - angles;
                } else {
                    angles = 360 - angles + 270;
                }
                $('#wheelcanvas').stopRotate();
                $('#wheelcanvas').rotate({
                    angle: 0,
                    animateTo: angles + 1800,
                    duration: 8000,
                    callback: function () {	//回调
                        turnplate.bRotate = false;
                        $scope.getInfo();
                        $scope.lotteryList();
                        $scope.backdrop = true;
                        $scope.lotteryShow = true;
                        $scope.pointerClick=true;// 避免未执行完重复点击
                    }
                });
            };
//点击抽奖   当
            $scope.pointerClick=true;// 避免未执行完重复点击
            // $('.pointer').click(function () {
            $scope.pointer=function () {
                if(!$scope.pointerClick){
                    return
                };
                $scope.pointerClick=false;
                if (!loginService.getAccessToken()) {    //判断是否登录
                    loginService.loginCtl(true, $location.absUrl());
                }
                if (loginService.getAccessToken()) {
                    //$scope.items.canshare    //分享是否还会加抽奖机会
                    //$scope.items.surplus   //可抽奖次数
                    if ($scope.items.surplus <= 0) {
                        if ($scope.items.canshare) {
                            $scope.pointerClick=true;// 避免未执行完重复点击
                            return alertService.msgAlert('exclamation-circle', '分享好友，可再抽一次！');
                        } else {
                            $scope.pointerClick=true;// 避免未执行完重复点击
                            return alertService.msgAlert('exclamation-circle', '您的抽奖机会已用完！');
                        }
                    } else {
                        console.log('resp1111111111', turnplate.restaraunts);
                        // /raffleApp/{raffleAppId}/wap/raffle/{id}/lottery  GET
                        $http({
                            method: "GET",    ////raffleApp/{raffleAppId}/wap/raffle/{id}  GET
                            url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/" + $scope.raffleId + '/lottery',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "raffleApp-Id": $scope.raffleAppId
                            },
                        }).then(function (resp) {
                            console.log('resp1111111111',resp);
                            if(resp.data.status=='200'){
                                $scope.drawArray = resp.data.data;
                                for (var i = 0; i < turnplate.restaraunts.length; i++) {
                                    if (turnplate.restaraunts[i] === $scope.drawArray.name) {
                                        console.log(666666666, i);
                                        if (turnplate.bRotate)return;
                                        turnplate.bRotate = !turnplate.bRotate;
                                        //获取随机数(奖品个数范围内)
                                        var item = rnd(1, turnplate.restaraunts.length);
                                        //奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
                                        rotateFn(i+1);
                                        break;
                                    }
                                }
                            }else{
                                $scope.pointerClick=true;// 避免未执行完重复点击
                                return alertService.msgAlert('exclamation-circle',resp.data.message);
                            }


                        }, function (resp) {
                            //error
                            $scope.pointerClick=true;// 避免未执行完重复点击
                            return alertService.msgAlert('exclamation-circle',resp.data.message);
                        });
                    }
                }


                /* switch (item) {
                 case 1:
                 rotateFn(252, turnplate.restaraunts[0]);
                 break;
                 case 2:
                 rotateFn(216, turnplate.restaraunts[1]);
                 break;
                 case 3:
                 rotateFn(180, turnplate.restaraunts[2]);
                 break;
                 case 4:
                 rotateFn(144, turnplate.restaraunts[3]);
                 break;
                 case 5:
                 rotateFn(108, turnplate.restaraunts[4]);
                 break;
                 case 6:
                 rotateFn(72, turnplate.restaraunts[5]);
                 break;
                 case 7:
                 rotateFn(36, turnplate.restaraunts[6]);
                 break;
                 case 8:
                 rotateFn(360, turnplate.restaraunts[7]);
                 break;
                 case 9:
                 rotateFn(324, turnplate.restaraunts[8]);
                 break;
                 case 10:
                 rotateFn(288, turnplate.restaraunts[9]);
                 break;
                 } */
                //console.log(item);
            };
            // });
        };
        function rnd(n, m) {
            var random = Math.floor(Math.random() * (m - n + 1) + n);
            return random;
        }

        //页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
        // window.onload=function(){
        //     console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaa',$scope.lottery);
        //     // drawRouletteWheel();
        // };

        $scope.drawRouletteWheel = function () {
            console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaa', $scope.lottery);
            var canvas = document.getElementById("wheelcanvas");
            if (canvas.getContext) {
                //根据奖品个数计算圆周角度
                var arc = Math.PI / (turnplate.restaraunts.length / 2);
                var ctx = canvas.getContext("2d");
                //在给定矩形内清空一个矩形
                ctx.clearRect(0, 0, 280, 280);
                //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
                ctx.strokeStyle = "#FFBE04";
                //font 属性设置或返回画布上文本内容的当前字体属性
                ctx.font = 'bold 14px Microsoft YaHei';
                for (var i = 0; i < turnplate.restaraunts.length; i++) {
                    var angle = turnplate.startAngle + i * arc;
                    ctx.fillStyle = turnplate.colors[i];
                    ctx.beginPath();
                    //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
                    ctx.arc(140, 140, turnplate.outsideRadius, angle, angle + arc, false);
                    ctx.arc(140, 140, turnplate.insideRadius, angle + arc, angle, true);
                    ctx.stroke();
                    ctx.fill();
                    //锁画布(为了保存之前的画布状态)
                    ctx.save();

                    //改变画布文字颜色
                    var b = i + 2;
                    if (b % 2) {
                        ctx.fillStyle = "#FFFFFF";
                    } else {
                        ctx.fillStyle = "#E5302F";
                    }
                    ;

                    //----绘制奖品开始----


                    var text = turnplate.restaraunts[i];
                    var line_height = 16;
                    //translate方法重新映射画布上的 (0,0) 位置
                    ctx.translate(140 + Math.cos(angle + arc / 2) * turnplate.textRadius, 140 + Math.sin(angle + arc / 2) * turnplate.textRadius);

                    //rotate方法旋转当前的绘图
                    ctx.rotate(angle + arc / 2 + Math.PI / 2);

                    /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
                    if (text.indexOf("盘") > 0) {//判断字符进行换行
                        var texts = text.split("盘");
                        for (var j = 0; j < texts.length; j++) {
                            ctx.font = j == 0 ? 'bold 14px Microsoft YaHei' : 'bold 14px Microsoft YaHei';
                            if (j == 0) {
                                ctx.fillText(texts[j] + "盘", -ctx.measureText(texts[j] + "盘").width / 2, j * line_height);
                            } else {
                                ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height * 1.2); //调整行间距
                            }
                        }
                    } else if (text.indexOf("盘") == -1 && text.length > 8) {//奖品名称长度超过一定范围
                        text = text.substring(0, 8) + "||" + text.substring(8);
                        var texts = text.split("||");
                        for (var j = 0; j < texts.length; j++) {
                            ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                        }
                    } else {

                        //在画布上绘制填色的文本。文本的默认颜色是黑色

                        //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
                        ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
                    }

                    //添加对应图标

                    if (text.indexOf(turnplate.restaraunts[0]) >= 0) {
                        var img = document.getElementById("1-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[1]) >= 0) {
                        var img = document.getElementById("2-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[2]) >= 0) {
                        var img = document.getElementById("3-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[3]) >= 0) {
                        console.log('test', turnplate.restaraunts);
                        var img = document.getElementById("4-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[4]) >= 0) {
                        var img = document.getElementById("5-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[5]) >= 0) {
                        var img = document.getElementById("6-img");
                        img.onload = function () {
                            ctx.drawImage(img, -10, 10, 35, 35);
                        };
                        ctx.drawImage(img, -10, 10, 35, 35);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[6]) >= 0) {
                        var img = document.getElementById("7-img");
                        img.onload = function () {
                            ctx.drawImage(img, -30, 20);
                        };
                        ctx.drawImage(img, -30, 20);
                    }
                    ;

                    if (text.indexOf(turnplate.restaraunts[7]) >= 0) {
                        var img = document.getElementById("8-img");
                        img.onload = function () {
                            ctx.drawImage(img, -35, 20);
                        };
                        ctx.drawImage(img, -35, 20);
                    }
                    ;
                    //把当前画布返回（调整）到上一个save()状态之前
                    ctx.restore();
                    //----绘制奖品结束----
                }
            }
        };



        $scope.imgShareShow = false;
        $scope.imgEwmShow = false;
        // /raffleApp/{raffleAppId}/raffle/{raffleId}/isFollow  Get   userId
        // $scope.isClick=true;



        $scope.imgWrapShow = false;
        $scope.shareUrl = conf.sharePath + "#/raffleApp/" + $scope.raffleAppId + "/raffle/" + $scope.raffleId + "/home";
        $scope.imgShow = function () {
            $scope.imgWrapShow = !$scope.imgWrapShow;
            if ($scope.imgWrapShow == true) {
                $scope.dialogShow = false;
                $scope.imgShareShow = false;
                $scope.backdrop = true;
            }
        };
        $scope.imgShow2 = function () {
            $scope.imgWrapShow = !$scope.imgWrapShow;
            if ($scope.imgWrapShow == true) {
                $scope.dialogShow = false;
                $scope.imgShareShow = false;
                $scope.backdrop2 = true;
            }
        };
        $scope.addLotteryNum = function () {
            $http({
                method: "GET",   ///raffleApp/{raffleAppId}/wap/raffle/{id}/addTickets  GET openId
                url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/" + $scope.raffleId + "/addTickets",
                params: {
                    openId: store.get('openId'),
                },
                headers: {
                    //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "raffleApp-Id": $scope.raffleAppId
                },
            }).then(function (resp) {
                console.log('respadd', resp);
                $scope.getInfo();
            }, function (resp) {
                //error
            });
        };
        //分享
        $scope.wxCsh = function () {
            console.log('share', $scope.shareTitle, $scope.imgUrl);
            if (wxService.isInWx()) {
                wxService.init().then(function (data) {
                    //wx分享
                    if (wxService.isInWx()) {
                        var confWx = {
                            title: $scope.shareTitle,
                            desc: $scope.shareDesp,
                            link: $scope.shareUrl,
                            imgUrl: $scope.imgUrl,
                            success: function () {
                                $scope.addLotteryNum();
                            },
                            cancel: function () {

                            }
                        };
                        if ($scope.wxInit) {
                            wxService.shareRing(confWx);
                            wxService.shareFriend(confWx);
                        } else {
                            wxService.init().then(function (data) {
                                if (data) {
                                    $scope.wxInit = true;
                                    wxService.shareRing(confWx);
                                    wxService.shareFriend(confWx);
                                }
                            });
                        }
                    }
                    ;
                    if (data) {
                        $scope.wxInit = true;
                    }
                    console.log('~~~~~~~~~~~~~~~', data);
                })
            }
            ;
        };
        $scope.shareFiend = function () {
            $scope.imgShareShow = !$scope.imgShareShow;
            if ($scope.imgShareShow == true) {
                $scope.dialogShow = false;
                $scope.backdrop = true;
            }
        };
        //打开游戏规则
        $scope.openRule = function () {
            $scope.backdrop = true;
            $scope.ruleShow = true;
        };
        //查看我的奖品
        $scope.openMedia = function () {
            if($scope.recordList.length==0){
                return alertService.msgAlert('exclamation-circle', '亲，您还没有参与抽奖哦！');
            }
            $scope.backdrop = true;
            $scope.mediaShow = true;
            $scope.lotteryShow1 = true;
        };
        //查看奖品详细
        $scope.lotteryDetail = function (recordId) {
            $scope.lotteryShow1 = !$scope.lotteryShow1;
            // /raffleApp/{raffleAppId}/raffle/{raffleId}/wap/record/{id}/detail   GET openId
            if(recordId){
                $http({
                    method: "GET",   //判断是否关注   未关注，--关注，关注后---推送领取奖品，shareurl为领取奖品路径
                    url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId +"/raffle/"+ $scope.raffleId +"/wap/record/"+recordId+"/detail",
                    headers: {
                        "raffleApp-Id": $scope.raffleAppId
                    },
                    params: {
                        openId: store.get('openId'),
                    }
                }).then(function (resp) {
                    console.log('奖品领取情况',resp);
                    $scope.awardBuy=resp.data.data;
                }, function (resp) {
                    //error
                    alertService.msgAlert('exclamation-circle', resp.data.message);
                });
            }

        };
        //领取奖品跳转
        $scope.getLottery = function (recordId) {
            // /raffleApp/{raffleAppId}/wap/raffle/{raffleId}/isFollow/{recordId}
            //$state.go("main.raffleApp.raffle.confirmOrder", {orderId: recordId}, {reload: true});
            if($scope.items.mustFollow){
                $http({
                    method: "GET",   //判断是否关注   未关注，--关注，关注后---推送领取奖品，shareurl为领取奖品路径
                    url: conf.apiPath + "/raffleApp/" + $scope.raffleAppId + "/wap/raffle/" + $scope.raffleId + "/isFollow/"+recordId,
                    headers: {
                        "raffleApp-Id": $scope.raffleAppId
                    },
                    params: {
                        openId: store.get('openId'),
                        shareUrl: conf.sharePath + "#/raffleApp/" + $scope.raffleAppId + "/raffle/" + $scope.raffleId + "/confirmOrder?orderId="+recordId,
                    }
                }).then(function (resp) {
                    console.log('判断是否关注',resp);
                    if(resp.data.status=='200'){
                        //已关注
                        $state.go("main.raffleApp.raffle.confirmOrder", {orderId: recordId}, {reload: true});
                    }else{
                        //未关注
                        $scope.lotteryShow=false;
                        $scope.mediaShow=false;
                        $scope.ewmUrl=resp.data.data;
                        $scope.imgEwmShow=true;
                    }
                }, function (resp) {
                    //error
                    alertService.msgAlert('exclamation-circle', resp.data.message);
                });
            }else{
                $state.go("main.raffleApp.raffle.confirmOrder", {orderId: recordId}, {reload: true});
            }

        };
        $scope.closeDialog = function () {
            $scope.imgShareShow = false;   //砍价
            $scope.imgWrapShow = false;   //分享背景
            $scope.dialogShow = false;
            $scope.backdrop = false;   //背景蒙层
            $scope.backdrop2 = false;   //背景蒙层2
            $scope.imgEwmShow = false;  //识别二维码
            $scope.ruleShow = false;    //游戏规则
            $scope.lotteryShow = false;    //中奖弹窗
            $scope.mediaShow = false;  //查看奖品弹窗
            //$scope.getInfo();
        };


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$templateCache',
    'loginService',
    '$rootScope',
    '$stateParams',
    '$location',
    '$window',
    '$httpParamSerializer',
    '$timeout',
    'wxService',
    'alertService'
];

export default Controller ;
