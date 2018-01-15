import conf from "../../../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;

        $scope.brandAppId = $stateParams.brandAppId;
        loginService.loginCtl(true);
        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        // loginService.loginCtl(true);


        $scope.pageTitle = '全部订单';
        $scope.checkTabsShow = false;
        $scope.checkTabs = function (index) {
            if(!index){
                $scope.checkTabsShow = !$scope.checkTabsShow;
                return;
            }
            $scope.pageTitle = index;
            $scope.checkTabsShow = !$scope.checkTabsShow;
            // console.log($scope.pageTitle);
            if(index == '全部'){
                $scope.status = [];
            }else if(index == '待付款'){
                $scope.status = ['UNPAYED']; //["待付款"]
            }else if(index == '待发货'){
                $scope.status = ['UNCONFIRMED','UNSHIPPED'];//[ "待确认接单","待发货"]
            }else if(index == '待收货'){
                $scope.status = ['UNRECEIVED'];//["待收货","UNRECEIVED", "待自提"]
            }else if(index == '已完成'){
                $scope.status = ['CLOSED','FINISHED','CANCELED','REJECTED'];//["已关闭","已完成","已取消","卖家拒绝接单"]
            }
            $scope.pageChange(0);
        };

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = [];
            $scope.pageChange(0);
        };

        $scope.focus = function (status) {
            if(status){
                $scope.searchShow = true;
            }else {
                $scope.searchShow = false;
            }
        };



        $scope.currpage = 0;
        $scope.pageChange = function (currpage) {
            $scope.currpage = currpage;
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order',
                params: {
                    page:currpage,
                    size:conf.pageSize,
                    keyWord:$scope.keyWord,
                    status:$scope.status,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if(currpage){
                    $scope.orderList.number = resp.data.data.number;
                    for (var i=0;i< resp.data.data.content.length;i++){
                        $scope.orderList.content.push(resp.data.data.content[i])
                    }
                }else {
                    $scope.orderList = resp.data.data;
                }
                // console.log('11111111111111111111111111',$scope.orderList);

            }, function (resp) {
                //TODO 错误处理
            });
        };


        // $scope.pageChange($scope.currpage);
        if($stateParams.status == 'UNPAYED'){
            $scope.checkTabs('待付款');
            $scope.checkTabsShow = false;
        }else if($stateParams.status == 'UNSHIPPED'){
            $scope.checkTabs('待发货');
            $scope.checkTabsShow = false;
        }else {
            $scope.checkTabs('全部');
            $scope.checkTabsShow = false;
        }




        //确认拒绝接单
        $scope.confirmOrder = function (orderId,status) {
            // console.log('1233213123123',orderId);
            if(status){
                alertService.confirm(null,'确认接单?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/'+ orderId + '/confirmOrder',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log('resp-------------',resp);
                            if(resp.data.status == 10011){
                                alertService.msgAlert("exclamation-circle",resp.data.data);
                            }
                            $scope.pageChange($scope.currpage);
                        }, function (resp) {
                            //TODO 错误处理
                            if(resp.data.status == 10026){
                                alertService.confirm(null,'请先绑定收款支付宝帐号','温馨提示',null,'去绑定').then(function (data) {
                                    if(data){
                                        $state.go("main.brandApp.store.shop.setPay", {form:'main.brandApp.store.order.orderCenter'}, {reload: true});
                                    }
                                });
                            }else{
                                alertService.msgAlert("exclamation-circle", resp.data.message);
                            }
                        });
                    }
                });
            }else {
                alertService.confirm(null,'拒绝接单?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/order/'+ orderId + '/rejectOrder',
                            params: {
                                memo:null,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log('resp-------------',resp);
                            $scope.pageChange($scope.currpage);
                        }, function (resp) {
                                alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    }
                });
            }
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", null, {reload: true});
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    'loginService',
    '$state'
];

export default Controller ;
