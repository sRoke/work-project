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

        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup',
            params: {
                page:0,
                size:999,
                sort:[],
                keyWord:'',
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            $scope.dataInfo = resp.data.data;
            // console.log('resp-----------------------', resp);
        }, function (error) {

        });







        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.shop.shopManage", null, {reload: true});
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
