import conf from "../../../../../conf";
import buyDialog from "!html-loader?minimize=true!./buyDialog.html";

var $scope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    authService,
    $rootScope,
    $templateCache;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        $templateCache = _$templateCache;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        // authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId).then(function (data) {
        //     console.log('55555555555555', data)
        //     $scope.viewShow = data;
        // });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.id = $stateParams.id;
        var storage = window.localStorage;

        //顶部选项卡
        $scope.activeNum = 1;
        $scope.tabBtnClick = function (num) {
            $scope.activeNum = num;
        };

        $scope.swiper = '';
        if (wxService.isInWx()) {
            wxService.init().then(function (data) {
                if (data) {
                    $scope.wxInit = true;
                }
                // console.log('~~~~~~~~~~~~~~~', data);
            })
        }

        // $scope.com = [
        //     'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //     'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //     'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //     'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //     'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        // ]

        $scope.getInfo = function (swiper) {

            if (loginService.getAccessToken()) {
                $http({
                    method: "GET",
                    ///brandApp/{brandAppId}/shop/{shopId}/mall/item/{id}
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/item/" + $scope.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.data = resp.data.data;


                    $timeout(function () {
                        swiper.update();
                    }, 500);

                    //默认选中第0个sku的价格
                    $scope.data.salePrice = $scope.data.skuList[0].salePrice;
                    $scope.data.labelPrice = $scope.data.skuList[0].labelPrice;

                    $scope.selectSku = $scope.data.skuList[0];

                    // for (let i in $scope.data.skuList) {
                    //     console.log('$scope.data.skuList', $scope.data.skuList[i].storage);
                    //     $scope.data.skuList[i].storage = Number($scope.data.skuList[i].storage);
                    //     if ($scope.data.skuList[i].storage >= 1) {
                    //         $scope.data.salePrice = $scope.data.skuList[i].salePrice;
                    //         $scope.data.labelPrice = $scope.data.skuList[i].labelPrice;
                    //         $scope.data.selectSku = $scope.data.skuList[i];
                    //         console.log('$scope.data.selectSku', $scope.data.selectSku);
                    //         break;
                    //     }
                    // }

//wx分享
                    if (wxService.isInWx()) {

                        // console.log('location.href', location.href);
                        var confWx = {
                            title: $scope.data.title,
                            desc: "您的朋友分享了一个链接给你",
                            link: location.href,
                            imgUrl: $scope.data.imgs[0],
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
                            })
                        }

                    }
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                        // url: "https://kingsilk.net/shop/rs/local/16700/api/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                        params: {
                            type: 'MALL',
                            cartId: storage.getItem("cartId")
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).then(function (resp) {
                        // console.log('resp---order--create', resp);
                        $scope.totalNum = resp.data.data.totalNum;


                    }, function (resp) {
                        //error
                    });
                    $scope.detail = resp.data.data.detail;

                    var tplUrl = "tpl.html";
                    $scope.tplUrl = tplUrl;
                    $templateCache.put(tplUrl, $scope.detail);
                }, function (resp) {
                    //error
                });
            } else {
                $http({
                    method: "GET",
                    ///brandApp/{brandAppId}/shop/{shopId}/mall/item/{id}
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/item/" + $scope.id,
                    headers: {
                        // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.data = resp.data.data;
                    $timeout(function () {
                        swiper.update();
                    }, 500);

                    //默认选中第0个sku的价格
                    $scope.data.salePrice = $scope.data.skuList[0].salePrice;
                    $scope.data.labelPrice = $scope.data.skuList[0].labelPrice;
                    $scope.selectSku = $scope.data.skuList[0];
//wx分享
                    if (wxService.isInWx()) {
                        var confWx = {
                            title: $scope.data.title,
                            desc: $scope.data.desp,
                            link: location.href,
                            imgUrl: $scope.data.imgs[0] + "?imageView2/2/w/100/h/100",
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
                            })
                        }

                    }
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                        // url: "https://kingsilk.net/shop/rs/local/16700/api/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                        params: {
                            type: 'MALL',
                            cartId: storage.getItem("cartId")
                        },
                        headers: {
                            // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).then(function (resp) {
                        // console.log('resp---order--create', resp);
                        $scope.totalNum = resp.data.data.totalNum;


                    }, function (resp) {
                        //error
                    });

                    $scope.detail = resp.data.data.detail;

                    var tplUrl = "tpl.html";
                    $scope.tplUrl = tplUrl;
                    $templateCache.put(tplUrl, $scope.detail);
                }, function (resp) {
                    //error
                });
            }


        };
        // $scope.getInfo();


        $scope.buyDialog = function (status) {
            $mdBottomSheet.show({
                template: buyDialog,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                locals: {key: $scope.data},
                bindToController: true,
                controller: ['$mdBottomSheet', 'locals', 'alertService', function ($mdBottomSheet, locals, alertService) {
                    var vmd = this;

                    vmd.list = locals.key;
                    // console.log('vmd.list', vmd.list);

                    vmd.skuNum = 1;
                    vmd.status = status;

                    //默认选中
                    // vmd.list.skuList[0].check = true;
                    // vmd.chooseSku = vmd.list.skuList[0];
                    ////默认选中第0个
                    // for (let i in vmd.list.skuList) {
                    //     console.log('vmd.list.skuList', vmd.list.skuList[i].storage);
                    //     vmd.list.skuList[i].storage = Number(vmd.list.skuList[i].storage);
                    //     if (vmd.list.skuList[i].storage >= 1) {
                    //         vmd.checkedSku = vmd.list.skuList[i];
                    //         break;
                    //     }
                    // }
                    // console.log(' vmd.checkedSku ', vmd.checkedSku);
                    vmd.checkedSku = vmd.list.skuList[0];

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


                    // vmd.chooseThis = function (sku) {
                    //
                    //     for (var i = 0; i < vmd.list.skus.length; i++) {
                    //         vmd.list.skus[i].check = false;
                    //     }
                    //
                    //     sku.check = true;
                    //
                    //     console.log('sku', sku);
                    //
                    //     vmd.chooseSku = sku;
                    // };


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
                                vmd.skuNum = 1;
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


                    //输入框失去焦点
                    vmd.skuNumBlur = function (num, sku) {
                        vmd.skuNum = Number(num);
                        // console.log(num, vmd.skuNum, typeof vmd.skuNum);
                        if (vmd.skuNum == '') {
                            vmd.skuNum = 0;
                        } else if (!( /^\d+(\.\d+)?$/.test(num))) { //测试
                            vmd.skuNum = 0;
                            alertService.msgAlert("exclamation-circle", "请输入正整数");
                        }
                        if (!/^\d+$/.test(vmd.skuNum)) {
                            vmd.skuNum = 0;
                            alertService.msgAlert("exclamation-circle", "请输入整数");
                        }
                        ;
                        if (sku.storage < vmd.skuNum) {
                            alertService.msgAlert("exclamation-circle", "库存仅剩" + sku.storage + "件");
                            vmd.skuNum = sku.storage;
                        }
                        // vmd.warnView();
                        if (vmd.skuNum <= 0) {
                            vmd.skuNum = 0;
                        }
                        // vmd.addCart(vmd.skuNum, sku)
                    };


                    vmd.skuNumCount = function (status, num, sku) {
                        if (status == 1) {
                            vmd.skuNum++;
                            if (sku.storage < vmd.skuNum) {
                                alertService.msgAlert("exclamation-circle", "库存仅剩" + sku.storage + "件");
                                vmd.skuNum = sku.storage;
                            }
                        }
                        else {
                            vmd.skuNum--;
                            if (sku.storage < vmd.skuNum) {
                                alertService.msgAlert("exclamation-circle", "库存仅剩" + sku.storage + "件");
                                vmd.skuNum = sku.storage;
                            }
                            if (vmd.skuNum <= 1) {
                                vmd.skuNum = 1;
                            }
                        }
                        // vmd.skuNumBlur(num, sku);
                        // console.log('vmd.skuNum', vmd.skuNum);
                    };

                    if (status == 3) {
                        vmd.buyBtn = true;
                    } else {
                        vmd.buyBtn = false;
                    }


                    vmd.cancleDialog = function () {
                        $mdBottomSheet.hide();
                    };
                    vmd.submit = function (status) {

                        // console.log('vmd.checkedSku', vmd.checkedSku);

                        if (vmd.checkedSku.storage <= 0) {
                            alertService.msgAlert("exclamation-circle", "库存不足");
                            return
                        }
                        // if (vmd.checkedSku.storage < vmd.skuNum) {
                        //     alertService.msgAlert("exclamation-circle", "库存仅剩" + sku.storage + "件");
                        //     vmd.skuNum = vmd.checkedSku.storage;
                        //     return;
                        // }

                        $mdBottomSheet.hide({
                            num: vmd.skuNum,
                            skuData: vmd.checkedSku,
                            status: status
                        });
                    };
                    // $mdBottomSheet.hide({totlePrice: vmd.totlePrice});
                }]
            }).then(function (data) {

                // console.log('data', data);

                if (data.status == 1) {
                    if (loginService.getAccessToken()) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/add",
                            params: {
                                num: data.num,
                                skuId: data.skuData.id,
                                type: 'MALL',
                                cartId: storage.getItem("cartId")

                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).then(function (resp) {
                            // console.log('resp---order--create', resp);
                            //$scope.data = resp.data.data;
                            $http({
                                method: "GET",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                                // url: "https://kingsilk.net/shop/rs/local/16700/api/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",

                                params: {
                                    type: 'MALL',
                                    cartId: storage.getItem("cartId")
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).then(function (resp) {
                                // console.log('resp---order--create', resp);

                                $scope.totalNum = resp.data.data.totalNum;

                            }, function (resp) {
                                //error
                            });

                        }, function (resp) {
                            //error
                        });
                    } else {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/add",
                            params: {
                                num: data.num,
                                skuId: data.skuData.id,
                                type: 'MALL',
                                cartId: storage.getItem("cartId")
                            },
                            headers: {
                                // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).then(function (resp) {
                            // console.log('resp---order--create', resp);

                            $scope.cartId = resp.data.data;

                            storage.setItem("cartId", $scope.cartId);

                            $http({
                                method: "GET",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                                // url: "https://kingsilk.net/shop/rs/local/16700/api/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/num",
                                params: {
                                    type: 'MALL',
                                    cartId: storage.getItem("cartId")
                                },
                                headers: {
                                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).then(function (resp) {
                                // console.log('resp---order--create', resp);
                                $scope.totalNum = resp.data.data.totalNum;


                            }, function (resp) {
                                //error
                            });
                            //$scope.data = resp.data.data;
                        }, function (resp) {
                            //error
                        });
                    }


                } else {

                    if (loginService.getAccessToken()) {
                        $scope.arr = {};
                        $scope.arr.skuId = data.skuData.id;
                        $scope.arr.num = data.num;
                        $scope.orderCheckReq = [];
                        $scope.orderCheckReq.push($scope.arr);
                        $http({
                            method: "POST",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/order/create",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: $scope.orderCheckReq,
                        }).then(function (resp) {
                            // console.log('resp---order--create', resp);
                            //$scope.data = resp.data.data;
                            $scope.orderId = resp.data.data.orderId;
                            if (resp.data.status == '200') {
                                $state.go("main.brandApp.store.confirmOrder", {
                                    orderId: $scope.orderId,
                                    from: "DETAIL"
                                }, {reload: true});
                            } else {
                                alertService.msgAlert("exclamation-circle", resp.data.exception);
                            }

                        }, function (resp) {
                            //error
                        });
                    } else {
                        $state.go("main.wxLogin", ({backUrl: $location.absUrl(), brandAppId: $stateParams.brandAppId}))
                    }

                }

            })
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
            $state.go("main.brandApp.store.home", {reload: true});
            // } else {
            //     history.back();
            // }
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope',
    '$templateCache'
];

export default Controller ;
