import cart from "!html-loader?minimize=true!./cart.html";
import conf from "../../../../../conf";

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
    alertService;
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
                _alertService) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.discount = false;
        $scope.goPay = false;

        $scope.alert = function (num) {
            if (num == '1') {
                $scope.inputAlert('自定义整单折扣', '例如九五折,请输入9.5', '1');
            } else if (num == '2') {
                $scope.inputAlert('自定义减价', '请输入金额', '2');
            }

        };
        $scope.inputAlert = function (title, ts, status) {
            $mdDialog.show({
                template: cart,
                parent: angular.element(document.body).find('#qh-agency-wap-front'),
                controllerAs: "vm",
                clickOutsideToClose: true,
                fullscreen: false,
                controller: ['$scope', '$mdDialog', '$rootScope', "$interval", 'alertService','$filter',function ($scope, $mdDialog, $rootScope, $interval, alertService,$filter) {
                    var vm = this;
                    vm.title = title;
                    vm.ts = ts;
                    vm.status = status;
                    vm.cancel = function () {
                        $mdDialog.cancel();
                    };

                    vm.checkValue = function(){
                        // if(vm.status == '1'){
                        //     $filter('num')(vm.inputText)
                        // }else if(vm.status == '2'){
                        //     $filter('num')(vm.inputText)
                        // }

                        // ^[0-9]+(.[0-9]{2})?$

                        // if (/([1-9]\d*|0)+(\.\d{0,2})?$/.test(vm.inputText)) {
                        //    console.log('true');
                        // } else {
                        //     alertService.msgAlert("exclamation-circle", "请输入正确的折扣");
                        // }
                    };

                    vm.checkSubmit = function () {
                        if (vm.status == '1') {
                            if (/^[0-9]\.[0-9]$/.test(vm.inputText) || /^[1-9]$/.test(vm.inputText)) {
                                // console.log('折扣')
                                $mdDialog.hide(vm.inputText);
                            } else {
                                alertService.msgAlert("exclamation-circle", "请输入正确的折扣");
                            }
                        } else if (vm.status == '2') {
                            if (/^[0-9]+(\.\d{1,2})?$/.test(vm.inputText)) {
                                $mdDialog.hide(vm.inputText);
                                // console.log('减价');
                            } else {
                                alertService.msgAlert("exclamation-circle", "请输入正确的减价");
                            }
                        }

                    };
                }],
                //parent: '.ks-main '
            }).then(function (data) {
                if (data) {
                    if (status == '1') {
                        // if(data*10 < 70){
                        //     alertService.msgAlert("exclamation-circle", "折扣不能低于7折");
                        // }else {
                            $scope.setDiscount(data * 10, 0);
                        // }
                        // console.log('折扣', data)
                    } else if (status == '2') {
                        // if(data > $scope.data.orderPrice/100*0.3){
                        //     alertService.msgAlert("exclamation-circle", "减价金额不能高于总价30%");
                        // }else {
                            $scope.setDiscount(0, data * 100);
                        // }
                        // console.log('减价', data)
                    }
                }
            }, function (error) {
                // console.log('222', error)
            });
        };


        $scope.chooseDiscount = function () {
            $scope.discount = !$scope.discount;
        };


        //限制输入位数
        $scope.checkValue = function () {
          if($scope.telPhone.length>11){
              $scope.telPhone = $scope.telPhone.substr(0,$scope.telPhone.length-1)
          }
        };

        //--------------------------------------删除优惠

        $scope.delDiscount = function () {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $stateParams.orderId + '/calculationPrice',
                params: {
                    discount: null,  //折扣
                    reducePrice: null, //减价
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.getData();
                }, function () {

                }
            );
        };


        //------------------------------------------订单详情
        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $stateParams.orderId + '/detail',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.data = resp.data.data;
                    for (let i in  $scope.data.items) {
                        if ($scope.data.items[i].num > $scope.data.items[i].skuInfo.storage) {
                            $scope.goPay = false;
                            return;
                        } else {
                            $scope.goPay = true;
                        }
                    }

                    if ($scope.data.activityType == 'DISCOUNT') { //折扣
                        $scope.discountNum =$scope.data.paymentPrice / $scope.data.orderPrice * 10;
                        // $scope.discountNum = Math.round($scope.data.paymentPrice / $scope.data.orderPrice * 100)/10;
                        $scope.discountSpec = '整单' + $scope.data.discount/10 + '折';
                    } else if ($scope.data.activityType == 'REDUCE') {
                        $scope.discountSpec = '减价' + $scope.data.reducePrice / 100 + '元';
                    }


                    // console.log('resp', resp);
                }, function () {

                }
            );
        };
        $scope.getData();
        //-------------------------------------------设置优惠

        $scope.setDiscount = function (discount, reducePrice) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $stateParams.orderId + '/calculationPrice',
                params: {
                    discount: discount ? discount : null,  //折扣
                    reducePrice: reducePrice ? reducePrice : null, //减价
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log(resp)
                if(resp.data.status == 200){
                    $scope.getData();
                }else if(resp.data.status == 1046){
                    alertService.msgAlert("exclamation-circle",resp.data.message);
                }
                }, function (error) {
                }
            );
        };


        //-----------------------------------------------------收款
        $scope.clickOrderCreate = false;
        $scope.orderCreate = function () {
            if ($scope.clickOrderCreate) {
                return;
            } else {
                $scope.clickOrderCreate = true;
            }
            //手机号验证
            if ($scope.telPhone && !(/^1[34578]\d{9}$/.test($scope.telPhone)) ) {
                alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                $scope.clickOrderCreate = false;
                return false;
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $stateParams.orderId + '/create',
                data: {
                    cellphone:$scope.telPhone,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.pay($stateParams.orderId);
                ///backurl 转到我的订单页面unionOrder
                // console.log(resp);
                // let back = '#/brandApp/' + $scope.brandAppId + '/cashier';
                // location.href = `${conf.payUrl}${resp.data.data}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
                }, function () {
                    $scope.clickOrderCreate = false;
                }
            );
        };


        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // 跳转到支付页
                /////backurl 转到我的订单页面unionOrder
                if(resp.data.data){
                    // alert(resp.data.data);
                    // console.log(resp.data.data);
                    let back = 'brandApp/' + $scope.brandAppId + '/cashier';
                    location.href = `${conf.payUrl}${resp.data.data}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
                }else {
                    // console.log(resp.data.data);
                }
            }, function (resp) {
                //TODO 失败页
                $scope.clickOrderCreate = false;
            });
        }

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.cashier", null, {reload: true});
            } else {
                history.back();
            }
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
    'alertService'
];

export default Controller ;
