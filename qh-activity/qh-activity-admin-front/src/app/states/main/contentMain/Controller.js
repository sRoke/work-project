import conf from "../../../conf";


var $scope,
    $state,
    $http,
    alertService,
    loginService;
class Controller {
    constructor(_$scope,
                _$state,
                _$http,
                _alertService,
                _loginService) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        $state = _$state;
        loginService.loginCtl(true);
        $scope.user = {};

        $scope.userInfo = function () {
            $http({
                method: "get",
                url: conf.oauthPath + "/api/s/user/info",
                withCredentials:true,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                }
            }).then(function (resp) {
                console.log("SUCCESS - ", resp);
                $scope.user = resp.data.data;
            }, function (err) {
                console.log("ERROR", err)
            });
        };
        $scope.userInfo();

        $scope.getUserApp = function () {
            $http({
                method: "get",
                url: conf.apiPath + "/home",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                },
                params: {}
            }).success(function (resp) {
                $scope.userAppList = resp.data;
                console.log(resp.data);
            });
        };

        $scope.getUserApp();

        $scope.goApp = function (app) {
            console.log(app.appUrl.replace('{brandAppId}',app.brandAppId));
            location.href = app.appUrl.replace('{brandAppId}',app.brandAppId);
        };

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);
            $http({
                method: "POST",
                url: "https:" + conf.oauthPath +"/logout",
                headers: {},
                params: {},
                withCredentials:true,
            }).success(function (resp) {
                // console.log('data', resp);
                location.reload();
            },function(resp){
                console.log('ERR', resp);
            });
        };


    }


}

Controller.$inject = [
    '$scope',
    '$state',
    '$http',
    'alertService',
    'loginService',

];

export default Controller ;
