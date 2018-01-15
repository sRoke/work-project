import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    alertService,
    $stateParams,
    $location,
    $timeout,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _alertService,
                _$stateParams,
                _$location,
                _$timeout,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        alertService=_alertService;
        $stateParams = _$stateParams;
        $location =_$location;
        $timeout=_$timeout;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId= $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.edit=false;
        $scope.changeEdit=function () {
            $scope.edit=!$scope.edit;
        };
        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId+"/withdraw/ali",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.item= resp.data.data;
                // console.log('$scope.data', $scope.item);

            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getInfo();
        // 设为默认
        $scope.setDefault = function (select,index) {
            // console.log('insdex',select,index);
            if(index==1){
                // console.log(111111111)
                select=false;
            }else{
                // console.log(2222222222);
                select=!select;
            };
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/withdraw/binding",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    binding:select,
                }
            }).then(function (resp) {
                    // console.log('set-info',resp);
                    $scope.item.default=select;
                    $timeout(function () {
                        $scope.getInfo();
                        // console.log('timeout')
                    }, 30);
                }, function () {

                }
            );
        };
        $scope.deleteAli=function () {
            alertService.confirm(null, "", "确定解绑？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/withdraw/ali",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp);
                        $scope.edit=false;
                        $scope.getInfo();
                    }, function (resp) {
                        //error
                    });
                }
            });
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                $state.go("main.brandApp.store.shop.myIncome", {brandAppId:$stateParams.brandAppId}, {reload: true});
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
    '$timeout',
    '$http',
];

export default Controller ;
