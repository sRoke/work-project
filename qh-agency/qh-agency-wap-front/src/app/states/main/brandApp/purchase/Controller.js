import cart from "!html-loader?minimize=true!./cart.html";
import conf from "../../../../conf";

var $scope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    alertService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _alertService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        alertService=_alertService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.imgView = conf.imgView1;
        $scope.itemList = [];
        $scope.curPage = 0;
        $scope.pageEnd = true;
        $scope.getItemList = function (categoryId, about) {
            if (about != 'more') {
                $scope.curPage = 0;
            }
            console.log(categoryId);
            $scope.categoryId = categoryId;
            $http({
                method: 'GET',        ///brandApp/{brandAppId}/item/searchItem
                 url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111/item",
                //url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/skuStore/search",
                params: {
                    page: $scope.curPage,
                    size: conf.pageSize,
                    keyWord: $scope.keyWord,
                    categoryId: categoryId,
                    type: 'purchase'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // if(categoryId!='more'){
                //     $scope.itemList=[];     //如果不是加载更多分页，为类别筛选的话，进行清空;
                // }
                $scope.data = resp.data.data;
                if ($scope.data.number == '0') {
                    $scope.itemList = resp.data.data.content;
                    $scope.curPage = $scope.data.number + 1;
                    if ($scope.data.number + 1 < $scope.data.totalPages) {
                        $scope.pageEnd =true;
                    }else{
                        $scope.pageEnd =false;
                    }
                }
                else {
                    $scope.itemList = $scope.itemList.concat(resp.data.data.content);
                }


                if (about == 'more') {
                    if ($scope.data.number + 1 >= $scope.data.totalPages) {
                        $scope.pageEnd = false;
                        return;
                    }
                    $scope.curPage = $scope.data.number + 1;
                }

            })
        };
        $scope.getItemList();

        $scope.getBtnList = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/category/getCategoryList",
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
                //$scope.getItemList(null);
            })

        };
        $scope.getBtnList();


        $scope.btnClick = function (btn) {
            if (btn == null) {
                $scope.check = true;
                for (var i = 0; i < $scope.btnList.length; i++) {
                    $scope.btnList[i].check = false;
                }
                $scope.getItemList(null);
            }
            else {
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
        //获取购物车列表
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart",
                params: {
                    type: 'PURCHASE'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.comments = resp.data.data;
                    $scope.totlePrice = 0;
                    if (!resp.data.data) {
                        console.log('未添加');
                        $scope.totlePrice = 0;
                        return;
                    }
                    for (let i = 0; i < $scope.comments.length; i++) {
                        $scope.totlePrice += (Number($scope.comments[i].sku.price) * $scope.comments[i].num);
                    }

                }, function () {

                }
            );
        };
        $scope.getList();
        // 生成订单
        $scope.orderCreate = function () {
            $scope.postParams = [];
            if ($scope.comments) {
                for (let i = 0; i < $scope.comments.length; i++) {
                    let tmpSku = {
                        skuId: $scope.comments[i].sku.skuId,
                        num: $scope.comments[i].num
                    };
                    $scope.postParams.push(tmpSku);
                }
            }
            if ($scope.postParams.length < 1) {
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
                    $state.go("main.brandApp.order.checkOrder", {
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
                    vmd.warmchange = false;
                    vmd.warnView = function () {

                        vmd.warmchange = true;
                        $timeout(function () {
                            vmd.warmchange = false;
                        }, 1000);
                    };
                    vmd.editShow=false;
                    vmd.getList = function () {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart",
                            params: {
                                type: 'PURCHASE'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            console.log('resp.data.data', resp.data.data);
                                vmd.comments = resp.data.data;
                                vmd.totlePrice = 0;
                                if (!resp.data.data) {
                                    vmd.editShow=false;
                                    console.log('未添加');
                                    $scope.totlePrice = 0;
                                    return;
                                }else{
                                    vmd.editShow=true;
                                }
                                vmd.getCheck = function (name) {
                                    if (vmd.comments != null) {
                                        for (var i = 0; i < vmd.comments.length; i++) {
                                            vmd.comments[i].checked = name;
                                            vmd.totlePrice += (Number(vmd.comments[i].sku.price) * vmd.comments[i].num);
                                        }
                                    }
                                }
                                vmd.getCheck(true);
                                //全选
                                vmd.checkAll = true;
                                vmd.checkdAll = function () {
                                    vmd.checkAll = !vmd.checkAll;
                                    if (!vmd.checkAll) {   //全选
                                        vmd.getCheck(false);
                                    } else {
                                        vmd.getCheck(true);
                                    }
                                };
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
                            console.log('commments', vmd.comments);    //走部分删除
                            var cartIdItems=[];
                            for(var i = 0; i < vmd.comments.length; i++){
                                if(!vmd.comments[i].checked){
                                    console.log(2,vmd.comments[i].sku.skuId);
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


                    // 计算商品数量。进行加减
                    vmd.skuNumCount = function (num, item) {
                        console.log(111111, item.num);
                        vmd.warnView();
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
                                type: 'PURCHASE'
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
                    // 当输入框为null时失去焦点事件
                    vmd.numBlur = function (item) {
                        console.log(222222213456, item.num);
                        if (item.num > item.sku.storage) {
                            alertService.msgAlert("exclamation-circle", "当前规格库存不足");
                            item.num = item.sku.storage; //todo------------------
                        }
                        if (item.num == null || vmd.skuNum == '' || item.num == '' || item.num <= 0) {
                            console.log('qw');
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
                                //console.log(resp);
                                vmd.getList();
                            }, function () {
                                //error
                            }
                        );
                    };
                    vmd.deleteOrder = function () {
                        // vmd.deleteParams = [];
                        // if (vmd.comments) {
                        //     for (let i = 0; i < vmd.comments.length; i++) {
                        //         let deleteSku = {
                        //             skuId: vmd.comments[i].sku.skuId,
                        //             num: 0
                        //         };
                        //         vmd.deleteParams.push(deleteSku);
                        //     }
                        // }
                        // if (vmd.deleteParams.length < 1) {
                        //     // alertService.msgAlert("exclamation-circle", "您还没有选择任何商品哦");
                        //     return;
                        // }
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/cart/clearCart",
                            params: {
                                type: 'PURCHASE'
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
                                let orderData = resp.data;
                                /*.orderId*/
                                vmd.orderId = orderData.data;
                                $mdBottomSheet.hide().then(
                                    $state.go("main.brandApp.order.checkOrder", {
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
                if (data) {
                    $scope.totlePrice = data.totlePrice;
                    for (let i = 0; i < data.skuData.length; i++) {
                        for (let j = 0; j < data.skuData[i].sku.specs.length; j++) {
                            $scope.skuNum[data.skuData[i].sku.specs[j].valueId] = data.skuData[i].num;
                        }
                    }
                }
            })
        };

        // $scope.searchItem = function () {
        //     $http({
        //         method: 'GET',
        //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/skuStore/search",
        //         params: {
        //             keyWord: $scope.keyWord,
        //             type:"purchase"
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //             "brandApp-Id": $scope.brandAppId
        //         }
        //     }).then(function (resp) {
        //         $scope.itemList = resp.data.data.content;
        //
        //         //计算最低价格　
        //         $scope.minPrice = '';
        //
        //     })
        // };
        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.home", null, {reload: true});
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
    'alertService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location'
];

export default Controller ;
