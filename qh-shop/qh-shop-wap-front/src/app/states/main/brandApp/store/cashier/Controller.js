import cart from "!html-loader?minimize=true!./cart.html";
import conf from "../../../../../conf";
import view1 from "!html-loader?minimize=true!./view1.html";


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
    $rootScope;
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
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;

        $scope.imgView = conf.imgView1;

        $scope.currentPage = 0;
        $scope.chooseNav = '全部商品';
        //-----------------------------------------------用来切换头部导航
        $scope.tab = 1;
        $scope.changeTab = function (tabNum) {
            $scope.tab = tabNum;
        };


        /**
         * --------------------------------------------------------------------------微信扫码选择界面js
         */
        //-------------------------------------调用微信扫一扫功能
        if (wxService.isInWx()) {
            // console.log('wxService', wxService);
            wxService.init().then(function (data) {
                if (data) {
                    $scope.initWX = true;
                }
            })
        }
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
                            $scope.getItemInformation($scope.sysQrcode);
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
                                    $scope.getItemInformation($scope.sysQrcode);
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


        // if (wxService.isInWx()) {
        //     $scope.$on("$destory", function () {
        //         wxService.shareRing(); // 恢复默认绑定
        //         wxService.shareFriend();
        //     });
        //     var link = location.href;
        //     var curConf = {
        //         title: '111111', // 分享标题
        //         desc: '111111',
        //         link: '111111',
        //         imgUrl: '111111', // 分享图标
        //         success: function () {
        //             // 用户确认分享后执行的回调函数
        //         },
        //         cancel: function () {
        //             // 用户取消分享后执行的回调函数
        //         }
        //     };
        //     wxService.initShareOnStart(curConf);
        // }

        $scope.getItemInformation = function (seq) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/" + seq + '/scanCode',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-----------------------', resp);
                $scope.sysItem = resp.data.data;
                $scope.getList();
            }, function (error) {
                if (error.status == 500) {
                    $scope.sysItem = null;
                    alertService.msgAlert("exclamation-circle", "库存中不存在该商品!");
                }
            });
        }


        /**
         * --------------------------------------------------------------------------商品选择界面js
         */

        //------------------------------------------------选择商品规格弹窗
        $scope.getItemSpec = function (item) {
            $mdBottomSheet.show({
                template: view1,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                bindToController: true,
                controller: ['$mdBottomSheet', function ($mdBottomSheet) {
                    var vmd = this;
                    vmd.skuNum = 1;
                    vmd.skuStock = 0;


                    // 当输入框为null时失去焦点事件
                    vmd.numBlur = function () {
                        if (vmd.skuNum == null || vmd.skuNum == '') {
                            vmd.skuNum = 1;
                        }
                    };
                    //------------------------------------输入数量ng-change触发事件
                    vmd.formatNum = function () {
                        //当输入框type = munber时输入框为空时vmd.skuNum == null
                        //当输入框type = tel时输入框为空时vmd.skuNum == ''
                        if (vmd.skuNum == '' || vmd.skuNum == null) {
                            return;
                        } else if (vmd.skuNum <= 0) {
                            vmd.skuNum = 1;
                        } else if (vmd.skuNum > vmd.skuStock) {
                            alertService.msgAlert("exclamation-circle", "当前规格库存不足");
                            vmd.skuNum = vmd.skuStock; //todo------------------
                        }
                    };
                    //-----------------------------------点击数量+-号触发事件
                    vmd.changeNum = function (status) {
                        if (status == '-1') {
                            if (vmd.skuNum > 1) {
                                vmd.skuNum--;
                            }
                        } else if (status == '+1') {
                            if (vmd.skuNum < vmd.skuStock) {
                                vmd.skuNum++;
                            }
                        }
                    };

                    //--------------------------------------点击确认事件
                    vmd.submit = function () {

                        if (!vmd.sku.skuId) {
                            // console.log('未找到对应sku');
                            return;
                        }

                        if (vmd.skuNum <= 0) {
                            // console.log('商品数量不能小于0');
                            return;
                        }
                        if (vmd.skuNum > vmd.skuStock) {
                            alertService.msgAlert("exclamation-circle", "当前规格库存不足");
                            return;
                        }
                        //--------------------------------------------加入购物车
                        $http({
                            method: "PUT",
                            num: vmd.skuNum,
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart",
                            params: {
                                skuId: vmd.sku.skuId,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            $mdBottomSheet.hide('true');
                        }, function () {

                        });

                        // $mdBottomSheet.hide();
                    };

                    //--------------------------------点击 X 号退出事件
                    vmd.exit = function () {
                        $mdBottomSheet.hide();
                    };
                    //---------------------------获取该商品规格等信息的事件
                    vmd.getData = function () {
                        $http({
                            method: "GET",
                            url: conf.agencyPath + "/brandApp/" + $scope.brandAppId + "/item/" + item.id + "/detail",
                            params: {
                                type: 'REFUND'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                vmd.data = resp.data.data;
                                // vmd.speclist = angular.copy(resp.data.data.specs); //用于提示什么规格还未选择
                                ///数据容错处理
                                if (!vmd.data.skus || vmd.data.skus.length == 0) {
                                    alert("商品错误");
                                    return;
                                } else {
                                    for (let i in vmd.data.skus) {
                                        vmd.skuStock += vmd.data.skus[i].storage;
                                    }
                                }
                            }, function () {
                            }
                        );
                    };
                    vmd.getData();
                    vmd.selectspeclist = [];
                    vmd.clickedSpec = function (spec, btn) {
                        vmd.selectspeclist[spec.nameId] = btn.valueId;
                        // console.log(' vmd.selectspeclist====', vmd.selectspeclist);
                        vmd.getSku();
                    };
                    vmd.getSku = function () {
                        let skuList = vmd.data.skus;
                        let specList = vmd.selectspeclist;
                        for (let i in skuList) {
                            var flog = false;
                            for (let j in skuList[i].specs) {
                                // console.log('1',specList[skuList[i].specs[j].nameId],i,j);
                                // console.log('2',skuList[i].specs[j].valueId);
                                if (specList[skuList[i].specs[j].nameId] == skuList[i].specs[j].valueId) {
                                    flog = true;
                                } else {
                                    flog = false;
                                    break;
                                }
                            }
                            if (flog) {
                                // console.log('sku========',skuList[i]);
                                vmd.sku = skuList[i];
                                vmd.skuStock = skuList[i].storage;
                                if (vmd.skuStock <= 0) {
                                    alertService.msgAlert("exclamation-circle", "当前规格缺货");
                                }
                            }
                        }
                    }


                }]
            }).then(function (data) {
                // console.log('data',data);
                if (data) {
                    $scope.getList()
                }
            })
        };
        //-----------------------------------------获取商品列表
        $scope.getItemList = function (categoryId) {
            $scope.currentPage = 0;
            $scope.itemList = [];
            $scope.data = false;
            $scope.categoryId = categoryId;
            $http({
                method: 'GET',
                url: conf.agencyPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/skuStore/searchSkuStore",
                params: {
                    categoryId: categoryId,
                    type: 'refund',
                    page: $scope.currentPage
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.itemList = resp.data.data.content;
                $scope.num = resp.data.data;
                $scope.getCheckNum();
                // $scope.totlePrice = 0;
                // $scope.totleNum = 0;
                // if (!resp.data.data) {
                //     console.log('未添加');
                //     $scope.totlePrice = 0;
                //     $scope.totleNum = 0;
                //     return;
                // }
                // for (let i = 0; i < $scope.itemList.length; i++) {
                //     $scope.totlePrice += (Number($scope.itemList[i].salePrice) * $scope.itemList[i].num);
                //     $scope.totleNum += $scope.itemList[i].num;
                // }


                //计算最低价格　
                $scope.minPrice = '';
            })
        };
        $scope.getItemList();


        $scope.getCheckNum = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/item",
                params: {
                    // type: 'REFUND'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    // console.log(111111111, resp);
                    $scope.data = resp.data.data;
                    $scope.comments = resp.data.data;
                    //sku匹配   购物车num与列表num 相匹配
                    if ($scope.data) {
                        for (let i = 0; i < $scope.data.length; i++) {
                            //console.log(i,$scope.data[i].sku.skuId)
                            for (let j = 0; j < $scope.itemList.length; j++) {
                                if ($scope.data[i].sku.skuId == $scope.itemList[j].id) {
                                    //console.log('匹配')
                                    $scope.itemList[j].checkedNum = $scope.data[i].num;
                                }
                            }
                            ;
                        }
                        ;
                    }
                    ;


                    $scope.totlePrice = 0;
                    $scope.totleNum = 0;
                    if (!resp.data.data) {
                        // console.log('   未添加');
                        $scope.totlePrice = 0;
                        $scope.totleNum = 0;
                        return;
                    }
                    for (let i = 0; i < resp.data.data.length; i++) {
                        $scope.totlePrice += (Number(resp.data.data[i].sku.salePrice) * resp.data.data[i].num);
                        $scope.totleNum += resp.data.data[i].num;
                    }


                    // console.log($scope.itemList);
                }, function () {

                }
            );
        };
        //$scope.getCheckNum();

        $scope.item={};
        $scope.item.checkedNum=0;











        //商品加减1
        $scope.minusNum = function (item) {
            if (!item.checkedNum) {
                item.checkedNum = 0;
                return false;
            } else {
                item.checkedNum--;
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/setNum",
                params: {
                    skuId: item.id,
                    num: item.checkedNum
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('123');
                $scope.getCheckNum();          //获取购物车列表
            }, function () {

            });
        };
        $scope.addNum = function (item) {
            //console.log(222,item.checkedNum);
            if (!item.checkedNum) {    //不存在
                item.checkedNum = 0;
                if (item.num > 0) {
                    item.checkedNum++;
                } else {
                    return false;
                }
            } else {
                if (item.checkedNum <= item.num - 1) {
                    item.checkedNum++;
// <<<<<<< f2ab6f1d45de3580f49a71aef19c3e351a45226a
//                 } else {
//                     return false;
// =======
                }else{
                    alertService.msgAlert("exclamation-circle", "库存仅剩" + item.num + "件");
                    $scope.item.checkedNum=item.num;
// >>>>>>> 收银购物车
                }
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/setNum",
                params: {
                    skuId: item.id,
                    num: item.checkedNum
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log(1234);
                $scope.getCheckNum();   //
            }, function () {

            });
        };


        //------------------------------------------------加载更多
        $scope.moreFlog = false; //防止连点
        $scope.getMore = function () {
            if ($scope.moreFlog == false) {
                $scope.moreFlog = true;
            } else {
                return;
            }
            $http({
                method: 'GET',
                url: conf.agencyPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/skuStore/searchSkuStore",
                params: {
                    categoryId: $scope.categoryId,
                    page: $scope.currentPage + 1,
                    type: 'refund'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.num = resp.data.data;
                $scope.currentPage++;
                for (var i = 0; i < resp.data.data.content.length; i++) {
                    $scope.itemList.push(resp.data.data.content[i]);
                }
                //计算最低价格　
                $scope.minPrice = '';
                $scope.moreFlog = false;
            })
        };
        //---------------------------------------------------------获取左侧分类列表
        $scope.getBtnList = function () {
            $http({
                method: 'GET',
                url: conf.agencyPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/category/getCategoryList",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.btnList = resp.data.data;
                // 默认选第一个
                // $scope.btnList[0].check = true;
                $scope.check = true;
                // $scope.getItemList(null);
            })

        };
        $scope.getBtnList();
        //--------------------------------------------------------选择 左侧分类
        $scope.btnClick = function (btn) {
            $scope.data = false;
            $scope.itemList = [];
            if (btn == null) {

                $scope.chooseNav = '全部商品';
                $scope.check = true;
                for (var i = 0; i < $scope.btnList.length; i++) {
                    $scope.btnList[i].check = false;
                }
                $scope.getItemList(null);
            }
            else {
                $scope.chooseNav = btn.name;
                for (var i = 0; i < $scope.btnList.length; i++) {
                    $scope.check = false;
                    $scope.btnList[i].check = false;
                    if (btn.name == $scope.btnList[i].name) {
                        $scope.btnList[i].check = true;
                        $scope.getItemList(btn.id);
                    }
                }
            }
        };
        //-------------------------------------------------------获取购物车列表
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/item",
                params: {
                    // type: 'REFUND'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {

                    // console.log('------',resp.data);

                    $scope.comments = resp.data.data;
                    $scope.totlePrice = 0;
                    $scope.totleNum = 0;
                    if (!resp.data.data) {
                        // console.log('   未添加');
                        $scope.totlePrice = 0;
                        $scope.totleNum = 0;
                        return;
                    }
                    for (let i = 0; i < $scope.comments.length; i++) {
                        $scope.totlePrice += (Number($scope.comments[i].sku.salePrice) * $scope.comments[i].num);
                        $scope.totleNum += $scope.comments[i].num;
                    }


                }, function () {

                }
            );
        };
        // $scope.getList();
        // 生成订单
        $scope.orderCreate = function () {
            // console.log(123);
            $scope.postParams = [];
            // console.log($scope.comments);
            if ($scope.comments) {
                // console.log(1, $scope.comments)
                for (let i = 0; i < $scope.comments.length; i++) {
                    let tmpSku = {
                        skuId: $scope.comments[i].sku.skuId,
                        num: $scope.comments[i].num
                    };
                    $scope.postParams.push(tmpSku);
                }
            }
            if ($scope.postParams.length < 1) {
                // console.log(2, $scope.postParams)
                // alertService.msgAlert("exclamation-circle", "您还没有选择任何商品哦");
                return;
            }

            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order",
                data: $scope.postParams,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    let orderData = resp.data;
                    /*.orderId*/
                    $scope.orderId = orderData.data;
                    $state.go("main.brandApp.settlement", {
                        orderId: $scope.orderId,
                        from: "CART"
                    }, {reload: true})

                }, function () {

                }
            );
        };
        $scope.openDialog = function () {
            $scope.alert = '';
            $mdBottomSheet.show({
                template: cart,
                clickOutsideToClose: true,
                fullscreen: false,
                controllerAs: "vmd",
                bindToController: true,
                controller: ['$mdBottomSheet', function ($mdBottomSheet) {
                    var vmd = this;
                    //-------------------------------------------------------获取购物车列表


                    vmd.editShow=false;
                    vmd.getList = function () {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/item",
                            params: {
                                // type: 'REFUND'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                vmd.comments = resp.data.data;
                                vmd.totlePrice = 0;
                                vmd.totleNum = 0;
                                if (!resp.data.data) {
                                    vmd.editShow=false;
                                    // console.log('未添加');
                                    vmd.totlePrice = 0;
                                    vmd.totleNum = 0;
                                    $scope.totlePrice = vmd.totlePrice;
                                    $scope.totleNum = vmd.totleNum;
                                    return;
                                }else{
                                    vmd.editShow=true;
                                }
                                // for (var i = 0; i < vmd.comments.length; i++) {
                                //     vmd.totlePrice += (Number(vmd.comments[i].sku.salePrice) * vmd.comments[i].num);
                                //     vmd.totleNum += vmd.comments[i].num;
                                //     // console.log('000===', vmd.totleNum)
                                // }
                                vmd.getCheck = function (name) {
                                    if (vmd.comments != null) {
                                        for (var i = 0; i < vmd.comments.length; i++) {
                                            vmd.comments[i].checked = name;
                                            vmd.totlePrice += (Number(vmd.comments[i].sku.price) * vmd.comments[i].num);
                                            vmd.totleNum += vmd.comments[i].num;
                                        }
                                    }
                                }
                                vmd.getCheck(true);
                                //全选
                                vmd.checkAll = true;
                                vmd.checkedAll = function () {
                                    vmd.checkAll = !vmd.checkAll;
                                    if (!vmd.checkAll) {   //全选
                                        vmd.getCheck(false);
                                    } else {
                                        vmd.getCheck(true);
                                    }
                                };

                                $scope.totlePrice = vmd.totlePrice;
                                $scope.totleNum = vmd.totleNum;
                            }, function () {

                            }
                        );
                    };
                    vmd.getList();
                    //编辑
                    vmd.isEdit = true;
                    vmd.changeEdit = function () {
                        vmd.isEdit = !vmd.isEdit;
                        //最终吊取购物车列表接口
                        vmd.getList();
                    };
                    //选中
                    vmd.changeCheck = function (item) {
                        item.checked = !item.checked;
                        //判断是否全选中
                        var numFasle=0;
                        if (vmd.comments != null) {
                            for (var i = 0; i < vmd.comments.length; i++) {
                                if (!vmd.comments[i].checked ) {    //全选中
                                    numFasle=numFasle+1;       //一个fasle
                                }
                            }
                            if(numFasle==vmd.comments.length){
                                vmd.checkAll = false;
                            }else{
                                vmd.checkAll = true;
                            }
                        }
                    };
                    //点击删除   1-判断是否是全删   2-判断是否为部分删除
                    vmd.deleteCar = function () {
                        if (!vmd.checkAll) {      //    情况1
                            vmd.deleteOrder();
                        } else {
                            // console.log('commments', vmd.comments);    //走部分删除
                            var cartIdItems=[];
                            for(var i = 0; i < vmd.comments.length; i++){
                                if(!vmd.comments[i].checked){
                                    // console.log(2,vmd.comments[i].sku.skuId);
                                    cartIdItems.push(vmd.comments[i].sku.skuId);
                                }
                            }
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/removeCart",
                                params: {
                                    cartItems: cartIdItems,
                                    type: 'PURCHASE'
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                }
                            }).then(function (resp) {
                                    // 成功
                                    vmd.getList();
                                }, function () {
                                    //error
                                }
                            );
                        }
                    };


                    // 当输入框为null时失去焦点事件
                    vmd.numBlur = function (item) {
                        // console.log(222222213456, item.num);
                        if (item.num > item.sku.storage) {
                            alertService.msgAlert("exclamation-circle", "当前规格库存不足");
                            item.num = item.sku.storage; //todo------------------
                        }
                        if (item.num == null || vmd.skuNum == '' || item.num == '' || item.num <= 0) {
                            // console.log('qw');
                            item.num = 1;
                        }
                        if(!/^\d+$/.test(item.num)){
                            item.num = 1;
                            alertService.msgAlert("exclamation-circle", "请输入整数");
                        };
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/setNum",
                            params: {
                                num: item.num,
                                skuId: item.sku.skuId,
                                type: 'PURCHASE'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                // console.log(resp);
                                vmd.getList();
                            }, function () {
                                //error
                            }
                        );
                    };

                    //------------------------------------输入数量ng-change触发事件
                    vmd.formatNum = function (item) {
                        if (item.num == null || item.num == '') {
                            return;
                        } else if (item.num <= 0) {
                            item.num = 1;
                        } else if (item.num > item.sku.storage) {
                            alertService.msgAlert("exclamation-circle", "当前规格库存不足");
                            item.num = item.sku.storage; //todo------------------
                        }
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/setNum",
                            params: {
                                num: item.num,
                                skuId: item.sku.skuId,
                                // type: 'REFUND'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                vmd.getList();
                            }, function () {
                                //error
                            }
                        );
                    };
                    // ---------------------计算商品数量。进行加减
                    vmd.skuNumCount = function (num, item) {

                        if (item.num >= item.sku.storage && num == 1) {
                            return;
                        }
                        item.num += num;
                        if (item.sku.storage < item.num) {
                            alertService.msgAlert("exclamation-circle", "库存仅剩" + item.sku.storage + "件");
                            item.num = item.sku.storage;
                        }
                        if (item.num <= 0) {
                            item.num = 0;
                        }
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/setNum",
                            params: {
                                num: item.num,
                                skuId: item.sku.skuId,
                                // type: 'REFUND'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                vmd.getList();
                            }, function () {
                                //error
                            }
                        );
                    };

                    vmd.deleteOrder = function () {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/clearCart",
                            params: {
                                // type: 'REFUND'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                vmd.getList();
                                // $scope.$digest();
                            }, function () {
                                //error
                            }
                        );
                    };
                    // 生成订单
                    vmd.orderCreate = function () {
                        vmd.postParams = [];
                        if (vmd.comments) {
                            for (let i = 0; i < vmd.comments.length; i++) {
                                let tmpSku = {
                                    skuId: vmd.comments[i].sku.skuId,
                                    num: vmd.comments[i].num
                                };
                                vmd.postParams.push(tmpSku);
                            }
                        }
                        if (vmd.postParams.length < 1) {
                            // alertService.msgAlert("exclamation-circle", "您还没有选择任何商品哦");
                            return;
                        }

                        $http({
                            method: "POST",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order",
                            data: vmd.postParams,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                                // console.log(resp);
                                let orderData = resp.data;
                                /*.orderId*/
                                vmd.orderId = orderData.data;
                                $mdBottomSheet.hide().then(
                                    $state.go("main.brandApp.settlement", {
                                        orderId: vmd.orderId,
                                        from: "CART"
                                    }, {reload: true})
                                );

                            }, function () {

                            }
                        );
                    };
                    vmd.cancleDialog = function () {
                        $mdBottomSheet.hide()
                    };
                    vmd.submit = function () {
                        $mdBottomSheet.hide({
                            totlePrice: vmd.totlePrice,
                            skuData: vmd.comments,
                            postParams: vmd.postParams,
                        });
                    };
                    // $mdBottomSheet.hide({totlePrice: vmd.totlePrice});
                }]
            }).then(function (data) {

                // console.log('fffffffffffffffffff');
                if (data) {
                    $scope.totlePrice = data.totlePrice;
                    for (let i = 0; i < data.skuData.length; i++) {
                        for (let j = 0; j < data.skuData[i].sku.specs.length; j++) {
                            $scope.skuNum[data.skuData[i].sku.specs[j].valueId] = data.skuData[i].num;
                        }
                    }
                } else {
                    // console.log(2121212121212121);

                    $scope.getList();
                }
            })
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            var path = 'brandApp/' + $scope.brandAppId + '/home';
            window.location = conf.agencyRootUrl + path;
        };
        $scope.pageGo = function (url) {
            location.href = conf.agencyRootUrl + 'brandApp/' + $scope.brandAppId + url;
        }


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
    '$rootScope'
];

export default Controller ;
