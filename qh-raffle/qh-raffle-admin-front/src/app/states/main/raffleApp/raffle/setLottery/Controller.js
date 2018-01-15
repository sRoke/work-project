import conf from "../../../../../conf";
import weui from 'weui.js';
import angular from 'angular';
import PhotoClip from 'photoclip';
import html2canvas from 'html2canvas';
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    Upload,
    wxService,
    $interval,
    alertService,
    $rootScope,
    $filter,
    $timeout,
    $templateCache;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _Upload,
                _wxService,
                _$interval,
                _alertService,
                _$rootScope,
                _$filter,
                _$timeout,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        alertService = _alertService;
        $interval = _$interval;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        wxService = _wxService;
        $stateParams = _$stateParams;
        $rootScope = _$rootScope;
        $filter = _$filter;
        $timeout = _$timeout;
        $scope.raffleAppId = $stateParams.raffleAppId;
        $scope.from = $stateParams.from;
        $scope.id = $stateParams.id;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());


        // var idArry = '';
        //
        // for(var i = 0 ;i<=index;i++){
        //     idArry += ' #file_' + i ;
        // }
        console.log('start', $rootScope.lotteryList);
        if ($scope.from == 'view') {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                //console.log('resprespresprespresp', resp);
                $scope.lotteryList = resp.data.data.awards;
            }, function () {

            });
        }
        if ($scope.from == 'edit') {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('123-test', $rootScope.lotteryList);
                if ($rootScope.lotteryList) {
                    $rootScope.lotteryList = $rootScope.lotteryList;
                } else {
                    $rootScope.lotteryList = resp.data.data.awards;
                }
            }, function () {

            });
        }


        //再次点击图片时单独初始化当前fun（）函数
        $scope.aa = function (num) {
            $scope.fun(num);
        };

        $scope.choosePhote = false;
        // 图片裁剪

        $scope.fun = function (index) {
            $timeout(function () {
                var pc = new PhotoClip('#clipArea', {
                    size: [260, 260],
                    outputSize: 640,
                    // adaptive: ['70','40'],
                    file: '#file_' + index,
                    view: '#view',
                    ok: '#clipBtn',
                    style: {
                        maskColor: 'rgba(0,0,0,0.7)',
                        // jpgFillColor:''
                    },
                    loadStart: function () {
                        console.log('开始读取照片', index);
                    },
                    loadComplete: function () {
                        console.log('照片读取完成', $scope);
                        $scope.choosePhote = true;
                        $scope.$apply();
                    },
                    done: function (dataURL) {
                        console.log('base64裁剪完成,正在上传');
                        $scope.saveImg(dataURL, index);
                    },
                    fail: function (msg) {
                        // alert(msg);
                    }
                });
            }, 0);
        }

        $scope.saveImg = function (dataUrl, index) {
            $http({
                method: "POST",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
                data: {
                    base64DataUrl: dataUrl
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.dataUrl = resp.data.data;
                    $scope.getImg($scope.dataUrl, index);
                    console.log(resp.data.data);

                }, function () {
                    //error
                }
            );
        };
        $scope.getImg = function (id, index) {
            $http({
                method: "GET",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    console.log(resp.data.data);
                    // $scope.imgs = resp.data.data.cdnUrls[0].url ;
                    $scope.lotteryList[index].picture = resp.data.data.cdnUrls[0].url;
                    $scope.dataUrl = null;
                    $scope.choosePhote = false;
                }, function () {
                    //error
                }
            );
        };
        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        };


        //             抽奖活动奖品 awards
