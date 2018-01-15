import conf from "../../../../../conf";
import Clipboard from "clipboard"
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
                _authService,
                _$rootScope) {
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
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        // authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId).then(function (data) {
        //     console.log('55555555555555', data)
        //     $scope.viewShow = data;
        // });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        var storage = window.localStorage;
        $scope.cartId = storage.getItem("cartId");

        $scope.fromUrl = $stateParams.fromUrl;
        $scope.cartItems = [];  //定义删除商品数组,,存skuId

        $scope.cartSubmit = [];  //定义删除商品数组,,存skuId


        //定义数组函数,查询元素在数组中的位置,如果有返回i,没有就返回-1
        Array.prototype.indexOf = function (val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) return i;
            }
            return -1;
        };
        //定义数组删除函数,根据查询道德index,删除数组中的指定元素
        Array.prototype.remove = function (val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
        $scope.deleteSku = function (sku) {
            //通过此方法就可以调用删除
            $scope.cartItems.remove(sku);
            // console.log('$scope.cartItems', $scope.cartItems)
        };


        $scope.totalPrice = 0;


        // 控制左上角编辑保存
        $scope.edit = false;
        $scope.goEdit = function () {
            $scope.edit = true;
        };

        $scope.goSave = function () {
            $scope.edit = false;
        };

        $scope.getCart = function () {
            if (loginService.getAccessToken()) {
                // console.log(1);
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart",
                    params: {
                        cartId: $scope.cartId,
                        type: 'MALL'
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function (resp) {
                    // console.log('resp---order--create', resp);
                    $scope.com = resp.data.data;

                    if ($scope.com) {
                        $scope.countTotalPrice()
                    }

                    //$scope.data = resp.data.data;
                }, function (resp) {
                    //error
                });
            } else {
                // console.log(2);
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart",
                    params: {
                        cartId: $scope.cartId,
                        type: 'MALL'
                    },
                    headers: {
                        // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function (resp) {
                    // console.log('resp---order--create', resp);
                    $scope.com = resp.data.data;

                    if ($scope.com) {
                        $scope.countTotalPrice()
                    }

                    //$scope.data = resp.data.data;
                }, function (resp) {
                    //error
                });
            }

        };
        $scope.getCart();  //获取购物车信息


        // $scope.com = [
        //     {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     },
        //     {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     }, {
        //         imgs: 'https://assets.servedby-buysellads.com/p/manage/asset/id/28536',
        //         title: '超级啥地方还是返回阿阿斯顿发斯蒂芬速度发放',
        //         price: '199',
        //         sku: 'asdfasf',
        //         oldPrice: '520',
        //         check: false,
        //     },
        // ]


        //单个选中
        $scope.changeCheck = function (lis) {
            // console.log('changeCheck', lis);
            lis.check = !lis.check;  //切换选中状态

            let submitData = {
                skuId: lis.sku.skuId,
                num: lis.num
            };
            // console.log('submitData', submitData);

            if (lis.check) {  //如果选中,push  id
                $scope.cartItems.push(lis.sku.skuId);
                $scope.cartSubmit.push(submitData);
                // console.log('submitData', submitData);
                // console.log('$scope.cartSubmit', $scope.cartSubmit);

            } else {  //否则删除id
                $scope.deleteSku(lis.sku.skuId);
                // $scope.deleteSku(submitData);

                for (var i = 0; i < $scope.cartSubmit.length; i++) {
                    // console.log('submitData.skuId', submitData.skuId);
                    // console.log('$scope.cartSubmit[i].skuId ', $scope.cartSubmit[i].skuId);

                    if ($scope.cartSubmit[i].skuId == submitData.skuId) {

                        $scope.cartSubmit.splice(i, 1)
                    }
                }

                // $scope.cartSubmit.remove(submitData);

                // console.log('submitData', submitData);
            }


            for (var i = 0; i < $scope.com.length; i++) {
                $scope.checkTrue = false;  //判断是否满足全选状态
                if (!$scope.com[i].check) {
                    $scope.checkTrue = false;
                    break
                } else {
                    $scope.checkTrue = true;
                }
            }

            if ($scope.checkTrue) {
                $scope.allCheck = true;
            } else {
                $scope.allCheck = false;
            }

            $scope.countTotalPrice();  //计算选中

        };

        //全选
        $scope.allCheck = false;  //定义全选状态
        $scope.changeAllCheck = function () {
            $scope.allCheck = !$scope.allCheck;  //切换状态
            $scope.cartSubmit = [];
            $scope.cartItems = [];
            for (var i = 0; i < $scope.com.length; i++) {

                $scope.submitData = {
                    skuId: $scope.com[i].sku.skuId,
                    num: $scope.com[i].num
                };

                if ($scope.allCheck) {
                    $scope.com[i].check = true;
                    $scope.cartItems.push($scope.com[i].sku.skuId);  //如果选中,将id push到数组中为删除商品做准备
                    $scope.cartSubmit.push($scope.submitData);
                }
                else {
                    $scope.com[i].check = false;
                    $scope.deleteSku($scope.com[i].sku.skuId);   //如果取消选中,则删除这个id
                    $scope.cartSubmit.length = 0;
                }
            }
            $scope.countTotalPrice();  //每选中一次计算一次
        };


        //商品数量加减
        $scope.skuNumCount = function (numStatus, list) {
            if (numStatus == -1) {
                list.num -= 1;
                if (list.num <= 0) {
                    list.num = 0;

                }
            } else {
                list.num += 1;
            }


            if (list.sku.storage < list.num) {
                alertService.msgAlert("exclamation-circle", "库存仅剩" + list.sku.storage + "件");
                list.num = list.sku.storage
            }

            // console.log('list', list);
            $scope.addCartNum(list);
            $scope.countTotalPrice();  //数量每变一次计算一次和
        };


        $scope.addCartNum = function (list) {
            // $http({
            //     method: "PUT",
            //     url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart",
            //     params: {
            //         num: list.num,
            //         skuId: list.sku.skuId,
            //         type: 'MALL',
            //         cartId: storage.getItem("cartId")
            //
            //     },
            //     headers: {
            //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "brandApp-Id": $scope.brandAppId
            //     },
            // }).then(function (resp) {
            //     console.log('resp---order--create', resp);
            //     //$scope.data = resp.data.data;
            // }, function (resp) {
            //     //error
            // });

            if (loginService.getAccessToken()) {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart",
                    params: {
                        num: list.num,
                        skuId: list.sku.skuId,
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
                    $scope.getCart();
                }, function (resp) {
                    //error
                });
            } else {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart",
                    params: {
                        num: list.num,
                        skuId: list.sku.skuId,
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

                    $scope.getCart();
                    //$scope.data = resp.data.data;
                }, function (resp) {
                    //error
                });
            }
        }


        //计算合计金额
        $scope.countTotalPrice = function () {
            $scope.price = [];  //存入金额

            //计算金额
            for (let i = 0; i < $scope.com.length; i++) {
                if ($scope.com[i].check) {
                    $scope.price[i] = $scope.com[i].num * $scope.com[i].sku.price;
                    // console.log('$scope.price', $scope.price);
                } else {
                    $scope.price[i] = 0;
                }
            }

            //计算金额和
            for (var i = 0, sum = 0; i < $scope.price.length; i++) {
                sum += $scope.price[i];
                // console.log(sum);
            }
            $scope.totalPrice = sum;   //赋值
            // console.log("use for:sum = " + sum);
            // console.log(' $scope.totalPrice ', $scope.totalPrice);

        };

        //去结算
        $scope.submit = function () {
            // console.log('$scope.cartItems', $scope.cartItems);
            // console.log('$scope.cartSubmit', $scope.cartSubmit);

            if (!$scope.cartSubmit || $scope.cartSubmit.length <= 0) {
                alertService.msgAlert("exclamation-circle", '请选择商品');
                return
            }

            $scope.orderCheckReq = [];
            $scope.orderCheckReq = $scope.cartSubmit;
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
                $scope.orderId = resp.data.data.orderId;
                if (resp.data.status == '200') {
                    $state.go("main.brandApp.store.confirmOrder", {
                        orderId: $scope.orderId,
                        from: "CART"
                    }, {reload: true});
                } else {
                    alertService.msgAlert("exclamation-circle", resp.data.exception);
                }
            }, function (resp) {
                //error
            });
        };


        //  /brandApp/{brandAppId}/shop/{shopId}/cart /removeCart    PUT    type(MALL) cartItems(skuId list)

        //删除购物车
        $scope.deleteItem = function () {

            // console.log('$scope.cartItems', $scope.cartItems);

            if ($scope.cartItems.length <= 0) {
                return
            }
            // $http({
            //     method: "PUT",
            //     url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/removeCart",
            //     params: {
            //         type: 'MALL',
            //         cartItems: $scope.cartItems
            //     },
            //     headers: {
            //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "brandApp-Id": $scope.brandAppId
            //     },
            // }).then(function (resp) {
            //     $scope.getCart();
            //
            // }, function (resp) {
            //     //error
            // });


            if (loginService.getAccessToken()) {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/removeCart",
                    params: {
                        cartItems: $scope.cartItems,
                        type: 'MALL',
                        cartId: storage.getItem("cartId")

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function (resp) {
                    $scope.getCart();
                }, function (resp) {
                    //error
                });
            } else {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/cart/removeCart",
                    params: {
                        cartItems: $scope.cartItems,
                        type: 'MALL',
                        cartId: storage.getItem("cartId")
                    },
                    headers: {
                        // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function (resp) {
                    $scope.getCart();

                    //$scope.data = resp.data.data;
                }, function (resp) {
                    //error
                });
            }


        };
        $scope.showTip = false;
        $scope.getNum = function () {
            ///brandApp/{brandAppId}/shop/{shopId}/home/getNum
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/" + $stateParams.storeId + '/home/getNum',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function (resp) {
                console.log('111111111111resp---order--create', resp);

                $scope.numData = resp.data.data;

                if($scope.numData.UNPAYED>0){
                    $scope.showTip = true;
                }

            }, function (resp) {
                //error
            });
        };
        $scope.getNum();

        $scope.closeTs = function () {
            $scope.showTip = false;
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
            $state.go("main.brandApp.store.item", {id: $stateParams.itemId}, {reload: true});
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
    '$rootScope'
];

export default Controller ;
