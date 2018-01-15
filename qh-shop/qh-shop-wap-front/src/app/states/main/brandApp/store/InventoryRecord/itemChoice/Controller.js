import conf from "../../../../../../conf";
import cart from "!html-loader?minimize=true!./cart.html";



import 'swiper'
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $timeout,
    $mdBottomSheet,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$timeout,
                _$mdBottomSheet,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $timeout = _$timeout;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        $mdBottomSheet = _$mdBottomSheet;
        loginService.loginCtl(true, $location.absUrl());

        $scope.isClick = 0;


        $scope.com = new Array(3);


        $timeout(function () {
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
        },10);




        $scope.clickThis = function (num) {
            $scope.isClick = num;
            $scope.swiper.slideTo(num, 1000, false);
        };


        $scope.showCart = function (item) {
            $scope.openDialog()
        }


        $scope.openDialog = function () {
            $scope.alert = '';
            $mdBottomSheet.show({
                templateUrl: 'itemAdd.html',
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
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", {brandAppId: $stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$timeout',
    '$mdBottomSheet',
    '$http',
];

export default Controller ;