// （奖品名称 name 奖品数量 num 奖品中奖概率 chance 奖品图片 picture 奖品类型
// awardType("VIRTUAL", "虚拟"  "MATERIAL", "实物")
// ）


        $scope.lottery = {};
        $scope.lottery = {
            picture: '',
            name: '',
            num: '',
            chance: '',
            awardType: '',
        };
        // if($scope.from=='edit'){
        //     if($rootScope.lotteryList){
        //         $rootScope.lotteryList=$rootScope.lotteryList;
        //        // $scope.raffleList=$rootScope.lotteryList;
        //         $scope.fun($rootScope.lotteryList.length-1);
        //     }else{
        //         $rootScope.lotteryList=[];
        //         $rootScope.lotteryList.push(angular.copy($scope.lottery));
        //         $rootScope.lotteryList[0].awardType='MATERIAL';
        //         //遍历循环   奖品默认为是实物
        //         for(var i=0;i< $rootScope.lotteryList.length;i++){
        //             $scope.fun(i);
        //         }
        //     }
        // }
        if ($scope.from == 'add') {
            console.log('$rootScope.lotteryList', $rootScope.lotteryList);
            if ($rootScope.lotteryList) {
                if ($rootScope.lotteryList.length > 0) {
                    console.log(1111111111)
                    $rootScope.lotteryList = $rootScope.lotteryList;
                } else {
                    $rootScope.lotteryList = [];
                    $rootScope.lotteryList.push(angular.copy($scope.lottery));
                    $rootScope.lotteryList[$rootScope.lotteryList.length - 1].awardType = 'MATERIAL';
                }
            } else {
                $rootScope.lotteryList = [];
                $rootScope.lotteryList.push(angular.copy($scope.lottery));
                $rootScope.lotteryList[$rootScope.lotteryList.length - 1].awardType = 'MATERIAL';
            }
            // console.log('test-4560',$rootScope.lotteryList);
            for (var i = 0; i < $rootScope.lotteryList.length; i++) {
                $scope.fun(i);
            }

        }


        $scope.addLottery = function () {
            //非空验证
            for (var i = 0; i < $rootScope.lotteryList.length; i++) {
                var j = i + 1;
                if (!$rootScope.lotteryList[i].name) {
                    return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '名称');
                }
                if ($rootScope.lotteryList[i].name.length > 7) {
                    return alertService.msgAlert('exclamation-circle', '奖品' + j + '名称长度不得超过7');
                }
                if (!$rootScope.lotteryList[i].picture) {
                    return alertService.msgAlert('exclamation-circle', '请上传奖品' + j + '图片');
                }
                if (!$rootScope.lotteryList[i].num && $rootScope.lotteryList[i].num != 0) {
                    return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '数量');
                }
                var reg = /^\d+$/;
                if (!reg.test($rootScope.lotteryList[i].num)) {
                    return alertService.msgAlert('exclamation-circle', '请填写正确的奖品数量');
                }
                if (!$rootScope.lotteryList[i].chance) {
                    if ($rootScope.lotteryList[i].chance!= 0) {
                        return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '中奖概率');
                    }
                }
                var reg1 = /^\d+(?:.\d{1,2})?$/;
                if (!reg1.test($rootScope.lotteryList[i].chance) || $rootScope.lotteryList[i].chance > 100) {
                    return alertService.msgAlert('exclamation-circle', '请填写正确的中奖概率');
                }
                // var reg = /^(?:0|[1-9][0-9]?|100)$/;
                // if(!reg.test($rootScope.lotteryList[i].chance)){
                //     return alertService.msgAlert('exclamation-circle', '请填写正确的中奖概率');
                // }

            }
            $rootScope.lotteryList.push(angular.copy($scope.lottery));
            //遍历循环   奖品默认为是实物
            $rootScope.lotteryList[$rootScope.lotteryList.length - 1].awardType = 'MATERIAL';
            $scope.fun($rootScope.lotteryList.length - 1);
        };
        $scope.deleteLottery = function (index) {
            alertService.confirm(null, '删除奖品', '温馨提示').then(function (data) {
                if (data) {
                    $rootScope.lotteryList.splice(index, 1);
                }
            })

        };
        //点击改变实物虚拟
        $scope.changeAwardType = function (lottery, num) {
            if (num == 1) {
                lottery.awardType = 'MATERIAL';
            } else {
                lottery.awardType = 'VIRTUAL';
            }
        };
        $scope.backdrop = false;
        $scope.lotteryShow = false;
        $scope.cancelCanvas = function () {
            $scope.backdrop = false;
            $scope.lotteryShow = false;
        };
        //$scope.canvas = document.createElement("canvas");
        $scope.sureCanvas = function () {
            var mycanvas = document.getElementById("wheelcanvas");
            var mydataUrl = mycanvas.toDataURL("image");
            // $scope.ddd = ddd;
            // var newImg = document.getElementById("fff");
            // newImg.src = ddd;
            $scope.getImg1 = function (id) {
                $http({
                    method: "GET",
                    url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                    }
                }).then(function (resp) {
                        $rootScope.imgUrl = resp.data.data.cdnUrls[0].url;
                        console.log('imgUrl', $rootScope.imgUrl);
                        $rootScope.dataUrl1 = null;
                        // return
                        if ($rootScope.imgUrl) {
                            if ($scope.from == 'add') {
                                $state.go("main.raffleApp.raffle.raffleAdd", {form: 'text'}, {reload: true});
                            } else if ($scope.from == 'edit') {
                                $state.go("main.raffleApp.raffle.raffleEdit", {
                                    id: $scope.id,
                                    form: 'text'
                                }, {reload: true});
                            }
                        }

                    }, function () {
                        //error
                    }
                );
            };
            $scope.saveImg1 = function (mydataUrl) {
                $http({
                    method: "POST",
                    url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
                    data: {
                        base64DataUrl: mydataUrl
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                    }
                }).then(function (resp) {
                        $scope.dataUrl1 = resp.data.data;
                        $scope.getImg1($scope.dataUrl1);

                    }, function () {
                        //error
                    }
                );
            };
            if (mydataUrl) {
                $scope.saveImg1(mydataUrl);
            }

            // $scope.canvas.width = angular.element(document.getElementById('abc'))[0].clientWidth*2;
            // $scope.canvas.height = angular.element(document.getElementById('abc'))[0].clientHeight*2;
            // $scope.canvas.style.width = angular.element(document.getElementById('abc'))[0].clientWidth + "px";
            // $scope.canvas.style.height = angular.element(document.getElementById('abc'))[0].clientHeight + "px";
            // var context = $scope.canvas.getContext("2d");
            // //然后将画布缩放，将图像放大两倍画到画布上
            // context.scale(2,2);
            // var dataUrl1 = canvas.toDataURL("image/png", 1.0);
            // $scope.dataUrl1 = dataUrl1;
            // var newImg = document.createElement("img");
            // newImg.src = dataUrl1;
            // setTimeout(function () {
            //     html2canvas(document.getElementById('abc'), {
            //         //canvas:$scope.canvas,
            //         onrendered: function (canvas) {
            //             //var ctx=canvas.getContext("2d");
            //             //ctx.scale(2,2);
            //             console.log(canvas);
            //             var dataUrl1 = canvas.toDataURL("image/png", 1.0);
            //             $scope.dataUrl1 = dataUrl1;
            //             var newImg = document.createElement("img");
            //             newImg.src = dataUrl1;
            //             $scope.cancassuccess = true;
            //             $scope.saveImg1 = function (dataUrl1) {
            //                 $http({
            //                     method: "POST",
            //                     url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
            //                     data: {
            //                         base64DataUrl: dataUrl1
            //                     },
            //                     headers: {
            //                         'Authorization': 'Bearer ' + loginService.getAccessToken()
            //                     }
            //                 }).then(function (resp) {
            //                         $scope.dataUrl1 = resp.data.data;
            //                         $scope.getImg1($scope.dataUrl1);
            //
            //                     }, function () {
            //                         //error
            //                     }
            //                 );
            //             };
            //             $scope.saveImg1(dataUrl1);
            //             $scope.getImg1 = function (id) {
            //                 $http({
            //                     method: "GET",
            //                     url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
            //                     headers: {
            //                         'Authorization': 'Bearer ' + loginService.getAccessToken()
            //                     }
            //                 }).then(function (resp) {
            //                         $rootScope.imgUrl = resp.data.data.cdnUrls[0].url;
            //                         console.log('imgUrl', $rootScope.imgUrl);
            //                         alert($rootScope.imgUrl);
            //                         $scope.dataUrl1 = null;
            //                         // return
            //                         if($rootScope.imgUrl){
            //                             if($scope.from=='add'){
            //                                 $state.go("main.raffleApp.raffle.raffleAdd", {form:'text'}, {reload: true});
            //                             }else if($scope.from=='edit'){
            //                                 $state.go("main.raffleApp.raffle.raffleEdit", {id:$scope.id,form:'text'}, {reload: true});
            //                             }
            //                         }
            //
            //                     }, function () {
            //                         //error
            //                     }
            //                 );
            //             };
            //             $scope.$digest();
            //             // setTimeout(function () {
            //             //     $scope.popularityList($scope.dataUrl);
            //             // }, 100)
            //         },
            //         // width: 280,
            //         // height: 280,
            //         //background: '',
            //         logging: true,
            //         //allowTaint: false,
            //         useCORS:true,
            //         timeout: 50,
            //     });
            // }, 0)


        };


        //转盘参数配置
        var turnplate = {
            restaraunts: [],				//大转盘奖品名称
            colors: [],					//大转盘奖品区块对应背景颜色
            outsideRadius: 114,			//大转盘外圆的半径
            goodsimgArr: [],             //奖品图片页面标签
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
            for (var i = 0; i < $scope.raffleList.length; i++) {
                turnplate.restaraunts.push($scope.raffleList[i].name);
            }
            // turnplate.restaraunts = ["碧根果一袋", "年货红包", "水果拼盘", "2元现金红包", "夏威夷果一袋"];
            if ($scope.raffleList.length == 3) {
                turnplate.colors = ["#fefe9e", "#fee394", "#fff3c9",];
            } else if ($scope.raffleList.length == 4) {
                turnplate.colors = ["#fefe9e", "#fee394", "#fefe9e", "#fee394",];
            } else if ($scope.raffleList.length == 5) {
                turnplate.colors = ["#fefe9e", "#fee394", "#fff3c9", "#fefe9e", "#fee394", "#fff3c9",];
            } else if ($scope.raffleList.length == 6) {
                turnplate.colors = ["#fefe9e", "#fee394", "#fff3c9", "#fefe9e", "#fee394", "#fff3c9",];
            }


        };


        $scope.drawRouletteWheel = function () {
            console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaa', $scope.raffleList);
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
                ctx.font = '12px Microsoft YaHei';
                for (var i = 0; i < turnplate.restaraunts.length; i++) {
                    var angle = turnplate.startAngle + i * arc;
                    console.log('测试---i', i);
                    console.log('测试---arc', arc);
                    console.log('测试---angle', angle);
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
                        ctx.fillStyle = "#F69920";
                    } else {
                        ctx.fillStyle = "#F69920";
                    }
                    ;

                    //----绘制奖品开始----


                    var text = turnplate.restaraunts[i];
                    var line_height = 14;
                    //translate方法重新映射画布上的 (0,0) 位置
                    ctx.translate(140 + Math.cos(angle + arc / 2) * turnplate.textRadius, 140 + Math.sin(angle + arc / 2) * turnplate.textRadius);
                    console.log('测试---translate', 140 + Math.cos(angle + arc / 2) * turnplate.textRadius, 140 + Math.sin(angle + arc / 2) * turnplate.textRadius);
                    //rotate方法旋转当前的绘图
                    ctx.rotate(angle + arc / 2 + Math.PI / 2);
                    // console.log('测试---angle',angle + arc / 2 + Math.PI / 2);
                    /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/

                    if (text.indexOf("盘") > 0) {//判断字符进行换行
                        console.log('text---zoule222',);
                        var texts = text.split("盘");
                        for (var j = 0; j < texts.length; j++) {
                            ctx.font = j == 0 ? '12px Microsoft YaHei' : '12px Microsoft YaHei';
                            if (j == 0) {
                                ctx.fillText(texts[j] + "盘", -ctx.measureText(texts[j] + "盘").width / 2, j * line_height);
                            } else {
                                ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height * 1.2); //调整行间距
                            }
                        }
                    } else if (text.indexOf("盘") == -1 && text.length > 8) {//奖品名称长度超过一定范围
                        // console.log('text---zoule',);
                        // text = text.substring(0, 8);
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
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);

                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[1]) >= 0) {
                        let img = document.getElementById("2-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[2]) >= 0) {
                        let img = document.getElementById("3-img");
                        //img.src = "https://img.kingsilk.net/5a56dab5dc0e82000841ac40";
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[3]) >= 0) {
                        //console.log('test', turnplate.restaraunts);
                        let img = document.getElementById("4-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[4]) >= 0) {
                        let img = document.getElementById("5-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[5]) >= 0) {
                        let img = document.getElementById("6-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    if (text.indexOf(turnplate.restaraunts[6]) >= 0) {
                        let img = document.getElementById("7-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;

                    if (text.indexOf(turnplate.restaraunts[7]) >= 0) {
                        let img = document.getElementById("8-img");
                        img.onload = function () {
                            ctx.drawImage(img, -18, 8, 36, 36);
                        };
                        ctx.drawImage(img, -18, 8, 36, 36);
                    }
                    ;
                    //把当前画布返回（调整）到上一个save()状态之前
                    ctx.restore();

                    //----绘制奖品结束----
                }
                // $timeout(function () {
                //     var mycanvas = document.getElementById("wheelcanvas");
                //     var ddd = mycanvas.toDataURL("image/png", 1.0);
                //     $scope.ddd = ddd;
                //     alert($scope.ddd);
                //     var newImg = document.getElementById("fff");;
                //     newImg.src = ddd;
                // },1000)

            }
        };


        $scope.save = function () {
            //非空验证
            for (var i = 0; i < $rootScope.lotteryList.length; i++) {
                var j = i + 1;
                if (!$rootScope.lotteryList[i].name) {
                    return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '名称');
                }
                if ($rootScope.lotteryList[i].name.length > 7) {
                    return alertService.msgAlert('exclamation-circle', '奖品' + j + '名称长度不得超过7');
                }
                if (!$rootScope.lotteryList[i].picture) {
                    return alertService.msgAlert('exclamation-circle', '请上传奖品' + j + '图片');
                }
                if (!$rootScope.lotteryList[i].num && $rootScope.lotteryList[i].num != 0) {
                    return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '数量');
                }
                var reg = /^\d+$/;
                if (!reg.test($rootScope.lotteryList[i].num)) {
                    return alertService.msgAlert('exclamation-circle', '请填写正确的奖品数量');
                }
                if (!$rootScope.lotteryList[i].chance) {
                    if ($rootScope.lotteryList[i].chance!= 0) {
                        return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '中奖概率');
                    }
                    //return alertService.msgAlert('exclamation-circle', '请填写奖品' + j + '中奖概率');
                }
                var reg2 = /^\d+(?:.\d{1,2})?$/;
                if (!reg2.test($rootScope.lotteryList[i].chance) || $rootScope.lotteryList[i].chance > 100) {
                    return alertService.msgAlert('exclamation-circle', '请填写正确的中奖概率');
                }

            }
            if ($rootScope.lotteryList.length < 3) {
                return alertService.msgAlert('exclamation-circle', '最少应设置3个奖项')
            }
            console.log('end----', $rootScope.lotteryList);
            $scope.raffleList = $rootScope.lotteryList;


            $timeout(function () {
                $scope.cshLottery();
                $scope.drawRouletteWheel();
            }, 300)

            $scope.backdrop = true;
            $scope.lotteryShow = true;
            // if($scope.from=='add'){
            //     $state.go("main.raffleApp.raffle.raffleAdd", {form:'text'}, {reload: true});
            // }else if($scope.from=='edit'){
            //     $state.go("main.raffleApp.raffle.raffleEdit", {id:$scope.id,form:'text'}, {reload: true});
            // }

        };
        $scope.fallbackPage = function () {
            if ($scope.from == 'add') {
                alertService.confirm(null, '您还未保存?', '温馨提示').then(function (data) {
                    if (data) {
                        $rootScope.lotteryList = [];
                        $state.go("main.raffleApp.raffle.raffleAdd", {form: 'text'}, {reload: true});
                    }
                })

            } else if ($scope.from == 'view') {
                $state.go("main.raffleApp.raffle.raffleView", {id: $scope.id}, {reload: true});
            } else if ($scope.from == 'edit') {
                alertService.confirm(null, '您还未保存?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id,
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            $rootScope.lotteryList = resp.data.data.awards;
                            $state.go("main.raffleApp.raffle.raffleEdit", {
                                id: $scope.id,
                                form: 'text'
                            }, {reload: true});
                        }, function () {

                        });


                    }
                });

            }

        };


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$q',
    'Upload',
    'wxService',
    '$interval',
    'alertService',
    '$rootScope',
    '$filter',
    '$timeout',
    '$templateCache'
];

export default Controller ;