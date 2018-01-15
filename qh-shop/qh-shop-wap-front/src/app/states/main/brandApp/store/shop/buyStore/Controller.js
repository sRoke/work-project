import conf from "../../../../../../conf";
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
        alertService=_alertService;
        $location = _$location;
        $http = _$http;
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId=$stateParams.brandAppId;
        $scope.storeId=$stateParams.storeId;
        $scope.go = function (state) {
            $state.go(state);
        };
        $scope.buyPrice = {};
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
            }, function (error) {

            });
        };
        $scope.getPrice();
        $scope.goBuy = function () {
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/shop/"+ $scope.storeId+"/shopOrder/buy",
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

                    let back = 'brandApp/' + $stateParams.brandAppId + '/store/'+$scope.storeId+'/home';
                    // let back = 'brandApp/' + $scope.brandAppId + "/store/"+$scope.storeId +"/personalCenter/allOrder";
                    location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$stateParams.brandAppId}&backUrl=${encodeURIComponent(conf.payBackUrl + back)}`;
                } else {
                    // console.log(resp.data.data);
                }
            }, function (resp) {
                alertService.msgAlert("exclamation-circle",resp.data.message)
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
