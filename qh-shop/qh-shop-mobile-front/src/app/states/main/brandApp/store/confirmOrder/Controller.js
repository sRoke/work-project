import conf from "../../../../../conf";
import weui from 'weui.js';
var $scope,
    $http,
    $state,
    $log,
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
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope,) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        authService = _authService;
        $stateParams = _$stateParams;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        $rootScope = _$rootScope;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.id = $stateParams.id;
        $scope.orderId = $stateParams.orderId;
        $scope.from = $stateParams.from;
        $scope.select = $stateParams.select;
        // console.log('$scope.from', $scope.from, $scope.orderId);
        $scope.isCheck = false;//到店自提
        $scope.isSelect = false;//快递配送
        // wx.openAddress({
        //     success: function (resp) {
        //         console.log('success',resp);
        //     // 用户成功拉出地址
        //     },
        //     cancel: function (resp) {
        //         console.log('error',resp);
        //     // 用户取消拉出地址
        //     }
        // })
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/order/" + $scope.orderId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.items = resp.data.data;
                // console.log($scope.items);
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //选择配送方式
        $scope.changeStyle = function (num) {
            if (num == 1) {    //自提
                $scope.isCheck = true;
                $scope.isSelect = false;
                $scope.orderType='SINCE';
            } else {         //快递
                $scope.isCheck = false;
                $scope.isSelect = true;
                $scope.orderType='MALL';
            }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/order/" + $scope.orderId+"/deliverType",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    orderSourceType: $scope.orderType,
                }
            }).then(function (resp) {
                $scope.getInfo();
            }, function (resp) {
                //error
            });


        };
        if ($scope.select == 'addr') {
            $scope.isCheck = false;//到店自提
            $scope.isSelect = true;//快递配送
        };
        //打开时间弹窗
        var vm = this;
        $scope.select = {};
        $scope.select.time = '请选择到店时间';
        var myTime = new Date();
        var day1 = new Date(myTime.getFullYear(), myTime.getMonth() + 1, 0);  //本月
        //获取天数：
        var daycount1 = day1.getDate();   //获取本月天数
        $scope.timeData=[];
        $scope.arr1 = {
            label: '',
            value:'',
            children: [],
        };
        $scope.arr2 = {
            label: '',
            value:'',
            children: [],
        };
        for (var i = myTime.getDate(); i <= daycount1; i++) {
            var label = i + '日';
            var value=i + '日';
            $scope.arr1.children.push({label: label,value:value});
        }
        $scope.arr1.label=myTime.getFullYear() + '年' + (myTime.getMonth() + 1) + '月';
        $scope.arr1.value=myTime.getFullYear() + '年' + (myTime.getMonth() + 1) + '月';
        $scope.timeData.push($scope.arr1);

        if (myTime.getMonth() + 2 > 12) {
            var day2=new Date((myTime.getFullYear() + 1),(myTime.getMonth() + 2 - 12),0);
            $scope.arr2.label=(myTime.getFullYear() + 1) + '年' + (myTime.getMonth() + 2 - 12) + '月';
            $scope.arr2.value=(myTime.getFullYear() + 1) + '年' + (myTime.getMonth() + 2 - 12) + '月';
        }else{
            day2=day1;
            $scope.arr2.label=myTime.getFullYear() + '年' + (myTime.getMonth() + 1) + '月';
            $scope.arr2.value=myTime.getFullYear() + '年' + (myTime.getMonth() + 1) + '月';
        }
        var daycount2 = day2.getDate();   //获取下月天数
        for (var i = 1; i <= daycount2; i++) {
            var label = i + '日';
            var value=i + '日';
            $scope.arr2.children.push({label: label,value:value});
        }
        $scope.timeData.push($scope.arr2);
        // console.log('daycount', $scope.timeData);
        $scope.openAdcDialog = function () {
            // weui.datePicker({
            //     start: new Date(),
            //     end: new Date().getFullYear(),
            //     defaultValue: [new Date().getFullYear(), new Date().getMonth()+1, new Date().getDate()],
            //     onConfirm: function(result){
            //         console.log('result',result[0].label);
            //         $scope.select.time=result[0].label+result[1].label+result[2].label;
            //         $scope.$apply();
            //         console.log('$scope.select.time',$scope.select.time);
            //     },
            //     id: 'ma_expect_date',
            //     className: 'ma_expect_date_picker'
            // });
            weui.picker($scope.timeData, {
                defaultValue: [myTime.getFullYear() + '年' + (myTime.getMonth() + 1) + '月', myTime.getDate()+'日'],
                onChange: function (result) {
                    // console.log(result);
                },
                onConfirm: function (result) {
                    // console.log('result------', result);
                    $scope.select.time=result[0].label+result[1].label;
                    $scope.$apply();

                }
            });
        };
        //选择地址
        $scope.selectAddress = function () {
            $state.go("main.brandApp.store.personalCenter.address", {
                orderId: $scope.orderId,
                from: $scope.from
            }, {reload: true});
        };
        $scope.submitOrder = function () {
            // SINCE("SINCE", "自提订单"),
            //     MALL("MALL", "线上订单");
            $scope.orderInfo = {};
            if (!$scope.isCheck && !$scope.isSelect) {
                alertService.msgAlert("exclamation-circle", "请选择配送方式");
                return;
            }
            ;
            //快递配送
            if ($scope.isSelect && !$scope.isCheck) {
                $scope.orderInfo = {
                    buyerMemo: $scope.memo,
                    sourceType: 'MALL',
                };
            }
            ;
            //到店自提
            if (!$scope.isSelect && $scope.isCheck) {
                if ($scope.select.time == '请选择到店时间') {
                    alertService.msgAlert("exclamation-circle", "请选择到店时间");
                    return;
                }
                $scope.orderInfo = {
                    buyerMemo: $scope.memo,
                    sourceType: 'SINCE',
                    sinceTakeTime: $scope.select.time,
                };
            }
            ;
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/order/" + $scope.orderId + "/commitOrder",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: $scope.orderInfo,
            }).then(function (resp) {
                // console.log('resp', resp);
                if (resp.data.status == '200') {
                    // console.log('//////')
                    $scope.pay($scope.orderId);
                }else{
                    alertService.msgAlert('cancle', resp.data.data);
                }
            }, function (resp) {
                //error
            });

        };
        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id) {
            // console.log('支付')
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('支付2', resp);

                    // 跳转到支付页
                    /////backurl 转到我的订单页面unionOrder
                    let back = 'brandApp/' + $scope.brandAppId + "/store/" + $scope.storeId + "/personalCenter/allOrder";
                    location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$scope.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;

            }, function (resp) {
                //TODO 失败页
                alertService.msgAlert('cancle', resp.data.message);
            });
        }

        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
            $state.go("main.brandApp.store.home", {brandAppId: $stateParams.brandAppId}, {reload: true});
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
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope',
];

export default Controller ;
