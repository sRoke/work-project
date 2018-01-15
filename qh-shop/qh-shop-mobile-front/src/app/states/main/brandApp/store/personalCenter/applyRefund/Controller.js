import conf from "../../../../../../conf";
import weui from 'weui.js';
var $scope,
    loginService,
    $state,
    alertService,
    $stateParams,
    $location,
    $rootScope,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _alertService,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        alertService=_alertService;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.orderId = $stateParams.orderID;
        $scope.skuId = $stateParams.skuID;
        // console.log(' $scope.skuId', $scope.skuId);
        loginService.loginCtl(true,$location.absUrl());
        //获取商品详情
        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/refund/sku/"+$scope.skuId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    orderId:$scope.orderId
                }
            }).then(function (resp) {
                // console.log('resp--info',resp.data.data);
                $scope.info=resp.data.data;
                $scope.num=$scope.info.skuInfo.num;
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        $scope.data={};
        $scope.data.selectType=false;
        $scope.openAdcDialog = function () {
            weui.picker([{
                label: '退货并退款',
                value: 'ITEM'
            }, {
                label: '仅退款',
                value: 'ONLYMONEY'
            }], {
                onChange: function (result) {
                    // console.log(result);
                },
                onConfirm: function (result) {
                    $scope.data.selectType=true;
                    $scope.refundType=result[0].value;
                    $scope.label=result[0].label;
                    $scope.$apply();
                    // console.log('result',$scope.refundType,$scope.label,$scope.data.selectType);
                }
            });
        };
       //提交退款申请
        $scope.btnSubmit=function () {
            if(!$scope.refundType){
                alertService.msgAlert("exclamation-circle", '请选择退款方式');
                return;
            }
            if(!$scope.price){
                alertService.msgAlert("exclamation-circle", '请输入退款金额');
                return;
            }
            if(($scope.price*100).toFixed(0)>$scope.info.skuInfo.allRealPayPrice){
                alertService.msgAlert("exclamation-circle", '申请退款金额不得大于商品支付金额');
                return;
            }
            $scope.req=[
                {
                    orderId:$scope.orderId,
                    skuId:$scope.skuId,
                    num:$scope.num,
                    reason:$scope.reason,
                    type:$scope.refundType,
                    price:($scope.price*100).toFixed(0),
                }
            ];
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/refund",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data:$scope.req,
            }).then(function (resp) {
                // console.log('resp--info',resp.data.data);
                if(resp.data.status=='200'){
                    $state.go("main.brandApp.store.personalCenter.refundOrder", {brandAppId:$stateParams.brandAppId}, {reload: true});
                }else{
                    alertService.msgAlert("exclamation-circle", resp.data.data);
                };
            }, function (resp) {
                //error
            });
        };
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.personalCenter.centerHome", {brandAppId:$stateParams.brandAppId}, {reload: true});
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
    'alertService',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
