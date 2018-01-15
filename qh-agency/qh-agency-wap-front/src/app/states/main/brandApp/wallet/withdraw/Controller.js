import conf from "../../../../../conf";
var $scope,
    $state,
    $stateParams,
    loginService,
    alertService,
    $filter,
    $http,
    $location;

class Controller {
    constructor(_$scope, _$state, _$stateParams,_loginService,_alertService,_$filter,_$http,_$location) {
        $scope = _$scope;
        $state = _$state;
        $stateParams = _$stateParams;
        loginService = _loginService;
        alertService = _alertService;
        $filter=_$filter;
        $http=_$http;
        $location = _$location;
        $scope.brandAppId = $stateParams.brandAppId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
       // $scope.allCount=900;
        //初始化额度
        $scope.getMinCount=function(){
            $http({
                method: 'GET',
// <<<<<<< HEAD
// <<<<<<< HEAD
//                 url:conf.apiPath + '/brandCom/'+$scope.brandCompId+'/sysConf/withdrawalMinAmount',
// =======
//                 url:conf.apiPath + '/brandApp/'+$scope.brandCompId+'/sysconf/withdrawalMinAmount',
// >>>>>>> 代码重构
// =======
                url:conf.apiPath + '/brandApp/'+$scope.brandAppId+'/partner/111/sysConf/withdrawalMinAmount',
// >>>>>>> wap端brandCom改为brandApp
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(resp.data.data);
                //最小额度
                $scope.minCount = $filter('number')(resp.data.data.defaultValue/100,2).replace(/,/g, "");

            });
        };
        $scope.getMinCount();
        //$scope.minCount=500;
        //获取账户money
        $scope.getCount=function(){
            $http({
                method: 'GET',
                url:conf.apiPath + '/brandApp/'+$scope.brandAppId+'/partner/123456789/partnerAccount/info',
                params: {
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(resp.data.data);
                //可提现金额
                 $scope.count = resp.data.data.balance;
            });
        };
        $scope.getCount();

        $scope.check=false;
        $scope.checkMoney=function(money){
           // console.log(1111111,typeof money, typeof Number($scope.minCount));
            if(/^\d+(\.\d{1,2})?$/.test(money) && money>=Number($scope.minCount)){
                $scope.check=true;
            }else{
                $scope.check=false;
            };
            // if(/^\d+(\.\d{1,2})?$/.test(money)){
            //     $scope.txMoney = $scope.txMoney.substr(0, $scope.txMoney.length - 1);
            //     console.log(1111111111,$scope.txMoney);
            //     $scope.check=true;
            // }else{
            //     $scope.check=false;
            // };
        };
        $scope.getAll=function(){
            $scope.txMoney=$filter('number')($scope.count/100, 2).replace(/,/g, "");
           // console.log(111111111111111,typeof $scope.txMoney);
            if(/^\d+(\.\d{1,2})?$/.test($scope.txMoney) && $scope.txMoney>=Number($scope.minCount) ){
                $scope.check=true;
            }else{
                $scope.check=true;
            };
            $scope.txMoney=Number($scope.txMoney);
        };
        //确认提现
        $scope.checkCount=function(){
           // if($scope.txMoney)
            if(!$scope.txMoney){
                alertService.msgAlert("exclamation-circle", "提现金额不得为0");
                // $scope.txMoney='';
                return false;
            };
            if($scope.txMoney>$scope.count/100){
                alertService.msgAlert("exclamation-circle", "提现金额不得超过可提现金额");
               // $scope.txMoney='';
                return false;
            };
            if($scope.txMoney>20000.00){
                alertService.msgAlert("exclamation-circle", "单笔最高提现额度不得超过20000.00");
                $scope.txMoney='';
                return false;
            };
           // $state.go("main.brandApp.wallet.wdSuccess",{count:$scope.txMoney},{reload:true})
            //当确认体现直接调用提现接口，
            //提现接口
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/withdrawCash",
                params: {
                   // password:$scope.pswd,
                    applyAmount:$scope.txMoney*100
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    console.log('success',resp);
                    if(resp.data.status!='200'){
                        alertService.msgAlert("exclamation-circle",resp.data.data)
                    }else{
                        $state.go("main.brandApp.wallet.wdSuccess",{count:$scope.txMoney}, {reload: true});
                    };
                    //
                }, function (resp) {
                    //error
                }
            );
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$state',
    '$stateParams',
    'loginService',
    'alertService',
    '$filter',
    '$http',
    '$location',
];

export default Controller ;
