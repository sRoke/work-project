import conf from "../../../../conf"
import  "../../../../thirdJs/html2canvas.js"

var $scope,
    $rootScope,
    alertService,
    $http,
    authService,
    $state,
    $log,
    loginService,
    $httpParamSerializer,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$rootScope,
                _alertService,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _$httpParamSerializer,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $httpParamSerializer = _$httpParamSerializer;
        $log = _$log;
        $stateParams = _$stateParams;
        $rootScope = _$rootScope;
        alertService=_alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);

        $scope.id = $stateParams.id;

        $scope.pageChanged = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                $scope.data = response.data;
                $scope.message.realName = $scope.data.realName;
                $scope.message.prox = $scope.data.partnerType;
                var str = $scope.data.shopAddr;
                str = str.split(' ');//先按照空格分割成数组
                // str.pop();//删除数组最后一个元素
                // str = str.join(' ');//在拼接成字符串
                // setTimeout(function () {
                $scope.message.address = str[1]
            });
        }
        $scope.pageChanged();


        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/certificate/" + $scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                $scope.aaa = response.data;
            });
        }
        // $scope.getInfo();


        $scope.message = {};


        $scope.edit = true;

        $scope.generate = function () {
            $scope.edit = false;
        };

        Date.prototype.format = function (format) {
            var date = {
                "M+": this.getMonth() + 1,
                "d+": this.getDate(),
                "h+": this.getHours(),
                "m+": this.getMinutes(),
                "s+": this.getSeconds(),
                "q+": Math.floor((this.getMonth() + 3) / 3),
                "S+": this.getMilliseconds()
            };
            if (/(y+)/i.test(format)) {
                format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (var k in date) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1
                        ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
                }
            }
            return format;
        }
        var d = new Date();

        $scope.message.startYear = d.getFullYear();
        $scope.message.startMonth = d.getMonth() + 1;
        $scope.message.startDay = d.getDate();
        $scope.message.endYear = d.getFullYear() + 1;
        $scope.message.endMonth = d.getMonth() + 1;
        $scope.message.endDay = d.getDate();
        $scope.message.signDay = d.format('yyyy年MM月dd日');
        $scope.message.seq = d.format('yyyyMMddhms');


        // $("#imgShow").click(function () {
        //     $("canvas").remove();
        //     html2canvas(document.getElementById('imgData'), {
        //         onrendered: function (canvas) {
        //             console.log(canvas);
        //             // document.getElementById('imgData').appendChild(canvas);
        //             // var url = canvas.toDataURL();
        //             // document.body.append($("<img>").attr("src", url));
        //             setTimeout(function () {
        //                 var dataUrl = canvas.toDataURL();
        //                 $scope.dataUrl = dataUrl;
        //                 var newImg = document.createElement("img");
        //                 newImg.src = dataUrl;
        //                 console.log(newImg);
        //                 document.getElementById('preserve').appendChild(newImg);
        //                 $scope.save($scope.dataUrl)
        //             }, 500)
        //         },
        //         width: 1000,
        //         height: 1000,
        //         background: '#ba9f6e',
        //         logging: true,
        //         allowTaint: true,
        //         // timeout: 1000,
        //     });
        // });
        $rootScope.backShow = false;
        $scope.preShow = function (num) {
            $("canvas").remove();
            if (num == 1) {
                html2canvas(document.getElementById('imgData'), {
                    onrendered: function (canvas) {
                        setTimeout(function () {
                            var dataUrl = canvas.toDataURL();
                            $scope.dataUrl = dataUrl;
                            $scope.save($scope.dataUrl)
                        }, 100)
                    },
                    width: 1000,
                    height: 1000,
                    background: '#ba9f6e',
                    logging: true,
                    allowTaint: true,
                    // timeout: 1000,
                });
            } else {
                $rootScope.backShow = true;
                html2canvas(document.getElementById('imgData'), {
                    onrendered: function (canvas) {
                        // document.getElementById('imgData').appendChild(canvas);
                        // var url = canvas.toDataURL();
                        // document.body.append($("<img>").attr("src", url));
                        setTimeout(function () {
                            var dataUrl = canvas.toDataURL();
                            $scope.dataUrl = dataUrl;
                            var newImg = document.createElement("img");
                            newImg.src = dataUrl;
                            newImg.setAttribute("id", "newImg");
                            // document.getElementById('preserve').appendChild(newImg);
                            // var mdIcon = '<md-icon  md-font-set="ks-shop-font" md-font-icon="ks-close"></md-icon>';
                            var mdIcon = document.getElementById("mdIcon");

                            document.getElementById('preserve').appendChild(newImg).style.cssText =
                                "width: 560px;" +
                                "height: 800px;" +
                                "position: absolute;" +
                                "left: 50%;" +
                                "top: 94px;" +
                                "margin-left: -280px;" +
                                "z-index: 222222222;";
                            document.getElementById('preserve').appendChild(mdIcon).style.cssText =
                                "width: 30px;" +
                                "height: 30px;" +
                                "position: absolute;" +
                                "left: 50%;" +
                                "top: 70px;" +
                                "z-index: 222222222;" +
                                "margin-left: 280px;" +
                                "color: #fff;";

                        }, 100)
                    },
                    width: 1000,
                    height: 1000,
                    background: '#ba9f6e',
                    logging: true,
                    allowTaint: true,
                    // timeout: 1000,
                });
            }

        };


        $scope.save = function (dataUrl) {
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
                    $scope.getImg($scope.dataUrl);
                }, function () {
                    //error
                }
            );


        };

        $scope.getImg = function (id) {
            $http({
                method: "GET",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.realSave(resp.data.data.cdnUrls[0].url)
                }, function () {
                    //error
                }
            );
        };

        $scope.realSave = function (imgUrl) {
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/certificate",
                data: {
                    // realName: $scope.message.name,
                    partnerId: $scope.data.id,
                    certificateTemplateId: '',
                    adcNum: '',
                    partnerType: "REGIONAL_AGENCY",
                    address: $scope.message.address,
                    realName: $scope.message.realName,
                    prox: $scope.message.prox,
                    startDay: $scope.message.startDay,
                    startYear: $scope.message.startYear,
                    startMonth: $scope.message.startMonth,
                    endYear: $scope.message.endYear,
                    endMonth: $scope.message.endMonth,
                    endDay: $scope.message.endDay,
                    seq: $scope.message.seq,
                    signDay: $scope.message.signDay,
                    certificateImg: imgUrl,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                alertService.msgAlert("success", '授权成功');
                }, function () {
                    //error
                }
            );
        }

        // html2canvas(document.getElementById('imgData'), {
        //     onrendered: function (canvas) {
        //         console.log('document.getElementById', document.getElementById('imgData'));
        //
        //         console.log('canvas', canvas);
        //         canvas.id = "mycanvas";
        //         //document.body.appendChild(canvas);
        //         //生成base64图片数据
        //         var dataUrl = canvas.toDataURL();
        //         var newImg = document.createElement("img");
        //         newImg.src = dataUrl;
        //         document.getElementById('imgData').appendChild(newImg);
        //     },
        //     width: 300,
        //     height: 300,
        //     // background: '#ba9f6e',
        //     logging: true,
        //     allowTaint: true,
        // });


        //var _canvas = html2canvas(document.getElementById('box1'));
        //document.body.appendChild(_canvas);


    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    'alertService',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    '$httpParamSerializer',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
