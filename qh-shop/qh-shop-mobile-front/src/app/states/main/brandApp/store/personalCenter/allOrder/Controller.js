import conf from "../../../../../../conf";
var $scope,
    loginService,
    $state,
    alertService,
    $stateParams,
    $location,
    $rootScope,
    $http
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
        $scope.status=$stateParams.status;
        loginService.loginCtl(true,$location.absUrl());
        //初始化
        if($scope.status=='UNPAYED'){
            $scope.orderTitle='待付款';
            $scope.status=['UNPAYED'];
        }else if($scope.status=='UNSHIPPED'){
            $scope.orderTitle='待发货';      //待接单  待发货
            $scope.status=['UNCONFIRMED','UNSHIPPED'];
        }else if($scope.status=='UNRECEIVED'){
            $scope.orderTitle='待收货';    //待收货    待自提
            $scope.status=['UNRECEIVED','SINCEING'];
        }else{
            $scope.orderTitle='全部订单';
            $scope.status=[];
        };
        $scope.page=1;
        $scope.items=[];//定义接收分页时的各页数据;
        $scope.getInfo=function (page) {
            // console.log('page',page);
            if(page=='0' || page=='undefined'){
                $scope.items=[];
            };
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/mall/order",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    size:conf.pageSize,
                    page:page?page:$scope.page-1,
                    status:$scope.status,
                    keyWord:$scope.searchId,
                }
            }).then(function (resp) {
                // console.log('resp',resp);
                if(resp.data.status=='10026'){
                    $scope.noOrder=true;
                }else{
                    $scope.noOrder=false;
                    $scope.items= $scope.items.concat(resp.data.data.content);
                    $scope.dataNum=resp.data.data;
                    $scope.page=$scope.dataNum.number+1;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        $scope.checkTabs=function (status) {
            $scope.status=status;
            if($scope.status=='UNPAYED'){
                $scope.orderTitle='待付款';
                $scope.status=['UNPAYED'];
            }else if($scope.status=='UNSHIPPED'){
                $scope.orderTitle='待发货';
                $scope.status=['UNCONFIRMED','UNSHIPPED'];
            }else if($scope.status=='UNRECEIVED'){
                $scope.orderTitle='待收货';
                $scope.status=['UNRECEIVED','SINCEING'];
            }else if($scope.status=='FINISHED'){
                $scope.orderTitle='已完成';
                $scope.status=['FINISHED','CLOSED','REJECTED','CANCELED'];
            }else{
                $scope.orderTitle='全部订单';
                $scope.status=[];
            };
            $scope.checkTabsShow=!$scope.checkTabsShow;
            $scope.getInfo('0');
        };
        $scope.checkTabsShow=false;
        $scope.checkTab=function () {
            $scope.checkTabsShow=!$scope.checkTabsShow;
        };
        //支付
        $scope.pay = function (id) {
            // console.log('支付')
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('支付2',resp);
                // 跳转到支付页
                /////backurl 转到我的订单页面unionOrder
                let back = 'brandApp/' + $scope.brandAppId + "/store/"+$scope.storeId +"/personalCenter/allOrder";
                location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$scope.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
            }, function (resp) {
                //TODO 失败页
            });
        };
        /*
         * 取消订单
         * */
        $scope.canclOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/cancelOrder",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $scope.getInfo('0');
                    }, function (resp) {
                        // console.log('resp',resp);

                        if(resp.data.status == '10034'){
                            alertService.msgAlert("exclamation-circle", "商家已接单,不能取消");
                        }
                        //error
                    });
                }
            });
        };
        /*
         * 确认收获
         * */
        $scope.confirmReceive  = function (id) {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/confirmReceive",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $scope.getInfo('0');
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        /*
         * 确认已自提
         * */
        $scope.confirmSince  = function (id) {
            alertService.confirm(null, "", "确认已自提？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/order/" + id + "/confirmSince",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $scope.getInfo('0');
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
                $state.go("main.brandApp.store.personalCenter.centerHome", {brandAppId:$stateParams.brandAppId}, {reload: true});
            // } else {
            //     history.back();
            // }
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
