import conf from "../../../../conf";
import moment from "moment";
var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
       // console.log("====================", $stateParams);
        $scope.id = $state.params.id;
        // js控制写在此处
        $scope.curPage=1;
        //前端进行请求
        $scope.pageChanged=function(){
            $http({
                method:"GET",
                url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/"+$scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                //console.log(response);
                $scope.data=response.data;
            });
        }
        $scope.pageChanged();
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;

