import conf from "../../../../conf";

var $scope,
    alertService,
    $http,
    loginService,
    $stateParams,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _alertService,
                _$http,
                _loginService,
                _$stateParams,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        loginService = _loginService;
        alertService = _alertService;
        $http = _$http;
        $stateParams = _$stateParams;
        $location = _$location;
        $scope.brandAppId = $stateParams.brandAppId;

        var vm = this;
        ////////////////////////////////////////
        const TAG = "main/cart ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true,$location.absUrl());


        $scope.editCartClick = false;

        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/111/cart",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.data = resp.data.data;
                }, function () {

                }
            );
        };
        $scope.getList();

        $scope.edit = function () {
            $scope.editCartClick = !$scope.editCartClick;
        };

        // 对当前购物车数量进行增加和减少
        $scope.add = function (cartItem, count) {
            cartItem.num += count;
            if (cartItem.num > cartItem.sku.storage) {
                cartItem.num = cartItem.sku.storage;
                alertService.msgAlert("cancle", "库存不足");
                return;
            }
            let num = cartItem.num;
            if (num == 0) {
                cartItem.num = 1;
                return;
            }
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/111/cart/setNum",
                data: [{
                    num: num,
                    skuId: cartItem.sku.skuId
                }],
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.checkStorage(true);
                    $scope.calcTotalPrice();
                }, function () {
                    //error
                }
            );
        };

        ////
        $scope.editNum = function (cartItem) {
            $http({
                method: "POST",
                url: conf.apiPath + "/cart/setNum",
                data: {
                    num: cartItem.num,
                    skuId: cartItem.sku.skuId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    //$scope.getList();
                    $scope.checkStorage(true);
                    $scope.calcTotalPrice();
                    // 重新计算价格（要求当前商品是选中状态）
                }, function () {
                }
            );
        };
        $scope.checkStorage = function (boo) {
            if ($scope.data && $scope.data.items) {
                for (var i = 0; i < $scope.data.items.length; i++) {
                    if (!boo) {
                        // 编辑状态可以进行解除 选中
                        if ($scope.data.items[i].sku.storage < $scope.data.items[i].num) {
                            $scope.data.items[i].isSelected = true;
                            $scope.data.items[i].selected = false;
                        } else {
                            $scope.data.items[i].isSelected = false;
                        }
                    } else {
                        $scope.data.items[i].isSelected = false;
                    }
                }
            }
            // 重新计算一下 防止出错
            $scope.calcTotalPrice();
        };


        $scope.numberTotal = 0;
        $scope.totalPrice = 0;
        // 重新计算价格（要求当前商品是选中状态）
        $scope.calcTotalPrice = function (boo) {
            var totalPrice = 0; //总价
            var numberTotal = 0; //总数量
            if ($scope.data && $scope.data.items) {
                for (var i = 0; i < $scope.data.items.length; i++) {
                    var cartItem = $scope.data.items[i];
                    if (cartItem.selected) {
                        totalPrice += cartItem.sku.price * cartItem.num;
                        numberTotal += 1;
                    }
                }
            }
            $scope.numberTotal = numberTotal;
            $scope.totalPrice = totalPrice;
            // 选择全选的则不用在进来判断了
            if (!boo) {
                $scope.clickAllChanged();
            }
        };
        vm.allSelected = false;
        // 判断全选是否已经选择中
        $scope.clickAllChanged = function () {
            var boo = true;
            if ($scope.data && $scope.data.items) {
                for (var i = 0; i < $scope.data.items.length; i++) {
                    if (!$scope.data.items[i].selected) {
                        boo = false;
                        break;
                    }
                }
            }
            vm.allSelected = boo;
        };

        /*
         * 全选
         * */
        //对当前商品进行全选
        // 是否全部选中
        vm.allSelected = false;
        $scope.selectAllChanged = function () {
            if ($scope.data && $scope.data.items) {
                for (var i = 0; i < $scope.data.items.length; i++) {
                    if ($scope.data.items[i].isSelected) {
                        continue;
                    }
                    if (vm.allSelected === true) {
                        $scope.data.items[i].selected = true;
                    } else if (vm.allSelected === false) {
                        $scope.data.items[i].selected = false;
                    }
                }
            }
            $scope.calcTotalPrice(true);
        };


        /*
         * 结算
         * */
        $scope.removeOrCreate = function (ev) {
            if ($scope.editCartClick) {
                $scope.removeCartItem(ev);
            } else {
                $scope.orderCreate();
            }
        };

        //移除购物车商品
        $scope.removeCartItem = function () {
            let postParams = [];

            if ($scope.data.items) {
                for (var i = 0; i < $scope.data.items.length; i++) {
                    if ($scope.data.items[i].selected) {
                        let tmpSku = {
                            skuId: $scope.data.items[i].sku.skuId,
                            num: 0
                        };
                        postParams.push(tmpSku);
                    }
                }
            }
            if (postParams.length < 1) {
                alertService.msgAlert("exclamation-circle", "请选择要删除的商品!");
                return;
            }
            alertService.confirm(null, null, "确认删除", "取消", "确认").then(function (data) {
                if (!data) {
                    return;
                }
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/111/cart/setNum",
                    data: postParams,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                    notShowError: true
                }).success(function () {
                    $scope.numberTotal = 0;
                    $scope.totalPrice = 0;
                    $scope.getList();
                }).error(function () {
                    $scope.numberTotal = 0;
                    $scope.totalPrice = 0;
                });
            });

        };

        // 生成订单
        $scope.orderCreate = function () {
            let postParams = [];
            if ($scope.data.items) {
                for (let i = 0; i < $scope.data.items.length; i++) {
                    if ($scope.data.items[i].selected) {
                        let tmpSku = {
                            skuId: $scope.data.items[i].sku.skuId,
                            num: $scope.data.items[i].num
                        };
                        postParams.push(tmpSku);
                    }
                }
            }
            if (postParams.length < 1) {
                alertService.msgAlert("exclamation-circle", "您还没有选择任何商品哦");
                return;
            }
            $http({
                method: "POST",
                url: conf.apiPath + "/order/check",
                data: postParams,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    let orderData = resp.data;
                    $scope.orderId = orderData.data.orderId;
                    $state.go("main.brandApp.order.checkOrder", {orderId: $scope.orderId, from: "CART"}, {reload: true});
                }, function () {

                }
            );
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'alertService',
    '$http',
    'loginService',
    '$stateParams',
    '$state',
    '$location',
];

export default Controller ;
