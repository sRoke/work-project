import conf from "../../../../../../conf";
import html2canvas from 'html2canvas';
import bottomHtml from "!html-loader?minimize=true!./bottomSheet.html";
import buyDialog from "!html-loader?minimize=true!./buyDialog.html";

var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $mdDialog,
    $mdBottomSheet,
    $templateCache,
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
                _$mdBottomSheet,
                _$templateCache,
                _wxService,
                _$interval,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        $mdBottomSheet =_$mdBottomSheet;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        wxService = _wxService;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $templateCache =_$templateCache;
        $interval = _$interval;

        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        // loginService.loginCtl(true);

        loginService.loginCtl(true);


        //顶部选项卡
        $scope.activeNum = 1;
        $scope.tabBtnClick = function (num) {
            $scope.activeNum = num;
        };
        $scope.swiper = '';
        $scope.itemImgs = [];
        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + "/item/" + $stateParams.id,
            data: $scope.skuData,
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            // console.log('resp', resp);
            $scope.itemData = resp.data.data;
            $scope.itemImgs = resp.data.data.imgs;
            $scope.chooseSku = resp.data.data.skuList[0];   //默认选中地一个sku
            // console.log('chooseSku==========', $scope.chooseSku);
            var tplUrl = "tpl.html";
            $scope.tplUrl = tplUrl;
            $templateCache.put(tplUrl, resp.data.data.detail);
        }, function (resp) {
            //TODO 错误处理
        });


        if (wxService.isInWx()){
            wxService.init().then(function (data) {
                if(data){
                    $scope.wxInit = true;
                }
            })
        }



        $scope.buyDialog = function (status) {
            $mdBottomSheet.show({
                template: buyDialog,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {key: $scope.itemData,chooseSku:$scope.chooseSku},
                bindToController: true,
                controller: ['$mdBottomSheet', 'locals', 'alertService', function ($mdBottomSheet, locals, alertService) {
                    var vmd = this;
                    vmd.list = locals.key;
                    // console.log('vmd.list', vmd.list);
                    vmd.skuNum = 0;


                    //默认选中
                    // vmd.list.skuList[0].check = true;
                    // vmd.chooseSku = vmd.list.skuList[0];
                    ////默认选中第0个
                    vmd.checkedSku = locals.chooseSku;
                    // console.log('vmd.checkedSku', vmd.checkedSku);


                    // vmd.selectSpecs = vmd.checkedSku.price;
                    //当前已选中的规格值,spec.value nameId为key，valueId为值
                    vmd.selectSpecs = {};
                    //默认选中一个
                    for (let i in vmd.list.specs) {
                        vmd.selectSpecs[vmd.checkedSku.specList[i].itemPropId] = vmd.checkedSku.specList[i].itemPropValueId;
                        // console.log('vmd.selectSpecs[vmd.checkedSku.specList[i].itemPropId] = vmd.checkedSku.specList[i].itemPropValueId;');
                        // console.log('vmd.selectSpecs[vmd.checkedSku.specList[i].itemPropId]', vmd.selectSpecs[vmd.checkedSku.specList[i].itemPropId]);
                        // console.log('vmd.checkedSku.specList[i].itemPropId', vmd.checkedSku.specList[i].itemPropId);

                    }
                    ////点击规格
                    vmd.clickedSpec = function (spec, curValue) {
                        // console.log('spec', spec);
                        // console.log('curValue', curValue);
                        ////原有规格值备份
                        let valueIdBak = vmd.selectSpecs[spec.itemProp.id];
                        //修改当前规格状态
                        vmd.selectSpecs[spec.itemProp.id] = curValue.id;
                        let hasSku = false;
                        //匹配sku
                        for (let i in vmd.list.skuList) {
                            let sku = vmd.list.skuList[i];
                            let boo = true;
                            for (let j in sku.specList) {
                                if (vmd.selectSpecs[sku.specList[j].itemPropId] != sku.specList[j].itemPropValueId) {
                                    boo = false;
                                    break;
                                }
                            }
                            if (boo) {
                                vmd.checkedSku = sku;
                                // console.log('123123', vmd.checkedSku);
                                hasSku = true;
                                break;
                            }
                        }
                        if (!hasSku) {
                            alertService.msgAlert("exclamation-circle", "当前规格缺货");
                            vmd.selectSpecs[spec.itemProp.id] = valueIdBak;
                        }
                    };
                    vmd.cancleDialog = function () {
                        $mdBottomSheet.hide();
                    };
                    vmd.submit = function () {
                        $mdBottomSheet.hide({
                            skuData:vmd.checkedSku,
                        });
                    };
                    // $mdBottomSheet.hide({totlePrice: vmd.totlePrice});
                }]
            }).then(function (data) {
                // console.log('data', data);
                $scope.chooseSku = data.skuData;
                $scope.doThis();
            })
        };



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

                if($scope.getShortUrlFlog === 2){
                    $scope.getShortUrl(conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+$scope.chooseSku.id);
                }

            }, function (resp) {
            });
        };
        $scope.getUserInfo();





        $scope.intervalItem = null;
        $scope.shareDialog = function (item) {          //点击右下角分享
            $interval.cancel($scope.intervalItem);   //再次点击去差距上次定时器
            $scope.intervalItem = null;
            $scope.chooseItem = item;
            //wx分享
            if(wxService.isInWx()){
                var confWx = {
                    title: item.title,
                    desc: "您的朋友分享了一个链接给你",
                    link: conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+item.id+'&skuId=' + $scope.chooseSku.id,
                    imgUrl: item.imgs+"?imageView2/2/w/100/h/100",
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
                    vm.shareImg = function (event) {
                        // console.log(22);
                        event.stopPropagation();
                    };
                    vm.imgShow = function () {

                        $scope.intervalItem = $interval(function () {
                            if($scope.dataUrl) {
                                $interval.cancel($scope.intervalItem);
                                $scope.intervalItem = null;
                                $scope.popularityList($scope.dataUrl);
                            }else {
                                // console.log('--------------------------',$scope.dataUrl);
                            }
                        },300);
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




        //要将 canvas 的宽高设置成容器宽高的 2 倍       创建canvas备用
        $scope.canvas = document.createElement("canvas");         //获取短网址并截图
        $scope.getShortUrl = function (url) {
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









        $scope.doThis = function () {
            if($scope.userInfo){
                $scope.getShortUrlFlog = 1;
                $scope.getShortUrl(conf.shareItemLink + '&form=' + $scope.userInfo.userId +'#/brandApp/'+$scope.brandAppId+'/store/'+$stateParams.storeId+'/item?id='+$scope.chooseSku.id);
            }else {
                $scope.getShortUrlFlog = 2;
            }
        }







        // $scope.pageTitle = '订单详情';
        /*返回上级*/
        $scope.fallbackPage = function () {
            history.back();
            // $state.go("main.brandApp.store.home", null, {reload: true});
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
    '$mdBottomSheet',
    '$templateCache',
    'wxService',
    '$interval',
    '$state'
];

export default Controller ;
