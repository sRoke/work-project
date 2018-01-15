import conf from "../../../../../../conf";
import bottomHtml from "!html-loader?minimize=true!./bottomSheet.html";


// import  "../../../../../../thirdJs/html2canvas.js"
import html2canvas from 'html2canvas';

var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $mdDialog,
    $timeout,
    $mdBottomSheet,
    wxService,
    $interval,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$mdBottomSheet,
                _wxService,
                _$interval,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        $mdBottomSheet = _$mdBottomSheet;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $timeout = _$timeout;
        wxService = _wxService;
        $interval = _$interval;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true);

        if (wxService.isInWx()){
            wxService.init().then(function (data) {
                if(data){
                    $scope.wxInit = true;
                }
                // console.log ('~~~~~~~~~~~~~~~',data);
            })
        }


        $scope.choosedCategory = {};



        $scope.wxSys = function () {
            if (wxService.isInWx()) {
                if ($scope.initWX) {
                    wx.scanQRCode({
                        desc: 'scanQRCode desc',
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                            $scope.sysQrcode = result[result.length - 1];
                            $scope.keyWord = $scope.sysQrcode;
                            $scope.search();
                            $scope.$digest();
                        },
                        error: function (res) {
                            if (res.errMsg.indexOf('function_not_exist') > 0) {
                                alertService.msgAlert("exclamation-circle", "版本过低请升级");
                            }
                        }
                    });
                } else {
                    wxService.init().then(function (data) {
                        if (data) {
                            wx.scanQRCode({
                                desc: 'scanQRCode desc',
                                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                                    $scope.sysQrcode = result[result.length - 1];
                                    $scope.keyWord = $scope.sysQrcode;
                                    $scope.search();
                                    $scope.$digest();
                                },
                                error: function (res) {
                                    if (res.errMsg.indexOf('function_not_exist') > 0) {
                                        alertService.msgAlert("exclamation-circle", "版本过低请升级");
                                    }
                                }
                            });
                        }
                    })
                }
            }
        };






















        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
            // console.log('1111111111111111111111111');
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = [];
            $scope.getList(0);
        };

        $scope.focus = function (status) {
            if(status){
                $scope.searchShow = true;
            }else {
                $scope.searchShow = false;
            }
        };



        $scope.getCategory = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+ $stateParams.storeId +'/category',
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.categoryList = [];
                for(var i=0;i<resp.data.data.length;i++ ){
                    if(!resp.data.data[i].parentId){
                        $scope.categoryList.push(resp.data.data[i]);
                    }
                }
                // $scope.categoryList = resp.data.data;
                // console.log('$scope.listData', $scope.categoryList);
            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getCategory();


        $scope.currpage = 0;
        $scope.getList = function (currpage) {     //获取商品列表
            $scope.currpage = currpage;
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + "/item",
                params: {
                    pageSize: conf.pageSize,
                    page: currpage,
                    status: 'NORMAL',
                    keyWords: $scope.keyWord,
                    category: $scope.choosedCategory.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if(currpage){
                    $scope.itemList.number = resp.data.data.number;
                    for (var i=0;i< resp.data.data.content.length;i++){
                        $scope.itemList.content.push(resp.data.data.content[i])
                    }
                }else {
                    $scope.itemList = resp.data.data;
                }
            }, function (resp) {
            });
        };
        $scope.getList(0);

        $scope.getUserInfo = function () {                                        //获取用户信息
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + "/shopStaff/currentInfo",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.userInfo = resp.data.data;
                // console.log('$scope.userInfo==========',$scope.userInfo);
            }, function (resp) {
            });
        };
        $scope.getUserInfo();



        $scope.doThis = function () {
            // /console.log('22222222222222222=========',$scope.chooseItem)
            $scope.getShortUrl(conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+$scope.chooseItem.id);
        }


        $timeout(function () {                 //商品属性选择图
            $scope.swiper = new Swiper('#swiper-container2', {
                wrapperClass: 'my-wrapper2',
                slideClass: 'my-slide2',
                pagination: '.my-swiper-pagination',
                slidesPerView: 'auto',
                paginationClickable: true,
                roundLengths: true,
                freeModeSticky: true,
                freeMode: true,
                watchSlidesProgress: true,
            });
        }, 300);

        $scope.isClick = 0;
        $scope.clickThis = function (num,category) {      // 选择那个属性
            $scope.isClick = num;
            $scope.choosedCategory = category;
            // console.log('$scope.choosedCategory===',$scope.choosedCategory);
            $scope.swiper.slideTo(num, 1000, false);
            $scope.getList(0);
        };


        $scope.intervalItem = null;
        $scope.shareDialog = function (evt,item) {          //点击右下角分享
            var e=(evt)?evt:window.event;
            if (window.event) {
                window.event.cancelBubble=true;// ie下阻止冒泡
            } else {
                //e.preventDefault();
                e.stopPropagation();// 其它浏览器下阻止冒泡
            }

            // console.log('2222222222222',item);
            $interval.cancel($scope.intervalItem);

            //获取二维码地址
            // $scope.getShortUrl('https://kingsilk.net/shop/local/17100/#/brandApp/5a09661d46e0fb0007a58cec/store/5a09672759bf965254a5e0a0/home');
            // $scope.getShortUrl(conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+item.id);
            //弹窗主图
            $scope.chooseItem = item;
            //wx分享
            if(wxService.isInWx()){
                var confWx = {
                    title: item.title,
                    desc: item.desp,
                    link: conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+item.id,
                    imgUrl: item.imgs[0]+"?imageView2/2/w/100/h/100",
                };
                if($scope.wxInit){
                    wxService.shareRing(confWx);
                    wxService.shareFriend(confWx);
                }else {
                    wxService.init().then(function (data) {
                        if(data){
                            $scope.wxInit = true;
                            wxService.shareRing(confWx);
                            wxService.shareFriend(confWx);
                        }
                    })
                }

            }
            $mdBottomSheet.show({
                template: bottomHtml,
                parent: angular.element(document.body),
                controllerAs: "vm",
                controller: ['wxService', function (wxService) {
                    var vm = this;
                    // var shareImg = document.getElementById('shareImg');
                    vm.shareImg = function (event) {
                        // console.log(22);
                        event.stopPropagation();
                    };
                    vm.imgShow = function () {


                        // $scope.getShortUrl('https://kingsilk.net/shop/local/17100/#/brandApp/5a09661d46e0fb0007a58cec/store/5a09672759bf965254a5e0a0/home');
                        $scope.intervalItem = $interval(function () {
                            if($scope.dataUrl) {
                                $interval.cancel($scope.intervalItem);
                                // console.log('++++++++++++++++++++++++++',$scope.dataUrl);
                                $scope.popularityList($scope.dataUrl);
                            }else {
                                // console.log('--------------------------',$scope.dataUrl);
                            }
                        },300);

                        // setTimeout(function () {
                        //     $scope.popularityList($scope.dataUrl);
                        // },600);

                        vm.cancel();
                    };
                    vm.shareFiend = function () {
                        $scope.maskShow();
                        vm.cancel();
                    };
                    vm.cancel = function () {
                        $mdBottomSheet.hide();
                    };
                }]
            }).then(function (clickedItem) {
            });
        };


        // $timeout(function () {
        //     $scope.simpleImg = 'https://img.kingsilk.net/3f79e2d9b7442af651d76be8cf98c11a';
        //     $scope.testImg = 'https://img.kingsilk.net/3f79e2d9b7442af651d76be8cf98c11a';
        // }, 0);


        //要将 canvas 的宽高设置成容器宽高的 2 倍       创建canvas备用
        $scope.canvas = document.createElement("canvas");
        $scope.getShortUrl = function (url) {
            $scope.dataUrl = '';
            $http({
                method: "post",
                url: conf.dwzApiPath,
                data: {
                    url:url,
                }
            }).then(function (resp) {
                var data = resp.data;
                // console.log('data', data);
                $scope.qrCodeKey = data.data;
                $scope.qrCodeUrl = conf.dwzUrlPath + $scope.qrCodeKey;
                // console.log('qrCodeUrl===',$scope.qrCodeUrl);
                // console.log('canvasSimple===',document.getElementById('canvasSimple'));
                // console.log('canvasSimple222===',angular.element(document.getElementById('canvasSimple')));
                $scope.canvas.width = angular.element(document.getElementById('canvasSimple'))[0].clientWidth*2;
                $scope.canvas.height = angular.element(document.getElementById('canvasSimple'))[0].clientHeight*2;
                $scope.canvas.style.width = angular.element(document.getElementById('canvasSimple'))[0].clientWidth + "px";
                $scope.canvas.style.height = angular.element(document.getElementById('canvasSimple'))[0].clientHeight + "px";
                var context = $scope.canvas.getContext("2d");
                //然后将画布缩放，将图像放大两倍画到画布上
                context.scale(2,2);

                setTimeout(function () {
                    html2canvas(document.getElementById('canvasSimple'), {
                        canvas:$scope.canvas,
                        onrendered: function (canvas) {

                            // console.log('canvas', canvas);
                            // var ctx=canvas.getContext("2d");

                            // ctx.scale(2,2);
                            var dataUrl = canvas.toDataURL("image/jpeg", 1.0);
                            $scope.dataUrl = dataUrl;
                            var newImg = document.createElement("img");
                            newImg.src = dataUrl;
                            $scope.cancassuccess = true;
                            $scope.$digest()
                            // setTimeout(function () {
                            //     $scope.popularityList($scope.dataUrl);
                            // }, 100)
                        },
                        // width: 600,
                        // height: 998,
                        background: '#ba9f6e',
                        logging: true,
                        allowTaint: false,
                        useCORS:true,
                        // timeout: 5000,
                    });
                }, 0)
            });
        };

        // $timeout(function () {
        //     $scope.getShortUrl('https://kingsilk.net/shop/local/17100/#/brandApp/5a09661d46e0fb0007a58cec/store/5a09672759bf965254a5e0a0/home');
        // }, 300);
        $scope.popularityList = function (imgUrl) {
            // console.log('img',imgUrl);
            $mdDialog.show({
                templateUrl: 'shareItem.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                hasBackdrop: false,
                controller: ['$http', '$stateParams', '$mdDialog', function ($http, $stateParams, $mdDialog) {
                    var vmd = this;
                    vmd.imgUrl = imgUrl;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
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

        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", null, {reload: true});
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$mdBottomSheet',
    'wxService',
    '$interval',
    '$state'
];

export default Controller ;
