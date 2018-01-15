import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $http = _$http;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };




        //------------------------------------------订单详情
        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $stateParams.id + '/detail',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.data = resp.data.data;

                    // console.log( $scope.data);

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
                        $scope.discountSpec = '减价' + $scope.data.activityPrice / 100 + '元';
                    }


                    // console.log('resp', resp);
                }, function () {

                }
            );
        };
        $scope.getData();







        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", {brandAppId:$stateParams.brandAppId}, {reload: true});
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
    '$http',
];

export default Controller ;
