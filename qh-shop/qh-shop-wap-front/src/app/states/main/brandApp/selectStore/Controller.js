import conf from "../../../../conf";
var $scope,
    loginService,
    $state,
    $stateParams,
    alertService,
    $location,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _alertService,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        $http = _$http;
        loginService.loginCtl(true, $location.absUrl());
        // $scope.go = function (state) {
        //     $state.go(state);
        // };
        $scope.buyPrice = {};
        $scope.getStore = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandAppId/" + $stateParams.brandAppId + '/home',
                // url: conf.apiPath + '/home',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $stateParams.brandAppId
                }
            }).then(function (resp) {
                if (resp.data.data.length == 1) {
                    $state.go('main.brandApp.store.home', {storeId: resp.data.data[0].shopId}, {reload: true})
                } else if (resp.data.data.length == 0) {
                    $scope.shopList = [];
                    $scope.getPrice();
                } else if (resp.data.data.length > 1) {
                    $scope.shopList = resp.data.data;
                }
                // console.log('resp-----------------------', resp);
            }, function (error) {

            });
        }
        $scope.getStore();

        ///brandApp/{brandAppId}/sysConf/shopPrice


        $scope.getPrice = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/sysConf/shopPrice',
                // url: conf.apiPath + '/home',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $stateParams.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-----------------------', resp);


                $scope.buyPrice = resp.data.data.monthly;
                $scope.free = resp.data.data.free.free;
                console.log('free', $scope.free);
            }, function (error) {
                alertService.msgAlert("exclamation-circle", error.data.message)
            });
        };

        $scope.canClick = true;


        $scope.getFree = function () {
            // /brandApp/{brandAppId}/shopOrder/create

            if ($scope.canClick) {
                $scope.canClick = false;
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shopOrder/create',
                    params: {},
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $stateParams.brandAppId
                    }
                }).then(function (resp) {
                    // console.log('resp-----------------------', resp);
                    $scope.canClick = true;

                    $scope.getStore();

                }, function (error) {

                });
            }

        };


        $scope.goBuy = function () {


            ///brandApp/{brandAppId}/shopOrder/buy

            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shopOrder/buy',
                params: {
                    duration: '1'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $stateParams.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-----------------------', resp);

                if (resp.data.data) {
                    // alert(resp.data.data);
                    // console.log(resp.data.data);

                    let back = 'brandApp/' + $stateParams.brandAppId + '/selectStore';
                    // let back = 'brandApp/' + $scope.brandAppId + "/store/"+$scope.storeId +"/personalCenter/allOrder";
                    location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$stateParams.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
                } else {
                    // console.log(resp.data.data);
                }
            }, function (resp) {
                alertService.msgAlert("exclamation-circle", resp.data.message)
            });
        }


    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    'alertService',
    '$location',
    '$http',
];

export default Controller ;
